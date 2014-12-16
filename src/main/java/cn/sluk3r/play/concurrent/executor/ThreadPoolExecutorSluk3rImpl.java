package cn.sluk3r.play.concurrent.executor;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by baiing on 2014/7/28.
 */
public class ThreadPoolExecutorSluk3rImpl extends ExecutorServiceSluk3rImpl {
    /**
     * Lock held on updates to poolSize, corePoolSize, maximumPoolSize, runState, and workers set.wangxc 这些变量可以用统一的一个锁？
     */
    private final ReentrantLock mainLock = new ReentrantLock();

    /**
     * Maximum pool size, updated only while holding mainLock but
     * volatile to allow concurrent readability even during updates.
     */
    private volatile int   maximumPoolSize;

    /**
     * The queue used for holding tasks and handing off to worker
     * threads.  Note that when using this queue, we do not require
     * that workQueue.poll() returning null necessarily means that
     * workQueue.isEmpty(), so must sometimes check both. This
     * accommodates special-purpose queues such as DelayQueues for
     * which poll() is allowed to return null even if it may later
     * return non-null when delays expire.
     */
    private final BlockingQueue<Runnable> workQueue;//wangxc  execute时，应该不会往这个Queue里放。

    /**
     * Current pool size, updated only while holding mainLock but
     * volatile to allow concurrent readability even during updates.
     */
    private volatile int   poolSize;
    /**
     * Core pool size, updated only while holding mainLock, but
     * volatile to allow concurrent readability even during updates.
     */
    private volatile int   corePoolSize; //wangxc 这个跟上面的poolSize在语义上有什么不同？

    /**
     * runState provides the main lifecyle control, taking on values:
     *
     *   RUNNING:  Accept new tasks and process queued tasks
     *   SHUTDOWN: Don't accept new tasks, but process queued tasks
     *   STOP:     Don't accept new tasks, don't process queued tasks,
     *             and interrupt in-progress tasks
     *   TERMINATED: Same as STOP, plus all threads have terminated
     *
     * The numerical order among these values matters, to allow
     * ordered comparisons. The runState monotonically increases over
     * time, but need not hit each state. The transitions are:
     *
     * RUNNING -> SHUTDOWN
     *    On invocation of shutdown(), perhaps implicitly in finalize()
     * (RUNNING or SHUTDOWN) -> STOP
     *    On invocation of shutdownNow()
     * SHUTDOWN -> TERMINATED
     *    When both queue and pool are empty
     * STOP -> TERMINATED
     *    When pool is empty
     */
    volatile int runState;
    static final int RUNNING    = 0;
    static final int SHUTDOWN   = 1;
    static final int STOP       = 2;
    static final int TERMINATED = 3;

    /**
     * Tracks largest attained pool size.
     */
    private int largestPoolSize; //wangxc 这个变量有什么用？

    /**
     * Factory for new threads. All threads are created using this
     * factory (via method addThread).  All callers must be prepared
     * for addThread to fail by returning null, which may reflect a
     * system or user's policy limiting the number of threads.  Even
     * though it is not treated as an error, failure to create threads
     * may result in new tasks being rejected or existing ones
     * remaining stuck in the queue. On the other hand, no special
     * precautions exist to handle OutOfMemoryErrors that might be
     * thrown while trying to create threads, since there is generally
     * no recourse from within this class.
     */
    private volatile ThreadFactory threadFactory;



    /**
     * Set containing all worker threads in pool. Accessed only when
     * holding mainLock.
     */
    private final HashSet<Worker> workers = new HashSet<Worker>();

    /**
     * The default rejected execution handler
     */
    private static final RejectedExecutionHandler defaultHandler =
            new AbortPolicy();

    /**
     * Handler called when saturated or shutdown in execute.
     */
    private volatile RejectedExecutionHandlerSluk3r handler;

    /**
     * Timeout in nanoseconds for idle threads waiting for work.
     * Threads use this timeout when there are more than corePoolSize
     * present or if allowCoreThreadTimeOut. Otherwise they wait
     * forever for new work.
     */
    private volatile long  keepAliveTime;



    public ThreadPoolExecutorSluk3rImpl(int corePoolSize,
                                        int maximumPoolSize,
                                        long keepAliveTime,
                                        TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue,
                                        ThreadFactory threadFactory,
                                        RejectedExecutionHandlerSluk3r handler) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }


    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }


    /*
     * Support for execute().
     *
     * Method execute() and its helper methods handle the various
     * cases encountered when new tasks are submitted.  The main
     * execute() method proceeds in 3 steps:
     *
     * 1. If it appears that fewer than corePoolSize threads are
     * running, try to start a new thread with the given command as
     * its first task.  The check here errs on the side of caution.
     * The call to addIfUnderCorePoolSize rechecks runState and pool
     * size under lock (they change only under lock) so prevents false
     * alarms that would add threads when it shouldn't, but may also
     * fail to add them when they should. This is compensated within
     * the following steps.
     *
     * 2. If a task can be successfully queued, then we are done, but
     * still need to compensate for missing the fact that we should
     * have added a thread (because existing ones died) or that
     * shutdown occurred since entry into this method. So we recheck
     * state and if necessary (in ensureQueuedTaskHandled) roll back
     * the enqueuing if shut down, or start a new thread if there are
     * none.
     *
     * 3. If we cannot queue task, then we try to add a new
     * thread. There's no guesswork here (addIfUnderMaximumPoolSize)
     * since it is performed under lock.  If it fails, we know we are
     * shut down or saturated.
     *
     * The reason for taking this overall approach is to normally
     * avoid holding mainLock during this method, which would be a
     * serious scalability bottleneck.  After warmup, almost all calls
     * take step 2 in a way that entails no locking.
     */
    /**
     * Executes the given task sometime in the future.  The task
     * may execute in a new thread or in an existing pooled thread.
     *
     * If the task cannot be submitted for execution, either because this
     * executor has been shutdown or because its capacity has been reached,
     * the task is handled by the current <tt>RejectedExecutionHandler</tt>.
     *
     * @param command the task to execute
     * @throws java.util.concurrent.RejectedExecutionException at discretion of
     * <tt>RejectedExecutionHandler</tt>, if task cannot be accepted
     * for execution
     * @throws NullPointerException if command is null
     */
    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        //wangxc 这里的poolSize >= corePoolSize， 表示什么？
        /*
        1. 从时间上来看， 应该是先执行addIfUnderCorePoolSize， 这里有一个疑问， 如果两条件（poolSize >= corePoolSize || !addIfUnderCorePoolSize(command)）都没满足时情况怎样？ 都没满足， 就表示还在corePoolSize以下。

         */
        if (poolSize >= corePoolSize || !addIfUnderCorePoolSize(command)) {//wangxc， 如果没有添加成功， 表示已经超出Pool的大小。 接下来的工作，就是怎么处理超出Pool的情况了。 这个条件里没有加是否RUNNING的判断
            if (runState == RUNNING && workQueue.offer(command)) { //wangxc 超过Pool的限制后， 就回到workQueue里。 有workers和workQueue两个集合。 有什么区别？
                if (runState != RUNNING || poolSize == 0) //wangxc， 也就是说虽然加进来了，但还没有执行，只有在一定条件下才执行。 runState为什么不是RUNNING?
                    ensureQueuedTaskHandled(command);
            }
            else if (!addIfUnderMaximumPoolSize(command))
                reject(command);
        }
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////private method
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Creates and starts a new thread running firstTask as its first
     * task, only if fewer than corePoolSize threads are running
     * and the pool is not shut down.
     * @param firstTask the task the new thread should run first (or
     * null if none)
     * @return true if successful
     */
    private boolean addIfUnderCorePoolSize(Runnable firstTask) {
        Thread t = null;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            if (poolSize < corePoolSize && runState == RUNNING)
                t = addThread(firstTask);
        } finally {
            mainLock.unlock();
        }
        return t != null;
    }

    void reject(Runnable command) {
        handler.rejectedExecution(command, this);
    }

    private final class Worker implements Runnable {

        public Thread thread;

        public Worker(Runnable firstTask) {

        }

        @Override
        public void run() {

        }
    }

    /**
     * Creates and returns a new thread running firstTask as its first
     * task. Call only while holding mainLock.
     *
     * @param firstTask the task the new thread should run first (or
     * null if none)
     * @return the new thread, or null if threadFactory fails to create thread
     */
    private Thread addThread(Runnable firstTask) {
        Worker w = new Worker(firstTask);//wangxc 惯用方式，会用代理模式，加管理方面的功能。
        Thread t = threadFactory.newThread(w);//wangxc 看了几个实现， 对工厂方法有了进一步的认识。
        boolean workerStarted = false;
        if (t != null) {
            if (t.isAlive()) // precheck that t is startable
                throw new IllegalThreadStateException();
            w.thread = t;
            workers.add(w);//wangxc
            int nt = ++poolSize;//wangxc 这个方法自身并没有加锁， 但在调用前后加锁。
            if (nt > largestPoolSize)// 这个是记录Pool的历史最高值， 会在哪用到？ 刚看了下， 当前类里没有找到实际的地方。
                largestPoolSize = nt; //wangxc  记录这个峰值有什么意义？
            try {
                t.start();
                workerStarted = true;
            }
            finally {
                if (!workerStarted) //wangxc 会有什么因素搞的不能启动成功？
                    workers.remove(w);
            }
        }
        return t;
    }



    /**
     * Rechecks state after queuing a task. Called from execute when
     * pool state has been observed to change after queuing a task. If
     * the task was queued concurrently with a call to shutdownNow,
     * and is still present in the queue, this task must be removed
     * and rejected to preserve shutdownNow guarantees.  Otherwise,
     * this method ensures (unless addThread fails) that there is at
     * least one live thread to handle this task
     * @param command the task
     */
    private void ensureQueuedTaskHandled(Runnable command) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        boolean reject = false;
        Thread t = null;
        try {
            int state = runState;
            if (state != RUNNING && workQueue.remove(command))
                reject = true;
            else if (state < STOP &&
                    poolSize < Math.max(corePoolSize, 1) &&
                    !workQueue.isEmpty())
                t = addThread(null);
        } finally {
            mainLock.unlock();
        }
        if (reject)
            reject(command);
    }



    /**
     * Creates and starts a new thread running firstTask as its first
     * task, only if fewer than maximumPoolSize threads are running
     * and pool is not shut down.
     * @param firstTask the task the new thread should run first (or
     * null if none)
     * @return true if successful
     */
    private boolean addIfUnderMaximumPoolSize(Runnable firstTask) {
        Thread t = null;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            if (poolSize < maximumPoolSize && runState == RUNNING)
                t = addThread(firstTask);
        } finally {
            mainLock.unlock();
        }
        return t != null;
    }

    /**
     * A handler for rejected tasks that throws a
     * <tt>RejectedExecutionException</tt>.
     */
    public static class AbortPolicy implements RejectedExecutionHandler {
        /**
         * Creates an <tt>AbortPolicy</tt>.
         */
        public AbortPolicy() { }

        /**
         * Always throws RejectedExecutionException.
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         * @throws java.util.concurrent.RejectedExecutionException always.
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new RejectedExecutionException();
        }
    }

    public interface RejectedExecutionHandlerSluk3r {
        /**
         * Method that may be invoked by a {@link ThreadPoolExecutor} when
         * {@link ThreadPoolExecutor#execute execute} cannot accept a
         * task.  This may occur when no more threads or queue slots are
         * available because their bounds would be exceeded, or upon
         * shutdown of the Executor.
         *
         * <p>In the absence of other alternatives, the method may throw
         * an unchecked {@link RejectedExecutionException}, which will be
         * propagated to the caller of {@code execute}.
         *
         * @param r the runnable task requested to be executed
         * @param executor the executor attempting to execute this task
         * @throws RejectedExecutionException if there is no remedy
         */
        void rejectedExecution(Runnable r, ThreadPoolExecutorSluk3rImpl executor);
    }
}

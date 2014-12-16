package cn.sluk3r.play.concurrent.executor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by baiing on 2014/7/26.
 */
public abstract class ExecutorServiceSluk3rImpl implements ExecutorService {

    ///////////////////////////////////////////////////////////////////
    /////abstract methods implemented in ThreadPoolExecutor
    ///////////////////////////////////////////////////////////////////
    @Override
    public abstract void shutdown();

    @Override
    public abstract  List<Runnable> shutdownNow();

    @Override
    public abstract  void execute(Runnable command);

    @Override
    public abstract boolean isShutdown();

    @Override
    public abstract boolean isTerminated();

    @Override
    public abstract boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
    ///////////////////////////////////////////////////////////////////



    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> ftask = newTaskFor(task);
        execute(ftask);
        return ftask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }



    ////////////////////////////////////////////protected and private///////////////////////////
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new FutureTask<T>(callable);
    }
}

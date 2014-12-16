package cn.sluk3r.test.concurrent.withLog;

import cn.sluk3r.play.collection.ArrayBlockingQueueSluk3rImpl;
import cn.sluk3r.play.concurrent.executor.withLog.ThreadPoolExecutorWithLog;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by baiing on 2014/12/1.
 */
public class ThreadPoolWithLogDemo {
    private static final Logger logger = Logger.getLogger(ThreadPoolWithLogDemo.class);
    private static final int SEED_FOR_SLEEP = 150;

    @Test
    public void poolSize() throws InterruptedException {
        int taskSize = 100;

        int corePoolSize = 1;
        int maxPoolSize = 3;
        int keepAliveTime = 5;
        int queueSize = 2;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(queueSize);
        ExecutorService e = new ThreadPoolExecutorWithLog(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, Executors.defaultThreadFactory(), new ThreadPoolExecutorWithLog.AbortPolicyWithLog());

        for (int i=0;i< taskSize;i++) {
            e.execute(makeRunnable());
        }

        while(!e.isTerminated()) {
            logger.info("wait for termination");
            TimeUnit.SECONDS.sleep(100);
        }
        logger.info("end...............");
    }

    private Runnable makeRunnable() {
        return  new Runnable() {
            @Override
            public void run() {
                Random r = new Random();
                try {
                    int sleepSeconds = r.nextInt(SEED_FOR_SLEEP);
                    logger.info("to sleep: "  + sleepSeconds + "s");
                    TimeUnit.SECONDS.sleep(sleepSeconds);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        };
    }

}

/*
1. grep 'execute===========' ./threadPoolLogFile.log  | awk -F  "===================" '{print $2}'

poolSize:[0][1], corePoolSize:[1][1], maximumPoolSize:[3][3], queue size:[0][0]
poolSize:[1][1], corePoolSize:[1][1], maximumPoolSize:[3][3], queue size:[0][1]
poolSize:[1][1], corePoolSize:[1][1], maximumPoolSize:[3][3], queue size:[1][2]
poolSize:[1][2], corePoolSize:[1][1], maximumPoolSize:[3][3], queue size:[2][2]
poolSize:[2][3], corePoolSize:[1][1], maximumPoolSize:[3][3], queue size:[2][2]
 */
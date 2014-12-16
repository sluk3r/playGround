package cn.sluk3r.test.collection.concurrent.threadPool;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by baiing on 2014/11/21.
 */
public class PoolDemo {
    private static  Logger logger = Logger.getLogger(PoolDemo.class);
    final Random r = new Random();

    @Test
    public void demo() throws InterruptedException {
        int threadNum = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        int taskNum = threadNum + 5;
        for (int i=0;i<taskNum;i++) {
            executorService.execute(makeTask());

            if (i == threadNum) {
                logger.info("about to submit task, and the total number will exceed the pool size");
            }
        }

        logger.info("submit completed, and wait for all completed");
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        logger.info("all task done, would return");
    }


    private Runnable makeTask() {
        return  new Runnable() {
            @Override
            public void run() {
                int timeFowSleep = r.nextInt(5) + 1;

                try {
                    logger.info("about to sleep for " + timeFowSleep + "s");
                    TimeUnit.SECONDS.sleep(timeFowSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

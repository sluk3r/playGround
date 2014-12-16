package cn.sluk3r.test.concurrent.withLog;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by baiing on 2014/12/15.
 */
public class SynchronousQueueCachedThreadpool {
    private static final Logger logger = Logger.getLogger(SynchronousQueueCachedThreadpool.class);
    Random r = new Random(100);

    @Test
    public void demo() {
        ExecutorService e = Executors.newCachedThreadPool();

        for (int i=0;i<10;i++) {
            e.execute(makeRunnable());
        }
    }

    private Runnable makeRunnable() {
        return new Runnable() {
            @Override
            public void run() {

//                boolean randomBoolean = r.nextBoolean();
//
//                if (randomBoolean) {
//                    throw  new NullPointerException("thrown intentionally");
//                }

                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        };
    }
}

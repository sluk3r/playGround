package cn.sluk3r.test.collection.concurrent;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by baiing on 2014/11/19.
 */
public class ConcurrentModificationExceptionDemo {
    private static Logger logger = Logger.getLogger(ConcurrentModificationExceptionDemo.class);

    @Test
    public void demo() {
        final List<String> list = new ArrayList<String>(Arrays.asList(new String[]{"abse", "fdaeqr", "fdafda", "reqreiqo", "fdafda", "reiqoreqoe", "dafdaeqre", "fdafdaeqrewq", "vafdafdqe", "ewqreqre3143143143"}));

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Callable<Date>  readTask = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                int i= 0;
                for(String s : list) {
                    logger.info("element from List: " + s);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }

                    if (i == (list.size() / 2)) {
                        countDownLatch.countDown();
                    }
                }
                return  new Date();
            }
        };


        Callable<Date>  modifyTask = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                list.add(String.valueOf(System.currentTimeMillis()));

                logger.info("after countDown");

                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
                return new Date();
            }
        };


        try {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            Future<Date> resultFromRead = executorService.submit(readTask);
            Future<Date> resultFromAdd = executorService.submit(modifyTask);

            logger.info("Result got from resultFromRead: " + resultFromRead.get());
            logger.info("Result got from resultFromAdd: " + resultFromAdd.get());
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
        } catch (ConcurrentModificationException e) {
            assertTrue(true);
        }

    }

}

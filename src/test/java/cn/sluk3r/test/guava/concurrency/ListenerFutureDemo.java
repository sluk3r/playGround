package cn.sluk3r.test.guava.concurrency;

import com.google.common.util.concurrent.*;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by baiing on 2014/12/8.
 */
public class ListenerFutureDemo {
    private final static  Logger logger = Logger.getLogger(ListenerFutureDemo.class);

    @Test
    public void demo() throws InterruptedException {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        Callable<Date> callableTask = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                TimeUnit.SECONDS.sleep(30);

                logger.info("sleep end");

                return new Date();
            }
        };

        final ListenableFuture futureTask = executorService.submit(callableTask);

        Futures.addCallback(futureTask, new FutureCallback<Date>() {
            @Override
            public void onSuccess(Date date) {
                logger.info("date from future: " + date);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        executorService.awaitTermination(2, TimeUnit.MINUTES);
    }
}

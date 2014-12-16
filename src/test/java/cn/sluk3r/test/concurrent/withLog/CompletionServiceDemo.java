package cn.sluk3r.test.concurrent.withLog;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by baiing on 2014/12/16.
 */
public class CompletionServiceDemo {

    @Test
    public void demo() {
        Executor exec = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<String>(exec);

        for (int i = 0; i < 4; i++) {
            // Callable task that will be submitted to
            // CompletionService.
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() {

                    try {
                        System.out.println("Started " + Thread.currentThread() + " at "   + new Date());
                        Thread.sleep(5000);
                        System.out.println("Exiting " + Thread.currentThread() + " at "   + new Date());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return Thread.currentThread() + " success!!!";
                }

            };

            // Submitting the task to the CompletionService
            completionService.submit(task);
        }

        // Waiting for the results and printing them
        for (int i = 0; i < 4; i++) {
            Future<String> f;
            try {
                f = completionService.take();
                System.out.println("RESULT=" + f.get() + " at " + new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

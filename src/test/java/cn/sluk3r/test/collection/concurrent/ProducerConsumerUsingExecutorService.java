package cn.sluk3r.test.collection.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by baiing on 2014/7/15.
 */
public class ProducerConsumerUsingExecutorService {
    ExecutorServiceThreadPool executorServiceThreadPool;
    static ArrayList consumerdata = new ArrayList();

    public static void main(String[] args) {
        ProducerConsumerUsingExecutorService prodconsumer = new ProducerConsumerUsingExecutorService();
        prodconsumer.init();
        Iterator itr = consumerdata.iterator();
        while(itr.hasNext()) {
            Object element = itr.next();
            System.out.print(element + " ");
        }
    }

    private void init() {
        executorServiceThreadPool = new ExecutorServiceThreadPool();
        for(int i = 0; i < 10; i++){
            executorServiceThreadPool.addThread(new Producer(i));
            executorServiceThreadPool.addThread(new Consumer());
        }
        executorServiceThreadPool.finish();
    }

    private class Producer implements Runnable {
        int data;
        public Producer(int datatoput) {
            data = datatoput;
        }

        @Override
        public void run() {
            System.out.println("Inserting Element " + data);
            try {
                executorServiceThreadPool.queue.put(data);
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    private class Consumer implements Runnable {
        int datatake;
        @Override
        public void run() {
            try {
                datatake = executorServiceThreadPool.queue.take();
                consumerdata.add(datatake);
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

    }
}


class ExecutorServiceThreadPool {
    final BlockingQueue<Integer> queue =null;

    ExecutorService executor = Executors.newFixedThreadPool(2);
    public void addThread(Runnable r){
        executor.submit(r);
    }

    public void finish(){
        try {
            executor.shutdown();
            executor.awaitTermination(50, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecutorServiceThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Finished all threads");
    }

}
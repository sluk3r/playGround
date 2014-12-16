package cn.sluk3r.test.collection.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by baiing on 2014/7/15.
 */
public class ProducerConsumerDemo {
    protected boolean done = false;

    public ProducerConsumerDemo(int nP, int nC) {
        BlockingQueue myQueue = new LinkedBlockingQueue();
        for (int i=0; i<nP; i++)
            new Thread(new Producer(myQueue)).start();
        for (int i=0; i<nC; i++)
            new Thread(new Consumer(myQueue)).start();
    }

    class Producer implements Runnable {
        protected BlockingQueue queue;

        Producer(BlockingQueue theQueue) { this.queue = theQueue; }

        public void run() {
            try {
                while (true) {
                    Object justProduced = getRequestFromNetwork();
                    queue.put(justProduced);
                    System.out.println("Produced 1 object; List size now " + queue.size());
                    if (done) {
                        return;
                    }
                }
            } catch (InterruptedException ex) {
                System.out.println("Producer INTERRUPTED");
            }
        }

        Object getRequestFromNetwork() {  // Simulation of reading from client
            try {
                Thread.sleep(10); // simulate time passing during read
            } catch (InterruptedException ex) {
                System.out.println("Producer Read INTERRUPTED");
            }
            return(new Object());
        }
    }

    /** Inner class representing the Consumer side */
    class Consumer implements Runnable {
        protected BlockingQueue queue;
        Consumer(BlockingQueue theQueue) { this.queue = theQueue; }
        public void run() {
            try {
                while (true) {
                    Object obj = queue.take();
                    int len = queue.size();
                    System.out.println("List size now " + len);
                    process(obj);
                    if (done) {
                        return;
                    }
                }
            } catch (InterruptedException ex) {
                System.out.println("CONSUMER INTERRUPTED");
            }
        }

        void process(Object obj) {
            // Thread.sleep(xxx) // Simulate time passing
            System.out.println("Consuming object " + obj);
        }
    }


    @Test
    public void pri() throws InterruptedException {
        int numProducers = 4;
        int numConsumers = 3;
        ProducerConsumerDemo pc = new ProducerConsumerDemo(numProducers, numConsumers);

        // Let the simulation run for, say, 10 seconds
        Thread.sleep(10*1000);

        // End of simulation - shut down gracefully
        pc.done = true;
    }


    @Test
    public void demo() throws InterruptedException {
        final ExecutorService producers = Executors.newFixedThreadPool(100);
        final ExecutorService consumers = Executors.newFixedThreadPool(100);





        producers.shutdown();
        producers.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        consumers.shutdown();
        consumers.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}


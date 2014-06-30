package cn.sluk3r.play.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HashMapInfiniteLoopSimulator {
    private static final int NB_THREADS = 3;
    private static final int NB_TEST_ITERATIONS = 50;
    private static final int LOOP_SIZE = Integer.parseInt(System.getProperty("worker_loop_size", "100"));

    private static Map<String, Integer> nonThreadSafeMap = null;
    private static Map<String, Integer> threadSafeMap1 = null;
    private static Map<String, Integer> threadSafeMap2 = null;
    private static Map<String, Integer> threadSafeMap3 = null;

    /**
     * Main program
     *
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println("Infinite Looping HashMap Simulator");
//        System.out.println("Author: Pierre-Hugues Charbonneau");
//        System.out.println("http://javaeesupportpatterns.blogspot.com");

        // Plain old HashMap (since JDK 1.2)
//        nonThreadSafeMap = new HashMap<String, Integer>(2);
        nonThreadSafeMap = new HashMap<String, Integer>(1000000);

        // Plain old Hashtable (since JDK 1.0)
        threadSafeMap1 = new Hashtable<String, Integer>(2);

        // Fully synchronized HashMap
        threadSafeMap2 = new HashMap<String, Integer>(2);
        threadSafeMap2 = Collections.synchronizedMap(threadSafeMap2);

        // ConcurrentHashMap (since JDK 1.5)
        threadSafeMap3 = new ConcurrentHashMap<String, Integer>(2); // ConcurrentHashMap

        Map<String,Map> testedMapImpl = new HashMap();
        testedMapImpl.put("nonThreadSafeMap_HashMap",nonThreadSafeMap);
        testedMapImpl.put("threadSafeMap1_HashTable",threadSafeMap1);
        testedMapImpl.put("threadSafeMap2_synchronizedHashMap",threadSafeMap2);
        testedMapImpl.put("ConcurrentHashMap",threadSafeMap3);

        if (args.length > 0) {
            doTest(testedMapImpl.get(args[0]), args[0]);
        } else {
            for(Map.Entry<String,Map> me: testedMapImpl.entrySet()) {
                try {
                    doTest(me.getValue(), me.getKey());
                } catch (Throwable t) {
                    t.printStackTrace();
                }

            }
        }

    }

    static void doTest(Map assignedMapForTest, String name) {
        System.out.println("run " + name);
        float totalProcessingTimeOuter = 0;
        for (int i = 0; i < NB_TEST_ITERATIONS; i++) {
            /*** Assign map at your convenience ****/
            long timeBefore = System.currentTimeMillis();
            long timeAfter = 0;
            float totalProcessingTime = 0;

            ExecutorService executor = Executors.newFixedThreadPool(NB_THREADS);

            for (int j = 0; j < NB_THREADS; j++) {

                /** Assign the Map at your convenience **/
                Runnable worker = new WorkerThread(assignedMapForTest);
                executor.execute(worker);
            }

            // This will make the executor accept no new threads
            // and finish all existing threads in the queue
            executor.shutdown();
            // Wait until all threads are finish
            while (!executor.isTerminated()) {

            }

            timeAfter = System.currentTimeMillis();
            totalProcessingTime = new Float((float) (timeAfter - timeBefore) / (float) 1000);

            totalProcessingTimeOuter += totalProcessingTime;

//            System.out.println("All threads completed in " + totalProcessingTime + " seconds, Map impl name is: " + name + " threads number: " + NB_THREADS + " loop number: " + NB_TEST_ITERATIONS);
        }

        System.out.println(" Map impl name is: " + name + ", avg Time: " + totalProcessingTimeOuter/NB_TEST_ITERATIONS);
        System.gc();
    }


    static class WorkerThread implements Runnable {
        private Map<String, Integer> map = null;

        public WorkerThread(Map<String, Integer> assignedMap) {
            this.map = assignedMap;

        }

        @Override
        public void run() {
            for (int i=0; i<LOOP_SIZE; i++) {
                try {
                    // Return 2 integers between 1-1000000 inclusive
                    Integer newInteger1 = (int) Math.ceil(Math.random() * 1000000);
                    Integer newInteger2 = (int) Math.ceil(Math.random() * 1000000);

                    // 1. Attempt to retrieve a random Integer element
                    Integer retrievedInteger = map.get(String.valueOf(newInteger1));

                    // 2. Attempt to insert a random Integer element
                    map.put(String.valueOf(newInteger2), newInteger2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

/*

java -Dworker_loop_size=500  cn.sluk3r.play.collection.HashMapInfiniteLoopSimulator
 Map impl name is: threadSafeMap2_synchronizedHashMap, avg Time: 0.010840002
 Map impl name is: ConcurrentHashMap, avg Time: 0.009520002
 Map impl name is: threadSafeMap1_HashTable, avg Time: 0.0062
 Hash table 卡住没有退出




java -Dworker_loop_size=1000  cn.sluk3r.play.collection.HashMapInfiniteLoopSimulator

Map impl name is: threadSafeMap2_synchronizedHashMap, avg Time: 0.012700001
Map impl name is: ConcurrentHashMap, avg Time: 0.00998
Map impl name is: threadSafeMap1_HashTable, avg Time: 0.0128
Hash table 卡住没有退出

 */
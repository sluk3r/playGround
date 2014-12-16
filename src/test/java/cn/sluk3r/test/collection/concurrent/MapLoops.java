package cn.sluk3r.test.collection.concurrent;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by baiing on 2014/6/20.
 */
public class MapLoops {

    static int nkeys       = 10000;
    static int pinsert     = 60;
    static int premove     = 2;
    static int maxThreads  = 100;
    static int nops        = 100000;
    static int removesPerMaxRandom;
    static int insertsPerMaxRandom;

    static final ExecutorService pool = Executors.newCachedThreadPool();

    static final List<Throwable> throwables
            = new CopyOnWriteArrayList<Throwable>();

    public static void main(String[] args) throws Exception {

        Class mapClass = null;
        if (args.length > 0) {
            try {
                mapClass = Class.forName(args[0]);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class " + args[0] + " not found.");
            }
        }
        else
            mapClass = java.util.concurrent.ConcurrentHashMap.class;

        if (args.length > 1)
            maxThreads = Integer.parseInt(args[1]);

        if (args.length > 2)
            nkeys = Integer.parseInt(args[2]);

        if (args.length > 3)
            pinsert = Integer.parseInt(args[3]);

        if (args.length > 4)
            premove = Integer.parseInt(args[4]);

        if (args.length > 5)
            nops = Integer.parseInt(args[5]);

        // normalize probabilities wrt random number generator
        removesPerMaxRandom = (int)(((double)premove/100.0 * 0x7FFFFFFFL));
        insertsPerMaxRandom = (int)(((double)pinsert/100.0 * 0x7FFFFFFFL));

        System.out.print("Class: " + mapClass.getName());
        System.out.print(" threads: " + maxThreads);
        System.out.print(" size: " + nkeys);
        System.out.print(" ins: " + pinsert);
        System.out.print(" rem: " + premove);
        System.out.print(" ops: " + nops);
        System.out.println();

        int k = 1;
        int warmups = 2;
        for (int i = 1; i <= maxThreads;) {
            Thread.sleep(100);
            test(i, nkeys, mapClass);
            if (warmups > 0)
                --warmups;
            else if (i == k) {
                k = i << 1;
                i = i + (i >>> 1);
            }
            else if (i == 1 && k == 2) {
                i = k;
                warmups = 1;
            }
            else
                i = k;
        }
        pool.shutdown();
        if (! pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS))
            throw new Error();

        if (! throwables.isEmpty())
            throw new Error
                    (throwables.size() + " thread(s) terminated abruptly.");
    }

    static Integer[] makeKeys(int n) {
        LoopHelpers.SimpleRandom rng = new LoopHelpers.SimpleRandom();
        Integer[] key = new Integer[n];
        for (int i = 0; i < key.length; ++i)
            key[i] = new Integer(rng.next());
        return key;
    }

    static void shuffleKeys(Integer[] key) {
        Random rng = new Random();
        for (int i = key.length; i > 1; --i) {
            int j = rng.nextInt(i);
            Integer tmp = key[j];
            key[j] = key[i-1];
            key[i-1] = tmp;
        }
    }

    static void test(int i, int nkeys, Class mapClass) throws Exception {
        System.out.print("Threads: " + i + "\t:");
        Map<Integer, Integer> map = (Map<Integer,Integer>)mapClass.newInstance();
        Integer[] key = makeKeys(nkeys);
        // Uncomment to start with a non-empty table
        //        for (int j = 0; j < nkeys; j += 4) // start 1/4 occupied
        //            map.put(key[j], key[j]);
        LoopHelpers.BarrierTimer timer = new LoopHelpers.BarrierTimer();
        CyclicBarrier barrier = new CyclicBarrier(i+1, timer);
        for (int t = 0; t < i; ++t)
            pool.execute(new Runner(map, key, barrier));
        barrier.await();
        barrier.await();
        long time = timer.getTime();
        long tpo = time / (i * (long)nops);
        System.out.print(LoopHelpers.rightJustify(tpo) + " ns per op");
        double secs = (double)(time) / 1000000000.0;
        System.out.println("\t " + secs + "s run time");
        map.clear();
    }

    static class Runner implements Runnable {
        final Map<Integer,Integer> map;
        final Integer[] key;
        final LoopHelpers.SimpleRandom rng = new LoopHelpers.SimpleRandom();
        final CyclicBarrier barrier;
        int position;
        int total;

        Runner(Map<Integer,Integer> map, Integer[] key,  CyclicBarrier barrier) {
            this.map = map;
            this.key = key;
            this.barrier = barrier;
            position = key.length / 2;
        }

        int step() {
            // random-walk around key positions,  bunching accesses
            int r = rng.next();
            position += (r & 7) - 3;
            while (position >= key.length) position -= key.length;
            while (position < 0) position += key.length;

            Integer k = key[position];
            Integer x = map.get(k);

            if (x != null) {
                if (x.intValue() != k.intValue())
                    throw new Error("bad mapping: " + x + " to " + k);

                if (r < removesPerMaxRandom) {
                    if (map.remove(k) != null) {
                        position = total % key.length; // move from position
                        return 2;
                    }
                }
            }
            else if (r < insertsPerMaxRandom) {
                ++position;
                map.put(k, k);
                return 2;
            }

            // Uncomment to add a little computation between accesses
            //            total += LoopHelpers.compute1(k.intValue());
            total += r;
            return 1;
        }

        public void run() {
            try {
                barrier.await();
                int ops = nops;
                while (ops > 0)
                    ops -= step();
                barrier.await();
            }
            catch (Throwable throwable) {
                synchronized(System.err) {
                    System.err.println("--------------------------------");
                    throwable.printStackTrace();
                }
                throwables.add(throwable);
            }
        }
    }
}


class LoopHelpers {

    // Some mindless computation to do between synchronizations...

    /**
     * generates 32 bit pseudo-random numbers.
     * Adapted from http://www.snippets.org
     */
    public static int compute1(int x) {
        int lo = 16807 * (x & 0xFFFF);
        int hi = 16807 * (x >>> 16);
        lo += (hi & 0x7FFF) << 16;
        if ((lo & 0x80000000) != 0) {
            lo &= 0x7fffffff;
            ++lo;
        }
        lo += hi >>> 15;
        if (lo == 0 || (lo & 0x80000000) != 0) {
            lo &= 0x7fffffff;
            ++lo;
        }
        return lo;
    }

    /**
     *  Computes a linear congruential random number a random number
     *  of times.
     */
    public static int compute2(int x) {
        int loops = (x >>> 4) & 7;
        while (loops-- > 0) {
            x = (x * 2147483647) % 16807;
        }
        return x;
    }

    /**
     * An actually useful random number generator, but unsynchronized.
     * Basically same as java.util.Random.
     */
    public static class SimpleRandom {
        private final static long multiplier = 0x5DEECE66DL;
        private final static long addend = 0xBL;
        private final static long mask = (1L << 48) - 1;
        static final AtomicLong seq = new AtomicLong(1);
        private long seed = System.nanoTime() + seq.getAndIncrement();

        public void setSeed(long s) {
            seed = s;
        }

        public int next() {
            long nextseed = (seed * multiplier + addend) & mask;
            seed = nextseed;
            return ((int)(nextseed >>> 17)) & 0x7FFFFFFF;
        }
    }

    public static class BarrierTimer implements Runnable {
        public volatile long startTime;
        public volatile long endTime;
        public void run() {
            long t = System.nanoTime();
            if (startTime == 0)
                startTime = t;
            else
                endTime = t;
        }
        public void clear() {
            startTime = 0;
            endTime = 0;
        }
        public long getTime() {
            return endTime - startTime;
        }
    }

    public static String rightJustify(long n) {
        // There's probably a better way to do this...
        String field = "         ";
        String num = Long.toString(n);
        if (num.length() >= field.length())
            return num;
        StringBuffer b = new StringBuffer(field);
        b.replace(b.length()-num.length(), b.length(), num);
        return b.toString();
    }

}
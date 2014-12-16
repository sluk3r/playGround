package cn.sluk3r.test.collection;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by baiing on 2014/12/3.
 */
public class HashMapDemo {

    static class PerfTestResult {
        static final String template = "\t%s\t%s\t%s";
        int cnt;
        long hashMapDuration;
        long treeMapDuration;

        public PerfTestResult(int cnt, long hashMapDuration, long treeMapDuration) {
            this.cnt = cnt;
            this.hashMapDuration = hashMapDuration;
            this.treeMapDuration = treeMapDuration;
        }

        public String toString() {
            return String.format(template, cnt, hashMapDuration, treeMapDuration);
        }
    }

    Random r = new Random(1000);

    private int randomInt() {
        return r.nextInt(999);
    }

    private void doPut(Map m, int cnt) {
        for (int i=0;i<cnt;i++) {
            m.put(i, String.valueOf(i));
        }
    }


    private PerfTestResult doPerfTestBatchly(int cnt) {
        Map hashMap = new HashMap();
        Map treeMap = new TreeMap();

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        doPut(hashMap, cnt);
        stopWatch.split();
        long hashMapDuration = stopWatch.getSplitTime();

        doPut(treeMap, cnt);
        stopWatch.split();
        long treeMapDuration = stopWatch.getSplitTime();

        return new PerfTestResult(cnt, hashMapDuration, treeMapDuration);
    }


    @Test
    public void HashMapTimeVsTreeMap() {
        int[] cnts = new int[] {100, 1000, 10000, 50000, 100000};

        List<PerfTestResult> results = new ArrayList<PerfTestResult>(cnts.length);
        for (int cnt: cnts) {
            results.add(doPerfTestBatchly(cnt));
        }

        System.out.println(String.format(PerfTestResult.template, "cnt", "hashMapDuration(ms)", "treeMapDuration(ms)"));
        for (PerfTestResult p: results) System.out.println(p);
    }


    @Test
    public void shrinkOrNot() {
        int initSize = 4;
        HashMap map = new HashMap(initSize);

        System.out.println("before put size: " + map.size());

        for (int i=0;i<=initSize; i++) {
            map.put(i, i);
        }
        System.out.println("after put size: " + map.size());

        map.clear();
        System.out.println("after clear size: " + map.size());
    }


    @Test
    public void nullAsKey() {
        Map map = new HashMap();

        map.put(null, new Date());

        assertTrue(! map.values().isEmpty());
    }



    @Test
    public void treeMap() {

        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>(comparator);

        for (int i=0;i<10;i++) {
            int randomValue = r.nextInt(100);
            sortedMap.put(randomValue, String.valueOf(randomValue));
        }

        System.out.println(sortedMap);
    }

}


/*
cnt	hashMapDuration(ms)	treeMapDuration(ms)
	100	0	0
	1000	2	11
	10000	21	29
	50000	13	32
	100000	13	46
 */
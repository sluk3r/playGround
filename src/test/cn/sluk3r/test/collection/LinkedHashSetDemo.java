package cn.sluk3r.test.collection;

import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertTrue;

/**
 * Created by baiing on 2014/6/24.
 */
public class LinkedHashSetDemo {
    @Test
    public void demo() {
        int size = 100;
        LinkedHashSet<Integer> lsh  = new LinkedHashSet();
        putIntoSet(size, lsh);

        int lastValue = -1;
        for (Integer i : lsh) {
            assertTrue(i > lastValue);
            lastValue = i;
        }
        System.out.println();
        HashSet<Integer> hashSet = new HashSet();
        putIntoSet(size, hashSet);

        int i = 0;
        lastValue = -1;
        for (Integer j: hashSet) {
            assertTrue(String.format("next value[%s] should be greater than last value[%s], and loop is: %s",j, lastValue, i), j > lastValue);
            lastValue = j;
            i++;
        }

    }

    private void putIntoSet(int size, Set s) {
        for(int i=0; i<size; i++) {
            s.add(i);
        }
    }
}

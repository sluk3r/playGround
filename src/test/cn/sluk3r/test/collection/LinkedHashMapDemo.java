package cn.sluk3r.test.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

/**
 * Created by baiing on 2014/6/23.
 */
public class LinkedHashMapDemo {
    @Test
    public void demo(){
        int size = 10;
        LinkedHashMap<String, Integer> m  = new LinkedHashMap();
        putIntoMap(size,m);

        int lastValue = -1;

        for (Map.Entry<String, Integer> me: m.entrySet()) {
            assertTrue(me.getValue() > lastValue);
            lastValue = me.getValue();
        }
        System.out.println();
        HashMap<String, Integer> hashMap = new HashMap();
        putIntoMap(size,hashMap);

        int i = 0;
        lastValue = -1;
        for (Map.Entry<String, Integer> me: hashMap.entrySet()) {
            assertTrue(String.format("next value[%s] should be greater than last value[%s], and loop is: %s",me.getValue(), lastValue, i+1), me.getValue() > lastValue);
            lastValue = me.getValue();
            i++;
        }
    }


    private void putIntoMap(int size, Map m) {
        for (int i=0;i<size;i++) {
            m.put("key"+i, i);
        }
    }
}

package cn.sluk3r.test.collection;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by baiing on 2014/6/23.
 */
public class LinkedHashMapDemo {
    @Test
    public void demo(){
        LinkedHashMap<String, Integer> m  = new LinkedHashMap();

        m.put("k1", 1);
        m.put("k2", 2);
        m.put("k3", 3);

        for (Map.Entry<String, Integer> me: m.entrySet()) {
            System.out.print("key: " + me.getKey());
            System.out.println("\t value: " + me.getValue());
        }
    }
}

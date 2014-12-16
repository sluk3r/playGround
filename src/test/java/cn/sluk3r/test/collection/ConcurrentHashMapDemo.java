package cn.sluk3r.test.collection;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by baiing on 2014/6/26.
 */
public class ConcurrentHashMapDemo {

    @Test
    public void demo() {
        Map m = new ConcurrentHashMap();

        m.put("key1", 1);
        m.put("key2", 2);
        m.put("key3", 3);

        System.out.println(m.get("key1"));
        System.out.println("size: " + m.size());
    }
}

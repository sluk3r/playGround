import cn.sluk3r.play.collection.HashMapSluk3rImpl;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by baiing on 2014/6/20.
 */
public class HashMapTest {
    int size = 10;
    Map h;

    @Before
    public void setUp() {
        h = new HashMapSluk3rImpl(size);
    }

    @After
    public void tearDown() {
        h = null;
    }

    @Test
    public void testGet() {
        h.put("key", 12);
        assertEquals("", 12, h.get("key"));

        h.put("key2", 12);
        assertEquals("", 12, h.get("key2"));
    }

    @Test
    public void testSize() {
        h.put("key", 12);
        assertEquals("", 1, h.size());

        h.put("key", 12);
        assertEquals("", 1, h.size());

        h.put("key2", 12);
        assertEquals("", 2, h.size());
    }

    @Test
    public void testContainsKey() {
        h.put("key2", 12);
        assertTrue(h.containsKey("key2"));
    }

    @Test
    public void testContainsValue() {
        h.put("key2", 12);
        assertTrue(h.containsValue(12));
    }

    @Test
    public void testRemove() {
        assertTrue(h.isEmpty());
        h.put("key", 111);
        h.remove("key");
        assertTrue(h.isEmpty());
    }

    @Test
    public void testKeySet() {
        assertTrue(h.isEmpty());

        h.put("key1", 1);
        h.put("key2", 2);
        h.put("key3", 3);

        Set keySet = h.keySet();
        assertEquals(3, keySet.size());
    }
}

package cn.sluk3r.test.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by baiing on 2014/12/2.
 */
public class ArraysDemo {

    @Test
    public void asLit() {
        List list = Arrays.asList(1,2, 3,4, 5);
        System.out.println("size: " + list.size());
        assertTrue(list.size() == 5);

        Iterator it = list.iterator();
        assertNotNull(it);


    }
}

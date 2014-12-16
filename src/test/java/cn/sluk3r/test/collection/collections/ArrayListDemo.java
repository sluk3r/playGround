package cn.sluk3r.test.collection.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baiing on 2014/11/24.
 */
public class ArrayListDemo {

    @Test
    public void overOfRange() {
        int size = 10;
        List l = new ArrayList(size);

        for (int i=0;i<=size;i++) {
            l.add(i);
        }

        System.out.println(l.size());
    }
}

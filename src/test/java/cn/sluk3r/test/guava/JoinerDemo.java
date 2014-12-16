package cn.sluk3r.test.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by baiing on 2014/9/5.
 */
public class JoinerDemo {

    @Test
    public void demo() {
        List<String> stringList = Lists.newArrayList("a","b", "c");

        String r = Joiner.on(",").join(stringList);
        assertEquals(r, "a,b,c");
    }
}

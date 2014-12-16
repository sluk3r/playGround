package cn.sluk3r.test.javase;

import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by baiing on 2014/9/28.
 */
public class Basic {

    @Test
    public void booleanOpr() {
        List list = null;

        try {
            if (list.size() > 0 & list != null ) {
                assertTrue(1==1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

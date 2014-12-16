package cn.sluk3r.test.collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;
import java.util.List;

/**
 * Created by baiing on 2014/12/8.
 */
public class ForEach {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = NullPointerException.class)
    public void forEachWithExpected() {
        runTest();
    }

    @Test
    public void forEachWithRule() {
        thrown.expect(NullPointerException.class);
        runTest();
    }

    private void runTest() {
        List<Date> dates = null;

        for(Date d: dates) {
            System.out.println(d);
        }
    }

}

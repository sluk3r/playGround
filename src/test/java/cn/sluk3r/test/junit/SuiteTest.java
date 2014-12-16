package cn.sluk3r.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by baiing on 2014/7/30.
 */


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ATest.class,BTest.class
})
public class SuiteTest {
}

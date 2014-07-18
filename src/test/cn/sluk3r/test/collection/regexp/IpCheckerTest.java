package cn.sluk3r.test.collection.regexp;

import cn.sluk3r.play.regexp.IpChecker;
import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by baiing on 2014/7/17.
 */
public class IpCheckerTest {
    IpChecker c = new IpChecker();

    @Test
    public void test() {
        String[] errorIps = new String[]{
                null,
                "198.122.211.112.",
                "1.2.3.",
                "256,256.256.1",
                "1.*.23.b"
        };

        for (String ip: errorIps) {
            assertFalse(String.format("for ip[%s], should be false", ip), c.validate(ip));
        }

        String[] correctIps = new String[] {
                "192.168.2.43",
                "1.2.3.4"
        };

        for(String ip: correctIps) {
            assertTrue(String.format("for ip[%s], should be true", ip), c.validate(ip));
        }
    }

    @Test
    public void testSingle() {
        String ip = "192.168.2.43";
        assertTrue(String.format("for ip[%s], should be true", ip), c.validate(ip));
    }

    @Test
    public void testReg() {
        assertTrue(verify("192", "192"));
        assertTrue("192 should be 1[0-9]{2}", verify("1[0-9]{2}", "192"));
        assertTrue("192 should be 1\\d{2}", verify("1\\d{2}", "192"));
        assertTrue("192 should be 1\\d{2}", verify("1\\d{2}", "192"));
    }

    private boolean verify(String reg, String target) {
        Pattern p = Pattern.compile(reg);
        return  p.matcher(target).matches();
    }
}

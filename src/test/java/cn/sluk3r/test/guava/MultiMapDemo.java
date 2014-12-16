package cn.sluk3r.test.guava;

import cn.sluk3r.play.logParse.InvokeInfo;
import cn.sluk3r.play.logParse.LogPaser;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by baiing on 2014/9/20.
 */
public class MultiMapDemo {
    private static final String methodInfo = "cn.baiing.rest.RoleRestService.getResourcesByRoleId";

    @Test
    public void demo() {
        Multimap<String, InvokeInfo> myMultimap = ArrayListMultimap.create();

        myMultimap.put(methodInfo, new InvokeInfo(methodInfo, 10, 1));
        myMultimap.put(methodInfo, new InvokeInfo(methodInfo, 1, 2));
        myMultimap.put(methodInfo, new InvokeInfo(methodInfo, 9, 3));
        myMultimap.put(methodInfo, new InvokeInfo(methodInfo, 7, 23));

        assertTrue(myMultimap.size() == 4);

        Collection<InvokeInfo>  values = myMultimap.get(methodInfo);
        int timeConsumedSum = 0;
        for (InvokeInfo invokeInfo: values) {
            timeConsumedSum += invokeInfo.getTimeConsumed();
        }

        assertTrue(timeConsumedSum == 27);
    }
}

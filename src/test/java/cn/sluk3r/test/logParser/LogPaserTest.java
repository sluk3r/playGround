package cn.sluk3r.test.logParser;

import cn.sluk3r.play.logParse.InvokeInfo;
import cn.sluk3r.play.logParse.InvokeInfoComparator;
import cn.sluk3r.play.logParse.LogPaser;
import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by baiing on 2014/9/20.
 */
public class LogPaserTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testReg() {
        String target = "2014-09-20-14:27:54,463 TRACE   - cn.baiing.interceptor.MonitorInterceptor.invokeUnderTrace(MonitorInterceptor.java:43)StopWatch 'cn.baiing.dao.db.ResourceDbDao.getResourcesByIds': running time (millis) = 0";
        System.out.println(LogPaser.parse2InvokeInfo(target).toJson());
    }

    @Test
    public void sort() throws IOException {
        List<InvokeInfo> invokeInfoList = Lists.asList(new InvokeInfo("fda", 12, 1), new InvokeInfo[]{new InvokeInfo("fda", 3, 9)});

        List<InvokeInfo> forSort = new ArrayList<InvokeInfo>(invokeInfoList);

        Collections.sort(forSort, InvokeInfoComparator.getInvokeInfoComparator(-1));

        System.out.println(objectMapper.writeValueAsString(forSort));
    }

}

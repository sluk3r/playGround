package cn.sluk3r.play.logParse;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by baiing on 2014/9/20.
 */
public class LogPaser {
    private static final Logger logger = Logger.getLogger(LogPaser.class);
    private static String START_FLAG = "...start...";
    private static String END_FLAG = "...end...";
    private static String invokePatten = "StopWatch '(.*)':.*= (\\d*)";
    private static Pattern p = Pattern.compile(invokePatten);
    private static final String saveBundleMethod = "cn.baiing.rest.KnowledgeRestService.saveKnowledgeBundle";


    public static void main(String[] args) throws IOException {
        String file = "F:\\kuaiPan\\docs\\baiing\\fromFanjunwei\\timeConsumingProfiling\\doc\\result\\profile3.txt";

        List<String> lines = Files.readLines(new File(file), Charsets.UTF_8);

        List<InvokeInfo> invokeInfos = new ArrayList<InvokeInfo>(20);

        InvokeInfo invokeInfo = new InvokeInfo();

        boolean invokedBetween = false;
        for (String ln : lines) {
            if (ln.contains(START_FLAG)) {
                invokedBetween = true;
                logger.info("new method invoked");
                String methodInfo = getMethodInfo(ln);

                invokeInfo.setMethod(methodInfo);
            } else if (ln.contains(END_FLAG)) {
                logger.info("method end");

                invokeInfos.add(invokeInfo);

                invokeInfo = new InvokeInfo();
                invokedBetween = false;
            } else if (invokedBetween) {
                InvokeInfo sub = parse2InvokeInfo(ln);
                if (sub == null ) continue;
                invokeInfo.addInvokeInfo(sub);
                invokeInfo.addTimeConsumed(sub.getTimeConsumed());
            }
        }
//        System.out.println("Result: " + new ObjectMapper().writeValueAsString(invokeInfos));

//        ananlysis(invokeInfos);
        extractSaveBundle(invokeInfos);
    }

    private static void extractSaveBundle(List<InvokeInfo> invokeInfos) {
        InvokeInfo saveBundle = null;
        for (InvokeInfo info: invokeInfos) {
            if (info.getMethod().contains(saveBundleMethod)) {
                saveBundle = info;
            }
        }

        List<InvokeInfo> sub = saveBundle.getSubInvoke();
        printStaticsInfo(sub);
    }


    private static void ananlysis(List<InvokeInfo> invokeInfos) {
        Multimap<String, InvokeInfo> myMultimap = convert2Multimap(invokeInfos);

        statMostTimeConsumed(myMultimap);
    }

    private static Multimap<String, InvokeInfo> convert2Multimap(List<InvokeInfo> invokeInfos) {
        Multimap<String, InvokeInfo> myMultimap = ArrayListMultimap.create();

        for (InvokeInfo info: invokeInfos) {
            myMultimap.put(info.getMethod(), info);

            for(InvokeInfo sub: info.getSubInvoke()) {
                myMultimap.put(info.getMethod(), info);
            }
        }
        return myMultimap;
    }

    public static void statMostTimeConsumed(Multimap<String, InvokeInfo> myMultimap) {
        List<InvokeInfo> result = new ArrayList<InvokeInfo>();
        for(Map.Entry<String, Collection<InvokeInfo>> me: myMultimap.asMap().entrySet()) {
            InvokeInfo invokeInfo = new InvokeInfo();

            invokeInfo.setMethod(me.getKey());
            invokeInfo.setSize(me.getValue().size());

            int total = 0;
            for(InvokeInfo info: me.getValue()) {
                total +=info.getTimeConsumed();
            }
            invokeInfo.setTimeConsumed(total);

            result.add(invokeInfo);
        }



    }

    private static void printStaticsInfo(List<InvokeInfo> result) {
        Collections.sort(result, InvokeInfoComparator.getInvokeInfoComparator(-1));
        for(InvokeInfo info: result) {
            System.out.println(String.format("%s\t%s\t%s", info.getMethod(), info.getTimeConsumed(), info.getSize()));
        }
    }


    //2014-09-20-14:27:54,463 TRACE   - cn.baiing.interceptor.MonitorInterceptor.invokeUnderTrace(MonitorInterceptor.java:43)StopWatch 'cn.baiing.dao.db.ResourceDbDao.getResourcesByIds': running time (millis) = 0
    public  static InvokeInfo parse2InvokeInfo(String subInvoke) {
        InvokeInfo invokeInfo = new InvokeInfo();
        Matcher m = p.matcher(subInvoke);
        if (m.find()) {
            invokeInfo.setMethod(m.group(1).trim());
            invokeInfo.setTimeConsumed(Integer.parseInt(m.group(2)));

            return  invokeInfo;
        }

        logger.info("unmactched run: " + subInvoke);
        return null;
    }

    static String getMethodInfo(String startLine) {
        String[] splitted = startLine.split(START_FLAG);

        return splitted[splitted.length - 1];
    }
}

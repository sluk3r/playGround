package cn.sluk3r.test.collection.concurrent.reorder;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by baiing on 2014/11/21.
 */
public class ReorderDemo {
    private  static  Logger logger = Logger.getLogger(ReorderDemo.class);
    private static int a = 0, b=0;
    private static int x=0, y=0;

    protected String run() throws InterruptedException {
        Thread t1 = new Thread() {
            public void run() {
                a= 1;
                x= b;
            }
        };


        Thread t2 = new Thread() {
            public void run() {
                b= 1;
                y= a;
            }
        };

        t1.start();t2.start();
        t1.join();t2.join();

        String result = String.format("(x:%s, y:%s)", x, y);

        a = 0;b=0;x=0;y=0;
        return result;
    }


    @Test
    public void runDemo() throws InterruptedException {
        ConcurrentHashMap<String, AtomicInteger> resultCollector = new ConcurrentHashMap();

        int loopNum = 100000;
        for (int i=0;i<loopNum;i++) {
            String result = run();

            resultCollector.putIfAbsent(result, new AtomicInteger(0));
            resultCollector.get(result).incrementAndGet();
        }


        for (Map.Entry<String, AtomicInteger> me: resultCollector.entrySet()) {
            int value = me.getValue().get();
            String line = String.format("%s\t%s\t%s%%", me.getKey(), value, (100* value/(float)loopNum));

            //logger.info("\n" + line);
            System.out.println(line);
        }
    }
}

/*
(x:0, y:0)	41
(x:0, y:1)	68778
(x:1, y:1)	126
(x:1, y:0)	31055


(x:0, y:0)	27
(x:0, y:1)	64327
(x:1, y:1)	124
(x:1, y:0)	35522


(x:0, y:0)	1	0.0010%
(x:0, y:1)	66347	66.347%
(x:1, y:1)	113	0.113%
(x:1, y:0)	33539	33.539%

(x:0, y:0)	2	0.0020%
(x:0, y:1)	64037	64.037%
(x:1, y:1)	101	0.101%
(x:1, y:0)	35860	35.86%
*/

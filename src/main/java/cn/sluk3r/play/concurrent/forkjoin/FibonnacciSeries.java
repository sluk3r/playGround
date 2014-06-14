package cn.sluk3r.play.concurrent.forkjoin;

/**
 * Created by baiing on 2014/6/14.
 */
import java.util.ArrayList;
import java.util.List;

public class FibonnacciSeries {
    public static void main(String[] arg) {
        int size = 25;
        List<Integer> fibinacciSeries = new ArrayList<Integer>();
        long start = System.currentTimeMillis();
        for (int index = 0; index < size; index++) {
            fibinacciSeries.add(FibonnacciGenerator.generate(index));
        }
        System.out.println("duration(ms): " + (System.currentTimeMillis() - start));
        dumpList(fibinacciSeries);
    }
    public static void dumpList(List list) {
        int index = 0;
        for (Object object : list) {
            System.out.printf("%d\t%d\n", index++, object);
        }
    }
}
class FibonnacciGenerator {
    public static Integer generate(Integer index) {
        if (index == 0) {
            return 0;
        }
        if (index < 2) {
            return 1;
        }
        Integer result = generate(index - 1) + generate(index - 2);
        return result;
    }
}

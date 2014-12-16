package cn.sluk3r.play.pattern.visitor.nice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by baiing on 2014/8/28.
 */
public class Main {

    public static void main(String[] args) {
        Collection c = new ArrayList();

        c.add("Hello world");
        c.add(1.234f);
        c.add(Arrays.asList(new String[]{"who", "are", "you"}));
    }
}

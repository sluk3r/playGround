package cn.sluk3r.play.pattern.visitor.raw;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by baiing on 2014/8/28.
 */
public class MessyPrintCollection {

    public void messyPrintCollection(Collection collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Collection)
                messyPrintCollection((Collection)o);
            else if (o instanceof String)
                System.out.println("'"+o.toString()+"'");
            else if (o instanceof Float)
                System.out.println(o.toString()+"f");
            else
                System.out.println(o.toString());
        }
    }
}

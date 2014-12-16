package cn.sluk3r.play.pattern.visitor.nice;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by baiing on 2014/8/28.
 */
public class PrintVisitor implements Visitor {
    public void visitCollection(Collection collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Visitable)
                ((Visitable) o).accept(this);
        }
    }

    public void visitString(String string) {
        System.out.println("'" + string + "'");
    }

    public void visitFloat(Float f) {
        System.out.println(f.toString() + "f");
    }
}

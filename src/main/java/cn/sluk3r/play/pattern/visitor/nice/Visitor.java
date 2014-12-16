package cn.sluk3r.play.pattern.visitor.nice;

import java.util.Collection;

/**
 * Created by baiing on 2014/8/28.
 */
public interface Visitor {
    public void visitCollection(Collection collection);
    public void visitString(String string);
    public void visitFloat(Float f);
}

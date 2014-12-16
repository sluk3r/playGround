package cn.sluk3r.play.pattern.visitor.nice;

/**
 * Created by baiing on 2014/8/28.
 */
public interface Visitable {
    public void accept(Visitor visitor);
}

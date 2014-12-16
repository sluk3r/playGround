package cn.sluk3r.play.pattern.visitor.nice;

/**
 * Created by baiing on 2014/8/28.
 */
public class VisitableString implements Visitable {
    private String value;
    public VisitableString(String string) {
        value = string;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitString(value);
    }
}

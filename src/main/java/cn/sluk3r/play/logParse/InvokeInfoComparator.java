package cn.sluk3r.play.logParse;

import java.util.Comparator;

/**
 * Created by baiing on 2014/9/20.
 */
public class InvokeInfoComparator implements Comparator<InvokeInfo> {
    int seqType;

    public InvokeInfoComparator(int seqType) {
        this.seqType = seqType;
    }

    @Override
    public int compare(InvokeInfo o1, InvokeInfo o2) {
        return  seqType * (o1.getTimeConsumed() - o2.getTimeConsumed());
    }

    public static InvokeInfoComparator getInvokeInfoComparator(int seqType) {
        return new InvokeInfoComparator(seqType);
    }
}
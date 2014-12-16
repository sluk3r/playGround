package cn.sluk3r.play.logParse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baiing on 2014/9/20.
 */
public   class InvokeInfo {
    String method;
    List<InvokeInfo> subInvoke;
    int timeConsumed = 0;
    int size;

    private void initList() {
        subInvoke = new ArrayList<InvokeInfo>();
    }

    public InvokeInfo(String method, int timeConsumed, int size) {
        this.method = method;
        this.timeConsumed = timeConsumed;
        this.size = size;
        initList();
    }

    public InvokeInfo() {
        initList();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addInvokeInfo(InvokeInfo invokeInfo) {
        subInvoke.add(invokeInfo);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<InvokeInfo> getSubInvoke() {
        return subInvoke;
    }

    public void setSubInvoke(List<InvokeInfo> subInvoke) {
        this.subInvoke = subInvoke;
    }

    public int getTimeConsumed() {
        return timeConsumed;
    }

    public void setTimeConsumed(int timeConsumed) {
        this.timeConsumed = timeConsumed;
    }

    public void addTimeConsumed(int timeConsumed) {
        this.timeConsumed += timeConsumed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvokeInfo)) return false;

        InvokeInfo that = (InvokeInfo) o;

        if (method != null ? !method.equals(that.method) : that.method != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        return result;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

}
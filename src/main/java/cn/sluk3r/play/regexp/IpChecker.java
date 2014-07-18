package cn.sluk3r.play.regexp;

import java.util.regex.Pattern;

/**
 * Created by baiing on 2014/7/17.
 */
public class IpChecker {
    String regexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    public boolean validate(String ip) {
        if (ip == null || ip.trim().length() < 7 || ip.trim().length() > 15) {
            return  false;
        }
        //                                                                1     2       3      3         3
        Pattern p = Pattern.compile(regexp);
        return  p.matcher(ip).matches();
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }
}

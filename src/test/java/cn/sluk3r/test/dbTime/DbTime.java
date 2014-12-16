package cn.sluk3r.test.dbTime;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by baiing on 2014/11/17.
 */
public class DbTime {
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void timeStamp() throws ParseException {
        long timeStamp = 1416194520;
        String now = "2014-11-17 11:22:00";

        Date dateFromLong = new Date(timeStamp);
        Date dateFromNow = f.parse(now);

        System.out.println(dateFromLong);
        System.out.println(dateFromNow);

        Timestamp timestamp = new Timestamp(timeStamp);
        System.out.println(timestamp);
    }
}

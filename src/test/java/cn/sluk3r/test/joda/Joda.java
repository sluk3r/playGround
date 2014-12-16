package cn.sluk3r.test.joda;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Date;

/**
 * Created by baiing on 2014/7/25.
 */
public class Joda {

    @Test
    public void addDay() {
        DateTime dateTime = new DateTime(2014,6, 13, 0, 0, 0);
        System.out.println(dateTime);

        dateTime.plusDays(1);

        DateTimeFormatter dateTimeFormatter =  DateTimeFormat.forPattern("yyyy-MM-dd"); //2014-06-13
        System.out.println(dateTime.toString(dateTimeFormatter));

    }

    @Test
    public void make() {
        //INSERT INTO a2_bi_search_logs   SELECT * FROM bi_search_logs l WHERE  	UNIX_TIMESTAMP(l.search_time) >= UNIX_TIMESTAMP('2014-06-11 00:00:00') 	AND UNIX_TIMESTAMP(l.search_time) <= UNIX_TIMESTAMP('2014-06-12 23:59:59');
        //ALTER TABLE a2_bi_search_logs  ADD PARTITION (PARTITION p5 VALUES LESS THAN (unix_timestamp('2014-06-13 00:00:00')) );

        String template = "ALTER TABLE a2_bi_search_logs  ADD PARTITION (PARTITION p%s VALUES LESS THAN (unix_timestamp('%s 00:00:00')) );";

        int pIndex = 6;
        int addDaies = 60;

        DateTime dateTime = new DateTime(2014,6, 13, 0, 0, 0);
        DateTimeFormatter dateTimeFormatter =  DateTimeFormat.forPattern("yyyy-MM-dd"); //2014-06-13
        for (int i=0;i<addDaies;i++) {
            dateTime = dateTime.plusDays(1);
            System.out.println(String.format(template, pIndex++,dateTime.toString(dateTimeFormatter)));
        }
    }


    @Test
    public void OneDay() {
        DateTime now = new DateTime();
        Date endDate = now.toDate();
        endDate.setHours(0);
        endDate.setMinutes(0);
        endDate.setSeconds(0);

        DateTime yesterday = now.plusDays(-1);
        Date startDate = yesterday.toDate();
        startDate.setHours(0);
        startDate.setMinutes(0);
        startDate.setSeconds(0);

        System.out.println(startDate);
        System.out.println(endDate);
    }
}

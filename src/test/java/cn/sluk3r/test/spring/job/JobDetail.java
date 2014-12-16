package cn.sluk3r.test.spring.job;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by baiing on 2014/7/26.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/job/jobDemo.xml"})
public class JobDetail {
    private static final Logger logger = Logger.getLogger(JobDetail.class);

    public void runJob() {
        logger.info("current time: " + new Date());
    }

    @Test
    public void jobTest() throws InterruptedException {
        TimeUnit.MINUTES.sleep(5);
    }
}

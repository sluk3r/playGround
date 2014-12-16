package cn.sluk3r.test.lang;

import org.apache.log4j.Logger;

/**
 * Created by baiing on 2014/12/16.
 */
public class Parent {
    String getCurrentClassNameWithReflection() {
        return this.getClass().getName();
    }


    protected  Logger getLogger() {
        return  Logger.getLogger(this.getClass());
    }

}

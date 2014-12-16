package cn.sluk3r.test.collection.collections;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by baiing on 2014/7/12.
 */
public class RevertLookupMap {

    @Test
    public void demo() {
        BidiMap option2ColumnName = new DualHashBidiMap();

        option2ColumnName.put("system", "osType");
        option2ColumnName.put("price", "price");
        option2ColumnName.put("brands", "brand");
        option2ColumnName.put("color", "color");
        option2ColumnName.put("nettype", "networkType");
        option2ColumnName.put("screen", "screenSize");

        assertEquals(option2ColumnName.getKey("screenSize"), "screen");
    }
}

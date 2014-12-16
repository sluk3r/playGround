package cn.sluk3r.test.collection.cvs;

import au.com.bytecode.opencsv.CSVWriter;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by baiing on 2014/7/12.
 */
public class CvsWriter {
    @Test
    public void cvs() throws IOException {
        String csv = "./output.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        String [] country = "India#China#United States".split("#");
        writer.writeNext(country);
        writer.close();
    }
}

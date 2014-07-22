package cn.sluk3r.play.weatherQuery;

import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OwmQuery {
    private static final Logger logger = Logger.getLogger(OwmQuery.class);
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //TODO 能不能批量查询？
    public static Map query(String city) throws IOException{
        String r = doQuery(city);

        String jsonTempPath = "$.main.temp";
        String jsonIconPath = "$.weather[0].icon";  //http://openweathermap.org/img/w/10d.png
        String jsonDtPath = "$.dt";
        String jsonWeaPath = "$.weather[0].main";

        Long dt = Long.valueOf(JsonPath.read(r, jsonDtPath) + "");
        dt = dt.longValue() * 1000;

        Map result = new HashMap();

        result.put("date", format.format(new Date(dt.longValue())));

        Double dh = JsonPath.read(r, jsonTempPath);

        result.put("temp", dh2dc(dh));
        result.put("icon", String.format("http://openweathermap.org/img/w/%s.png", JsonPath.read(r, jsonIconPath)));
        result.put("wea", JsonPath.read(r, jsonWeaPath));

        return result;
    }


    private static String doQuery (String subUrl) throws IOException {
        String responseBody = null;
        String baseOwmUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
        String owmAPPID = null;
        String APPID_HEADER = "x-api-key";

        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet(baseOwmUrl + subUrl);
        if (owmAPPID != null) {
            httpget.addHeader (APPID_HEADER, owmAPPID);
        }

        HttpResponse response = httpClient.execute (httpget);
        InputStream contentStream = null;
        try {
            StatusLine statusLine = response.getStatusLine ();
            if (statusLine == null) {
                throw new IOException (
                        String.format ("Unable to get a response from OWM server"));
            }
            int statusCode = statusLine.getStatusCode ();
            if (statusCode < 200 && statusCode >= 300) {
                throw new IOException (
                        String.format ("OWM server responded with status code %d: %s", statusCode, statusLine));
            }
            /* Read the response content */
            HttpEntity responseEntity = response.getEntity ();
            contentStream = responseEntity.getContent ();
            Reader isReader = new InputStreamReader(contentStream);
            int contentSize = (int) responseEntity.getContentLength ();
            if (contentSize < 0)
                contentSize = 8*1024;
            StringWriter strWriter = new StringWriter (contentSize);
            char[] buffer = new char[8*1024];
            int n = 0;
            while ((n = isReader.read(buffer)) != -1) {
                strWriter.write(buffer, 0, n);
            }
            responseBody = strWriter.toString ();
            contentStream.close ();
        } catch (IOException e) {
            throw e;
        } catch (RuntimeException re) {
            httpget.abort ();
            throw re;
        } finally {
            if (contentStream != null)
                contentStream.close ();
        }

        return responseBody;
    }

    private static Double dh2dc(Double dh) {
        return dh - 273.15;
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> r = query("北京");
        System.out.println("size: " + r.size());
        for(Map.Entry me: r.entrySet()) {
            System.out.println("key: " + me.getKey() + ", value: " + me.getValue());
        }
    }
}
package cn.sluk3r.test.httpFluent;

import org.apache.http.*;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by baiing on 2014/11/4.
 */
public class HttpFluentDemo {
    String url = "http://192.168.2.74:4600/BaiingBusinessEngine/rest/knowledge/search";
    String requestJson = "{\"keyword\": \"手机\",\"pageSize\": 10,\"scene\": \"mobilephone\",\"startPos\": 0}";

    Request request = null;


    @Before
    public void setUp() {
        request = Request.Post(url)
                .useExpectContinue()
                .version(HttpVersion.HTTP_1_1);

    }


//    static  ResponseHandler handler = new ResponseHandler<String>() {
//
//        public String handleResponse(final HttpResponse response) throws IOException {
//            StatusLine statusLine = response.getStatusLine();
//            HttpEntity entity = response.getEntity();
//            if (statusLine.getStatusCode() >= 300) {
//                throw new HttpResponseException(
//                        statusLine.getStatusCode(),
//                        statusLine.getReasonPhrase());
//            }
//            if (entity == null) {
//                throw new ClientProtocolException("Response contains no content");
//            }
//            ContentType contentType = ContentType.getOrDefault(entity);
//            if (!contentType.equals(ContentType.APPLICATION_XML)) {
//                throw new ClientProtocolException("Unexpected content type:" + contentType);
//            }
//            Charset charset = contentType.getCharset();
//            if (charset == null) {
//                charset = Consts.UTF_8;
//            }
//
//            entity.
//
//            String content = entity.getContent();
//            return docBuilder.parse(entity.getContent(), charset.name());
//        }
//    };

//    public static void main(String[] args) throws IOException {
//
//        String content = (String)
//                .bodyString(requestJson, ContentType.APPLICATION_JSON)
////                .execute().returnContent().asString();
//                .execute().handleResponse(handler);
//
//        System.out.println(content);
//    }


    @Test
    public void messyCode() throws IOException {
        System.out.println(request.bodyString(requestJson, ContentType.APPLICATION_JSON).execute().returnContent().asString());
    }

    @Test
    public void tryEncode() throws IOException {
        String content = new String(request.bodyString(requestJson, ContentType.APPLICATION_JSON).execute().returnContent().asBytes(), Charset.forName("UTF-8"));
        System.out.println(content);
    }
}

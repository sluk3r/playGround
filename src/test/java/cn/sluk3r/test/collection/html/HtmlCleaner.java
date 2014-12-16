package cn.sluk3r.test.collection.html;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.Test;

import java.util.Map;

import static org.jgroups.util.Util.assertTrue;

public class HtmlCleaner {

    @Test
    public void clean() {
        String htmlContent = "\n" +
                "                <td class=\"examples\">Search by group, artifact or description.<br/>\n" +
                "                    E.g: <a href=\"/search.html?query=log4j\">log4j</a>,\n" +
                "                    <a href=\"/search.html?query=spring\">spring</a>,\n" +
                "                    <a href=\"/search.html?query=hibernate\">hibernate</a>\n" +
                "                    <br/>\n" +
                "                </td>";
        String replaced = Jsoup.parse(htmlContent).text();
        assertTrue(!replaced.contains("<td>"));
        System.out.println(replaced);

        replaced = Jsoup.clean(htmlContent, Whitelist.none());
        assertTrue(!replaced.contains("<td>"));
        System.out.println(replaced);
    }

    @Test
    public void cleanSpace(){
        String htmlContent = "&nbsp;&nbsp;&nbsp; abc";

        String replaced = Jsoup.clean(htmlContent, Whitelist.none());
        assertTrue(replaced.contains("&nbsp;"));//&nbsp;这个还去不了。
        System.out.println(replaced);

        replaced = htmlContent.replaceAll("&nbsp;", "");
        assertTrue(!replaced.contains("&nbsp;"));
        System.out.println(replaced);
    }
}

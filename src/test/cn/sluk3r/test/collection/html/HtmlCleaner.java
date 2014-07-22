package cn.sluk3r.test.collection.html;

import org.jsoup.Jsoup;
import org.junit.Test;

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
    }
}

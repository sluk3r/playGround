package cn.sluk3r.test.collection.json;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.jgroups.util.Util.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * Created by baiing on 2014/7/23.
 */
public class JsonDemo {

    ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }


    @Test
    public void validateJsonFormat() {
        setFeature();
        String contentWithQuoteOnKey = "{\n" +
                "\t\"keyword\":\"111\",\n" +
                "\t\"spellCorrection\":\"http://localhost/Consumer\"\n" +
                "}";
        assertTrue(validateJson(contentWithQuoteOnKey));

        String contentWithoutQuoteOnKey = "{\n" +
                "\tkeyword:\"111\",\n" +
                "\tspellCorrection:\"http://localhost/Consumer\"\n" +
                "}";
        assertFalse(validateJson(contentWithoutQuoteOnKey)); //key的外面不能没有引号。
    }

    @Test
    public void testAsciss() {
        String c1 = "Double quote: &#039;";
        String c2 = "Double quote: '";

        assertNotEquals(c1, c2);

        System.out.println("c1: " + c1);
    }

    private void setFeature() {
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    @Test
    public void validateJsonWithFeature() {
        setFeature();

        //Exception: Unexpected character ('k' (code 107)): was expecting double-quote to start field name
        //Exception: Unexpected character (''' (code 39)): was expecting double-quote to start field name
        String contentSpecial = "{'name':'翼周边“感动有你，百万酬宾真情大回馈”活动','kid':101561,'picurl':'0','content':'<p> <p>一、活动主题</p> <p>感动有你，百万酬宾真情大回馈</p> <p>二、活动时间</p> <p>2013年11月28日早上10点-2014年3月31日24点</p> <p>三、活动内容</p> <p>1、回馈1：活动期间（11.18-12.31）参与本活动，凡成功邀请5次或成功购买1次及以上的新老用户即可领取价值30元的300兆流量卡一张，其中针对成功邀请的用户每天送出500张，成功购买的总共送出3000张，先到先得，送完为止。同时满足条件的用户只能领取一次流量卡。</p> <p>2、回馈2：2013年12月1日-12月14日期间登录2次及以上新老用户，将有机会获得抽取20000张300兆流量卡的机会；2013年12月15日-2014年3月31日升级到新版翼周边的注册用户将有机会获得抽取30000张300兆流量卡的机会。与回馈1同时满足条件的用户只能领取一次流量卡；</p> <p>3、回馈3：2013年11月28-12月31日期间，凡积分总分达到3000分翼周边安卓用户，可直接领取300兆流量卡取5500张。先到先得，领完为止。满足回馈1、回馈2条件的用户也可参加积分领取流量卡活动；</p> <p>4、回馈4：2014年1月1日-3月31日期间参加翼周边特别策划活动“翼周边与你同行，春节回家路”及相关微信微博活动，即有机会获得25000张300兆流量卡。满足回馈1、2、3条件的用户同时可参加回馈4活动。</p> <p>四、活动说明</p> <p>1、同时满足回馈1、回馈2条件的用户活动期间只能领取一次300兆流量卡；满足回馈1、回馈2条件的用户同时也可参加回馈3积分兑换流量卡活动；满足回馈1、2、3条件的用户同时可参加回馈4活动。</p> <p>2、参加“真情大回馈活动可参加翼周边同期其他活，可重复获奖。</p> <p>3、成功邀请指使用翼周边（安卓版1.7、1.8、1.9、1.9.1等4个版本中）的分享功能，被分享用户下载登录翼周边后同时填写邀请人的邀请码；当该邀请码被不同用户填写5次及以上的每天前500名邀请人即获取流量卡。</p> <p>4、成功购买指在翼周边及翼周边易信公众平台有购买功能的频道（如团购，电影等）在线购买并支付成功，且不发生退订退款等行为的用户。</p> <p>5、本次流量卡充值仅限电信手机用户，每个回馈活动结束后5个工作日内用户可以通过翼周边易信公众平台（易信号：yizhoubian2012）查询中奖结果。中奖结果同时将通过翼周边官网（LBS.189.CN）、翼周边安卓客户端公布。</p> <p>6、每个回馈活动结束后20个工作日内将直接将流量充值到用户电信手机，若规定时间内用户因各种原因未收到充值，请登录翼周边微信公众平台（微信号：yizhoubian）咨询和领取，或拨打客服电话（4008218114）咨询。</p> <p>7、流量即充即用，自充入之日起30天内有效。每月仅限充入一张促销卡，在30天有效期内，次月充入可以叠加流量（过期清零），并重新计算有效期，自最后充入之日起30天内有效 ；</p> <p>8、活动具体规则以官网（HTTP://LBS.189.CN）公布为准;详情及中奖结果请登录翼周边官网、翼周边安卓客户端或翼周边官方微博（@翼周边）;</p> <p>9、以上活动最终解释权归号百信息服务有限公司所有。</p> <p>五、FAQ</p> <p>1、参加真情大回馈活动需要哪些资格？非电信用户可以参加吗？</p> <p>答：翼周边Android、iOS版新老用户、翼周边易信公平平台粉丝、微信公众平台粉丝符合条件都可以参加。</p> <p>本次活动流量卡充值仅限电信手机用户。</p> <p>2、真情大回馈活动具体内容是什么？</p> <p>答：真情回馈活动有4中形式，具体如下：</p> <p>1）回馈1：活动期间（11.18-12.31）参与本活动，凡成功邀请5次或成功购买1次及以上的新老用户即可领取价值30元的300兆流量卡一张，其中针对成功邀请的用户每天送出500张，成功购买的总共送出3000张，先到先得，送完为止。同时满足条件的用户只能领取一次流量卡；</p> <p>2）回馈2：2013年12月1日-12月14日期间登录2次及以上新老用户，将有机会获得抽取20000张300兆流量卡的机会；2013年12月15日-2014年1月31日升级到新版翼周边的注册用户将有机会获得抽取30000张300兆流量卡的机会。与回馈1同时满足条件的用户只能领取一次流量卡；</p> <p>3）回馈3：2013年11月28-12月31日期间，凡积分总分达到3000分翼周边安卓用户，可直接领取300兆流量卡取5500张。先到先得，领完为止。满足回馈1、回馈2条件的用户也可参加积分领取流量卡活动；</p> <p>4）回馈4： 2014年1月1日-1月31日期间参加翼周边特别策划活动“翼周边与你同行，春节回家路”及相关微信微博活动，即有机会获得25000张300兆流量卡。满足回馈1、2、3条件的用户同时可参加回馈4活动。</p> <p>3、每种回馈活动都可以重复参加吗？</p> <p>答: 同时满足回馈1、回馈2条件的用户活动期间只能领取一次300兆流量卡；满足回馈1、回馈2条件的用户同时也可参加回馈3积分兑换流量卡活动；满足回馈1、2、3条件的用户同时可参加回馈4活动。</p> <p>4、已参加真情大回馈活动的是否可同时参加翼周边其他活动？</p> <p>答：可以。</p> <p>5、Android和IOS客户端都可以参加真情大回馈活动吗？</p> <p>答：可以。</p> <p>6、什么是升级？可以通过哪几种方式升级？ </p> <p>答：升级就是由以前较低版本更新到最新Android版本数据包。目前有三种方式可以升级：可以通过在官网（LBS.189.CN/yzb.apk </p> <p>）下载升级包方式升级；在各大电子市场下载更新包方式升级。</p> <p>7、如何判别已经升级成功？</p> <p>答：进入软件点击菜单-更多-版本检查，即可查看是否已成功升级最新版本。</p> <p>8、成功邀请的具体要求？</p> <p>答：成功邀请指使用翼周边（安卓版1.7、1.8、1.9、1.9.1等4个版本中）的分享功能，被分享用户下载登录翼周边后同时填写邀请人的邀请码；当该邀请码被不同用户填写5次及以上的每天前400名邀请人即获取流量卡。</p> <p>9、成功购买的具体有哪些要求？</p> <p>答：成功购买指在翼周边及翼周边易信公众平台有购买功能的频道（如团购，电影等）在线购买并支付成功，且不发生退订退款等行为的用户。</p> <p>10、如何知道自己是否中奖？</p> <p>答：本次流量卡充值仅限电信手机用户，每个回馈活动结束后5个工作日内用户可以通过翼周边易信公众平台（易信号：yizhoubian2012）查询中奖结果。中奖结果同时将通过翼周边官网（LBS.189.CN）、翼周边安卓客户端公布。</p> <p>11、如何领取流量卡？</p> <p>答：每个回馈活动结束后20个工作日内，系统默认将自动将流量充值到用户电信手机，若规定时间内用户因各种原因未收到充值，请登录翼周边微信公众平台（微信号：yizhoubian）咨询和领取。如本月已获电信流量卡用户可及时联系客服更改流量卡发放时间；如因用户原因无法及时发放，视为自动放弃或无效。</p> <p>12、流量卡有使用有效期吗？</p> <p>答：流量即充即用，自充入之日起30天内有效。每月仅限充入一张促销卡，在30天有效期内，次月充入可以叠加流量（过期清零），并重新计算有效期，自最后充入之日起30天内有效。</p> <p>13、我的个人信息是否会被泄露？</p> <p>答：不会，您的个人信息仅作为奖品发放使用，而且我们将做严格保密处理，不会有任何被泄露可能。</p> <p>14、翼周边的官方微博、易信公众平台、微信公众平台是什么？</p> <p>答：</p> <p>新浪微博搜索——翼周边，微博http://e.weibo.com/yizhoubian。</p> <p>易信搜索——翼周边（易信号：yizhoubian2012）。</p> <p>微信公众平台—翼周边(微信好：yizhoubian)。</p> <p>15、翼周边活动的客服热线是什么和客服咨询时间？</p> <p>答：4008218114，周一至周五（法定工作时间除外）。</p>','sdate':'','edate':'','rq':0}";
        assertTrue(validateJson(contentSpecial));

        String contentHTMLCleaned = Jsoup.clean(contentSpecial, Whitelist.none());
        //Exception: org.codehaus.jackson.JsonParseException: Unexpected character (''' (code 39)): was expecting double-quote to start field name
        assertTrue(validateJson(contentHTMLCleaned));

        //难道是从文件里读出来才行？
        String replaced = contentHTMLCleaned.replaceAll("'天翼客服'", "\"天翼客服\"");
        //Exception: org.codehaus.jackson.JsonParseException: Unexpected character (''' (code 39)): was expecting double-quote to start field name
        assertTrue(validateJson(replaced));
    }


//    @Test
//    public void parseOneTime() throws IOException {
//        String[] jsonRes = new String[]{youhuihuodong, taocan};
//
//        String ctnString = null;
//
//        for (String s: jsonRes) {
//            String content = getContent(s);
//
//            try {
//                Map result = mapper.readValue(content, Map.class);
//                List<Map> searchList = (List)result.get("searchList");
//
//                Map appMap = searchList.get(0);
//                List<Map> entityList = (List<Map>) appMap.get("list");
//
//                for(Map m : entityList) {
//                    String cnt = (String) m.get("content");
//
//                    System.out.println("before replace: " + cnt);
//                    cnt = cnt.replaceAll("&quot;", "'");
//                    System.out.println("after replace: " + cnt);
//
//                    ctnString = cnt;
////                    cnt = cnt.replaceAll("/", "%2F");
////                    System.out.println("cnt after replace slash: " + cnt);
//                    cnt = Jsoup.clean(cnt, Whitelist.none());
//                    System.out.println("cnt HTML clean: " + cnt);
//
//                    cnt = cnt.replaceAll("'天翼客服'", "\"天翼客服\"");
//                    System.out.println("cnt 天翼客服: " + cnt);
//
////                    cnt = URLEncoder.encode(cnt, "utf-8");
////                    System.out.println("cnt URLEncoder: " + cnt);
//
//
//                    Map cntMap = mapper.readValue(cnt.toString(), Map.class);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
////                System.out.println("for GSON: " + ctnString);
////                Map m = gson.fromJson(ctnString, Map.class);
////                System.out.println("size: " + m.size());
//                fail();
//            }
//        }
//    }

    private boolean validateJson(String json) {
        return validateJson(json, false);
    }

    private boolean validateJson(String json, boolean showException) {
        try {
            Map map = mapper.readValue(json, Map.class);
            assertNotNull(map);
            assertFalse(map.isEmpty());
            return true;
        } catch (IOException e) {
            if (showException) e.printStackTrace();
            return false;
        }
    }

    //FAIL_ON_UNKNOWN_PROPERTIES
    @Test
    public void failOnUnknownPropertiesDemo() {
        String personDataInJson = "{\"name\":\"baiiing\", \"age\":10}";

        try {
            mapper.readValue(personDataInJson, Person.class);
            fail("age在Person里不存在，应该有异常：UnrecognizedPropertyException: Unrecognized field");
        } catch (IOException e) {
        }

        try {
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.readValue(personDataInJson, Person.class);
        } catch (IOException e) {
            fail("已经指明FAIL_ON_UNKNOWN_PROPERTIES， 不会再报异常'UnrecognizedPropertyException'");
        }
    }

    static class Person {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

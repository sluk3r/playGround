package cn.sluk3r.test.guava.toJson;

//import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.base.Supplier;
import com.google.common.collect.*;
import com.google.gson.*;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.lang.reflect.Type;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by baiing on 2014/12/15.
 */
public class ToJsonWithGsonDemo {
    private static final Logger logger = Logger.getLogger(ToJsonWithGsonDemo.class);

    @Test
    public void demo() {
        Multimap<String, Long> values = createMap();

//        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Gson gson = getGson();

        System.out.println("gson: " + gson.toJson(values));
//        System.out.println("gson: " + gson.toJson(values.asMap()));
    }

    private Multimap<String, Long> createMap() {
        Multimap<String, Long> v = HashMultimap.create();

        v.put("a", 1L);
        v.put("a", 2L);
        v.put("a", 3L);

        return v;
    }


    static String jsonString = "{\n" +
            "   \"123455\":[\n" +
            "      {\n" +
            "         \"key\":\"Java Exercises\",\n" +
            "         \"url\":\"www.leveluplunch.com/java/exercises/\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"key\":\"Java Examples\",\n" +
            "         \"url\":\"www.leveluplunch.com/java/examples/\"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"999999\":[\n" +
            "      {\n" +
            "         \"key\":\"Java Tutorials\",\n" +
            "         \"url\":\"www.leveluplunch.com/java/tutorials/\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"key\":\"Java Examples\",\n" +
            "         \"url\":\"www.leveluplunch.com/java/examples/\"\n" +
            "      }\n" +
            "   ]\n" +
            "}";




    @Test
    public void convert() throws JsonParseException, JsonMappingException,
            JsonProcessingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule());

        Multimap<String, NavItem> navs = objectMapper.readValue(
                objectMapper.treeAsTokens(objectMapper.readTree(jsonString)),
                objectMapper.getTypeFactory().constructMapLikeType(
                        Multimap.class, String.class, NavItem.class));

        logger.info(navs);

        assertThat(navs.keys(), hasItems("123455", "999999"));
    }


    static String valueFromOnline = "[{\"knowledgeId\":114482,\"state\":33,\"isDeleted\":\"ALIVE\",\"knowledgesCombinationId\":131219,\"mainKnowledgeId\":114482,\"lastVersionedId\":128952,\"priority\":\"NORMAL\",\"creatorId\":3,\"lastOperatorId\":3,\"lastOperation\":31,\"creationTime\":\"Nov 15, 2014 11:45:41 AM\",\"lastUpdatedTime\":\"Nov 15, 2014 11:45:41 AM\",\"isPublished\":false,\"versionNum\":19,\"updateOperatorId\":3,\"knowledgeVersionedId\":131220,\"name\":\"（集团）翼支付添益宝\",\"locationId\":31118,\"locationLevel\":4,\"visibilityType\":\"PUBLISHATTR\",\"templateId\":121,\"businessTagIds\":[1185],\"dates\":{\"8864984870497531780\":\"Dec 31, 2035 12:00:00 AM\",\"4716279999522043193\":\"Mar 29, 2014 12:00:00 AM\"},\"datesWithExpType\":{},\"measuresWithExpType\":{},\"numericWithExpType\":{},\"richTextesWithExpType\":{},\"textesWithExpType\":{},\"multiSelectionsWithExpType\":{},\"singleSelectionsWithExpType\":{},\"exceptionUnits\":{}}, {\"knowledgeId\":131218,\"state\":3,\"isDeleted\":\"ALIVE\",\"knowledgesCombinationId\":131217,\"mainKnowledgeId\":131218,\"priority\":\"NORMAL\",\"creatorId\":3,\"lastOperatorId\":3,\"lastOperation\":2,\"creationTime\":\"Nov 15, 2014 11:12:50 AM\",\"lastUpdatedTime\":\"Nov 15, 2014 11:12:50 AM\",\"isPublished\":false,\"knowledgeVersionedId\":131218,\"name\":\"（集团）“天翼平台与易信服务器更新密码同步逻辑”的通知\",\"locationId\":31118,\"locationLevel\":4,\"visibilityType\":\"PUBLISHATTR\",\"templateId\":103,\"businessTagIds\":[1518],\"datesWithExpType\":{},\"measuresWithExpType\":{},\"numericWithExpType\":{},\"richTextesWithExpType\":{},\"textesWithExpType\":{},\"multiSelectionsWithExpType\":{},\"singleSelectionsWithExpType\":{},\"exceptionUnits\":{}}, {\"knowledgeId\":131216,\"state\":3,\"isDeleted\":\"ALIVE\",\"knowledgesCombinationId\":131215,\"mainKnowledgeId\":131216,\"priority\":\"NORMAL\",\"creatorId\":3,\"lastOperatorId\":3,\"lastOperation\":2,\"creationTime\":\"Nov 15, 2014 11:09:57 AM\",\"lastUpdatedTime\":\"Nov 15, 2014 11:09:57 AM\",\"isPublished\":false,\"knowledgeVersionedId\":131216,\"name\":\"测试\",\"locationId\":31118,\"locationLevel\":4,\"visibilityType\":\"PUBLISHATTR\",\"templateId\":103,\"businessTagIds\":[1529],\"datesWithExpType\":{},\"measuresWithExpType\":{},\"numericWithExpType\":{},\"richTextesWithExpType\":{},\"textesWithExpType\":{},\"multiSelectionsWithExpType\":{},\"singleSelectionsWithExpType\":{},\"exceptionUnits\":{}}, {\"knowledgeId\":131214,\"state\":3,\"isDeleted\":\"ALIVE\",\"knowledgesCombinationId\":131213,\"mainKnowledgeId\":131214,\"sourceTaskId\":6499,\"priority\":\"NORMAL\",\"creatorId\":3,\"lastOperatorId\":3,\"lastOperation\":2,\"creationTime\":\"Nov 15, 2014 10:29:20 AM\",\"lastUpdatedTime\":\"Nov 15, 2014 10:29:20 AM\",\"isPublished\":false,\"knowledgeVersionedId\":131214,\"name\":\"（集团）“天翼平台和易信服务器更新密码同步逻辑”的通知\",\"locationId\":31118,\"locationLevel\":4,\"visibilityType\":\"PUBLISHATTR\",\"templateId\":103,\"businessTagIds\":[1518],\"datesWithExpType\":{},\"measuresWithExpType\":{},\"numericWithExpType\":{},\"richTextesWithExpType\":{},\"textesWithExpType\":{},\"multiSelectionsWithExpType\":{},\"singleSelectionsWithExpType\":{},\"exceptionUnits\":{}}, {\"knowledgeId\":131212,\"state\":3,\"isDeleted\":\"ALIVE\",\"knowledgesCombinationId\":131211,\"mainKnowledgeId\":131212,\"priority\":\"NORMAL\",\"creatorId\":3,\"lastOperatorId\":3,\"lastOperation\":2,\"creationTime\":\"Nov 15, 2014 9:15:58 AM\",\"lastUpdatedTime\":\"Nov 15, 2014 9:15:58 AM\",\"isPublished\":false,\"knowledgeVersionedId\":131212,\"name\":\"（自治州）18574333995投诉备案\",\"locationId\":31365,\"locationLevel\":4,\"visibilityType\":\"PUBLISHATTR\",\"templateId\":113,\"businessTagIds\":[1529],\"datesWithExpType\":{},\"measuresWithExpType\":{},\"numericWithExpType\":{},\"richTextesWithExpType\":{},\"textesWithExpType\":{},\"multiSelectionsWithExpType\":{},\"singleSelectionsWithExpType\":{},\"exceptionUnits\":{}}]";
    private Gson getGson() {
            return  new GsonBuilder()
                    .registerTypeAdapter(Multimap.class, new com.google.gson.JsonSerializer<Multimap>() {
                        public JsonElement serialize(Multimap multimap, Type type, JsonSerializationContext jsonSerializationContext) {
                            return jsonSerializationContext.serialize(multimap.asMap());
                        }
                    })
                    .registerTypeAdapter(Multimap.class, new JsonDeserializer<Multimap>() {
                        public Multimap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                            final SetMultimap<String,String> map = Multimaps.newSetMultimap(new HashMap<String, Collection<String>>(), new Supplier<Set<String>>() {
                                public Set<String> get() {
                                    return Sets.newHashSet();
                                }
                            });
                            for (Map.Entry<String, JsonElement> entry : ((JsonObject) jsonElement).entrySet()) {
                                for (JsonElement element : (JsonArray) entry.getValue()) {
                                    map.get(entry.getKey()).add(element.getAsString());
                                }
                            }
                            return map;
                        }
                    })
                    .setPrettyPrinting()
                    .create();

    }
}

class NavItem {
    private String key;
    private String url;

    public NavItem() {
    }

    public NavItem(String key, String url) {
        this.key = key;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NavItem{" +
                "key='" + key + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

package controller.generalDataController;
import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

/**
 * Не розібрався точно для чого - але це серіалізація,
 * як я розумію - шаблон того, яким чином прога спілкується і отримує данні з API (в данному випадку - JSON)
 * інізіалізується один єдиний раз - тому статік.
 * Викликається в класі YouTubeAPI
 */
    class UnirestSerialization {
        static {
            Unirest.setObjectMapper(new ObjectMapper() {
                public <T> T readValue(String value, Class<T> valueType) {
                    return JSON.parseObject(value, valueType);
                }
                public String writeValue(Object value) {
                    return JSON.toJSONString(value);
                }
            });
        }
    }

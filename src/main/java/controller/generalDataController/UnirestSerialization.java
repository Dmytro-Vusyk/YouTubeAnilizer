package controller.generalDataController;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

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

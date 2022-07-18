package com.share.auth.center.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.gillion.ec.core.utils.Long2StringSerializer;

/**
 * @author : zhengry
 * Date: 2014/11/14
 * Time: 9:56
 */
public class JsonMapperHolder {
    private JsonMapperHolder(){}

    /**
     * jackson ObjectMapper实例
     */
    public static final ObjectMapper OBJECT_MAPPER;

    public static final JsonMapper JSON_MAPPER;

    static {
        JSON_MAPPER = JsonMapper.nonEmptyMapper();
        OBJECT_MAPPER = JSON_MAPPER.getMapper();
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        OBJECT_MAPPER.setDateFormat(new ISO8601DateFormat());
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, new Long2StringSerializer());
        OBJECT_MAPPER.registerModule(simpleModule);
    }

    public static final ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    public static final <T> T convert(Object value, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(value, clazz);
    }

    public static final JsonNode convert(Object value) {
        return convert(value, JsonNode.class);
    }
}

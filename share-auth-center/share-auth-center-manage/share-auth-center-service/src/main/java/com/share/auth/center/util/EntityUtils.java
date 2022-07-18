package com.share.auth.center.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.gillion.exception.BusinessRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 实体工具类，用于将Map转换为对应的JavaBean，或将对应的Java对象转换为JSON格式的字符串
 *
 * @author 翁美石 wengms
 * @version 1.00 2015年5月12日,2015   下午5:43:07
 */
@Slf4j
public class EntityUtils {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String ERROR_MSG = "无法读取json内容";

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityUtils.class);

    static {
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setDateFormat(new ISO8601DateFormat());
    }


    private EntityUtils() {
    }

    /**
     * 将Map转换为JavaBean
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T map2Bean(final Map<String, Object> map, final Class<T> clazz) {
        try {
            final String json = OBJECT_MAPPER.writeValueAsString(map);
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (final Exception e) {
            LOGGER.error("JSON 值转化失败，请检查。", e);
        }
        return null;
    }


    /**
     * 将对象转为javabean
     * @param o
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T object2Bean(Object o, Class<T> clazz) {
        try {
            String jsonStr = OBJECT_MAPPER.writeValueAsString(o);
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (IOException e) {
            log.error("实体转化失败", e);
            throw new BusinessRuntimeException("实体转化失败", e);
        }
    }

    /**
     * 将对象转化为格式化后的JSON字符串输出
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        if (object != null) {
            try {
                return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            } catch (JsonProcessingException e) {
                log.error("JSON字符串转换出错", e);
                throw new BusinessRuntimeException(e);
            }
        } else {
            return "";
        }
    }

    /**
     * 将对象转化为格式化后的JSON字符串输出
     *
     * @param object
     * @return
     */
    public static byte[] toJsonByte(Object object) {
        if (object != null) {
            try {
                return OBJECT_MAPPER.writeValueAsBytes(object);
            } catch (JsonProcessingException e) {
                log.error("JSON字符串转换出错", e);
                throw new BusinessRuntimeException(e);
            }
        } else {
            return new byte[0];
        }
    }

    /**
     * 将List中的数据转化为对应的JavaBean集合
     *
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> list2Beans(final List<Object> list, final Class<?> clazz) {
        final List<T> rtnList = new ArrayList<>();
        try {
            for (final Object object : list) {
                final String json = OBJECT_MAPPER.writeValueAsString(object);
                final Object result = OBJECT_MAPPER.readValue(json, clazz);
                rtnList.add((T) result);
            }
        } catch (final Exception e) {
            LOGGER.error("JSON 值转化失败，请检查。", e);
        }
        return rtnList;
    }


    /**
     * 将Map中的对象，通过配置转化为JavaBean的Map集合
     *
     * @param map
     * @param settings
     * @return
     */
    public static Map<String, Object> map2Beans(final Map<String, Object> map, final Map<String, Class<?>> settings) {
        final Map<String, Object> rtnValue = new HashMap<>(16);
        final Iterator<String> it = settings.keySet().iterator();
        while (it.hasNext()) {
            final String name = it.next();
            final Object obj = map.get(name);
            if (obj == null) {
                rtnValue.put(name, null);
            } else {
                final Class<?> clazz = settings.get(name);
                if (obj instanceof Map) {
                    final Object value = EntityUtils.map2Bean((Map<String, Object>) obj, clazz);
                    rtnValue.put(name, value);
                } else if (obj instanceof List) {
                    final List<Object> value = EntityUtils.list2Beans((List<Object>) obj, clazz);
                    rtnValue.put(name, value);
                } else if (obj.getClass().equals(clazz)) {
                    rtnValue.put(name, map.get(name));
                }
            }
        }
        return rtnValue;
    }


    /**
     * 读取json字符串
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObjectFromJsonString(String str, Class<T> clazz) {
        return StringUtils.isEmpty(str) ? null : readObject(str.getBytes(), clazz);
    }


    /**
     * 读取字节数组
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObject(byte[] data, Class<T> clazz) {
        JavaType javaType = EntityUtils.OBJECT_MAPPER.constructType(clazz);
        try {
            return EntityUtils.OBJECT_MAPPER.readValue(data, javaType);
        } catch (IOException e) {
            throw new BusinessRuntimeException(ERROR_MSG, e);
        }
    }

    /**
     * 读取字节数组
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObject(String data, Class<T> clazz) {
        JavaType javaType = EntityUtils.OBJECT_MAPPER.constructType(clazz);
        try {
            return EntityUtils.OBJECT_MAPPER.readValue(data, javaType);
        } catch (IOException e) {
            throw new BusinessRuntimeException(ERROR_MSG, e);
        }
    }

    /**
     * 读取输入流
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObject(InputStream data, Class<T> clazz) {
        JavaType javaType = EntityUtils.OBJECT_MAPPER.constructType(clazz);
        try {
            return EntityUtils.OBJECT_MAPPER.readValue(data, javaType);
        } catch (IOException e) {
            throw new BusinessRuntimeException(ERROR_MSG, e);
        }
    }


}

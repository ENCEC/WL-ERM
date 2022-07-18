package com.share.auth.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxf
 * @date 2020-10-26 10:23
 */
public class QueryResultUtils {

    public static final String QUERY_SUCCESS = "查询成功";

    /**
     * 私有构造器
     *
     * @param
     * @return
     * @author huanghwh
     * @date 2021/4/30 上午11:25
     */
    private QueryResultUtils() {
    }

    public static final Map<String, Object> getSuccessData(String msg, Object object) {
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", 1);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }

    public static final Map<String, Object> getFailData(String msg) {
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", 0);
        result.put("msg", msg);
        result.put("data", "");
        return result;
    }
}

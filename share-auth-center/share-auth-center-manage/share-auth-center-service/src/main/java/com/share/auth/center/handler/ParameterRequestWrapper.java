package com.share.auth.center.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chenxf
 * @Description: 继承HttpServletRequestWrapper，以便可以对request的requestParameter参数修改
 * @Date: 14:40 2021/1/11
 * @Param:
 * @Return:
 *
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String[]> params = new HashMap<>();

    @SuppressWarnings("unchecked")
    public ParameterRequestWrapper(HttpServletRequest request) {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
    }


    /**
     * @Author:chenxf
     * @Description: 重载一个构造方法
     * @Date: 14:41 2021/1/11
     * @Param: [request, extendParams]
     * @Return:
     *
     */
    public ParameterRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams) {
        this(request);
        addAllParameters(extendParams);
        //这里将扩展参数写入参数表
    }

    /**
     * @Author:chenxf
     * @Description: 重写getParameter，代表参数从当前类中的map获取
     * @Date: 14:41 2021/1/11
     * @Param: [name]
     * @Return:java.lang.String
     *
     */
    @Override
    public String getParameter(String name) {
         String[] values = params.get(name);
        if(values == null || values.length == 0 ) {
            return null;
        }
        return values[0];
    }

    /**
     * @Author:chenxf
     * @Description: 重写getParameter，代表参数从当前类中的map获取
     * @Date: 14:41 2021/1/11
     * @Param: [name]
     * @Return:java.lang.String[]
     *
     */
    @Override
    public String[] getParameterValues(String name) {
        //同上
        return params.get(name);
    }

    /**
     * @Author:chenxf
     * @Description: 增加多个参数
     * @Date: 14:41 2021/1/11
     * @Param: [otherParams]
     * @Return:void
     *
     */
    public void addAllParameters(Map<String, Object> otherParams) {
        for(Map.Entry<String, Object> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @Author:chenxf
     * @Description: 增加参数
     * @Date: 14:41 2021/1/11
     * @Param: [name, value]
     * @Return:void
     *
     */
    public void addParameter(String name, Object value) {
        if(value != null) {
            if(value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if(value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }
}

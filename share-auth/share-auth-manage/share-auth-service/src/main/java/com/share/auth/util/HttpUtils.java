package com.share.auth.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * <pre>
 * 描述：http工具类
 * </pre>
 *
 * @author ： mjy
 * @创建日期: 2018/11/28 14:33
 */
@Slf4j
public class HttpUtils {

    private HttpUtils() {
    }

    public static String sendGetRequest(String path, Map<String, String> params, String encoding) throws UnsupportedEncodingException {
        //参数转换为字符串
        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=");
            // 编码
            url.append(URLEncoder.encode(entry.getValue(), encoding));
            url.append('&');
        }
        url.deleteCharAt(url.length() - 1);
        try (CloseableHttpClient httpclient = new SslClient()) {

            // 创建httpget.
            HttpGet httpget = new HttpGet(url.toString());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                int successCode = 200;
                if (response.getStatusLine().getStatusCode() == successCode) {
                    // 获取响应实体
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return EntityUtils.toString(entity);
                    }
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("Facet aop failed error {}", e.getLocalizedMessage());
        }
        return null;
    }

}

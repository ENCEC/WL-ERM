package com.share.auth.center.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跨域请求（get\post）并获取响应结果
 *
 * @author liuhao
 * @version 2020-03-23
 */
@Slf4j
public class HttpsClientUtil {
    private HttpsClientUtil(){}
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 30000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        // Validate connections after 1 sec of inactivity
        connMgr.setValidateAfterInactivity(1000);
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);

        requestConfig = configBuilder.build();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url 请求地址
     * @param headers 请求头
     * @return JSONObject
     */
    public static JSONObject doGet(String url, Map<String, String> headers) {
        return doGet(url, new HashMap<>(16), headers);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url 请求地址
     * @param params 请求参数
     * @param headers 请求头
     * @return JSONObject
     */
    public static JSONObject doGet(String url, Map<String, Object> params, Map<String, String> headers) {
        String apiUrl = url;
        StringBuilder param = new StringBuilder();
        int i = 0;
        for (Map.Entry<String,Object> entry : params.entrySet())  {
            if (i == 0) {
                param.append("?");
            }else {
                param.append("&");
                param.append(entry.getKey()).append("=").append(entry.getValue());
            }
            i++;
        }
        apiUrl += param;
        String result = null;
        CloseableHttpClient httpClient = null;
        String httpsStr = "https";
        if (apiUrl.startsWith(httpsStr)) {
            httpClient = HttpClients.custom().setSSLSocketFactory(SslClient.createSslConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            for(Map.Entry<String, String> entry : headers.entrySet()){
                httpGet.addHeader(entry.getKey(),entry.getValue());
            }
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            log.error("HttpClientUtil-doGet:{}",e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("HttpClientUtil-close:{}", e);
            }
        }
        return JSON.parseObject(result);
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param url 请求地址
     * @param headers 请求头
     * @return JSONObject
     */
    public static JSONObject doPost(String url, Map<String,String> headers) {
        return doPost(url, new HashMap<>(5), headers);
    }

    /**
     * 发送 POST 请求，K-V形式
     *
     * @param url 请求地址
     * @param params 请求参数
     * @param headers 请求头
     * @return JSONObject
     */
    public static JSONObject doPost(String url, Map<String, Object> params, Map<String,String> headers) {
        CloseableHttpClient httpClient = null;

        String httpsStr = "https";
        if (url.startsWith(httpsStr)) {
            httpClient = HttpClients.custom().setSSLSocketFactory(SslClient.createSslConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        for(Map.Entry<String, String> entry : headers.entrySet()){
            httpPost.addHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, StandardCharsets.UTF_8));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("HttpClientUtil-doPost:{}",e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error("HttpClientUtil-close:{}",e);
                }
                response = null;
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("HttpClientUtil-close:{}", e);
            }
        }
        return JSON.parseObject(httpStr);
    }
}
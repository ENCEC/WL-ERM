package com.share.auth.center.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:chenxf
 * @Description: 获取request请求中的相关信息工具类
 * @Date: 16:09 2021/1/18
 * @Param: 
 * @Return:
 *
 */
@Slf4j
public class RequestUtil {
    private RequestUtil(){}
    /**
     * @Author:chenxf
     * @Description: 获取浏览器名称
     * @Date: 17:48 2020/11/28
     * @Param: [request]
     * @Return:java.lang.String
     *
     */
    public static final String getBrowser(HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * @Author:chenxf
     * @Description: 根据ip调用淘宝api获取城市名称
     * @Date: 17:49 2020/11/28
     * @Param: [ip]
     * @Return:java.lang.String
     *
     */
    public static final String getCityNameByTaoBaoApi(String ip) {
        String url = "http://106.15.80.185/service/getIpInfo.php?ip=" + ip;
        String cityName = "";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                JSONObject jsonResult = JSON.parseObject(strResult);
                JSONObject dataJson = jsonResult.getJSONObject("data");
                cityName = dataJson.getString("city");
            }
        } catch (Exception e) {
            log.error("e:{}", e);
        }
        return cityName;
    }
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.share.message.msg.client.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.share.message.msg.client.demo.Client;
import com.share.message.msg.client.demo.Request;
import com.share.message.msg.client.demo.Response;
import com.share.message.msg.client.demo.constant.Constants;
import com.share.message.msg.client.demo.constant.ContentType;
import com.share.message.msg.client.demo.constant.HttpHeader;
import com.share.message.msg.client.demo.enums.Method;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * 调用示例
 * 请替换APP_KEY,APP_SECRET,HOST,CUSTOM_HEADERS_TO_SIGN_PREFIX为真实配置
 * @Author nily
 * @Date 2020/12/31
 * @Time 下午12:34
 */
@Slf4j
public class PostUtil {

    private PostUtil() {
    }

    /**
     * 网关的host地址
     */
	private static final String HOST = "https://gateway.logink.cn";


    /**
     * HTTP POST 字符串
     *
     * @throws Exception
     */
    public static Response postString(String url, String body,
                                      String appKey, String appSecret) throws Exception {
        log.info("Url: " + url);
    	String path = url.substring(HOST.length());
        Map<String, String> headers = new HashMap<>(16);
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, ContentType.CONTENT_TYPE_JSON);
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMd5(body));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_JSON);
        //（非必填）用户自定义的header字段，用户自己决定是否参与签名，如果参与签名，将相关header信息设置如下
        headers.put("a-header1", "header1Value");
        headers.put("b-header2", "header2Value");

        List<String> customHeadersToSignPrefix = new ArrayList<>();
        customHeadersToSignPrefix.add("a-header1");
        customHeadersToSignPrefix.add("a-header2");


        Request request = new Request(Method.POST_STRING, HOST,
                path, appKey, appSecret, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(customHeadersToSignPrefix);

        //（非必填）根据api描述，如果有需要传递的参数，设置请求的query
        Map<String, String> querys = new HashMap<>(16);
        querys.put("a-query1", "query1Value");
        querys.put("b-query2", "query2Value");
        request.setQuerys(querys);

        request.setStringBody(body);

        //调用服务端
        Response response = Client.execute(request);
        log.info("Body: " + body);
        return response;
    }

}

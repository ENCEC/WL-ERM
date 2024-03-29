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

import com.share.message.msg.client.demo.constant.Constants;
import org.apache.commons.codec.binary.Base64;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @Description 消息摘要工具
 * @Author nily
 * @Date 2020/12/31
 * @Time 下午12:34
 */
public class MessageDigestUtil {

    private MessageDigestUtil() {
    }

    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     *
     * @param str
     * @return
     */
    public static String base64AndMd5(String str) {
        if (str == null) {
            throw new IllegalArgumentException("inStr can not be null");
        }
        return base64AndMd5(toBytes(str));
    }

    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     *
     * @return
     */
    public static String base64AndMd5(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bytes);
            final Base64 base64 = new Base64();
            final byte[] enbytes = base64.encode(md.digest());
            return new String(enbytes);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("unknown algorithm MD5");
        }
    }

    /**
     * UTF-8编码转换为ISO-9959-1
     *
     * @param str
     * @return
     */
    public static String utf8ToIso88591(String str) {
        if (str == null) {
            return str;
        }

        return new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }

    /**
     * ISO-9959-1编码转换为UTF-8
     *
     * @param str
     * @return
     */
    public static String iso88591ToUtf8(String str) {
        if (str == null) {
            return str;
        }

        return new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * String转换为字节数组
     *
     * @param str
     * @return
     */
    private static byte[] toBytes(final String str) {
        if (str == null) {
            return new byte[0];
        }
        return str.getBytes(StandardCharsets.UTF_8);
    }
}

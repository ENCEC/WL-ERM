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
package com.share.message.msg.client.demo.enums;

/**
 * @Description Http请求方法
 * @Author nily
 * @Date 2020/12/31
 * @Time 下午12:32
 */
public enum Method {
    /**
     * get请求
     */
    GET,
    /**
     * post请求，参数为form表单
     */
    POST_FORM,
    /**
     * post请求，参数string字符串
     */
    POST_STRING,
    /**
     * post请求，参数为字节
     */
    POST_BYTES,
    /**
     * put请求，参数为form
     */
    PUT_FORM,
    /**
     * put请求，参数为string字符串
     */
    PUT_STRING,
    /**
     * put请求，参数为字节
     */
    PUT_BYTES,
    /**
     * 删除
     */
    DELETE
}

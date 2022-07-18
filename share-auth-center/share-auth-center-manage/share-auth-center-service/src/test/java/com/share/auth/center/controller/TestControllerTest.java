package com.share.auth.center.controller;

import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.google.common.collect.ImmutableMap;
import com.share.auth.center.model.entity.SysApplication;
import com.share.auth.center.model.querymodels.QSysApplication;
import com.share.auth.center.model.querymodels.QUemUser;
import com.share.auth.center.model.vo.ApplicationVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestControllerTest {

    @Autowired
    DaoServiceClient client;
    @Value("${authCenter.addressList}")
    private List<String> centerAddList;

    @Test
    void saveSysApplication() {
        String env = "http://117.149.228.171:10003";
        Map<String,String> addMap = new HashMap<>(16);
        // 根据外网地址获取内网地址
        for(String address : centerAddList){
            String[] addArr = address.split("->");
            addMap.put(addArr[0], addArr[1]);
        }
        String innerAdd = addMap.get(env);
        System.out.println(innerAdd);
        System.out.println("tttt");
        List<SysApplication> lists = client.customCommand("BmsSqlParam")
                .mapperTo(SysApplication.class)
                .execute(ImmutableMap.of("application_name","test"));
        for(SysApplication sysApplication : lists){
            System.out.println(sysApplication.getApplicationName());
        }
    }

    @Test
    void relatedSelect(){
        Page page = QUemUser.uemUser
                .select(QUemUser.uemUserId,
                        QUemUser.account,
                        QUemUser.mobile,
                        QUemUser.email,
                        QUemUser.source,
                        QUemUser.oriApplication,
                        QUemUser.uemUser.chain(QSysApplication.applicationName))
                .paging(3,10)
                .mapperTo(ApplicationVO.class)
                .execute();
        System.out.println(page);
    }

    @Test
    void getToken(){
        String env = "http://117.149.228.171:10003";
        Map<String,String> addMap = new HashMap<>(16);
        // 根据外网地址获取内网地址
        for(String address : centerAddList){
            String[] addArr = address.split("->");
            addMap.put(addArr[0], addArr[1]);
        }
        String innerAdd = addMap.get(env);
        System.out.println(innerAdd);
    }
}
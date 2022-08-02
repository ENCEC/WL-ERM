package com.share.auth.controller;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.google.common.collect.ImmutableMap;
import com.share.auth.model.entity.SysApplication;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.querymodels.QSysApplication;
import com.share.auth.model.querymodels.QUemCompany;
import com.share.auth.model.querymodels.QUemUser;
import com.share.auth.model.vo.ApplicationVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestControllerTest {

    @Autowired
    DaoServiceClient client;

    @Test
    void saveSysApplication() {
        System.out.println("tttt");
//        SysApplication sys = new SysApplication();
//        //sys.setSysApplicationId("1234");
//        sys.setApplicationName("tt");
//        sys.setApplicationCode("test");
//        sys.setRowStatus(4);
//        int savedCount = QSysApplication.sysApplication.save(sys);
        List<SysApplication> lists = client.customCommand("BmsSqlParam")
                .mapperTo(SysApplication.class)
                .execute(ImmutableMap.of("application_name", "test"));
        for (SysApplication sysApplication : lists) {
            System.out.println(sysApplication.getApplicationName());
        }
    }

    @Test
    void selectSysApplication() {
        List<SysApplication> sysApplicationList = QSysApplication.sysApplication
                .select(QSysApplication.sysApplication.fieldContainer())
                .where(QSysApplication.applicationName.like(":applicationName"))
                .execute(ImmutableMap.of("applicationName", "44"));
        for (SysApplication sys : sysApplicationList) {
            System.out.println(sys.getApplicationName());
        }
    }

    @Test
    void companySave() {
        UemCompany uemCompany = new UemCompany();
        uemCompany.setCompanyNameCn("code");
        //uemCompany.setOrganizationType(12);
        uemCompany.setRowStatus(4);
        int saveCount = QUemCompany.uemCompany.tag("CompanySave").save(uemCompany);
        System.out.println(saveCount);
//        try{
//            int saveCount = QUemCompany.uemCompany.tag("CompanySave").save(uemCompany);
//            System.out.println(saveCount);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
    }

    @Test
    void relatedSelect() {
        Page page = QUemUser.uemUser
                .select(QUemUser.uemUserId,
                        QUemUser.account,
                        QUemUser.mobile,
                        QUemUser.email,
                        QUemUser.source,
                        QUemUser.oriApplication,
                        QUemUser.uemUser.chain(QSysApplication.applicationName))
                .paging(1, 10)
                .mapperTo(ApplicationVO.class)
                .execute();
        System.out.println(page);
    }

    @Test
    void saveApplication() {
        SysApplication sys = new SysApplication();
        //sys.setSysApplicationId("1234");
        //sys.setApplicationName("tt");
        sys.setApplicationCode("test");
        sys.setRowStatus(4);
        int savedCount = QSysApplication.sysApplication.save(sys);
        System.out.println(savedCount);
    }

    @Test
    void san() {
        String str = "1,2,3,4,5,6";
        String[] split = str.split(",");

        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
            Long  j = Long.valueOf(split[i]);
        }
    }

}

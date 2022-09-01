package com.share.auth.controller;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.google.common.collect.ImmutableMap;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.SysPostDTO;
import com.share.auth.model.entity.SysApplication;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.querymodels.QSysApplication;
import com.share.auth.model.querymodels.QSysPost;
import com.share.auth.model.querymodels.QUemCompany;
import com.share.auth.model.querymodels.QUemUser;
import com.share.auth.model.vo.ApplicationVO;
import com.share.support.result.CommonResult;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


//@RunWith(SpringRunner.class)
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
            Long j = Long.valueOf(split[i]);
        }
    }

    @DisplayName("分页查询岗位")
    @Test
    void querySysPost (){
        Page<SysPostDTO> sysPostDTOPage = QSysPost.sysPost.select(QSysPost.sysPost.fieldContainer())
                .where(
                        QSysPost.postName.like(":postName")
                                .and(QSysPost.status.eq(":status"))
                                .and(QSysPost.postId.goe$(1L))
                ).paging(1,10)
                .sorting(QSysPost.createTime.desc())
                .mapperTo(SysPostDTO.class)
                .execute(ImmutableMap.of("postName","项目经理","status","0"));
        System.out.println(sysPostDTOPage);
    }

    @DisplayName("岗位启动禁用")
    @Test
    void sysPostStartStop () {
        //岗位表id
        Long sysPostId =6969513008325959680L;
        //(0禁用,1启)
        String status = "0";
        //检查岗位是否存在
        assertFalse(Objects.isNull(sysPostId));
        SysPost sysPost = this.getSystemPostById(sysPostId);
        assertFalse(Objects.isNull(sysPost));

        sysPost.setPostId(sysPostId);
        sysPost.setStatus(status);
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int updateStatus = QSysPost.sysPost.selective(QSysPost.status).execute(sysPost);
        assertEquals(1,updateStatus,"失败");
    }

    public SysPost getSystemPostById(Long sysPostId) {
        List<SysPost> sysPostList = QSysPost.sysPost
                .select(QSysPost.sysPost.fieldContainer())
                .where(QSysPost.postId.eq$(sysPostId))
                .execute();
        if (sysPostList.size() == 1) {
            return sysPostList.get(0);
        }else {
            return null;
        }
    }
    @DisplayName("编辑岗位")
    @Test
    void updatePost() {
        //根据主键查询岗位信息
        Long sysPostID = 6969513008325959680L;
        String postName = "测试岗位";
        String postCode = " ";
        Integer postSort = 0;
        String remark = "";
        assertFalse(Objects.isNull(sysPostID),"id不允许没空");
        SysPost sysPost = this.getSystemPostById(sysPostID);
        assertFalse(Objects.isNull(sysPost),"不存在");
        //判断岗位名称是否改变
        if (sysPost.getPostName().equals(postName) == false) {
            List<SysPost> sysPosts = QSysPost.sysPost.select().where(QSysPost.postName.eq$(postName)).execute();
            assertFalse(CollectionUtils.isNotEmpty(sysPosts),"该岗位已存在");
        }
        //更新信息
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        sysPost.setPostId(sysPostID);
        sysPost.setPostName(postName);
        sysPost.setPostCode(postCode);
        sysPost.setPostSort(postSort);
        sysPost.setRemark(remark);
        int row = QSysPost.sysPost.save(sysPost);
        assertEquals(1,row,"编辑失败了");
    }
    @DisplayName("添加岗位")
    @Test
    void saveSyspost() {
        String postName = "测试岗位0901";
        String postCode = " ";
        Integer postSort = 0;
        String remark = "";
        //岗位名
        List<SysPost> sysPostList = QSysPost.sysPost
                .select(QSysPost.sysPost.fieldContainer())
                .where(QSysPost.postName.eq$(postName))
                .execute();
        assertFalse(CollectionUtils.isNotEmpty(sysPostList),"该岗位名已注册过！");
        SysPost sysPost = new SysPost();
        sysPost.setPostName(postName);
        sysPost.setPostCode(postCode);
        sysPost.setPostSort(postSort);
        sysPost.setRemark(remark);
        sysPost.setStatus("0");
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        int row = QSysPost.sysPost.save(sysPost);
        assertEquals(1,row,"添加失败");
    }
}

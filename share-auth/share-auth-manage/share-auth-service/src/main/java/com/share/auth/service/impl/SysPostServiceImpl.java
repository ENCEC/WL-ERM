package com.share.auth.service.impl;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.querymodels.QSysPost;
import com.share.auth.model.vo.SysPostVO;
import com.share.auth.service.SysPostService;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tanjp
 * @Date 2022/7/26 9:27
 */
@Service("SysPostService")
public class SysPostServiceImpl implements SysPostService {

    @Override
    public void addSysPost(SysPost sysPost) {
        sysPost.setStatus("0");
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        sysPost.setCreateBy("系统管理员");
        sysPost.setCreateTime(new DateTime());
        QSysPost.sysPost.save(sysPost);
    }

    @Override
    public Page<SysPost> findPage(int page, int size) {
        return QSysPost.sysPost
                .select(QSysPost.postId,QSysPost.postName,QSysPost.postCode,QSysPost.createBy,QSysPost.createTime,QSysPost.status)
                .where(QSysPost.postId.goe$(1L))
                .mapperTo(SysPost.class)
                .paging(page, size)
                .execute();
    }

    @Override
    public Page<SysPost> findPage(SysPost sysPost, int page, int size) {
        return QSysPost.sysPost
                .select(QSysPost.postId,QSysPost.postName,QSysPost.postCode,QSysPost.createBy,QSysPost.createTime,QSysPost.status)
                .where(QSysPost.postName.eq$(sysPost.getPostName()))
                .mapperTo(SysPost.class)
                .paging(page, size)
                .execute();
    }

    @Override
    public Page<SysPost> findPage(SysPost sysPost, int page, int size, String status) {
        return QSysPost.sysPost
                .select(QSysPost.postId,QSysPost.postName,QSysPost.postCode,QSysPost.createBy,QSysPost.createTime,QSysPost.status)
                .where(QSysPost.postName.eq$(sysPost.getPostName()).and(QSysPost.status.eq$(status)))
                .mapperTo(SysPost.class)
                .paging(page, size)
                .execute();
    }

    @Override
    public void updateSysPost(SysPost sysPost) {
        sysPost.setUpdateTime(new DateTime());
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QSysPost.sysPost
                .selective(QSysPost.postName)
                .update(sysPost);

    }

    @Override
    public void Prohibit(SysPost sysPost) {
        //QSysPost.sysPost.save(QSysPost.status.eq$("1"));
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QSysPost.sysPost
                .selective(QSysPost.status)
                .update(sysPost);


    }

    @Override
    public void deleteEmployeeId(Long id) {
        QSysPost.sysPost.deleteById(id);
    }
}

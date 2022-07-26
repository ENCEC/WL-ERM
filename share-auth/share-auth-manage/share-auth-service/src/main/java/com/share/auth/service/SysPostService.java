package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.vo.SysPostVO;

/**
 * @author tanjp
 * @Date 2022/7/26 9:27
 */
public interface SysPostService {
    /*
     * 新增岗位
     */
    void addSysPost(SysPost sysPost);

    //分页查询   page:当前页     size:每页显示的条数
    Page<SysPost> findPage(int page, int size);

    //分页查询条件   page:当前页     size:每页显示的条数  sysPost 条件
    Page<SysPost> findPage(SysPost sysPost,int page, int size);

    //分页查询条件   page:当前页     size:每页显示的条数  sysPost 条件
    Page<SysPost> findPage(SysPost sysPost,int page, int size,String status);

    //编辑
    void updateSysPost(SysPost sysPost);

    //禁用
    void Prohibit(SysPost sysPost);

    //删除
    void deleteEmployeeId(Long id);

    //获取选中的
}

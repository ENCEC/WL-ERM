package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.model.vo.UemUserPermissionVO;

/**
 * 权限审核Service
 * @author chenhy
 * @date 2021-05-19
 */
public interface UemUserPermissionService {


    /**
     * 保存权限申请信息
     * @param uemUserPermissionVO 权限申请信息
     */
    void saveUemUserPermission(UemUserPermissionVO uemUserPermissionVO);

    /**
     * 查询权限申请列表
     * @param uemUserPermissionVO 权限vo
     * @return 权限申请列表
     */
    Page<UemUserPermissionVO> queryUemUserPermission(UemUserPermissionVO uemUserPermissionVO);

    /**
     * 查询权限申请详情
     * @param uemUserPermissionId 权限id
     * @return 权限申请详情
     */
    UemUserPermissionVO getUemUserPermissionById(Long uemUserPermissionId);

    /**
     * 权限申请审核
     * @param uemUserPermissionVO 审核信息
     */
    void auditUemUserPermission(UemUserPermissionVO uemUserPermissionVO);

}

package com.share.auth.user;

import com.gillion.ec.core.security.data.CrudType;
import com.gillion.ec.core.security.data.IDataPermission;
import com.share.auth.model.entity.SysRoleAcl;
import lombok.*;

/**
 * 数据权限配置类
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-08-17
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataPermission implements IDataPermission {

    private String conditionSql;

    private CrudType crudType;

    public DataPermission(SysRoleAcl sysRoleAcl) {
        this.conditionSql = sysRoleAcl.getConditionSql();
        switch (sysRoleAcl.getCrudType().intValue()) {
            case 0:
                crudType = CrudType.INSERT;
                break;
            case 1:
                crudType = CrudType.DELETE;
                break;
            case 2:
                crudType = CrudType.UPDATE;
                break;
            case 3:
            default:
                crudType = CrudType.SELECT;
                break;
        }
    }
}

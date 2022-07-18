package com.gillion.permission;

import com.gillion.ec.core.security.data.AclMode;
import com.gillion.ec.core.security.data.CrudType;
import lombok.Data;

/**
 *  数据权限信息类，用于接收查询的权限信息
 *
 * @author wengms
 * @date 2:51 PM 2018/12/19
 */
@Data
public class DataPermissionInfo {
    private String tableName;
    private CrudType crudType;
    private AclMode aclMode;
    private String conditionSql;


    /**
     *
     * 将数据权限信息映射到权限表对象
     * @author wengms
     * @date 2:52 PM 2018/12/19
     * @param
     * @return com.gillion.permission.Table
     */
    public Table mappingTable(){
        return Table
                .builder()
                .aclMode(aclMode)
                .tableName(tableName)
                .build();
    }

    /**
     *
     * 将数据权限信息映射到具体数据权限操作类上
     * @author wengms
     * @date 2:52 PM 2018/12/19
     * @param
     * @return com.gillion.permission.DataPermission
     */
    public DataPermission mappingDataPermission(){
        return DataPermission
                .builder()
                .conditionSql(conditionSql)
                .crudType(crudType)
                .build();
    }

}

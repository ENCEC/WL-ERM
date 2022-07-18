package com.gillion.permission;


import com.gillion.ec.core.security.data.CrudType;
import com.gillion.ec.core.security.data.IDataPermission;
import lombok.*;


/**
 * 数据权限配置实体类
 *
 * @author wengms
 * @date 2:51 PM 2018/12/19
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataPermission implements IDataPermission {

    private String conditionSql;
    private CrudType crudType;
    private int tableId;



}

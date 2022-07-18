package com.gillion.permission;

import com.gillion.ec.core.security.data.AclMode;
import com.gillion.ec.core.security.data.CrudType;
import com.gillion.ec.core.security.data.ITable;
import com.gillion.ec.core.utils.ContextHolder;
import org.jooq.lambda.Seq;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author wengms
 * @date 2018/12/19 10:56 AM
 * @email wengms@gillion.com.cn
 */
public class DataPermissionUtils {

    private DataPermissionUtils(){}

    /**
     * 获取角色对应的数据权限列表
     *
     * @author wengms
     * @date 2:53 PM 2018/12/19
     * @param roleIds 角色id列表
     * @return java.util.Collection<com.gillion.ec.core.security.data.ITable>
     */
    public static Collection<ITable> getPermissionTableByRoles(String... roleIds) {
        JdbcTemplate jdbcTemplate = ContextHolder.getBean(JdbcTemplate.class);
        String parameterPlaceholder = Seq.of(roleIds)
                .map(roleId -> "?")
                .toString(",");

        List<Table> tables = jdbcTemplate.query("select * from sys_acl_table", (rs, i) -> {
            Table table = new Table();
            table.setTableName(rs.getString("table_name"));
            table.setAclMode(AclMode.values()[rs.getInt("acl_type")]);
            table.setTableId(rs.getInt("acl_table_id"));
            return table;
        });

        List<DataPermission> dataPermissions = jdbcTemplate.query("select * from sys_role_acl where role_id in ("+parameterPlaceholder+")",(rs,row)->{
            DataPermission dataPermission = new DataPermission();
            dataPermission.setConditionSql(rs.getString("conditions_sql"));
            dataPermission.setTableId(rs.getInt("acl_table_id"));
            dataPermission.setCrudType(CrudType.values()[rs.getInt("crud_type")]);
            return dataPermission;
        },roleIds);

        Map<Integer,List<DataPermission>> groupDataPermissions =  Seq.seq(dataPermissions).groupBy(DataPermission::getTableId);

        Seq.seq(tables)
                .forEach(table -> {
                    List<DataPermission> tableDataPermissions = groupDataPermissions.get(table.getTableId());
                    if (tableDataPermissions==null){
                        tableDataPermissions = Collections.EMPTY_LIST;
                    }
                    table.setDataPermissions(tableDataPermissions);
                });



        return Seq.seq(tables)
                .map(table -> (ITable)table)
                .toList();

    }

    /**
     * 将DataPermissionInfo转化为Table对象
     *
     * @author wengms
     * @date 2:54 PM 2018/12/19
     * @param tablePermissions
     * @return com.gillion.ec.core.security.data.ITable
     */
    private static ITable transformPermissionTable(List<DataPermissionInfo> tablePermissions) {
        DataPermissionInfo dataPermissionInfo = tablePermissions.get(0);
        Table table = dataPermissionInfo.mappingTable();
        List<DataPermission> dataPermissions = Seq.seq(tablePermissions)
                .map(DataPermissionInfo::mappingDataPermission)
                .toList();
        table.setDataPermissions(dataPermissions);
        return table;
    }
}

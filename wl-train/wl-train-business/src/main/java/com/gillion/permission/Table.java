package com.gillion.permission;


import com.gillion.ec.core.security.data.AclMode;
import com.gillion.ec.core.security.data.IDataPermission;
import com.gillion.ec.core.security.data.ITable;
import com.google.common.collect.Lists;
import lombok.*;
import org.jooq.lambda.Seq;

import java.util.Collection;

/**
 * 数据权限具体操作表对象
 *
 * @author wengms
 * @date 2:54 PM 2018/12/19
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Table implements ITable {

    private String tableName;
    private AclMode aclMode;
    private int tableId;
    @Singular
    private Collection<IDataPermission> dataPermissions;

    @Override
    public String getTableName() {
        return this.tableName;
    }


    public void setDataPermissions(Collection<DataPermission> dataPermissions) {
        this.dataPermissions = Lists.newArrayList();
        Seq.seq(dataPermissions)
                .forEach(this.dataPermissions::add);
    }

    @Override
    public AclMode getAclMode() {
        return this.aclMode;
    }

    @Override
    public Collection<IDataPermission> getDataPermissions() {
        if (this.dataPermissions == null) {
            this.dataPermissions = Lists.newArrayList();
        }
        return this.dataPermissions;
    }
}

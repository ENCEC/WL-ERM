package com.share.auth.user;

import com.beust.jcommander.internal.Lists;
import com.gillion.ec.core.security.data.AclMode;
import com.gillion.ec.core.security.data.IDataPermission;
import com.gillion.ec.core.security.data.ITable;
import com.share.auth.model.entity.SysAclTable;
import lombok.*;
import org.jooq.lambda.Seq;

import java.util.Collection;

/**
 * 数据权限配置类
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-08-17
 */
//@Data
//@Builder
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
@Value
public class TablePermission implements ITable {

    private String tableName;

    private AclMode aclMode;

    Collection<IDataPermission> dataPermissions;

//    @Singular
//    private Collection<IDataPermission> dataPermissions;
//
//    @Override
//    public String getTableName() {
//        return this.tableName;
//    }
//
//    public TablePermission(SysAclTable table) {
//        this.tableName = table.getTableName();
//        if (table.getAclMode() == 0) {
//            this.aclMode = AclMode.WHITE_LIST;
//        } else {
//            this.aclMode = AclMode.BLACK_LIST;
//        }
//    }
//
//    public void setDataPermissions(Collection<DataPermission> dataPermissions) {
//        this.dataPermissions = Lists.newArrayList();
//        Seq.seq(dataPermissions)
//                .forEach(this.dataPermissions::add);
//    }
//
//    @Override
//    public AclMode getAclMode() {
//        return this.aclMode;
//    }
//
//    @Override
//    public Collection<IDataPermission> getDataPermissions() {
//        if (this.dataPermissions == null) {
//            this.dataPermissions = Lists.newArrayList();
//        }
//        return this.dataPermissions;
//    }
}

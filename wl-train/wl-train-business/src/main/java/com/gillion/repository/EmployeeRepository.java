package com.gillion.repository;

import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.ds.client.api.queryobject.expressions.Expression;
import com.gillion.ds.exception.LocalizedExceptions;
import com.gillion.model.entity.Employee;
import com.gillion.model.querymodels.QEmployee;
import com.gillion.ds.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gillion.model.querymodels.QEmployee.employee;

/**
 * (Employee)数据存储类
 *
 * @author liaowj
 * @since 2019-10-23 23:05:26
 */
@SuppressWarnings({"WeakerAccess", "SpringJavaInjectionPointsAutowiringInspection"})
@Repository("employeeRepository}")
public class EmployeeRepository{

    /**
     * 根据外部组织的查询表达式查找 (Employee) 对象集合
     */
    public static final String DS_QUICKSTART_EMPLOYEE_QUERY_WHERE = "DS_QUICKSTART#EMPLOYEE#QUERY_WHERE";

    public static final String DS_QUICKSTART_EMPLOYEE_ADD = "DS_QUICKSTART#EMPLOYEE#ADD";

    public static final String DS_QUICKSTART_EMPLOYEE_UPDATE = "DS_QUICKSTART#EMPLOYEE#UPDATE";

    public static final String DS_QUICKSTART_EMPLOYEE_DELETE = "DS_QUICKSTART#EMPLOYEE#DELETE";


    private final DaoServiceClient client;

    public EmployeeRepository(@Qualifier("embedEngineClient") DaoServiceClient client) {
        this.client = client;
    }

    public List<Employee> queryWhere(Expression conditionExpression) {
        return client.withModel(employee)
                .select(employee.fieldContainer())
                .where(conditionExpression)
                .tag(DS_QUICKSTART_EMPLOYEE_QUERY_WHERE)
                .execute();
    }

    public void save(Employee e) {
        client.withModel(employee)
                .add()
                .tag(DS_QUICKSTART_EMPLOYEE_ADD)
                .execute(e);
    }

    public int update(Employee e) {
        return client.withModel(employee)
                .update()
                .tag(DS_QUICKSTART_EMPLOYEE_UPDATE)
                .execute(e);
    }

    public void delete(Long id) {
        client.withModel(employee)
                .delete()
                .where(QEmployee.id.eq$(id))
                .tag(DS_QUICKSTART_EMPLOYEE_UPDATE)
                .execute();
    }

    public Employee queryOne() {
        try {
            return client.withModel(employee)
                    .selectOne(employee.fieldContainer())
                    .where(QEmployee.id.eq$(1L))
                    .tag(DS_QUICKSTART_EMPLOYEE_QUERY_WHERE)
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("数据库不存在");
        }

    }

}

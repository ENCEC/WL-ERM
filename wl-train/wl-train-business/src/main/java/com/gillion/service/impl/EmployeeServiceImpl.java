package com.gillion.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.entity.Employee;
import com.gillion.model.querymodels.QEmployee;
import com.gillion.repository.EmployeeRepository;
import com.gillion.service.EmployeeService;
import com.gillion.service.FeignTransactionTest;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 * generate by daoService use Easy Code
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {


    @Override
    public Employee findByCnameAndMobile(String cname, String mobile) {
        Validate.notBlank(cname);
        Validate.notBlank(mobile);
        return QEmployee.employee.selectOne()
                .where(QEmployee.cname.eq$(cname).and(QEmployee.mobile.eq$(mobile)))
                .execute();
    }

    @Override
    public void transactionTest() {
        Employee employee = new Employee();
        employee.setEmployeeId(2L);
        employee.setMobile("123");
        employee.setCname("test");
        employee.setDepartmentId(1L);
        employee.setName("test");
        employee.setPassword("test");

        employee.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        QEmployee.employee.save(employee);
    }

    @Override
    public void batchInsertTest(){
        Employee employee;
        List<Employee> employeeList = new ArrayList<>();
        for(int i = 0; i <10; i++){
            employee = new Employee();
            employee.setEmployeeId(Long.parseLong(RandomUtil.randomNumbers(5)));
            employee.setMobile("123"+i);
            employee.setCname("test"+i);
            employee.setDepartmentId(1L);
            employee.setName("test"+i);
            employee.setPassword("test"+i);
            employee.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            employeeList.add(employee);
        }
        QEmployee.employee.save(employeeList);
    }

    @Override
    public void updateTest() {
//        Employee employee = QEmployee.employee.selectOne().where(QEmployee.employeeId.eq$(61716L)).execute();
//        employee.setDeptId(2L);
//        employee.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//        QEmployee.employee.save(employee);
        int updatedCount = QEmployee.employee.update(QEmployee.cname,QEmployee.address)
                .where(QEmployee.id.in(":ids"))
                .execute("小明","湖南长沙", Lists.newArrayList(52720L,45800L));
    }

    @Override
    public void deleteTest() {
        Employee employee = new Employee();
        employee.setEmployeeId(98999L);
        employee.setRowStatus(RowStatusConstants.ROW_STATUS_DELETED);
        QEmployee.employee.save(employee);
    }

    @Override
    public void deleteEmployeeId(Integer id) {
        QEmployee.employee.deleteById(id.longValue());
    }
}

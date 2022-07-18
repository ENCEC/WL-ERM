package com.gillion.service;

import com.gillion.model.entity.Employee;

/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 */
public interface EmployeeService{

    Employee findByCnameAndMobile(String cname, String mobile);

    void transactionTest();

    void batchInsertTest();

    void updateTest();

    void deleteTest();

    void deleteEmployeeId(Integer id);
}

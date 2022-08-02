package com.gillion.controller;

import com.gillion.ec.core.utils.ResultUtils;
import com.gillion.model.entity.Employee;
import com.gillion.service.EmployeeService;
import com.share.auth.api.ShareAuthInterface;
import com.share.support.result.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ShareAuthInterface authInterface;


    @PostMapping("/query1")
    @ResponseBody
    public Map<String, Object> query1(@RequestParam(value = "cname") String cname, @RequestParam(value = "mobile") String mobile) {
        Employee employee = employeeService.findByCnameAndMobile(cname,mobile);
        return ResultUtils.getSuccessResultData();
    }

    @RequestMapping("/insert1")
    public void insert1(){
        employeeService.transactionTest();
    }

    @RequestMapping("/insert2")
    public void insert2(){
        employeeService.batchInsertTest();
    }

    @RequestMapping("/update1")
    public void update1(){
        employeeService.updateTest();
    }

    @RequestMapping("/delete1")
    public void delete1(){
        employeeService.deleteTest();
    }

    @DeleteMapping("delete1/{id}")
    public Map<String, Object> deleteEmployeeId(@PathVariable("id") Integer id) {
        employeeService.deleteEmployeeId(id);
        return ResultUtils.getSuccessResultData(true);
    }

    @GetMapping("/hello")
    public String getHelloFlag(){
       ResultHelper resultHelper = authInterface.getHelloFlag();
       String str = (String) resultHelper.getData();
       return str;
    }
}

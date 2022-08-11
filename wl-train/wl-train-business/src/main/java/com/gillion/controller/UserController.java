//package com.gillion.controller;
//import com.gillion.ec.core.utils.ResultUtils;
//import com.gillion.login.User;
//import com.gillion.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
///**
// * @author wengms
// * @date 2021/1/11 3:35 下午
// * @email wengms@gillion.com.cn
// */
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private LoginService loginService;
//
//    @PostMapping("/login")
//    @ResponseBody
//    public Map<String, Object> login(@RequestBody User user) {
//        loginService.login(user);
//        return ResultUtils.getSuccessResultData();
//    }
//
//    @GetMapping("/logout")
//    @ResponseBody
//    public Map<String, Object> login() {
//        loginService.logout();
//        return ResultUtils.getSuccessResultData();
//    }
//}

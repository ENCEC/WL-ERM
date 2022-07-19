//package com.share.file.config;
//
//import com.gillion.ds.exception.UpdateCountNotEqualException;
//import com.gillion.ec.core.utils.ResultUtils;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.Map;
//
///**
// * <pre>
// * 描述：版本异常处理
// * </pre>
// *
// * @author chenql
// * @email chenql@gillion.com.cn
// * @date 2020/5/12
// * @time 17:01
// * @description: 版本异常处理
// */
//@ControllerAdvice
//public class VersionExceptionHandler {
//
//    @ExceptionHandler(UpdateCountNotEqualException.class)
//    @ResponseBody
//    public Map<String, Object> handle(UpdateCountNotEqualException e) {
//        return ResultUtils.getFaildResultData("CommonConstants.VERSION_MESSAGE");
//    }
//
//}

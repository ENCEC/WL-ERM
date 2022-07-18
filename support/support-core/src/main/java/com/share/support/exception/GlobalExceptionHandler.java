package com.share.support.exception;

import com.gillion.ds.exception.UpdateCountUnintendedException;
import com.share.support.enums.GlobalExceptionCode;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author tujx
 * @description 全局异常处理
 * @date 2020/12/24
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 数据库操作异常
     *
     * @param ex
     * @return ResultHelper
     * @throws
     * @author tujx
     */
    @ResponseBody
    @ExceptionHandler({BadSqlGrammarException.class, DataIntegrityViolationException.class})
    public ResultHelper databaseErrorHandler(Exception ex) {
        log.error("发生数据库异常：", ex);
        return CommonResult.getFaildResultDataWithErrorCode(GlobalExceptionCode.DATASOURCE_EXCEPTION.getCode(), GlobalExceptionCode.DATASOURCE_EXCEPTION.getMessage());
    }


    /**
     * 捕捉IO异常
     *
     * @param ex
     * @return ResultHelper
     * @throws
     * @author tujx
     */
    @ResponseBody
    @ExceptionHandler(IOException.class)
    public ResultHelper iOErrorHandler(IOException ex) {
        log.error("发生IO异常：", ex);
        return CommonResult.getFaildResultDataWithErrorCode(GlobalExceptionCode.IO_EXCEPTION.getCode(),GlobalExceptionCode.IO_EXCEPTION.getMessage());
    }


    /**
     * 捕捉空指针异常
     *
     * @param ex
     * @return ResultHelper
     * @throws
     * @author tujx
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public ResultHelper nullPointErrorHandler(NullPointerException ex) {
        log.error("发生空指针异常：", ex);
        return CommonResult.getFaildResultDataWithErrorCode(GlobalExceptionCode.NULL_POINT_EXCEPTION.getCode(),GlobalExceptionCode.NULL_POINT_EXCEPTION.getMessage());
    }


    /**
     * 捕捉业务异常
     *
     * @param ex
     * @return ResultHelper
     * @throws
     * @author tujx
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResultHelper myErrorHandler(RuntimeException ex) {
        log.error("发生业务异常：", ex);
        return CommonResult.getFaildResultDataWithErrorCode(GlobalExceptionCode.RUNTIME_EXCEPTION.getCode(),ex.getMessage());
    }

    /**
     * 前端数据格式异常处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultHelper httpMessageNotReadableErrorHandler(HttpMessageNotReadableException ex){
        log.error("发生业务异常：", ex);
        return CommonResult.getFaildResultDataWithErrorCode(GlobalExceptionCode.RUNTIME_EXCEPTION.getCode(),"业务接口异常");
    }

    /**
     * 捕捉业务异常
     *
     * @param ex
     * @return ResultHelper
     * @throws
     * @author tujx
     */
    @ResponseBody
    @ExceptionHandler(UpdateCountUnintendedException.class)
    public ResultHelper UpdateCountUnintendedErrorHandler(UpdateCountUnintendedException ex) {
        log.error("发生乐观锁版本不一致异常：", ex);
        return CommonResult.getFaildResultDataWithErrorCode(GlobalExceptionCode.VERSION_EXCEPTION.getCode(),GlobalExceptionCode.VERSION_EXCEPTION.getMessage());
    }

    /**
     * 捕捉其它异常
     *
     * @param ex
     * @return ResultHelper
     * @throws
     * @author tujx
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultHelper errorHandler(Exception ex) {
        log.error("发生未知异常：", ex);
        return CommonResult.getFaildResultDataWithErrorCode(GlobalExceptionCode.UNKNOWN_EXCEPTION.getCode(),GlobalExceptionCode.UNKNOWN_EXCEPTION.getMessage());
    }
}

package com.share.support.result;


import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * 统一的返回工具类
 * @author wangcl
 * @date 2020/12/31
 */
public class CommonResult {
    public static final String RESULT_SYMBOL = "RESULT_SYMBOL";

    public CommonResult() {
    }

    /**
     * 返回失败结果
     * @return
     */
    public static final ResultHelper getFaildResultData() {
        ResultHelper resultHelper = new ResultHelper();
        resultHelper.setSuccess(false);
        return resultHelper;
    }

    /**
     * 返回失败结果
     * @param errorMessages 错误信息
     * @return
     */
    public static final ResultHelper getFaildResultData(String... errorMessages) {
        ResultHelper resultHelper = new ResultHelper();
        resultHelper.setSuccess(false);
        resultHelper.setErrorMessages(errorMessages);
        return resultHelper;
    }

    /**
     * 返回失败结果
     * @param errorCode 失败码
     * @param errorMessages 失败信息
     * @return
     */
    public static final ResultHelper getFaildResultDataWithErrorCode(Object errorCode, String... errorMessages) {
        ResultHelper resultHelper = new ResultHelper();
        resultHelper.setSuccess(false);
        resultHelper.setErrorMessages(errorMessages);
        resultHelper.setErrorCode(errorCode);
        return resultHelper;
    }

    /**
     * 返回失败信息
     * @param errorMessages 失败集合
     * @return
     */
    public static final ResultHelper getFaildResultData(Collection<String> errorMessages) {
        if (CollectionUtils.isNotEmpty(errorMessages)) {
            String[] var1 = (String[])errorMessages.toArray(new String[errorMessages.size()]);
            return getFaildResultData(var1);
        } else {
            return getFaildResultData();
        }
    }

    /**
     * 返回成功
     * @return
     */
    public static final ResultHelper getSuccessResultData() {
        ResultHelper resultHelper = new ResultHelper();
        resultHelper.setSuccess(true);
        return resultHelper;
    }

    /**
     * 返回成功
     * @param data 具体对象
     * @return
     */
    public static final ResultHelper getSuccessResultData(Object data) {
        ResultHelper resultHelper = new ResultHelper();
        resultHelper.setSuccess(true);
        resultHelper.setData(data);
        return resultHelper;
    }
}

package com.share.support.result;

/**
 * 外部接口以及feign间调用统一返回值
 * @author wangcl
 * @date 2021/05/21
 */
public class CommonApiReturn {
    private CommonApiReturn(){}
    /**
     * 返回失败结果
     * @return
     */
    public static final ApiResultHelper<Object> getFailedResultData(String resultCode, String resultMsg) {
        ApiResultHelper<Object> resultHelper = new ApiResultHelper<>();
        resultHelper.setResultCode(resultCode);
        resultHelper.setResultMsg(resultMsg);
        return resultHelper;
    }

    /**
     * 返回成功
     * @return
     */
    public static final ApiResultHelper<Object> getSuccessResultData(String resultCode, String resultMsg) {
        ApiResultHelper<Object> resultHelper = new ApiResultHelper<>();
        resultHelper.setResultCode(resultCode);
        resultHelper.setResultMsg(resultMsg);
        return resultHelper;
    }

    /**
     * 返回成功
     * @param data 具体对象
     * @return
     */
    public static final ApiResultHelper<Object> getSuccessResultData(String resultCode, String resultMsg, Object data) {
        ApiResultHelper<Object> resultHelper = getSuccessResultData(resultCode, resultMsg);
        resultHelper.setData(data);
        return resultHelper;
    }
}

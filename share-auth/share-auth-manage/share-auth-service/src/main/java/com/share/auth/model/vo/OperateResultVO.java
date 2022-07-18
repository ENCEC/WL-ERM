package com.share.auth.model.vo;

import lombok.Data;

/**
 * 国家综合交通运输信息平台增、删、改结果VO
 * @author chenhy
 * @date 2021-05-14
 */
@Data
public class OperateResultVO {

    /**
     * 返回结果状态码：
     * 1  - 同步成功；
     * 0  - 同步失败；
     * -1 - 连接错误；
     * -2 - 没有传入必要参数
     */
    private String status;

    /**
     * 成功信息或失败原因
     */
    private String message;

    /**
     * 返回结果总数
     */
    private String resultCount;

    /**
     * 参考操作类型1：新增，2：修改，3：删除
     */
    private String optionType;

    /**
     * 组织机构编码：表示对于哪个组织机构进行操作
     */
    private String orgCode;

    /**
     * 登录账号：表示对于哪个用户进行操作
     */
    private String loginNo;

    /**
     * 返回成功信息
     * @param optionType 操作类型
     * @param orgCode 组织机构代码
     * @param loginNo 登录账号
     * @return 成功信息
     */
    public static OperateResultVO getSuccessMessage(String optionType, String orgCode, String loginNo){
        OperateResultVO resultVO = new OperateResultVO();
        resultVO.setStatus("1");
        resultVO.setMessage("同步成功");
        resultVO.setResultCount("1");
        resultVO.setOptionType(optionType);
        resultVO.setOrgCode(orgCode);
        resultVO.setLoginNo(loginNo);
        return resultVO;
    }

    /**
     * 返回失败信息
     * @param optionType 操作类型
     * @param orgCode 组织机构代码
     * @param loginNo 登录账号
     * @return 失败信息
     */
    public static OperateResultVO getFailMessage(String optionType, String orgCode, String loginNo){
        OperateResultVO resultVO = new OperateResultVO();
        resultVO.setStatus("0");
        resultVO.setMessage("同步失败");
        resultVO.setResultCount("1");
        resultVO.setOptionType(optionType);
        resultVO.setOrgCode(orgCode);
        resultVO.setLoginNo(loginNo);
        return resultVO;
    }

    /**
     * 返回参数错误信息
     * @param optionType 操作类型
     * @param orgCode 组织机构代码
     * @param loginNo 登录账号
     * @return 失败信息
     */
    public static OperateResultVO getParamErrorMessage(String message, String optionType, String orgCode, String loginNo){
        OperateResultVO resultVO = new OperateResultVO();
        resultVO.setStatus("-2");
        resultVO.setMessage(message);
        resultVO.setResultCount("1");
        resultVO.setOptionType(optionType);
        resultVO.setOrgCode(orgCode);
        resultVO.setLoginNo(loginNo);
        return resultVO;
    }

}

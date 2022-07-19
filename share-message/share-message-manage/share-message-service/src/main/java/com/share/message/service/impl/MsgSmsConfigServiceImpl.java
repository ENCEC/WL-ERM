package com.share.message.service.impl;

import com.gillion.ds.utils.ResultUtils;
import com.share.message.constants.GlobalConstant;
import com.share.message.dto.MsgSmsConfigDto;
import com.share.message.enums.GlobalEnum;
import com.share.message.model.entity.MsgSmsConfig;
import com.share.message.model.querymodels.QMsgSmsConfig;
import com.share.message.service.MsgSmsConfigService;
import com.share.support.util.AES128Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 消息模板实现类
 * @Author nily
 * @Date 2020/11/2
 * @Time 2:28 下午
 */
@Service
public class MsgSmsConfigServiceImpl implements MsgSmsConfigService {
    private static final String REGEX_NUMBER = "^[a-z0-9A-Z]+$";
    /**
     * nily
     * 宏模板配置AES加密
     * 同邮件密码加密秘钥
     */
    @Value("${mail.aes_secret_key}")
    private String passwordSecretKey;


    /**
     * @param msgSmsConfigDto
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 新增短信发送配置
     */
    @Override
    public Map<String, Object> saveSmsConfig(MsgSmsConfigDto msgSmsConfigDto) {
        Map<String, Object> resultData = validateMsgConfigValue(msgSmsConfigDto);
        if (resultData != null) {
            return resultData;
        }
        MsgSmsConfig msgSmsConfig = convertToEntity(msgSmsConfigDto);

        //AES 加密
        try {
            // 有服务商的才需要加密密码
            if (StringUtils.isNotEmpty(msgSmsConfig.getSmsServiceType()) && GlobalEnum.ServiceType.ISP.getCode().equals(msgSmsConfig.getSmsServiceType())) {
                String password = AES128Util.encrypt(msgSmsConfig.getIspPassword(), passwordSecretKey);
                msgSmsConfig.setIspPassword(password);
            }
        } catch (Exception e) {
            return ResultUtils.getFailedResultData("密码加密异常");
        }

        msgSmsConfig.setRowStatus(GlobalConstant.SAVE);
        int save = QMsgSmsConfig.msgSmsConfig.save(msgSmsConfig);
        if (save == 1) {
            resultData = ResultUtils.getSuccessResultData(msgSmsConfig);
        } else {
            resultData = ResultUtils.getFailedResultData("saveSmsConfig 保存失败");
        }
        return resultData;
    }


    /**
     * @param msgSmsConfigDto 短信发送配置对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 编辑短信发送配置
     */
    @Override
    public Map<String, Object> updateSmsConfig(MsgSmsConfigDto msgSmsConfigDto) {
        Map<String, Object> resultData = validateMsgConfigValue(msgSmsConfigDto);
        if (resultData != null) {
            return resultData;
        }
        MsgSmsConfig msgSmsConfig = convertToEntity(msgSmsConfigDto);
        MsgSmsConfig msgSmsConfig1 = QMsgSmsConfig.msgSmsConfig.selectOne().byId(msgSmsConfig.getMsgSmsConfigId());

        if (msgSmsConfig1 != null && !msgSmsConfig1.getIspPassword().equals(msgSmsConfig.getIspPassword())) {
            // 密码被修改 AES 加密
            try {
                // 有服务商的才需要加密密码
                if (StringUtils.isNotEmpty(msgSmsConfig.getSmsServiceType()) && GlobalEnum.ServiceType.ISP.getCode().equals(msgSmsConfig.getSmsServiceType())) {
                    String password = AES128Util.encrypt(msgSmsConfig.getIspPassword(), passwordSecretKey);
                    msgSmsConfig.setIspPassword(password);
                }
            } catch (Exception e) {
                return ResultUtils.getFailedResultData("密码加密异常");
            }
        }
        msgSmsConfig.setRowStatus(GlobalConstant.UPDATE);


        if (msgSmsConfig1 != null) {
            int update = QMsgSmsConfig.msgSmsConfig.save(msgSmsConfig);
            if (update == 1) {
                resultData = ResultUtils.getSuccessResultData(msgSmsConfig);
            } else {
                resultData = ResultUtils.getFailedResultData("updateSmsConfig 更新失败");
            }
        } else {
            resultData = ResultUtils.getFailedResultData("updateSmsConfig 更新失败 查询不到对应的msgSmsConfig");
        }
        return resultData;
    }

    /**
     * @param msgSmsConfigDto
     * @return com.share.message.model.entity.MsgSmsConfig
     * @Description dto 转化成 entity
     */
    private MsgSmsConfig convertToEntity(MsgSmsConfigDto msgSmsConfigDto) {
        MsgSmsConfig msgSmsConfig = new MsgSmsConfig();
        if (msgSmsConfigDto != null) {
            msgSmsConfig.setMsgSmsConfigId(msgSmsConfigDto.getMsgSmsConfigId());
            msgSmsConfig.setBusinessSystemId(msgSmsConfigDto.getBusinessSystemId());
            msgSmsConfig.setChannelName(msgSmsConfigDto.getChannelName());
            msgSmsConfig.setKeyword(msgSmsConfigDto.getKeyword());
            msgSmsConfig.setSmsServiceType(msgSmsConfigDto.getSmsServiceType());
            msgSmsConfig.setSmsUrl(msgSmsConfigDto.getSmsUrl());
            msgSmsConfig.setAppKey(msgSmsConfigDto.getAppKey());
            msgSmsConfig.setAppSecret(msgSmsConfigDto.getAppSecret());
            msgSmsConfig.setIspNo(msgSmsConfigDto.getIspNo());
            msgSmsConfig.setIspAccount(msgSmsConfigDto.getIspAccount());
            //密码 AES 加密
            msgSmsConfig.setIspPassword(msgSmsConfigDto.getIspPassword());
            msgSmsConfig.setPriority(msgSmsConfigDto.getPriority());
            msgSmsConfig.setIsValid(true);
        }
        return msgSmsConfig;
    }

    /**
     * @param businessSystemId
     * @return java.util.List<com.share.message.dto.MsgSmsConfigDto>
     * @Description 根据 businessSystemId 获取服务商列表
     */
    @Override
    public List<MsgSmsConfigDto> getMsgConfigByBusinessSystemId(String businessSystemId) {
        List<MsgSmsConfig> msgSmsConfig = QMsgSmsConfig.msgSmsConfig.select().where(QMsgSmsConfig.businessSystemId.eq$(businessSystemId).and(QMsgSmsConfig.priority.notNull()).and(QMsgSmsConfig.isValid.eq$(true)))
                .sorting(QMsgSmsConfig.priority.desc(), QMsgSmsConfig.modifyTime.desc())
                .execute();
        List<MsgSmsConfigDto> lists = new ArrayList<>();
        for (MsgSmsConfig smsConfig : msgSmsConfig) {
            MsgSmsConfigDto msgSmsConfigDto = convertToDto(smsConfig);
            lists.add(msgSmsConfigDto);
        }
        return lists;
    }

    /**
     * @param msgSmsConfig
     * @return com.share.message.dto.MsgSmsConfigDto
     * @Description entity 转成dto
     */
    private MsgSmsConfigDto convertToDto(MsgSmsConfig msgSmsConfig) {
        MsgSmsConfigDto msgSmsConfigDto = new MsgSmsConfigDto();
        if (msgSmsConfig != null) {
            msgSmsConfigDto.setMsgSmsConfigId(msgSmsConfig.getMsgSmsConfigId());
            msgSmsConfigDto.setBusinessSystemId(msgSmsConfig.getBusinessSystemId());
            msgSmsConfigDto.setChannelName(msgSmsConfig.getChannelName());
            msgSmsConfigDto.setKeyword(msgSmsConfig.getKeyword());
            msgSmsConfigDto.setSmsServiceType(msgSmsConfig.getSmsServiceType());
            msgSmsConfigDto.setSmsUrl(msgSmsConfig.getSmsUrl());
            msgSmsConfigDto.setAppKey(msgSmsConfig.getAppKey());
            msgSmsConfigDto.setAppSecret(msgSmsConfig.getAppSecret());
            msgSmsConfigDto.setIspNo(msgSmsConfig.getIspNo());
            msgSmsConfigDto.setIspAccount(msgSmsConfig.getIspAccount());
            msgSmsConfigDto.setIspPassword(msgSmsConfig.getIspPassword());
            msgSmsConfigDto.setPriority(msgSmsConfig.getPriority());
        }
        return msgSmsConfigDto;
    }

    /**
     * @param msgSmsConfigDto
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 参数验证
     */
    private Map<String, Object> validateMsgConfigValue(MsgSmsConfigDto msgSmsConfigDto) {
        int length50 = 50;
        int length200 = 200;
        int length300 = 300;
        if (StringUtils.isEmpty(msgSmsConfigDto.getChannelName())) {
            return ResultUtils.getFailedResultData("消息发送配置对象 渠道名称 必填字段为空");
        } else if (msgSmsConfigDto.getChannelName().length() > length50) {
            return ResultUtils.getFailedResultData("消息发送配置对象 渠道名称 字段大于50字符 ");
        }

        if (StringUtils.isEmpty(msgSmsConfigDto.getSmsServiceType())) {
            return ResultUtils.getFailedResultData("消息发送配置对象 短信接口类型 必填字段为空 ");
        }
        if (StringUtils.isEmpty(msgSmsConfigDto.getSmsUrl())) {
            return ResultUtils.getFailedResultData("消息发送配置对象 短信接口地址必填字段为空");
        } else if (msgSmsConfigDto.getSmsUrl().length() > length200) {
            return ResultUtils.getFailedResultData("消息发送配置对象 短信接口地址 必填字段大于200字符 ");
        }

        if (StringUtils.equals(msgSmsConfigDto.getSmsServiceType(), GlobalEnum.ServiceType.ISP.getCode())) {
            if (StringUtils.isEmpty(msgSmsConfigDto.getIspNo())) {
                return ResultUtils.getFailedResultData("消息发送配置对象 服务商编号 必填字段为空 ");
            }
            if (StringUtils.isEmpty(msgSmsConfigDto.getIspAccount())) {
                return ResultUtils.getFailedResultData("消息发送配置对象 服务商账号 必填字段为空 ");
            } else if (msgSmsConfigDto.getIspAccount().length() > length300) {
                return ResultUtils.getFailedResultData("消息发送配置对象 服务商账号  大于300字符 ");
            }
            if (StringUtils.isEmpty(msgSmsConfigDto.getIspPassword())) {
                return ResultUtils.getFailedResultData("消息发送配置对象 服务商密码 必填字段为空");
            }
        }

        if (StringUtils.isEmpty(msgSmsConfigDto.getAppKey())) {
            return ResultUtils.getFailedResultData("消息发送配置对象 AppKey 必填字段为空");
        } else if (msgSmsConfigDto.getAppKey().length() > length50) {
            return ResultUtils.getFailedResultData("消息发送配置对象 AppKey 大于50字符");
        } else if (!msgSmsConfigDto.getAppKey().matches(REGEX_NUMBER)) {
            return ResultUtils.getFailedResultData("消息发送配置对象 Appkey 不是数字或字符格式");
        }

        if (StringUtils.isEmpty(msgSmsConfigDto.getAppSecret())) {
            return ResultUtils.getFailedResultData("消息发送配置对象  AppSecret 必填字段为空 ");
        } else if (msgSmsConfigDto.getAppSecret().length() > length50) {
            return ResultUtils.getFailedResultData("消息发送配置对象  AppSecret 必填字段大于50字符");
        } else if (!msgSmsConfigDto.getAppSecret().matches(REGEX_NUMBER)) {
            return ResultUtils.getFailedResultData("消息发送配置对象  AppSecret 不是数字或字符格式 ");
        }
        return null;
    }
}

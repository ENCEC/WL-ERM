package com.share.message.service.impl;

import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.message.constants.GlobalConstant;
import com.share.message.enums.DataLengthEnum;
import com.share.message.model.entity.MsgEmailConfig;
import com.share.message.model.entity.MsgEmailTemplate;
import com.share.message.model.querymodels.QMsgEmailConfig;
import com.share.message.model.querymodels.QMsgEmailTemplate;
import com.share.message.service.SysApplicationService;
import com.share.support.util.AES128Util;
import com.share.message.vo.MsgEmailConfigVO;
import com.share.message.service.MsgEmailConfigService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * 邮件发送配置实现类
 *
 * @author tujx
 * @date 2020/10/16
 */
@Slf4j
@Service
public class MsgEmailConfigServiceImpl implements MsgEmailConfigService {

    @Autowired
    private SysApplicationService sysApplicationService;

    /**
     * 邮件密码加密秘钥
     */
    @Value("${mail.aes_secret_key}")
    private String passwordSecretKey;

    /**
     * 已确认禁用绑定模板
     */
    private static final Integer TEMPLATE_DISABLED_Y = 1;

    /**
     * 未确认禁用绑定模板
     */
    private static final Integer TEMPLATE_DISABLED_N = 0;


    /**
     * 新增一条邮件发送配置
     *
     * @param msgEmailConfigVO 邮件发送配置信息
     * @return Map
     * @author tujx
     */
    @Override
    public ResultHelper<String> saveEmailConfig(MsgEmailConfigVO msgEmailConfigVO) {
        //数据校验
        if (StringUtils.isBlank(msgEmailConfigVO.getSystemId())) {
            return CommonResult.getFaildResultData("新增失败，使用方系统编码不能为空");
        }
        List<String> validateFailStrs = emailConfigValidate(msgEmailConfigVO, null);
        String applicationCode = msgEmailConfigVO.getSystemId();
        if (!CollectionUtils.isEmpty(validateFailStrs)) {
            //数据校验未通过
            String validateFailStr = StringUtils.join(validateFailStrs, "，");
            return CommonResult.getFaildResultData("新增失败，" + validateFailStr);
        }
        MsgEmailConfig msgEmailConfig = new MsgEmailConfig();
        BeanUtils.copyProperties(msgEmailConfigVO, msgEmailConfig);
        //对密码进行AES加密
        try {
            String password = AES128Util.encrypt(msgEmailConfig.getEmailPassword(), passwordSecretKey);
            msgEmailConfig.setEmailPassword(password);
        } catch (Exception e) {
            return CommonResult.getFaildResultData("新增失败，密码加密异常");
        }
        msgEmailConfig.setIsValid(true);
        msgEmailConfig.setBusinessSystemId(applicationCode);
        msgEmailConfig.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        QMsgEmailConfig.msgEmailConfig.tag("EmailConfigCode").save(msgEmailConfig);
        return CommonResult.getSuccessResultData("新增成功");
    }

    /**
     * 编辑一条邮件发送配置
     *
     * @param msgEmailConfigVO 邮件发送配置信息
     * @return Map
     * @author tujx
     */
    @Override
    public ResultHelper<String> updateEmailConfig(MsgEmailConfigVO msgEmailConfigVO) {
        MsgEmailConfig msgEmailConfig = QMsgEmailConfig.msgEmailConfig.selectOne().byId(msgEmailConfigVO.getMsgEmailConfigId());
        //数据校验
        List<String> validateFailStrs = emailConfigValidate(msgEmailConfigVO, msgEmailConfig);
        if (!CollectionUtils.isEmpty(validateFailStrs)) {
            //数据校验未通过
            String validateFailStr = StringUtils.join(validateFailStrs, "，");
            return CommonResult.getFaildResultData("保存失败，" + validateFailStr);
        }
        //判断密码是否变更
        if (!StringUtils.equals(msgEmailConfig.getEmailPassword(), msgEmailConfigVO.getEmailPassword())) {
            try {
                //对新密码进行AES加密
                String password = AES128Util.encrypt(msgEmailConfigVO.getEmailPassword(), passwordSecretKey);
                msgEmailConfigVO.setEmailPassword(password);
            } catch (Exception e) {
                return CommonResult.getFaildResultData("保存失败，密码加密异常");
            }
        }
        BeanUtils.copyProperties(msgEmailConfigVO, msgEmailConfig);
        msgEmailConfig.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QMsgEmailConfig.msgEmailConfig.selective(QMsgEmailConfig.emailConfigName, QMsgEmailConfig.emailType, QMsgEmailConfig.host,
                QMsgEmailConfig.port, QMsgEmailConfig.protocol, QMsgEmailConfig.emailAddress, QMsgEmailConfig.emailPassword,
                QMsgEmailConfig.emailUser)
                .update(msgEmailConfig);
        return CommonResult.getSuccessResultData("保存成功");
    }

    /**
     * 禁用/启用邮件发送配置
     *
     * @param msgEmailConfigId 邮件发送配置id
     * @param disabledTemplate 是否同时停用相关模板，0：否，1：是
     * @return Map
     * @author tujx
     */
    @Override
    public ResultHelper<String> updateEmailConfigStatusById(Long msgEmailConfigId, Integer disabledTemplate) {
        ResultHelper<String> result;
        MsgEmailConfig msgEmailConfig = QMsgEmailConfig.msgEmailConfig.selectOne().byId(msgEmailConfigId);
        if (Objects.isNull(msgEmailConfig)) {
            result = CommonResult.getFaildResultData("修改失败");
        } else {
            if (Boolean.TRUE.equals(msgEmailConfig.getIsValid())) {
                //禁用操作
                List<MsgEmailTemplate> enableTemplateList = QMsgEmailTemplate.msgEmailTemplate.select()
                        .where(QMsgEmailTemplate.msgEmailConfigId.eq$(msgEmailConfigId).and(QMsgEmailTemplate.isValid.eq$(true)))
                        .mapperTo(MsgEmailTemplate.class)
                        .execute();
                if (Objects.equals(TEMPLATE_DISABLED_N, disabledTemplate)) {
                    //初次点击禁用，判断是否绑定了启用模板
                    if (!CollectionUtils.isEmpty(enableTemplateList)) {
                        //绑定了模板，暂停禁用，返回前端确认是否同时禁用所有绑定的邮件模板
                        result = CommonResult.getFaildResultDataWithErrorCode(0);
                        return result;
                    }
                } else if (Objects.equals(TEMPLATE_DISABLED_Y, disabledTemplate) && !CollectionUtils.isEmpty(enableTemplateList)) {
                    //用户选择禁用所有绑定的模板
                    enableTemplateList.stream().forEach(t -> t.setIsValid(false));
                    QMsgEmailTemplate.msgEmailTemplate.selective(QMsgEmailTemplate.isValid).update(enableTemplateList);

                }

            }
            msgEmailConfig.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            msgEmailConfig.setIsValid(!msgEmailConfig.getIsValid());
            QMsgEmailConfig.msgEmailConfig.save(msgEmailConfig);
            result = CommonResult.getSuccessResultData("修改成功");
        }
        return result;
    }


    /**
     * 发送邮箱配置数据校验
     *
     * @param msgEmailConfigVO 提交的配置信息
     * @param savedEmailConfig 数据库中的配置信息
     * @return List<String> 校验提示
     * @throws
     * @author tujx
     */
    public List<String> emailConfigValidate(MsgEmailConfigVO msgEmailConfigVO, MsgEmailConfig savedEmailConfig) {
        List<String> validateFailStrs = new ArrayList<>();
        if (StringUtils.isBlank(msgEmailConfigVO.getEmailConfigName())) {
            validateFailStrs.add("邮箱配置名称不能为空");
        } else if (StringUtils.length(msgEmailConfigVO.getEmailConfigName()) > DataLengthEnum.EmailConfig.EMAIL_CONFIG_NAME.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailConfig.EMAIL_CONFIG_NAME.getTipsStr());
        }
        if (StringUtils.isBlank(msgEmailConfigVO.getEmailType())) {
            validateFailStrs.add("邮箱类型不能为空");
        }
        if (StringUtils.isBlank(msgEmailConfigVO.getHost())) {
            validateFailStrs.add("服务器地址不能为空");
        } else if (StringUtils.length(msgEmailConfigVO.getHost()) > DataLengthEnum.EmailConfig.HOST.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailConfig.HOST.getTipsStr());
        }
        if (Objects.isNull(msgEmailConfigVO.getPort())) {
            validateFailStrs.add("服务器端口不能为空");
        } else if (Integer.toString(msgEmailConfigVO.getPort()).length() > DataLengthEnum.EmailConfig.PORT.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailConfig.PORT.getTipsStr());
        }
        if (StringUtils.isBlank(msgEmailConfigVO.getProtocol())) {
            validateFailStrs.add("邮箱协议不能为空");
        }
        if (Objects.isNull(msgEmailConfigVO.getEmailAddress())) {
            validateFailStrs.add("发送方邮箱不能为空");
        } else if (msgEmailConfigVO.getEmailAddress().length() > DataLengthEnum.EmailConfig.EMAIL_ADDRESS.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailConfig.EMAIL_ADDRESS.getTipsStr());
        } else {
            Matcher emailMatcher = GlobalConstant.EMAIL_PATTERN.matcher(msgEmailConfigVO.getEmailAddress());
            if (!emailMatcher.matches()) {
                validateFailStrs.add("发送方邮箱格式不正确");
            }
        }
        Boolean newPwdFlag = false;
        if (Objects.isNull(msgEmailConfigVO.getEmailPassword())) {
            validateFailStrs.add("发送方邮箱密码不能为空");
        } else if (Objects.nonNull(savedEmailConfig)) {
            //调用方是更新方法
            if (!StringUtils.equals(savedEmailConfig.getEmailPassword(), msgEmailConfigVO.getEmailPassword())) {
                //密码已更新
                newPwdFlag = true;
            }
        } else {
            //调用方是新增方法
            newPwdFlag = true;
        }
        if (Boolean.TRUE.equals(newPwdFlag) && msgEmailConfigVO.getEmailPassword().length() > DataLengthEnum.EmailConfig.EMAIL_PASSWORD.getLength()) {
            //新密码，进行校验
            validateFailStrs.add(DataLengthEnum.EmailConfig.EMAIL_PASSWORD.getTipsStr());

        }
        if (msgEmailConfigVO.getEmailUser().length() > DataLengthEnum.EmailConfig.EMAIL_USER.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailConfig.EMAIL_USER.getTipsStr());
        }
        return validateFailStrs;
    }


}

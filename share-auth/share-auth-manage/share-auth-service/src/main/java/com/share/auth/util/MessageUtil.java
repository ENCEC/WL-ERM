package com.share.auth.util;

import com.share.auth.constants.GlobalConstant;
import com.share.auth.model.entity.OauthClientDetails;
import com.share.auth.model.entity.SysApplication;
import com.share.auth.model.entity.SysPlatformUser;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.querymodels.QOauthClientDetails;
import com.share.auth.model.querymodels.QSysApplication;
import com.share.auth.model.querymodels.QSysPlatformUser;
import com.share.auth.model.querymodels.QUemUser;
import com.share.support.util.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * @Author:chenxf
 * @Description: 消息发送工具类
 * @Date: 20:15 2020/11/26
 * @Param:
 * @Return:
 */
@Component
public class MessageUtil {

    /**系统clientId*/
    @Value("${msg.systemCode}")
    private String systemCode;

    private static String SYSTEM_CODE;

    @PostConstruct
    public void getClient(){
        SYSTEM_CODE = this.systemCode;
    }



    /**
     * @Author:chenxf
     * @Description: 获取创建人手机号
     * @Date: 19:26 2020/11/26
     * @Param: [createId]
     * @Return:java.lang.String
     */
    public static String getCreatePhone(Long createId) {
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(createId);
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(createId);
        if (Objects.nonNull(uemUser)) {
            return uemUser.getMobile();
        } else if (Objects.nonNull(sysPlatformUser)) {
            return sysPlatformUser.getTel();
        }
        return null;
    }

    /**
     * @Author:chenxf
     * @Description: 获取当前系统applicationCode
     * @Date: 19:27 2020/11/26
     * @Param: []
     * @Return:java.lang.String
     */
    public static String getApplicationCode() {
       return SYSTEM_CODE;
    }


    /**
     * @Author:chenxf
     * @Description: 获取当前系统applicationId
     * @Date: 19:27 2020/11/26
     * @Param: []
     * @Return:java.lang.Long
     */
    public static Long getApplicationId() {
        SysApplication sysApplication = QSysApplication.sysApplication.selectOne(QSysApplication.sysApplication.fieldContainer()).where(QSysApplication.applicationCode.eq$(SYSTEM_CODE)).execute();
        if (Objects.nonNull(sysApplication)) {
            return sysApplication.getSysApplicationId();
        } else {
            return null;
        }
    }

    /**
     * 获取来源应用id
     * @return 来源应用id
     */
    public static Long getOriApplicationId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 判空
        if (Objects.isNull(requestAttributes)) {
            return null;
        }
        // 获取clientId
        HttpServletRequest request = requestAttributes.getRequest();
        String clientId = CookieUtil.getCookieByName(request, GlobalConstant.COOKIE_CLIENT_ID);
        // 判空
        if (StringUtils.isBlank(clientId)) {
            return null;
        }
        // 获取应用id
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
        return Objects.isNull(oauthClientDetails) ? null : oauthClientDetails.getSysApplicationId();
    }

    /**
     * @Author:chenxf
     * @Description: 获取指定位数的数字随机数
     * @Date: 15:08 2020/11/28
     * @Param: [num]
     * @Return:java.lang.String
     */
    public static String randomNum(int num) {
        StringBuilder randomNum = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < num; i++) {
            randomNum.append(random.nextInt(10));
        }
        return randomNum.toString();
    }

    /**
     * 生成随机密码，2位大写字母+3位小写字母+_(下划线)+4位随机数字，字母o和数字0不在随机范围内容
     * @return 随机面
     */
    public static String generateRandomPassword() {
        // 密码
        StringBuilder randomPassword = new StringBuilder();
        // 安全随机数
        SecureRandom random = new SecureRandom();
        // 2位随机大写字母
        int upperLetterLength = 2;
        for (int i = 0; i < upperLetterLength; i++) {
            char letter = GlobalConstant.RANDOM_LETTER.charAt(random.nextInt(GlobalConstant.RANDOM_LETTER.length()));
            // 第3-5位字母转小写
            randomPassword.append(letter);
        }
        // 3位随机小写字母
        int lowerLetterLength = 3;
        for (int i = 0; i < lowerLetterLength; i++) {
            char letter = GlobalConstant.RANDOM_LETTER.charAt(random.nextInt(GlobalConstant.RANDOM_LETTER.length()));
            // 字母转小写
            String letterStr = String.valueOf(letter);
            randomPassword.append(StringUtils.lowerCase(letterStr));
        }
        // 拼接下划线
        randomPassword.append("_");
        // 4位随机数字
        int numberLength = 4;
        for (int i = 0; i < numberLength; i++) {
            char number = GlobalConstant.RANDOM_NUMBER.charAt(random.nextInt(GlobalConstant.RANDOM_NUMBER.length()));
            randomPassword.append(number);
        }

        return randomPassword.toString();
    }

}

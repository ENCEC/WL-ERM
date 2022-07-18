package com.gillion.user;


import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.UserInfoCollector;
import com.gillion.ec.core.utils.CookieUtils;
import com.gillion.ec.core.utils.RequestAndResponseContextHolder;
import com.gillion.ec.core.utils.UserUtils;
import com.gillion.ec.mq.config.ConsumerGroup;
import com.gillion.ec.mq.config.Topic;
import com.gillion.ec.mq.listener.ConsumerListener;
import com.gillion.ec.mq.listener.ProducerListener;
import com.gillion.ec.mq.message.Message;
import com.gillion.login.CredentialManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息获取器，主要用于获取当前登录用户信息
 *
 * @author wengms
 * @date 2:44 PM 2018/12/19
 */
@Component("userInfoCollector")
public class DefaultUserInfoCollector implements UserInfoCollector, ProducerListener, ConsumerListener {

    @Autowired
    private CredentialManager credentialManager;

    private ThreadLocal<String> ticketThreadLocal = new ThreadLocal<>();

    /**
     * 获取当前登录用户ID
     *
     * @author wengms
     * @date 2:45 PM 2018/12/19
     * @param
     * @return java.lang.Object
     */
    @Override
    public Object getUserId() {
        return getCurrentLoginUser().getUserId();
    }


    /**
     * 获取当前组织ID
     *
     * @author wengms
     * @date 2:45 PM 2018/12/19
     * @param
     * @return java.lang.String
     * @deprecated 根据项目需要扩展即可，demo项目中暂不实现
     */
    @Deprecated
    @Override
    public String getOfficeId() {
        //TODO 暂不实现
        return null;
    }

    /**
     * 获取当前登录用户信息
     *
     * @author wengms
     * @date 2:44 PM 2018/12/19
     * @param
     * @return com.gillion.ec.core.security.IUser
     */
    @Override
    public IUser getCurrentLoginUser() {
        String ticket = getTicket();
        if (StringUtils.isNotEmpty(ticket)) {
            return credentialManager.parseCredential(ticket);
        }
        return UserUtils.anonymousUser;
    }

    private String getTicket() {
        if (StringUtils.isNotEmpty(ticketThreadLocal.get())) {
            return ticketThreadLocal.get();
        } else {
            HttpServletRequest request = RequestAndResponseContextHolder.request();
            if (request != null) {
                Cookie ticketCookie = CookieUtils.getCookie("ticket");
                if (ticketCookie != null) {
                    return ticketCookie.getValue();
                }
            }
            return null;
        }


    }

    @Override
    public void beforeConsume(ConsumerGroup consumerGroup, Message message) {
        String ticket = message.getHeaderValue("ticket");
        ticketThreadLocal.set(ticket);
    }

    @Override
    public void afterConsume(ConsumerGroup consumerGroup, Message message) {
        ticketThreadLocal.remove();

    }

    @Override
    public void beforeProduce(Topic topic, Message message) {
        String ticket = this.getTicket();
        message.getHeader().put("ticket", ticket);
    }

    @Override
    public void afterProduce(Topic topic, Message message) {
    }
}

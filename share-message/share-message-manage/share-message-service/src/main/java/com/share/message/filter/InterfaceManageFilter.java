package com.share.message.filter;

import com.alibaba.fastjson.JSON;
import com.share.message.enums.GlobalEnum;
//import com.share.portal.api.PortalEnvironmentService;
//import com.share.portal.domain.ValidateCapabilityVO;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author tujx
 * @description 接口管理过滤器
 * @date 2020/12/16
 */
@Slf4j
/*@WebFilter(filterName = "interfaceManageFilter", urlPatterns = {"/msgEmailTemplate/getEmailTemplateByCode",
        "/msgEmailTemplate/sendEmail","/MsgApi/getMarcoById","/MsgApi/sendMsg"})*/
public class InterfaceManageFilter implements Filter {

    /**
     * 当前环境名称
     */
    @Value("${env}")
    private String env;

    /**
     * 能力名称-推送中心
     */
    private static final String CAPABILITY_NAME = "推送中心";

//    @Autowired
//    private PortalEnvironmentService portalEnvironmentService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        Map<String, Object> result = new HashMap<>(3);
//        result.put("resultCode", GlobalEnum.ErrorCode.DATASOURCE_API_ERROR.getResultCode());
//        String resultMsg = null;
//        ValidateCapabilityVO validateCapabilityVO = new ValidateCapabilityVO();
//        validateCapabilityVO.setEnvCode(env);
//        validateCapabilityVO.setCapabilityName(CAPABILITY_NAME);
//        //查询环境、能力是否为正常状态（启用、上线）
//        ResultHelper<Object> envCapabilityResult = portalEnvironmentService.validateEnvCapabilityStatusByName(validateCapabilityVO);
//        Boolean requestStatus = envCapabilityResult.getSuccess();
//        if (Objects.isNull(requestStatus) || Boolean.FALSE.equals(requestStatus)) {
//            resultMsg = GlobalEnum.ErrorCode.DATASOURCE_API_ERROR.getResultMsg();
//            ArrayList<Object> failMsgList = (ArrayList) envCapabilityResult.getErrorMessages();
//            if (!CollectionUtils.isEmpty(failMsgList)) {
//                resultMsg = resultMsg + "【" + failMsgList.get(0) + "】";
//            }
//        }
//        if (StringUtils.isNotBlank(resultMsg)) {
//            //环境能力配置异常
//            result.put("resultMsg", resultMsg);
//            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//            httpResponse.setStatus(200);
//            httpResponse.setCharacterEncoding("UTF-8");
//            httpResponse.setContentType("application/json;charset=utf-8");
//            PrintWriter out = httpResponse.getWriter();
//            out.write(JSON.toJSONString(result));
//            out.flush();
//            out.close();
//            return;
//        }
        //能力配置正常
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

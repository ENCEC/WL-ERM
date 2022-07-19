package com.share.message.service.impl;

import com.share.auth.api.ShareAuthInterface;
import com.share.auth.domain.QueryApplicationDTO;
import com.share.message.service.SysApplicationService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.share.message.constants.GlobalConstant.SESSION_KEY_APP_CODE;

/**
 * @author tujx
 * @description 系统切换实现类
 * @date 2020/11/19
 */
@Slf4j
@Service
public class SysApplicationServiceImpl implements SysApplicationService {
    /**
     * 公共服务内部系统编码
     */
    @Value("${portal.applicationCode.file}")
    private String file;
    @Value("${portal.applicationCode.message}")
    private String message;
    @Value("${portal.applicationCode.env}")
    private String env;

    /**
     * 账号权限应用权限api固定参数
     */
    public static final String OP_TYPE = "yycx";

    @Autowired
    private ShareAuthInterface shareAuthInterface;

    /**
     * 获取系统列表
     *
     * @param
     * @return ResultHelper<List < QueryApplicationDTO>>
     * @throws
     * @author tujx
     */
    @Override
    public ResultHelper<List<QueryApplicationDTO>> getSystemList() {
        List<String> portalSystemList = Stream.of(file, message, env).collect(Collectors.toList());
        //调用账号权限系统接口获取系统列表
        List<QueryApplicationDTO> applicationList = shareAuthInterface.queryApplicationForOpType(OP_TYPE);
        if (CollectionUtils.isNotEmpty(applicationList)) {
            List<QueryApplicationDTO> list = applicationList.stream().filter(a -> portalSystemList.contains(a.getApplicationCode()) == false).collect(Collectors.toList());
            return CommonResult.getSuccessResultData(list);
        }
        return CommonResult.getSuccessResultData(applicationList);
    }

    /**
     * 切换选定的系统
     *
     * @param applicationCode 系统code
     * @return Map
     * @author tujx
     */
    @Override
    public ResultHelper<Object> updateApplication(String applicationCode) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return CommonResult.getFaildResultData("切换失败");
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY_APP_CODE, applicationCode);
        return CommonResult.getSuccessResultData();
    }

    /**
     * 查询当前的系统编码
     *
     * @return String 系统编码
     * @author tujx
     */
    @Override
    public String getCurrentApplicationCode() {
        String result = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            throw new NullPointerException("查询失败");
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        Object applicationCode = session.getAttribute(SESSION_KEY_APP_CODE);
        if (Objects.nonNull(applicationCode)) {
            result = applicationCode.toString();
        }
        return result;
    }
}

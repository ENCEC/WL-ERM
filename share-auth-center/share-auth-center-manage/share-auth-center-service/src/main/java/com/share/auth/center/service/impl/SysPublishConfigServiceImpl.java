package com.share.auth.center.service.impl;
import com.share.auth.center.model.entity.SysPublishConfig;
import com.share.auth.center.model.querymodels.QSysPublishConfig;
import com.share.auth.center.service.SysPublishConfigService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

/**
 * @author wangzicheng
 * @description
 * @date 2021年04月12日 23:40
 */
@Service
public class SysPublishConfigServiceImpl implements SysPublishConfigService {


    @Override
    public String getTimes() {
        SysPublishConfig sysPublishConfig = QSysPublishConfig.sysPublishConfig.selectOne()
                .where(QSysPublishConfig.isOpen.notNull())
                .execute();
        if (Boolean.FALSE.equals(Objects.isNull(sysPublishConfig)) && Boolean.FALSE.equals(sysPublishConfig.getIsDeleted()) && Boolean.TRUE.equals(sysPublishConfig.getIsOpen())) {
            return encodeTimes(sysPublishConfig.getTimes());
        } else {
            return null;
        }
    }

    /**
     * @param times
     * @return java.lang.String
     * @author wangzicheng
     * @date 2021/4/8 13:40
     * base64 加密
     */
    private String encodeTimes(String times) {
        //创建加密对象
        Base64.Encoder base64 = Base64.getEncoder();
        //获取加密后的字节数组
        byte[] encode = base64.encode(times.getBytes());
        //把密文转换为字符串
        return new String(encode);
    }
}

package com.gillion.service.impl;

import com.gillion.ec.core.utils.AntPathReqMatcher;
import com.gillion.ec.security.web.domain.SysResource;
import com.gillion.ec.security.web.domain.SysResourceRelation;
import com.gillion.ec.security.web.function.service.ResourceService;
import com.gillion.ec.security.web.utils.ResourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  资源服务信息
 *
 * @author wengms
 * @date 2:53 PM 2018/12/19
 */
@Service
public class ResourceServiceImpl implements ResourceService<AntPathReqMatcher> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取所有资源权限信息
     *
     * @author wengms
     * @date 2:53 PM 2018/12/19
     * @param
     * @return java.util.Map<com.gillion.ec.core.utils.AntPathReqMatcher,com.gillion.ec.security.web.domain.SysResource>
     */
    @Override
    public Map<AntPathReqMatcher, SysResource> loadAllUrlMatcherWithResource() {
        final List<SysResource> resources = jdbcTemplate.query("SELECT RESOURCE_ID, URL  FROM sys_resource", BeanPropertyRowMapper.newInstance(SysResource.class));
        final List<SysResourceRelation> resourceRelations = jdbcTemplate.query("SELECT RESOURCE_ID, PARENT_RESOURCE_ID FROM SYS_RESOURCE_REL", BeanPropertyRowMapper.newInstance(SysResourceRelation.class));
        return ResourceUtils.buildResourceData(resources, resourceRelations);
    }
}

package com.share.auth.service.impl;

import com.gillion.ds.client.DSContext;
import com.share.auth.domain.SysDictCodeVO;
import com.share.auth.service.SysDictService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @description数据字典接口实现类
 * @Author: zhuyp
 * @Date: 2021/10/27 10:45
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Override
    public Map<String, List<SysDictCodeVO>> selectSysDictCodeMapToCodeList(List<String> codeList) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("dictTypeCodeList",codeList);
        List<SysDictCodeVO> sysDictCodeDTOList = DSContext.customization("CZT_selectSysDictCode_Auth").select()
                .mapperTo(SysDictCodeVO.class)
                .execute(paramMap);
        Map<String, List<SysDictCodeVO>> queryResourceDTOMap = sysDictCodeDTOList.stream().collect(Collectors.groupingBy(SysDictCodeVO::getDictTypeCode));
        return queryResourceDTOMap;
    }
}

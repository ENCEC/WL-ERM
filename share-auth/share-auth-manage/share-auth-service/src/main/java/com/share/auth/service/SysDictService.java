package com.share.auth.service;

import com.share.auth.domain.SysDictCodeVO;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description数据字典接口
 * @Author: zhuyp
 * @Date: 2021/10/27 10:43
 */
public interface SysDictService {
    /**
     * 查询数据字典数据
     * @param dictTypeCodeList
     * @return   Map<String, List<SysDictCodeVO>>
     */
    Map<String, List<SysDictCodeVO>> selectSysDictCodeMapToCodeList(List<String> dictTypeCodeList);

}

package com.gillion.remote.impl;

import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.ec.distributed.annotations.Lockable;
import com.gillion.model.entity.DictAreaInfo;
import com.gillion.model.entity.WaybillRouteNode;
import com.gillion.remote.RemoteRouteService;
import com.gillion.repository.DictAreaInfoRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 这是一个模拟远程服务, 计算路由节点
 *
 * @author guosc
 * @Email guosc@gillion.com.cn
 * @date 2019/10/24
 * @time 22:01
 * @description:
 */
@Service
public class RemoteRouteServiceImpl implements RemoteRouteService {

    private List<DictAreaInfo> dictAreaInfos = Lists.newLinkedList();

    @Autowired
    private DictAreaInfoRepository dictAreaInfoRepository;


    @Lockable(lockKey = "p0")
    @Override
    public List<WaybillRouteNode> calculateRouteInfos(String sendAreaCode, String deliveryAreaCode) {
        List<WaybillRouteNode> waybillRouteNodes = Lists.newArrayList();
        List<DictAreaInfo> dictAreaInfos;
        if(this.dictAreaInfos.size() == 0){
            this.dictAreaInfos = dictAreaInfoRepository.queryAll().stream().sorted((Comparator.comparingLong(dictAreaInfo -> Long.valueOf(dictAreaInfo.getAreaCode())))).collect(Collectors.toList());

        }
        dictAreaInfos = this.dictAreaInfos.stream()
                .filter(info-> (Long.valueOf(info.getAreaCode())>=Long.valueOf(sendAreaCode))&& (Long.valueOf(info.getAreaCode())<=Long.valueOf(deliveryAreaCode)))
                .collect(Collectors.toList());
        for(int i=0;i<dictAreaInfos.size()-1;i++){
            WaybillRouteNode waybillRouteNode = new WaybillRouteNode();
            waybillRouteNode.setStartingSiteName(dictAreaInfos.get(i).getAreaFullName());
            waybillRouteNode.setArrivalSiteName(dictAreaInfos.get(i+1).getAreaFullName());
            waybillRouteNode.setSiteNum(i);
            waybillRouteNode.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            waybillRouteNodes.add(waybillRouteNode);
        }
        return waybillRouteNodes;
    }
}

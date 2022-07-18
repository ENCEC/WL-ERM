package com.gillion.remote;

import com.gillion.model.entity.WaybillRouteNode;

import java.util.List;

/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 */
public interface RemoteRouteService {

    List<WaybillRouteNode> calculateRouteInfos(String sendAreaCode, String deliveryAreaCode);
}

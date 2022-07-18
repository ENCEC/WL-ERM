package com.gillion.repository;

import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.model.entity.WaybillInfo;
import com.gillion.model.querymodels.QWaybillInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


/**
 * @author guosc
 * @Email guosc@gillion.com.cn
 * @date 2019/11/18
 * @time 17:34
 * @description:
 */
@SuppressWarnings({"WeakerAccess", "SpringJavaInjectionPointsAutowiringInspection"})
@Repository("waybillInfoRepository}")
public class WaybillInfoRepository {
    public static final String DS_QUICKSTART_WAYBILL_INFO_SAVE_ONE = "DS_QUICKSTART#WAYBILL_INFO#SAVE_ONE";


    private final DaoServiceClient client;

    public WaybillInfoRepository(@Qualifier("embedEngineClient") DaoServiceClient client) {
        this.client = client;
    }

    public void saveWaybillInfo(WaybillInfo waybillInfo) {
        client.withModel(QWaybillInfo.waybillInfo)
                .add()
                .tag(DS_QUICKSTART_WAYBILL_INFO_SAVE_ONE)
                .execute(waybillInfo);
    }
}

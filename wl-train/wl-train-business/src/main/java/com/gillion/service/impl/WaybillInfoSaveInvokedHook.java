package com.gillion.service.impl;

import com.gillion.ds.chain.annotation.RestfulApiInvokedHookFor;
import com.gillion.ds.chain.interfaces.RestfulInvokedHook;
import com.gillion.ds.chain.model.InvokedResponse;
import com.gillion.model.entity.WaybillInfo;
import org.springframework.stereotype.Component;

/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 */
@Component
@RestfulApiInvokedHookFor("DS_QUICKSTART_WAYBILL_INFO_SAVE")
public class WaybillInfoSaveInvokedHook implements RestfulInvokedHook {

    @Override
    public InvokedResponse process(InvokedResponse invokedResponse) {
        final WaybillInfo waybillInfo = invokedResponse.getResponseDataBean(WaybillInfo.class);
        final String consigneeMobile = waybillInfo.getConsigneeMobile();
        waybillInfo.setConsigneeMobile(consigneeMobile.substring(0, 3) + "****" + consigneeMobile.substring(7));
        return invokedResponse;
    }
}

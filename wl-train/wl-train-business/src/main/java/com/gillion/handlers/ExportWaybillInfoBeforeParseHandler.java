package com.gillion.handlers;

import com.gillion.ds.inout.annotation.InoutHandlerFor;
import com.gillion.ds.inout.chain.ExportInvoker;
import com.gillion.ds.inout.chain.handler.ExportCommand;
import com.gillion.ds.inout.chain.handler.ExportHandler;
import com.gillion.ds.inout.chain.model.ExportRecord;
import com.gillion.ds.inout.model.HandlerType;
import com.gillion.model.querymodels.QWaybillInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author zengqw
 * @version 1.0.0.0
 * @date 2020-01-21 15:11
 */
@Component
@InoutHandlerFor(tag = "export_waybill_info", handlerType = HandlerType.BEFORE_EXPORT_PARSE)
@Slf4j
public class ExportWaybillInfoBeforeParseHandler implements ExportHandler {

    @Override
    public ExportRecord handle(ExportInvoker invoker, ExportCommand command) {
        final ExportRecord record = command.getRecord();
        record.getCellValue(QWaybillInfo.sendAreaCode);
//        record.getOriginCellValue(QWaybillInfo.sendAreaCode);
        return invoker.invoke(command);
    }
}

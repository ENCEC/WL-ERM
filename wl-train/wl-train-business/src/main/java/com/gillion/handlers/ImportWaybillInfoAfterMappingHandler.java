package com.gillion.handlers;

import com.gillion.ds.inout.annotation.InoutHandlerFor;
import com.gillion.ds.inout.chain.ImportInvoker;
import com.gillion.ds.inout.chain.handler.ImportCommand;
import com.gillion.ds.inout.chain.handler.ImportHandler;
import com.gillion.ds.inout.chain.model.ImportRecord;
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
@InoutHandlerFor(tag = "import_waybill_info", handlerType = HandlerType.AFTER_IMPORT_MAPPING)
@Slf4j
public class ImportWaybillInfoAfterMappingHandler implements ImportHandler {

    @Override
    public ImportRecord handle(ImportInvoker invoker, ImportCommand command) {
        final ImportRecord record = command.getRecord();
        record.getCellValue(QWaybillInfo.sendAreaCode);
        return invoker.invoke(command);
    }
}

package com.gillion.handlers;

import com.gillion.ds.inout.annotation.InoutHandlerFor;
import com.gillion.ds.inout.chain.ExportInvoker;
import com.gillion.ds.inout.chain.handler.ExportCommand;
import com.gillion.ds.inout.chain.handler.ExportHandler;
import com.gillion.ds.inout.chain.model.ExportRecord;
import com.gillion.ds.inout.model.HandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author zengqw
 * @version 1.0.0.0
 * @date 2020-01-21 15:11
 */
@Component
@InoutHandlerFor(tag = "export_waybill_info", handlerType = HandlerType.BEFORE_EXPORT_VALIDATE)
@Slf4j
public class ExportWaybillInfoBeforeValidateHandler implements ExportHandler {

    @Override
    public ExportRecord handle(ExportInvoker invoker, ExportCommand command) {
        if (command.getRecord().getRecordIndex() == 0) {
            command.getRecord().addColumn(0, "序号", "rowNumber");
        }
        command.getRecord().setValue("rowNumber", command.getRecord().getRecordIndex() + 1);
        return invoker.invoke(command);
    }
}

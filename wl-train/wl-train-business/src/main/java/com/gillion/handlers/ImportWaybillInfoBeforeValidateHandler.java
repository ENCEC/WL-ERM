package com.gillion.handlers;

import com.gillion.ds.excel.input.model.NoMoreImportRecord;
import com.gillion.ds.inout.annotation.InoutHandlerFor;
import com.gillion.ds.inout.chain.ImportInvoker;
import com.gillion.ds.inout.chain.handler.ImportCommand;
import com.gillion.ds.inout.chain.handler.ImportHandler;
import com.gillion.ds.inout.chain.model.ImportRecord;
import com.gillion.ds.inout.model.HandlerType;
import com.gillion.model.entity.Employee;
import com.gillion.model.entity.WaybillRouteNode;
import com.gillion.model.querymodels.QWaybillInfo;
import com.gillion.remote.RemoteRouteService;
import com.gillion.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 */
@Component
@InoutHandlerFor(tag = "import_waybill_info", handlerType = HandlerType.BEFORE_IMPORT_VALIDATE)
@Slf4j
public class ImportWaybillInfoBeforeValidateHandler implements ImportHandler {

    private final EmployeeService employeeService;
    private final RemoteRouteService remoteRouteService;

    public ImportWaybillInfoBeforeValidateHandler(EmployeeService employeeService, RemoteRouteService remoteRouteService) {
        this.employeeService = employeeService;
        this.remoteRouteService = remoteRouteService;
    }

    @Override
    public ImportRecord handle(ImportInvoker invoker, ImportCommand command) {
        if (command.getRecord() instanceof NoMoreImportRecord) {
            return invoker.invoke(command);
        }
        final ImportRecord record = command.getRecord();
        setCollectEmployeeId(record);
        setWaybillRouteNodes(record);
        log.info("导入进行中");
        return invoker.invoke(command);
    }

    /**
     * 计算并设置路由节点信息
     *
     * @param record 导入记录
     */
    private void setWaybillRouteNodes(ImportRecord record) {
        final String sendAreaCode = record.getCellValue(QWaybillInfo.sendAreaCode);
        final String deliveryAreaCode = record.getCellValue(QWaybillInfo.deliveryAreaCode);
        final List<WaybillRouteNode> wbsRouteNodes = remoteRouteService.calculateRouteInfos(sendAreaCode, deliveryAreaCode);
        record.setValue("waybillRouteNodes", wbsRouteNodes);
    }

    /**
     * 查询并设置揽件员工编号
     *
     * @param record 导入的记录
     */
    private void setCollectEmployeeId(ImportRecord record) {
        final String cname = record.getCellValue(QWaybillInfo.collectEmployeeCname);
        final String mobile = record.getCellValue(QWaybillInfo.collectEmployeeMobile);
        final Employee collector = employeeService.findByCnameAndMobile(cname, mobile);
        if (collector == null) {
            record.setValue(QWaybillInfo.collectEmployeeId, 1L);
        } else {
            record.setValue(QWaybillInfo.collectEmployeeId, collector.getEmployeeId());
        }
    }
}

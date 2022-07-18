package com.gillion.service.impl;

import com.gillion.ds.api.Command;
import com.gillion.ds.api.IApiParam;
import com.gillion.ds.chain.annotation.RestfulApiHandlerFor;
import com.gillion.ds.chain.interfaces.Invoker;
import com.gillion.ds.chain.interfaces.Processor;
import com.gillion.ds.chain.model.processresult.ProcessResult;
import com.gillion.ds.exception.AbstractProcessorChainException;
import com.gillion.model.entity.Employee;
import com.gillion.model.entity.WaybillInfo;
import com.gillion.model.entity.WaybillRouteNode;
import com.gillion.remote.RemoteRouteService;
import com.gillion.service.EmployeeService;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 */
@Component
@RestfulApiHandlerFor("DS_QUICKSTART_WAYBILL_INFO_SAVE")
public class WaybillRouteUnitPersistProcessor implements Processor {

    private final EmployeeService employeeService;
    private final RemoteRouteService remoteRouteService;

    public WaybillRouteUnitPersistProcessor(EmployeeService employeeService, RemoteRouteService remoteRouteService) {
        this.employeeService = employeeService;
        this.remoteRouteService = remoteRouteService;
    }

    @Override
    public ProcessResult process(Invoker invoker, Command command) throws AbstractProcessorChainException {
        Validate.isTrue(command.getArgs().isObject(), "关联保存订单及其费用信息入参必须是对象");
        IApiParam iApiParam = command.getApiParam();
        final WaybillInfo waybillInfo = iApiParam.getReqArgs().asBean(WaybillInfo.class);
        setCollectEmployeeId(waybillInfo);
        setWaybillRouteNodes(waybillInfo);
        return invoker.invoke(command);
    }


    /**
     * 计算并设置路由节点信息
     *
     * @param record 导入记录
     */
    private void setWaybillRouteNodes(WaybillInfo record) {
        final String sendAreaCode = record.getSendAreaCode();
        final String deliveryAreaCode = record.getDeliveryAreaCode();
        Validate.notBlank(sendAreaCode, "订单的始发地信息不能为空");
        Validate.notBlank(deliveryAreaCode, "订单的目的地信息不能为空");
        final List<WaybillRouteNode> wbsRouteNodes = remoteRouteService.calculateRouteInfos(sendAreaCode, deliveryAreaCode);
//        record.setWaybillRouteNodes(wbsRouteNodes);
    }

    /**
     * 查询并设置揽件员工编号
     *
     * @param record 导入的记录
     */
    private void setCollectEmployeeId(WaybillInfo record) {
        final String cname = record.getCollectEmployeeCname();
        final String mobile = record.getCollectEmployeeMobile();
        Validate.notBlank(cname, "揽件人中文姓名不能为空");
        Validate.notBlank(mobile, "揽件人手机号码不能为空");
        final Employee collector = employeeService.findByCnameAndMobile(cname, mobile);
        Validate.notNull(collector, "姓名为: %s 且手机号码为: %s 的快递员不存在", cname, mobile);
        record.setCollectEmployeeId(collector.getEmployeeId());
    }
}

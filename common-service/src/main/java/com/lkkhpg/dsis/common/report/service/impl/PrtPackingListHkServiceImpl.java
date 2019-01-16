/*
 *
 */
package com.lkkhpg.dsis.common.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.StringUtil;
import com.lkkhpg.dsis.common.report.dto.PackingListHkSalesHeader;
import com.lkkhpg.dsis.common.report.dto.PackingListHkSalesLine;
import com.lkkhpg.dsis.common.report.dto.PackingListHkShipment;
import com.lkkhpg.dsis.common.report.mapper.PrtPackingListHkMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;

/**
 * PACKING-LIST-HK.
 * 
 * @author zhenyang.he
 *
 */
@Service("prtPackingListHkService")
public class PrtPackingListHkServiceImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(PrtPackingListHkServiceImpl.class);

    @Autowired
    private PrtPackingListHkMapper packListHkMapper;

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        debugMsg("----------------Start process in PrtPackingListHkServiceImpl---------------");
        Map<String, Object> result = new HashMap<>();
        String orderNumber = (String) paramMap.get("orderNumber");
        if (StringUtil.isEmpty(orderNumber)) {
            return result;
        }
        debugMsg("----------------OrderNUmber is {}--------------------=", orderNumber);
        // shipmentNo
        List<PackingListHkShipment> shipmentNos = packListHkMapper.queryShipmentNo(orderNumber);
        // 订单头数据
        PackingListHkSalesHeader header = packListHkMapper.queryHeader(orderNumber);
        // 订单行数据
        List<PackingListHkSalesLine> lines = packListHkMapper.queryLine(orderNumber);

        int index = 0;
        BigDecimal totalqty;
        // shipmentQty 汇总
        BigDecimal totalShipmentQty = BigDecimal.ZERO;
        // NotShippedQty 汇总
        BigDecimal totalNotShippedQty = BigDecimal.ZERO;

        List<List<PackingListHkSalesLine>> pageList = new ArrayList<>();
        List<PackingListHkSalesLine> tmpList = new ArrayList<>();

        for (PackingListHkSalesLine line : lines) {
            index++;
            if (line.getShipmentQty() != null) {
                totalShipmentQty.add(line.getShipmentQty());
            } else {
                totalShipmentQty.add(BigDecimal.ZERO);
            }
            if(line.getNotShippedQty() !=null){
                totalNotShippedQty.add(line.getNotShippedQty());
            }else{
                totalNotShippedQty.add(BigDecimal.ZERO);
            }
            PackingListHkSalesLine ln = new PackingListHkSalesLine();
            ln.setItemName(line.getItemName());
            ln.setItemNumber(line.getItemNumber());
            ln.setInventory(line.getInventory());
            ln.setNotShippedQty(line.getNotShippedQty());
            ln.setQty(line.getQty());
            ln.setShipmentQty(line.getShipmentQty());
            ln.setTotalNotShippedQty(totalNotShippedQty);
            ln.setTotalShipmentQty(totalShipmentQty);
            tmpList.add(ln);
            if (index == 16) {
                List<PackingListHkSalesLine> list = new ArrayList<>();
                list.addAll(tmpList);
                pageList.add(list);
                tmpList.clear();
                index = 0;
                totalNotShippedQty = BigDecimal.ZERO;
                totalShipmentQty = BigDecimal.ZERO;
            }
        }
        if (tmpList.size() > 0) {
            pageList.add(tmpList);
        }

        result.put("header", header);
        debugMsg("----------------distributorNo is {}------------------", header.getDistributorNo());
        result.put("lines", pageList);
        debugMsg("----------------line is  {}---------------------------", lines.size());
        result.put("shipmentNos", shipmentNos);
        debugMsg("----------------shipment No. is {}---------------------", shipmentNos.size());
        return result;
    }

    private void debugMsg(String msg, Object... args) {
        if (logger.isDebugEnabled()) {
            if (args != null && args.length > 0) {
                logger.debug(msg, args);
            } else {
                logger.debug(msg);
            }
        }
    }

}

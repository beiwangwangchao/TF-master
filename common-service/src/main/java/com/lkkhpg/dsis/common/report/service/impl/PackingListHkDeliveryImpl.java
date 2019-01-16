/*
 *
 */
package com.lkkhpg.dsis.common.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.report.dto.PackingListDeliveryHeader;
import com.lkkhpg.dsis.common.report.dto.PackingListDeliveryLine;
import com.lkkhpg.dsis.common.report.dto.PackingListDeliverys;
import com.lkkhpg.dsis.common.report.mapper.PrtPackingListHkMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;

/**
 * PACKING-LIST-HK 打印清单.
 * 
 * @author zhenyang.he
 *
 */
@Service("PrintDeliveryRecordService")
public class PackingListHkDeliveryImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(PackingListHkDeliveryImpl.class);

    @Autowired
    private PrtPackingListHkMapper packingListMapper;

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        debugMsg("---------------Start process in prtPackingListHkDeliberyService----------");
        Map<String, Object> result = new HashMap<>();
        String delivery = (String) paramMap.get("deliveryId");
        List<String> deliveryList = new ArrayList<String>();
        Map map = new HashMap<>();
        if (delivery.equals(null)) {
            return result;
        } else {
            Collections.addAll(deliveryList, delivery.split(","));
        }
        map.put("deliveryList", deliveryList);
        debugMsg("-----------------deliveryId is {}", delivery);
        // 发运头
        List<PackingListDeliveryHeader> headers = packingListMapper.queryDeliveryHeader(map);
        List<List<List<PackingListDeliverys>>> pageAll = new ArrayList<>();        
        for (PackingListDeliveryHeader head : headers) {
            // 发运行
            List<PackingListDeliveryLine> lines = packingListMapper.queryDeliveryLine(head.getDeliveryId());
            PackingListDeliveryLine pages =  packingListMapper.queryPage(head.getDeliveryId());
            BigDecimal totalShipmentQty = BigDecimal.ZERO;
            BigDecimal totalNotShippedQty = BigDecimal.ZERO;
            int index = 0;
            List<List<PackingListDeliverys>> pageList = new ArrayList<>();
            List<PackingListDeliverys> tmpList = new ArrayList<>();
            PackingListDeliveryLine lns = new PackingListDeliveryLine();
            for (PackingListDeliveryLine line : lines) {
                index++;
                PackingListDeliverys hl = new PackingListDeliverys();
                // 在每一条行记录上添加上头的记录
                hl.setInventory(head.getInventory());
                hl.setDistributorName(head.getDistributorName());
                hl.setDistributorNo(head.getDistributorNo());
                hl.setReceiptNo(head.getReceiptNo());
                hl.setShipmentNo(head.getShipmentNo());
                hl.setDeliveryDate(head.getDeliveryDate());
                // 补充行记录
                if (line.getShipmentQty() != null) {
                    totalShipmentQty = totalShipmentQty.add(line.getShipmentQty());
                } else {
                    line.setShipmentQty(BigDecimal.ZERO);
                }
                if (line.getnotShippedQty() != null) {
                    totalNotShippedQty = totalNotShippedQty.add(line.getnotShippedQty());
                } else {
                    line.setnotShippedQty(BigDecimal.ZERO);
                }
                hl.setLineId(line.getLineId());
                hl.setLotNumber(line.getLotNumber());
                hl.setExpiryDate(line.getExpiryDate());
                hl.setShipmentQty(line.getShipmentQty());
                hl.setTotalShipmentQty(totalShipmentQty);
                hl.setTotalNotShippedQty(totalNotShippedQty);
                hl.setPage(line.getPage());
                hl.setPages(pages.getPages());
                if (line.getLineId().equals(lns.getLineId())) {
                    hl.setProductCode(null);
                    hl.setPackageCode(null);
                    hl.setDesctiption(null);
                    hl.setQty(null);                    
                    hl.setNotShippedQty(null);
                    hl.setFlag("N");
                } else {
                    lns.setLineId(line.getLineId());
                    hl.setProductCode(line.getProductCode());
                    hl.setPackageCode(line.getPackageCode());
                    hl.setDesctiption(line.getDesctiption());
                    hl.setQty(line.getQty());
                    hl.setNotShippedQty(line.getnotShippedQty());
                    hl.setFlag("Y");
                }
                tmpList.add(hl);
                if (index == 15) {
                    List<PackingListDeliverys> list = new ArrayList<>();
                    list.addAll(tmpList);
                    pageList.add(list);
                    index = 0;
                    tmpList.clear();
                    totalNotShippedQty = BigDecimal.ZERO;
                    totalShipmentQty = BigDecimal.ZERO;
                }
            }
            if (tmpList.size() > 0) {
                pageList.add(tmpList);
            }
            pageAll.add(pageList);           
        }
        result.put("lines", pageAll);
       // result.put("pages", pageAll.size());
        debugMsg("-----------------lines is {}", pageAll.size());
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

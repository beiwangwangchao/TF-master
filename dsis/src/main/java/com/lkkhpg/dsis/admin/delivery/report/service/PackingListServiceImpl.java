/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.report.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.delivery.service.IDeliveryService;
import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;
import com.lkkhpg.dsis.common.delivery.dto.OrderDeliveryLine;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;

/**
 * HK packing list data service.
 * @author chenjingxiong
 */
@Service("packingListService1")
public class PackingListServiceImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(PackingListServiceImpl.class);
    
    @Autowired
    private IDeliveryService deliveryService;
    
    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        debugMsg("====================start process in PackingListServiceImpl======================");
        Map<String, Object> result = new HashMap<>();
        String deliveryIdStr = (String) paramMap.get("deliveryId");
        if (StringUtils.isEmpty(deliveryIdStr)) {
            return result;
        }
        Long deliveryId = Long.parseLong(deliveryIdStr);
        
        debugMsg("====================deliveryId is {}======================", deliveryId);
        
        OrderDelivery head = deliveryService.getDeliveryDetails(request,deliveryId);
        List<OrderDeliveryLine> lines = deliveryService.getDeliveryDetailsLine(request, deliveryId);
        
        BigDecimal totalUnShippedQuantity = BigDecimal.ZERO;
        BigDecimal totalShippedQuantity = BigDecimal.ZERO;
        for (OrderDeliveryLine line : lines) {
            totalUnShippedQuantity = totalUnShippedQuantity.add(line.getUnShippedQuantity());
            totalShippedQuantity = totalShippedQuantity.add(line.getOutstandingQty());
        }
        
        result.put("head", head);
        debugMsg("====================head is {}======================", head.getDeliveryNumber());
        result.put("lines", lines);
        debugMsg("====================line is {}======================", lines.size());

        result.put("totalUnShippedQuantity", totalUnShippedQuantity);
        result.put("totalShippedQuantity", totalShippedQuantity);

        debugMsg("====================end process in PackingListServiceImpl======================");
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

/*
 *
 */
package com.lkkhpg.dsis.common.report.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.report.dto.PackingListDeliveryHeader;
import com.lkkhpg.dsis.common.report.dto.PackingListDeliveryLine;
import com.lkkhpg.dsis.common.report.dto.PackingListHkSalesHeader;
import com.lkkhpg.dsis.common.report.dto.PackingListHkSalesLine;
import com.lkkhpg.dsis.common.report.dto.PackingListHkShipment;

/**
 * PACKING-LIST-HK mapper.
 * 
 * @author zhenyang.he
 *
 */
public interface PrtPackingListHkMapper {
    
    List<PackingListHkShipment> queryShipmentNo(String orderNumber);
    
    PackingListHkSalesHeader queryHeader(String orderNumber);
    
    List<PackingListHkSalesLine> queryLine(String orderNumber);
    
    List<PackingListDeliveryHeader> queryDeliveryHeader(Map deliverys);
    
    List<PackingListDeliveryLine> queryDeliveryLine(Long deliveryId);
    
    PackingListDeliveryLine queryPage(Long deliveryId);
}

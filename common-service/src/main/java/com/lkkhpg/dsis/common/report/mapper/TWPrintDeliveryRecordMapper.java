package com.lkkhpg.dsis.common.report.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.report.dto.TWPrintDeliveryRecordHeader;
import com.lkkhpg.dsis.common.report.dto.TWPrintDeliveryRecordLine;

public interface TWPrintDeliveryRecordMapper {

    List<TWPrintDeliveryRecordLine> queryDeliveryLine(Long deliveryId);

    String getLoginName(Long accountId);

    List<TWPrintDeliveryRecordHeader> queryDeliveryHeader(Map deliveryId);

    TWPrintDeliveryRecordLine queryTotalQty(Long deliveryId);

    TWPrintDeliveryRecordLine queryPages(Long deliveryId);

}

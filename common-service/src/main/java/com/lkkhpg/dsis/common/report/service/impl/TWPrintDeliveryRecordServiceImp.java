package com.lkkhpg.dsis.common.report.service.impl;

/**
 *  打印清单 TWDELIVERY-LINE.
 * 
 * @author lipeng.lin
 *
 */
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.report.dto.PackingListDeliveryLine;
import com.lkkhpg.dsis.common.report.dto.TWPrintDeliveryRecodes;
import com.lkkhpg.dsis.common.report.dto.TWPrintDeliveryRecordHeader;
import com.lkkhpg.dsis.common.report.dto.TWPrintDeliveryRecordLine;
import com.lkkhpg.dsis.common.report.mapper.TWPrintDeliveryRecordMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.service.IAccountService;

@Service("twPrintDeliveryDataService")
public class TWPrintDeliveryRecordServiceImp implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(TWPrintDeliveryRecordServiceImp.class);
    @Autowired
    private TWPrintDeliveryRecordMapper twPrintDeliveryRecordMapper;
    @Autowired
    private IAccountService accountService;

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        debugMsg("====================start process in TWInvoiceDataServiceImpl======================");
        Map<String, Object> result = new HashMap<>();
        String deliveryIdStr = (String) paramMap.get("deliveryId");
        List<String> deliveryList = new ArrayList<String>();
        Map map = new HashMap<>();
        if (deliveryIdStr.equals(null)) {
            return result;
        } else {
            Collections.addAll(deliveryList, deliveryIdStr.split(","));
        }
        map.put("deliveryList", deliveryList);
        debugMsg("====================deliveryId is {}======================", deliveryIdStr);
        // 发运头
        List<TWPrintDeliveryRecordHeader> headers = twPrintDeliveryRecordMapper.queryDeliveryHeader(map);
        List<List<List<TWPrintDeliveryRecodes>>> pageAll = new ArrayList<>();
        for (TWPrintDeliveryRecordHeader header : headers) {

            // 发运行
            List<TWPrintDeliveryRecordLine> lines = twPrintDeliveryRecordMapper
                    .queryDeliveryLine(header.getDeliveryId());
            debugMsg("====================lines is {}======================",
                    lines.get(0).getRowNum() + "----" + lines.get(0).getProductDesctiption());
            BigDecimal totalShipmentQty = BigDecimal.ZERO;
            BigDecimal totalNoShippedQty = BigDecimal.ZERO;
            BigDecimal subTotalQty = BigDecimal.ZERO;
            BigDecimal totalQty = BigDecimal.ZERO;
            BigDecimal subTotalShipmentQty = BigDecimal.ZERO;
            BigDecimal subTotalNoShippedQty = BigDecimal.ZERO;
            TWPrintDeliveryRecordLine pages = twPrintDeliveryRecordMapper.queryPages(header.getDeliveryId());
            int index = 0;
            List<List<TWPrintDeliveryRecodes>> pageList = new ArrayList<>();
            List<TWPrintDeliveryRecodes> tmpList = new ArrayList<>();

            TWPrintDeliveryRecordLine lns = new TWPrintDeliveryRecordLine();
            // 临时变量用于判断 DeliveryID是否与上一个DeliveryID一样
            String deliveryID = null;
            String firstFlag = "Y";// 第一次运行的标志
            for (TWPrintDeliveryRecordLine line : lines) {
                index++;
                TWPrintDeliveryRecodes hl = new TWPrintDeliveryRecodes();
                // 获取头数据
                hl.setDeliveryNumber(header.getDeliveryNumber());
                hl.setOrderNumber(header.getOrderNumber());
                hl.setInvoiceNumber(header.getInvoiceNumber());
                hl.setInventory(header.getInventory());
                hl.setSaleOrderType(header.getSaleOrderType());
                hl.setDeliveryType(header.getDeliveryType());
                hl.setDeliveryDate(header.getDeliveryDate());
                hl.setDistributorName(header.getDistributorName());
                hl.setContactName(header.getContactName());
                hl.setPhone(header.getPhone());
                hl.setAddress(header.getAddress());
                hl.setRemark(header.getRemark());

                // 行记录获取

                if (firstFlag.equals("Y")) {
                    deliveryID = line.getLineId();// 把第一条数据赋值给deliveryID
                    if (line.getNoShipmentQty() != null) {
                        totalNoShippedQty = line.getNoShipmentQty();
                    } else {
                        totalNoShippedQty = BigDecimal.ZERO;
                    }
                    if (line.getQty() != null) {
                        totalQty = line.getQty();
                    } else {
                        totalQty = BigDecimal.ZERO;
                    }
                    firstFlag = "N";
                }
                // 计算未发运的数量和订购量
                if (line.getLineId().equals(deliveryID)) {// 如果当前的deliveryID等于发运行LineId时,将当前的LineId赋值deliveryID
                    deliveryID = line.getLineId();
                } else {// 否则的话，即前后的deliveryID的不同，把未完成的数量加进来
                    deliveryID = line.getLineId();
                    if (line.getNoShipmentQty() != null) {
                        totalNoShippedQty = totalNoShippedQty.add(line.getNoShipmentQty());
                    } else {
                        line.setNoShipmentQty(BigDecimal.ZERO);
                    }
                    if (line.getQty() != null) {// 订购量
                        totalQty = totalQty.add(line.getQty());
                    } else {
                        line.setTotalQty(BigDecimal.ZERO);
                    }
                }
                // 计算已发运的数量
                if (line.getShipmentQty() != null) {
                    totalShipmentQty = totalShipmentQty.add(line.getShipmentQty());
                } else {
                    line.setShipmentQty(BigDecimal.ZERO);
                }

                hl.setRowNum(line.getRowNum());
                hl.setLineId(line.getLineId());
                hl.setLotNumber(line.getLotNumber());
                hl.setExpiryDate(line.getExpiryDate());
                hl.setShipmentQty(line.getShipmentQty());
                hl.setTotalShipmentQty(totalShipmentQty);
                hl.setTotalNoShippedQty(totalNoShippedQty);
                hl.setPage(line.getPage());// 当前页码
                hl.setPages(pages.getPages());// 总页码
                debugMsg("-----------------PackCode is {}" + line.getPackCode());
                if (line.getLineId().equals(lns.getLineId())) {
                    hl.setProductCode(null);
                    hl.setProductDesctiption(null);
                    hl.setQty(null);
                    hl.setNoShipmentQty(null);
                    hl.setPackCode(null);
                    hl.setFlag("N");
                    // 页小计（当发运行id相同时不进行相加）
                    subTotalQty = subTotalQty.add(BigDecimal.ZERO);
                    subTotalShipmentQty = subTotalShipmentQty.add(line.getShipmentQty());
                    subTotalNoShippedQty = subTotalNoShippedQty.add(BigDecimal.ZERO);
                    hl.setSubTotalQty(subTotalQty);
                    hl.setSubTotalShipmentQty(subTotalShipmentQty);
                    hl.setSubTotalNoShippedQty(subTotalNoShippedQty);
                } else {
                    lns.setLineId(line.getLineId());
                    hl.setProductCode(line.getProductCode());
                    hl.setProductDesctiption(line.getProductDesctiption());
                    hl.setQty(line.getQty());
                    hl.setNoShipmentQty(line.getNoShipmentQty());
                    hl.setPackCode(line.getPackCode());
                    hl.setFlag("Y");
                    // 页小计
                    subTotalQty = subTotalQty.add(line.getQty());
                    subTotalShipmentQty = subTotalShipmentQty.add(line.getShipmentQty());
                    subTotalNoShippedQty = subTotalNoShippedQty.add(line.getNoShipmentQty());
                    hl.setSubTotalQty(subTotalQty);
                    hl.setSubTotalShipmentQty(subTotalShipmentQty);
                    hl.setSubTotalNoShippedQty(subTotalNoShippedQty);

                }
                tmpList.add(hl);

                if (index == 8) {
                    List<TWPrintDeliveryRecodes> list = new ArrayList<>();
                    list.addAll(tmpList);
                    pageList.add(list);
                    index = 0;
                    tmpList.clear();
                    subTotalQty = BigDecimal.ZERO;
                    subTotalShipmentQty = BigDecimal.ZERO;
                    subTotalNoShippedQty = BigDecimal.ZERO;
                }

            }
            if (tmpList.size() > 0) {
                pageList.add(tmpList);
            }
            pageAll.add(pageList);
            // 汇总（页总计）
            TWPrintDeliveryRecordLine totalList = twPrintDeliveryRecordMapper.queryTotalQty(header.getDeliveryId());
            if (totalList != null) {
                BigDecimal totalShipmentQty1 = totalList.getTotalShipmentQty();
                result.put("totalShipmentQty", totalShipmentQty1.toString());
            }
            BigDecimal totalNoShipmentQty1 = totalNoShippedQty;
            result.put("totalNoShipmentQty", totalNoShipmentQty1.toString());
            result.put("totalQty", totalQty.toString());
        }

        // 总页码数
        result.put("Detail", pageAll);
        // 打印时间
        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA);
        String dt = df.format(date);
        result.put("printDate", dt);

        // 获取指定用户名
        Account account = accountService.getAccountByAccountId(request.getAccountId());
        String username = "";
        if (account != null) {
            username = account.getLoginName();
        }
        result.put("username", username);
        debugMsg("=================end process in TWInvoiceDataServiceImpl======================");

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

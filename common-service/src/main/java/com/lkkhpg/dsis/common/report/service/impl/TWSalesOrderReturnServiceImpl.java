/*
 * #{copyright}#
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
import org.springframework.util.StringUtils;

import com.lkkhpg.dsis.common.report.dto.TWSalesOrderReturnHeader;
import com.lkkhpg.dsis.common.report.dto.TWSalesOrderReturnLine;
import com.lkkhpg.dsis.common.report.dto.TWSalesOrderReturnfoot;
import com.lkkhpg.dsis.common.report.mapper.TWSalesOrderReturnMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;

/**
 * 台湾营业人销货退回单报表实现类.
 * 
 * @author zhenyang.he
 *
 */
@Service("TWSalesOrderReturnService")
public class TWSalesOrderReturnServiceImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(TWSalesOrderReturnServiceImpl.class);

    @Autowired
    private TWSalesOrderReturnMapper salesOrderReturnMapper;

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {

        debugMsg("------------------------Start process in TWSalesOrderReturn print--------------------");
        Map<String, Object> result = new HashMap<>();
        String returnNumber = (String) paramMap.get("returnNumber");
        if (returnNumber == null) {
            return result;
        }
        debugMsg("------------------------return NUmber is {}----------------------------", returnNumber);
        // 退货头数据
        TWSalesOrderReturnHeader head = salesOrderReturnMapper.queryReturnHeader(returnNumber);
        // 会员信息
        TWSalesOrderReturnfoot foot = salesOrderReturnMapper.queryReturnfoot(returnNumber);
        // 退货行数据
        List<TWSalesOrderReturnLine> lines = salesOrderReturnMapper.queryReturnList(returnNumber);
        int index = 0;
        BigDecimal totalAmt = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        List<List<TWSalesOrderReturnLine>> pageList = new ArrayList<>();
        List<TWSalesOrderReturnLine> tmpList = new ArrayList<>();
        for (TWSalesOrderReturnLine line : lines) {
            if (line.getItemName() != null) {
                index++;
                TWSalesOrderReturnLine ln = new TWSalesOrderReturnLine();
                ln.setPayYear(line.getPayYear());
                ln.setPayMom(line.getPayMom());
                ln.setPayDay(line.getPayDay());
                ln.setZg(line.getZg());
                ln.setInvoiceNum(line.getInvoiceNum());
                ln.setItemName(line.getItemName());
                ln.setTaxable("V");
                // 数量
                ln.setQuantity(line.getQuantity());
                // 单价 = 销售价 / 税率
                ln.setPrice(line.getSalesPrice().divide(line.getTax(), 0));
                // 金额 = 数量 * 单价
                ln.setAmount(line.getQuantity().multiply(line.getSalesPrice().divide(line.getTax(), 0)));
                // 营业税额 = 销售价 - 单价
                ln.setTaxAmount(line.getQuantity()
                        .multiply(line.getSalesPrice().subtract(line.getSalesPrice().divide(line.getTax(), 0))));
                if (ln.getAmount() != null) {
                    totalAmt = totalAmt.add(ln.getAmount());
                } else {
                    totalAmt = totalAmt.add(BigDecimal.ZERO);
                }
                if (ln.getTaxAmount() != null) {
                    totalTax = totalTax.add(ln.getTaxAmount());
                } else {
                    totalTax = totalTax.add(BigDecimal.ZERO);
                }
                ln.setTotalAmount(totalAmt);
                ln.setTotalTaxAmount(totalTax);
                tmpList.add(ln);
                if (index == 12) {
                    List<TWSalesOrderReturnLine> list = new ArrayList<>();
                    list.addAll(tmpList);
                    pageList.add(list);
                    tmpList.clear();
                    index = 0;
                }
            }
        }
        if (tmpList.size() > 0) {
            Long headerId = lines.get(0).getOrderHeaderId();
            List<TWSalesOrderReturnfoot> adlists = salesOrderReturnMapper.queryAdjustmentAmount(headerId);
            TWSalesOrderReturnfoot shippingfee = salesOrderReturnMapper.queryShippingFee(headerId);
            if (lines.get(0).getReturnAdjustFlag().equals("Y")) {
                for (TWSalesOrderReturnfoot adlist : adlists) {
                    TWSalesOrderReturnLine l = new TWSalesOrderReturnLine();
                    l.setPayYear(lines.get(0).getPayYear());
                    l.setPayMom(lines.get(0).getPayMom());
                    l.setPayDay(lines.get(0).getPayDay());
                    l.setZg(lines.get(0).getZg());
                    l.setInvoiceNum(lines.get(0).getInvoiceNum());
                    l.setItemName("人工調整");
                    l.setAmount(adlist.getAdjustmentAmount());
                    if (adlist.getAdjustmentAmount() != null) {
                        totalAmt = totalAmt.add(adlist.getAdjustmentAmount());
                    } else {
                        totalAmt = totalAmt.add(BigDecimal.ZERO);
                    }
                    l.setTotalAmount(totalAmt);
                    l.setTotalTaxAmount(totalTax);
                    tmpList.add(l);
                    if (tmpList.size() == 12) {
                        List<TWSalesOrderReturnLine> lists = new ArrayList<>();
                        lists.addAll(tmpList);
                        pageList.add(lists);
                        tmpList.clear();
                    }
                }
            }
            if (lines.get(0).getShippingFeeFlag().equals("Y")) {
                TWSalesOrderReturnLine rl = new TWSalesOrderReturnLine();
                rl.setPayYear(lines.get(0).getPayYear());
                rl.setPayMom(lines.get(0).getPayMom());
                rl.setPayDay(lines.get(0).getPayDay());
                rl.setZg(lines.get(0).getZg());
                rl.setInvoiceNum(lines.get(0).getInvoiceNum());
                rl.setItemName("運費");
                rl.setAmount(shippingfee.getShippingFee());
                if (shippingfee.getShippingFee() != null) {
                    totalAmt = totalAmt.add(shippingfee.getShippingFee());
                } else {
                    totalAmt = totalAmt.add(BigDecimal.ZERO);
                }
                rl.setTotalAmount(totalAmt);
                rl.setTotalTaxAmount(totalTax);
                tmpList.add(rl);
            }

            pageList.add(tmpList);
        }
        result.put("head", head);
        debugMsg("-------------------Company Name is {}---------------------------------", head.getCompanyName());
        result.put("foot", foot);
        debugMsg("-------------------member Name is {}-----------------------------------", foot.getMemberName());
        result.put("lines", pageList);
        debugMsg("-----------------------lines is {} -------------------------------------", pageList.size());
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

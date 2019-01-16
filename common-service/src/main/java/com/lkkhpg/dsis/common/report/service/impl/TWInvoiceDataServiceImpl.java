/*
 *
 */
package com.lkkhpg.dsis.common.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.common.order.mapper.OrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesAdjustmentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesVoucherMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.service.IAccountService;

/**
 * 台湾发票.
 * 
 * @author HuangJiaJing
 *
 */
@Service("twInvoiceDataService")
public class TWInvoiceDataServiceImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(TWInvoiceDataServiceImpl.class);

    /**
     * 支付方式-信用卡-POS.
     */
    public static final String PAYMENT_METHOD_CREDI = "CREDI";

    /**
     * 支付方式-借记卡-POS.
     */
    public static final String PAYMENT_METHOD_DBCRD = "DBCRD";

    /**
     * 人工調整.
     */
    public static final String STR = "調整";
    /**
     * 運費.
     */
    private static final String STR2 = "運費";

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private SpmLocationMapper locationMapper;

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    private String tailNumber;

    @Autowired
    private SalesVoucherMapper salesVoucherMapper;

    @Autowired
    private SalesAdjustmentMapper salesAdjustmentMapper;

    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;

    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private MemberMapper memberMapper;

    private final String SHIP = "SHIP";

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        debugMsg("====================start process in TWInvoiceDataServiceImpl======================");
        Map<String, Object> result = new HashMap<>();
        String headerIdStr = (String) paramMap.get("headerId");
        if (StringUtils.isEmpty(headerIdStr)) {
            return result;
        }
        Long headerId = Long.parseLong(headerIdStr);
        debugMsg("====================headerId is {}======================", headerId);
        // 根据订单头ID获取订单信息
        SalesOrder head = salesOrderMapper.selectSalesOrderByHeadId(headerId);
        // 根据订单的会员ID获取对应的会员信息
        if (head != null && head.getMemberId() != null) {
            SalesOrder salesOrder = salesOrderMapper.getBrNo(head.getMemberId());
            if (salesOrder != null && salesOrder.getMemberName() != null) {
                head.setMemberName(salesOrder.getMemberName());
            }
            if (salesOrder != null && salesOrder.getMemberCode() != null) {
                head.setMemberCode(salesOrder.getMemberCode());
            }
            if (salesOrder != null && salesOrder.getBrNo() != null) {
                head.setBrNo(salesOrder.getBrNo());
            }
            if (salesOrder != null && salesOrder.getGstId() != null) {
                head.setGstId(salesOrder.getGstId());
            }
        }
        // 檢查號碼
        String code = "";
        if (head.getInvoiceNumber() != null) {
            int len = head.getInvoiceNumber().length() - 5;
            code = head.getInvoiceNumber().substring(len);
        }
        // 取统一编号
        String uniformNumber = "";
        if (head.getMemberId() != null) {
            Member member = memberMapper.selectByPK(head.getMemberId());
            if ("COMP".equals(member.getMemberType())) {
                uniformNumber = member.getBrNumber();
            }
        }
        // 支付日期
        Date payDate = head.getPayDate();
        if (payDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(payDate);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            result.put("year", year - 1911);
            result.put("month", month + 1);
            result.put("day", day);
        }
        // 订单的收货人
        String shipTo = "";
        String address = "";
        List<SalesSites> salesSites2 = salesSitesMapper.selectSites(SHIP, headerId);
        if (!salesSites2.isEmpty()) {
            shipTo = salesSites2.get(0).getName();
            StringBuilder sb = new StringBuilder();
            sb.append(salesSites2.get(0).getStateName()).append(salesSites2.get(0).getCityName())
                    .append(salesSites2.get(0).getAddress1() == null ? "" : salesSites2.get(0).getAddress1())
                    .append(salesSites2.get(0).getAddress2() == null ? "" : salesSites2.get(0).getAddress2())
                    .append(salesSites2.get(0).getAddress2() == null ? "" : salesSites2.get(0).getAddress2());
            address = sb.toString();
        }
        // 金额合计tailNumber
        BigDecimal totalAmount = BigDecimal.ZERO;
        // 订单行
        List<SalesLine> lines = salesLineMapper.querySalesLineByHeaderId(paramMap);
        // 获取折扣
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(headerId);
        for (SalesVoucher salesVoucher : salesVouchers) {
            SalesLine salesLine = new SalesLine();
            // 优惠券编码
            salesLine.setItemNumber(salesVoucher.getVoucherCode());
            // 优惠券名称
            salesLine.setItemName(salesVoucher.getName());
            // 优惠券数量
            salesLine.setQuantity(new BigDecimal(1));
            // 优惠券金额
            if (salesVoucher.getDiscountValue() != null) {
                salesLine.setUnitSellingPrice(salesVoucher.getDiscountValue().negate());
                salesLine.setAmount(salesVoucher.getDiscountValue().negate());
            }
            lines.add(salesLine);
        }
        // 销售支付调整
        List<SalesAdjustment> salesAdjustments = salesAdjustmentMapper.selectByHeaderId(headerId);
        for (SalesAdjustment salesAdjustment : salesAdjustments) {
            SalesLine salesLine = new SalesLine();
            salesLine.setItemName(STR);
            salesLine.setAmount(salesAdjustment.getAdjustmentAmount());
            lines.add(salesLine);
        }
        // 运费
        SalesLogistics salesLogistics = salesLogisticsMapper.selectByHeaderId(headerId, "N");
        SalesLine salesLine = new SalesLine();
        if (salesLogistics != null) {
            salesLine.setItemName(STR2);
            salesLine.setAmount(salesLogistics.getShippingFee());
        }
        lines.add(salesLine);
        tailNumber = null;
        List<OrderPayment> orderPayments = orderPaymentMapper.queryTailnumber(headerId);
        for (OrderPayment orderPayment : orderPayments) {
            if (orderPayment != null) {
                if (PAYMENT_METHOD_DBCRD.equals(orderPayment.getPaymentMethod())
                        || PAYMENT_METHOD_CREDI.equals(orderPayment.getPaymentMethod())) {
                    tailNumber = orderPayment.getTailNumber();
                }
            }
        }
        // PV合计
        BigDecimal totalPV = BigDecimal.ZERO;
        int index = 0;
        List<List<SalesLine>> pageList = new ArrayList<>();
        List<SalesLine> tmpList = new ArrayList<>();
        for (SalesLine line : lines) {
            index++;
            // 金额
            if (line.getUnitSellingPrice() != null) {
                line.setAmount(line.getUnitSellingPrice().multiply(new BigDecimal(line.getQuantity().toString())));
            }
            // 小计积分
            BigDecimal pv = BigDecimal.ZERO;
            if (line.getPv() != null) {
                pv = line.getPv().multiply(new BigDecimal(line.getQuantity().toString()));
                totalPV = totalPV.add(pv);
            }
            if (line.getAmount() != null) {
                totalAmount = totalAmount.add(line.getAmount());
            }
            tmpList.add(line);

            if (index == 13) {
                List<SalesLine> list = new ArrayList<>();
                list.addAll(tmpList);
                pageList.add(list);
                tmpList.clear();
                index = 0;
            }
        }
        if (tmpList.size() > 0) {
            pageList.add(tmpList);
        }
        // 获取指定用户名
        Account account = accountService.getAccountByAccountId(request.getAccountId());
        String username = "";
        if (account != null) {
            username = account.getLoginName();
        }
        totalAmount = head.getActrualPayAmt();
        result.put("head", head);
        result.put("code", code);
        result.put("shipTo", shipTo);
        result.put("uniformNumber", uniformNumber);
        debugMsg("====================head is {}======================", head.getInvoiceNumber());
        result.put("lines", pageList);
        debugMsg("====================line is {}======================", lines.size());
        result.put("totalAmount", totalAmount);
        result.put("totalPV", totalPV);
        result.put("address", address);
        result.put("tailNumber", tailNumber);
        String totalNum = digitUppercase(totalAmount.doubleValue());
        result.put("totalNum", totalNum.toCharArray());
        result.put("username", username);
        debugMsg("====================end process in TWInvoiceDataServiceImpl======================");
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

    private static String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

    public String digitUppercase(double n) {
        String integerPart = (int) Math.floor(n) + "";
        int length = integerPart.length();
        StringBuilder sb = new StringBuilder();
        if (length < 8) {
            for (int i = 0; i < (8 - length); i++) {
                sb.append(digit[0]);
            }
        }

        for (char c : integerPart.toCharArray()) {
            sb.append(digit[c - 48]);
        }
        return sb.toString();
    }
}

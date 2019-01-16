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

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesReturn;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.common.order.mapper.OrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesAdjustmentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesVoucherMapper;
import com.lkkhpg.dsis.common.report.dto.SgReturnOrderHeader;
import com.lkkhpg.dsis.common.report.dto.SgReturnOrderLine;
import com.lkkhpg.dsis.common.report.mapper.SgReturnOrderMapper;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.IUserService;

/**
 * 新加坡退货单报表模板实现类.
 * 
 * @author zhenyang.he
 *
 */
@Service("sgReturnOderService")
public class SgReturnOrderServiceImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(SgReturnOrderServiceImpl.class);

    @Autowired
    private SgReturnOrderMapper returnOrderMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;

    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IParamService paramService;

    @Autowired
    private SpmTaxMapper spmTaxMapper;

    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;

    @Autowired
    private SalesAdjustmentMapper salesAdjustmentMapper;

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    @Autowired
    private SalesVoucherMapper salesVoucherMapper;

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {

        debugMsg("-----------------------------Start process in SgReturnOrderServiceImpl-----------------------------");
        Map<String, Object> result = new HashMap<>();
        String returnNumber = (String) paramMap.get("returnNumber");
        if (returnNumber == null) {
            return result;
        }
        debugMsg("-----------------------------returnNumber is {} -------------------------------------", returnNumber);
        debugMsg("----------------------------- start to head Data -----------------------------------------");
        // 返回信息头数据
        SgReturnOrderHeader head = new SgReturnOrderHeader();

        // 获取退货单头上的信息
        SalesReturn salesreturn = returnOrderMapper.queryRetrunSales(returnNumber);
        head.setReturnDate(salesreturn.getReturnDate());
        head.setReturnNumber(salesreturn.getReturnNumber());
        head.setCreditNote(salesreturn.getCreditNote());
        head.setRemark(salesreturn.getRemark());
        head.setTaxAmt(salesreturn.getTaxAmount());
        // 根据订单头获取会员信息
        SalesOrder salesOrder = salesOrderMapper.selectSalesOrderByHeadId(salesreturn.getOrderHeaderId());
        if (salesOrder != null && salesOrder.getMemberId() != null) {
            SalesOrder salesMem = salesOrderMapper.getBrNo(salesOrder.getMemberId());
            if (salesMem != null && salesMem.getMemberName() != null) {
                head.setMemberName(salesMem.getMemberName());
            }
            if (salesMem != null && salesMem.getMemberCode() != null) {
                head.setMemberCode(salesMem.getMemberCode());
            }
            if (salesMem != null && salesMem.getGstId() != null) {
                head.setGstId(salesMem.getGstId());
            }
        }

        // 获取奖金期间
        SpmPeriod spmPeriod = spmPeriodMapper.selectByPrimaryKey(salesOrder.getPeriodId());
        if (spmPeriod != null) {
            head.setPeriod(spmPeriod.getPeriodName());
        }

        // 获取收货人 ,收货地址, 收货电话
        List<SalesSites> salesSites = salesSitesMapper.selectSitesByHeaderIdAndSiteType("SHIP",
                salesreturn.getOrderHeaderId());
        if (!salesSites.isEmpty()) {
            head.setShipTo(salesSites.get(0).getName());
            head.setDeliveryAddress(salesSites.get(0).getAddress());
            head.setTelNo(salesSites.get(0).getPhone());
        }

        // 获取订单配送类型
        CodeValue codevale = codeService.getCodeValue(request, "DM.DELIVERY_TYPE", salesOrder.getDeliveryType());
        if (codevale != null) {
            head.setDeliveryType(codevale.getMeaning());
        }

        // 获取指定用户名
        Long userId = userService.getUserIdByAccountId(request.getAccountId());
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            head.setUserName(user.getUserName());
        }

        // 获取税码
        List<String> taxCodes = paramService.getParamValues(request, "SPM.TAX_CODE", "SALES", salesreturn.getSalesOrgId());

        // 获取Unit price before GST
        List<String> paramValues = paramService.getParamValues(request, "SPM.PRICE_INCLUDE_TAX", "SALES",
                salesreturn.getSalesOrgId());

        // 税码
        String taxCode = "";
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal tax2 = BigDecimal.ZERO;
        if (!taxCodes.isEmpty()) {
            taxCode = taxCodes.get(0);
        }

        // 根据税码获取税率
        SpmTax spmTax = spmTaxMapper.getTaxByCode(taxCode);
        if (spmTax != null) {
            tax = tax.add(spmTax.getTaxPercent());
            tax2 = tax2.add(spmTax.getTaxPercent().divide(new BigDecimal(100)));
        }

        // 获取行记录数据信息
        List<SgReturnOrderLine> lines = returnOrderMapper.queryReturnOrderLine(returnNumber);
        int index = 0;
        // PV合计
        BigDecimal totalPV = BigDecimal.ZERO;
        // 金额合计
        BigDecimal totalAmt = BigDecimal.ZERO;
        // 税合计
        BigDecimal totalGST = BigDecimal.ZERO;
        List<List<SgReturnOrderLine>> pageList = new ArrayList<>();
        List<SgReturnOrderLine> tmpList = new ArrayList<>();
        for (SgReturnOrderLine returnOrderline : lines) {
            index++;
            if (!paramValues.isEmpty()) {
                // 当组织参数SPM.PRICE_INCLUDE_TAX = Y
                // 取UNIT_ORIG_PRICE÷(1+税率)(通过组织参数税码SPM.TAX_CODE取税率)
                if ("Y".equals(paramValues.get(0))) {
                    if (tax.compareTo(BigDecimal.ZERO) > 0) {
                        returnOrderline.setUnitOrigPrice(returnOrderline.getUnitOrigPrice()
                                .divide(new BigDecimal(1).add(tax2), 3, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            returnOrderline.setAmount(returnOrderline.getUnitOrigPrice().multiply(returnOrderline.getQuantity()));
            if (tax.compareTo(BigDecimal.ZERO) == 1) {
                returnOrderline.setSgt(
                        returnOrderline.getQuantity().multiply(returnOrderline.getUnitOrigPrice().multiply(tax2)));
            } else {
                returnOrderline.setSgt(new BigDecimal(0.00));
            }
            totalPV = totalPV.add(returnOrderline.getPv().multiply(returnOrderline.getQuantity()));
            if (returnOrderline.getSgt() != null) {
                totalGST = totalGST.add(returnOrderline.getSgt());
            }
            tmpList.add(returnOrderline);
            if (index == 8) {
                List<SgReturnOrderLine> list = new ArrayList<>();
                list.addAll(tmpList);
                pageList.add(list);
                tmpList.clear();
                index = 0;
            }
        }
        if (tmpList.size() > 0) {
            pageList.add(tmpList);
        }
        totalAmt = totalAmt.add(salesreturn.getAmount().subtract(salesreturn.getTaxAmount()));

        // 获取运费
        BigDecimal shippingFee = BigDecimal.ZERO;
        BigDecimal shippingFeeBeforeTax = BigDecimal.ZERO;
        SalesLogistics salesLogistics = salesLogisticsMapper.selectByHeaderId(salesreturn.getOrderHeaderId(), "N");
        if (salesLogistics != null) {
            shippingFee = shippingFee.add(salesLogistics.getShippingFee());
        }
        if (tax.compareTo(BigDecimal.ZERO) == 1) {
            shippingFeeBeforeTax = shippingFeeBeforeTax
                    .add(shippingFee.divide(new BigDecimal(1).add(tax2), 3, BigDecimal.ROUND_HALF_UP));
        }

        // 获取优惠
        BigDecimal adjustment = BigDecimal.ZERO;
        List<SalesAdjustment> salesAdjustments = salesAdjustmentMapper.selectByHeaderId(salesreturn.getOrderHeaderId());
        for (SalesAdjustment salesAdjustment : salesAdjustments) {
            adjustment = adjustment.add(salesAdjustment.getAdjustmentAmount());
        }

        // 获取折扣
        BigDecimal voucher = BigDecimal.ZERO;
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(salesreturn.getOrderHeaderId());
        for (SalesVoucher salesVoucher : salesVouchers) {
            voucher = voucher.add(salesVoucher.getDiscountValue().negate());
        }
        BigDecimal Total = shippingFeeBeforeTax.add(totalAmt);
        if (!tax.equals(null)) {
            head.setTax(tax);
        } else {
            head.setTax(BigDecimal.ZERO);
        }
        head.setTotalPV(totalPV);
        head.setTotalGST(totalGST);
        head.setTotalAmt(totalAmt);
        head.setTotal(Total);
        head.setTotalTax(head.getTotal().multiply(tax).divide(new BigDecimal(100)).setScale(3, BigDecimal.ROUND_HALF_UP));        
        head.setVoucher(voucher);
        if (salesreturn.getShippingFeeFlag().equals("Y")) {
            head.setShippingFeeBeforeTax(shippingFeeBeforeTax);
        } else {
            head.setShippingFeeBeforeTax(BigDecimal.ZERO);
        }
        if (salesreturn.getReturnAdjustFlag().equals("Y")) {
            head.setAdjustment(adjustment);
        } else {
            head.setAdjustment(BigDecimal.ZERO);
        }
        if(head.getShippingFeeBeforeTax() == null){
            head.setShippingFeeTax(BigDecimal.ZERO);
        }else{            
            head.setShippingFeeTax(head.getShippingFeeBeforeTax().multiply(tax).divide(new BigDecimal(100)).setScale(3, BigDecimal.ROUND_HALF_UP));
        }
        head.setTotalAfterTax(salesreturn.getAmount().add(shippingFee));
        head.setTotalactualPayAmount(salesreturn.getActualPayAmount());
        // 支付方式合计金额
        List<OrderPayment> orderPayments = orderPaymentMapper
                .selectPayMethodAmtByHeaderId(salesreturn.getOrderHeaderId());
        for (OrderPayment orderPayment : orderPayments) {
            CodeValue codevalue = codeService.getCodeValue(request, "OM.PAY_METHOD_MODIFY",
                    orderPayment.getPaymentMethod());
            if (codevalue != null) {
                orderPayment.setPaymentMethod(codevalue.getMeaning());
            }
            CodeValue codevalue2 = codeService.getCodeValue(request, "OM.PAY_METHOD_FIXED",
                    orderPayment.getPaymentMethod());
            if (codevalue2 != null) {
                orderPayment.setPaymentMethod(codevalue2.getMeaning());
            }
        }

        result.put("head", head);
        result.put("lines", pageList);
        result.put("paymentMethed", orderPayments);
        debugMsg("---------------------------------lines is {}-------------------------------------", pageList.size());
        debugMsg("---------------------------------end to SgReturnOrderServiceImpl-------------------------");
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

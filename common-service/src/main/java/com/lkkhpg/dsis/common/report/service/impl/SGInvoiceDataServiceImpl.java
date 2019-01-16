/*
 *
 */
package com.lkkhpg.dsis.common.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
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
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.IUserService;

/**
 * 新加坡发票模板.
 * 
 * @author huanghanrui
 */

@Service("sgInvoiceDataService")
public class SGInvoiceDataServiceImpl implements IReportDataService {

    private static Logger logger = LoggerFactory.getLogger(SGInvoiceDataServiceImpl.class);

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private IUserService iUserService;

    @Autowired
    private SalesAdjustmentMapper salesAdjustmentMapper;

    @Autowired
    private SalesVoucherMapper salesVoucherMapper;

    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;

    @Autowired
    private IParamService paramService;

    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;

    @Autowired
    private SpmTaxMapper spmTaxMapper;
    
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private ICodeService codeService;

    private final String SALES = "SALES";

    private final String PRICE_INCLUDE_TAX = "SPM.PRICE_INCLUDE_TAX";

    private final String TAX_CODE = "SPM.TAX_CODE";

    private final String BILL = "BILL";

    private final String SHIP = "SHIP";

    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        debugMsg("====================start process in MLInvoiceDataServiceImpl======================");
        Map<String, Object> result = new HashMap<>();
        String headerIdStr = (String) paramMap.get("headerId");
        if (StringUtils.isEmpty(headerIdStr)) {
            return result;
        }
        Long headerId = Long.parseLong(headerIdStr);
        // Long orderId = (Long) paramMap.get("orderId");
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
        // 获取奖金期间
        SpmPeriod spmPeriod = spmPeriodMapper.selectByPrimaryKey(head.getPeriodId());
        String period = "";
        if (spmPeriod != null) {
            period = spmPeriod.getPeriodName();
        }
        // 获取Unit price before GST
        List<String> paramValues = paramService.getParamValues(request, PRICE_INCLUDE_TAX, SALES, head.getSalesOrgId());
        // 获取税码
        List<String> taxCodes = paramService.getParamValues(request, TAX_CODE, SALES, head.getSalesOrgId());
        
        
        // 获取订单人和订单地址
        String billingTo = "";
        String billingToCAN = "";
        // 订单地址
        String billingAdress = "";
        String billingAdressToCAN = "";
        List<SalesSites> salesSites = salesSitesMapper.selectSitesByHeaderIdAndSiteType(BILL, headerId);
        if (!salesSites.isEmpty()) {
            billingTo = salesSites.get(0).getName();
            billingAdress = salesSites.get(0).getAddress();
        }
        List<SalesSites> salesSitesMY = salesSitesMapper.selectSitesByHeaderIdAndSiteTypeCAN(BILL, headerId);
        if (!salesSitesMY.isEmpty()) {
            billingToCAN = salesSitesMY.get(0).getName();
            StringBuilder sb = new StringBuilder();
            sb.append(salesSitesMY.get(0).getAddress1() == null ? "" : salesSitesMY.get(0).getAddress1() + ",")
                    .append(salesSitesMY.get(0).getAddress2() == null ? "" : salesSitesMY.get(0).getAddress2() + ",")
                    .append(salesSitesMY.get(0).getAddress3() == null ? "" : salesSitesMY.get(0).getAddress3() + ",")
                    .append(salesSitesMY.get(0).getZipCode() == null ? "" : salesSitesMY.get(0).getZipCode() + ",")
                    .append(salesSitesMY.get(0).getCityName()).append(",")
                    .append(salesSitesMY.get(0).getCountryName());
            billingAdressToCAN = sb.toString();
        }
        
        
        // 收货人和收货地址,电话
        // 订单的收货人
        String shipTo = "";
        String shipToCAN = "";
        // 订单的收货地址
        String deliveryAdress = "";
        String deliveryAdressToCAN = "";
        // 电话
        String telNo = "";
        String telNoToCAN = "";
        List<SalesSites> salesSites2 = salesSitesMapper.selectSitesByHeaderIdAndSiteType(SHIP, headerId);
        if (!salesSites2.isEmpty()) {
            shipTo = salesSites2.get(0).getName();
            deliveryAdress = salesSites2.get(0).getAddress();
            telNo = salesSites2.get(0).getPhone();
        }
        List<SalesSites> salesSitesMY2 = salesSitesMapper.selectSitesByHeaderIdAndSiteTypeCAN(SHIP, headerId);
        if (!salesSitesMY2.isEmpty()) {
            shipToCAN = salesSitesMY2.get(0).getName();
            StringBuilder sb = new StringBuilder();
            sb.append(salesSitesMY2.get(0).getAddress1() == null ? "" : salesSitesMY2.get(0).getAddress1() + ",")
                    .append(salesSitesMY2.get(0).getAddress2() == null ? "" : salesSitesMY2.get(0).getAddress2() + ",")
                    .append(salesSitesMY2.get(0).getAddress3() == null ? "" : salesSitesMY2.get(0).getAddress3() + ",")
                    .append(salesSitesMY2.get(0).getZipCode() == null ? "" : salesSitesMY2.get(0).getZipCode() + ",")
                    .append(salesSitesMY2.get(0).getCityName()).append(",")
                    .append(salesSitesMY2.get(0).getCountryName());
            deliveryAdressToCAN = sb.toString();
            telNoToCAN = salesSitesMY2.get(0).getPhone();
        }
        
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal tax2 = BigDecimal.ZERO;
        // 税码
        String taxCode = "";
        if (!taxCodes.isEmpty()) {
            taxCode = taxCodes.get(0);
        }
        // 根据税码获取税率
        SpmTax spmTax = spmTaxMapper.getTaxByCode(taxCode);
        if (spmTax != null) {
            tax = tax.add(spmTax.getTaxPercent());
            tax2 = tax2.add(spmTax.getTaxPercent().divide(new BigDecimal(100)));
        }
        List<SalesLine> lines = salesLineMapper.querySalesLineByHeaderId(paramMap);
        //币种符号
        String curr = "";
        
        // 税合计
        BigDecimal totalGST = BigDecimal.ZERO;
        
        //当商品code为15160502时计算其7%的税.
        BigDecimal totaltaxforitem = BigDecimal.ZERO;
        
        if(lines.size() > 0){
            curr = lines.get(0).getCurrency();
        }
        for (SalesLine salesLine : lines) {
            if (paramValues != null) {
                // 当组织参数SPM.PRICE_INCLUDE_TAX=Y
                // 取UNIT_ORIG_PRICE÷（1+税率）（通过组织参数税码SPM.TAX_CODE取税率）
                if ("Y".equals(paramValues.get(0))) {
                    if (tax.compareTo(BigDecimal.ZERO) > 0) {
                        salesLine.setUnitOrigPrice(salesLine.getUnitOrigPrice().divide(new BigDecimal(1).add(tax2), 3,
                                BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            salesLine.setAmount(salesLine.getUnitOrigPrice().multiply(salesLine.getQuantity()));
            salesLine.setAmount1(salesLine.getUnitSellingPrice().multiply(salesLine.getQuantity()));
            if (tax.compareTo(BigDecimal.ZERO) == 1) {
                salesLine.setSgt(salesLine.getQuantity().multiply(salesLine.getUnitOrigPrice().multiply(tax2).setScale(3, BigDecimal.ROUND_HALF_UP)));
            } else {
                salesLine.setSgt(new BigDecimal(0.00));
            }
            totalGST = totalGST.add(salesLine.getSgt());
            //判断该商品为15160502
            SpmSalesOrganization sso = spmSalesOrganizationMapper.selectByPrimaryKey(salesLine.getSalesOrgId());
            if(salesLine.getItemNumber().equals("15160502") && sso.getCode().equals("SBC")){
                totaltaxforitem = totaltaxforitem.add(salesLine.getAmount().multiply(new BigDecimal(7)).divide(new BigDecimal(100)));
            }
            
        }
        // 支付方式合计金额
        List<OrderPayment> orderPayments = orderPaymentMapper.selectPayMethodAmtByHeaderId(headerId);
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
        // 获取优惠
        List<SalesAdjustment> salesAdjustments = salesAdjustmentMapper.selectByHeaderId(headerId);
        BigDecimal adjustment = BigDecimal.ZERO;
        for (SalesAdjustment salesAdjustment : salesAdjustments) {
            adjustment = adjustment.add(salesAdjustment.getAdjustmentAmount());
        }
        // 获取折扣
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(headerId);
        BigDecimal voucher = BigDecimal.ZERO;
        for (SalesVoucher salesVoucher : salesVouchers) {
            voucher = voucher.add(salesVoucher.getDiscountValue().negate());
        }
        // 获取运费
        BigDecimal shippingFee = BigDecimal.ZERO;
        BigDecimal shippingFee1 = BigDecimal.ZERO;
        SalesLogistics salesLogistics = salesLogisticsMapper.selectByHeaderId(headerId, "N");
        if (salesLogistics != null) {
            shippingFee = shippingFee.add(salesLogistics.getShippingFee());
            shippingFee1 = shippingFee1.add(salesLogistics.getShippingFee());
        }
        // 获取指定用户名
        //Account account = accountService.getAccountByAccountId(request.getAccountId());
        Long userId = iUserService.getUserIdByAccountId(request.getAccountId());
        User user = userMapper.selectByPrimaryKey(userId);
        String username = "";
        if (user != null) {
            username = user.getUserName();
        }
        // 获取订单配送类型
        CodeValue codevalue = codeService.getCodeValue(request, "DM.DELIVERY_TYPE", head.getDeliveryType());
        if (codevalue != null) {
            head.setDeliveryType(codevalue.getMeaning());
        }
        // PV合计
        BigDecimal totalPV = BigDecimal.ZERO;
        // 金额合计
        BigDecimal totalAmt = BigDecimal.ZERO;
        
        int index = 0;
        List<List<SalesLine>> pageList = new ArrayList<>();
        List<SalesLine> tmpList = new ArrayList<>();
        for (SalesLine line : lines) {
            index++;
            totalPV = totalPV.add(line.getPv().multiply(line.getQuantity()));
           /* if (line.getSgt() != null) {
                totalGST = totalGST.add(line.getSgt());
            }*/
            tmpList.add(line);
            if (index == 8) {
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
        totalAmt = totalAmt.add(head.getOrderAmt().subtract(head.getTaxAmt()));
        BigDecimal shippingFeeBeforeTax = BigDecimal.ZERO;
        if (tax.compareTo(BigDecimal.ZERO) == 1) {
            shippingFeeBeforeTax = shippingFeeBeforeTax
                    .add(shippingFee.divide(new BigDecimal(1).add(tax2), 3, BigDecimal.ROUND_HALF_UP));
        }
        BigDecimal Total = shippingFeeBeforeTax.add(totalAmt);
        // GST/HST值
        BigDecimal gsthst = BigDecimal.ZERO;
        String gsthstStr = "";
        String pstqstStr = "";
        // (加拿大)5% GST取值（BC-GST）
        BigDecimal gst = BigDecimal.ZERO;
        StringBuffer gstStr = new StringBuffer(taxCode);
        gstStr.append("-GST");
        // (加拿大)5% HST取值（BC-HST）
        BigDecimal hst = BigDecimal.ZERO;
        StringBuffer hstStr = new StringBuffer(taxCode);
        hstStr.append("-HST");
        // (加拿大)7% PST/QST
        BigDecimal pst = BigDecimal.ZERO;
        StringBuffer pstStr = new StringBuffer(taxCode);
        pstStr.append("-PST");
        // (加拿大)5% HST取值（BC-HST）
        BigDecimal qst = BigDecimal.ZERO;
        StringBuffer qstStr = new StringBuffer(taxCode);
        qstStr.append("-QST");
        BigDecimal pstqst = BigDecimal.ZERO;
        List<CodeValue> codeValues = codeService.selectCodeValuesByCodeName(request, "SYS.TAX_MAPPING");
        for (CodeValue codeVal : codeValues) {
            if (gstStr.toString().equals(codeVal.getValue())) {
                gst = gst.add(new BigDecimal(codeVal.getMeaning().toString()));
                gsthst = gsthst.add(gst);
            } else if (hstStr.toString().equals(codeVal.getValue())) {
                hst = hst.add(new BigDecimal(codeVal.getMeaning().toString()));
                gsthst = gsthst.add(hst);
            } else if (pstStr.toString().equals(codeVal.getValue())) {
                pst = pst.add(new BigDecimal(codeVal.getMeaning().toString()));
                pstqst = pstqst.add(pst);
            } else if (qstStr.toString().equals(codeVal.getValue())) {
                qst = qst.add(new BigDecimal(codeVal.getMeaning().toString()));
                pstqst = pstqst.add(qst);
            }
        }
        
        //gsthst -- GST
        //pstqst -- PST
        BigDecimal allST = BigDecimal.ZERO;
        allST = gsthst.add(pstqst);
        //totalAfterTax - Total
        BigDecimal AA = BigDecimal.ZERO;
        BigDecimal BB = BigDecimal.ZERO;
        BigDecimal CC = BigDecimal.ZERO;
        BigDecimal taxAmt2 = BigDecimal.ZERO;
        BigDecimal taxAmt3 = BigDecimal.ZERO;
        AA = head.getOrderAmt().add(shippingFee).subtract(Total).setScale(3, BigDecimal.ROUND_HALF_UP);
        int allST1 =  allST.compareTo(BigDecimal.ZERO);
        if (allST1 == 0){
            allST = BigDecimal.ONE;
        }
        if(totaltaxforitem.equals(BigDecimal.ZERO)){
        BB = AA.divide(allST,3, BigDecimal.ROUND_HALF_UP)
                .multiply(gsthst).setScale(2, BigDecimal.ROUND_HALF_UP);
        CC = AA.subtract(BB);
        }else{
            taxAmt2 = head.getOrderAmt().add(shippingFee).subtract(Total).subtract(totaltaxforitem);
            taxAmt3 = taxAmt2.divide(pstqst.add(gsthst), 3, BigDecimal.ROUND_HALF_UP);
            CC = totaltaxforitem.add(taxAmt3.multiply(pstqst));
            BB = taxAmt3.multiply(gsthst);
            pstqst = new BigDecimal(7);
        }
        //判断GST为零的时候其税率变为零        
        /*if(BB.equals(BigDecimal.ZERO)){
            gsthst = BigDecimal.ZERO;
        }*/
        //运费的税
        BigDecimal shippingFeeTax = BigDecimal.ZERO;
        
        //税前总额的税
        BigDecimal totalTax = BigDecimal.ZERO;
        
        //shippingFeeTax  = shippingFeeBeforeTax.multiply(tax).divide(new BigDecimal(100)).setScale(3, BigDecimal.ROUND_HALF_UP);
        totalTax = head.getOrderAmt().add(shippingFee).subtract(Total);
        shippingFeeTax = totalTax.subtract(head.getTaxAmt());
        
        gsthstStr = gsthst.toString();
        pstqstStr = pstqst.toString();
        result.put("head", head);
        result.put("billingTo", billingTo);
        result.put("billingAdress", billingAdress);
        result.put("billingToCAN", billingToCAN);
        result.put("billingAdressToCAN", billingAdressToCAN);
        result.put("shipTo", shipTo);
        result.put("deliveryAdress", deliveryAdress);
        result.put("telNo", telNo);
        result.put("shipToCAN", shipToCAN);
        result.put("deliveryAdressToCAN", deliveryAdressToCAN);
        result.put("telNoToCAN", telNoToCAN);
        
        result.put("period", period);
        debugMsg("====================invoiceNumber is {}======================", head.getInvoiceNumber());
        result.put("lines", pageList);
        debugMsg("====================line is {}======================", lines.size());
        debugMsg("====================totalAmt is {}======================", totalAmt);
        result.put("totalPV", totalPV);
        result.put("totalAmt", totalAmt);
        // Total amount(after GST)
        result.put("totalGST", totalGST);
        result.put("totalGSTAmount", totalAmt.add(totalGST));
        result.put("paymentMethed", orderPayments);
        result.put("username", username);
        result.put("salesAdjustment", adjustment);
        result.put("salesVouchers", voucher);
        result.put("SalesLogistics", salesLogistics);
        // 税率
        result.put("GST", tax);
        // Total Amount Payable(SGD)
        result.put("TotalAmountPayable", head.getActrualPayAmt());
        // （加拿大）Total After Tax
        result.put("totalAfterTax", head.getOrderAmt().add(shippingFee));
        result.put("gsthstStr", gsthstStr);
        result.put("pstqstStr", pstqstStr);
        // 加拿大（5% GST/HST）
        result.put("gsthst", gsthst.multiply(Total).divide(new BigDecimal(100)));
        // 加拿大（7% PST/QST）
        result.put("pstqst", head.getOrderAmt().add(shippingFee)
                .subtract(gsthst.multiply(Total).divide(new BigDecimal(100)).add(Total)));
        result.put("shippingFeeBeforeTax", shippingFeeBeforeTax);
        result.put("Total", Total);
        result.put("TotalOrderAmt", head.getOrderAmt().add(shippingFee1));
        result.put("shippingFeeTax", shippingFeeTax);
        result.put("totalTax", totalTax);
        result.put("BB", BB);
        result.put("CC", CC);
        result.put("curr", curr);
        debugMsg("====================end process in MLInvoiceDataServiceImpl======================");
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

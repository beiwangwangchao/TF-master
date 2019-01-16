/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.common.json.JSON;
import com.github.pagehelper.StringUtil;
import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.Coupons;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListPriceData;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderSubmission;
import com.lkkhpg.dsis.admin.integration.dapp.dto.Product;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.GetProductListMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOrderSubmissionService;
import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmCityMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmCountryMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmStateMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.PriceListDetail;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.product.mapper.PriceListDetailMapper;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.common.service.ICommMemSiteService;
import com.lkkhpg.dsis.common.service.ICommOrderPaymentService;
import com.lkkhpg.dsis.common.service.IDeliveryInvOrgMatchService;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.common.service.IMwsOrderPaymentService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.MultiLanguageField;
import com.lkkhpg.dsis.platform.mapper.system.MultiLanguageMapper;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 订单提交接口实现类.
 *
 * @author shenqb
 */
@Service
@Transactional
public class OrderSubmissionServiceImpl implements IOrderSubmissionService {

    private Logger logger = LoggerFactory.getLogger(OrderSubmissionServiceImpl.class);

    @Autowired
    private ISalesOrderService salesOrderService;

    @Autowired
    private SpmLocationMapper spmLocationMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private ICommMemSiteService memSiteService;

    @Autowired
    private InvItemMapper invItemMapper;

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private SpmStateMapper spmStateMapper;

    @Autowired
    private PriceListDetailMapper priceListDetailMapper;

    @Autowired
    private IMarketParamService marketParamService;

    @Autowired
    private SpmTaxMapper spmTaxMapper;

    @Autowired
    private SpmCountryMapper spmCountryMapper;

    @Autowired
    private SpmCityMapper spmCityMapper;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IParamService paramServiceImpl;

    @Autowired
    private ICommOrderPaymentService commOrderPaymentService;

    @Autowired
    private IDAppUtilService dAppUtilService;

    @Autowired
    private IMwsOrderPaymentService mwsOrderPaymentService;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private MultiLanguageMapper multiLanguageMapper;
    
    @Autowired
    private IDeliveryInvOrgMatchService deliveryInvOrgMatchService;
    
    @Autowired
    private GetProductListMapper getProductListMapper;

    @Transactional(rollbackFor = Exception.class)
    public String orderSubmit(IRequest iRequest, OrderSubmission orderSubmission) throws DAppException {
        if (logger.isInfoEnabled()) {
            logger.info("dapp orderSubmit begin:");
        }
        if (salesOrderMapper.isExistDappNo(orderSubmission.getAppNo()) > 0) {
            throw new DAppException(IntegrationConstant.APP_NO_EXISTED, IntegrationConstant.ERROR_30001,
                    "appNo = " + orderSubmission.getAppNo());
        }
        // 构造订单信息
        SalesOrder dappSalesOrder = self().parseOrder(iRequest, orderSubmission);

        // 提交pos后的订单
        SalesOrder posSalesOrder = new SalesOrder();

        // 暂存dapp订单信息，待调用commService后覆盖
        BigDecimal dappActrualPayAmt = dappSalesOrder.getActrualPayAmt();
        List<SalesLine> dappLines = dappSalesOrder.getLines();
        List<BigDecimal> dappUnitSellingPrices = new ArrayList<BigDecimal>();
        List<BigDecimal> dappLineAmounts = new ArrayList<BigDecimal>();
        for (SalesLine dappLine : dappLines) {
            dappUnitSellingPrices.add(dappLine.getUnitSellingPrice());
            dappLineAmounts.add(dappLine.getUnitSellingPrice());
        }
        try {
            // 调用common service
            posSalesOrder = salesOrderService.submitOrder(iRequest, dappSalesOrder);

            // reset order ：actrualPayAmt, unitSellingPrice, amount
            posSalesOrder.setActrualPayAmt(dappActrualPayAmt);
            for (int i = 0; i < posSalesOrder.getLines().size(); i++) {
                posSalesOrder.getLines().get(i).setUnitSellingPrice(dappUnitSellingPrices.get(i));
                posSalesOrder.getLines().get(i).setAmount(dappLineAmounts.get(i));
                salesLineMapper.updateByPrimaryKeySelective(posSalesOrder.getLines().get(i));
            }
            salesOrderMapper.updateByPrimaryKey(posSalesOrder);

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("dapp orderSubmit error:{}", IntegrationException.getErrorStackTrace(e));
            }
            throw new DAppException(IntegrationConstant.CALL_ORDER_API_FAILED + " : " + IntegrationException.getErrorStackTrace(e),
                    IntegrationConstant.ERROR_30002, "appNo = " + orderSubmission.getAppNo());
        }

        // 如果提交失败，则报错
        if (StringUtils.isEmpty(posSalesOrder.getOrderNumber())) {
            if (logger.isErrorEnabled()) {
                logger.error("dapp orderSubmit error:{}", "orderNumber is null");
            }
            throw new DAppException(IntegrationConstant.CALL_ORDER_API_FAILED, IntegrationConstant.ERROR_30002,
                    "appNo = " + orderSubmission.getAppNo());
        }

        // 如果是未完成，则冻结支付信息
        if (IntegrationConstant.ORDER_STATUS_PAYIN.equals(posSalesOrder.getOrderStatus())
                || IntegrationConstant.ORDER_STATUS_WPAY.equals(posSalesOrder.getOrderStatus())) {
            lockPayment(iRequest, posSalesOrder, orderSubmission);
        }
        // 如果已完成，则新增支付信息
        if (IntegrationConstant.ORDER_STATUS_COMP_ED.equals(posSalesOrder.getOrderStatus())) {
            lockPayment(iRequest, posSalesOrder, orderSubmission);
            addPayment(iRequest, posSalesOrder);
        }

        return posSalesOrder.getOrderNumber();

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long saveLocation(String countryCode, String stateCode, String cityCode, String addressLine1,
            String addressLine2, Long zipCode) throws DAppException {
        SpmLocation location = new SpmLocation();
        Date activeDate = new Date();
        location.setStartActiveDate(activeDate);
        location.setEnabledFlag("Y");
        location.setCountryCode(countryCode);
        location.setStateCode(stateCode);
        location.setCityCode(cityCode);
        location.setAddressLine1(addressLine1);
        location.setAddressLine2(addressLine2);
        location.setZipCode(String.valueOf(zipCode));
        location.setObjectVersionNumber(-1L);
        location.setRequestId(-1L);
        location.setProgramId(-1L);
        location.setCreatedBy(-1L);
        location.setLastUpdatedBy(-1L);
        List<SpmLocation> locations = spmLocationMapper.queryByLocation(location);
        if (locations.size() > 0) {
            try {
                spmLocationMapper.updateByPrimaryKey(locations.get(0));
                return locations.get(0).getLocationId();
            } catch (Exception e) {
                throw new DAppException(IntegrationConstant.SAVE_LOCATION_ERROR, IntegrationConstant.ERROR_60002,
                        "orderSubmit error");
            }
        }
        try {
            spmLocationMapper.insertSelective(location);
        } catch (Exception e) {
            throw new DAppException(IntegrationConstant.SAVE_LOCATION_ERROR, IntegrationConstant.ERROR_60002,
                    "orderSubmit error");
        }
        Long locationId = location.getLocationId();
        if (StringUtils.isEmpty(locationId)) {
            throw new DAppException(IntegrationConstant.LOCATION_MESSAGE_NOT_FOUND, IntegrationConstant.ERROR_60003,
                    "orderSubmit error");
        }
        return locationId;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long saveMemSite(IRequest iRequest, Long memberId, Long locationId, String siteUseCode,
            OrderSubmission orderSubmission) throws DAppException {
        if (StringUtils.isEmpty(memberId)) {
            throw new DAppException(IntegrationConstant.MEMBER_CODE_NOT_EXISTED, IntegrationConstant.ERROR_20001,
                    "orderSubmit error");
        }
        MemSite memSite = new MemSite();
        SpmLocation location = new SpmLocation();
        location.setLocationId(locationId);
        memSite.setSpmLocation(location);
        memSite.setMemberId(memberId);
        memSite.setLocationId(locationId);
        memSite.setSiteUseCode(siteUseCode);
        if (siteUseCode.equals(IntegrationConstant.MEMBER_SITE_TYPE_SHIP)) {
            memSite.setName(orderSubmission.getDeliveryReceiver());
            memSite.setPhone(orderSubmission.getDeliveryPhone());
        } else if (siteUseCode.equals(IntegrationConstant.MEMBER_SITE_TYPE_BILL)) {
            memSite.setName(orderSubmission.getBillingReceiver());
            memSite.setPhone(orderSubmission.getBillingPhone());
        }
        memSite.setDefaultFlag("N");

        memSite.setCreatedBy(-1L);
        memSite.setRequestId(-1L);
        memSite.setObjectVersionNumber(1L);
        memSite.setLastUpdatedBy(-1L);
        memSite.setLastUpdateDate(new Date());
        memSite.setProgramId(-1L);
        memSite.setCreationDate(new Date());
        memSiteService.saveMemSite(iRequest, memSite);
        return memSite.getSiteId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long getSalesOrgIdByCode(String orgCode, Long marketId) throws DAppException {
        if (null == marketId) {
            throw new DAppException(IntegrationConstant.MARKET_CODE_NOT_EXISTED, IntegrationConstant.ERROR_60004,
                    "invalid market code");
        }
        SpmSalesOrganization spmSaleOrganization = new SpmSalesOrganization();
        spmSaleOrganization.setCode(orgCode);
        spmSaleOrganization.setMarketId(marketId);
        List<SpmSalesOrganization> spmSalesOrganizationList = spmSalesOrganizationMapper
                .getSalesOrgByCodeAndMarket(spmSaleOrganization);
        if (spmSalesOrganizationList.size() == 0) {
            throw new DAppException(IntegrationConstant.SALESORGANIZATION_CODE_NOT_EXISTED,
                    IntegrationConstant.ERROR_60005, "orderSubmit error");
        }
        spmSaleOrganization = spmSalesOrganizationList.get(0);
        Long salesOrgId = spmSaleOrganization.getSalesOrgId();
        if (StringUtils.isEmpty(salesOrgId)) {
            throw new DAppException(IntegrationConstant.SALESORGANIZATION_CODE_NOT_EXISTED,
                    IntegrationConstant.ERROR_60005, "orderSubmit error");
        }
        return salesOrgId;
    }

    private Map<String, Object> parsePeriod(Date date) {
        Calendar cal = Calendar.getInstance();
        Map<String, Object> map = new HashMap<String, Object>();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR); // 获取年份
        int month = cal.get(Calendar.MONTH) + 1; // 获取月份
        String periodName = String.valueOf(year) + String.format("%02d", month);
        map.put("periodYear", String.valueOf(year));
        map.put("periodMonth", String.format("%02d", month));
        map.put("periodName", periodName);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public SalesOrder parseOrder(IRequest iRequest, OrderSubmission orderSubmission) throws DAppException {
        Map<String, Object> marketMap = new HashMap<String, Object>();
        marketMap.put("marketCode", orderSubmission.getMarket());
        // 订单请求参数，必输
        String billingCountryCode = spmStateMapper.queryCountryByStateForDapp(orderSubmission.getBillingState());
        String deliveryCountryCode = spmStateMapper.queryCountryByStateForDapp(orderSubmission.getDeliveryState());
        if (StringUtils.isEmpty(billingCountryCode) || StringUtils.isEmpty(deliveryCountryCode)) {
            throw new DAppException(IntegrationConstant.UNABLED_TO_GET_COUNTRY_BY_STATE,
                    IntegrationConstant.ERROR_60001, "appNo = " + orderSubmission.getAppNo());
        }

        Long marketId = spmMarketMapper.queryMarketIdByCodeAndCompany(marketMap);
        Long salesOrgId = self().getSalesOrgIdByCode(orderSubmission.getSaleOrganization(), marketId);
        String dappSyncFlag = IntegrationConstant.ORDER_SUBMIT_ASYC_FLAG;
        String dappAppNo = orderSubmission.getAppNo();
        iRequest.setLocale(BaseConstants.DEFAULT_LANG);
        iRequest.setAttribute(SystemProfileConstants.MARKET_ID, marketId);
        iRequest.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
        // 设置dapp默认语言
        String dappDefaultLang = codeService.getCodeMeaningByValue(iRequest,
                IntegrationConstant.ISG_DAPP_DEFAULT_LANGUAGE, orderSubmission.getMarket());
        List<String> syslangs = multiLanguageMapper.querySysLangs();
        if (!StringUtils.isEmpty(dappDefaultLang) && syslangs.contains(dappDefaultLang)) {
            iRequest.setLocale(dappDefaultLang);
        }
        // 设置dapp账户
        Account dappAccount = accountService.selectByLoginName(IntegrationConstant.DAPP_ACCOUNT_NAME);
        if (null == dappAccount || StringUtil.isEmpty(dappAccount.getLoginName())) {
            throw new DAppException(IntegrationConstant.ISG_ERROR_DAPP_USER_MISSING,
                    IntegrationConstant.STATUS_CODE_ORDER, IntegrationConstant.ISG_ERROR_DAPP_USER_MISSING);
        }
        iRequest.setAccountId(dappAccount.getAccountId());
        RequestHelper.setCurrentRequest(iRequest);
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("dapp -> order submission info : iRequest : " + JSON.json(iRequest));
            }
        } catch (IOException e) {
        }
        Member memberQuery = new Member();
        memberQuery.setMemberCode(orderSubmission.getDistributorNumber());
        //memberQuery.setMarketId(marketId);
        List<Member> members = memberMapper.selectValidMembers(memberQuery);
        if (members.size() == 0) {
            String errorMSg = " memberCode : " + memberQuery.getMemberCode();
            if (logger.isErrorEnabled()) {
                logger.error("dapp -> order submission error: member is null , error params : " + errorMSg);
            }
            throw new DAppException(IntegrationConstant.MEMBER_CODE_NOT_EXISTED + errorMSg,
                    IntegrationConstant.ERROR_20001, "appNo = " + orderSubmission.getAppNo());
        }
        Long memberId = members.get(0).getMemberId();

        // 构建订单订单地址
        SalesSites shipSalesSites = processSalesSite(iRequest, orderSubmission,
                IntegrationConstant.MEMBER_SITE_TYPE_SHIP, deliveryCountryCode);
        SalesSites billSalesSites = processSalesSite(iRequest, orderSubmission,
                IntegrationConstant.MEMBER_SITE_TYPE_BILL, billingCountryCode);
        List<SalesSites> salesSitesList = new ArrayList<SalesSites>();
        salesSitesList.add(shipSalesSites);
        salesSitesList.add(billSalesSites);
        BigDecimal orderAmt = orderSubmission.getAmount();
        BigDecimal discountAmt = BigDecimal.ZERO;
        // BigDecimal salesPoint = null;//在创建订单接口里设置
        BigDecimal taxAmt = BigDecimal.ZERO;
        String sourceType = IntegrationConstant.ORDER_SOURCE_TYPE_DAPP;
        // // 订单来源为"普通"
        String remark = IntegrationConstant.ORDER_SUBMIT_REMARK; // 备注数据来源
        String channel = IntegrationConstant.ORDER_CHANNEL;
        String codFlag = IntegrationConstant.ORDER_SUBMIT_COD_FLAG; // 不是“货到付款”
        String currency = orderSubmission.getCurrency();
        Date orderDate = dAppUtilService.dateTimeStringToDate(orderSubmission.getOrderDate());
        String periodName = (String) parsePeriod(orderDate).get("periodName");
        double shippingFee = 0d;
        if (null != orderSubmission.getShippingFee()) {
            shippingFee = orderSubmission.getShippingFee().doubleValue();
        }

        // 获取ECoupons总金额
        BigDecimal voucherCount = BigDecimal.ZERO;
        List<Coupons> couponsList = orderSubmission.getCoupons();
        List<Voucher> vouchers = voucherService.getMemberVouchersForVIP(iRequest, memberId);
        List<String> voucherCodes = new ArrayList<String>();
        for (Voucher voucher : vouchers) {
            voucherCodes.add(voucher.getVoucherCode());
        }
        if (couponsList != null && couponsList.size() > 0) {
            for (Coupons coupons : couponsList) {
                if (!StringUtil.isEmpty(coupons.getCouponCode())) {
                    for (Voucher voucher : vouchers) {
                        if (voucher.getVoucherCode().equals(coupons.getCouponCode())) {
                            voucherCount = voucherCount.add(voucher.getDiscountValue());
                        }
                    }

                }
            }
        }
        // 实付款 = 订单金额 + 运费 + Ecoupons金额
        Double actrualPayAmt = new BigDecimal(orderAmt.doubleValue() + shippingFee + voucherCount.doubleValue())
                .setScale(IntegrationConstant.defaultScale, BigDecimal.ROUND_HALF_UP).doubleValue();

        if (!StringUtils.isEmpty(orderSubmission.getBonusPeriod())) {
            periodName = orderSubmission.getBonusPeriod();
        }
        String itemSalesType = IntegrationConstant.ORDER_SUBMIT_ITEM_SALES_TYPE; // 销售类型为“购买”
        String deliveryType = IntegrationConstant.ORDER_SUBMIT_DELIVERY_TYPE; // 发运类型为"物流配送"
        if (IntegrationConstant.ORDER_SUBMIT_DELIVERY_TYPE_PCKUP.equals(orderSubmission.getMailing())) {
            deliveryType = IntegrationConstant.ORDER_SUBMIT_DELIVERY_TYPE_PCKUP;
            salesSitesList.remove(shipSalesSites);
        }
      //根据供货关系，取销售组织关联的库存组织
        Long invOrgId = deliveryInvOrgMatchService.matchInvOrg(iRequest, salesOrgId, deliveryType,
                null);
        String sourceKey = orderSubmission.getSourceOrderNo();
        Date payDate = null;
        if (IntegrationConstant.ORDER_STATUS_COMP_ED.equals(orderSubmission.getOrderStatus())) {
            payDate = orderDate;
        }
        String orderStatus;
        if (StringUtils.isEmpty(orderSubmission.getOrderStatus())) {
            orderStatus = IntegrationConstant.ORDER_STATUS_PAYIN; // 订单类型默认为“支付中”
        } else {
            orderStatus = orderSubmission.getOrderStatus();
            List<CodeValue> codeValues = codeService.selectCodeValuesByCodeName(iRequest,
                    IntegrationConstant.OM_ORDER_STATUS);
            List<String> statusValues = new ArrayList<String>();
            for (CodeValue codeValue : codeValues) {
                statusValues.add(codeValue.getValue());
            }
            if (!statusValues.contains(orderStatus)) {
                throw new DAppException(IntegrationConstant.ORDER_STATUS_NOT_EXIST, IntegrationConstant.ERROR_30012,
                        "orderStatus = " + orderStatus);
            }
        }
        String orderType = orderSubmission.getOrderType();
        if (null != orderType) {
            if (!validateOrderType(members.get(0), orderType)) {
                throw new DAppException(IntegrationConstant.ORDER_TYPE_ERROR, IntegrationConstant.ERROR_30014,
                        "orderType = " + orderType + ",but member role = " + members.get(0).getMemberRole());
            }
        }
        
        // 表基础字段
        Long objectVersionNumber = 1L;
        Long requestId = -1L;
        Long lastUpdatedBy = dappAccount.getAccountId();
        Date lastUpdateDate = new Date();
        Long createdBy = dappAccount.getAccountId();
        Long createUserId = dappAccount.getAccountId();
        Date creationDate = new Date();
        Long programId = -1L;
        SalesLogistics salesLogistics = null;
        // 设置物流信息
        if (null != orderSubmission.getShippingFee()) {
            salesLogistics = new SalesLogistics();
            salesLogistics.setShippingTierId(-1L); // 承运商默认-1
            salesLogistics.setSalesOrgId(salesOrgId);
            salesLogistics.setShippingFee(orderSubmission.getShippingFee());
            salesLogistics.setCodFlag(codFlag);
            salesLogistics.setObjectVersionNumber(objectVersionNumber);
            salesLogistics.setRequestId(requestId);
            salesLogistics.setLastUpdatedBy(lastUpdatedBy);
            salesLogistics.setLastUpdateDate(lastUpdateDate);
            salesLogistics.setCreatedBy(createdBy);
            salesLogistics.setCreationDate(creationDate);
            salesLogistics.setProgramId(programId);
        }

        // 设置订单头-必输字段
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setSalesOrgId(salesOrgId);
        salesOrder.setOrderStatus(orderStatus);
        salesOrder.setOrderDate(orderDate);
        salesOrder.setOrderType(orderType);
        salesOrder.setChannel(channel);
        salesOrder.setCreateUserId(createUserId);
        salesOrder.setCurrency(currency);
        salesOrder.setOrderAmt(orderAmt);
        salesOrder.setDiscountAmt(discountAmt);
        salesOrder.setTaxAmt(taxAmt);
        salesOrder.setDeliveryType(deliveryType);
        salesOrder.setCodFlag(codFlag);
        salesOrder.setSourceType(sourceType);

        salesOrder.setDappSyncFlag(dappSyncFlag);
        salesOrder.setRemark(remark);
        salesOrder.setMemberId(memberId);

        salesOrder.setPeriod(periodName);
        salesOrder.setLogistics(salesLogistics);
        salesOrder.setSalesSites(salesSitesList);

        salesOrder.setCreatedBy(createdBy);
        salesOrder.setRequestId(requestId);
        salesOrder.setObjectVersionNumber(objectVersionNumber);
        salesOrder.setLastUpdatedBy(lastUpdatedBy);
        salesOrder.setLastUpdateDate(lastUpdateDate);
        salesOrder.setProgramId(programId);
        salesOrder.setCreationDate(creationDate);
        salesOrder.setDappAppNo(dappAppNo);
        salesOrder.setPayDate(payDate);
        salesOrder.setSourceKey(sourceKey);
        salesOrder.setActrualPayAmt(BigDecimal.valueOf(actrualPayAmt));

        // 设置订单行必输字段
        List<SalesLine> lines = new ArrayList<SalesLine>();
        for (Product product : orderSubmission.getProducts()) {
            InvItem invItem = new InvItem();
            invItem.setItemNumber(product.getProductCode());
            List<InvItem> invItemList = invItemMapper.getItemByItemNumber(invItem);
            if (invItemList.size() == 0) {
                throw new DAppException(IntegrationConstant.PRODUCT_CODE_NOT_EXISTED, IntegrationConstant.ERROR_40001,
                        "appNo = " + orderSubmission.getAppNo());
            }
            // 可用量校验
            List<String> backOrders = paramServiceImpl.getParamValues(iRequest,
                    SystemProfileConstants.PARAM_ALLOW_BACK_ORDER, SystemProfileConstants.ORG_TYPE_SALES, salesOrgId);
            if (backOrders != null && backOrders.size() > 0 && !backOrders.get(0).equals("Y")) {
                if (logger.isInfoEnabled()) {
                    logger.info("param backOrders is 'Y', begin check the inventory");
                }
                Map<String, Object> itemQueryMap = new HashMap<String, Object>();
                itemQueryMap.put("salesOrgId", salesOrgId);
                itemQueryMap.put("itemId", invItemList.get(0).getItemId());
                int inventory = 0;
                if (null != invItemMapper.getItemCountBySalesOrgId(itemQueryMap)) {
                    inventory = invItemMapper.getItemCountBySalesOrgId(itemQueryMap);
                }
                if (product.getQuantity().doubleValue() > inventory) {
                    throw new DAppException(IntegrationConstant.PRODUCT_QUANTITY_NOT_ENOUGH,
                            IntegrationConstant.ERROR_30011,
                            "productCode = " + product.getProductCode() + ", inventory = " + inventory);
                }
            }

            SalesLine salesLine = new SalesLine();
            salesLine.setDefaultInvOrgId(invOrgId);
            salesLine.setItemId(invItemList.get(0).getItemId());
            salesLine.setItemNumber(product.getProductCode());
            salesLine.setQuantity(product.getQuantity());
            salesLine.setStatus(orderSubmission.getShippingStatus());
            salesLine.setSourceType(sourceType);
            salesLine.setUomCode(invItemList.get(0).getUomCode());
            salesLine.setSalesOrgId(salesOrgId);
            salesLine.setItemSalesType(itemSalesType);
            // salesLine.setUnitSellingPrice(processPrice(iRequest, marketId,
            // product.getPrice()));
            salesLine.setUnitSellingPrice(product.getPrice()); // 销售价取自入参
            // salesLine.setPv(product.getPv());
            salesLine.setAmount(product.getPrice().multiply(product.getQuantity()));
            salesLine.setUnitDiscountPrice(BigDecimal.ZERO);
            salesLine.setUnitOrigPrice(
                    processPrice(iRequest, marketId, product.getPrice(), orderSubmission.getMarket(), invItemList.get(0).getItemId(), orderSubmission.getSaleOrganization(), product.getProductCode())); // 原价
                                                                                                        // =
                                                                                                        // 销售价
                                                                                                        // -
                                                                                                        // 税
            List<PriceListDetail> priceListDetails = priceListDetailMapper.selectByItem(salesLine.getItemId());
            for (PriceListDetail priceListDetail : priceListDetails) {
                if (priceListDetail.getSalesOrgId().equals(salesOrder.getSalesOrgId())) {

                    if (priceListDetail.getPriceType().equals("PV")) {
                        if (logger.isInfoEnabled()) {
                            logger.info("dapp order submit : request param - > pv : " + product.getPv()
                                    + " , db - > pv : " + priceListDetail.getUnitPrice());
                        }
                        if (priceListDetail.getStartActiveDate().getTime() < new Date().getTime()
                                && (null == priceListDetail.getEndActiveDate()
                                        || priceListDetail.getEndActiveDate().getTime() > new Date().getTime())) {
                            salesLine.setPv(priceListDetail.getUnitPrice());
                        }

                    }
                }
            }
            if (null == salesLine.getPv()) {
                throw new DAppException(IntegrationConstant.PV_NOT_EXIST, IntegrationConstant.ERROR_30010,
                        "pv = " + product.getPv());
            }
            if (product.getCoupons().size() > 0) {
                if (!StringUtils.isEmpty(product.getCoupons().get(0).getCouponCode())) {
                    Voucher voucher = voucherMapper.getVoucherByCode(product.getCoupons().get(0).getCouponCode());
                    if (voucher != null) {
                        Long voucherId = voucher.getVoucherId();
                        salesLine.setVoucherId(voucherId);
                    }
                }
            }
            salesLine.setCreatedBy(createdBy);
            salesLine.setRequestId(requestId);
            salesLine.setObjectVersionNumber(objectVersionNumber);
            salesLine.setLastUpdatedBy(lastUpdatedBy);
            salesLine.setLastUpdateDate(lastUpdateDate);
            salesLine.setProgramId(programId);
            salesLine.setCreationDate(creationDate);
            lines.add(salesLine);
        }
        salesOrder.setLines(lines);
        if (logger.isInfoEnabled()) {
            logger.info("dapp orderSubmit - make saelsOrder request success:");
        }
        return salesOrder;
    }

    private SalesSites processSalesSite(IRequest iRequest, OrderSubmission orderSubmission, String siteType,
            String countryCode) {
        SalesSites salesSites = new SalesSites();
        salesSites.setAutoshipFlag(IntegrationConstant.ORDER_AUTO_FLAG);
        salesSites.setSalesOrgId(iRequest.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        salesSites.setCreatedBy(-1L);
        salesSites.setRequestId(-1L);
        salesSites.setObjectVersionNumber(1L);
        salesSites.setLastUpdatedBy(-1L);
        salesSites.setLastUpdateDate(new Date());
        salesSites.setProgramId(-1L);
        salesSites.setCreationDate(new Date());
        if (siteType.equals(IntegrationConstant.MEMBER_SITE_TYPE_SHIP)) {
            salesSites.setSiteType(siteType);
            salesSites.setAddress1(orderSubmission.getDeliveryAddress());
            if (!StringUtils.isEmpty(orderSubmission.getDeliveryAddress2())) {
                salesSites.setAddress2(orderSubmission.getDeliveryAddress2());
            }
            if (!StringUtils.isEmpty(orderSubmission.getDeliveryAddress3())) {
                salesSites.setAddress3(orderSubmission.getDeliveryAddress3());
            }
            salesSites.setCountryCode(orderSubmission.getDeliveryCountry());
            salesSites.setCityCode(orderSubmission.getDeliveryCity());
            salesSites.setStateCode(orderSubmission.getDeliveryState());
            salesSites.setCountryCode(countryCode);
            salesSites.setAreaCode(orderSubmission.getDeliveryAreaCode());
            salesSites.setZipCode(String.valueOf(orderSubmission.getDeliveryZipCode()));
            salesSites.setName(orderSubmission.getDeliveryReceiver());
            if (!StringUtils.isEmpty(orderSubmission.getDeliveryAreaCode())
                    && orderSubmission.getDeliveryAreaCode().equals("886")
                    && !orderSubmission.getDeliveryPhone().startsWith("0")) {
                orderSubmission.setDeliveryPhone("0" + orderSubmission.getDeliveryPhone());
            }
            salesSites.setPhone(orderSubmission.getDeliveryPhone());
            salesSites.setAddress1(orderSubmission.getDeliveryAddress());
            String deliveryCountryName = spmCountryMapper
                    .selectByPrimaryKey(orderSubmission.getDeliveryCountry()) == null ? ""
                            : spmCountryMapper.selectByPrimaryKey(orderSubmission.getDeliveryCountry()).getName();
            String deliveryStateName = spmStateMapper.selectByPrimaryKey(orderSubmission.getDeliveryState()) == null
                    ? "" : spmStateMapper.selectByPrimaryKey(orderSubmission.getDeliveryState()).getName();
            String deliveryCityName = spmCityMapper.selectByPrimaryKey(orderSubmission.getDeliveryCity()) == null ? ""
                    : spmCityMapper.selectByPrimaryKey(orderSubmission.getDeliveryCity()).getName();
            salesSites.setAddress(
                    deliveryCountryName + deliveryStateName + deliveryCityName + orderSubmission.getDeliveryAddress()
                            + (orderSubmission.getDeliveryAddress2() == null ? ""
                                    : orderSubmission.getDeliveryAddress2())
                            + (orderSubmission.getDeliveryAddress3() == null ? ""
                                    : orderSubmission.getDeliveryAddress3()));
        }
        if (siteType.equals(IntegrationConstant.MEMBER_SITE_TYPE_BILL)) {
            salesSites.setSiteType(siteType);
            salesSites.setAddress1(orderSubmission.getBillingAddress());
            if (!StringUtils.isEmpty(orderSubmission.getBillingAddress2())) {
                salesSites.setAddress2(orderSubmission.getBillingAddress2());
            }
            if (!StringUtils.isEmpty(orderSubmission.getBillingAddress3())) {
                salesSites.setAddress3(orderSubmission.getBillingAddress3());
            }
            salesSites.setCountryCode(orderSubmission.getBillingCountry());
            salesSites.setCityCode(orderSubmission.getBillingCity());
            salesSites.setStateCode(orderSubmission.getBillingState());
            salesSites.setCountryCode(countryCode);
            salesSites.setAreaCode(orderSubmission.getBillingAreaCode());
            salesSites.setZipCode(String.valueOf(orderSubmission.getBillingZipCode()));
            salesSites.setName(orderSubmission.getBillingReceiver());
            if (!StringUtils.isEmpty(orderSubmission.getBillingAreaCode())
                    && orderSubmission.getBillingAreaCode().equals("886")
                    && !orderSubmission.getBillingPhone().startsWith("0")) {
                orderSubmission.setBillingPhone("0" + orderSubmission.getBillingPhone());
            }
            salesSites.setPhone(orderSubmission.getBillingPhone());
            salesSites.setAddress1(orderSubmission.getBillingAddress());
            String billingCountryName = spmCountryMapper.selectByPrimaryKey(orderSubmission.getBillingCountry()) == null
                    ? "" : spmCountryMapper.selectByPrimaryKey(orderSubmission.getBillingCountry()).getName();
            String billingStateName = spmStateMapper.selectByPrimaryKey(orderSubmission.getBillingState()) == null ? ""
                    : spmStateMapper.selectByPrimaryKey(orderSubmission.getBillingState()).getName();
            String billingCityName = spmCityMapper.selectByPrimaryKey(orderSubmission.getBillingCity()) == null ? ""
                    : spmCityMapper.selectByPrimaryKey(orderSubmission.getBillingCity()).getName();
            salesSites
                    .setAddress(billingCountryName + billingStateName + billingCityName
                            + orderSubmission.getBillingAddress() + (orderSubmission.getBillingAddress2() == null ? ""
                                    : orderSubmission.getBillingAddress2())
                            + (orderSubmission.getBillingAddress3() == null ? ""
                                    : orderSubmission.getBillingAddress3()));
        }
        return salesSites;
    }

    // 根据推过来的销售价获取原价
    private BigDecimal processPrice(IRequest request, Long marketId, BigDecimal price, String marketCode, Long itemId, String salesOrganization, String itemNumber) {
        //加拿大市场特殊逻辑：如果是该市场下的特定商品，则税率为7%（后面调整）
        if("15160502".equals(itemNumber)){
            if("SBC".equals(salesOrganization)){
                price = price.divide(new BigDecimal(1 + 0.07), 2, BigDecimal.ROUND_HALF_DOWN);
                return price;
            }
            if("SONT".equals(salesOrganization)){
                price = price.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_DOWN);
                return price;
            }
        }
        //先判断商品不计税字段
        Map<String, Object> priceMap = new HashMap<String, Object>();
        priceMap.put("itemId", itemId);
        priceMap.put("saleOrganization", salesOrganization);
        List<GetProductListPriceData> prices = getProductListMapper.selectPriceByItemId(priceMap);
        if(prices.size() > 0 && !StringUtils.isEmpty(prices.get(0).getDisableTaxFlag()) && prices.get(0).getDisableTaxFlag().equals(BaseConstants.YES)){
            return price;
        }
        //再判断销售组织上的计税逻辑
        BigDecimal taxPercent = dAppUtilService.getTaxBySalesOrg(request);
        if (null != taxPercent) {
            price = price.divide(new BigDecimal(1 + taxPercent.doubleValue() / 100d), 2, BigDecimal.ROUND_HALF_DOWN);
        }
        return price;
    }

    private boolean validateOrderType(Member member, String orderType) {
        boolean success = false;
        if (member.getMemberRole().equals(IntegrationConstant.MEMBER_ROLE_VIP)) {
            if (orderType.equals(IntegrationConstant.ORDER_SUBMIT_ORDER_TYPE_VIP)) {
                success = true;
            } else {
                success = false;
            }
        } else if (member.getMemberRole().equals(IntegrationConstant.MEMBER_ROLE_DIS)) {
            if (orderType.equals(IntegrationConstant.ORDER_SUBMIT_ORDER_TYPE)) {
                success = true;
            } else {
                success = false;
            }
        }
        return success;
    }

    @Transactional(rollbackFor = Exception.class)
    private void lockPayment(IRequest iRequest, SalesOrder salesOrder, OrderSubmission orderSubmission)
            throws DAppException {
        List<OmMwsOrderPayment> omMwsOrderPayments = new ArrayList<OmMwsOrderPayment>();

        // 冻结ECoupons的支付信息
        BigDecimal voucherCount = BigDecimal.ZERO; // ECoupons总金额
        List<Coupons> couponsList = orderSubmission.getCoupons();
        List<Voucher> vouchers = voucherService.getMemberVouchersForVIP(iRequest, salesOrder.getMemberId());
        List<String> voucherCodes = new ArrayList<String>();
        for (Voucher voucher : vouchers) {
            voucherCodes.add(voucher.getVoucherCode());
        }
        if (couponsList != null && couponsList.size() > 0) {
            for (Coupons coupons : couponsList) {
                if (!StringUtil.isEmpty(coupons.getCouponCode())) {
                    boolean isVoucher = false;
                    for (Voucher voucher : vouchers) {
                        if (voucher.getVoucherCode().equals(coupons.getCouponCode())) {
                            OmMwsOrderPayment couponsPayment = new OmMwsOrderPayment();
                            couponsPayment.setOrderHeaderId(salesOrder.getHeaderId());
                            couponsPayment.setSalesOrgId(salesOrder.getSalesOrgId());
                            couponsPayment.setVoucherId(voucher.getVoucherId());
                            couponsPayment.setPaymentMethod(IntegrationConstant.ORDER_PAYMETHOD_ECUP);
                            couponsPayment.setPaymentAmount(voucher.getDiscountValue());
                            couponsPayment.setRemark(salesOrder.getRemark());
                            couponsPayment.setStatus("NEW");
                            omMwsOrderPayments.add(couponsPayment);
                            voucherCount = voucherCount.add(voucher.getDiscountValue());
                            isVoucher = true;
                        }
                    }
                    // 如果Ecoupons没有匹配到会员现有的voucher,则报错
                    if (!isVoucher) {
                        if (logger.isErrorEnabled()) {
                            logger.error("dapp orderSubmit error : Ecoupon {} not enough for member {}",
                                    coupons.getCouponCode(), orderSubmission.getDistributorNumber());
                        }
                        throw new DAppException(IntegrationConstant.ECOUPONS_NOT_ENOUGH,
                                IntegrationConstant.ERROR_30016, "Ecoupon = " + coupons.getCouponCode());
                    }
                }
            }
        }

        // 冻结订单的支付信息
        OmMwsOrderPayment orderPayment = new OmMwsOrderPayment();
        orderPayment.setOrderHeaderId(salesOrder.getHeaderId());
        orderPayment.setSalesOrgId(salesOrder.getSalesOrgId());
        orderPayment.setPaymentMethod(IntegrationConstant.ORDER_PAYMETHOD_ONLPA);
        orderPayment.setPaymentAmount(salesOrder.getActrualPayAmt().subtract(voucherCount)
                .setScale(IntegrationConstant.defaultScale, BigDecimal.ROUND_HALF_UP)); // 订单头支付行金额
                                                                                        // =
                                                                                        // 实付款
                                                                                        // -
                                                                                        // ecoupon总金额，以便过comm
                                                                                        // service的校验
        orderPayment.setRemark(salesOrder.getRemark());
        orderPayment.setStatus("NEW");
        omMwsOrderPayments.add(orderPayment);

        try {
            mwsOrderPaymentService.saveOmMwsOrderPayment(iRequest, omMwsOrderPayments);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("dapp orderSubmit error : error to saveOmMwsOrderPayment ");
            }
            throw new DAppException(IntegrationConstant.ERROR_LOCK_ORDER_PAYMENT, IntegrationConstant.ERROR_30017,
                    "appNo = " + orderSubmission.getAppNo());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void addPayment(IRequest iRequest, SalesOrder salesOrder) throws DAppException {
        OmMwsOrderPayment couponsPayment = new OmMwsOrderPayment();
        List<OrderPayment> orderPayments = new ArrayList<OrderPayment>();
        couponsPayment.setOrderHeaderId(salesOrder.getHeaderId());
        List<OmMwsOrderPayment> omMwsOrderPayments = mwsOrderPaymentService.getMwsOrderPayments(iRequest,
                couponsPayment);
        for (OmMwsOrderPayment omMwsOrderPayment : omMwsOrderPayments) {
            if (IntegrationConstant.ORDER_PAYMETHOD_ONLPA.equals(omMwsOrderPayment.getPaymentMethod())
                    || IntegrationConstant.ORDER_PAYMETHOD_ECUP.equals(omMwsOrderPayment.getPaymentMethod())) {
                OrderPayment orderPayment = new OrderPayment();
                orderPayment.setOrderHeaderId(omMwsOrderPayment.getOrderHeaderId());
                orderPayment.setSalesOrgId(omMwsOrderPayment.getSalesOrgId());
                orderPayment.setPaymentMethod(omMwsOrderPayment.getPaymentMethod());
                orderPayment.setPaymentAmount(omMwsOrderPayment.getPaymentAmount());
                orderPayment.setTailNumber(omMwsOrderPayment.getTailNumber());
                orderPayment.setRemark("");
                orderPayment.setTransactionNumber(omMwsOrderPayment.getTransactionNumber());
                orderPayment.setVoucherId(omMwsOrderPayment.getVoucherId());
                orderPayment.setStatus("NEW");
                orderPayments.add(orderPayment);

            }
            // 扣减用掉的coupons
            if (IntegrationConstant.ORDER_PAYMETHOD_ECUP.equals(omMwsOrderPayment.getPaymentMethod())) {
                Voucher voucher = voucherMapper.selectByPrimaryKey(omMwsOrderPayment.getVoucherId());
                voucher.setQuantity(voucher.getQuantity() - 1);
                voucherMapper.updateByPrimaryKeySelective(voucher);
            }
        }

        try {
            // 手工暂时设置成付款中以便调用comm service
            salesOrder.setOrderStatus(IntegrationConstant.ORDER_STATUS_PAYIN);
            salesOrderMapper.updateByPrimaryKey(salesOrder);

            // 调用comm service
            commOrderPaymentService.createOrderPayment(iRequest, orderPayments, salesOrder.getHeaderId());

        } catch (Exception e) {
            // TODO: handle exception
            if (logger.isErrorEnabled()) {
                logger.error("订单" + salesOrder.getOrderNumber() + "入库数据失败：" + IntegrationException.getErrorStackTrace(e));
            }
            throw new DAppException(IntegrationConstant.ORDER_PAYMENT_SAVE_ERROR, IntegrationConstant.ERROR_30004,
                    IntegrationConstant.ORDER_PAYMENT_SAVE_ERROR + ":" + IntegrationException.getErrorStackTrace(e));
        }
    }
}

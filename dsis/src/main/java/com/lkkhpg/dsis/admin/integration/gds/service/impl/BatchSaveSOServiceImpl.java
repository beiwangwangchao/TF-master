/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgSoHeader;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgSoLine;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgSoHeaderMapper;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgSoLineMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IBatchSaveSOService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.user.exception.UserException;
import com.lkkhpg.dsis.admin.user.service.IUserInfoService;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.config.mapper.SpmCurrencyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesReturn;
import com.lkkhpg.dsis.common.order.dto.SalesRtnLine;
import com.lkkhpg.dsis.common.order.mapper.SalesAdjustmentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesReturnMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesRtnLineMapper;
import com.lkkhpg.dsis.common.product.dto.PriceListDetail;
import com.lkkhpg.dsis.common.product.mapper.PriceListDetailMapper;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.GsoDetail;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mapper.system.AccountMapper;

/**
 * 批量同步销售资料.
 * 
 * @author wuyichu
 */
@Transactional
@Service
public class BatchSaveSOServiceImpl implements IBatchSaveSOService {

    private final Logger log = LoggerFactory.getLogger(BatchSaveSOServiceImpl.class);
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private IsgSoHeaderMapper isgSoHeaderMapper;
    @Autowired
    private IsgSoLineMapper isgSoLineMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private SalesAdjustmentMapper salesAdjustmentMapper;
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private SalesReturnMapper salesReturnMapper;

    @Autowired
    private SalesRtnLineMapper salesReturnLineMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private IMarketParamService marketParamService;

    @Autowired
    private PriceListDetailMapper priceListDetailMapper;

    @Autowired
    private IParamService paramServiceImpl;

    @Autowired
    private SpmTaxMapper spmTaxMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;

    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;
    
    @Autowired
    private SpmMarketMapper spmMarketMapper;

    public static Long defaultScale = 2L;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<BatchSavePOSTBody> getOrders(String adviseNo, String orgCode) {
        List<Long> marketIds = null;
        try {
            marketIds = gdsUtilService.getMarketIds(orgCode);
        } catch (IntegrationException e1) {
            // 找不到映射组织代码，输出日志直接返回
            log.error("market not exist for marketCode {}", orgCode, e1);
            return null;
        }

        /*
         * SpmMarket spmMarket = spmMarketMapper.selectMarketByCode(posOrgCode);
         * if (spmMarket == null) { if (log.isErrorEnabled()) { log.error(
         * "market not exist for marketCode {}", orgCode); }
         * 
         * return null; }
         */

        List<BatchSavePOSTBody> result = new ArrayList<BatchSavePOSTBody>();
        List<SalesOrder> orders = salesOrderMapper.selectWaitSyncGds(marketIds);
        SimpleDateFormat commonDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BeanCopier headerCopy = BeanCopier.create(BatchSavePOSTBody.class, IsgSoHeader.class, false);
        BeanCopier lineCopy = BeanCopier.create(GsoDetail.class, IsgSoLine.class, false);
        // if (orders == null || orders.isEmpty()) {
        // return result;
        // }
        for (SalesOrder salesOrder : orders) {

            // 订单取消状态，还未同步过的不同步
            if (IntegrationConstant.SO_CHECK_STATUS.equals(salesOrder.getOrderStatus())) {
                Long times = isgSoHeaderMapper.countBySoNo(salesOrder.getOrderNumber());
                if (times != null && times > 0) {
                    result.add(convert2Body(salesOrder, adviseNo, commonDateFormat, headerCopy, lineCopy, orgCode));
                } else {
                    try {

                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("get orders is error adviseNo is {}", adviseNo, e);
                        }
                    } finally {
                        self().updateSalesOrderSyncStaus(salesOrder);
                    }
                }
            } else {
                result.add(convert2Body(salesOrder, adviseNo, commonDateFormat, headerCopy, lineCopy, orgCode));
            }

        }
        // 退货单取值逻辑
        try {
            self().getReturnOrder(adviseNo, orgCode, result, commonDateFormat, headerCopy, lineCopy);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private BatchSavePOSTBody convert2Body(SalesOrder order, String adviseNo, SimpleDateFormat sdf,
            BeanCopier headerCopy, BeanCopier lineCopy, String orgCode) {
        BatchSavePOSTBody result = new BatchSavePOSTBody();
        Account createUser = accountMapper.selectByPrimaryKey(order.getCreatedBy());
        result.setAdviseNo(adviseNo);
        // TODO 编码过长？？
        String orderNumber = order.getOrderNumber();
        if (order.getObjectVersionNumber() < 0) {
            orderNumber = orderNumber.substring(1, orderNumber.length());
        }
        result.setSoNo(orderNumber);
        SpmSalesOrganization salesOrganization = spmSalesOrganizationMapper.selectByPrimaryKey(order.getSalesOrgId());
        if (salesOrganization != null) {
            result.setSoOrgCode(orgCode);
        }
        SpmPeriod period = spmPeriodMapper.selectByPrimaryKey(order.getPeriodId());
        // TODO 奖金月份格式不对？？？
        result.setSoPeriod(period.getPeriodName().replace("-", ""));
        // TODO 退货时候的类型？
        result.setSoType(IntegrationConstant.SO_TYPE_NORMAL);
        result.setSoEntryClass(IntegrationConstant.SO_ENTRY_P);
        result.setSoEntryTime(sdf.format(order.getOrderDate()));
        result.setSoEntryBy((createUser == null) ? null : createUser.getLoginName());
        result.setRefSoNo("");

        IRequest request = RequestHelper.newEmptyRequest();
        RequestHelper.setCurrentRequest(request);
        request.setLocale(BaseConstants.DEFAULT_LANG);

        // 获取组织参数 - 是否启用税
        String enable_tax = marketParamService.getParamValue(request, SystemProfileConstants.SPM_ENABLE_TAX,
                SystemProfileConstants.MARKET, salesOrganization.getMarketId().toString(),
                SystemProfileConstants.ORG_TYPE_MARKET);

        // 获取组织参数 - 商品价格是否含税
        String price_include_tax = marketParamService.getParamValue(request,
                SystemProfileConstants.SPM_PRICE_INCLUDE_TAX, SystemProfileConstants.MARKET,
                salesOrganization.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);

        // 获取市场币种代码
        String currencyCode = marketParamService.getParamValue(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.MARKET, String.valueOf(salesOrganization.getMarketId()),
                SystemProfileConstants.ORG_TYPE_MARKET);
        // 获取对应币种精度
        Long scale = defaultScale;
        SpmCurrency sc = spmCurrencyMapper.selectByPrimaryKey(currencyCode);
        if (null != sc && null != sc.getPrecision()) {
            scale = sc.getPrecision();
        }
        // 获取组织参数税码
        // 计算税率
        List<String> taxCodes = paramServiceImpl.getSalesParamValues(request, "SPM.TAX_CODE",
                salesOrganization.getSalesOrgId());
        SpmTax spmTax = new SpmTax();
        Double taxPercent = 0d;
        // 如果组织参数税码不为空
        if (!(taxCodes == null || taxCodes.isEmpty())) {
            spmTax.setTaxCode(taxCodes.get(0));
            spmTax = spmTaxMapper.queryByTax(spmTax).get(0);
            if (null != spmTax.getTaxPercent()) {
                // 如果获取到税则返回
                taxPercent = spmTax.getTaxPercent().doubleValue();
            }
        }
        SpmMarket market = spmMarketMapper.selectByPrimaryKey(salesOrganization.getMarketId());
        
        // 获取订单运费
        BigDecimal shippingFee = BigDecimal.ZERO;
        SalesLogistics logistics = salesLogisticsMapper.selectByHeaderId(order.getHeaderId(), OrderConstants.NO);
        if (null != logistics && null != logistics.getShippingFee()) {
            shippingFee = logistics.getShippingFee();
        }
        // 对于台湾/香港：总金额 = 订单总金额,销售税 = 0
        result.setLocalTotalAmt(
                (order.getOrderAmt()).setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        result.setLocalTotalSaleTaxAmt(
                BigDecimal.ZERO.setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        // 对于马来：总金额 = 订单总金额-税 , 销售税 = 税
        if (BaseConstants.YES.equals(enable_tax) && BaseConstants.NO.equals(price_include_tax)) {
            result.setLocalTotalAmt((order.getOrderAmt().subtract(order.getTaxAmt()))
                    .setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            result.setLocalTotalSaleTaxAmt(
                    order.getTaxAmt().setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        if (order.getMemberId() != null) {
            Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
            if (null != member) {
                result.setOrderDealerName(
                        (null == member.getChineseName() ? member.getEnglishName() : member.getChineseName()));
                result.setOrderDealerTele(member.getPhoneNo());
                result.setOrderDealerNo(member.getMemberCode());
                result.setSoDealerNo(salesOrganization.getCode());
            }
        } else {
            try {
                User user = userInfoService.selectUserByPrimaryKey(null, order.getCreateUserId());
                if (user != null) {
                    result.setOrderDealerName(user.getUserName());
                    result.setOrderDealerTele(user.getPhoneNo());
                    result.setOrderDealerNo(String.valueOf(order.getCreateUserId()));
                }
            } catch (UserException e) {
                if (log.isErrorEnabled()) {
                    log.error("query account error", e);
                }
            }
        }

        result.setOrderAmt(order.getOrderAmt().setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        result.setOrderDate(sdf.format(order.getOrderDate()));
        result.setLastUpdateBy(String.valueOf(order.getLastUpdatedBy()));
        result.setCreateBy(String.valueOf(order.getCreatedBy()));
        result.setLastUpdateTime(sdf.format(order.getLastUpdateDate()));
        if (OrderConstants.YES.equals(order.getFirstFlag())) {
            result.setFirstSo(Boolean.TRUE);
        } else {
            result.setFirstSo(Boolean.FALSE);
        }
        result.setLocalCurrencyCode(order.getCurrency());

        BigDecimal totalAdjust = salesAdjustmentMapper.sumAmountByHeaderId(order.getHeaderId());
        if (totalAdjust != null) {
            result.setLocalTotalEcoupon(totalAdjust.setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            result.setLocalTotalEcoupon(
                    BigDecimal.ZERO.setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        // result.setLocalTotalRebate(0.0); //本来已经注释掉的，我没有改
        result.setComments(order.getRemark());
        result.setRealTimeProcFlag(OrderConstants.NO);

        List<GsoDetail> details = new ArrayList<GsoDetail>();
        List<SalesLine> lines = order.getLines();
        BigDecimal totalPV = BigDecimal.ZERO;
        BigDecimal localTotalRebate = BigDecimal.ZERO;

        List<String> itemSalesTypes = new ArrayList<String>();
        for (SalesLine line : lines) {
            if (line.getLineId() == null) {
                continue;
            }
            GsoDetail detail = new GsoDetail();
            detail.setLocalSaleType(processSalesType(line.getItemSalesType(), line.getPv())); // GDS固定值
            detail.setProductCode(line.getItemNumber());
            detail.setProductPrice(line.getUnitOrigPrice().doubleValue());
            detail.setProductPoint(line.getPv().doubleValue());
            detail.setLocalSaleQty(line.getQuantity().longValue());
            if (OrderConstants.SALES_STATUS_VOIDED.equals(order.getOrderStatus())) {
                detail.setLocalSaleQty(0L - line.getQuantity().longValue());
            }
            detail.setLocalSalePrice(line.getUnitOrigPrice().doubleValue());
            detail.setLocalSalePoint(line.getPv().doubleValue());
            totalPV = totalPV.add(line.getPv().multiply(line.getQuantity()));
            // TODO 订单行号
            detail.setLineNo(line.getLineNum().intValue());
            detail.setCreateBy(String.valueOf(line.getCreatedBy()));
            detail.setLastUpdateBy(String.valueOf(line.getLastUpdatedBy()));
            detail.setLastUpdateTime(sdf.format(line.getLastUpdateDate()));
            // 商品经销商价格
            BigDecimal distributorPrice = getLocalSaleRebate(line.getLineId(), order.getSalesOrgId(), "DIS",
                    order.getOrderNumber());
            // 商品VIP价格
            BigDecimal vipPrice = getLocalSaleRebate(line.getLineId(), order.getSalesOrgId(), "VIP",
                    order.getOrderNumber());
            if (null == distributorPrice) {
                distributorPrice = BigDecimal.ZERO;
            }
            if (null == vipPrice) {
                vipPrice = BigDecimal.ZERO;
            }
            detail.setLocalSaleRebate(vipPrice.doubleValue() - distributorPrice.doubleValue());
            // 订单类型为VIP时才去计算该字段逻辑
            if (!order.getOrderType().equals("VIPP")) {
                detail.setLocalSaleRebate(0d);
            }
            
          //加拿大市场特殊逻辑：如果是该市场下的特定商品，则税率为7%（后面调整）
            if("15160502".equals(line.getItemNumber())){
                if("SBC".equals(salesOrganization.getCode())){
                    taxPercent = 7d;
                }
                if("SONT".equals(salesOrganization.getCode())){
                    taxPercent = 0d;
                }
            }
            
            // 对于台湾/香港：销售税 = 0
            detail.setLocalSaleTaxAmt(0.0);
            // 对于马来：销售税 = 单件税
            if (BaseConstants.YES.equals(enable_tax) && BaseConstants.NO.equals(price_include_tax)) {
                if (null != taxPercent) {
                    detail.setLocalSaleTaxAmt(
                            new BigDecimal((line.getUnitOrigPrice().doubleValue() * (taxPercent / 100d)))
                                    .setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
                } else {
                    detail.setLocalSaleTaxAmt(null);
                }
            }

            // 需要返回的订单行：产品编码&销售类型 唯一
            // 处理逻辑：丢掉行号大的，保留行号小的，并且把行号大的那行的税和数量加到行号小的行的上面
            if (itemSalesTypes.size() == 0) {
                itemSalesTypes.add(line.getItemSalesType());
            }
            boolean isEquals = false;
            for (int i = 0; i < details.size(); i++) {
                if (detail.getProductCode().equals(details.get(i).getProductCode())
                        && line.getItemSalesType().equals(itemSalesTypes.get(i))) {
                    if (detail.getLineNo() > details.get(i).getLineNo()) {
                        details.get(i).setLocalSaleQty(detail.getLocalSaleQty() + details.get(i).getLocalSaleQty());
                        details.get(i).setLocalSaleTaxAmt(new BigDecimal(detail.getLocalSaleTaxAmt())
                                .add(new BigDecimal(details.get(i).getLocalSaleTaxAmt())).doubleValue());
                    } else {
                        detail.setLocalSaleQty(detail.getLocalSaleQty() + details.get(i).getLocalSaleQty());
                        detail.setLocalSaleTaxAmt(new BigDecimal(detail.getLocalSaleTaxAmt())
                                .add(new BigDecimal(details.get(i).getLocalSaleTaxAmt())).doubleValue());
                        details.remove(details.get(i));
                        details.add(detail);
                        itemSalesTypes.remove(i);
                        itemSalesTypes.add(line.getItemSalesType());
                    }
                    isEquals = true;
                }
            }
            if (!isEquals) {
                itemSalesTypes.add(line.getItemSalesType());
                details.add(detail);
            }

            // 本國合計優惠差額
            localTotalRebate = localTotalRebate.add(vipPrice.subtract(distributorPrice).multiply(line.getQuantity()));
        }
        // 订单类型为VIP时才去计算该字段逻辑
        if (!order.getOrderType().equals("VIPP")) {
            localTotalRebate = BigDecimal.ZERO;
        }
        result.setLocalTotalRebate(localTotalRebate.setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        // 积分
        result.setLocalTotalPoint(totalPV.setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        IsgSoHeader header = new IsgSoHeader();
        headerCopy.copy(result, header, null);
        header.setProcessStatus(IntegrationConstant.PROCESS_STATUS_P);
        header.setFirstSoNo(order.getFirstFlag());
        header.setObjectVersionNumber(order.getObjectVersionNumber());
        header.setHeaderId(order.getHeaderId());
        header.setSoNo(order.getOrderNumber());
        isgSoHeaderMapper.insert(header);
        for (GsoDetail gsoDetail : details) {
            IsgSoLine isgSoLine = new IsgSoLine();
            lineCopy.copy(gsoDetail, isgSoLine, null);
            isgSoLine.setInterfaceId(String.valueOf(header.getInterfaceId()));
            isgSoLine.setProcessStatus(IntegrationConstant.PROCESS_STATUS_P);
            isgSoLine.setAdviseNo(adviseNo);
            isgSoLine.setGsoMaster(order.getOrderNumber());
            isgSoLine.setObjectVersionNumber(order.getObjectVersionNumber());
            isgSoLineMapper.insert(isgSoLine);
        }
        result.setGsoDetails(details);

        // 订单已失效则传值
        if (OrderConstants.SALES_STATUS_VOIDED.equals(order.getOrderStatus())) {
            result.setRefSoNo(orderNumber);
            // 失效销售订单：头订单编号+R
            result.setSoNo(orderNumber + "R");
            // 失效的销售订单，订单日期是已关闭的月份时，传12
            if (period.getClosingStatus().equals("Y")) {
                result.setSoType(IntegrationConstant.SO_TYPE_VOID);
            }
            // 总金额取反
            result.setLocalTotalAmt(((order.getOrderAmt()).negate())
                    .setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            if (BaseConstants.YES.equals(enable_tax) && BaseConstants.NO.equals(price_include_tax)) {
                result.setLocalTotalAmt((order.getTaxAmt().subtract(order.getOrderAmt()))
                        .setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            }
            // PV取反
            result.setLocalTotalPoint(
                    totalPV.negate().setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            // 销售税取反
            result.setLocalTotalSaleTaxAmt(
                    order.getTaxAmt().negate().setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            // rebate取反
            result.setLocalTotalRebate(
                    localTotalRebate.negate().setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            // 行上税取反
            for (GsoDetail gsoDetail : details) {
                gsoDetail.setLocalSaleTaxAmt(0d - gsoDetail.getLocalSaleTaxAmt());
            }
            result.setGsoDetails(details);

        } else if (OrderConstants.SALES_STATUS_TO_BE_CONFIRM.equals(order.getOrderStatus())
                || OrderConstants.SALES_STATUS_COMPELETED.equals(order.getOrderStatus())) {
            result.setRefSoNo("");
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateOrders(String adviseNo, List<BatchSavePOSTBody> requestData, List<BatchSavePOSTResponse> response,
            Exception exception) {
        Date proccessTime = new Date();
        String market = null;
        if (requestData != null && requestData.size() > 0) {
            BatchSavePOSTBody batchSavePOSTBody = requestData.get(0);
            try {
                market = gdsUtilService.getOrgCode(batchSavePOSTBody.getSoOrgCode());
            } catch (IntegrationException e) {
                log.error("error to mapping the org code!", e);
            }
        }
        if (exception != null) {
            if (log.isErrorEnabled()) {
                log.error("sync salesOrder faild {}", exception);
            }
            for (BatchSavePOSTBody body : requestData) {
                salesOrderMapper.updateSyncByOrderNumber(body.getSoNo(), OrderConstants.NO, adviseNo);
                isgSoHeaderMapper.updateProccessStatusByAdviseNoAndSONO(adviseNo, body.getSoNo(),
                        IntegrationConstant.PROCESS_STATUS_E, exception.getMessage(), proccessTime);
                isgSoLineMapper.updateProccessStatusByAdviseNoAndSONO(adviseNo, body.getSoNo(),
                        IntegrationConstant.PROCESS_STATUS_E, exception.getMessage(), proccessTime);
            }
            return;
        }
        for (BatchSavePOSTResponse batchSavePOSTResponse : response) {
            String soNo = batchSavePOSTResponse.getSoNo();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderNumber", soNo);
            map.put("market", market);
            map.put("returnNumber", soNo);
            // 在pos系统中根据去除首位的订单编号与GDS返回的编号进行较，若能找到则更新这张订单的同步标记
            SalesOrder salesOrder = salesOrderMapper.selectOrderBySubNumber(map);
            SalesReturn salesReturn = salesReturnMapper.selectRObySubRm(map);
            if (salesOrder != null || salesReturn != null) {
                if (salesOrder != null) {
                    soNo = salesOrder.getOrderNumber();
                }
                if (salesReturn != null) {
                    soNo = salesReturn.getReturnNumber();
                }
            }
            // 转换void单为pos系统的订单，去掉R标识
            if (soNo.endsWith("R")) {
                soNo = soNo.substring(0, soNo.length() - 1);
            }
            if (batchSavePOSTResponse.getSuccess() != null && batchSavePOSTResponse.getSuccess()) {
                salesOrderMapper.updateSyncByOrderNumber(soNo, OrderConstants.YES, adviseNo);
                salesReturnMapper.updateSyncFlagByReturnNumber(soNo, OrderConstants.YES);
                isgSoHeaderMapper.updateProccessStatusByAdviseNoAndSONO(adviseNo, batchSavePOSTResponse.getSoNo(),
                        IntegrationConstant.PROCESS_STATUS_S, IntegrationConstant.PROCESS_MESSAGE_SUCCESS,
                        proccessTime);
                isgSoLineMapper.updateProccessStatusByAdviseNoAndSONO(adviseNo, batchSavePOSTResponse.getSoNo(),
                        IntegrationConstant.PROCESS_STATUS_S, IntegrationConstant.PROCESS_MESSAGE_SUCCESS,
                        proccessTime);
            } else {
                salesOrderMapper.updateSyncByOrderNumber(soNo, OrderConstants.NO, adviseNo);
                salesReturnMapper.updateSyncFlagByReturnNumber(soNo, OrderConstants.NO);
                isgSoHeaderMapper.updateProccessStatusByAdviseNoAndSONO(adviseNo, soNo,
                        IntegrationConstant.PROCESS_STATUS_E, batchSavePOSTResponse.getMessage(), proccessTime);
                isgSoLineMapper.updateProccessStatusByAdviseNoAndSONO(adviseNo, soNo,
                        IntegrationConstant.PROCESS_STATUS_E, IntegrationConstant.PROCESS_STATUS_E, proccessTime);
            }
        }

    }

    @Override
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateSalesOrderSyncStaus(SalesOrder salesOrder) {
        salesOrder.setSyncFlag(IntegrationConstant.YES);
        salesOrderMapper.updateByPrimaryKeySelective(salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void getReturnOrder(String adviseNo, String gdsOrgCode, List<BatchSavePOSTBody> result,
            SimpleDateFormat commonDateFormat, BeanCopier headerCopy, BeanCopier lineCopy) {
        IRequest request = RequestHelper.newEmptyRequest();
        RequestHelper.setCurrentRequest(request);
        request.setLocale(BaseConstants.DEFAULT_LANG);
        String orgCode = null;
        List<Long> marketIdList = new ArrayList<Long>();
        try {
            marketIdList = gdsUtilService.getMarketIds(gdsOrgCode);
        } catch (IntegrationException e) {
            log.error(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING, e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("marketCode", orgCode);
        // 根据当前市场取得销售组织Id
        // TODO:可能存在多个?
        // request.setAttribute(SystemProfileConstants.MARKET_ID, marketId);
        map.put("returnStatus", "NEW");
        map.put("syncFlag", BaseConstants.NO);
        map.put("marketIdList", marketIdList);
        // 获取未同步(同步标记为N)的数据
        List<SalesReturn> returns = salesReturnMapper.selectBySync(map);

        for (SalesReturn return1 : returns) {
            SpmSalesOrganization organization = spmSalesOrganizationMapper.selectByPrimaryKey(return1.getSalesOrgId());
            // 获取组织参数 - 是否启用税
            String enable_tax = marketParamService.getParamValue(request, SystemProfileConstants.SPM_ENABLE_TAX,
                    SystemProfileConstants.MARKET, organization.getMarketId().toString(),
                    SystemProfileConstants.ORG_TYPE_MARKET);
            // 获取组织参数 - 商品价格是否含税
            String price_include_tax = marketParamService.getParamValue(request,
                    SystemProfileConstants.SPM_PRICE_INCLUDE_TAX, SystemProfileConstants.MARKET,
                    organization.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
            // 获取组织参数税码
            // 计算税率
            List<String> taxCodes = paramServiceImpl.getSalesParamValues(request, "SPM.TAX_CODE",
                    organization.getSalesOrgId());
            SpmTax spmTax = new SpmTax();
            Double taxPercent = 0d;
            // 如果组织参数税码不为空
            if (!(taxCodes == null || taxCodes.isEmpty())) {
                spmTax.setTaxCode(taxCodes.get(0));
                spmTax = spmTaxMapper.queryByTax(spmTax).get(0);
                if (null != spmTax.getTaxPercent()) {
                    // 如果获取到税则返回
                    taxPercent = spmTax.getTaxPercent().doubleValue();
                }
            }
            // 获取市场币种代码
            String currencyCode = marketParamService.getParamValue(request, SystemProfileConstants.SPM_CURRENCY,
                    SystemProfileConstants.MARKET, String.valueOf(organization.getMarketId()),
                    SystemProfileConstants.ORG_TYPE_MARKET);
            // 获取对应币种精度
            Long scale = defaultScale;
            SpmCurrency sc = spmCurrencyMapper.selectByPrimaryKey(currencyCode);
            if (null != sc && null != sc.getPrecision()) {
                scale = sc.getPrecision();
            }
            request.setAttribute(SystemProfileConstants.SALES_ORG_ID, return1.getSalesOrgId());
            BatchSavePOSTBody batchSavePOSTBody = new BatchSavePOSTBody();
            batchSavePOSTBody.setAdviseNo(adviseNo);
            String returnStatus = return1.getReturnStatus();
            if (OrderConstants.RETURN_STATUS_RECLM.equals(returnStatus)
                    || OrderConstants.RETURN_STATUS_EXCLM.equals(returnStatus)) {
                batchSavePOSTBody.setSoType("12");
            } else {
                batchSavePOSTBody.setSoType("01");
            }
            batchSavePOSTBody.setSoOrgCode(gdsOrgCode);
            batchSavePOSTBody
                    .setSoDealerNo(spmSalesOrganizationMapper.selectByPrimaryKey(return1.getSalesOrgId()).getCode());
            String returnDateString = gdsUtilService.toDateString(return1.getReturnDate());
            batchSavePOSTBody.setSoEntryClass(IntegrationConstant.SO_ENTRY_P);
            batchSavePOSTBody.setSoEntryTime(commonDateFormat.format(return1.getReturnDate()));
            // 根据退货单头上的orderHeaderId取得正向销售订单信息
            SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKey(return1.getOrderHeaderId());
            if (salesOrder == null) {
                continue;
            }
            // SoPeriod逻辑:
            // 1、使用退货日期和期间表比较，得到一个期间
            // 2、拿这个期间和原单的期间比较，若这个期间<原单期间，则把原单期间写到退单的期间；若这个期间>=原单期间，则把这个期间写道退单的期间
            List<SpmPeriod> periods = spmPeriodMapper.selectPeriodByDateAndOrgId(return1.getReturnDate(),
                    organization.getMarketId());
            SpmPeriod returnPeriod = null;
            if (periods != null && periods.size() > 0) {
                returnPeriod = periods.get(0);
            }
            Long periodId = salesOrder.getPeriodId();
            SpmPeriod salesPeriod = spmPeriodMapper.selectByPrimaryKey(periodId);
            if (returnPeriod != null && salesPeriod != null) {
                if (returnPeriod.getPeriodName().compareTo(salesPeriod.getPeriodName()) < 0) {
                    batchSavePOSTBody.setSoPeriod(salesPeriod.getPeriodName());
                } else {
                    batchSavePOSTBody.setSoPeriod(returnPeriod.getPeriodName());
                }
            } else {
                if (log.isErrorEnabled()) {
                    log.error("returnPeriod:{}或salesPeriod:{}为空",returnPeriod,salesPeriod);
                }
            }
            String returnNumber = return1.getReturnNumber();
            if (return1.getObjectVersionNumber() < 0) {
                returnNumber = returnNumber.substring(1, returnNumber.length());
            }
            batchSavePOSTBody.setSoNo(returnNumber);
            Long memberId = salesOrder.getMemberId();
            Member member = memberMapper.selectByPrimaryKey(memberId);
            Account createUser = accountMapper.selectByPrimaryKey(salesOrder.getCreatedBy());
            batchSavePOSTBody.setSoEntryBy((createUser == null) ? null : createUser.getLoginName());
            if (member == null) {
                batchSavePOSTBody.setOrderDealerNo("");
            } else {
                batchSavePOSTBody.setOrderDealerNo(member.getMemberCode());
            }
            // OrderDate取退货单的退货日期
            batchSavePOSTBody.setOrderDate(returnDateString);
            batchSavePOSTBody.setOrderAmt(
                    return1.getAmount().setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            if (member == null) {
                batchSavePOSTBody.setOrderDealerName("");
                batchSavePOSTBody.setOrderDealerTele("");
            } else {
                batchSavePOSTBody.setOrderDealerName(
                        (null == member.getChineseName() ? member.getEnglishName() : member.getChineseName()));
                batchSavePOSTBody.setOrderDealerTele(member.getPhoneNo());
            }
            batchSavePOSTBody.setFirstSo(false);
            String orderNumber = salesOrder.getOrderNumber();
            if (return1.getObjectVersionNumber() < 0 || salesOrder.getObjectVersionNumber() < 0) {
                orderNumber = orderNumber.substring(1, orderNumber.length());
            }
            batchSavePOSTBody.setRefSoNo(orderNumber);
            batchSavePOSTBody.setLocalCurrencyCode(salesOrder.getCurrency());
            // 获取订单运费
            BigDecimal shippingFee = BigDecimal.ZERO;
            SalesLogistics logistics = salesLogisticsMapper.selectByHeaderId(salesOrder.getHeaderId(),
                    OrderConstants.NO);
            if (null != logistics && null != logistics.getShippingFee()) {
                shippingFee = logistics.getShippingFee();
            }
            // 区分马来和其它市场
            if (BaseConstants.YES.equals(enable_tax) && BaseConstants.NO.equals(price_include_tax)) {
                /*
                 * batchSavePOSTBody.setLocalTotalAmt(
                 * -(return1.getAmount().doubleValue()) -
                 * (return1.getTaxAmount().doubleValue()));
                 */
                batchSavePOSTBody.setLocalTotalAmt((return1.getAmount().subtract(return1.getTaxAmount()).negate())
                        .setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            } else {
                batchSavePOSTBody.setLocalTotalAmt((return1.getAmount()).negate()
                        .setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            }
            BigDecimal totalAdjust = salesAdjustmentMapper.sumAmountByHeaderId(salesOrder.getHeaderId());
            if (totalAdjust != null) {
                batchSavePOSTBody.setLocalTotalEcoupon(
                        totalAdjust.setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            } else {
                batchSavePOSTBody.setLocalTotalEcoupon(
                        BigDecimal.ZERO.setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            }
            map.put("headerId", return1.getOrderHeaderId());
            map.put("salesOrgId", return1.getSalesOrgId());
            // 3、当确定了退单的期间后，拿退单的期间和原订单的期间作比较，若相等，则localTotalPoint取值不变；若不等，则为0
            if (salesPeriod.getPeriodName().equals(batchSavePOSTBody.getSoPeriod())) {
                batchSavePOSTBody.setLocalTotalPoint(
                        return1.getPoint().negate().setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            } else {
                batchSavePOSTBody.setLocalTotalPoint(0d);
            }
            batchSavePOSTBody.setRealTimeProcFlag(OrderConstants.NO);
            // 区分马来和其它市场
            if (BaseConstants.YES.equals(enable_tax) && BaseConstants.NO.equals(price_include_tax)) {
                batchSavePOSTBody.setLocalTotalSaleTaxAmt(return1.getTaxAmount().negate()
                        .setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            } else {
                batchSavePOSTBody.setLocalTotalSaleTaxAmt(
                        BigDecimal.ZERO.setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            }

            List<GsoDetail> gsoDetails = new ArrayList<GsoDetail>();
            map.put("returnId", return1.getReturnId());
            map.put("headerId", salesOrder.getHeaderId());
            // 根据退货单returnId获取其行信息
            List<SalesRtnLine> salesRtnLines = salesReturnLineMapper.selectByReturnId(map);
            BigDecimal localTotalRebate = BigDecimal.ZERO;

            List<String> itemSalesTypes = new ArrayList<String>();
            for (SalesRtnLine salesRtnLine : salesRtnLines) {
                map.put("lineId", salesRtnLine.getOrderLineId());
                SalesLine salesLine = salesLineMapper.selectOrderLine(map);
                GsoDetail gsoDetail = new GsoDetail();
                gsoDetail.setProductCode(salesLine.getItemNumber());
                gsoDetail.setProductPrice(salesLine.getUnitOrigPrice().doubleValue());
                gsoDetail.setProductPoint(salesLine.getPv().doubleValue());
                gsoDetail.setLocalSaleType(processSalesType(salesLine.getItemSalesType(), salesLine.getPv()));
                gsoDetail.setLocalSalePrice(salesLine.getUnitOrigPrice().doubleValue());
                if (batchSavePOSTBody.getLocalTotalPoint() != 0d) {
                    gsoDetail.setLocalSalePoint(salesLine.getPv().doubleValue());
                } else {
                    gsoDetail.setLocalSalePoint(0d);
                }
                // 退货单行商品经销商价格
                BigDecimal distributorPrice = getLocalSaleRebate(salesRtnLine.getOrderLineId(),
                        salesRtnLine.getSalesOrgId(), "DIS", salesOrder.getOrderNumber());
                // 退货单行商品VIP价格
                BigDecimal vipPrice = getLocalSaleRebate(salesRtnLine.getOrderLineId(), salesRtnLine.getSalesOrgId(),
                        "VIP", salesOrder.getOrderNumber());
                if (null == distributorPrice) {
                    distributorPrice = BigDecimal.ZERO;
                }
                if (null == vipPrice) {
                    vipPrice = BigDecimal.ZERO;
                }
                // 退货单类型为VIP时才去计算该字段逻辑
                if (!salesOrder.getOrderType().equals("VIPP")) {
                    gsoDetail.setLocalSaleRebate(0d);
                } else {
                    gsoDetail.setLocalSaleRebate(vipPrice.subtract(distributorPrice).doubleValue());
                }
                gsoDetail.setLocalSaleQty(-salesRtnLine.getQuantity().longValue());
                gsoDetail.setLineNo(salesLine.getLineNum().intValue());
                // 对于台湾/香港：销售税 = 0
                gsoDetail.setLocalSaleTaxAmt(0.0);
                // 对于马来：销售税 = -单件税

                if (BaseConstants.YES.equals(enable_tax) && BaseConstants.NO.equals(price_include_tax)) {
                    if (null != taxPercent) {
                        gsoDetail.setLocalSaleTaxAmt(
                                -(new BigDecimal((salesLine.getUnitOrigPrice().doubleValue() * (taxPercent / 100d)))
                                        .setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue()));
                    } else {
                        gsoDetail.setLocalSaleTaxAmt(null);
                    }
                }

                // 需要返回的订单行：产品编码&销售类型 唯一
                // 处理逻辑：丢掉行号大的，保留行号小的，并且把行号大的那行的税和数量加到行号小的行的上面
                if (itemSalesTypes.size() == 0) {
                    itemSalesTypes.add(salesLine.getItemSalesType());
                }
                boolean isEquals = false;
                for (int i = 0; i < gsoDetails.size(); i++) {
                    if (gsoDetail.getProductCode().equals(gsoDetails.get(i).getProductCode())
                            && salesLine.getItemSalesType().equals(itemSalesTypes.get(i))) {
                        if (gsoDetail.getLineNo() > gsoDetails.get(i).getLineNo()) {
                            gsoDetails.get(i)
                                    .setLocalSaleQty(gsoDetail.getLocalSaleQty() + gsoDetails.get(i).getLocalSaleQty());
                            gsoDetails.get(i).setLocalSaleTaxAmt(new BigDecimal(gsoDetail.getLocalSaleTaxAmt())
                                    .add(new BigDecimal(gsoDetails.get(i).getLocalSaleTaxAmt())).doubleValue());
                        } else {
                            gsoDetail
                                    .setLocalSaleQty(gsoDetail.getLocalSaleQty() + gsoDetails.get(i).getLocalSaleQty());
                            gsoDetail.setLocalSaleTaxAmt(new BigDecimal(gsoDetail.getLocalSaleTaxAmt())
                                    .add(new BigDecimal(gsoDetails.get(i).getLocalSaleTaxAmt())).doubleValue());
                            gsoDetails.remove(gsoDetails.get(i));
                            gsoDetails.add(gsoDetail);
                            itemSalesTypes.remove(i);
                            itemSalesTypes.add(salesLine.getItemSalesType());
                        }
                        isEquals = true;
                    }
                }
                if (!isEquals) {
                    itemSalesTypes.add(salesLine.getItemSalesType());
                    gsoDetails.add(gsoDetail);
                }
                // 4、当确定了退单的期间后，拿退单的期间和原订单的期间作比较，若相等，则localTotalRebate取值不变；若不等，则为0
                if (salesPeriod.getPeriodName().equals(batchSavePOSTBody.getSoPeriod())) {
                    // 本國合計優惠差額
                    localTotalRebate = localTotalRebate
                            .add(vipPrice.subtract(distributorPrice).multiply(salesRtnLine.getQuantity()));
                } else {
                    localTotalRebate = BigDecimal.ZERO;
                }
            }
            // 退货单类型为VIP时才去计算该字段逻辑
            if (!salesOrder.getOrderType().equals("VIPP")) {
                localTotalRebate = BigDecimal.ZERO;
            }
            // batchSavePOSTBody.setLocalTotalRebate(0-localTotalRebate); //取负值
            batchSavePOSTBody.setLocalTotalRebate(
                    localTotalRebate.negate().setScale(scale.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue());
            batchSavePOSTBody.setGsoDetails(gsoDetails);

            IsgSoHeader isgSoHeader = new IsgSoHeader();
            headerCopy.copy(batchSavePOSTBody, isgSoHeader, null);
            isgSoHeader.setProcessStatus(IntegrationConstant.PROCESS_STATUS_P);
            // isgSoHeader.setFirstSoNo(order.getFirstFlag());
            isgSoHeader.setObjectVersionNumber(salesOrder.getObjectVersionNumber());
            isgSoHeader.setAdviseNo(adviseNo);
            isgSoHeader.setHeaderId(return1.getReturnId());
            isgSoHeader.setSoNo(return1.getReturnNumber());
            isgSoHeaderMapper.insert(isgSoHeader);
            for (GsoDetail gsoDetail : gsoDetails) {
                IsgSoLine isgSoLine = new IsgSoLine();
                lineCopy.copy(gsoDetail, isgSoLine, null);
                isgSoLine.setInterfaceId(String.valueOf(isgSoHeader.getInterfaceId()));
                isgSoLine.setProcessStatus(IntegrationConstant.PROCESS_STATUS_P);
                isgSoLine.setAdviseNo(adviseNo);
                isgSoLine.setGsoMaster(salesOrder.getOrderNumber());
                isgSoLine.setObjectVersionNumber(salesOrder.getObjectVersionNumber());
                isgSoLineMapper.insert(isgSoLine);
            }
            result.add(batchSavePOSTBody);
        }
        // } else {
        // log.error(IntegrationException.MSG_ERROR_CURRENT_SALES_ORG_ID_NULL);
        // }
    }

    private String processSalesType(String orderType, BigDecimal pv) {
        switch (orderType) {
        case "PURC":
            if (pv.equals(BigDecimal.ZERO)) {
                return "04";
            } else {
                return "00";
            }
        case "FREE":
            return "03";
        case "EXCH":
            return "07";
        case "GIFT":
            return "03";
        case "REDE":
            return "03";
        default:
            return "00";
        }
    }

    private BigDecimal getLocalSaleRebate(Long lineId, Long salesOrgId, String priceType, String ordedrNumber) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("lineId", lineId);
        map.put("salesOrgId", salesOrgId);
        map.put("priceType", priceType);
        map.put("orderNumber", ordedrNumber);
        PriceListDetail priceListDetail = priceListDetailMapper.selectByItemAndSalesOrg(map);
        if (null == priceListDetail) {
            priceListDetail = priceListDetailMapper.selectByItemAndSalesOrgAndCurrentDate(map);
            if (null == priceListDetail) {
                return BigDecimal.ZERO;
            }
        }
        BigDecimal price = priceListDetail.getUnitPrice();
        return price;
    }

}

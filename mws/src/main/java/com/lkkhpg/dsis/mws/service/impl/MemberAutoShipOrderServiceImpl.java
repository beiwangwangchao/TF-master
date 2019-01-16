/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.common.config.dto.SpmCity;
import com.lkkhpg.dsis.common.config.dto.SpmCountry;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmState;
import com.lkkhpg.dsis.common.config.mapper.SpmCurrencyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemCardMapper;
import com.lkkhpg.dsis.common.order.dto.AutoshipLine;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.AutoshipLineMapper;
import com.lkkhpg.dsis.common.order.mapper.AutoshipOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.ISalesSitesService;
import com.lkkhpg.dsis.common.service.ISpmCityService;
import com.lkkhpg.dsis.common.service.ISpmCountryService;
import com.lkkhpg.dsis.common.service.ISpmStateService;
import com.lkkhpg.dsis.mws.dto.AutoShipCriteria;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.service.IMemberAutoShipOrderService;
import com.lkkhpg.dsis.mws.service.IMemberSiteService;
import com.lkkhpg.dsis.mws.service.IProductService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员自动订单Service接口实现类.
 * 
 * @author gulin
 *
 */
@Service
@Transactional
public class MemberAutoShipOrderServiceImpl implements IMemberAutoShipOrderService {
    /**
     * 税率/100.
     */
    public static final int RATE_HUNDRED = 100;

    /**
     * 分页大小-10.
     */
    public static final int NUMBER_TEN = 10;

    @Autowired
    private AutoshipOrderMapper autoshipOrderMapper;

    @Autowired
    private AutoshipLineMapper autoshipLineMapper;

    @Autowired
    private IProductService productService;

    @Autowired
    private IParamService paramService;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;

    @Autowired
    private MemCardMapper memCardMapper;

    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;

    @Autowired
    private ISalesSitesService salesSitesService;

    @Autowired
    private IMemberSiteService memberSiteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ISpmCountryService spmCountryService;

    @Autowired
    private ISpmStateService spmStateService;

    @Autowired
    private ISpmCityService spmCityService;
    
    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Override
    public Map<String, Object> queryAutoShipProducts(IRequest request) {
        Long memberId = null;
        Product product;
        Map<String, Object> autoShipInfo = new HashMap<String, Object>();
        List<Product> autoShipProducts = new ArrayList<Product>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalPV = BigDecimal.ZERO;
        BigDecimal totalPoint = BigDecimal.ZERO;
        if (request != null) {
            memberId = Long.parseLong(request.getAttribute(Member.FIELD_MEMBER_ID).toString());
        }
        AutoshipOrder autoShipOrder = autoshipOrderMapper.selectAutoShipOrderByMember(memberId, null);
        if (null == autoShipOrder) {
            return null;
        }
        // 获取信用卡信息
        MemCard memCard = memCardMapper.selectByPrimaryKey(autoShipOrder.getCreditCardId());
        autoShipInfo.put("creditCard", memCard);

        // 获取销售区域信息，便于页面展示
        SpmSalesOrganization autoSalesOrg = spmSalesOrganizationMapper
                .selectByPrimaryKey(autoShipOrder.getSalesOrgId());
        autoShipInfo.put("autoSalesOrgId", autoSalesOrg.getSalesOrgId());
        autoShipInfo.put("autoSalesOrgName", autoSalesOrg.getName());
        // 获取销售区域本位币符号，若没有记录的话也能展示币种
        SpmCurrency currency = new SpmCurrency();
        List<String> currencyCode = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY, "SALES",
                autoShipOrder.getSalesOrgId());
        currency.setCurrencyCode(currencyCode.get(0));
        List<SpmCurrency> currencies = spmCurrencyMapper.querySpmCurrency(currency);
        autoShipInfo.put("currency", currencies.get(0).getSymbol());
        autoShipInfo.put("currencyCode", currencies.get(0).getCurrencyCode());
        List<AutoshipLine> lines = autoshipLineMapper.selectSimpleLine(autoShipOrder.getAutoshipId());
        for (AutoshipLine temp : lines) {
            product = new Product();
            product.setItemId(temp.getItemId());
            product.setOrgId(autoShipOrder.getSalesOrgId());
            product.setFunctionArea("AUTOS");
            List<Product> products = productService.getSimpleProductsByWhereClause(request, product, 1, -1);
            if (products.size() > 0) {
                Product autoShipProduct = products.get(0);
                if (null == autoShipProduct.getTax()) {
                    autoShipInfo.put("taxRate", BigDecimal.ZERO);
                } else {
                    autoShipInfo.put("taxRate", autoShipProduct.getTax());
                }
                if (null != temp.getQuantity()) {
                    autoShipProduct.setItemAmount(Long.parseLong(temp.getQuantity().toString()));
                }
                totalPrice = totalPrice
                        .add(autoShipProduct.getDisAmt().multiply(new BigDecimal(autoShipProduct.getItemAmount())));
                totalPV = totalPV
                        .add(autoShipProduct.getPvAmt().multiply(new BigDecimal(autoShipProduct.getItemAmount())));
                autoShipProducts.add(autoShipProduct);
            }
        }

        // 根据组织参数计算销售积分.
        List<String> codeRate = paramService.getParamValues(request, "SPM.SALES_POINT_RATE", "SALES",
                autoShipOrder.getSalesOrgId());
        List<String> codePointLimit = paramService.getParamValues(request, "SPM.SALES_POINT_LIMIT", "SALES",
                autoShipOrder.getSalesOrgId());
        String pointRate = "";
        String pointLimit = "";
        if (!codeRate.isEmpty()) {
            pointRate = codeRate.get(0);
            autoShipInfo.put("pointRate", new BigDecimal(pointRate));
            totalPoint = totalPrice.multiply(new BigDecimal(pointRate).divide(new BigDecimal(RATE_HUNDRED))).setScale(0,
                    BigDecimal.ROUND_HALF_UP);
            if (!codePointLimit.isEmpty()) {
                pointLimit = codePointLimit.get(0);
                autoShipInfo.put("pointLimit", new BigDecimal(pointLimit));
                if (new BigDecimal(pointLimit).compareTo(totalPoint) < 0) {
                    totalPoint = new BigDecimal(pointLimit);
                }
            } else {
                autoShipInfo.put("pointLimit", new BigDecimal(-1));
            }
        } else {
            autoShipInfo.put("pointRate", BigDecimal.ZERO);
            autoShipInfo.put("pointLimit", new BigDecimal(-1));
        }
        // 获取自动订货单历史地址信息
        List<SalesSites> sites = salesSitesService.getSitesByHeaderId(request, autoShipOrder.getAutoshipId(), "Y");
        autoShipInfo.put("sites", sites);
        // 获取自动订货单历史物流信息
        SalesLogistics historyLogistics = salesLogisticsMapper.selectByHeaderId(autoShipOrder.getAutoshipId(),
                OrderConstants.YES);
        autoShipInfo.put("historyLogistics", historyLogistics);
        autoShipInfo.put("products", autoShipProducts);
        autoShipInfo.put("totalPV", totalPV);
        autoShipInfo.put("totalPrice", totalPrice);
        autoShipInfo.put("totalPoint", totalPoint);
        autoShipInfo.put("deliveryType", autoShipOrder.getDeliveryType());
        return autoShipInfo;
    }

    @Override
    public List<Product> queryOptionalProducts(IRequest request, AutoShipCriteria criteria) {
        Long memberId = null;
        if (request != null) {
            memberId = Long.parseLong(request.getAttribute(Member.FIELD_MEMBER_ID).toString());
        }
        AutoshipOrder autoShipOrder = autoshipOrderMapper.selectAutoShipOrderByMember(memberId, null);
        return productService.getFastProductsByWhereClause(request, criteria.getItemIds(),
                autoShipOrder.getSalesOrgId(), criteria.getItemName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> updateAutoShipLine(IRequest request, List<Product> products, AutoshipOrder autoshipOrderInfo)
            throws CommOrderException {
        List<SalesSites> salesSites = autoshipOrderInfo.getSalesSites();
        SalesLogistics logistics = autoshipOrderInfo.getLogistics();
        String deliveryType = autoshipOrderInfo.getDeliveryType();
        Long memberId = null;
        Long salesOrgId = null;
        BigDecimal minSum = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalPV = BigDecimal.ZERO;
        BigDecimal totalPoint = BigDecimal.ZERO;
        BigDecimal freight = BigDecimal.ZERO;
        List<AutoshipLine> updateLines = new ArrayList<AutoshipLine>();
        List<String> result = new ArrayList<String>();
        if (request != null) {
            memberId = Long.parseLong(request.getAttribute(Member.FIELD_MEMBER_ID).toString());
        }
        AutoshipOrder autoShipOrder = autoshipOrderMapper.selectAutoShipOrderByMember(memberId, null);
        salesOrgId = autoShipOrder.getSalesOrgId();
        // 计算商品销售积分，pv值，商品总金额，运费
        List<Product> productsInfo = getProductInfo(request, products, salesOrgId);
        // 校验是否商品数量是否大于最小购买数，若没有，则返回提示
        for (Product temp : productsInfo) {
            if (temp.getMinOrderQuantity() > temp.getItemAmount()) {
                result.add(temp.getItemName() + "_" + temp.getMinOrderQuantity());
                continue;
            }
        }
        if (!result.isEmpty()) {
            result.add(0, "error1");
            return result;
        }
        for (Product temp : productsInfo) {
            totalPrice = totalPrice.add(temp.getDisAmt().multiply(new BigDecimal(temp.getItemAmount())));
            totalPV = totalPV.add(temp.getPvAmt().multiply(new BigDecimal(temp.getItemAmount())));
            // 将转换后的行信息存入集合中
            updateLines.add(this.productTurnToLine(temp, autoShipOrder));
        }
        // 总金额需要加入运费计算 freight
        if (DeliveryConstants.DELIVERY_TYPE_SHIPPMENT.equals(deliveryType)) {
            freight = logistics.getShippingFee();
        }
        // 根据市场id获取自动订货税后最小金额
        List<String> minSumCode = paramService.getParamValues(request, "SPM.AUTOSHIP_MIN_SUM", "SALES", salesOrgId);
        if (!minSumCode.isEmpty()) {
            minSum = new BigDecimal(minSumCode.get(0));
            if (minSum.compareTo(totalPrice.add(freight)) > 0) {
                // 订单总金额小于最小金额，验证不通过
                result.add("error2");
                result.add(minSum.toString());
                return result;
            }
        }
        // 计算销售积分
        List<String> codeRate = paramService.getParamValues(request, "SPM.SALES_POINT_RATE", "SALES", salesOrgId);
        List<String> codePointLimit = paramService.getParamValues(request, "SPM.SALES_POINT_LIMIT", "SALES",
                salesOrgId);
        String pointRate = "";
        String pointLimit = "";
        if (!codeRate.isEmpty()) {
            pointRate = codeRate.get(0);
            totalPoint = totalPrice.multiply(new BigDecimal(pointRate).divide(new BigDecimal(RATE_HUNDRED))).setScale(0,
                    BigDecimal.ROUND_HALF_UP);
            if (!codePointLimit.isEmpty()) {
                pointLimit = codePointLimit.get(0);
                if (new BigDecimal(pointLimit).compareTo(totalPoint) < 0) {
                    totalPoint = new BigDecimal(pointLimit);
                }
            }
        }
        // 删除当前自动订单头Id对应的行信息，便于更新最新信息
        autoshipLineMapper.deleteByHeaderId(autoShipOrder.getAutoshipId());
        // 插入最新的的autoship行数据
        for (AutoshipLine line : updateLines) {
            autoshipLineMapper.insertSelective(line);
        }
        // 更新订单头字段信息：销售积分，订单金额，税额，地址信息
        autoShipOrder.setSalesScore(totalPoint);
        autoShipOrder.setOrderAmt(totalPrice.add(freight));
        BigDecimal taxRate = productsInfo.get(0).getTax();
        if (null == taxRate || new BigDecimal(-1).compareTo(taxRate) == 0) {
            autoShipOrder.setTaxAmt(BigDecimal.ZERO);
        } else {
            BigDecimal originalPrice = BigDecimal.ZERO;
            originalPrice = totalPrice.divide(new BigDecimal(1).add(taxRate.divide(new BigDecimal(RATE_HUNDRED))), 2,
                    BigDecimal.ROUND_HALF_UP);
            autoShipOrder.setTaxAmt(totalPrice.subtract(originalPrice));
        }
        autoShipOrder.setDeliveryType(deliveryType);
        autoshipOrderMapper.updateByPrimaryKeySelective(autoShipOrder);
        // 插入物流地址信息，物流运费信息
        if (logistics != null) {
            SalesLogistics historyLogistics = salesLogisticsMapper.selectByHeaderId(autoShipOrder.getAutoshipId(),
                    OrderConstants.YES);
            if (historyLogistics != null) {
                logistics.setLogisticsId(historyLogistics.getLogisticsId());
                logistics.setHeaderId(autoShipOrder.getAutoshipId());
                logistics.setAutoshipFlag(OrderConstants.YES);
                salesLogisticsMapper.updateByPrimaryKeySelective(logistics);
            } else {
                logistics.setHeaderId(autoShipOrder.getAutoshipId());
                logistics.setAutoshipFlag(OrderConstants.YES);
                salesLogisticsMapper.insertSelective(logistics);
            }
        } else {
            salesLogisticsMapper.deleteByHeaderId(autoShipOrder.getAutoshipId(), OrderConstants.YES);
        }

        List<SalesSites> salesSitesTemp = salesSites;
        /*List<SalesSites> historySites = salesSitesService.getSitesByHeaderId(request, autoShipOrder.getAutoshipId(),
                "Y");*/
        if (!salesSites.isEmpty()) {
            salesSitesMapper.deleteByHeaderIdAndAutoshipFlag(OrderConstants.YES, autoShipOrder.getAutoshipId());
            for (SalesSites sites : salesSitesTemp) {
                sites.setHeaderId(autoShipOrder.getAutoshipId());
                sites.setAutoshipFlag(OrderConstants.YES);
                sites.setSalesSiteId(null);
                SpmLocation temp = new SpmLocation();
                temp.setCountryCode(sites.getCountryCode());
                temp.setStateCode(sites.getStateCode());
                temp.setCityCode(sites.getCityCode());
                temp.setAddressLine1(sites.getAddress1());
                temp.setAddressLine2(sites.getAddress2());
                temp.setAddressLine3(sites.getAddress3());
                sites.setAddress(queryAddressByCode(request, temp));
                salesSitesService.submit(request, sites);
            }
        }
        result.add("success");
        return result;
    }

    /**
     * 根据传入的商品id获取商品详细信息.
     * 
     * @param request
     *            统一上下文.
     * @param products
     *            商品id列表.
     * @param salesOrgId
     *            自动订单销售区域id.
     * @return 返回商品详细信息.
     */
    private List<Product> getProductInfo(IRequest request, List<Product> products, Long salesOrgId) {
        List<Product> productsInfo = new ArrayList<Product>();
        Product productTemp = null;
        for (Product temp : products) {
            productTemp = new Product();
            productTemp.setItemId(temp.getItemId());
            productTemp.setOrgId(salesOrgId);
            productTemp.setFunctionArea("AUTOS");
            List<Product> queryResult = productService.getSimpleProductsByWhereClause(request, productTemp, 1, -1);
            if (!queryResult.isEmpty()) {
                Product autoShipProduct = queryResult.get(0);
                autoShipProduct.setItemAmount(temp.getItemAmount());
                productsInfo.add(autoShipProduct);
            }
        }
        return productsInfo;
    }

    /**
     * 将查询到的商品转换为自动订单行信息.
     * 
     * @param product
     *            商品详细信息.
     * @param autoShipOrder
     *            自动订货头信息.
     * @return 返回自动订货行信息dto.
     */
    private AutoshipLine productTurnToLine(Product product, AutoshipOrder autoShipOrder) {
        AutoshipLine autoShipLine = new AutoshipLine();
        autoShipLine.setItemType(product.getItemType());
        autoShipLine.setAutoshipId(autoShipOrder.getAutoshipId());
        autoShipLine.setItemId(product.getItemId());
        autoShipLine.setUnitOrigPrice(product.getOrginalAmt());
        autoShipLine.setUnitDiscountPrice(BigDecimal.ZERO);
        autoShipLine.setUnitSellingPrice(product.getDisAmt());
        autoShipLine.setItemSalesType("PURC");
        autoShipLine.setPv(product.getPvAmt());
        autoShipLine.setQuantity(new BigDecimal(product.getItemAmount()));
        autoShipLine.setAmount(product.getDisAmt().multiply(new BigDecimal(product.getItemAmount())));
        autoShipLine.setSalesOrgId(autoShipOrder.getSalesOrgId());
        autoShipLine.setUomCode(product.getUomCode());
        return autoShipLine;
    }

    @Override
    public Map<String, Object> querySites(IRequest request, List<SalesSites> list) throws JsonProcessingException {
        Map<String, Object> allSites = new HashMap<String, Object>();
        Long memberId = (Long) request.getAttribute(MemberConstants.MWS_MEMBER_ID);
        // 1.通过memberId,查询出所有地址的集合,三种类型的地址一个sql查快
        List<MemSite> siteList = new ArrayList<MemSite>();
        MemSite allSite = new MemSite();
        allSite.setMemberId(memberId);
        siteList = memberSiteService.queryMemSites(request, allSite);
        // 2.分离出收货地址列表和账单地址列表
        List<MemSite> shipSites = new ArrayList<MemSite>();
        List<MemSite> billSites = new ArrayList<MemSite>();
        List<MemSite> sites = new ArrayList<MemSite>();
        for (MemSite ms : siteList) {
            if (MemberConstants.MEM_SITE_TYPE_SHIP.equals(ms.getSiteUseCode())) { // 默认收货地址
                shipSites.add(ms);
                sites.add(ms);
            } else if (MemberConstants.MEM_SITE_TYPE_BILL.equals(ms.getSiteUseCode())) { // 默认账单地址
                billSites.add(ms);
                sites.add(ms);
            }
        }
        // 处理salesSite成memSite,以便显示在界面上
        for (SalesSites ss : list) {
            MemSite ms = new MemSite();
            ms.setName(ss.getName());
            ms.setPhone(ss.getPhone());
            ms.setAreaCode(ss.getAreaCode());
            ms.setSiteUseCode(ss.getSiteType());
            ms.setDefaultFlag(MemberConstants.NO);
            SpmLocation temp = new SpmLocation();
            temp.setCountryCode(ss.getCountryCode());
            temp.setStateCode(ss.getStateCode());
            temp.setCityCode(ss.getCityCode());
            temp.setAddressLine1(ss.getAddress1());
            temp.setAddressLine2(ss.getAddress2());
            temp.setAddressLine3(ss.getAddress3());
            ms.setAddress(queryAddressByCode(request, temp));
            SpmLocation sl = new SpmLocation();
            sl.setCountryCode(ss.getCountryCode());
            sl.setStateCode(ss.getStateCode());
            sl.setCityCode(ss.getCityCode());
            sl.setAddressLine1(ss.getAddress());
            sl.setZipCode(ss.getZipCode());
            ms.setSpmLocation(sl);
            if (MemberConstants.MEM_SITE_TYPE_SHIP.equals(ss.getSiteType())) {
                ms.setSiteId(0L);
                shipSites.add(0, ms);
                sites.add(ms);
            } else if (MemberConstants.MEM_SITE_TYPE_BILL.equals(ss.getSiteType())) {
                ms.setSiteId(-1L);
                billSites.add(0, ms);
                sites.add(ms);
            }
        }
        allSites.put("shipSites", shipSites);
        allSites.put("billSites", billSites);
        allSites.put("sites", objectMapper.writeValueAsString(sites));
        return allSites;
    }

    private String queryAddressByCode(IRequest request, SpmLocation location) {
        StringBuffer address = new StringBuffer("");
        SpmCountry sc = new SpmCountry();
        sc.setCountryCode(location.getCountryCode());
        List<SpmCountry> scList = spmCountryService.queryCountry(request, sc, 1, NUMBER_TEN);
        address.append(scList.get(0).getName());
        SpmState ss = new SpmState();
        ss.setStateCode(location.getStateCode());
        List<SpmState> ssList = spmStateService.queryState(request, ss, 1, NUMBER_TEN);
        address.append(ssList.get(0).getName());
        SpmCity sci = new SpmCity();
        sci.setCityCode(location.getCityCode());
        List<SpmCity> sciList = spmCityService.queryCity(request, sci, 1, NUMBER_TEN);
        address.append(sciList.get(0).getName());
        if (null != location.getAddressLine1()) {
            address.append(location.getAddressLine1());
        }
        if (null != location.getAddressLine2()) {
            address.append(location.getAddressLine2());
        }
        if (null != location.getAddressLine3()) {
            address.append(location.getAddressLine3());
        }
        return address.toString();
    }

    @Override
    public List<Product> getProduct(IRequest request) {
        List<Product> autoShipProducts = new ArrayList<Product>();
        Long memberId = null;
        if (request != null) {
            memberId = Long.parseLong(request.getAttribute(Member.FIELD_MEMBER_ID).toString());
        }
        AutoshipOrder autoShipOrder = autoshipOrderMapper.selectAutoShipOrderByMember(memberId, null);
        List<AutoshipLine> lines = autoshipLineMapper.selectSimpleLine(autoShipOrder.getAutoshipId());
        Product product;
        for (AutoshipLine temp : lines) {
            product = new Product();
            product.setItemId(temp.getItemId());
            product.setOrgId(autoShipOrder.getSalesOrgId());
            product.setFunctionArea("AUTOS");
            List<Product> products = productService.getSimpleProductsByWhereClause(request, product, 1, -1);
            if (products.size() > 0) {
                Product autoShipProduct = products.get(0);
                if (null != temp.getQuantity()) {
                    autoShipProduct.setItemAmount(Long.parseLong(temp.getQuantity().toString()));
                }
                autoShipProducts.add(autoShipProduct);
            }
        }
        return autoShipProducts;
    }

    @Override
    public List<MemSite> getAllSites(IRequest request) {
        Long memberId = null;
        if (request != null) {
            memberId = Long.parseLong(request.getAttribute(Member.FIELD_MEMBER_ID).toString());
        }
        AutoshipOrder autoShipOrder = autoshipOrderMapper.selectAutoShipOrderByMember(memberId, null);
        // 获取自动订货单历史地址信息
        List<SalesSites> sites = salesSitesService.getSitesByHeaderId(request, autoShipOrder.getAutoshipId(), "Y");
        List<MemSite> siteList = new ArrayList<MemSite>();
        MemSite allSite = new MemSite();
        allSite.setMemberId(memberId);
        siteList = memberSiteService.queryMemSites(request, allSite);
        // 处理salesSite成memSite,以便显示在界面上
        for (SalesSites ss : sites) {
            MemSite ms = new MemSite();
            ms.setName(ss.getName());
            ms.setPhone(ss.getPhone());
            ms.setAreaCode(ss.getAreaCode());
            ms.setSiteUseCode(ss.getSiteType());
            ms.setDefaultFlag(MemberConstants.NO);
            SpmLocation temp = new SpmLocation();
            temp.setCountryCode(ss.getCountryCode());
            temp.setStateCode(ss.getStateCode());
            temp.setCityCode(ss.getCityCode());
            temp.setAddressLine1(ss.getAddress1());
            temp.setAddressLine2(ss.getAddress2());
            temp.setAddressLine3(ss.getAddress3());
            ms.setAddress(queryAddressByCode(request, temp));
            SpmLocation sl = new SpmLocation();
            sl.setCountryCode(ss.getCountryCode());
            sl.setStateCode(ss.getStateCode());
            sl.setCityCode(ss.getCityCode());
            sl.setAddressLine1(ss.getAddress());
            sl.setZipCode(ss.getZipCode());
            ms.setSpmLocation(sl);
            if (MemberConstants.MEM_SITE_TYPE_SHIP.equals(ss.getSiteType())) {
                ms.setSiteId(0L);
                siteList.add(ms);
            } else if (MemberConstants.MEM_SITE_TYPE_BILL.equals(ss.getSiteType())) {
                ms.setSiteId(-1L);
                siteList.add(ms);
            }
        }
        return siteList;
    }
}

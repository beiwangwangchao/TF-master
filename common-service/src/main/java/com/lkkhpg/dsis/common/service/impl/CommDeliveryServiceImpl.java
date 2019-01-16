/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickLine;
import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;
import com.lkkhpg.dsis.common.delivery.dto.OrderDeliveryLine;
import com.lkkhpg.dsis.common.delivery.dto.OrderDeliveryLot;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryLineMapper;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryLotMapper;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryMapper;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.product.dto.InvItemPropertyV;
import com.lkkhpg.dsis.common.product.mapper.InvItemPropertyMapper;
import com.lkkhpg.dsis.common.service.ICommDeliveryPickService;
import com.lkkhpg.dsis.common.service.ICommDeliveryService;
import com.lkkhpg.dsis.common.service.ICommItemService;
import com.lkkhpg.dsis.common.service.IDeliveryInvOrgMatchService;
import com.lkkhpg.dsis.common.service.IInvCheckService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 发运单Service实现类.
 * 
 * @author peng.li
 * @author chenjingxiong
 */
@Service
public class CommDeliveryServiceImpl implements ICommDeliveryService {

    private static final String DOC_TYPE_DELIVERY = "DeliveryNumber";

    private static final String DELIVERY_NUM_PREFIX = "DL";

    private static final int DELIVERY_NUM_LENGTH = 6;

    private static final Long DELIVERY_NUMBER_SEQUENCE_INIT = 1L;

    @Autowired
    private OrderDeliveryMapper dmOrderDeliveryMapper;

    @Autowired
    private OrderDeliveryLineMapper dmOrderDeliveryLineMapper;

    @Autowired
    private OrderDeliveryLotMapper dmOrderDeliveryLotMapper;

    @Autowired
    private ICommItemService commItemService;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private IInvCheckService invCheckService;

    @Autowired
    private ICommDeliveryPickService commDeliveryPickService;

    @Autowired
    private IDeliveryInvOrgMatchService deliveryInvOrgMatchService;

    @Autowired
    private IDocSequenceService docSequanceService;

    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Autowired
    private InvItemPropertyMapper invItemPropertyMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Long> createDelivery(IRequest request, DeliveryPickHead pickRelease) throws CommDeliveryException {

        List<Long> deliveryIdList = new ArrayList<>();
        List<DeliveryPickLine> picklist = pickRelease.getLines() == null ? new ArrayList<DeliveryPickLine>()
                : pickRelease.getLines();
        Map<String, List<DeliveryPickLine>> pickLineByOrgIdMap = new HashMap<String, List<DeliveryPickLine>>();
        Long orderId = pickRelease.getOrderId();
        Long pickReleaseId = pickRelease.getPickReleaseId();
        List<DeliveryPickLine> pickLineList;
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKey(orderId);
        if (salesOrder == null) {
            return null;
        }
        String deliveryType = salesOrder.getDeliveryType();
        for (DeliveryPickLine deliveryPickLine : picklist) {
            if (deliveryPickLine == null || deliveryPickLine.getInvOrgId() == null) {
                continue;
            }
            String orgId = deliveryPickLine.getInvOrgId().toString();
            if (pickLineByOrgIdMap.containsKey(orgId)) {
                pickLineList = pickLineByOrgIdMap.get(orgId);
                pickLineList.add(deliveryPickLine);
            } else {
                pickLineList = new ArrayList<>();
                pickLineList.add(deliveryPickLine);
            }
            pickLineByOrgIdMap.put(orgId, pickLineList);
        }

        // 生成发运单
        for (String invOrgId : pickLineByOrgIdMap.keySet()) {
            // 发运单头
            OrderDelivery deliveryDetailsHead = new OrderDelivery();
            deliveryDetailsHead.setOrderHeaderId(orderId);
            deliveryDetailsHead.setInvOrgId(Long.parseLong(invOrgId));
            deliveryDetailsHead.setDeliveryType(deliveryType);

            // 获取流水号
            DocSequence docSequence = new DocSequence();
            docSequence.setDocType(DOC_TYPE_DELIVERY);
            String deliveryNum = docSequanceService.getSequence(request, docSequence, DELIVERY_NUM_PREFIX,
                    DELIVERY_NUM_LENGTH, DELIVERY_NUMBER_SEQUENCE_INIT);

            deliveryDetailsHead.setDeliveryNumber(deliveryNum);
            deliveryDetailsHead.setDeliveryStatus(DeliveryConstants.DELIVERY_STATUS_NEW);
            deliveryDetailsHead.setPickReleaseId(pickReleaseId);
            deliveryDetailsHead.setDeliveryDate(new Date());

            deliveryDetailsHead.setCreatedBy(request.getAccountId());
            deliveryDetailsHead.setLastUpdatedBy(request.getAccountId());
            
            
            /*deliveryDetailsHead.setRemark(salesOrder.getRemark());
            deliveryDetailsHead.setInternalRemark(salesOrder.getInternalRemark());*/
            // 物流信息
            List<SalesSites> salesSites = salesSitesMapper
                    .selectSitesByHeaderIdAndSiteType(OrderConstants.ORDER_SITES_SHIP, orderId);
            if (salesSites != null && salesSites.size() > 0) {
                SalesSites address = salesSites.get(0);
                deliveryDetailsHead.setContactName(address.getName());
                deliveryDetailsHead.setPhone(address.getPhone());
                deliveryDetailsHead.setCityCode(address.getCityCode());
                deliveryDetailsHead.setStateCode(address.getStateCode());
                deliveryDetailsHead.setCountryCode(address.getCountryCode());
                deliveryDetailsHead.setAddress(address.getAddress());
                deliveryDetailsHead.setAreaCode(address.getAreaCode());
                deliveryDetailsHead.setZipCode(address.getZipCode());
                deliveryDetailsHead.setAddress1(address.getAddress1());
                deliveryDetailsHead.setAddress2(address.getAddress2());
                deliveryDetailsHead.setAddress3(address.getAddress3());
            }
            // 保存发运单头获取头Id
            dmOrderDeliveryMapper.insert(deliveryDetailsHead);

            Long deliveryId = deliveryDetailsHead.getDeliveryId();
            deliveryIdList.add(deliveryId);

            // 发运行
            List<DeliveryPickLine> list = pickLineByOrgIdMap.get(invOrgId);
            for (DeliveryPickLine deliveryPickLine : list) {
                // 商品包不放到发运行里来
                if (DeliveryConstants.ITEM_TYPE_VY.equals(deliveryPickLine.getItemType())
                        || DeliveryConstants.ITEM_TYPE_VN.equals(deliveryPickLine.getItemType())) {
                    continue;
                }

                OrderDeliveryLine deliveryDetailsLine = new OrderDeliveryLine();

                InvItemPropertyV protertyV = commItemService.getItemProperties(request, deliveryPickLine.getItemId(),
                        Long.parseLong(invOrgId));

                deliveryDetailsLine.setItemId(deliveryPickLine.getItemId());
                deliveryDetailsLine.setCountItemId(Long.parseLong(protertyV.getCountItemId()));

                if (deliveryPickLine.getPackageItemId() != null) {
                    deliveryDetailsLine.setPackageItemId(deliveryPickLine.getPackageItemId());
                } else {
                    if (deliveryPickLine.getParentLineId() != null) {
                        for (DeliveryPickLine parentLine : list) {
                            if (deliveryPickLine.getParentLineId().equals(parentLine.getLineId())) {
                                deliveryDetailsLine.setPackageItemId(parentLine.getItemId());
                            }
                        }
                    }
                }

                deliveryDetailsLine.setDeliveryId(deliveryId);
                deliveryDetailsLine.setInvOrgId(Long.parseLong(invOrgId));
                deliveryDetailsLine.setOrderLineId(deliveryPickLine.getOrderLineId());

                BigDecimal outstandingQty = deliveryPickLine.getPickQuantity();
                deliveryDetailsLine.setOutstandingQty(outstandingQty);

                deliveryDetailsLine.setPickReleaseId(pickReleaseId);
                deliveryDetailsLine.setPickReleaseLineId(deliveryPickLine.getPickReleaseLineId());

                deliveryDetailsLine.setCreatedBy(request.getAccountId());
                deliveryDetailsLine.setLastUpdatedBy(request.getAccountId());

                // 保存发运行，获取行ID
                dmOrderDeliveryLineMapper.insert(deliveryDetailsLine);
                Long deliveryLineId = deliveryDetailsLine.getLineId();

                // 根据销售定单行,查询商品信息
                SalesLine salesLine = salesLineMapper.selectByPrimaryKey(deliveryDetailsLine.getOrderLineId());
                if (salesLine == null) {
                    break;
                }
                Long itemId = salesLine.getItemId();
                Long countItemId = deliveryDetailsLine.getCountItemId();
                // 是否启用自动分配批次
                if (enabledLot(request, Long.parseLong(invOrgId), countItemId)) {
                    // 获取建议批次
                    List<Map<String, Object>> lotList = dmOrderDeliveryLotMapper.getItemAvailableLots(countItemId,
                            Long.parseLong(invOrgId));
                    if (lotList != null && lotList.size() > 0) {
                        BigDecimal restOutstandingQty = outstandingQty;
                        for (Map<String, Object> map : lotList) {
                            if (map.get("lotNumber") == null) {
                                continue;
                            }
                            OrderDeliveryLot lot = new OrderDeliveryLot();
                            lot.setDeliveryId(deliveryId);
                            lot.setDeliveryLineId(deliveryLineId);
                            lot.setLotNumber(map.get("lotNumber").toString());

                            lot.setCreatedBy(request.getAccountId());
                            lot.setLastUpdatedBy(request.getAccountId());

                            BigDecimal quantity = new BigDecimal(map.get("quantity").toString());
                            if (BigDecimal.ZERO.compareTo(quantity) >= 0) {
                                continue;
                            }
                            /*
                             * 根据商品代码，对获取到的库存组织下其所有的批次号按照事务处理标识(
                             * INIT_TRANSACTION_ID)升序，
                             * 得到每个批次的库存量a、b、c、d...：将每个批次的库存量相加，直到满足“挑库数量”为止。
                             */
                            if (restOutstandingQty.compareTo(quantity) >= 0) {
                                lot.setOutstandingQty(quantity);
                                restOutstandingQty = restOutstandingQty.subtract(quantity);
                            } else {
                                lot.setOutstandingQty(restOutstandingQty);
                                restOutstandingQty = BigDecimal.ZERO;
                            }
                            dmOrderDeliveryLotMapper.insert(lot);
                            if (BigDecimal.ZERO.equals(restOutstandingQty)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return deliveryIdList;
    }

    /**
     * 是否启用自动分配批次.
     * 
     * @param request
     *            请求上下文
     * @param orgId
     *            组织编号.
     * @param itemId
     *            商品编号.
     * @return true 为启用 ;false 为不启用
     */
    private boolean enabledLot(IRequest request, Long orgId, Long itemId) {

        InvItemPropertyV protertyV = commItemService.getItemProperties(request, itemId, orgId);

        if (protertyV != null && BaseConstants.YES.equals(protertyV.getLotControlFlag())) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Long> createDeliveriesByOrder(IRequest request, SalesOrder salesOrder) throws CommDeliveryException {
        List<SalesLine> salesLines = salesOrder.getLines();

        if (salesLines == null || !OrderConstants.SALES_STATUS_COMPELETED.equals(salesOrder.getOrderStatus())) {
            return null;
        }
        // 是否满足库存
        boolean stockEnoughFlag = true;
        SalesSites deliverySite = null;
        if (salesOrder.getSalesSites() == null) {
            List<SalesSites> salesSites = salesSitesMapper.selectSitesByHeaderIdAndAutoshipFlag(OrderConstants.NO,
                    salesOrder.getHeaderId());
            salesOrder.setSalesSites(salesSites);
            for (SalesSites sites : salesSites) {
                if (OrderConstants.ORDER_SITES_SHIP.equals(sites.getSiteType())) {
                    deliverySite = sites;
                }
            }
        } else {
            for (SalesSites sites : salesOrder.getSalesSites()) {
                if (OrderConstants.ORDER_SITES_SHIP.equals(sites.getSiteType())) {
                    deliverySite = sites;
                }
            }
        }

        // 查看所有商品是否分配到库存组织中
        for (SalesLine salesLine : salesLines) {
            if (OrderConstants.ORDER_DELIVERY_SHIPP.equals(salesOrder.getDeliveryType())) {
                // 匹配库存
                Long matchInvOrgId = deliveryInvOrgMatchService.matchInvOrg(request, salesOrder.getSalesOrgId(),
                        deliverySite, salesLine.getItemId(), salesLine.getQuantity());
                
                if (matchInvOrgId == null) {
                    // 无法匹配到库存组织，无法生产发运单
                    return null;
                } else {
                    salesLine.setDefaultInvOrgId(matchInvOrgId);
                    // 更新订单行
                    salesLineMapper.updateByPrimaryKeySelective(salesLine);
                }
            }
            String enabledFlag = invItemPropertyMapper.queryInvEnabledFlag(salesLine.getItemId(),
                    salesLine.getDefaultInvOrgId());
            if (StringUtils.isEmpty(enabledFlag) || BaseConstants.NO.equals(enabledFlag)) {
                return null;
            }
        }

        // 构建挑库单
        DeliveryPickHead pickHead = new DeliveryPickHead();
        pickHead.setOrderId(salesOrder.getHeaderId());
        pickHead.setRemark("Auto Generated");
        // 挑库单行
        List<DeliveryPickLine> pickLines = new ArrayList<>();
        // 汇总Map
        // key值 = itemId + invOrgId
        // value = 数量和

        Map<String, BigDecimal> summaryMap = new HashMap<>();

        final String splitor = "&";
        for (SalesLine salesLine : salesLines) {
            Long defaultInvOrgId = salesLine.getDefaultInvOrgId();

            DeliveryPickLine pickLine = new DeliveryPickLine();
            pickLine.setPickQuantity(salesLine.getQuantity());
            pickLine.setOrderLineId(salesLine.getLineId());
            pickLine.setSalesOrgId(salesOrder.getSalesOrgId());
            pickLine.setSurplusQty(salesLine.getQuantity());
            pickLine.setInventory(salesLine.getQuantity());
            pickLine.setItemType(salesLine.getItemType());
            pickLine.setParentLineId(salesLine.getParentLineId());
            pickLine.setLineId(salesLine.getLineId());
            pickLine.setItemId(salesLine.getItemId());
            if (!DeliveryConstants.DELIVERY_TYPE_PICK_UP.equals(salesOrder.getDeliveryType())
                    || salesLine.getDefaultInvOrgId() == null) {
                // 物流配送
                pickLine.setInvOrgId(salesLine.getDefaultInvOrgId());
            } else {
                // 自提允许界面选择库存组织
                // TODO:验证库存组织是否合法
                pickLine.setInvOrgId(salesLine.getDefaultInvOrgId());
            }

            pickLines.add(pickLine);
            // 汇总订单类型为ITEM 和 PACK 行,用于库存校验
            if (DeliveryConstants.ITEM_TYPE_ITEM.equals(salesLine.getItemType())
                    || DeliveryConstants.ITEM_TYPE_PKG.equals(salesLine.getItemType())) {
                StringBuilder sb = new StringBuilder();

                InvItemPropertyV pv = commItemService.getItemProperties(request, salesLine.getItemId(),
                        salesLine.getDefaultInvOrgId());

                Long countItemId = Long.parseLong(pv.getCountItemId());

                String key = sb.append(countItemId).append(splitor).append(pickLine.getInvOrgId()).toString();
                BigDecimal quantity = summaryMap.get(key);
                if (quantity == null) {
                    quantity = BigDecimal.ZERO;
                }
                quantity = quantity.add(salesLine.getQuantity());
                summaryMap.put(key, quantity);
            }
        }
        // 库存检查
        for (String key : summaryMap.keySet()) {
            String[] keyInfos = key.split(splitor);
            Long itemId = Long.parseLong(keyInfos[0]);
            Long invOrgId = Long.parseLong(keyInfos[1]);
            BigDecimal quantity = summaryMap.get(key);
            // 检查库存
            boolean checkResult = invCheckService.check(request, itemId, invOrgId, null, null, null, quantity);
            // 有库存不足的商品
            if (!checkResult) {
                stockEnoughFlag = false;
                break;
            }
        }

        // 库存量不足，需动工处理，不产生发运单
        if (!stockEnoughFlag) {
            return null;
        }

        pickHead.setLines(pickLines);

        // 插入挑库单
        List<Long> deliveryIds = commDeliveryPickService.saveDeliveryPick(request, pickHead);
        if (deliveryIds != null && !deliveryIds.isEmpty()) {
            List<DeliveryPickLine> list = pickHead.getLines();
            for (DeliveryPickLine dp : list) {
                // 修改订单行未挑库数量
                SalesLine salesLine = salesLineMapper.selectByPrimaryKeyForLock(dp.getOrderLineId());
                if (dp.getPickQuantity().compareTo(salesLine.getUnpickQuantity()) > 0) {
                    // 挑库数量>未挑库数量
                    throw new CommDeliveryException(CommDeliveryException.MORE_THAN_UNPICKED, null);
                }
                // 减去已经挑库的数量
                salesLine.setUnpickQuantity(salesLine.getUnpickQuantity().subtract(dp.getPickQuantity()));
                salesLineMapper.updateByPrimaryKey(salesLine);
            }
        }
        return deliveryIds;
    }
}

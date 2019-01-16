/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.delivery.exception.DeliveryException;
import com.lkkhpg.dsis.admin.delivery.service.IDeliveryPickService;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead;
import com.lkkhpg.dsis.common.delivery.dto.DeliveryPickLine;
import com.lkkhpg.dsis.common.delivery.mapper.DeliveryPickLineMapper;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.product.dto.InvItemPropertyV;
import com.lkkhpg.dsis.common.service.ICommDeliveryPickService;
import com.lkkhpg.dsis.common.service.ICommItemService;
import com.lkkhpg.dsis.common.service.IDeliveryInvOrgMatchService;
import com.lkkhpg.dsis.common.service.IInvCheckService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 挑库发放服务实现.
 * 
 * @author Zhaoqi
 *
 */
@Service
@Transactional
public class DeliveryPickServiceImpl implements IDeliveryPickService {

    @Autowired
    private ICommDeliveryPickService commDeliveryPickService;

    @Autowired
    private ICommItemService commItemService;

    @Autowired
    private DeliveryPickLineMapper deliveryPickLineMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private IDeliveryInvOrgMatchService deliveryInvOrgMatchService;

    @Autowired
    private IInvCheckService invCheckService;
    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Long> saveDeliveryPick(IRequest request, @StdWho DeliveryPickHead deliveryPickHead)
            throws CommDeliveryException {
        List<Long> deliveryId = commDeliveryPickService.saveDeliveryPick(request, deliveryPickHead);
        List<DeliveryPickLine> list = deliveryPickHead.getLines();
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
        return deliveryId;
    }

    @Override
    public List<DeliveryPickLine> selectOrder(IRequest request, DeliveryPickLine deliveryPick, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return deliveryPickLineMapper.selectOrder(deliveryPick);
    }

    @Override
    public List<DeliveryPickLine> selectOrderLine(IRequest request,  DeliveryPickLine deliveryPick, int page,
            int pagesize) {
        SalesOrder salesOrder = salesOrderMapper.queryByOrderNumber(deliveryPick.getOrderNumber());
        List<SalesSites> salesSites = salesSitesMapper.selectSitesByHeaderIdAndAutoshipFlag(OrderConstants.NO,
                salesOrder.getHeaderId());
        salesOrder.setSalesSites(salesSites);
        SalesSites deliverySite = null;
        for (SalesSites sites : salesSites) {
            if (OrderConstants.ORDER_SITES_SHIP.equals(sites.getSiteType())) {
                deliverySite = sites;
            }
        }

        Long invOrgId = deliveryInvOrgMatchService.matchInvOrg(request, salesOrder.getSalesOrgId(),
                salesOrder.getDeliveryType(), deliverySite);

       //PageHelper.startPage(page, pagesize);
        List<DeliveryPickLine> list = deliveryPickLineMapper.selectOrderLine(deliveryPick.getOrderNumber(),
                invOrgId);

        for (DeliveryPickLine line : list) {
            if (DeliveryConstants.ITEM_TYPE_VN.equals(line.getItemType())
                    || DeliveryConstants.ITEM_TYPE_VI.equals(line.getItemType())) {

                line.setInventory(new BigDecimal(DeliveryConstants.VI_MAX_INV));
                line.setPickQuantity(line.getSurplusQty());
            } else if (DeliveryConstants.ITEM_TYPE_VY.equals(line.getItemType())) {
                // 计算虚拟商品包的库存
                BigDecimal vyInv = new BigDecimal(DeliveryConstants.VI_MAX_INV);
                for (DeliveryPickLine vyl : list) {
                    if (line.getLineId().equals(vyl.getParentLineId())
                            && DeliveryConstants.ITEM_TYPE_ITEM.equals(vyl.getItemType())) {
                        BigDecimal temp1 = vyl.getQuantity().divide(line.getQuantity());
                        BigDecimal temp2 = vyl.getInventory().divide(temp1, BigDecimal.ROUND_DOWN);

                        if (vyInv.compareTo(temp2) > 0) {
                            vyInv = temp2;
                        }
                    }
                }
                line.setInventory(vyInv);
                BigDecimal vypick;
                if (vyInv.compareTo(line.getSurplusQty()) > 0) {
                    vypick = line.getSurplusQty();
                } else {
                    vypick = vyInv;
                }
                line.setPickQuantity(vypick);
                // 更新子行的挑库数量
                for (DeliveryPickLine vyl : list) {
                    if (line.getLineId().equals(vyl.getParentLineId())) {
                        BigDecimal temp1 = vyl.getQuantity().divide(line.getQuantity());
                        BigDecimal vylpick = vypick.multiply(temp1);
                        vyl.setPickQuantity(vylpick);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryInvOrgs(IRequest request, String orderNumber) {
        List<Map<String, Object>> list = deliveryPickLineMapper.queryInvOrgs(orderNumber);
        return list;
    }

    @Override
    public List<DeliveryPickLine> queryInventory(IRequest request, List<DeliveryPickLine> deliveryPickLines) {
        for (DeliveryPickLine deliveryPickLine : deliveryPickLines) {
            DeliveryPickLine line = deliveryPickLineMapper.queryInventory(deliveryPickLine.getItemId(),
                    deliveryPickLine.getInvOrgId());
            if (line == null) {
                deliveryPickLine.setInventory(BigDecimal.ZERO);
            } else {
                deliveryPickLine.setInventory(line.getInventory());
            }
        }
        return deliveryPickLines;
    }

    @Override
    public List<DeliveryPickLine> selectInvOrg(IRequest request, DeliveryPickLine deliveryPickLine) {
        List<DeliveryPickLine> list = new ArrayList<>();
        if (DeliveryConstants.DELIVERY_TYPE_SHIPPMENT.equals(deliveryPickLine.getDeliveryType())) {
            list = deliveryPickLineMapper.selectInvOrgBySite(deliveryPickLine);
        } else {
            list = deliveryPickLineMapper.selectInvOrgByOrg(deliveryPickLine);
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.lkkhpg.dsis.admin.delivery.delivery.service.IDeliveryPickService#
     * verifyInventory(com.lkkhpg.dsis.platform.core.IRequest,
     * com.lkkhpg.dsis.common.delivery.dto.DeliveryPickHead)
     */
    @Override
    public void verifyInventory(IRequest request, DeliveryPickHead deliveryPickHead)
            throws DeliveryException, CommDeliveryException {
        // TODO Auto-generated method stub
        Map<String, BigDecimal> summaryMap = new HashMap<>();
        final String splitor = "&";
        for (DeliveryPickLine line : deliveryPickHead.getLines()) {
            if (DeliveryConstants.ITEM_TYPE_ITEM.equals(line.getItemType())
                    || DeliveryConstants.ITEM_TYPE_PKG.equals(line.getItemType())) {
                StringBuilder sb = new StringBuilder();
                InvItemPropertyV pv = commItemService.getItemProperties(request, line.getItemId(), line.getInvOrgId());
                Long countItemId = Long.parseLong(pv.getCountItemId());

                String key = sb.append(countItemId).append(splitor).append(line.getInvOrgId()).toString();
                BigDecimal quantity = summaryMap.get(key);
                if (quantity == null) {
                    quantity = BigDecimal.ZERO;
                }
                quantity = quantity.add(line.getPickQuantity());
                summaryMap.put(key, quantity);
            }
        }
        // 库存检查
        for (String key : summaryMap.keySet()) {
            String[] keyInfos = key.split(splitor);
            Long itemId = Long.parseLong(keyInfos[0]);
            /*modified by furong.tang 修改查询逻辑*/
            Long salesOrgId = request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
            //Long invOrgId = Long.parseLong(keyInfos[1]);
            BigDecimal quantity = summaryMap.get(key);
            // 检查库存
            boolean checkResult = invCheckService.check(request, itemId, salesOrgId, null, null, null, quantity);
            // 有库存不足的商品
            if (!checkResult) {
                throw new CommDeliveryException(CommDeliveryException.STOCK_NOT_ENOUGH, new Object[] {});
            }
        }

    }
}

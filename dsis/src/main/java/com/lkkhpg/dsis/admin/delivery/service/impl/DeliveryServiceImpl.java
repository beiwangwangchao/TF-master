/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.delivery.exception.DeliveryException;
import com.lkkhpg.dsis.admin.delivery.service.IDeliveryService;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.service.IInvTransactionService;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.delivery.dto.*;
import com.lkkhpg.dsis.common.delivery.mapper.DeliveryMapper;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryLineMapper;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryLotMapper;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryMapper;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.product.dto.InvItemHierarchy;
import com.lkkhpg.dsis.common.product.mapper.InvItemHierarchyMapper;
import com.lkkhpg.dsis.common.service.ICommDeliveryPickService;
import com.lkkhpg.dsis.common.service.ICommDeliveryService;
import com.lkkhpg.dsis.common.service.ICommItemService;
import com.lkkhpg.dsis.common.service.IInvCheckService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 发运单Service实现类.
 * 
 * @author peng.li
 */
@Service
public class DeliveryServiceImpl implements IDeliveryService {

    private Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    private static final String TRX_SOURCE_TYPE_LOT = "DM_ORDER_DELIVERY_LOT";

    private static final String TRX_SOURCE_TYPE_LINE = "DM_ORDER_DELIVERY_LINE";

    @Autowired
    private ICommDeliveryService commDeliveryService;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private OrderDeliveryMapper dmOrderDeliveryMapper;

    @Autowired
    private OrderDeliveryLineMapper dmOrderDeliveryLineMapper;

    @Autowired
    private OrderDeliveryLotMapper dmOrderDeliveryLotMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private IInvCheckService invCheckService;

    @Autowired
    private ICommDeliveryPickService commDeliveryPickService;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private ICommItemService commItemService;

    @Autowired
    private IInvTransactionService invTransactionService;

    @Autowired
    private InvItemHierarchyMapper invItemHierarchyMapper;

    @Override
    public List<OrderDelivery> queryDelivery(IRequest request, DeliveryForQuery delivery, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<String> statusList = new ArrayList<String>();
        if (delivery.getDeliveryStatus() != null) {
            for (String s : delivery.getDeliveryStatus().split(";")) {
                statusList.add(s);
            }
        }
        delivery.setDeliveryStatusList(statusList);
        return deliveryMapper.queryDelivery(delivery);
    }

    @Override
    public List<OrderDelivery> queryDeliveryStatus() {
        return null;
    }

    @Override
    public List<Map<String, Object>> orderListQuery(IRequest request) {
        return deliveryMapper.orderComboxQuery();
    }

    @Override
    public List<Map<String, Object>> invComboxQuery(IRequest request) {
        return deliveryMapper.invComboxQuery();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDelivery save(IRequest request, OrderDelivery deliveryHead) throws CommDeliveryException {
        if (DeliveryConstants.DELIVERY_STATUS_NEW.equals(deliveryHead.getDeliveryStatus())
                || DeliveryConstants.DELIVERY_STATUS_PENDDING.equals(deliveryHead.getDeliveryStatus())) {

            validate(request, deliveryHead);
            dmOrderDeliveryMapper.updateByPrimaryKeySelective(deliveryHead);
            List<OrderDeliveryLine> deliveryLines = deliveryHead.getDeliveryLines();
            for (OrderDeliveryLine deliveryLine : deliveryLines) {
                dmOrderDeliveryLineMapper.updateByPrimaryKey(deliveryLine);
                List<OrderDeliveryLot> lots = deliveryLine.getLots();
                for (OrderDeliveryLot lot : lots) {
                    if (DTOStatus.ADD.equals(lot.get__status())) {
                        lot.setDeliveryLineId(deliveryLine.getLineId());
                        lot.setDeliveryId(deliveryLine.getDeliveryId());
                        dmOrderDeliveryLotMapper.insert(lot);
                    } else if (DTOStatus.DELETE.equals(lot.get__status()) && lot.getDeliveryLotId() != null) {
                        dmOrderDeliveryLotMapper.deleteByPrimaryKey(lot.getDeliveryLotId());
                    } else {
                        dmOrderDeliveryLotMapper.updateByPrimaryKey(lot);
                    }
                }
            }
            /*
             * // 更新订单 SalesOrder order =
             * salesOrderMapper.selectByPrimaryKeyOnly(deliveryHead.
             * getOrderHeaderId());
             * order.setInternalRemark(deliveryHead.getInternalRemark());
             * order.setRemark(deliveryHead.getRemark());
             * salesOrderMapper.updateByPrimaryKey(order);
             */

        } else {
            throw new DeliveryException(DeliveryException.STATUS_NOT_MATCH, new Object[] {});
        }
        return deliveryHead;
    }

    @Override
    public OrderDelivery saveDeliveryHead(IRequest request, @StdWho OrderDelivery deliveryHead) {
        dmOrderDeliveryMapper.updateByPrimaryKeySelective(deliveryHead);
        return deliveryHead;
    }

    /**
     * 校验发运单.
     * 
     * @param request
     *            请求上下文
     * @param deliveryHead
     *            发运
     * @throws CommDeliveryException
     *             发运统一异常
     */
    private void validate(IRequest request, OrderDelivery deliveryHead) throws CommDeliveryException {
        OrderDelivery d = dmOrderDeliveryMapper.queryByPrimaryKey(deliveryHead.getDeliveryId());
        if (!DeliveryConstants.DELIVERY_STATUS_PENDDING.equals(d.getDeliveryStatus())
                && !DeliveryConstants.DELIVERY_STATUS_NEW.equals(d.getDeliveryStatus())) {
            throw new CommDeliveryException(CommDeliveryException.CURRENT_STATUS_CANNOT_BE_SAVE, new Object[] {});
        }
        List<OrderDeliveryLine> deliveryLines = deliveryHead.getDeliveryLines();
        // 如商品包中的商品退回，需检查退回的商品数量是否构成一个商品包
        Map<Long, List<OrderDeliveryLine>> packageMap = new HashMap<>();
        for (OrderDeliveryLine packageItem : deliveryHead.getDeliveryLines()) {
            if (packageItem.getPackageItemId() != null) {
                SalesLine salesLine = salesLineMapper.selectByPrimaryKey(packageItem.getOrderLineId());
                Long parentlineId = salesLine.getParentLineId();
                if (packageMap.containsKey(parentlineId)) {
                    packageMap.get(parentlineId).add(packageItem);
                } else {
                    List<OrderDeliveryLine> packageList = new ArrayList<>();
                    packageList.add(packageItem);
                    packageMap.put(parentlineId, packageList);
                }
            }
        }
        if (packageMap.size() > 0) {
            for (Long parentlineId : packageMap.keySet()) {
                Long packageItemId = packageMap.get(parentlineId).get(0).getPackageItemId();
                List<InvItemHierarchy> invItemHierachys = invItemHierarchyMapper.getHierarchyByItemId(packageItemId);
                // 退回商品包数量
                BigDecimal returnQtyPackageNum = null;
                // 本次发运商品包数量
                BigDecimal outstandingQtyPackageNum = null;
                for (InvItemHierarchy hieratchy : invItemHierachys) {

                    boolean flag = false;
                    for (OrderDeliveryLine dine : packageMap.get(parentlineId)) {
                        if (dine.getItemId().equals(hieratchy.getItemId())) {
                            if (dine.getReturnQty() == null) {
                                dine.setReturnQty(BigDecimal.ZERO);
                            }
                            try {
                                BigDecimal outstandingQty = dine.getOutstandingQty().divide(hieratchy.getQuantity());
                                BigDecimal returnQty = dine.getReturnQty().divide(hieratchy.getQuantity());

                                if (!isDouble(returnQty) || !isDouble(outstandingQty)) {
                                    throw new DeliveryException(CommDeliveryException.RETURN_MUST_MAKE_UP_PACKAGE,
                                            new Object[] {});
                                }
                                if (returnQtyPackageNum == null) {
                                    returnQtyPackageNum = returnQty;
                                    flag = true;
                                }
                                if (outstandingQtyPackageNum == null) {
                                    outstandingQtyPackageNum = outstandingQty;
                                    flag = true;
                                }
                                if (returnQtyPackageNum.compareTo(returnQty) != 0
                                        || outstandingQtyPackageNum.compareTo(outstandingQty) != 0) {
                                    throw new DeliveryException(CommDeliveryException.RETURN_MUST_MAKE_UP_PACKAGE,
                                            new Object[] {});
                                } else {
                                    flag = true;
                                }

                            } catch (Exception e) {
                                throw new DeliveryException(CommDeliveryException.RETURN_MUST_MAKE_UP_PACKAGE,
                                        new Object[] {});
                            }
                        }
                    }
                    if (!flag) {
                        throw new DeliveryException(CommDeliveryException.RETURN_MUST_MAKE_UP_PACKAGE, new Object[] {});
                    }
                }
            }
        }
        for (OrderDeliveryLine deliveryLine : deliveryLines) {

            // 批次控制
            String batchCtrlFlag = deliveryLine.getBatchCtrlFlag();

            BigDecimal currentDeliveryQuantity = deliveryLine.getOutstandingQty();

            List<OrderDeliveryLot> lots = deliveryLine.getLots();

            // 商品启用批次控制，必须含有批次信息
            if (DeliveryConstants.YES.equals(batchCtrlFlag) && (lots == null || lots.isEmpty())) {
                throw new DeliveryException(DeliveryException.DELIVERY_LOT_EMPTY, new Object[] {});
            }
            if (deliveryLine.getReturnQty() == null) {
                deliveryLine.setReturnQty(BigDecimal.ZERO);
            }

            // 挑库数量>= 本次发运数量 + 退回数量
            if (deliveryLine.getPickQuantity()
                    .compareTo(deliveryLine.getOutstandingQty().add(deliveryLine.getReturnQty())) < 0) {
                throw new DeliveryException(DeliveryException.MORE_THAN_PICKEDQTY, new Object[] {});
            }

            BigDecimal lotQuantitySum = BigDecimal.ZERO;
            BigDecimal lotReturnQtySum = BigDecimal.ZERO;
            Map<String, BigDecimal> lotQuantityMap = new HashMap<>();

            if (DeliveryConstants.YES.equals(batchCtrlFlag)) {

                for (OrderDeliveryLot lot : lots) {

                    if (DTOStatus.DELETE.equals(lot.get__status())) {
                        continue;
                    }
                    if (lot.getReturnQty() == null) {
                        lot.setReturnQty(BigDecimal.ZERO);
                    }
                    if (lot.getReturnQty().compareTo(lot.getOutstandingQty()) > 0) {
                        throw new DeliveryException(DeliveryException.MORE_THAN_DELIVERYQTY, new Object[] {});
                    }
                    lotQuantitySum = lotQuantitySum.add(lot.getOutstandingQty().subtract(lot.getReturnQty()));
                    lotReturnQtySum = lotReturnQtySum.add(lot.getReturnQty());

                    BigDecimal lotQuantity = lotQuantityMap.get(lot.getLotNumber());
                    if (lotQuantity == null) {
                        lotQuantity = BigDecimal.ZERO;
                    }
                    lotQuantity = lotQuantity.add(lot.getOutstandingQty().subtract(lot.getReturnQty()));
                    lotQuantityMap.put(lot.getLotNumber(), lotQuantity);
                }

                // 行上退回数量 = SUM(批次退回数量)
                if (!lotReturnQtySum.equals(deliveryLine.getReturnQty())) {
                    throw new DeliveryException(DeliveryException.MORE_THAN_LL, new Object[] {});
                }

                // 本次发运数量= SUM(批次发运数量-批次退回数量)
                if (!currentDeliveryQuantity.equals(lotQuantitySum)) {
                    throw new DeliveryException(DeliveryException.MUST_EQUAL_DELIVERYQTY, new Object[] {});
                }
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDelivery submit(IRequest request, OrderDelivery deliveryHead)
            throws InventoryException, CommDeliveryException {
        if (DeliveryConstants.DELIVERY_STATUS_PENDDING.equals(deliveryHead.getDeliveryStatus())) {

            OrderDelivery d = dmOrderDeliveryMapper.queryByPrimaryKey(deliveryHead.getDeliveryId());
            if (!DeliveryConstants.DELIVERY_STATUS_PENDDING.equals(d.getDeliveryStatus())) {
                throw new CommDeliveryException(CommDeliveryException.CURRENT_STATUS_CANNOT_BE_COMMITTED,
                        new Object[] {});
            }
            // 提交前做保存
            self().save(request, deliveryHead);

            // 处理日期不能为空
            if (deliveryHead.getDeliveryDate() == null) {
                throw new CommDeliveryException(CommDeliveryException.NULL_TRANSACTION_DATE, new Object[] {});
            }

            if (DeliveryConstants.DELIVERY_TYPE_PICK_UP.equals(deliveryHead.getDeliveryType())) {
                deliveryHead.setDeliveryStatus(DeliveryConstants.DELIVERY_STATUS_PCKUP);
            } else {
                deliveryHead.setDeliveryStatus(DeliveryConstants.DELIVERY_STATUS_SHIPPED);
            }

            dmOrderDeliveryMapper.updateByPrimaryKeySelective(deliveryHead);

            List<OrderDeliveryLine> lines = deliveryHead.getDeliveryLines();
            // 调用事务处理程序
            self().processTransaction(request, deliveryHead);
            Map<Long, SalesLine> packageMap = new HashMap<>();
            for (OrderDeliveryLine line : lines) {
                SalesLine salesLine = salesLineMapper.selectByPrimaryKeyForLock(line.getOrderLineId());
                // 修改订单行未发运数量
                if (line.getReturnQty() != null) {
                    salesLine.setUnpickQuantity(salesLine.getUnpickQuantity().add(line.getReturnQty()));
                }
                // 修改销售订单行状态
                BigDecimal shippedAmount = dmOrderDeliveryMapper.getShippedAmountByOrderLine(line.getOrderLineId());

                if (salesLine.getQuantity().equals(shippedAmount)) {
                    // 已发运
                    salesLine.setStatus(OrderConstants.LINE_DELIVERY_STATUS_SHIPP);
                } else if (BigDecimal.ZERO.compareTo(shippedAmount) < 0) {
                    // 部分发运
                    salesLine.setStatus(OrderConstants.LINE_DELIVERY_STATUS_PSHIP);
                }
                if (salesLine.getParentLineId() != null) {
                    SalesLine parentLine = salesLineMapper.selectByPrimaryKeyForLock(salesLine.getParentLineId());
                    parentLine.setStatus(salesLine.getStatus());
                    salesLineMapper.updateByPrimaryKeySelective(parentLine);
                }
                salesLineMapper.updateByPrimaryKeySelective(salesLine);

                // 修改虚拟商品包状态.
                if (salesLine.getParentLineId() != null && !packageMap.containsKey(salesLine.getParentLineId())) {

                    SalesLine parentLine = salesLineMapper.selectByPrimaryKeyForLock(salesLine.getParentLineId());
                    BigDecimal unitQuantity = salesLine.getQuantity().divide(parentLine.getQuantity());
                    parentLine.setUnpickQuantity(salesLine.getUnpickQuantity().divide(unitQuantity));
                    salesLineMapper.updateByPrimaryKeySelective(parentLine);
                    packageMap.put(salesLine.getParentLineId(), parentLine);
                }
            }

            // 判断是否需要生成新发运单
            List<DeliveryPickLine> pickLines = new ArrayList<>();
            for (OrderDeliveryLine line : lines) {
                if (DTOStatus.DELETE.equals(line.get__status())) {
                    continue;
                }

                BigDecimal remainQty = line.getPickQuantity().subtract(line.getOutstandingQty())
                        .subtract(line.getReturnQty());

                if (BigDecimal.ZERO.compareTo(remainQty) < 0) {
                    // “挑库数量—本次发运数量—退回数量” > 0
                    // 需要重新生成发运单
                    DeliveryPickLine pickLine = new DeliveryPickLine();
                    pickLine.setOrderLineId(line.getOrderLineId());
                    pickLine.setInvOrgId(line.getInvOrgId());
                    pickLine.setPickQuantity(remainQty);
                    pickLine.setSurplusQty(line.getUnShippedQuantity());
                    pickLine.setInventory(line.getOnhandQuantity());
                    pickLine.setCountItemId(line.getCountItemId());
                    pickLine.setPackageItemId(line.getPackageItemId());
                    pickLine.setItemId(line.getItemId());
                    pickLines.add(pickLine);
                }
            }
            if (pickLines.size() > 0) {
                // 生成新发运单
                DeliveryPickHead pickHead = new DeliveryPickHead();
                pickHead.setOrderId(deliveryHead.getOrderHeaderId());
                pickHead.setRemark(deliveryHead.getDeliveryNumber());
                pickHead.setLines(pickLines);

                // 保存挑库单
                commDeliveryPickService.saveDeliveryPick(request, pickHead);
            }
        } else {
            throw new DeliveryException(DeliveryException.STATUS_NOT_MATCH, new Object[] {});
        }

        return deliveryHead;
    }

    /**
     * 执行事务处理.
     * 
     * @param request
     * @param deliveryHead
     * @throws InventoryException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processTransaction(IRequest request, OrderDelivery deliveryHead) throws InventoryException {
        List<OrderDeliveryLine> lines = deliveryHead.getDeliveryLines();
        List<InvTransaction> trxs = new ArrayList<>();
        for (OrderDeliveryLine line : lines) {

            if (DeliveryConstants.YES.equals(line.getBatchCtrlFlag())) {
                List<OrderDeliveryLot> lots = line.getLots();
                for (OrderDeliveryLot lot : lots) {
       
                    // 验证库存
                    boolean checkFlag = invCheckService.checkInv(request, line.getCountItemId(),
                            deliveryHead.getInvOrgId(), null, null, lot.getLotNumber(),
                            lot.getOutstandingQty().subtract(lot.getReturnQty()));
                    if (!checkFlag) {
                        throw new InventoryException(InventoryException.MSG_ERROR_INV_BATCH_INVENTORY_SHORTAGE,
                                new Object[] { line.getItemNumber(), lot.getLotNumber() });
                    }

                    InvTransaction trx = new InvTransaction();
                    trx.setOrganizationId(deliveryHead.getInvOrgId());
                    trx.setItemId(line.getCountItemId());
                    trx.setTrxDate(deliveryHead.getDeliveryDate());
                    BigDecimal trxQty = lot.getOutstandingQty().subtract(lot.getReturnQty()).negate();
                    if (BigDecimal.ZERO.equals(trxQty)) {
                        // 发运数量为0，不生成库存事务
                        continue;
                    }
                    trx.setTrxQty(trxQty);
                    trx.setTrxType(InventoryConstants.TRANSACTION_TYPE_DELIVERY);
                    trx.setTrxSourceType(TRX_SOURCE_TYPE_LOT);
                    trx.setTrxSourceKey(lot.getDeliveryLotId().toString());
                    trx.setLotNumber(lot.getLotNumber());
                    trx.setExpiryDate(lot.getExpiryDate());
                    trx.setSubinventoryId(-1L);
                    trx.setTrxSourceReference(deliveryHead.getDeliveryNumber());
                    trx.setPackageItemId(line.getPackageItemId());

                    StringBuilder sb = new StringBuilder();
                    if (!StringUtils.isEmpty(deliveryHead.getRemark())) {
                        sb.append(deliveryHead.getRemark()).append(";");
                    }
                    if (lot.getRemark() != null) {
                        sb.append(lot.getRemark());
                    }

                    trx.setRemark(sb.toString());
                    trxs.add(trx);
                }

            } else {
                // 验证库存
                boolean checkFlag = invCheckService.checkInv(request, line.getCountItemId(), deliveryHead.getInvOrgId(),
                        null, null, null, line.getOutstandingQty());
                if (!checkFlag) {
                    throw new InventoryException(InventoryException.MSG_ERROR_INV_INVENTORY_SHORTAGE,
                            new Object[] { line.getItemNumber() });
                }
                InvTransaction trx = new InvTransaction();
                trx.setOrganizationId(deliveryHead.getInvOrgId());
                trx.setItemId(line.getItemId());
                trx.setTrxDate(deliveryHead.getDeliveryDate());
                trx.setTrxQty(line.getOutstandingQty().negate());
                trx.setTrxType(InventoryConstants.TRANSACTION_TYPE_DELIVERY);
                trx.setTrxSourceType(TRX_SOURCE_TYPE_LINE);
                trx.setPackageItemId(line.getPackageItemId());
                trx.setTrxSourceKey(line.getLineId().toString());
                trx.setSubinventoryId(-1L);
                trx.setTrxSourceReference(deliveryHead.getDeliveryNumber());
                if (deliveryHead.getRemark() != null) {
                    trx.setRemark(deliveryHead.getRemark());
                }
                trxs.add(trx);
            }

        }
        if(trxs.size() == 0){
            throw new InventoryException(InventoryException.MSG_ERROR_INV_TRANSACTION_CAN_NOT_NULL,
                    new Object[] { });
        }
        invTransactionService.processTransaction(request, trxs);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDelivery cancel(IRequest request, Long deliveryId) throws DeliveryException {
        OrderDelivery deliveryHead = dmOrderDeliveryMapper.selectByPrimaryKey(request.getLocale(), deliveryId);
        if (DeliveryConstants.DELIVERY_STATUS_NEW.equals(deliveryHead.getDeliveryStatus())
                || DeliveryConstants.DELIVERY_STATUS_PENDDING.equals(deliveryHead.getDeliveryStatus())) {
            deliveryHead.setDeliveryStatus(DeliveryConstants.DELIVERY_STATUS_CANCLED);
            deliveryHead.setLastUpdatedBy(request.getAccountId());
            deliveryHead.setLastUpdateDate(new Date());
            dmOrderDeliveryMapper.updateByPrimaryKey(deliveryHead);
            Map<Long, SalesLine> packageMap = new HashMap<>();
            // 退回挑库数量
            List<OrderDeliveryLine> deliverylines = dmOrderDeliveryLineMapper.selectByDeleveryId(deliveryId);
            for (OrderDeliveryLine deliveryline : deliverylines) {
                SalesLine salesLine = salesLineMapper.selectByPrimaryKeyForLock(deliveryline.getOrderLineId());
                // 修改订单行未发运数量
                salesLine.setUnpickQuantity(salesLine.getUnpickQuantity().add(deliveryline.getPickQuantity()));
                salesLineMapper.updateByPrimaryKey(salesLine);
                // 修改虚拟商品包状态.
                if (salesLine.getParentLineId() != null && !packageMap.containsKey(salesLine.getParentLineId())) {

                    SalesLine parentLine = salesLineMapper.selectByPrimaryKeyForLock(salesLine.getParentLineId());
                    BigDecimal unitQuantity = salesLine.getQuantity().divide(parentLine.getQuantity());
                    parentLine.setUnpickQuantity(salesLine.getUnpickQuantity().divide(unitQuantity));
                    salesLineMapper.updateByPrimaryKeySelective(parentLine);
                    packageMap.put(salesLine.getParentLineId(), parentLine);
                }
            }
            return deliveryHead;
        } else {
            throw new DeliveryException(DeliveryException.STATUS_NOT_MATCH, new Object[] {});
        }
    }

    @Override
    public OrderDelivery getDeliveryDetails(IRequest request, Long deliveryId) {
        request.getLocale();
        OrderDelivery deliveryhead = dmOrderDeliveryMapper.selectByPrimaryKey(request.getLocale(), deliveryId);
        return deliveryhead;
    }

    @Override
    public List<OrderDeliveryLine> getDeliveryDetailsLine(IRequest requestContext, long deliveryId) {
        /*modified by furong.tang 获取销售组织确定商品价格*/
        OrderDelivery deliveryhead = dmOrderDeliveryMapper.selectByPrimaryKey(requestContext.getLocale(), deliveryId);
        Long orderHeaderId = deliveryhead.getOrderHeaderId();
        List<OrderDeliveryLine> deliverylines = dmOrderDeliveryLineMapper.selectByDeleveryIdAndOrderHeaderId(deliveryId,orderHeaderId);
        for (OrderDeliveryLine deliveryline : deliverylines) {
            List<OrderDeliveryLot> lots = dmOrderDeliveryLotMapper.selectByDeliveryLineId(deliveryline.getLineId());
            deliveryline.setLots(lots);
        }
        return deliverylines;
    }

    @Override
    public List<Map<String, Object>> getItemLots(IRequest request, long itemId, long orgId) {
        return dmOrderDeliveryLotMapper.getItemLots(itemId, orgId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Long> createDelivery(IRequest request, DeliveryPickHead pickRelease) throws CommDeliveryException {
        return commDeliveryService.createDelivery(request, pickRelease);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Long> createDeliveriesByOrder(IRequest request, SalesOrder salesOrder) throws CommDeliveryException {
        return commDeliveryService.createDeliveriesByOrder(request, salesOrder);
    }

    /**
     * 根据失效订单头ID修改关联发运单状态.
     */
    @Override
    public void updateDeliveryStatus(IRequest request, Long headerId, String newStatus, List<String> status) {
        dmOrderDeliveryMapper.updateDeliveryStatus(headerId, newStatus, status);
    }

    /**
     * 判断BigDecimal是是否整数.
     * 
     * @param b
     *            要判断的整数
     * @return true:整数 false ：非整数.
     */

    private static Boolean isDouble(BigDecimal b) {

        return b.compareTo(new BigDecimal(b.intValue())) == 0;

    }
}

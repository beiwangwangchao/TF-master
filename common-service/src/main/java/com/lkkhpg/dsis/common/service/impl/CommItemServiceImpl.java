/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.common.constant.ProductConstants;
import com.lkkhpg.dsis.common.order.dto.OrderType;
import com.lkkhpg.dsis.common.order.dto.SalesType;
import com.lkkhpg.dsis.common.order.mapper.OrderTypeMapper;
import com.lkkhpg.dsis.common.product.dto.*;
import com.lkkhpg.dsis.common.product.mapper.PriceListDetailMapper;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.product.mapper.InvCategoryBMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemPropertyMapper;
import com.lkkhpg.dsis.common.service.ICommItemService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 商品CommonService实现类.
 * 
 * @author chenjingxiong
 */
@Service
@Transactional
public class CommItemServiceImpl implements ICommItemService {

    @Autowired
    private InvItemMapper invItemMapper;

    @Autowired
    private InvCategoryBMapper invCategoryBMapper;

    @Autowired
    private InvItemPropertyMapper invItemPropertyMapper;

    @Autowired
    private PriceListDetailMapper priceListDetailMapper;

    @Autowired
    private OrderTypeMapper orderTypeMapper;

    @Override
    public List<InvCategoryB> queryCategory(IRequest request, InvCategoryB invCategoryB) {
        List<InvCategoryB> categoryList = invCategoryBMapper.queryCategory(invCategoryB);
        return categoryList;
    }

    @Override
    public InvItem queryItemDetails(IRequest request, Long itemId) {
        InvItem item = invItemMapper.getItemByIdForOrder(itemId);
        return item;
    }

    @Override
    public List<InvItem> queryItems(IRequest request, InvItem item, int page, int pageSize) {
        // 进行分页
        PageHelper.startPage(page, pageSize);
        //modify by weiming.wei 20180119 通过销售组织对商品进行查询
        return invItemMapper.queryItemByOrg(item);

        //return invItemMapper.queryItem(item);
    }
    
    @Override
    public List<InvItem> queryItemsForOrder(IRequest request, InvItem item, int page, int pageSize) {
        // 进行分页
        PageHelper.startPage(page, pageSize);
        return invItemMapper.queryItemForOrder(item);
    }

    @Override
    public List<InvItem> queryItemsOfUnitConvert(IRequest request, InvItem invItem, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return invItemMapper.queryItemsOfUnitConvert(invItem);
    }

    @Override
    public InvItemPropertyV getItemProperties(IRequest request, Long itemId, Long invOrgId) {
        InvItemPropertyV pv = invItemPropertyMapper.queryItemPropertyVByItemIdAndOrgId(itemId, invOrgId);
        if (pv == null) {
            pv = invItemPropertyMapper.queryItemPropertyVByItemIdAndOrgId(itemId, null);
        }
        return pv;
    }




    @Override
    public OrderItemPrice queryItemPriceForOrder(IRequest request, Long itemId, String currency, String uomCode,
                                                 Long salesOrgId, String orderType, String itemSalestype) {

        OrderItemPrice orderItemPrice = new OrderItemPrice(new BigDecimal(0), new BigDecimal(0), new BigDecimal(0),
                "N");
        OrderType ot = orderTypeMapper.queryForOrder(salesOrgId, orderType, itemSalestype);
        if (ot != null) {
            orderItemPrice.setSalesPriceType(ot.getPriceType());
            PriceListDetail priceListDetail = priceListDetailMapper.selectPriceForOrder(itemId, currency, uomCode,
                    salesOrgId, ot.getPriceType());
            if (priceListDetail != null) {
                orderItemPrice.setDisableFlag(priceListDetail.getDisableTaxFlag());
            }
            if (ot.getSalesType() != null && ot.getSalesType().size() > 0) {
                SalesType st = ot.getSalesType().iterator().next();
                if (st != null) {
                    orderItemPrice.setTaxPriceType(st.getTaxPrice());
                    if (BaseConstants.NO.equals(st.getFreeFlag())) {
                        orderItemPrice.setSalesPrice(priceListDetail.getUnitPrice());
                    }
                    if (BaseConstants.YES.equals(st.getCalPvFlag())) {
                        PriceListDetail priceListDetail1 = priceListDetailMapper.selectPriceForOrder(itemId, currency,
                                uomCode, salesOrgId, ProductConstants.PRICE_TYPE_PV);
                        if (priceListDetail1 != null) {
                            orderItemPrice.setPv(priceListDetail1.getUnitPrice());
                        }
                    }

                    // 免费计税金额为零
                    if (BaseConstants.YES.equals(st.getFreeFlag())) {
                        orderItemPrice.setTaxPrice(BigDecimal.ZERO);
                    } else {
                        // 计税类型
                        if (!st.getTaxPrice().equals(ot.getPriceType())) {
                            PriceListDetail priceListDetail1 = priceListDetailMapper.selectPriceForOrder(itemId,
                                    currency, uomCode, salesOrgId, st.getTaxPrice());
                            if (priceListDetail1 != null) {
                                orderItemPrice.setTaxPrice(priceListDetail1.getUnitPrice());
                            }
                        } else {
                            orderItemPrice.setTaxPrice(orderItemPrice.getSalesPrice());
                        }
                    }
                }
            }
        }
        return orderItemPrice;
    }
}

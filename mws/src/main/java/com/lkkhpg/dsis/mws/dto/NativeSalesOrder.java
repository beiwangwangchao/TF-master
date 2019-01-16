/*
 *
 */
package com.lkkhpg.dsis.mws.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 前台传来的未处理过的订单信息DTO.
 *
 * @author guanghui.liu
 */
public class NativeSalesOrder extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 不完整的订单信息.
     */
    private SalesOrder salesOrder;
    
    /**
     * 只有id和数量的订单产品信息.
     */
    @JsonInclude(Include.NON_NULL)
    private List<ShopCartItem> prods;
    
    /**
     * 不完整的autoship订单信息.
     */
    private AutoshipOrder autoshipOrder;
    
    /**
     * 只有id和数量的订单产品信息.
     */
    @JsonInclude(Include.NON_NULL)
    private List<Product> products;

    /**
     * 支付方式.
     */
    private OmMwsOrderPayment omMwsOrderPayment;

    
    public OmMwsOrderPayment getOmMwsOrderPayment() {
        return omMwsOrderPayment;
    }

    public void setOmMwsOrderPayment(OmMwsOrderPayment omMwsOrderPayment) {
        this.omMwsOrderPayment = omMwsOrderPayment;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public List<ShopCartItem> getProds() {
        return prods;
    }

    public void setProds(List<ShopCartItem> prods) {
        this.prods = prods;
    }

    public AutoshipOrder getAutoshipOrder() {
        return autoshipOrder;
    }

    public void setAutoshipOrder(AutoshipOrder autoshipOrder) {
        this.autoshipOrder = autoshipOrder;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.service;

import java.util.List;

import com.lkkhpg.dsis.admin.delivery.exception.DeliveryException;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTier;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierDtl;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierSeg;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 承运商接口.
 * 
 * @author huangjiajing
 */
public interface IShippingTierService extends ProxySelf<IShippingTierService> {

    /**
     * 根据货币以及地区获取承运商.
     * 
     * @param request
     *            上下文请求.
     * @param location
     *            地址
     * @param currency
     *            货币
     * @param apptype
     *            应用类型
     * @return 满足条件的承运商
     */
    List<ShippingTier> queryShippingTiersByLocation(IRequest request, SpmLocation location, Long currency,
            String apptype);

    /**
     * 查询物流资料信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTier
     *            承运商信息
     * @param page
     *            页数默认是1
     * @param pagesize
     *            每页显示的行数默认是10
     * @return 物流资料信息
     */
    List<ShippingTier> selectShippingTiers(IRequest request, ShippingTier shippingTier, int page, int pagesize);

    /**
     * 根据条件查询物流运费.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtls
     *            物流运费信息
     * @return 运费查询结果
     */
    List<ShippingTierDtl> selectShippingTierDtls(IRequest request, ShippingTierDtl shippingTierDtls);

    /**
     * 根据条件查询地点信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtlSegs
     *            地点详细信息
     * @return 地点查询结果
     */
    List<ShippingTierSeg> selectShippingTierDtlSegs(IRequest request, ShippingTierSeg shippingTierDtlSegs);

    /**
     * 创建一条新的物流信息.
     * 
     * @param request
     * 
     * @param shippingTier
     *            承运商信息
     * @return 承运商对象
     * @throws DeliveryException
     */
    ShippingTier createShippingTier(IRequest request, @StdWho ShippingTier shippingTier) throws DeliveryException;

    /**
     * 删除物流信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTiers
     *            承运商对象
     */
    void batchDelete(IRequest request, @StdWho List<ShippingTier> shippingTiers);

    /**
     * 根据id删除运费信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtls
     *            运费信息
     */
    void batchDeleteShippingTierDtls(IRequest request, @StdWho List<ShippingTierDtl> shippingTierDtls);

    /**
     * 删除地点信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtlSegs
     *            地点信息
     */
    void batchDeleteShippingTierDtlSegs(IRequest request, List<ShippingTierSeg> shippingTierDtlSegs);

    /**
     * 插入一条新的运费信息.
     * 
     * @param shippingTierDtl
     *            运费信息对象
     * @return 运费对象
     */
    ShippingTierDtl insertShippingTierDtl(@StdWho ShippingTierDtl shippingTierDtl);

    /**
     * 插入一条新的地点信息.
     * 
     * @param shippingTierSeg
     *            地点信息
     * 
     * @return 地点对象
     */
    ShippingTierSeg insertShippingTierSeg(@StdWho ShippingTierSeg shippingTierSeg);

    /**
     * 修改承运商信息.
     * 
     * @param request
     * 
     * @param shippingTier
     *            承运商对象
     * @return 承运商对象
     * @throws DeliveryException
     */
    ShippingTier updateShippingTier(IRequest request, @StdWho ShippingTier shippingTier) throws DeliveryException;

    /**
     * 提交判断是修改或新增.
     * 
     * @param request
     *            请求上下文
     * @param shippingTiers
     *            承运商对象
     * @return 承运商信息
     * @throws DeliveryException
     */
    List<ShippingTier> batchUpdate(IRequest request, @StdWho List<ShippingTier> shippingTiers) throws DeliveryException;

    /**
     * 判断地点是否重复.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtlSegs
     *            地点dto
     * @return true/false
     */
    Boolean isExistShippingTierSeg(IRequest request, ShippingTierSeg shippingTierDtlSegs);

    /**
     * 验证地点.
     * 
     * @param request
     *            请求上下文
     * @param shippingTiers
     *            头表集合
     * @return true/false
     */
    Boolean validateShippingTierSeg(IRequest request, List<ShippingTier> shippingTiers);

    /**
     * 验证头表.
     * 
     * @param request
     *            请求上下文
     * @param shippingTiers
     *            头表集合
     * @return true/false
     */
    Boolean validateShippingTier(IRequest request, List<ShippingTier> shippingTiers);

    /**
     * 判断头表重复.
     * 
     * @param request
     *            请求上下文
     * @param shippingTier
     *            头表集合
     * @return true/false
     */
    Boolean isExistShippingTier(IRequest request, ShippingTier shippingTier);
}

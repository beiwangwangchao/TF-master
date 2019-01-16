/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;

import com.lkkhpg.dsis.common.delivery.dto.ShippingTier;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierSeg;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 物流信息service.
 * 
 * @author guanghui.liu
 */
public interface IShippingTierService extends ProxySelf<IShippingTierService> {

    /**
     * 查询选中地址下所有可用物流.
     * 
     * @param iRequest
     *            统一上下文
     * @param shippingTierSeg
     *            包含location信息
     * @param salesOrgId
     *            销售区域ID
     * @param currencyCode
     *            本位币
     * @return 可用物流列表
     */
    List<ShippingTier> queryShippingTier(IRequest iRequest, ShippingTierSeg shippingTierSeg, Long salesOrgId,
            String currencyCode,String apptype);

}

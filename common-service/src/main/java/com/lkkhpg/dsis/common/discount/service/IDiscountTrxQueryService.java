package com.lkkhpg.dsis.common.discount.service;

/**
 * Project: pos2
 * Package: com.lkkhpg.dsis.common.discount.service
 * User: 11816
 * Date: 2018/1/23
 * Time: 11:16
 */
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxQuery;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.util.List;

/**
 * Project: pos2
 * Package: com.lkkhpg.dsis.common.discount.service
 * User: 11816
 * Date: 2018/1/18
 * Time: 20:03
 */
public interface IDiscountTrxQueryService extends ProxySelf<IDiscountTrxHeadService> {
    public List<DiscountTrxQuery> queryBasic(IRequest request, DiscountTrxQuery discountTrxQuery, int page, int pagesize);

    public List<DiscountTrxQuery> query( DiscountTrxQuery discountTrxQuery);
}


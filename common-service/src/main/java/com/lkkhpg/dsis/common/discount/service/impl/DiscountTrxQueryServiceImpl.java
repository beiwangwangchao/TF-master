package com.lkkhpg.dsis.common.discount.service.impl;


import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxQuery;
import com.lkkhpg.dsis.common.discount.mapper.DiscountTrxQueryMapper;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxQueryService;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project: pos2
 * Package: com.lkkhpg.dsis.common.discount.service.impl
 * User: 11816
 * Date: 2018/1/18
 * Time: 20:03
 */
@Service
public class DiscountTrxQueryServiceImpl implements IDiscountTrxQueryService {

    @Autowired
    private DiscountTrxQueryMapper discountTrxQueryMapper;

    @Override
    public List<DiscountTrxQuery> queryBasic(IRequest request, DiscountTrxQuery discountTrxQuery, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);

        return discountTrxQueryMapper.queryBasic(discountTrxQuery,discountTrxQuery.getReasonList());
    }
    @Override
    public List<DiscountTrxQuery> query( DiscountTrxQuery discountTrxQuery){
        return discountTrxQueryMapper.query(discountTrxQuery);
    }
}


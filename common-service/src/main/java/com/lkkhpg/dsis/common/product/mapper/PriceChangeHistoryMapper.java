/**
 * Created by miaoyifan on 2018/1/3.
 */
package com.lkkhpg.dsis.common.product.mapper;

import com.lkkhpg.dsis.common.product.dto.PriceChangeHistory;

/*
* 商品价格变更mapper
* */
public interface PriceChangeHistoryMapper {
    int insert(PriceChangeHistory record);
}

/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import com.lkkhpg.dsis.common.order.dto.CreditCard;

/**
 * 信用卡mapper.
 * 
 * @author wuyichu
 */
public interface CreditCardMapper {
    int deleteByPrimaryKey(Long creditCardId);

    int insert(CreditCard record);

    int insertSelective(CreditCard record);

    CreditCard selectByPrimaryKey(Long creditCardId);

    int updateByPrimaryKeySelective(CreditCard record);

    int updateByPrimaryKey(CreditCard record);
}
/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;

/**
 * MWS订单支付Mapper.
 * @author shenqb
 *
 */
public interface OmMwsOrderPaymentMapper {
    int deleteByPrimaryKey(Long paymentId);

    int insert(OmMwsOrderPayment record);

    int insertSelective(OmMwsOrderPayment record);

    OmMwsOrderPayment selectByPrimaryKey(Long paymentId);

    int updateByPrimaryKeySelective(OmMwsOrderPayment record);

    int updateByPrimaryKey(OmMwsOrderPayment record);
    
    List<OmMwsOrderPayment> selectByAttributes(OmMwsOrderPayment record);
    
    OmMwsOrderPayment queryRemainingBalSum(Long memberid);
    
    int updateRemainingBalStatus(OmMwsOrderPayment omMwsOrderPayment);
}
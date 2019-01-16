/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.MemberInfo;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderInfo;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IAddOrderCallbackService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackBody;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackResponse;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackBody.MessageBody;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.integration.dapp.service.IDAppCallbackService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.mysql.fabric.xmlrpc.base.Data;

/**
 * 创建订单callback实现类.
 * 
 * @author shenqb
 *
 */
@Service
@Transactional
public class AddOrderCallbackServiceImpl implements IAddOrderCallbackService {

    private Logger logger = LoggerFactory.getLogger(AddOrderCallbackServiceImpl.class);

    @Autowired
    private IDAppCallbackService dAppCallbackService;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SpmMarketMapper spmMarkeetMapper;

    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private IDAppUtilService dAppUtilService;

    @Override
    public boolean addOrderCallback() throws DAppException {
        List<SalesOrder> orders = findNoSyncOrders();
        IRequest iRequest = RequestHelper.newEmptyRequest();
        iRequest.setLocale(BaseConstants.DEFAULT_LANG);
        iRequest.setAccountId(dAppUtilService.getDappAccountId());
        for (SalesOrder order : orders) {
            try {
                updateSyncFlag(iRequest, order);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("addOrderCallback -> update syncFlag error ：{}", IntegrationException.getErrorStackTrace(e));
                }
            }

        }
        return true;
    }

    private List<SalesOrder> findNoSyncOrders() {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("dappSyncFlag", "N");
        List<SalesOrder> orders = salesOrderMapper.queryOrdersForDapp(queryMap);
        return orders;

    }

    public void updateSyncFlag(IRequest iRequest, SalesOrder order) throws IntegrationException {
        SpmMarket spmMarket = spmMarkeetMapper.selectBySalesOrgId(order.getSalesOrgId());
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setMarket(spmMarket == null ? null : spmMarket.getCode());
        orderInfo.setOrderID(order.getOrderNumber());
        orderInfo.setOrderType(order.getOrderType());

        MemberInfo memberInfo = new MemberInfo();
        Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
        memberInfo.setMemberID(member.getMemberCode());
        memberInfo.setMemberType(member.getMemberType());
        memberInfo.setMarket(member.getMarketCode());
        memberInfo.setAreaCode(member.getAreaCode());
        memberInfo.setEmail(member.getEmail());
        memberInfo.setMobileNumber(member.getPhoneNo());
        memberInfo.setMemberRole(member.getMemberRole());

        MessageBody messageBody = new MessageBody();
        messageBody.setAction(IntegrationConstant.DAPP_CALLBACK_UPDATE);
        if (IntegrationConstant.ORDER_STATUS_VOID.equals(order.getOrderStatus())) {
            messageBody.setAction(IntegrationConstant.DAPP_CALLBACK_CANCEL);
        }
        messageBody.setOrderInfo(orderInfo);
        messageBody.setMemberInfo(memberInfo);
        DAppCallbackBody dAppCallbackBody = new DAppCallbackBody();
        dAppCallbackBody.setMessageBody(messageBody);
        dAppCallbackBody.generateClientCallbackID();
        dAppCallbackBody.setJobType(IntegrationConstant.DAPP_CALLBACK_JOB_TYPE);
        DAppCallbackResponse isCallback = null;
        try {
            isCallback = dAppCallbackService.callback(dAppCallbackBody);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("addOrderCallback -> dAppCallbackService.callback error ：{}", IntegrationException.getErrorStackTrace(e));
            }
        }
        
        if (isCallback != null && isCallback.getData() != null && isCallback.getData().getId() != null) {
            order.setDappSyncFlag("Y");
            try {
                salesOrderMapper.updateByPrimaryKeySelective(order);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("addOrderCallback -> update syncFlag error ：{}", IntegrationException.getErrorStackTrace(e));
                }
                throw new IntegrationException(IntegrationException.MSG_ERROR_ORDER_UPDATE_ASNC_FLAG,
                        new Object[] { IntegrationException.getErrorStackTrace(e) });
            }

        } else {
            if (logger.isErrorEnabled()) {
                logger.error("addOrderCallback ：callback msg null or errorCode != 0");
                if(isCallback != null && isCallback.getError() != null && isCallback.getError().getMessage() != null){
                    logger.error("addOrderCallback error result：{}", isCallback.getError().getMessage());
                }
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ORDER_CALLBACK_RETURN_MSG_ERROR, null);
        }
    }

}

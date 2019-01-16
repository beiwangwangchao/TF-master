/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.CallBackMemberInfo;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderInfo;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDistributorCallBackService;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackBody;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackResponse;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackBody.MessageBody;
import com.lkkhpg.dsis.integration.dapp.service.IDAppCallbackService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;

/**
 * POS distributor callback实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class DistributorCallBackServiceImpl implements IDistributorCallBackService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IDAppCallbackService dAppCallbackService;
    
    @Autowired
    private IDAppUtilService dAppUtilService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callbackDistributor(IRequest request, Member member, String action) {
        DAppCallbackBody dAppCallbackBody = new DAppCallbackBody();
        String id = dAppCallbackBody.generateClientCallbackID();
        dAppCallbackBody.setClientCallbackID(id);
        dAppCallbackBody.setJobType(IntegrationConstant.CALLBACK_JOB_TYPE_MEMBER);

        MessageBody messageBody = new MessageBody();
        messageBody.setAction(action);

        CallBackMemberInfo memberInfo = new CallBackMemberInfo();
        memberInfo.setMemberID(member.getMemberCode());
        memberInfo.setMemberType(member.getMemberType());
        memberInfo.setMemberRole(member.getMemberRole());
        memberInfo.setMarket(member.getMarketCode());
        memberInfo.setAreaCode(member.getAreaCode());
        memberInfo.setMobileNumber(member.getPhoneNo());
        memberInfo.setEmail(member.getEmail());
        messageBody.setMemberInfo(memberInfo);
        messageBody.setOrderInfo(new OrderInfo());
        dAppCallbackBody.setMessageBody(messageBody);
        DAppCallbackResponse callback = dAppCallbackService.callback(dAppCallbackBody);
        if (callback != null && callback.getData() != null && callback.getData().getId() != null) {
            member.setDappSyncFlag(IntegrationConstant.YES);
            memberMapper.updateDAppSyncFlag(member);
        } else {
            if (callback != null && callback.getError() != null) {
                if (logger.isErrorEnabled()) {
                    logger.error(IntegrationConstant.FAILED_TO_CALLBACK, callback.getError().getCode(),
                            callback.getError().getMessage());
                }
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error(IntegrationConstant.FAILED_TO_CALLBACK, null, null);
                }
            }
            member.setDappSyncFlag(IntegrationConstant.NO);
            memberMapper.updateDAppSyncFlag(member);
        }
    }

    @Override
    public void callbackDistributorNoSync() {
        if (logger.isInfoEnabled()) {
            logger.info("======JOB RUNNING======");
        }
        try {
            List<Member> members = memberMapper.selectMemberByDappSync();
            for (Member member : members) {
                try {
                    DAppCallbackBody dAppCallbackBody = new DAppCallbackBody();
                    String id = dAppCallbackBody.generateClientCallbackID();
                    dAppCallbackBody.setClientCallbackID(id);
                    dAppCallbackBody.setJobType(IntegrationConstant.CALLBACK_JOB_TYPE_MEMBER);

                    MessageBody messageBody = new MessageBody();
                    messageBody.setAction(IntegrationConstant.OPER_TYPE_U);

                    CallBackMemberInfo memberInfo = new CallBackMemberInfo();
                    memberInfo.setMemberID(member.getMemberCode());
                    memberInfo.setMemberType(member.getMemberType());
                    memberInfo.setMemberRole(member.getMemberRole());
                    memberInfo.setMarket(member.getMarketCode());
                    memberInfo.setAreaCode(member.getAreaCode());
                    memberInfo.setMobileNumber(member.getPhoneNo());
                    memberInfo.setEmail(member.getEmail());

                    messageBody.setMemberInfo(memberInfo);
                    dAppCallbackBody.setMessageBody(messageBody);
                    DAppCallbackResponse callback = dAppCallbackService.callback(dAppCallbackBody);
                    IRequest request = RequestHelper.newEmptyRequest();
                    request.setAccountId(dAppUtilService.getDappAccountId());
                    if (callback != null && callback.getData() != null && callback.getData().getId() != null) {
                        member.setDappSyncFlag(IntegrationConstant.YES);
                        memberMapper.updateDAppSyncFlag(member);
                    } else {
                        if (callback != null && callback.getError() != null) {
                            if (logger.isErrorEnabled()) {
                                logger.error(IntegrationConstant.FAILED_TO_CALLBACK, callback.getError().getCode(),
                                        callback.getError().getMessage());
                            }
                        } else {
                            if (logger.isErrorEnabled()) {
                                logger.error(IntegrationConstant.FAILED_TO_CALLBACK, null, null);
                            }
                        }
                        member.setDappSyncFlag(IntegrationConstant.NO);
                        memberMapper.updateDAppSyncFlag(member);
                    }
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("======JOB FINISH======");
        }
    }

}

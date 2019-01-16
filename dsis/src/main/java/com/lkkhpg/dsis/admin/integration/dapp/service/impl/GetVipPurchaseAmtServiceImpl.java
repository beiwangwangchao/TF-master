/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPurchaseAmountRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPurchaseAmountResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPvRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPvResponse;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetVipPurchaseAmtService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetVipPvService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemApplyRole;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemApplyRoleMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;

/**
 * 查询VIP会员消费总额接口实现类.
 *
 * @author shenqb
 */
@Service
@Transactional
public class GetVipPurchaseAmtServiceImpl implements IGetVipPurchaseAmtService {

    private Logger logger = LoggerFactory.getLogger(GetVipPurchaseAmtServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IDAppUtilService dAppUtilService;
    
    @Autowired
    private ICommMemberService commMemberService;
    
    @Autowired
    private IMarketParamService marketParamService;
    
    @Autowired
    private MemApplyRoleMapper memApplyRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GetVipPurchaseAmountResponse getVipPurchaseAmt(GetVipPurchaseAmountRequest getVipPurchaseAmountRequest)
            throws DAppException {
        if (logger.isInfoEnabled()) {
            logger.info("dapp getVipPv begin:");
        }
        GetVipPurchaseAmountResponse getVipPurchaseAmtResponse = new GetVipPurchaseAmountResponse();
        IRequest iRequest = RequestHelper.newEmptyRequest();
        iRequest.setLocale(BaseConstants.DEFAULT_LANG);
        // 获取会员DTO, 校验会员是否为VIP
        Member member = memberMapper.selectMembersByMemberCode(getVipPurchaseAmountRequest.getVIPNumber());
        if (null == member) {
            if (logger.isErrorEnabled()) {
                logger.error("dapp -> get vip purchase error: member is null , error params : " + getVipPurchaseAmountRequest.getVIPNumber());
            }
            throw new DAppException(IntegrationConstant.MEMBER_CODE_NOT_EXISTED,
                    IntegrationConstant.ERROR_20001, "memberCode = " + getVipPurchaseAmountRequest.getVIPNumber());
        }
        if (!IntegrationConstant.MEMBER_ROLE_VIP.equals(member.getMemberRole())) {
            getVipPurchaseAmtResponse.setVIPNumber(getVipPurchaseAmountRequest.getVIPNumber());
            getVipPurchaseAmtResponse.setMemberRole(member.getMemberRole());
            return getVipPurchaseAmtResponse;
        }
        //获取qualifiedFlag, qualifiedAmt, purchaseAmt, dateFrom, dateTo
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            result  = commMemberService.validateForVIPToDIS(iRequest, member.getMemberId());
        } catch (CommMemberException e) {
            if (logger.isErrorEnabled()) {
                logger.error("dapp调用commMemberService的validateForVIPToDIS方法报错：{}", IntegrationException.getErrorStackTrace(e));
            }
            throw new DAppException(IntegrationException.getErrorStackTrace(e), IntegrationConstant.STATUS_CODE_MEMBER,
                    "memberCode = " + getVipPurchaseAmountRequest.getVIPNumber());
        }
        // 获取组织参数 - 币种
        String currency = marketParamService.getParamValue(iRequest, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.MARKET, member.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
        //设置qualifiedFlag
        String qualifiedFlag = "N";
        boolean isEqual = (boolean)result.get("qualifiedFlag");
        if(isEqual){
            qualifiedFlag = "Y";
            MemApplyRole memApplyRole = new MemApplyRole();
            memApplyRole.setMemberId(member.getMemberId());
            List<MemApplyRole> memApplyRoleList = memApplyRoleMapper.selectAllRecords(memApplyRole);
            if(memApplyRoleList.size() > 0){
                if(MemberConstants.CHANGE_ROLE_APPROVE_STATUS_APING.equals(memApplyRoleList.get(0).getStatus())){
                    qualifiedFlag = "P";
                }
            }
        }
        getVipPurchaseAmtResponse.setVIPNumber(getVipPurchaseAmountRequest.getVIPNumber());
        getVipPurchaseAmtResponse.setMemberRole(member.getMemberRole());
        getVipPurchaseAmtResponse.setQualifiedFlag(qualifiedFlag);
        getVipPurchaseAmtResponse.setQualifiedAmt(new BigDecimal((double)result.get("qualifiedAmt")));
        getVipPurchaseAmtResponse.setPurchaseAmt((BigDecimal)result.get("purchaseAmt"));
        getVipPurchaseAmtResponse.setDateFrom(dAppUtilService.toDateTimeString((Date)result.get("dateFrom")));
        getVipPurchaseAmtResponse.setDateTo(dAppUtilService.toDateTimeString((Date)result.get("dateTo")));
        getVipPurchaseAmtResponse.setCurrency(currency);
        return getVipPurchaseAmtResponse;
    }

}

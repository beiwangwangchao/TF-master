/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPvRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPvResponse;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetVipPvService;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 查询订单详情接口实现类.
 *
 * @author shenqb
 */
@Service
@Transactional
public class GetVipPvServiceImpl implements IGetVipPvService {

    private Logger logger = LoggerFactory.getLogger(GetVipPvServiceImpl.class);

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IDAppUtilService dAppUtilService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GetVipPvResponse getVipPv(GetVipPvRequest getVipPvRequest) throws DAppException {
        if (logger.isInfoEnabled()) {
            logger.info("dapp getVipPv begin:");
        }
        GetVipPvResponse getVipPvResponse = new GetVipPvResponse();
        // 校验会员是否为VIP
        Long memberId = memberMapper.checkVIP(getVipPvRequest.getVIPNumber());
        if (memberId == null) {
            if (logger.isErrorEnabled()) {
                logger.error("invalid VIPNumber");
            }
            throw new DAppException(IntegrationConstant.MEMBER_NOT_VIP, IntegrationConstant.ERROR_20001,
                    "VIPNumber = " + getVipPvRequest.getVIPNumber());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberId", memberId);
        if (null != getVipPvRequest.getDateFrom()) {
            // get方法传参会把参数的加号变成空号
            Date dateFrom = dAppUtilService.dateTimeStringToDate(getVipPvRequest.getDateFrom().replaceAll(" ", "+"));
            map.put("dateFrom", dateFrom);
        }
        if (null != getVipPvRequest.getDateTo()) {
            Date dateTo = dAppUtilService.dateTimeStringToDate(getVipPvRequest.getDateTo().replaceAll(" ", "+"));
            map.put("dateTo", dateTo);
        }

        BigDecimal pv = salesOrderMapper.getPvForVIP(map);

        getVipPvResponse.setVIPNumber(getVipPvRequest.getVIPNumber());
        getVipPvResponse.setPV(pv);
        return getVipPvResponse;
    }

}

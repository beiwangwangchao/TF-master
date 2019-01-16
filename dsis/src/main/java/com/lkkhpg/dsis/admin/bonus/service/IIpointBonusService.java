/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service;

import java.util.List;

import com.lkkhpg.dsis.common.bonus.dto.IpointBonus;
import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * ipoint奖金记录service.
 * 
 * @author wangc
 *
 */
public interface IIpointBonusService {

    /**
     * 查询ipoint奖金记录.
     * 
     * @param request
     *            请求上下文
     * @param ipointBonus
     *            奖金记录
     * @param pagesize
     *            页码
     * @param page
     *            每页记录数
     * @return 奖金记录列表
     */
    List<IpointBonus> queryIpointBonuses(IRequest request, IpointBonus ipointBonus, int page, int pagesize);

    /**
     * ipoint奖金记录提交.
     * 
     * @param createRequestContext
     *            请求上下文
     * @param ipointBonus
     *            奖金记录
     * @return 奖金记录列表
     * @throws CommBonusException
     *             奖金基础异常
     */
    List<IpointBonus> submitIpointBonuses(IRequest createRequestContext, List<IpointBonus> ipointBonus)
            throws CommBonusException;

    /**
     * ipoint奖金记录审核.
     * 
     * @param createRequestContext
     *            请求上下文
     * @param ipointBonus
     *            奖金记录
     * @return 奖金记录列表
     * @throws CommBonusException
     *             奖金基础异常
     */
    List<IpointBonus> approveIpointBonuses(IRequest createRequestContext, List<IpointBonus> ipointBonus)
            throws CommBonusException;

    /**
     * ipoint奖金记录拒绝.
     * 
     * @param createRequestContext
     *            请求上下文
     * @param ipointBonus
     *            奖金记录
     * @return 奖金记录列表
     * @throws CommBonusException
     *             奖金基础异常
     */
    List<IpointBonus> rejectIpointBonuses(IRequest createRequestContext, List<IpointBonus> ipointBonus)
                            throws CommBonusException;
}

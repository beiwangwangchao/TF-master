/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.Map;

import com.lkkhpg.dsis.admin.integration.dapp.dto.UpdateDistributorRequest;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 更新会员接口类.
 * 
 * @author linyuheng
 */
public interface IUpdateDistributorService extends ProxySelf<IUpdateDistributorService> {

    /**
     * 更新业务员(更新指定字段).
     * 
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @return Map map
     * @throws DAppException
     *             DApp异常
     * @throws CommMemberException
     *             会员统一异常
     */
    Map<String, Object> updateDistributor(UpdateDistributorRequest updateDistributorRequest)
            throws DAppException, CommMemberException;

    /**
     * 设置更新的会员信息.
     * 
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @param market
     *            市场
     * @throws DAppException
     *             dapp接口异常
     */
    void setMemberInfo(UpdateDistributorRequest updateDistributorRequest, Member member, SpmMarket market)
            throws DAppException;

    /**
     * 更新会员配偶信息.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @throws DAppException
     *             dapp接口异常
     */
    void updateMemberSpouseInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest, Member member)
            throws DAppException;

    /**
     * 更新会员受益人信息.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @throws DAppException
     *             dapp接口异常
     */
    void updateMemberBeneficiaryInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest, Member member)
            throws DAppException;

    /**
     * 更新会员账户信息.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @throws DAppException
     *             dapp接口异常
     */
    void updateMemberAccountInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest, Member member)
            throws DAppException;

    /**
     * 更新会员家庭地址信息.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @throws DAppException
     *             dapp接口异常
     */
    void updateMemberHomeSiteInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest, Member member)
            throws DAppException;

    /**
     * 更新会员联系地址信息.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @throws DAppException
     *             dapp接口异常
     */
    void updateMemberCtactSiteInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest, Member member)
            throws DAppException;

    /**
     * 新增配偶信息.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @return 相关人DTO
     * @throws DAppException
     *             dapp接口异常
     */
    MemRelationship addMemberSpouseInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest,
            Member member) throws DAppException;

    /**
     * 新增相关人信息.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @return 相关人DTO
     * @throws DAppException
     *             dapp接口异常
     */
    MemRelationship addMemberBeneficiaryInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest,
            Member member) throws DAppException;

    /**
     * 新增账户信息.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @param member
     *            会员DTO
     * @return 相关人DTO
     * @throws DAppException
     *             dapp接口异常
     */
    MemAccount addMemberAccountInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest, Member member)
            throws DAppException;

    // /**
    // * 校验地址.
    // *
    // * @param countryCode
    // * 国家代码
    // * @param stateCode
    // * 州省代码
    // * @param cityCode
    // * 城市代码
    // * @param siteUseCode
    // * 地址用途代码
    // * @throws DAppException
    // * dapp接口异常
    // */
    // void validateLocation(String countryCode, String stateCode, String
    // cityCode, String siteUseCode)
    // throws DAppException;
}

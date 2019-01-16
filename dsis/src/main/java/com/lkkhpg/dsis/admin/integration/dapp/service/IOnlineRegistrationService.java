/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OnlineRegistrationRequest;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemAttribute;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 线上入会接口类.
 * 
 * @author linyuheng
 */
public interface IOnlineRegistrationService extends ProxySelf<IOnlineRegistrationService> {

    /**
     * 新增会员.
     * 
     * @param onlineRegistrationRequest
     *            线上入会请求DTO
     * @return map
     * @throws CommMemberException
     *             会员统一异常
     * @throws DAppException
     *             DApp异常
     */
    Map<String, Object> addDistributor(OnlineRegistrationRequest onlineRegistrationRequest)
            throws CommMemberException, DAppException;

    /**
     * 设置个人住址信息.
     * 
     * @param onlineRegistrationRequest
     *            线上入会请求DTO
     * @param member
     *            会员DTO
     * @return memSite 个人住址地点
     * @throws DAppException
     *             dapp接口异常
     */
    MemSite setCtactSite(OnlineRegistrationRequest onlineRegistrationRequest, Member member) throws DAppException;

    /**
     * 设置联系住址信息.
     * 
     * @param onlineRegistrationRequest
     *            线上入会请求DTO
     * @param member
     *            会员DTO
     * @return memSite 联系住址地点
     * @throws DAppException
     *             dapp接口异常
     */
    MemSite setHomeSite(OnlineRegistrationRequest onlineRegistrationRequest, Member member) throws DAppException;

    /**
     * 设置账单地址信息.
     * 
     * @param onlineRegistrationRequest
     *            线上入会请求DTO
     * @param member
     *            会员DTO
     * @return memSite 账单地点
     */
    MemSite setBillSite(OnlineRegistrationRequest onlineRegistrationRequest, Member member);

    /**
     * 设置收货地址信息.
     * 
     * @param onlineRegistrationRequest
     *            线上入会请求DTO
     * @param member
     *            会员DTO
     * @return memSite 收货地点
     */
    MemSite setShipSite(OnlineRegistrationRequest onlineRegistrationRequest, Member member);

    /**
     * 设置配偶信息.
     * 
     * @param onlineRegistrationRequest
     *            线上入会请求DTO
     * @return memRelationship 配偶数据
     * @throws DAppException
     *             DApp异常
     */
    MemRelationship setSpouseInfo(OnlineRegistrationRequest onlineRegistrationRequest) throws DAppException;

    /**
     * 设置受益人信息.
     * 
     * @param onlineRegistrationRequest
     *            线上入会请求DTO
     * @return memRelationship 受益人数据
     * @throws DAppException
     *             DApp异常
     */
    MemRelationship setBenfInfo(OnlineRegistrationRequest onlineRegistrationRequest) throws DAppException;

    /**
     * 设置账户信息.
     * 
     * @param onlineRegistrationRequest
     *            线上入会请求DTO
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @return memAccount 账户数据
     * @throws DAppException
     *             Dapp接口异常
     */
    MemAccount setAccount(OnlineRegistrationRequest onlineRegistrationRequest, Member member, IRequest request)
            throws DAppException;

    /**
     * 设置属性.
     * 
     * @param memAttributes
     *            会员属性列表
     */
    void setAttributes(List<MemAttribute> memAttributes);

    /**
     * 校验地址.
     * 
     * @param countryCode
     *            国家代码
     * @param stateCode
     *            州省代码
     * @param cityCode
     *            城市代码
     * @param siteUseCode
     *            地点用途代码
     * @throws DAppException
     *             dapp接口异常
     */
    void validateLocation(String countryCode, String stateCode, String cityCode, String siteUseCode)
            throws DAppException;
}

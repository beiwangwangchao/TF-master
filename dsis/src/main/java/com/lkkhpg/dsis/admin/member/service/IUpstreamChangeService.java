/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.common.member.dto.MemUpstreamChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员上线变更接口类.
 * 
 * @author linyuheng
 */
public interface IUpstreamChangeService extends ProxySelf<IUpstreamChangeService> {
    /**
     * 查询变更上线记录.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            会员上线变更DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 会员变更上线记录列表
     */
    List<MemUpstreamChange> queryUpstreamChange(IRequest request, MemUpstreamChange upstreamChange, int page,
            int pagesize);

    /**
     * 查询原上线(手动输入).
     * 
     * @param request
     *            请求上下文
     * @param memUpstreamChange
     *            会员变更上线DTO
     * @return 会员变更上线记录列表
     * @throws MemberException
     *             会员异常
     */
    List<MemUpstreamChange> queryOldUpstream(IRequest request, MemUpstreamChange memUpstreamChange)
            throws MemberException;

    /**
     * 提交变更上线信息.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            会员上线变更DTO
     * @return 会员ID
     * @throws MemberException
     *             会员统一异常
     * @throws IntegrationException
     *             接口统一异常
     */
    String submitUpstreamChange(IRequest request, @StdWho MemUpstreamChange upstreamChange)
            throws MemberException, IntegrationException;

    /**
     * 检查新上线有效性.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            变更上线DTO
     * @throws MemberException
     *             会员统一异常
     * @throws IntegrationException
     *             接口统一异常
     */
    void checkNewSponsorValidity(IRequest request, MemUpstreamChange upstreamChange)
            throws MemberException, IntegrationException;

    /**
     * 检查申请变更上线的会员有效性.
     * 
     * @param request
     *            请求上下文
     * @param memUpstreamChange
     *            变更上线DTO
     * @param member
     *            会员DTO
     * @param memUpstreamChanges
     *            会员变更上线DTO集合
     * @return 会员变更上线DTO集合
     * @throws MemberException
     */
    List<MemUpstreamChange> checkValidity(IRequest request, MemUpstreamChange memUpstreamChange, Member member,
            List<MemUpstreamChange> memUpstreamChanges) throws MemberException;

    /**
     * 手动同步至GDS.
     * 
     * @param request
     *            请求上下文
     * @param memUpstreamChanges
     *            待同步的变更上线列表
     * @throws IntegrationException
     *             接口异常
     */
    void synToGds(IRequest request, @StdWho List<MemUpstreamChange> memUpstreamChanges) throws IntegrationException;

    /**
     * 会员移线申请.
     * 
     * @param request
     *            请求上下文
     * @param memberCode
     *            会员卡号
     * @param upstreamChange
     *            变更上线申请信息
     * @throws IntegrationException
     *             接口统一异常
     */
    void applyMoveLine(IRequest request, String memberCode, MemUpstreamChange upstreamChange)
            throws IntegrationException;
}

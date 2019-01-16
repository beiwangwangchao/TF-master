/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.platform.annotation.AuditEntry;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员地址service.
 * 
 * @author guanghui.liu
 */
public interface IMemberSiteService extends ProxySelf<IMemberSiteService> {

    /**
     * 查询会员地址.
     * 
     * @param request
     *            请求上下文
     * @param memSite
     *            可通过memberId和siteUseCode查询
     * @return 会员地址列表
     */
    List<MemSite> queryMemSites(IRequest request, MemSite memSite);

    /**
     * 更新地址默认标记.
     * 
     * @param request
     *            请求上下文
     * @param memSite
     *            包含siteId,siteUseCode,memberId
     * @return 更新的条数
     */
    @AuditEntry("MEMBER")
    int updateDefaultFlag(IRequest request, @StdWho MemSite memSite);

    /**
     * 保存（创建/更新）会员地点.
     * 
     * @param request
     *            请求上下文
     * @param memSite
     *            会员地点DTO
     * @return 会员地点DTO
     */
    @AuditEntry("MEMBER")
    MemSite saveMemSite(IRequest request, @StdWho MemSite memSite);
    
    MemSite saveCtactMemSite(IRequest request, @StdWho MemSite memSite);

    /**
     * 删除会员地点.
     * 
     * @param request
     *            请求上下文
     * @param memSiteId
     *            会员地点Id
     * @return 返回删除地址后新的默认地址siteId
     * @throws MemberException
     *             如果只有一条地址时抛出删除异常
     */
    @AuditEntry("MEMBER")
    Long deleteMemSite(IRequest request, Long memSiteId) throws MemberException;
}
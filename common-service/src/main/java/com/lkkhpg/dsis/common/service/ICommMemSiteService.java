/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员地点Service接口.
 * 
 * @author frank.li
 */
public interface ICommMemSiteService extends ProxySelf<ICommMemSiteService> {

    /**
     * 保存（创建/更新）会员地点.
     * 
     * @param request
     *            请求上下文
     * @param memSite
     *            会员地点DTO
     * @return 会员地点DTO
     */
    MemSite saveMemSite(IRequest request, @StdWho MemSite memSite);

    /**
     * 删除会员地点.
     * 
     * @param request
     *            请求上下文
     * @param site
     *            会员地点Id
     * @return 执行结果boolean
     */
    boolean deleteMemSite(IRequest request, MemSite site);
    
    /**
     * 查询会员地址.
     * 
     * @param request 
     *            请求上下文 
     * @param memSite 
     *            会员地址
     * @return 会员地址列表
     */
    List<MemSite> queryMemSite(IRequest request, MemSite memSite);
    
    /**
     * 更新SPM地点.
     * @param spmLocation 地点DTO
     * @return 执行结果boolean
     */
    boolean updateSpmLocation(@StdWho SpmLocation spmLocation);
}

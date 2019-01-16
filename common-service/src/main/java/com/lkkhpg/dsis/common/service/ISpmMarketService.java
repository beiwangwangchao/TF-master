/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.annotation.AuditEntry;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 地址Service接口.
 * 
 * @author frank.li
 */
public interface ISpmMarketService extends ProxySelf<ISpmMarketService> {

    /**
     * 保存市场.
     * 
     * @param request
     *            请求上下文
     * @param markets
     *            市场List
     * @return 市场List
     * @throws CommSystemProfileException
     *             基础设置异常
     */
    @AuditEntry("MARKET")
    List<SpmMarket> saveMarket(IRequest request, @StdWho List<SpmMarket> markets) throws CommSystemProfileException;

    /**
     * 查询公司的companyid,用来作为启用信用支付的公司
     * @param marketId
     * @return
     */
    List<SpmMarket>selectCompanyId(Long marketId);

    /**
     * 删除市场.
     * 
     * @param request
     *            请求上下文
     * @param markets
     *            市场List
     * @return boolean
     */
    @AuditEntry("MARKET")
    boolean deleteMarket(IRequest request, List<SpmMarket> markets);

    /**
     * 查询市场.
     * 
     * @param request
     *            请求上下文
     * @param market
     *            市场DTO
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return 市场List
     */
    List<SpmMarket> queryMarket(IRequest request, SpmMarket market, int page, int pagesize);


    List<SpmMarket> queryMarket(IRequest request, SpmMarket market);
    /**
     * 查询市场.
     *  数据屏蔽  只显示和该市场所在公司有关的市场
     * @param request
     *            请求上下文
     * @param market
     *            市场DTO
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return 市场List
     */
    List<SpmMarket> queryMarket2(IRequest request, SpmMarket market, int page, int pagesize);

    /**
     * 通过 marketId 查找市场.
     * 
     * @param request
     *            请求上下文
     * @param marketId
     *            市场主键
     * @return 市场dto.
     */
    SpmMarket queryByMarketId(IRequest request, Long marketId);

    /**
     * 根据销售组织Id获取公司和市场.
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售组织Id
     * @return 公司和市场
     */
    Map<String, Object> getCompAndMarket(IRequest request, Long salesOrgId);

    /**
     * 查询市场数量顺带查询消息数量.
     * 
     * @return map 数量map
     */
    Map<String, Object> queryMarketsAndSmsQuanties(IRequest request);

    /**
     * 获取用户可访问的市场.
     * 
     * @return map 数量map
     */
    List<SpmMarket> queryMarketsByRole(IRequest request);
}

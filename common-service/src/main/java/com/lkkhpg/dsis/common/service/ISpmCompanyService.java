/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 公司查询接口.
 * 
 * @author liang.rao
 *
 */
public interface ISpmCompanyService extends ProxySelf<ISpmCompanyService> {


    /**
     * 根据市场的ID去查询商户号
     * @param marketId
     * @return
     */
   SpmCompany selectPartner(Long marketId);

    
    /**
     * 查询公司信息.
     * 
     * @param request
     *            请求上下文.
     * @param company
     *            公司信息.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @return 公司信息集合.
     */
    List<SpmCompany> queryCompany(IRequest request, SpmCompany company, int page, int pagesize);

    /**
     * 查询公司信息.
     *
     * 数据屏蔽  只能显示当前用户所在市场下所在的公司及其子公司或者母公司
     * @param request
     *            请求上下文.
     * @param company
     *            公司信息.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @return 公司信息集合.
     */
    List<SpmCompany> queryCompany2(IRequest request, SpmCompany company, int page, int pagesize);
    
    /**
     * 根据id查询公司信息.
     * 
     * @param request
     *            请求上下文.
     * @param company
     *            公司信息.
     * @return 公司信息集合.
     */
    List<SpmCompany> queryCompanyById(IRequest request, SpmCompany company);
    
    /**
     * 保存公司信息.
     * 
     * @param request 
     *            请求上下文.
     * @param company
     *            公司信息.
     * @return 公司信息.
     * @throws CommSystemProfileException 系统配置统一异常.
     */
    SpmCompany saveCompany(IRequest request, @StdWho SpmCompany company) throws CommSystemProfileException;
    
   List<SpmCompany>queyBrNo(SpmCompany company);
    
}


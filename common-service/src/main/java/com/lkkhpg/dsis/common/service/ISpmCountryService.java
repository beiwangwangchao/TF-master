/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmCountry;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 国家Service接口.
 * 
 * @author shenqb
 */
public interface ISpmCountryService extends ProxySelf<ISpmCountryService> {

    /**
     * 保存国家详情.
     * 
     * @param request
     *            请求上下文
     * @param countrys
     *            国s
     * @return 国家
     * @throws CommSystemProfileException
     *             基础设置异常
     */
    List<SpmCountry> saveCountryDetail(IRequest request, @StdWho List<SpmCountry> countrys)
            throws CommSystemProfileException;

    /**
     * 删除国家.
     * 
     * @param request
     *            请求上下文
     * @param countries
     *            国家List
     * @return boolean
     */
    boolean deleteCountry(IRequest request, List<SpmCountry> countries);

    /**
     * 查询国家.
     * 
     * @param request
     *            请求上下文
     * @param country
     *            国家DTO
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return 国家List
     */
    List<SpmCountry> queryCountry(IRequest request, SpmCountry country, int page, int pagesize);

    /**
     * 
     * @param request
     *            请求上下文
     * @param countryCode
     *            国家代码
     * @return 国家
     */
    SpmCountry queryCountryDetail(IRequest request, String countryCode); 
    
    /**
     * 查询国家-地址编辑器专用.
     * 
     * @param request
     *            请求上下文
     * @param country
     *            国家DTO
     * @return 国家List
     */
    List<SpmCountry> queryCountryForLocation(IRequest request, SpmCountry country);
}

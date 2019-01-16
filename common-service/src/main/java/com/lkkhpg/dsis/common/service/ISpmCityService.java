/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmCity;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 城市Service接口.
 * @author shenqb
 */
public interface ISpmCityService extends ProxySelf<ISpmCityService> {

    /**
     * 保存城市.
     * @param request
     *            请求上下文
     * @param cities
     *            城市List
     * @return 城市List
     * @throws CommSystemProfileException
     */
    List<SpmCity> saveCity(IRequest request, @StdWho List<SpmCity> cities) throws CommSystemProfileException;

    /**
     * 删除城市.
     * @param request
     *            请求上下文
     * @param cities
     *            城市List
     * @return boolean
     */
    boolean deleteCity(IRequest request, @StdWho List<SpmCity> cities);

    /**
     * 查询城市.
     * @param request
     *            请求上下文
     * @param city
     *            城市DTO
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return 城市List
     */
    List<SpmCity> queryCity(IRequest request, SpmCity city, int page, int pagesize);

    /**
     * 查询州省为空的城市.
     * @param request
     * @param city
     * @param page
     * @param pagesize
     * @return 城市List
     */
    List<SpmCity> queryNullCity(IRequest request, SpmCity city, int page, int pagesize);

    /**
     * 查询城市-地址编辑器专用.
     * @param request
     *            请求上下文
     * @param city
     *            城市DTO
     * @return 城市List
     */
    List<SpmCity> queryCityForLocation(IRequest request, SpmCity city);

    /**
     * 根据城市编码获取城市.
     * @param request 请求上下文
     * @param cityCode 城市编码
     * @return 城市DTO
     */
    SpmCity getCity(IRequest request, String cityCode);
}

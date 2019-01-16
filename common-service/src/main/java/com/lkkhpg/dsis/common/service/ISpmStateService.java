/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmState;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 省/州Service接口.
 * 
 * @author huangjiajing
 */
public interface ISpmStateService extends ProxySelf<ISpmStateService> {

    /**
     * 保存省/州.
     * 
     * @param request
     *            请求上下文
     * @param states
     *            省/州List
     * @return 省/州List
     * @throws CommSystemProfileException 
     */
    List<SpmState> saveState(IRequest request, @StdWho List<SpmState> states) throws CommSystemProfileException;


    /**
     * 查询省/州.
     * 
     * @param request
     *            请求上下文
     * @param state
     *            省/州DTO
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return 省/州List
     */
    List<SpmState> queryState(IRequest request, SpmState state, int page, int pagesize);
    
    /**
     * 查询省/州.
     * 
     * @param request
     *            请求上下文
     * @param state
     *            省/州DTO
     * @param page
     *            页
     * @param pageSize
     *            页数
     * @return 省/州List
     */
    List<SpmState> queryStateNoCountry(IRequest request, SpmState state, int page, int pageSize);
    
    /**
     * 删除州省.
     * 
     * @param request
     *            请求上下文
     * @param states
     *            州省List
     * @return boolean
     */
    boolean deleteState(IRequest request, @StdWho List<SpmState> states);
    
    /**
     * 查询省/州-地址编辑器专用.
     * 
     * @param request
     *            请求上下文
     * @param state
     *            省/州DTO
     * @return 省/州List
     */
    List<SpmState> queryStateForLocation(IRequest request, SpmState state);
}

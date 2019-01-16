/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.lkkhpg.dsis.integration.recorder.dto.InterfaceRecord;
import com.lkkhpg.dsis.integration.recorder.dto.ListenerRecord;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 接口集成日志接口类.
 * 
 * @author frank.li
 */
public interface IIsgLogService {

    /**
     * 获取接口交互行日志.
     * 
     * @param request
     *            请求上下文
     * @param params
     *            查询参数
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return interfaceRecords 日志列表
     */
    List<InterfaceRecord> getInterfaceRecordLog(IRequest request, Map<String, Object> params, int page, int pagesize);

    List<ListenerRecord> getInterfaceListenerLog(IRequest request, ListenerRecord params, int page, int pagesize);

    /**
     * 接口表数据查询.
     * 
     * @param request
     *            请求上下文
     * @param mapper
     *            MAPPER
     * @param params
     *            参数
     * @param page
     * @param pagesize
     * @return list 数据列表
     */
    List<? extends BaseDTO> queryInterfaceDatas(IRequest request, String mapper,
                                                @RequestParam Map<String, Object> params, int page, int pagesize);

}

/*
 *
 */
package com.lkkhpg.dsis.platform.report.service;

import java.util.Map;

import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 报表数据Service.
 * @author chenjingxiong
 */
public interface IReportDataService {
    
    /**
     * 传入参数，输出报表数据.
     * @param paramMap 输入参数.
     * @return 报表数据.
     */
    Map<String, Object> process(IRequest request, Map<String, Object> paramMap);

}

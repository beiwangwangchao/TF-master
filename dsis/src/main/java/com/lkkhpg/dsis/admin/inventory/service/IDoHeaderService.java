/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.lading.dto.DoHeader;
import com.lkkhpg.dsis.common.inventory.lading.dto.DoLine;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
/**
 * 提货单service.
 * @author liuxuan
 *
 */
public interface IDoHeaderService {
    
    /**
     * 查询提货单头.
     * @param request 请求上下文
     * @param doHeader 提货单表dto
     * @param page 页数
     * @param pagesize 每页显示数
     * @return 提货单集合
     */
    List<DoHeader> queryDoHeader(IRequest request, DoHeader doHeader, int page, int pagesize);
    
    /**
     * 保存提货单.
     * @param request 请求上下文
     * @param doHeaders 提货单集合
     * @return 已保存提货单
     */
    List<DoHeader> saveDoHeader(IRequest request, @StdWho List<DoHeader> doHeaders);
    
    /**
     * 查询提货单行.
     * @param request 请求上下文
     * @param doLine 
     * @param page 页数
     * @param pagesize 每页显示数
     * @return 提货单行表
     */
    List<DoLine> queryDoLine(IRequest request, DoLine doLine);
    
}

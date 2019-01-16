/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import java.util.List;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.common.config.dto.SpmInvNumbering;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 发票编号规则Service.
 * @author chenjingxiong
 */
public interface ISpmInvNumberingService {

    
    /**
     * 查询发票规则.
     * @param request 统一上下文.
     * @param record 查询条件.
     * @param page 页数.
     * @param pageSize 每页记录数.
     * @return 响应数据
     */
    List<SpmInvNumbering> querySpmInvNumberings(IRequest request, SpmInvNumbering record, int page, int pageSize);
    
    /**
     * 保存发票规则.
     * @param request 统一上下文.
     * @param spmInvNumberings 发票规则.
     * @return 保存后的发票规则.
     * @throws SystemProfileException 系统配置统一异常
     */
    List<SpmInvNumbering> saveSpmInvNumberings(IRequest request, @StdWho List<SpmInvNumbering> spmInvNumberings)
            throws SystemProfileException;
    
    /**
     * 删除选中.
     * @param request 统一上下文
     * @param spmInvNumberings 发票规则.
     */
    void deleteByRuleId(IRequest request, List<SpmInvNumbering> spmInvNumberings);
    /**
     * 监控空白字轨是否上传.
     *
     * @param marketCode
     *            市场
     * @throws Exception
     */
    void monitorBlankTrackcode(String marketCode) throws Exception;

    /**
     * 监控空白字轨数量.
     *
     * @param marketCode
     *            市场
     * @throws Exception
     */
    void monitorAvailableBlankTrackcode(String marketCode) throws Exception;
}

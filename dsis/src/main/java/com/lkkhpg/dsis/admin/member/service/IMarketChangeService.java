/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.system.DsisServiceRequest;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员市场变更接口类.
 * 
 * @author linyuheng
 */
public interface IMarketChangeService extends ProxySelf<IMarketChangeService> {

    /**
     * 查询市场变更记录.
     * 
     * @param iRequest
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 会员变更市场记录列表
     */
    List<MemMarketChange> queryMarketChange(IRequest iRequest, MemMarketChange memMarketChange, int page, int pagesize);

    /**
     * 查询市场变更记录(审核中).
     * 
     * @param iRequest
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 会员变更市场记录列表
     */
    List<MemMarketChange> queryApprovingMarketChange(IRequest iRequest, MemMarketChange memMarketChange, int page,
            int pagesize);

    /**
     * 批准市场变更.
     * 
     * @param iRequest
     *            请求上下文
     * @param request
     *            原生请求上下文
     * @param marketChange
     *            会员变更市场DTO
     * @return 更新条数
     * @throws Exception
     */
    boolean approveMarketChange(IRequest iRequest, HttpServletRequest request, MemMarketChange marketChange)
            throws Exception;

    /**
     * 查询原市场（手动输入）.
     * 
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @return 会员变更市场DTO集合
     */
    List<MemMarketChange> queryOldMarket(IRequest request, MemMarketChange memMarketChange);

    /**
     * 提交变更市场信息.
     * 
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @return 变更市场记录编号
     * @throws IntegrationException
     *             接口统一异常
     */
    String submitUpstreamChange(IRequest request, MemMarketChange memMarketChange) throws IntegrationException;

    /**
     * 获取其他市场转入本市场（目标市场）申请的待审批列表.
     * 
     * @param request
     *            请求上下文
     * @param subOrg
     *            源销售机构（源市场）代号
     * @param orgCode
     *            市场编码
     * @return 会员变更市场DTO集合
     * @throws Exception
     * 
     */
    List<MemMarketChange> queryNewMarketChangeFromGds(IRequest request, String subOrg, String orgCode) throws Exception;

    /**
     * 删除变更市场记录.
     * 
     * @param iRequest
     *            请求上下文
     * @param memMarketChanges
     *            待删除的变更市场列表
     * @throws Exception
     */
    void deleteMarketChange(IRequest iRequest, List<MemMarketChange> memMarketChanges) throws Exception;
    
    /**
     * 给市场变更审核人发送站内信.
     * 
     * @param request
     *            统一上下文
     * @param salesOrgId
     *            销售组织Id
     * @param toMarketId 目标市场ID
     * @return true-发送成功；false-发送失败
     * @throws Exception 异常
     */
    boolean sendMessage(DsisServiceRequest request, Long salesOrgId, Long toMarketId) throws Exception;

}

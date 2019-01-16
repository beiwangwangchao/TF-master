/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.member.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.common.member.dto.MemStatusChange;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员状态变更Service接口.
 * 
 * @author yuchuan.zeng@hand-china.com
 */
public interface IMemStatusChangeService extends ProxySelf<IMemStatusChangeService> {

    /**
     * 会员状态变更记录查询（根据memberId和申请日期查询）.
     * 
     * @param request
     *            请求上下文
     * @param memStatusChange
     *            会员状态变更dto
     * @param page
     *            第几页
     * @param pagesize
     *            每页几条
     * @return 会员状态变更dto集合
     */
    List<MemStatusChange> queryApplyDateAndMemberId(IRequest request, MemStatusChange memStatusChange, int page,
            int pagesize);

    /**
     * 批量保存MemStatusChange.
     * 
     * @param request
     *            请求上下文
     * @param memStatusChanges
     *            会员状态变更dto集合
     * @return 会员状态变更dto集合
     * @throws MemberException
     *             会员异常
     * @throws IntegrationException
     *             接口异常
     */
    List<MemStatusChange> saveMemStatusChanges(IRequest request, List<MemStatusChange> memStatusChanges)
            throws MemberException, IntegrationException;

    /**
     * 保存MemStatusChange.
     * 
     * @param request
     *            请求上下文
     * @param memStatusChange
     *            会员状态变更dto
     * @return MemStatusChange 会员状态变更dto
     * @throws MemberException
     *             会员异常
     * @throws IntegrationException
     *             接口异常
     */
    MemStatusChange saveMemStatusChange(IRequest request, @StdWho MemStatusChange memStatusChange)
            throws MemberException, IntegrationException;

    /**
     * 保存MemStatusChange.
     * 
     * @param request
     *            请求上下文
     * 
     * @param memStatusChange
     *            会员状态变更dto
     * @return boolean
     */
    boolean saveMemStatusChangeForJob(IRequest request, MemStatusChange memStatusChange);

    /**
     * 手动同步至GDS.
     * 
     * @param iRequest
     *            请求上下文
     * @param memStatusChange
     *            待同步的变更状态列表
     * @throws IntegrationException
     *             接口异常
     */
    void synToGds(IRequest iRequest, @StdWho List<MemStatusChange> memStatusChange) throws IntegrationException;

    /**
     * 判断会员是否有一条状态为“审核中”的终止或者自动终止记录.
     * 
     * @param memStatusChange
     *            会员状态变更DTO
     * @param request
     *            请求上下文
     * @return 是否有数据
     * @throws MemberException 
     */
    boolean validRecord(IRequest request, MemStatusChange memStatusChange) throws MemberException;
}

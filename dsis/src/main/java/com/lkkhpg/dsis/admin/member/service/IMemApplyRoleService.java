/*
 *
 */
package com.lkkhpg.dsis.admin.member.service;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.MemApplyRole;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员申请角色变更接口类.
 * 
 * @author linyuheng
 */
public interface IMemApplyRoleService extends ProxySelf<IMemApplyRoleService> {

    /**
     * 获取申请号.
     * 
     * @param request
     *            请求上下文
     * @return 申请流水号
     */
    String getApplyNumber(IRequest request);

    /**
     * 查询变更角色记录.
     * 
     * @param request
     *            请求上下文
     * @param memApplyRole
     *            变更角色DTO
     * @return 变更角色记录
     */
    List<MemApplyRole> queryRecords(IRequest request, MemApplyRole memApplyRole);

    /**
     * 插入申请记录.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     */
    void insertRecord(IRequest request, Member member);

    /**
     * 更新审核中的申请记录.
     * 
     * @param request
     *            请求上下文
     */
    void updatePendingRecord(IRequest request);

}

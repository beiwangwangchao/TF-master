/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.member.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员审计Service.
 * 
 * @author shengyang.zhou@hand-china.com
 * @author xiawang.liu
 */
public interface IMemberAuditService extends ProxySelf<IMemberAuditService> {
    
    /**
     * 会员审计查询方法.
     * 
     * @param iRequest
     *            请求上下文
     * @param param
     *            Map<String, Object>
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 返回数据
     */
    List<Map<String, Object>> selectAuditHistory(IRequest iRequest, Map<String, Object> param, int pageNum,
                                                 int pageSize);
    
    /**
     * 会员相关人审计查询方法.
     * 
     * @param iRequest
     *            请求上下文
     * @param param
     *            Map<String, Object>
     * @return 返回数据
     */
    List<Map<String, Object>> selectRelationshipAuditHistory(IRequest iRequest, Map<String, Object> param);
    
    /**
     * 会员信用卡审计查询方法.
     * 
     * @param iRequest
     *            请求上下文
     * @param param
     *            Map<String, Object>
     * @return 返回数据
     */
    List<Map<String, Object>> selectCardAuditHistory(IRequest iRequest, Map<String, Object> param);
    
    /**
     * 会员地址审计查询方法.
     * 
     * @param iRequest
     *            请求上下文
     * @param param
     *            Map<String, Object>
     * @return 返回数据
     */
    List<Map<String, Object>> selectSiteAuditHistory(IRequest iRequest, Map<String, Object> param);
    
}

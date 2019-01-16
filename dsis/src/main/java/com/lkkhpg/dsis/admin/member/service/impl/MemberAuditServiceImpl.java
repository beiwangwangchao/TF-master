/*
 *
 */

package com.lkkhpg.dsis.admin.member.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.member.mapper.MemberAuditMapper;
import com.lkkhpg.dsis.admin.member.service.IMemberAuditService;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.audit.util.AuditUtils;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class MemberAuditServiceImpl implements IMemberAuditService {

	public static final String MEM_REL_ID = "MEM_REL_ID";
	
	public static final String CARD_ID = "CARD_ID";
	
	public static final String SITE_ID = "SITE_ID";
	
    @Autowired
    private MemberAuditMapper auditMapper;

    @Override
    public List<Map<String, Object>> selectAuditHistory(IRequest iRequest, Map<String, Object> param, int pageNum,
            int pageSize) {
        PageHelper.offsetPage((pageNum - 1) * pageSize, pageSize + 1); // 多查一行,这样最后一行也会显示差异结果
        List<Map<String, Object>> result = auditMapper.selectAuditHistory(param);
        List<Map<String, Object>> result2 = AuditUtils.transform(result, Member.class);
        if (result2.size() == pageSize + 1) {
            // 多查的一行不返回
            result2.remove(result2.size() - 1);
        }
        Page page = new Page(pageNum, pageSize);
        page.addAll(result2);
        page.setTotal(((Page) result).getTotal());
        return page;
    }

	@Override
	public List<Map<String, Object>> selectRelationshipAuditHistory(IRequest iRequest, Map<String, Object> param) {
        List<Map<String, Object>> resultPrev = auditMapper.selectRelationshipAuditHistoryPrev(param);
        List<Map<String, Object>> resultThis = auditMapper.selectRelationshipAuditHistoryThis(param);
        List<Map<String, Object>> result2 = AuditUtils.transform1(resultPrev, resultThis, MemRelationship.class, MEM_REL_ID);
        return result2;
	}

	@Override
	public List<Map<String, Object>> selectCardAuditHistory(IRequest iRequest, Map<String, Object> param) {
		List<Map<String, Object>> resultPrev = auditMapper.selectCardAuditHistoryPrev(param);
        List<Map<String, Object>> resultThis = auditMapper.selectCardAuditHistoryThis(param);
        List<Map<String, Object>> result2 = AuditUtils.transform1(resultPrev, resultThis, MemCard.class, CARD_ID);
		return result2;
	}

	@Override
	public List<Map<String, Object>> selectSiteAuditHistory(IRequest iRequest, Map<String, Object> param) {
		List<Map<String, Object>> resultPrev = auditMapper.selectSiteAuditHistoryPrev(param);
        List<Map<String, Object>> resultThis = auditMapper.selectSiteAuditHistoryThis(param);
        List<Map<String, Object>> result2 = AuditUtils.transform1(resultPrev, resultThis, MemSite.class, SITE_ID);
		return result2;
	}

}

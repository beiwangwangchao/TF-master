/*
 *
 */

package com.lkkhpg.dsis.admin.member.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface MemberAuditMapper {

    List<Map<String, Object>> selectAuditHistory(Map<String, Object> param);
    
    /*相关人*/
    List<Map<String, Object>> selectRelationshipAuditHistoryPrev(Map<String, Object> param);
    List<Map<String, Object>> selectRelationshipAuditHistoryThis(Map<String, Object> param);
    
    /*信用卡*/
    List<Map<String, Object>> selectCardAuditHistoryPrev(Map<String, Object> param);
    List<Map<String, Object>> selectCardAuditHistoryThis(Map<String, Object> param);
    
    /*地址*/
    List<Map<String, Object>> selectSiteAuditHistoryPrev(Map<String, Object> param);
    List<Map<String, Object>> selectSiteAuditHistoryThis(Map<String, Object> param);
    
}

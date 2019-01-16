/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.system.dto.AttachmentFile;
import com.lkkhpg.dsis.platform.dto.attachment.AttachCategory;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 */
public interface AttachmentsMapper {

    /**
     * 查询MWS相关市场目录信息.
     * @param marketCode
     * @return List
     */
    List<AttachCategory> selectMWSAttachCategory(String marketCode);
    
    /**
     * 查询MWS相关市场附件信息.
     * 
     * @param file
     * @return List
     */
    List<AttachmentFile> selectAttachmentFiles(AttachmentFile file);
    
    List<AttachmentFile> queryCategory(Map<String, Object> map);
}

/*
 *
 */
package com.lkkhpg.dsis.platform.service.attachment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.attachment.Attachment;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;
import com.lkkhpg.dsis.platform.mapper.attachment.AttachmentMapper;
import com.lkkhpg.dsis.platform.mapper.attachment.SysFileMapper;
import com.lkkhpg.dsis.platform.service.attachment.IAttachmentService;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;

/**
 * 附件service.
 * 
 * @author hua.xiao@hand-china.com
 */
@Service
@Transactional
public class AttachmentServiceImpl implements IAttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;
    
    @Autowired
    private SysFileMapper sysFileMapper;
    
    @Autowired
    private ISysFileService sysFileService;
    
    

    @Override
    public Attachment insert(IRequest requestContext, @StdWho Attachment attach) {
        attachmentMapper.insert(attach);
        return attach;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Attachment selectAttachByCodeAndKey(IRequest requestContext, String sourceType, String sourceKey) {
        Attachment attachment = new Attachment();
        attachment.setSourceKey(sourceKey);
        attachment.setSourceType(sourceType);
        return attachmentMapper.selectAttachment(attachment);
    }

    @Override
    public Attachment deleteAttachment(IRequest requestContext, Attachment attach) {
        
        SysFile file = new SysFile();
        file.setAttachmentId(attach.getAttachmentId());
        List<SysFile> files = sysFileMapper.selectSysFiles(file);
        for (SysFile f : files) {
            sysFileService.delete(requestContext, f);
        }
        attachmentMapper.delete(attach);
        return attach;
    }

}

/*
 *
 */
package com.lkkhpg.dsis.platform.service.attachment.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.attachment.Attachment;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;
import com.lkkhpg.dsis.platform.exception.UniqueFileMutiException;
import com.lkkhpg.dsis.platform.mapper.attachment.AttachmentMapper;
import com.lkkhpg.dsis.platform.mapper.attachment.SysFileMapper;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;
import com.lkkhpg.dsis.platform.util.UploadUtil;

/**
 * @author hua.xiao@hand-china.com
 */
@Service
@Transactional
public class SysFileServiceImpl implements ISysFileService {

    /**
     * 不使用分页情况.
     */
    private static final int NO_PAGE = -1;
    /**
     * 第一页.
     */
    private static final int PAGE_NO = 1;

    /**
     * 图片分辨率-高.
     */
    public static final int H_WIDTH = 480;
    public static final int H_HEIGHT = 480;

    /**
     * 图片分辨率-中.
     */
    public static final int M_WIDTH = 360;
    public static final int M_HEIGHT = 360;

    /**
     * 图片分辨率-低.
     */
    public static final int L_WIDTH = 78;
    public static final int L_HEIGHT = 78;

    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysFile insert(IRequest requestContext, @StdWho SysFile file) {
        sysFileMapper.insert(file);
        return file;
    }

    @Override
    public SysFile delete(IRequest requestContext, SysFile file) {
        SysFile sysFile = sysFileMapper.selectByPrimaryKey(file.getFileId());
        if (sysFile != null) {
            sysFileMapper.delete(file);
        } else {
            sysFile = file;
        }
        UploadUtil.deleteFile(sysFile.getFilePath());
        return sysFile;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SysFile> selectFiles(IRequest requestContext, SysFile file, int page, int pagesize) {
        if (pagesize > NO_PAGE) {
            PageHelper.offsetPage(page, pagesize);
        }
        return sysFileMapper.selectSysFiles(file);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysFile insertFileAndAttach(IRequest requestContext, Attachment attach, @StdWho SysFile file) {
        Attachment params = new Attachment();
        params.setSourceType(attach.getSourceType());
        params.setSourceKey(attach.getSourceKey());
        params = attachmentMapper.selectAttachment(params);
        if (params == null) {
            attach.setStartActiveDate(new Date());
            attach.setEndActiveDate(new Date());
            attachmentMapper.insert(attach);
            params = attach;
        }
        file.setAttachmentId(params.getAttachmentId());
        self().insert(requestContext, file);
        return file;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public SysFile selectByPrimaryKey(IRequest requestContext, Long fileId) {
        return sysFileMapper.selectByPrimaryKey(fileId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SysFile> selectFilesByTypeAndKey(IRequest requestContext, String sourceType, String sourceKey) {

        if (StringUtils.isBlank(sourceType) || sourceKey == null) {
            return null;
        }
        Attachment attachment = new Attachment();
        attachment.setSourceKey(sourceKey);
        attachment.setSourceType(sourceType);
        Attachment attach = attachmentMapper.selectAttachment(attachment);
        if (attach != null) {
            SysFile file = new SysFile();
            file.setAttachmentId(attach.getAttachmentId());
            return self().selectFiles(requestContext, file, PAGE_NO, NO_PAGE);
        } else {
            return null;
        }
    }

    @Override
    public void removeFiles(IRequest requestContext, List<SysFile> sysFiles) {
        for (SysFile file : sysFiles) {
            self().delete(requestContext, file);
        }
    }

    @Override
    public List<SysFile> selectFilesByCategoryId(IRequest requestContext, SysFile file, Long categoryId, int page,
            int pagesize) {
        Attachment params = new Attachment(categoryId);
        List<Attachment> attachments = attachmentMapper.selectAttachments(params);
        if (categoryId == null || attachments == null || attachments.size() <= 0) {
            return new LinkedList<SysFile>();
        }

        List<Long> attachmentIds = new ArrayList<Long>();

        for (Attachment a : attachments) {
            attachmentIds.add(a.getAttachmentId());
        }

        return sysFileMapper.selectFilesByAttachIds(attachmentIds, file);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysFile updateOrInsertFile(IRequest requestContext, @StdWho Attachment attach, @StdWho SysFile file)
            throws UniqueFileMutiException {
        Attachment params = new Attachment();
        String path = null;
        params.setSourceType(attach.getSourceType());
        params.setSourceKey(attach.getSourceKey());
        params = attachmentMapper.selectAttachment(params);
        // 第一次上传
        if (params == null) {
            return self().insertFileAndAttach(requestContext, attach, file);
        } else {
            // 第二次上传,更新SysFile
            SysFile sysParams = new SysFile();
            sysParams.setAttachmentId(params.getAttachmentId());
            List<SysFile> sysFiles = sysFileMapper.selectSysFiles(sysParams);
            if (sysFiles.isEmpty()) {
                file.setAttachmentId(params.getAttachmentId());
                sysFileMapper.insert(file);
                return file;
            } else if (sysFiles.size() > 1) {
                throw new UniqueFileMutiException();
            } else {
                // 至少存在一个
                path = sysFiles.get(0).getFilePath();
                SysFile sysFile = sysFiles.get(0);
                sysFile.setFileName(file.getFileName());
                sysFile.setFilePath(file.getFilePath());
                sysFile.setFileSize(file.getFileSize());
                sysFile.setUploadDate(file.getUploadDate());
                sysFile.setLastUpdatedBy(file.getLastUpdatedBy());
                sysFile.setFileType(file.getFileType());
                sysFileMapper.updateByPrimaryKeySelective(sysFile);
                UploadUtil.deleteFile(path);
                return sysFile;
            }
        }
    }

    @Override
    public List<SysFile> selectByIds(IRequest requestContext, List<String> fileIds) {
        if (fileIds == null || fileIds.size() <= 0) {
            return null;
        }
        return sysFileMapper.selectFilesByIds(fileIds);
    }

    @Override
    public List<SysFile> selectFilesByTypeAndKey(IRequest requestContext, String sourceType, Long sourceKey) {
        return self().selectFilesByTypeAndKey(requestContext, sourceType, sourceKey.toString());
    }

    @Override
    public SysFile deleteImage(IRequest requestContext, SysFile file) {
        SysFile sysFile = sysFileMapper.selectByPrimaryKey(file.getFileId());
        if (sysFile != null) {
            sysFileMapper.delete(file);
        } else {
            sysFile = file;
        }
        String filePath = sysFile.getFilePath();
        UploadUtil.deleteFile(filePath + "_" + H_WIDTH + "_" + H_HEIGHT);
        UploadUtil.deleteFile(filePath + "_" + M_WIDTH + "_" + M_HEIGHT);
        UploadUtil.deleteFile(filePath + "_" + L_WIDTH + "_" + L_HEIGHT);
        UploadUtil.deleteFile(sysFile.getFilePath());
        return sysFile;
    }

    @Override
    public Long selectFileIdBySourceKey(Long itemId){
        String sourceKey = itemId.toString();
        return sysFileMapper.selectFileIdBySourceKey(sourceKey);
    };

}

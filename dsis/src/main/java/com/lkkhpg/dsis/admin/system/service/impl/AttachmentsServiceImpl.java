/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.system.exception.UploadException;
import com.lkkhpg.dsis.admin.system.service.IAttachmentsService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.system.dto.AttachAssign;
import com.lkkhpg.dsis.common.system.dto.AttachmentFile;
import com.lkkhpg.dsis.common.system.dto.MWSImages;
import com.lkkhpg.dsis.common.system.mapper.AttachAssignMapper;
import com.lkkhpg.dsis.common.system.mapper.AttachmentsMapper;
import com.lkkhpg.dsis.common.system.mapper.MWSImagesMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.attachment.AttachCategory;
import com.lkkhpg.dsis.platform.dto.attachment.Attachment;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;
import com.lkkhpg.dsis.platform.exception.StoragePathNotExsitException;
import com.lkkhpg.dsis.platform.service.attachment.IAttachCategoryService;
import com.lkkhpg.dsis.platform.service.attachment.IAttachmentService;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;
import com.lkkhpg.dsis.platform.upload.FileInfo;
import com.lkkhpg.dsis.platform.upload.UpConstants;
import com.lkkhpg.dsis.platform.upload.Uploader;
import com.lkkhpg.dsis.platform.upload.UploaderFactory;
import com.lkkhpg.dsis.platform.util.UploadUtil;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 附件目录分配权限实现类.
 * 
 * 此Service不做分布式部署!
 * 
 * @author liang.rao
 *
 */
@Service
@Transactional
public class AttachmentsServiceImpl implements IAttachmentsService {
    
    private static Logger logger = LoggerFactory.getLogger(AttachmentsServiceImpl.class);
    
    @Autowired
    private AttachAssignMapper attachAssignMapper;
    
    @Autowired
    private AttachmentsMapper attachmentsMapper;
    
    @Autowired
    private MWSImagesMapper mwsImagesMapper;
    
    @Autowired
    private IAttachCategoryService categoryService;
    
    @Autowired
    private ISysFileService fileService;

    @Autowired
    private IAttachmentService attachmentService;
    
    @Autowired
    private SpmMarketMapper spmMarketMapper;

    public static final String OPERATION_TYPE = "ALL";
    
    @Override
    public void saveAssign(IRequest request, List<AttachAssign> aas) {
        for (AttachAssign aa : aas) {
            List<AttachAssign> aalist = attachAssignMapper.selectNotIn(aa);
            if (aa.getAttachAssignId() != null) {
                if (aalist.size() == 0) {
                    attachAssignMapper.updateByPrimaryKeySelective(aa);
                }
            } else if (aalist.size() == 0) {
                    aa.setOperationType(OPERATION_TYPE);
                    aa.setEnabledFlag(SystemProfileConstants.YES);
                    attachAssignMapper.insertSelective(aa);
                }
        }
    }

    @Override
    public void deleteAssign(IRequest request, List<AttachAssign> aas) {
        for (AttachAssign aa : aas) {
            attachAssignMapper.deleteByPrimaryKey(aa.getAttachAssignId());
        }
    }

    @Override
    public List<AttachAssign> selectAssign(IRequest request, AttachAssign aa, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return attachAssignMapper.selectByRecord(aa);
    }
    
    
    
    @Override
    public List<AttachCategory> selectMWSAttachCategory(IRequest request) {
        SpmMarket spmMarket = spmMarketMapper.selectByPrimaryKey(request.getAttribute(SystemProfileConstants.MARKET_ID));
        return attachmentsMapper.selectMWSAttachCategory(spmMarket.getCode());
    }
    
    @Override
    public List<AttachmentFile> selectAttachmentFiles(IRequest request, AttachmentFile file, int page, int pagesize) {
        SpmMarket spmMarket = spmMarketMapper.selectByPrimaryKey(request.getAttribute(SystemProfileConstants.MARKET_ID));
        PageHelper.startPage(page, pagesize);
        file.setMarketCode(spmMarket.getCode());
        return attachmentsMapper.selectAttachmentFiles(file);
    }
    
    
    @Override
    public List<AttachmentFile> batchDelete(IRequest request, List<AttachmentFile> files) {
        for (AttachmentFile afile : files) {
            if (afile.getFileId() != null) {
                fileService.delete(request, afile);
            }
        }
        return files;
    }
    
    
    
    @Override
    public List<MWSImages> queryMWSImages(IRequest request, MWSImages images) {
        return mwsImagesMapper.selectByMarketIdAndType(images);
    }

    @Override
    public List<MWSImages> insertMWSImages(IRequest request, @StdWho List<MWSImages> list) {
        for (MWSImages image : list) {
            mwsImagesMapper.insert(image);
        }
        return list;
    }
    
    @Override
    public List<MWSImages> batchUpdateMWSImages(IRequest request, @StdWho List<MWSImages> list) {
        for (MWSImages image : list) {
            if (image.get__status() != null) {
                switch (image.get__status()) {
                case DTOStatus.ADD:
                    mwsImagesMapper.insert(image);
                    break;
                case DTOStatus.UPDATE:
                    mwsImagesMapper.updateByPrimaryKey(image);
                    break;
                case DTOStatus.DELETE:
                    mwsImagesMapper.deleteByPrimaryKey(image.getImageId());
                    break;
                default:
                    break;
                }
            }
        }
        return list;
    }

    
    @Override
    public List<MWSImages> batchDeleteMWSImages(IRequest request, List<MWSImages> files) {
        List<Long> ids = new ArrayList<>();
        for (MWSImages img : files) {
            ids.add(img.getImageId());
        }
        
        for (SysFile file : mwsImagesMapper.selectFileByImageId(ids)) {
            Attachment att = new Attachment();
            att.setAttachmentId(file.getAttachmentId());
            attachmentService.deleteAttachment(request, att);
        }
        for (MWSImages img : files) {
            mwsImagesMapper.deleteByPrimaryKey(img.getImageId());
        }        
        return files;
    }
    
    /**
     * 由于传递了request,此Service不做分布式部署.
     * 
     * @param request
     * @throws StoragePathNotExsitException 
     */
    @Transactional(rollbackFor = Exception.class)
    public String uploadMWSImage(IRequest requestContext, HttpServletRequest request)
            throws StoragePathNotExsitException, UploadException {
        String code = UpConstants.SUCCESS;
        //2018-04-12
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        MultipartHttpServletRequest multiReq = multipartResolver.resolveMultipart(request);


        Uploader uploader = UploaderFactory.getMutiUploader();
        uploader.init(request);
//        String sourceType = uploader.getParams("sourceType");
//        String moduleCode = uploader.getParams("moduleCode");
//        String moduleType = uploader.getParams("moduleType");
//        String url = uploader.getParams("url");
       String sourceType = multiReq.getParameter("sourceType");
       String moduleCode = multiReq.getParameter("moduleCode");
       String moduleType = multiReq.getParameter("moduleType");
       String url = multiReq.getParameter("url");

        Long marketId = null;
        Integer sortNumber = 1;
        try {
            marketId = Long.valueOf(multiReq.getParameter("marketId"));
            sortNumber = Integer.valueOf(multiReq.getParameter("sortNumber"));
        } catch (NumberFormatException e) {
            //
        }
        
        List<MWSImages> list = new ArrayList<>();
        MWSImages image = new MWSImages();
        image.setModuleCode(moduleCode);
        image.setModuleType(moduleType);
        image.setUrl(url);
        image.setSortNumber(sortNumber);
        image.setMarketId(marketId);
        image.set__status(DTOStatus.ADD);
        list.add(image);
        
        list = self().batchUpdateMWSImages(requestContext, list);
        
        if (list.size() == 1) {
            String sourceKey = list.get(0).getImageId().toString();
            // 设置上传参数
            AttachCategory category = UploadUtil.initUploaderParams(uploader, sourceType, requestContext,
                    categoryService);
            if (category == null) {
                return uploadError("TYPE_ERROR");
            }
            List<FileInfo> fileInfos = uploader.upload();
            if (!UpConstants.SUCCESS.equals(uploader.getStatus())) {
                return uploadError("UPLOAD_ERROR");
            }
            FileInfo f = fileInfos.get(0);
            try {
                SysFile sysFile = UploadUtil.genSysFile(f, requestContext.getAccountId(),
                        requestContext.getAccountId());
                Attachment attach = UploadUtil.genAttachment(category, sourceKey, requestContext.getAccountId(),
                        requestContext.getAccountId());
                if (BaseConstants.YES.equals(category.getIsUnique())) {
                    sysFile = fileService.updateOrInsertFile(requestContext, attach, sysFile);
                } else {
                    fileService.insertFileAndAttach(requestContext, attach, sysFile);
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
                File file = f.getFile();
                if (file.exists()) {
                    file.delete();
                }
                code = UpConstants.UPLOAD_ERROR;
            }
            code = UpConstants.SUCCESS;
        } else {
            code = UpConstants.UPLOAD_ERROR;
        }
        if (!UpConstants.SUCCESS.equals(code)) {
        	return uploadError(code);
        }
        return code;
        //return uploadError(code);
        //return "<script>window.parent.uploadCallback('" + code + "');</script>";
    }
    
    private String uploadError(String code) throws UploadException {
        throw new UploadException(code);
//        return "<script>window.parent.uploadCallback('" + code + "');</script>";
    }

}
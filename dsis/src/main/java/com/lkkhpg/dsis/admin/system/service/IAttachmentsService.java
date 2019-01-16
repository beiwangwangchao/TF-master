/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lkkhpg.dsis.admin.system.exception.UploadException;
import com.lkkhpg.dsis.common.system.dto.AttachAssign;
import com.lkkhpg.dsis.common.system.dto.AttachmentFile;
import com.lkkhpg.dsis.common.system.dto.MWSImages;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.attachment.AttachCategory;
import com.lkkhpg.dsis.platform.exception.StoragePathNotExsitException;

/**
 * 附件目录分配权限接口.
 * 
 * @author liang.rao
 *
 */
public interface IAttachmentsService extends ProxySelf<IAttachmentsService> {

    /**
     * 保存附件目录分配权限信息.
     * 
     * @param request
     *            请求上下文.
     * @param aas
     *            附件目录权限信息.
     */
    void saveAssign(IRequest request, List<AttachAssign> aas);

    /**
     * 删除附件目录分配权限信息.
     * 
     * @param request
     *            请求上下文.
     * @param aas
     *            附件目录权限信息.
     */
    void deleteAssign(IRequest request, List<AttachAssign> aas);

    /**
     * 查询附件目录分配权限信息.
     * 
     * @param request
     *            请求上下文.
     * @param aa
     *            附件目录权限信息.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @return 附件目录分配权限信息.
     */
    List<AttachAssign> selectAssign(IRequest request, AttachAssign aa, int page, int pagesize);

    /**
     * 查询所有MWS分类.
     * @param request
     * @return List
     */
    List<AttachCategory> selectMWSAttachCategory(IRequest request);

    /**
     * 查询MWS分类下所有附件.
     * 
     * @param request
     * @param file
     * @param categoryId
     * @param page
     * @param pagesize
     * @return List
     */
    List<AttachmentFile> selectAttachmentFiles(IRequest request, AttachmentFile file, int page, int pagesize);

    /**
     * 批量删除.
     * 
     * @param request
     * @param files
     * @return List<AttachmentFile>
     */
    List<AttachmentFile> batchDelete(IRequest request, List<AttachmentFile> files);

    /**
     * 按照市场和code查询MWS站点图片.
     * 
     * @param request
     * @param images
     * @return list
     */
    List<MWSImages> queryMWSImages(IRequest request, MWSImages images);

    /**
     * 创建MWS站点图片.
     * 
     * @param request
     * @param list
     * @return List<MWSImages>
     */
    List<MWSImages> insertMWSImages(IRequest request, @StdWho List<MWSImages> list);

    /**
     * 批量更新.
     * 
     * @param request
     * @param list
     * @return List<MWSImages>
     */
    List<MWSImages> batchUpdateMWSImages(IRequest request, @StdWho List<MWSImages> list);

    /**
     * 批量删除MWS附件信息.
     * 
     * @param request
     * @param files
     * @return List<MWSImages>
     */
    List<MWSImages> batchDeleteMWSImages(IRequest request, List<MWSImages> files);
    
    /**
     * 上传MWS图片.
     * 
     * @param requestContext
     * @param request
     * @return
     */
    String uploadMWSImage(IRequest requestContext, HttpServletRequest request) throws StoragePathNotExsitException, UploadException;
}

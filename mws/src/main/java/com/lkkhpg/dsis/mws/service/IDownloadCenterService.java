/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.AttachmentFile;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * mws下载中心service接口.
 * 
 * @author Zhaoqi
 *
 */
public interface IDownloadCenterService extends ProxySelf<IDownloadCenterService> {

    /**
     * 查询分类.
     * 
     * @param request
     *            统一上下文
     * @param page
     * @param pagesize
     * @return 返回类别信息
     */
    List<AttachmentFile> queryCategory(IRequest request, int page, int pagesize);

    /**
     * 每个分类下面的附件.
     * 
     * @param request
     *            统一上下文
     * @param categoryId
     *            类别id
     * @param page
     * @param pagesize
     * @return 附件信息
     */
    List<AttachmentFile> selectDownloadMessage(IRequest request, Long categoryId, int page, int pagesize);
}

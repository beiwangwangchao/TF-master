/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.system.dto.AttachmentFile;
import com.lkkhpg.dsis.common.system.mapper.AttachmentsMapper;
import com.lkkhpg.dsis.mws.service.IDownloadCenterService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * mws下载中心service实现.
 * 
 * @author Zhaoqi
 *
 */
@Service
public class DownloadCenterServiceImpl implements IDownloadCenterService {

    @Autowired
    private AttachmentsMapper attachmentsMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Override
    public List<AttachmentFile> queryCategory(IRequest request, int page, int pagesize) {
        // Long marketId = Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString());
        List<SpmMarket> list = spmMarketMapper.queryMarket(request.getAttribute(SystemProfileConstants.MARKET_ID));
        PageHelper.startPage(page, pagesize);
        Map<String, Object> map = new HashMap<>();
        map.put("code", list.get(0).getCode());
        return attachmentsMapper.queryCategory(map);
    }

    @Override
    public List<AttachmentFile> selectDownloadMessage(IRequest request, Long categoryId, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        AttachmentFile attachmentFile = new AttachmentFile();
        attachmentFile.setCategoryId(categoryId);
        List<AttachmentFile> ls = attachmentsMapper.selectAttachmentFiles(attachmentFile);
        return ls;
    }

}

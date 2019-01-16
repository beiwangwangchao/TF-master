/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lkkhpg.dsis.common.system.dto.AttachmentFile;
import com.lkkhpg.dsis.mws.service.IDownloadCenterService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * mws下载中心controller.
 * 
 * @author Zhaoqi
 *
 */
@Controller
public class DownloadCenterController extends BaseController {

    @Autowired
    private IDownloadCenterService downloadCenterService;

    /**
     * 查询分类.
     * 
     * @param request
     *            统一上下文
     * @param page
     * @param pagesize
     * @return 返回类别信息
     */
    @RequestMapping(value = "/account/queryCategory")
    public ResponseData queryCategory(HttpServletRequest request, 
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        List<AttachmentFile> list = downloadCenterService.queryCategory(createRequestContext(request), page, pagesize);
        return new ResponseData(list);
    }

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
    @RequestMapping(value = "/account/selectDownloadMessage")
    public ResponseData selectDownloadMessage(HttpServletRequest request, Long categoryId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        List<AttachmentFile> list = downloadCenterService.selectDownloadMessage(createRequestContext(request),
                categoryId, page, pagesize);
        for (AttachmentFile af : list) {
            String befor = af.getFileName();
            String[] split = befor.split("\\."); // 分割文件去掉后缀
            String fileName = split[0];
            af.setFileName(fileName);
        }
        return new ResponseData(list);
    }
}

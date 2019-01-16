/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.admin.system.exception.UploadException;
import com.lkkhpg.dsis.admin.system.service.IAttachmentsService;
import com.lkkhpg.dsis.common.system.dto.AttachAssign;
import com.lkkhpg.dsis.common.system.dto.AttachmentFile;
import com.lkkhpg.dsis.common.system.dto.MWSImages;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.attachment.AttachCategory;
import com.lkkhpg.dsis.platform.exception.BaseException;
import com.lkkhpg.dsis.platform.exception.StoragePathNotExsitException;
import com.lkkhpg.dsis.platform.exception.TokenException;
import com.lkkhpg.dsis.platform.security.TokenUtils;
import com.lkkhpg.dsis.platform.service.attachment.IAttachCategoryService;

/**
 * 附件相关Controller.
 * 
 * @author liang.rao
 *
 */

@Controller
public class AttachmentsController extends BaseController {

    private static final String CODE_MWS_SITE_MAIN = "MWS_SITE_MAIN";
    private static final int MWS_SITE_MAIN_DEFAULT_SIZE = 4;

    @Autowired
    private IAttachmentsService attachmentsService;

    @Autowired
    private IAttachCategoryService categoryService;
    

    /**
     * 保存附件目录分配权限信息.
     * 
     * @param request
     *            请求上下文.
     * @param aas
     *            附件目录分配权限.
     * @return 响应信息.
     */
    @RequestMapping(value = "/sys/categoryassign/save")
    @ResponseBody
    public ResponseData save(HttpServletRequest request, @RequestBody List<AttachAssign> aas) {
        IRequest requestContext = createRequestContext(request);
        attachmentsService.saveAssign(requestContext, aas);
        return new ResponseData();
    }

    /**
     * 查询附件目录分配权限信息.
     * 
     * @param request
     *            请求上下文.
     * @param objectId
     *            关联categoryId.
     * @param objectType
     *            关联objectType.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @return 响应信息.
     */
    @RequestMapping(value = "/sys/categoryassign/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, Long objectId, String objectType,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        AttachAssign aa = new AttachAssign();
        aa.setObjectType(objectType);
        aa.setObjectId(objectId);
        return new ResponseData(attachmentsService.selectAssign(requestContext, aa, page, pagesize));
    }

    /**
     * 删除附件目录分配权限信息.
     * 
     * @param request
     *            请求上下文.
     * @param aas
     *            附件目录分配权限.
     * @return 响应信息.
     */
    @RequestMapping(value = "/sys/categoryassign/delete")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<AttachAssign> aas) {
        IRequest requestContext = createRequestContext(request);
        attachmentsService.deleteAssign(requestContext, aas);
        return new ResponseData();
    }

    /**
     * 查询MWS附件信息.
     * 
     * @param file
     *            查询条件
     * @param page
     *            页数
     * @param pagesize
     *            每页记录数
     * @param request
     *            请求上下文
     * @return 相应数据
     */
    @RequestMapping("sys/attachment/query")
    @ResponseBody
    public ResponseData queryAttatchments(AttachmentFile file, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        return new ResponseData(
                attachmentsService.selectAttachmentFiles(createRequestContext(request), file, page, pagesize));
    }

    /**
     * 删除附件.
     * 
     * @param files
     *            删除的文件
     * @param request
     *            请求上下文
     * @return 相应数据
     * @throws TokenException
     *             TokenException
     */
    @RequestMapping("sys/attachment/batchDelete")
    @ResponseBody
    public ResponseData batchDelete(@RequestBody List<AttachmentFile> files, HttpServletRequest request)
            throws TokenException {
        for (AttachmentFile f : files) {
            TokenUtils.checkToken(request.getSession(false), f);
        }
        return new ResponseData(attachmentsService.batchDelete(createRequestContext(request), files));
    }

    @RequestMapping(value = { "/sys/sys_attach_category_manage.html" })
    public ModelAndView attachmentCategoryList(HttpServletRequest request,
            @RequestParam(required = false) String parentCategoryId) {
        ModelAndView view = new ModelAndView(getViewPath() + "/sys/sys_attach_category_manage");
        if (parentCategoryId != null) {
            List<AttachCategory> cates = categoryService.selectCategoryBreadcrumbList(createRequestContext(request),
                    Long.valueOf(parentCategoryId));
            view.addObject("breadCrumb", cates);
        }

        return view;
    }

    /**
     * MWS附件上传界面.
     * 
     * @param request
     * @return ModelAndView
     */
    @RequestMapping("sys/sys_attachment_create.html")
    @ResponseBody
    public ModelAndView createAttatchment(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(getViewPath() + "/sys/sys_attachment_create");
        view.addObject("markets", attachmentsService.selectMWSAttachCategory(createRequestContext(request)));
        return view;
    }

    /**
     * MWS附件列表.
     * 
     * @param request
     * @return ModelAndView
     */
    @RequestMapping("sys/sys_attachment_list.html")
    @ResponseBody
    public ModelAndView attatchmentList(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(getViewPath() + "/sys/sys_attachment_list");
        view.addObject("markets", attachmentsService.selectMWSAttachCategory(createRequestContext(request)));
        return view;
    }

    /**
     * MWS图片查询. 需要附件目录提前定义好code.
     * 
     * @param images
     * @param request
     * @return ModelAndView
     */
    @RequestMapping("mws/images/query")
    @ResponseBody
    public ResponseData queryMWSImages(MWSImages images, HttpServletRequest request) {
        String sourceType = images.getModuleType();
        List<MWSImages> list = attachmentsService.queryMWSImages(createRequestContext(request), images);
        if (CODE_MWS_SITE_MAIN.equals(sourceType) && list.size() == 0) {
            initDefaultMWSImageList(request, images.getMarketId(), CODE_MWS_SITE_MAIN, MWS_SITE_MAIN_DEFAULT_SIZE);
            list = attachmentsService.queryMWSImages(createRequestContext(request), images);
        }
        return new ResponseData(list);
    }

    /**
     * 初始化默认数据.
     * 
     * @param request
     * @param marketid
     * @param moduleCode
     * @param size
     */
    private void initDefaultMWSImageList(HttpServletRequest request, Long marketid, String moduleCode, int size) {
        List<MWSImages> newList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            MWSImages img = new MWSImages();
            img.setModuleCode(moduleCode);
            img.setModuleType(moduleCode);
            img.setMarketId(marketid);
            img.setSortNumber(i);
            newList.add(img);
        }
        attachmentsService.insertMWSImages(createRequestContext(request), newList);
    }

    /**
     * 保存MWS站点图片.
     * 
     * @param images
     * @param request
     * @return 相应数据
     * @throws BaseException
     */
    @RequestMapping(value = "/mws/images/batchSubmit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData imageBatchSubmit(@RequestBody List<MWSImages> images, HttpServletRequest request)
            throws BaseException {
        return new ResponseData(attachmentsService.batchUpdateMWSImages(createRequestContext(request), images));
    }

    /**
     * 批量删除MWS站点图片.
     * 
     * @param images
     * @param request
     * @return ResponseData
     * @throws BaseException
     */
    @RequestMapping(value = "/mws/images/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData imageBatchDelete(@RequestBody List<MWSImages> images, HttpServletRequest request)
            throws BaseException {
        return new ResponseData(attachmentsService.batchDeleteMWSImages(createRequestContext(request), images));
    }
    
    
    /**
     * 上传图片.
     * 
     * @param request
     * @return Object
     * @throws StoragePathNotExsitException 
     * 
     */
    @RequestMapping(value = "/mws/images/upload", method = RequestMethod.POST)
    public Object uploadMWSImage(HttpServletRequest request) throws StoragePathNotExsitException {
        try {
            String code = attachmentsService.uploadMWSImage(createRequestContext(request), request);
            return "<script>window.parent.uploadCallback('" + code + "');</script>";
            //return attachmentsService.uploadMWSImage(createRequestContext(request), request);
        } catch (UploadException e) {
            //e.getMessage();
            return "<script>window.parent.uploadCallback('" + e.getMessage() + "');</script>";
        }
    }
    

}

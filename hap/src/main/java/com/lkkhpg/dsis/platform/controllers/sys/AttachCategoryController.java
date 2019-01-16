/*
 *
 */
package com.lkkhpg.dsis.platform.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.attachment.AttachCategory;
import com.lkkhpg.dsis.platform.exception.CategorySourceTypeRepeatException;
import com.lkkhpg.dsis.platform.service.attachment.IAttachCategoryService;

/**
 * 附件分类.
 * 
 * @author xiaohua
 *
 */
@Controller
public class AttachCategoryController extends BaseController {

    @Autowired
    private IAttachCategoryService categoryService;

    /**
     * 附件分类列表.
     * 
     * @param request
     *            HttpServletRequest
     * @param category
     *            AttachCategory参数
     * @return ResponseData 结果对象
     */
    @RequestMapping(value = "/sys/attachcategory/query")
    public ResponseData query(HttpServletRequest request, AttachCategory category) {
        if (category.getParentCategoryId() == null) {
            category.setParentCategoryId(AttachCategory.NO_PARENT);
        }
        return new ResponseData(categoryService.selectCategories(createRequestContext(request), category));
    }

    /**
     * 保存或者更新目录.
     * 
     * @param request
     *            HttpServletRequest
     * @param categories
     *            AttachCategory列表
     * @param result
     *            BindingResult
     * @return ResponseData 结果对象
     * @throws CategorySourceTypeRepeatException
     *             sourceType重复
     */
    @RequestMapping(value = "/sys/attachcategory/submit", method = RequestMethod.POST)
    public ResponseData submitCategory(HttpServletRequest request, @RequestBody List<AttachCategory> categories,
            BindingResult result) throws CategorySourceTypeRepeatException {
        getValidator().validate(categories, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        AttachCategory category = categoryService.validateType(categories);
        if (category != null) {
            throw new CategorySourceTypeRepeatException();

        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(categoryService.batchUpdate(requestCtx, categories));
    }

    /**
     * 归档.
     * 
     * @param request
     *            HttpServletRequest
     * @param category
     *            AttachCategory
     * @return ResponseData 结果对象
     */
    @RequestMapping(value = "/sys/attachcategory/remove")
    public ResponseData remove(HttpServletRequest request, @RequestBody AttachCategory category) {
        ResponseData response = new ResponseData();
        if (!categoryService.remove(category)) {
            response.setSuccess(false);
        } else {
            response.setSuccess(true);
        }
        return response;
    }

    /**
     * 返回分类的树数据.
     * 
     * @param request
     *            HttpServletRequest
     * @param category
     *            AttachCategory参数
     * @return List AttachCategory列表
     */
    @RequestMapping(value = "/sys/attachcategory/tree")
    @ResponseBody
    public List<AttachCategory> tree(HttpServletRequest request, AttachCategory category) {
        if (category.getParentCategoryId() == null) {
            category.setParentCategoryId(AttachCategory.NO_PARENT);
        }
        IRequest requestCtx = createRequestContext(request);
        return categoryService.selectCategories(requestCtx, category);
    }
    
    
    
    /**
     * 附件目录管理界面.
     * 
     * @param request
     * @param parentCategoryId
     * @return ModelAndView
     */
    @RequestMapping("/attach/sys_attach_category_manage.html")
    public ModelAndView attachmentCategoryList(HttpServletRequest request,
            @RequestParam(required = false) String parentCategoryId) {
        ModelAndView view = new ModelAndView(getViewPath() + "/attach/sys_attach_category_manage");
        if (parentCategoryId != null) {
            List<AttachCategory> cates = categoryService.selectCategoryBreadcrumbList(createRequestContext(request),
                    Long.valueOf(parentCategoryId));
            view.addObject("breadCrumb", cates);
        }

        return view;
    }
    
    
}

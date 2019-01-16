/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.common.product.dto.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.product.exception.ItemException;
import com.lkkhpg.dsis.admin.product.product.service.IItemService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.constant.ProductConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;

/**
 * 商品、商品包资料管理控制器.
 * 
 * @author linxixin, wangchao
 */
@Controller
public class ItemController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ICodeService codeService;

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private IItemService itemService;

    private static final String ITEM_IMAGE_SOURCE_TYPE = "PACKAGE_IMAGE";

    private static final String FILE_UPLOAD_DIR = "/upload";
    private static final String FILE_UPLOAD_SUB_IMG_DIR = "/img";
    private static final String FOR_RESOURCES_LOAD_DIR = "/resources";
    // 每个上传子目录保存的文件的最大数目
    private static final int MAX_NUM_PER_UPLOAD_SUB_DIR = 500;
    // 上传文件的最大文件大小
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2;
    // 系统默认建立和使用的以时间字符串作为文件名称的时间格式
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMdd";
    private static final String DEFAULT_SUB_FOLDER_FORMAT_NO_AUTO = "yyyy-MM-dd";
    private static final String DEFAULT_SUB_FOLDER_FORMAT_SECOND = "yyyyMMdd_HHmmss_SSS";

    /**
     * 商品资料和商品包的条件查询，用type来区分.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Id
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return 商品列表
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @RequestMapping(value = "/inv/item/query")
    @ResponseBody
    public ResponseData queryItem(HttpServletRequest request,HttpServletResponse response, InvItem item,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws IllegalArgumentException, IllegalAccessException, IOException {

            IRequest iRequest = createRequestContext(request);
            Long roleId = iRequest.getAttribute("_roleId");
            Long userId = iRequest.getAttribute("userId");
            item.setUserId(userId);
            item.setRoleId(roleId);

        List<InvItem> items = itemService.queryItem(createRequestContext(request), item, page, pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug("items:{}", items.toString());
        }
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, items);
            return null;
        } else {
            return new ResponseData(items);
        }
    }

    /**
     * 商品资料和商品包的条件查询，用type来区分,不限制市场，订单使用.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Id
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return 商品列表
     */
    @RequestMapping(value = "/inv/item/queryForOrder")
    @ResponseBody
    public ResponseData queryItemForOrder(HttpServletRequest request, InvItem item,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        List<InvItem> items = itemService.queryItemForOrder(createRequestContext(request), item, page, pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug("items:{}", items.toString());
        }
        return new ResponseData(items);
    }

    /**
     * 采购订单商品表查询.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Id
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return 商品列表
     */
    @RequestMapping(value = "/inv/poheaderitem/query")
    @ResponseBody
    public ResponseData queryPoHeaderItem(HttpServletRequest request, InvItem item,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        List<InvItem> items = itemService.queryPoHeaderItem(iRequest, item, page, pagesize);
        return new ResponseData(items);
    }

    /**
     * 查询未分配类别商品.
     * 
     * @param request
     *            上下文请求
     * @param item
     *            产品参数
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 符合条件的商品
     */
    @RequestMapping(value = "/inv/item/queryFilter")
    @ResponseBody
    public ResponseData queryItemFilter(HttpServletRequest request, InvItem item,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        List<InvItem> items = itemService.queryItemFilterByCategory(createRequestContext(request), item, page,
                pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug("items：{}", items.toString());
        }
        return new ResponseData(items);
    }

    /**
     * 查询底层的商品类别.
     * 
     * @param request
     *            请求上下文
     * @param itemType
     *            商品类型
     * @return 商品类别.
     * @throws JsonProcessingException
     *             json异常
     */
    @RequestMapping(value = "/inv/item/querytype", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryType(HttpServletRequest request, String itemType) throws JsonProcessingException {
        IRequest iRequest = createRequestContext(request);
        List<InvCategoryB> categories = itemService.queryCategoryLov(iRequest, itemType);
        return new ResponseData(categories);

    }

    /**
     * 保存商品包.
     * 
     * @param request
     *            请求上下文
     * @param itemAllDto
     *            商品Dto
     * @param result
     *            验证字段结果
     * @return 返回结果
     * @throws ItemException
     *             商品统一异常
     * @throws UnsupportedEncodingException
     *             编码异常
     */
    @RequestMapping(value = "inv/item/saveAll")
    @ResponseBody
    public ResponseData saveAllPackage(HttpServletRequest request, @RequestBody ItemAllDto itemAllDto,
            BindingResult result) throws ItemException, UnsupportedEncodingException {
        // 校验
        //getValidator().validate(itemAllDto, result);
        InvItem itemInfo = itemAllDto.getItemInfo();
        Date date = itemInfo.getValidateTo();
        if(date != null){
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
        }
        itemInfo.setValidateTo(date);
        List<InvItemHierarchy> itemIncludes = itemAllDto.getItemIncludes();
        List<ItemAttrDto> itemAttrsTls = itemAllDto.getItemAttrsTls();
        List<InvItemAssign> itemAssigns = itemAllDto.getItemAssigns();
        List<PriceListDetail> itemPriceDetail = itemAllDto.getItemPriceDetail();
        //BigDecimal fp=itemAllDto.getFirstPrice();
        //List<PriceListDetail> itemPriceDetailAll = itemAllDto.getItemPriceDetailAll();
        for (PriceListDetail pd : itemPriceDetail) {
            if(pd.getEndActiveDate() != null){
                Date da = pd.getEndActiveDate();
                da.setHours(23);
                da.setMinutes(59);
                da.setSeconds(59);
            }
        }

        if (itemInfo != null) {
            getValidator().validate(itemInfo, result);
        }
        if (itemIncludes != null) {
            getValidator().validate(itemIncludes, result);
        }
        if (itemAttrsTls != null) {
            //getValidator().validate(itemAttrsTls, result);
        }
        if (itemAssigns != null) {
            getValidator().validate(itemAssigns, result);
        }
        if (itemPriceDetail != null) {
            getValidator().validate(itemPriceDetail, result);
        }
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        HttpSession session = request.getSession();
        IRequest iRequest = createRequestContext(request);
        iRequest.setAttribute("createdBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));
        iRequest.setAttribute("salesOrgId",  Long.parseLong(String.valueOf(session.getAttribute("_salesOrgId"))));
        itemService.savePackageAll(iRequest, itemAllDto);
        ResponseData response = new ResponseData();
        response.setSuccess(true);
        return response;
    }

    /**
     * 发布商品.
     * 
     * @param request
     *            请求上下文
     * @param itemAllDto
     *            商品Dto
     * @param result
     *            验证字段结果
     * @return 返回结果
     * @throws ItemException
     *             商品统一异常
     * @throws UnsupportedEncodingException
     *             编码异常
     */
    @RequestMapping(value = "inv/item/publish")
    @ResponseBody
    public ResponseData publishItem(HttpServletRequest request, @RequestBody ItemAllDto itemAllDto,
            BindingResult result) throws ItemException, UnsupportedEncodingException {
        // 校验
        getValidator().validate(itemAllDto, result);
        InvItem itemInfo = itemAllDto.getItemInfo();
        List<InvItemHierarchy> itemIncludes = itemAllDto.getItemIncludes();
        List<ItemAttrDto> itemAttrsTls = itemAllDto.getItemAttrsTls();
        List<InvItemAssign> itemAssigns = itemAllDto.getItemAssigns();
        List<PriceListDetail> itemPriceDetail = itemAllDto.getItemPriceDetail();

        if (itemInfo != null) {
            getValidator().validate(itemInfo, result);
        }
        if (itemIncludes != null) {
            getValidator().validate(itemIncludes, result);
        }
        if (itemAttrsTls != null) {
            //getValidator().validate(itemAttrsTls, result);
        }
        if (itemAssigns != null) {
            getValidator().validate(itemAssigns, result);
        }
        if (itemPriceDetail != null) {
            getValidator().validate(itemPriceDetail, result);
        }
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        HttpSession session = request.getSession();
        IRequest iRequest = createRequestContext(request);
        iRequest.setAttribute("createdBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));
        iRequest.setAttribute("salesOrgId",  Long.parseLong(String.valueOf(session.getAttribute("_salesOrgId"))));
        itemService.publishItem(iRequest, itemAllDto);
        ResponseData response = new ResponseData();
        response.setSuccess(true);
        return response;
    }

    /**
     * 撤销商品发布.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return ResponseData
     * @throws ItemException
     *             商品统一异常
     */
    @RequestMapping(value = "/inv/item/unPublishByItemId")
    public ResponseData unPublishItem(HttpServletRequest request, Long itemId) throws ItemException {
        IRequest iRequest = createRequestContext(request);
        itemService.unPublishItem(iRequest, itemId);
        ResponseData response = new ResponseData();
        response.setSuccess(true);
        return response;
    }

    /**
     * 根据itemId查询商品包.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param mode
     *            商品保存方式
     * @return 返回结果
     */
    @RequestMapping(value = "/inv/item/queryByItemId")
    @ResponseBody
    public ResponseData queryByItemId(HttpServletRequest request, Long itemId, String mode) {
        IRequest iRequest = createRequestContext(request);
        InvItem item = itemService.queryItemByItemId(iRequest, itemId, mode);
        List<InvItem> items = new ArrayList<InvItem>();
        items.add(item);
        return new ResponseData(items);
    }

    /**
     * 获取下一条记录的itemId.
     * 
     * @param request
     *            请求上下文
     * @return 返回结果
     */
    @RequestMapping(value = "/inv/item/queryNextItemId")
    @ResponseBody
    public ResponseData getNextItemId(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Long itemId = itemService.getNextItemId(iRequest);
        List<Long> itemIds = new ArrayList<Long>();
        itemIds.add(itemId);
        return new ResponseData(itemIds);
    }

    /**
     * 根据itemId查询商品包语言.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品多语言列表
     */
    @RequestMapping(value = "/inv/item/queryItemTlsByItemId")
    @ResponseBody
    public ResponseData queryItemTlsByItemId(HttpServletRequest request, Long itemId) {
        IRequest iRequest = createRequestContext(request);
        List<InvItemTl> ItemTls = itemService.queryItemTlsByItemId(iRequest, itemId);
        return new ResponseData(ItemTls);
    }

    /**
     * 根据itemId查询商品包有效性.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品可用性列表
     */
    @RequestMapping(value = "/inv/item/queryItemAvailabilities")
    @ResponseBody
    public ResponseData queryItemAvailabilities(HttpServletRequest request, Long itemId) {
        IRequest iRequest = createRequestContext(request);
        List<InvItemAvailability> itemAvailability = itemService.queryItemAvailabilityByItemId(iRequest, itemId);
        return new ResponseData(itemAvailability);
    }

    /**
     * 根据itemId查询包含商品.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 包含商品列表
     */
    @RequestMapping(value = "/inv/item/queryHierarchyByItemId")
    @ResponseBody
    public ResponseData queryHierarchyByItemId(HttpServletRequest request, Long itemId) {
        IRequest iRequest = createRequestContext(request);
        List<InvItemHierarchy> itemHierarchy = itemService.queryItemHierarchyByItemId(iRequest, itemId);
        return new ResponseData(itemHierarchy);
    }

    /**
     * 根据itemId查询商品包参数语言.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品参数语言列表
     * @throws UnsupportedEncodingException
     *             字符格式异常
     */
    @RequestMapping(value = "/inv/item/queryItemAttrTlsByItemId")
    @ResponseBody
    public ResponseData queryItemAttrTlsByItemId(HttpServletRequest request, Long itemId)
            throws UnsupportedEncodingException {
        IRequest iRequest = createRequestContext(request);
        List<ItemAttrDto> ItemAttrTl = itemService.queryItemAttrTlsByItemId(iRequest, itemId);
        return new ResponseData(ItemAttrTl);
    }

    /**
     * 根据itemId查询商品包库存组织.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品库存组织列表
     */
    @RequestMapping(value = "/inv/item/queryItemAssignsByItemId")
    @ResponseBody
    public ResponseData queryItemAssignsByItemId(HttpServletRequest request, Long itemId) {
        IRequest iRequest = createRequestContext(request);
        List<InvItemAssign> itemAssign = itemService.queryItemAssignByItemId(iRequest, itemId);
        return new ResponseData(itemAssign);
    }

    /**
     * 销售区域查询(lov).
     * 
     * @param request
     *            请求上下文
     * @param org
     *            销售区域Dto
     * @param page
     *            分页
     * @param pagesize
     *            分页大小
     * @return 销售区域列表
     */
    @RequestMapping(value = "/inv/org/queryOrgs")
    @ResponseBody
    public ResponseData querySaleOrg(HttpServletRequest request, SpmSalesOrganization org,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        List<SpmSalesOrganization> queryOrgs = itemService.queryOrgs(iRequest, org, page, pagesize);
        return new ResponseData(queryOrgs);
    }

    /**
     * 商品价格查询.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param currency
     *            货币
     * @param uomCode
     *            单位
     * @param salesOrgId
     * @return 商品价格列表
     */
    @RequestMapping(value = "/inv/price/queryPriceByItemId")
    @ResponseBody
    public ResponseData querySaleOrg(HttpServletRequest request, Long itemId, String currency, String uomCode,
            Long salesOrgId) {
        IRequest iRequest = createRequestContext(request);
        List<PriceListDetail> queryPriceByItemId = itemService.queryPriceByItemId(iRequest, itemId, currency, uomCode,
                salesOrgId);
        return new ResponseData(queryPriceByItemId);
    }

    /**
     * 商品价格查询.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品价格列表
     */
    @RequestMapping(value = "/inv/price/queryPriceByItem")
    @ResponseBody
    public ResponseData queryPriceByItem(HttpServletRequest request, Long itemId) {
        HttpSession session = request.getSession();
        IRequest iRequest = createRequestContext(request);
        iRequest.setAttribute("userId", Long.parseLong(String.valueOf(session.getAttribute("userId"))));
        List<PriceListDetail> queryPriceByItemId = itemService.queryPriceByItem(iRequest, itemId);
        return new ResponseData(queryPriceByItemId);
    }

    /**
     * 查询所有的商品类别(comobox).
     * 
     * @param request
     *            请求上下文
     * @param itemType
     *            商品类型
     * @return 商品类别列表.
     */
    @RequestMapping(value = "/inv/category/queryAllCategory")
    @ResponseBody
    public ResponseData queryAllCategory(HttpServletRequest request, String itemType) {
        IRequest iRequest = createRequestContext(request);
        List<InvCategoryB> queryAllCategory = itemService.queryCategoryLov(iRequest, itemType);
        return new ResponseData(queryAllCategory);
    }

    /**
     * 查询所有库存组织(lov).
     * 
     * @param request
     *            请求上下文
     * @param org
     *            库存Dto
     * @return 库存组织信息
     */
    @RequestMapping(value = "/inv/org/queryAllInvLov")
    @ResponseBody
    public ResponseData queryAllOrganization(HttpServletRequest request, SpmInvOrganization org) {
        IRequest iRequest = createRequestContext(request);
        List<SpmInvOrganization> querySpmInvOrganization = itemService.querySpmInvOrganization(iRequest, org);
        return new ResponseData(querySpmInvOrganization);
    }

    /**
     * 库存属性参数查询.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param organizationId
     *            库存Id
     * @return 库存属性列表
     */
    @RequestMapping(value = "/inv/property/queryPropertyType", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryInvItemProperty(HttpServletRequest request, Long itemId, Long organizationId) {
        IRequest iRequest = createRequestContext(request);
        List<InvItemPropertyDto> invProperties = new ArrayList<InvItemPropertyDto>();
        InvItemPropertyDto invProperty = itemService.queryPropertyType(iRequest, itemId, organizationId);
        invProperties.add(invProperty);
        return new ResponseData(invProperties);
    }

    /**
     * 商品参数图片上传.
     * 
     * @param request
     *            请求上下文
     * @param response
     *            请求结果
     * @throws ItemException
     *             商品统一异常
     */
    @RequestMapping(value = "/inv/image/upload", method = RequestMethod.POST)
    public void itemImageUpload(HttpServletRequest request, HttpServletResponse response) throws ItemException {
        // 判断请求是否包含文件
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            return;
        }
        File folder = buildFolder(request);
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            // 上传文件的返回地址
            String fileUrl = "";

            FileItemFactory factory = new DiskFileItemFactory();

            ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
            servletFileUpload.setFileSizeMax(MAX_FILE_SIZE);

            List<FileItem> fileitem = servletFileUpload.parseRequest(request);

            if (null == fileitem || 0 == fileitem.size()) {
                return;
            }

            Iterator<FileItem> fileitemIndex = fileitem.iterator();
            if (fileitemIndex.hasNext()) {
                FileItem file = fileitemIndex.next();
                if (file.isFormField()) {
                    if (logger.isErrorEnabled()) {
                        logger.error("上传文件非法！isFormField=true");
                    }
                }

                String fileClientName = getFileName(file.getName());
                String fileSuffix = StringUtils.substring(fileClientName, fileClientName.lastIndexOf(".") + 1);

                if (!StringUtils.equalsIgnoreCase(fileSuffix, "jpg")
                        && !StringUtils.equalsIgnoreCase(fileSuffix, "jpeg")
                        && !StringUtils.equalsIgnoreCase(fileSuffix, "bmp")
                        && !StringUtils.equalsIgnoreCase(fileSuffix, "gif")
                        && !StringUtils.equalsIgnoreCase(fileSuffix, "png")) {
                    if (logger.isErrorEnabled()) {
                        logger.error("上传文件的格式错误 {}", fileSuffix);
                    }
                    throw new ItemException(ItemException.IMAGE_FORMAT_ERROR_EXCEPTION, new Object[] {});
                }

                if (logger.isInfoEnabled()) {
                    logger.info("开始上传文件: {}", file.getName());
                }

                String fileServerName = generateFileName(folder, fileSuffix);
                // 为了客户端已经设置好了图片名称在服务器继续能够明确识别，这里不改名称
                File newfile = new File(folder, fileServerName);
                file.write(newfile);

                if (logger.isInfoEnabled()) {
                    logger.info("上传文件结束，新名称:{}", fileServerName);
                    logger.info("上传文件结束，floder:{}", newfile.getPath());
                }

                fileUrl = FOR_RESOURCES_LOAD_DIR + FILE_UPLOAD_DIR + FILE_UPLOAD_SUB_IMG_DIR + File.separator
                        + folder.getName() + File.separator + newfile.getName();
                fileUrl = StringUtils.replace(fileUrl, ProductConstants.DOUBLE_SLASH, File.separator);
                fileUrl = request.getContextPath() + fileUrl;

                String callback = request.getParameter("CKEditorFuncNum");
                out.println("<script type=\"text/javascript\">");
                out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl + "',''" + ")");
                out.println("</script>");
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }

    }

    private String generateFileName(File folder, String suffix) {
        String filename;
        File file;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_SECOND);
        String base = format.format(date);
        filename = base + "." + suffix;
        file = new File(filename);
        int i = 1;
        while (file.exists()) {
            filename = String.format("%s_%d.%s", base, i, suffix);
            i++;
        }
        return filename;
    }

    private String getFileName(String str) {
        int index = str.lastIndexOf(ProductConstants.DOUBLE_SLASH);
        if (-1 != index) {
            return str.substring(index);
        } else {
            return str;
        }
    }

    /**
     * 创建目录.
     * 
     * @return 文件
     */
    private File buildFolder(HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath(FOR_RESOURCES_LOAD_DIR);

        File firstFolder = new File(realPath + FILE_UPLOAD_DIR);
        if (!firstFolder.exists()) {
            if (!firstFolder.mkdir()) {
                return null;
            }
        }

        String folderdir = realPath + FILE_UPLOAD_DIR + FILE_UPLOAD_SUB_IMG_DIR;
        if (logger.isDebugEnabled()) {
            logger.debug("folderdir{}", folderdir);
        }

        if (StringUtils.isBlank(folderdir)) {
            if (logger.isDebugEnabled()) {
                logger.debug("路径错误:{}", folderdir);
            }
            return null;
        }

        File floder = new File(folderdir);
        if (!floder.exists()) {
            if (!floder.mkdir()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("创建文件夹出错！path={}", folderdir);
                }
                return null;
            }
        }

        String[] files = floder.list();
        if (null != files && 0 < files.length) {
            Date oldDate = null;
            int index = -1;
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                try {
                    Date thisDate = DateUtils.parseDate(fileName,
                            new String[] { DEFAULT_SUB_FOLDER_FORMAT_AUTO, DEFAULT_SUB_FOLDER_FORMAT_NO_AUTO });
                    if (oldDate == null) {
                        oldDate = thisDate;
                        index = i;
                    } else {
                        if (thisDate.after(oldDate)) {
                            oldDate = thisDate;
                            index = i;
                        }
                    }
                } catch (ParseException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("error", e);
                    }
                }
            }

            if (null != oldDate && -1 != index) {
                File pointfloder = new File(folderdir + File.separator + files[index]);
                if (!pointfloder.exists()) {
                    if (!pointfloder.mkdir()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("创建文件夹出错！path={}", folderdir);
                        }
                        return null;
                    }
                }

                String[] pointfloderFiles = pointfloder.list();
                if (null != pointfloderFiles && MAX_NUM_PER_UPLOAD_SUB_DIR < pointfloderFiles.length) {
                    return buildNewFile(folderdir);
                }

                return pointfloder;
            }
            return buildNewFile(folderdir);
        } else {
            return buildNewFile(folderdir);
        }

    }

    /**
     * 创建一个新文件.
     * 
     * @param path
     *            路径
     * @return 新文件
     */
    private File buildNewFile(String path) {
        // 不含有子文件夹，新建一个，通常系统首次上传会有这个情况
        File newFile = buildFileBySysTime(path);
        if (null == newFile) {
            if (logger.isDebugEnabled()) {
                logger.debug("创建文件夹失败！newFile={}", newFile);
            }
        }

        return newFile;
    }

    /**
     * 根据当前的时间建立文件夹，时间格式yyyyMMdd.
     * 
     * @param path
     *            路径
     * @return 文件夹
     */
    private File buildFileBySysTime(String path) {
        DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
        String fileName = df.format(new Date());
        File file = new File(path + File.separator + fileName);
        if (!file.mkdir()) {
            return null;
        }
        return file;
    }

    /**
     * 查询库存组织下的商品.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Dto
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return 商品列表
     */
    @RequestMapping(value = "/inv/item/queryOrgItem")
    @ResponseBody
    public ResponseData queryItemWithOrg(HttpServletRequest request, InvItem item,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(itemService.queryItemWithOrg(requestContext, item, page, pagesize));
    }

    /**
     * 获取当前库存组织下的商品信息.
     * 
     * @param request
     *            请求上下文
     * @param invItem
     *            查询条件
     * @param trxType
     *            事务类型
     * @param page
     *            页码
     * @param pagesize
     *            分页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/inv/item/itemCodeQuery")
    @ResponseBody
    public ResponseData queryItems(HttpServletRequest request, InvItem invItem, String trxType,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        Long invOrgId = (Long) request.getSession(false).getAttribute(SystemProfileConstants.INV_ORG_ID);
        return new ResponseData(itemService.queryItemBs(requestContext, invItem, invOrgId, trxType, page, pagesize));
    }

    /**
     * 单位查询lov.
     * 
     * @param request
     *            请求上下文
     * @param invUom
     *            商品Dto
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return 单位列表
     */
    @RequestMapping(value = "/inv/item/queryInvUomCodeLov")
    @ResponseBody
    public ResponseData queryUomCodes(HttpServletRequest request, InvUnitOfMeasureB invUom,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(itemService.queryUomCodes(requestContext, invUom, page, pagesize));
    }

    /**
     * 查询可供移库的商品.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品DTO
     * @param outOrg
     *            转出库存组织
     * @param inOrg
     *            转入库存组织
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return ResponseData
     */
    @RequestMapping(value = "/inv/item/queryTransferItem")
    @ResponseBody
    public ResponseData queryTransferItems(HttpServletRequest request, InvItem item, Long outOrg, Long inOrg,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        if (outOrg == null || inOrg == null) {
            return new ResponseData();
        }

        return new ResponseData(itemService.queryTransferItems(requestContext, item, outOrg, inOrg, page, pagesize));
    }

    /**
     * 查询商品是否在指定库存区域.
     * 
     * @param request
     *            请求上下文
     * @param itemIds
     *            商品Id列表
     * @param orgId
     *            库存Id
     * @return 库存组织
     * @throws ItemException
     *             商品基本异常
     */
    @RequestMapping(value = "/inv/item/queryOrgIncludeItems")
    @ResponseBody
    public ResponseData queryOrgIncludeItems(HttpServletRequest request, String itemIds, Long orgId)
            throws ItemException {
        IRequest requestContext = createRequestContext(request);
        List<Long> ids = new ArrayList<Long>();
        String[] itemIdStr = itemIds.split(",");
        for (String idStr : itemIdStr) {
            ids.add(Long.parseLong(idStr));
        }
        List<Long> list = new ArrayList<Long>();
        Long result = itemService.checkOrgIncludeItems(requestContext, ids, orgId);
        list.add(result);
        return new ResponseData(list);
    }

    /*
    * 设置商品的初始价格
    * */
    @RequestMapping(value = "/inv/item/setFirstPrice")
    @ResponseBody
    public void setFirstPrice(HttpServletRequest request, String firstPrice) {

    }

    /**
     * 查询库存是否包含指定商品.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id列表
     * @param orgIds
     *            库存Id
     * @return 库存组织
     * @throws ItemException
     *             商品基本异常
     */
    @RequestMapping(value = "/inv/item/queryIncludeItemOrgs")
    @ResponseBody
    public ResponseData queryIncludeItemOrgs(HttpServletRequest request, Long itemId, String orgIds)
            throws ItemException {
        IRequest requestContext = createRequestContext(request);
        List<Long> invOrgIds = new ArrayList<Long>();
        String[] orgIdStr = orgIds.split(",");
        for (String idStr : orgIdStr) {
            invOrgIds.add(Long.parseLong(idStr));
        }
        List<Long> list = new ArrayList<Long>();
        Long result = itemService.checkIncludeItemOrgs(requestContext, itemId, invOrgIds);
        list.add(result);
        return new ResponseData(list);
    }

    /**
     * 查询商品计算库存为计算此商品的所有商品.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品
     * @param itemId
     *            商品ID
     * @param page
     *            页码
     * @param pagesize
     *            页面记录数
     * @return 商品列表
     */
    @RequestMapping(value = "/inv/item/queryByCountStock")
    @ResponseBody
    public ResponseData queryItemsByCountStock(HttpServletRequest request, InvItem item, Long itemId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        List<InvItem> items = itemService.queryItemsByCountStock(createRequestContext(request), item, itemId, page,
                pagesize);
        return new ResponseData(items);
    }

    /**
     * 检查商品所属计算库存商品的同一库存组织是否启用.
     */
    @RequestMapping(value = "/inv/item/checkInvOrgEnable")
    @ResponseBody
    public ResponseData checkInvOrgEnable(HttpServletRequest request, Long itemId, Long invOrgId) {
        Boolean enableFlag = itemService.checkInvOrgEnable(createRequestContext(request), itemId, invOrgId);
        List<String> list = new ArrayList<String>();
        if (Boolean.TRUE.equals(enableFlag)) {
            list.add("Y");
        } else {
            list.add("N");
        }
        return new ResponseData(list);
    }

    /**
     * 查询所有底层商品类别.
     * 
     * @param request
     *            请求上下文
     * @return 商品类别
     */
    @RequestMapping(value = "/inv/item/queryAllBottomCategory")
    @ResponseBody
    public ResponseData queryAllBottomCategory(HttpServletRequest request) {
        return new ResponseData(itemService.queryAllBottomCategory(createRequestContext(request)));
    }

    /**
     * 商品界面.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品id
     * @return 视图
     */
    @RequestMapping(value = "/pm/pm_item_show.html")
    @ResponseBody
    public ModelAndView maintainItem(HttpServletRequest request, @RequestParam(required = false) Long itemId) {
        return processMaintainView(request, itemId, "/pm/pm_item_show");
    }

    /**
     * 商品包界面.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品包id
     * @return 视图
     */
    @RequestMapping(value = "/pm/pm_package_show.html")
    @ResponseBody
    public ModelAndView maintainItemPackage(HttpServletRequest request, @RequestParam(required = false) Long itemId) {
        return processMaintainView(request, itemId, "/pm/pm_package_show");
    }

    private ModelAndView processMaintainView(HttpServletRequest request, @RequestParam(required = false) Long itemId,
            String viewName) {
        ModelAndView view = new ModelAndView(getViewPath() + viewName);
        IRequest requestContext = createRequestContext(request);
        String isEdit = "1";
        if (itemId == null) {
            isEdit = "0";
            itemId = itemService.getNextItemId(createRequestContext(request));
        }

        List<SysFile> pic_list = sysFileService.selectFilesByTypeAndKey(requestContext, ITEM_IMAGE_SOURCE_TYPE, itemId);
        view.addObject("item_pic_list", pic_list);
        view.addObject("source_type", ITEM_IMAGE_SOURCE_TYPE);
        view.addObject("isedit", isEdit);
        view.addObject("itemId", itemId);
        view.addObject("price_type", codeService.selectCodeValuesByCodeName(requestContext, "PM.PRICE_TYPE"));
        return view;
    }

    /**
     * 获取是否隐藏.
     * 
     * @param request
     *            统一上下文
     * @param itemId
     *            商品id
     * @param language 多语言
     * @return list 集合
     */
    @RequestMapping(value = "inv/item/getWhetherHide")
    public ResponseData getWhetherHide(HttpServletRequest request, Long itemId, String language) {
        InvItemAttrTl iat = new InvItemAttrTl();
        iat.setItemId(itemId);
        iat.setLang(language);
        List<InvItemAttrTl> list = itemService.getWhetherHide(createRequestContext(request), iat);
        List<InvItemAttrTl> ls = new ArrayList<InvItemAttrTl>();
        for (InvItemAttrTl invItemAttrTl : list) {
            if (ProductConstants.ATTR_DESCRIPTION.equals(invItemAttrTl.getName())
                    || ProductConstants.ATTR_USER_DIRECTIONS.equals(invItemAttrTl.getName())
                    || ProductConstants.ATTR_SPECIFICATION.equals(invItemAttrTl.getName())
                    || ProductConstants.ATTR_DOSE.equals(invItemAttrTl.getName())) {
                ls.add(invItemAttrTl);
            }
        }
        return new ResponseData(ls);
    }


    /**
     * 商品价格查询.
     *
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param currency
     *            货币
     * @param uomCode
     *            单位
     * @param salesOrgId
     *            销售组织Id
     * @param orderType
     *            订单类型
     * @param itemSalestype
     *            销售类型
     * @return 商品价格列表
     */

    @RequestMapping(value = "/inv/price/queryItemPriceForOrder")
    @ResponseBody
    public ResponseData queryItemPriceForOrder(HttpServletRequest request, Long itemId, String currency, String uomCode,
                                               Long salesOrgId, String orderType, String itemSalestype) {
        IRequest iRequest = createRequestContext(request);
        OrderItemPrice orderItemPrice = itemService.queryItemPriceForOrder(iRequest, itemId, currency, uomCode,
                salesOrgId, orderType, itemSalestype);
        List<OrderItemPrice> list = new ArrayList<OrderItemPrice>();
        list.add(orderItemPrice);
        return new ResponseData(list);
    }

}

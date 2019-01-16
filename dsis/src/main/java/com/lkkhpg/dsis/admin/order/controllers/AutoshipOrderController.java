/*
 *
 */
package com.lkkhpg.dsis.admin.order.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.admin.order.service.IAutoshipOrderService;
import com.lkkhpg.dsis.admin.order.service.ILicenseFileService;
import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.exception.CommLicenseFileException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.AutoshipLine;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.common.order.dto.LicenseFile;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.exception.EmailException;
import com.lkkhpg.dsis.platform.upload.ContentTypeFilter;
import com.lkkhpg.dsis.platform.upload.FileInfo;
import com.lkkhpg.dsis.platform.upload.UpConstants;
import com.lkkhpg.dsis.platform.upload.Uploader;
import com.lkkhpg.dsis.platform.upload.UploaderFactory;
import com.lkkhpg.dsis.platform.upload.impl.StandardController;

/**
 * 自动订货单查询控制层.
 * 
 * @author HuangJiaJing
 * @author wuyichu
 * @author runbai.chen
 * @author LiuXiaWang
 *
 */
@Controller
public class AutoshipOrderController extends BaseController {

    @Autowired
    private IAutoshipOrderService autoshipOrderService;

    @Autowired
    private ILicenseFileService licenseFileService;
    @Autowired
    private ISalesOrderService salesOrderService;

    @Autowired
    private MessageSource messageSource;

    /**
     * autoship创建页面路径.
     */
    public static final String TO_AUTOSHIP_CREATE = "admin/om/om_autoship_create";

    /**
     * autoship订单查询.
     * 
     * @param request
     *            http请求
     * @param autoshipOrder
     *            autoship查询参数
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 满足条件的autoship
     * @throws OrderException
     *             查询出错时抛出
     */
    @RequestMapping(value = "/om/autoship/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData selectAutoshipOrdersByParas(HttpServletRequest request, AutoshipOrder autoshipOrder,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws OrderException {
        IRequest iRequest = createRequestContext(request);
        List<AutoshipOrder> list = autoshipOrderService.selectAutoshipOrderParas(iRequest, autoshipOrder, page,
                pagesize);
        return new ResponseData(list);
    }

    /**
     * 执行autoship转换销售订单.
     * 
     * @param request
     *            http请求
     * @param autoshipOrders
     *            autoship集合
     * @return 批次号
     */
    @RequestMapping(value = "/om/autoship/execute", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData executeAutoshipOrders(HttpServletRequest request,
            @RequestBody List<AutoshipOrder> autoshipOrders) {
        IRequest iRequest = createRequestContext(request);
        String batchNumber = autoshipOrderService.executeAutoshipOrders(iRequest, autoshipOrders);

        return new ResponseData(structBatchNumber(batchNumber));
    }

    /**
     * 构造返回批次信息.
     * 
     * @param batchNumber
     *            批次
     * @return
     */
    private List<HashMap<String, String>> structBatchNumber(String batchNumber) {
        List<HashMap<String, String>> responseData = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("batchNumber", batchNumber);
        responseData.add(map);
        return responseData;
    }

    /**
     * 提交autoship数据.
     * 
     * @param request
     *            http请求
     * @param autoshipOrder
     *            autoship数据
     * @param result
     *            校验结果
     * @return 提交后的autoship数据
     * @throws CommOrderException
     *             写入或更新出错时抛出
     */
    @RequestMapping(value = "/om/autoship/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitAutoshipOrder(final HttpServletRequest request,
            @RequestBody final AutoshipOrder autoshipOrder, final BindingResult result) throws CommOrderException {
        getValidator().validate(autoshipOrder, result);
        IRequest iRequest = createRequestContext(request);
        autoshipOrderService.submitAutoshipOrder(iRequest, autoshipOrder);
        List<AutoshipOrder> rows = new ArrayList<AutoshipOrder>();
        rows.add(autoshipOrder);
        return new ResponseData(rows);
    }

    /**
     * 修改autoship状态.
     * 
     * @param request
     *            http请求
     * @param autoshipId
     *            autoshipid
     * @param autoshipStatus
     *            autoship状态
     * @return 是否成功
     * @throws CommOrderException
     *             修改出错时抛出
     */
    @RequestMapping(value = "/om/autoship/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateStatus(final HttpServletRequest request, final Long autoshipId,
            final String autoshipStatus) throws CommOrderException {
        ResponseData responseData = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        responseData.setSuccess(autoshipOrderService.updateOrderStatus(iRequest, autoshipId, autoshipStatus) > 0);
        return responseData;
    }

    /**
     * 获取autoship详情.
     * 
     * @param request
     *            http请求
     * @param autoshipId
     *            autoshipId
     * @return autoship详细信息
     */
    @RequestMapping(value = "/om/autoship/detail")
    @ResponseBody
    public ResponseData orderDetail(final HttpServletRequest request, final Long autoshipId) {
        ResponseData responseData = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        AutoshipOrder autoshipOrder = autoshipOrderService.getDetail(iRequest, autoshipId);
        List<AutoshipOrder> autoshipOrders = new ArrayList<AutoshipOrder>();
        autoshipOrders.add(autoshipOrder);
        responseData.setRows(autoshipOrders);
        return responseData;
    }

    /**
     * 删除autoship订单行.
     * 
     * @param request
     *            http请求
     * @param lines
     *            autoship订单行集合
     * @return true
     * @throws OrderException
     *             删除失败时抛出
     */
    @RequestMapping(value = "/om/autoship/deleteLine")
    @ResponseBody
    public ResponseData deleteLine(final HttpServletRequest request, @RequestBody final List<AutoshipLine> lines)
            throws OrderException {
        ResponseData responseData = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        autoshipOrderService.deleteLine(iRequest, lines);
        return responseData;
    }

    /**
     * 校验会员是否存在激活或暂停的autoship.
     * 
     * @param request
     *            http请求
     * @param memberId
     *            会员id
     * @return 存在则返回false 且autoship的id，否则返回true
     */
    @RequestMapping(value = "/om/autoship/checkByMemberId")
    @ResponseBody
    public ResponseData checkByMemberId(final HttpServletRequest request, final Long memberId) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(autoshipOrderService.checkMemberAutoship(iRequest, memberId));
    }

    /**
     * autoShip下载CSV文件.
     * 
     * @param request
     *            HttpServletRequest请求
     * @param licenseFile
     *            LicenseFile查询参数
     * @param response
     *            HttpServletResponse请求
     * @return 错误信息
     * @throws CommLicenseFileException
     */
    @RequestMapping(value = "/om/autoship/downcsv", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String downLoadCSV(HttpServletRequest request, LicenseFile licenseFile, HttpServletResponse response)
            throws CommLicenseFileException {
        IRequest iRequest = createRequestContext(request);
        if (licenseFile.getBatchNumber().isEmpty()) {
            return null;
        }
         return licenseFileService.downloadCsvFile(request, iRequest, response, licenseFile);
    }

    /**
     * autoShip上传解析CSV文件.
     * 
     * @param request
     *            HttpServletRequest请求
     * @return 提示信息
     * @throws CommLicenseFileException
     * @throws EmailException 
     */
    @RequestMapping(value = "/om/autoship/upcsv", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object upLoadCSV(HttpServletRequest request) throws CommLicenseFileException, EmailException {
        IRequest iRequest = createRequestContext(request);
        Locale locale = RequestContextUtils.getLocale(request);
        Uploader uploader = UploaderFactory.getMutiUploader();
        uploader.setMaxFileNum(OrderConstants.CSV_UPLOADNUMBER_1);
        uploader.setFilter(new ContentTypeFilter() {
            @Override
            public boolean isAccept(String orginalName, String contentType) {
                /*if (orginalName.endsWith(OrderConstants.CSV_TYPE)) {
                    return true;
                }
                return false;*/
                return true;
            }
        });
        uploader.setController(new StandardController() {
            @Override
            public String getFileDir(HttpServletRequest request, String orginalName) {
                File f = new File(OrderConstants.CSV_FILE_UPLOADPATH);
                if (!f.exists()) {
                    f.mkdirs();
                }
                return f.getAbsolutePath() + File.separator;
            }

            @Override
            public String urlFix(HttpServletRequest request, File file) {
                return file.getAbsolutePath();
            }
        });
        uploader.init(request);
        // 得到上传好的文件
        List<FileInfo> fileInfos = uploader.upload();
        String status = uploader.getStatus();
        if (!status.equals(UpConstants.SUCCESS)) {
            FileInfo fileInfo = fileInfos.get(0);
            // 特别的,抛出文件类型错误异常
            if (UpConstants.FILE_DISALLOWD_ERROR.equals(fileInfo.getStatus())) {
                String msg = messageSource.getMessage(CommLicenseFileException.CSV_UPLOAD_ERROR_FILE_TYPE_ERROR, null,
                        locale);
                return "<script>window.parent.uploadMessage('" + msg + "');</script>";
            }
            String msg = messageSource.getMessage(CommLicenseFileException.CSV_UPLOAD_ERROR_FILE_ERROR, null, locale);
            return "<script>window.parent.uploadMessage('" + msg + "');</script>";
        }
        FileInfo fileInfo = fileInfos.get(0);
        fileInfo.getFile();
        return licenseFileService.uploadCsvFile(request, iRequest, fileInfo);
    }

    /**
     * 创建autoship订单.
     * 
     * @param request
     *            请求上下文
     * @return 页面跳转
     * @throws OrderException
     *             抛出订单异常
     */
    @RequestMapping(value = "om/om_autoship_create.html")
    public ModelAndView createAutoship(final HttpServletRequest request,AutoshipOrder autoshipOrder) throws OrderException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(TO_AUTOSHIP_CREATE);
        IRequest requestContext = createRequestContext(request);
        Map<String, Object> basicData = autoshipOrderService.getBasicDataForCreate(requestContext, autoshipOrder);
        mav.addAllObjects(basicData);
        return mav;
    }
}

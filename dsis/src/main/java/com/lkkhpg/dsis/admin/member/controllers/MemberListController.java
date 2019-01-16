/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lkkhpg.dsis.admin.member.util.ReadExcelUtil;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherMember;
import com.lkkhpg.dsis.common.service.ICommMemberListService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 导入会员列表Contorller.
 * 
 * @author mclin
 */
@Controller
public class MemberListController extends BaseController {

    @Autowired
    private ICommMemberListService commMemberListService;

    @Autowired
    private IVoucherService voucherService;

    /**
     * 导入会员会员列表.
     * 
     * @param request
     *            请求上下文
     * @return map 响应数据 会员统一异常
     */
    @RequestMapping(value = "/mm/member/validate")
    public ResponseData importMembers(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Locale locale = RequestContextUtils.getLocale(request);
        ResponseData data = new ResponseData();

        String idType = null; // 页面传过来ID的类型，是优惠券ID还是消息ID
        Long mentionId = null; // 页面传过来ID的值，优惠券ID或消息ID

        Long marketId = null;

        // 判断是否为文件上传
        boolean flag = ServletFileUpload.isMultipartContent(request);
        if (flag) {
            // 创建文件上传的工厂对象
            FileItemFactory factory = new DiskFileItemFactory();
            // 创建ServletFileUpload对象，并将工厂对象作为参数传入
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 使用上传对象从请求对象中解析出提交的所有表单元素
            List<FileItem> lstForms = null;
            try {
                lstForms = upload.parseRequest(request);
            } catch (Exception e1) {
                data.setSuccess(false);
                data.setMessage(getMessageSource().getMessage(MemberConstants.MEMLIST_OTH_ERROR, null, locale));
                return data;
            }
            // 遍历表单元素集合，实现取出表单元素和上传文件
            // 遍历所有表单元素
            for (FileItem fileItem : lstForms) {
                // 判断每一个表单元素是否是普通表单
                if (!fileItem.isFormField()) {
                    String fileName = fileItem.getName();
                    fileName = FilenameUtils.getName(fileName);
                    String fileExt = FilenameUtils.getExtension(fileName);
                    String fileType = fileItem.getContentType();
                    // 判断上传的文件类型
                    if (!Arrays.asList(MemberConstants.MEMLIST_CONTENT_TYPE).contains(fileType)
                            || !Arrays.asList(MemberConstants.MEMLIST_CONTENT_EXT).contains(fileExt)) {
                        data.setSuccess(false);
                        data.setMessage(
                                getMessageSource().getMessage(MemberConstants.MEMLIST_Q_TYPE_DENIED, null, locale));
                        return data;
                    }
                    long fileSize = fileItem.getSize();
                    if (fileSize == 0) {
                        // 上传文件为空
                        data.setSuccess(false);
                        data.setMessage(
                                getMessageSource().getMessage(MemberConstants.MEMLIST_F_IS_EMPTY, null, locale));
                        return data;
                    }
                    if (fileSize > MemberConstants.MEMLIST_FILE_SIZE) {
                        // 上传文件大小超过2M
                        data.setSuccess(false);
                        data.setMessage(
                                getMessageSource().getMessage(MemberConstants.MEMLIST_F_EXCEED_SIZE, null, locale));
                        return data;
                    }
                    try {
                        ReadExcelUtil excelUtil = new ReadExcelUtil();
                        List<MemberList> memList = excelUtil.readExcelBySheet(fileItem, fileExt);
                        // 验证会员
                        if (memList == null || memList.isEmpty()) {
                            data.setSuccess(false);
                            data.setMessage(
                                    getMessageSource().getMessage(MemberConstants.MEMLIST_F_IS_EMPTY, null, locale));
                            return data;
                        }
                        // 会员code不为空或空字符串
                        for (MemberList memberList : memList) {
                            String memberCode = memberList.getMemberCode();
                            if (memberCode == null || "".equals(memberCode)) {
                                data.setSuccess(false);
                                data.setMessage(getMessageSource().getMessage(MemberConstants.MEMLIST_F_IS_EMPTY, null,
                                        locale));
                                return data;
                            } else if (MemberConstants.MSG_ERROR_VALUE_OF_THE_CELL_MUST_BE_MEMBERCODE
                                    .equals(memberCode)) {
                                data.setSuccess(false);
                                data.setMessage(getMessageSource().getMessage(
                                        MemberConstants.MSG_ERROR_VALUE_OF_THE_CELL_MUST_BE_MEMBERCODE, null, locale));
                                return data;
                            }
                        }
                        // 导入会员code不能重复
                        for (int i = 0; i < memList.size() - 1; i++) {
                            String memberCode = memList.get(i).getMemberCode();
                            for (int j = i + 1; j < memList.size(); j++) {
                                String validateMemberCode = memList.get(j).getMemberCode();
                                if (memberCode.equals(validateMemberCode)) {
                                    data.setSuccess(false);
                                    data.setMessage(getMessageSource().getMessage(MemberConstants.MEMBER_CODE_REPEAT,
                                            null, locale));
                                    return data;
                                }
                            }
                        }

                        // 将导入的信息保存到临时表中(优惠券)
                        Long groupId = commMemberListService.validate(requestContext, memList, idType, mentionId,
                                marketId);
                        List<Long> list = new ArrayList<Long>();
                        list.add(groupId);
                        return new ResponseData(list);
                    } catch (Exception e2) {
                        data.setSuccess(false);
                        data.setMessage(getMessageSource()
                                .getMessage(MemberConstants.MSG_ERROR_THE_FILE_IS_NOT_STANDARD, null, locale));
                        return data;
                    }
                } else {
                    if (MemberConstants.MEMLIST_NAME_IDTYPE.equals(fileItem.getFieldName())) {
                        idType = fileItem.getString();
                    }
                    /*
                     * if (MemberConstants.MEMLIST_NAME_IDVALUE.equals(fileItem.
                     * getFieldName())) { try { mentionId =
                     * Long.parseLong(fileItem.getString().trim()); } catch
                     * (NumberFormatException e) { data.setSuccess(false);
                     * data.setMessage(
                     * getMessageSource().getMessage(MemberConstants.
                     * MEMLIST_OTH_ERROR, null, locale)); return data; } }
                     */
                    if (MemberConstants.MEMLIST_NAME_IDMARKET.equals(fileItem.getFieldName())) {
                        marketId = Long.parseLong(fileItem.getString().trim());
                    }
                }
            }
        }
        // 上传的不是文件
        data.setSuccess(false);
        data.setMessage(getMessageSource().getMessage(MemberConstants.MEMLIST_Q_NOT_FILE, null, locale));
        return data;
    }

    /**
     * 会员导入.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     *            批id
     * @param groupType
     *            单据类型
     * @param mentionId
     *            单据id
     * @param maxMember 
     * @return 批id
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/mm/member/import")
    public ResponseData importMembers(HttpServletRequest request, Long groupId, String groupType, Long mentionId,
            BigDecimal maxMember) throws CommSystemProfileException {
        Long impottId = commMemberListService.importMembers(createRequestContext(request), groupId, groupType,
                mentionId, maxMember);
        List<Long> list = new ArrayList<Long>();
        list.add(impottId);
        return new ResponseData(list);
    }

    /**
     * 查询会员临时表数据.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     *            批id
     * @return 会员列表
     */
    @RequestMapping(value = "/mm/member/queryImport")
    public ResponseData queryImport(HttpServletRequest request, Long groupId) {
        return new ResponseData(commMemberListService.queryImport(createRequestContext(request), groupId));
    }

    /**
     * 删除会员导入临时表.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     *            批id
     * @param groupType
     *            单据类型
     * @param mentionId
     *            单据id
     * @return 批id
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/mm/member/deleteImport")
    public ResponseData deleteImport(HttpServletRequest request, Long groupId, String groupType, Long mentionId)
            throws CommSystemProfileException {
        Long importId = commMemberListService.deleteImport(createRequestContext(request), groupId, mentionId);
        List<Long> list = new ArrayList<Long>();
        list.add(importId);
        return new ResponseData(list);
    }

    /**
     * 
     * @param request 统一上下文
     * @param pdmVoucherMemberList 导入会员集
     * @return 返回会员信息集
     */
    @RequestMapping(value = "/admin/promotion/pdm_voucher_edit")
    public ResponseData pageSkip(HttpServletRequest request, @RequestBody List<PdmVoucherMember> pdmVoucherMemberList) {
        List<PdmVoucherMember> pvmList = new ArrayList<>();
        for (PdmVoucherMember pdmVoucherMember : pdmVoucherMemberList) {
            PdmVoucherMember pvm = voucherService.queryMember(createRequestContext(request), pdmVoucherMember.getMemberId());
            pvmList.add(pvm);
        }
        return new ResponseData(pvmList);
    }
}

/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.common.service.IManualMessageService;
import com.lkkhpg.dsis.common.system.dto.SiteMessageRead;
import com.lkkhpg.dsis.common.system.dto.SysMessageRead;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.exception.TokenException;

/**
 * 消息中心.
 * 
 * @author Zhaoqi
 *
 */
@Controller
public class MemberMessageController extends BaseController {

    public static final String ACCONT_MESSAGEDETAILS = "/account/account-messagedetails";

    @Autowired
    private IManualMessageService manualMessageService;

    /**
     * mws查询我的消息.
     * 
     * @param request
     *            统一上下文
     * @param status
     *            消息标记
     * @param sort
     * @param page
     * @param pagesize
     * @return 返回消息的list
     */
    @RequestMapping(value = "/account/message")
    public ResponseData queryMessageList(HttpServletRequest request, String status, String sort,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        Long accountId = createRequestContext(request).getAccountId();
        SiteMessageRead siteMessageRead = new SiteMessageRead();
        if ("".equals(status)) {
            siteMessageRead.setAccountId(accountId);
        } else {
            siteMessageRead.setAccountId(accountId);
            siteMessageRead.setStatus(status);
        }
        siteMessageRead.setSort(sort);
        List<SiteMessageRead> list = manualMessageService.queryMessageMws(siteMessageRead, page, pagesize);
        return new ResponseData(list);
    }

    /**
     * mws删除我的消息.
     * 
     * @param request
     *            统一上下文
     * @param msMessage
     *            复选框id集合
     * @return 返回受影响行
     * @throws TokenException
     */
    @RequestMapping(value = "/account/deleteMessage")
    @ResponseBody
    public ResponseData deleteMessage(HttpServletRequest request, @RequestBody List<SiteMessageRead> msMessage)
            throws TokenException {
        checkToken(request, msMessage);
        
        SysMessageRead sr = new SysMessageRead();
        for (SiteMessageRead siteMessageRead : msMessage) {
            sr.setReadId(siteMessageRead.getReadId());
            manualMessageService.delMessage(sr);
        }
        return new ResponseData();
    }

    /**
     * mws删除当前行消息.
     * 
     * @param request
     *            统一上下文
     * @param readId
     *            当前行Id
     * @return 返回受影响行
     */
    @RequestMapping(value = "/account/deleteCurrent")
    public ResponseData deleteCurrent(HttpServletRequest request, Long readId) {
        SysMessageRead smd = new SysMessageRead();
        smd.setReadId(readId);
        manualMessageService.delMessage(smd);
        return new ResponseData();
    }

    /**
     * mws查看消息内容.
     * 
     * @param request
     *            统一上下文
     * @param readId
     * @param sort
     * @return 返回消息内容
     */
    @RequestMapping(value = "/account/message/detail")
    public ModelAndView queryMessageDetail(HttpServletRequest request, Long readId, String sort) {
        ModelAndView result = new ModelAndView();
        SysMessageRead messageRead = new SysMessageRead();
        messageRead.setReadId(readId);
        List<SiteMessageRead> list = manualMessageService.readMessage(messageRead);
        String content = "";
        Date publishDate = null;
        String userName = "";
        String subject = "";
        for (SiteMessageRead siteMessageRead : list) {
            content = siteMessageRead.getContent();
            publishDate = siteMessageRead.getPublishDate();
            userName = siteMessageRead.getUserName();
            subject = siteMessageRead.getSubject();
        }
        result.setViewName(getViewPath() + ACCONT_MESSAGEDETAILS);
        result.addObject("content", content);
        result.addObject("readId", readId);
        result.addObject("publishDate", publishDate);
        result.addObject("userName", userName);
        result.addObject("subject", subject);
        result.addObject("sort", sort);
        return result;
    }

    @RequestMapping(value = "/account/message/queryNext")
    public ModelAndView queryNext(HttpServletRequest request, String date, String sort) throws ParseException {
        ModelAndView result = new ModelAndView();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long accountId = createRequestContext(request).getAccountId();
        SiteMessageRead siteMessageRead = new SiteMessageRead();
        if ("".equals(sort)) {
            siteMessageRead.setPublishDate(format.parse(date));
            siteMessageRead.setAccountId(accountId);
            siteMessageRead.setSort(sort);
        } else {
            siteMessageRead.setPublishDate(format.parse(date));
            siteMessageRead.setAccountId(accountId);
        }
        SiteMessageRead sr = manualMessageService.queryNext(siteMessageRead);
        // if (sr == null) {
        // result.setViewName(getViewPath() + ACCONT_MESSAGEDETAILS);
        // result.addObject("sortEmpty", "empty");
        // return result;
        // }
        Long readId = sr.getReadId();
        SysMessageRead messageRead = new SysMessageRead();
        messageRead.setReadId(readId);
        List<SiteMessageRead> list = manualMessageService.readMessage(messageRead);
        String content = "";
        Date publishDate = null;
        String userName = "";
        String subject = "";
        for (SiteMessageRead smr : list) {
            content = smr.getContent();
            publishDate = smr.getPublishDate();
            userName = smr.getUserName();
            subject = smr.getSubject();
        }
        result.setViewName(getViewPath() + ACCONT_MESSAGEDETAILS);
        result.addObject("content", content);
        result.addObject("readId", readId);
        result.addObject("publishDate", publishDate);
        result.addObject("userName", userName);
        result.addObject("subject", subject);
        return result;
    }
}

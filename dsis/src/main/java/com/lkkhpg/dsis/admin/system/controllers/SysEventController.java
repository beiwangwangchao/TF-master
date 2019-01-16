/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISysEventService;
import com.lkkhpg.dsis.common.system.dto.SysEvent;
import com.lkkhpg.dsis.common.system.dto.SysEventAssign;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 事件管理控制层.
 * 
 * @author wangc
 *
 */
@Controller
public class SysEventController extends BaseController {

    @Autowired
    private ISysEventService sysEventService;

    /**
     * 查询事件.
     * 
     * @param request
     *            请求上下文
     * @param event
     *            事件dto
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return 事件
     */
    @RequestMapping(value = "em/event/queryEvents")
    @ResponseBody
    public ResponseData queryEvents(HttpServletRequest request, SysEvent event, int page, int pagesize) {
        return new ResponseData(sysEventService.queryEvents(createRequestContext(request), event, page, pagesize));
    }

    /**
     * 根据id查询事件.
     * 
     * @param request
     *            请求上下问
     * @param eventId
     *            事件id
     * @return 事件dto
     */
    @RequestMapping(value = "em/event/getEventById")
    @ResponseBody
    public ResponseData getEventById(HttpServletRequest request, Long eventId) {
        List<SysEvent> events = new ArrayList<SysEvent>();
        SysEvent eventById = sysEventService.getEventById(createRequestContext(request), eventId);
        events.add(eventById);
        return new ResponseData(events);
    }

    /**
     * 根据事件id查询会员.
     * 
     * @param request
     *            请求上下问
     * @param eventId
     *            事件id
     * @param page
     *            页数
     * @param pagesize
     *            每页记录数
     * @return 会员列表
     */
    @RequestMapping(value = "em/event/getMemsByEventId")
    @ResponseBody
    public ResponseData getMemsByEventId(HttpServletRequest request, Long eventId) {
        return new ResponseData(
                sysEventService.getMemsByEventId(createRequestContext(request), eventId));
    }

    /**
     * 保存事件.
     * 
     * @param request
     *            请求上下文
     * @param events
     *            事件dto
     * @param result
     *            校验结果
     * @return 事件列表
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/em/event/save")
    @ResponseBody
    public ResponseData saveSysEvent(HttpServletRequest request, @RequestBody List<SysEvent> events,
            BindingResult result) throws CommSystemProfileException {
        getValidator().validate(events, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysEventService.saveSysEvent(requestContext, events));
    }

    @RequestMapping(value = "/em/event/addMembers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addMembers(@RequestBody List<SysEventAssign> assgins, Long eventId, Long maxMember,
            BindingResult result, HttpServletRequest request) throws CommSystemProfileException {
        getValidator().validate(assgins, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysEventService.addMembers(requestContext, assgins, eventId, maxMember));
    }

    /**
     * 删除活动分配会员.
     * 
     * @param request
     *            请求上下文
     * @param eventAssigns
     *            活动分配列表
     * @param result
     * @return 分配列表
     */
    @RequestMapping(value = "/em/event/removeMember")
    @ResponseBody
    public ResponseData deleteMember(HttpServletRequest request, @RequestBody List<SysEventAssign> eventAssigns,
            BindingResult result) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysEventService.deleteMember(requestContext, eventAssigns));
    }

    /**
     * 失效活动.
     * 
     * @param request
     *            请求上下文
     * @param events
     *            活动dto
     * @param result
     *            校验结果
     * @return 活动列表
     * @throws CommSystemProfileException
     *             系统基础异常
     */
    @RequestMapping(value = "/em/event/invalid")
    @ResponseBody
    public ResponseData invalidEvent(HttpServletRequest request, @RequestBody List<SysEvent> events,
            BindingResult result) throws CommSystemProfileException {
        getValidator().validate(events, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysEventService.invalidEvent(requestContext, events));
    }

    /**
     * 发布活动.
     * 
     * @param request
     *            请求上下文
     * @param events
     *            活动dto
     * @param result
     *            校验结果
     * @return 活动列表
     * @throws CommSystemProfileException
     *             系统基础异常
     */
    @RequestMapping(value = "/em/event/publish")
    @ResponseBody
    public ResponseData publishEvent(HttpServletRequest request, @RequestBody List<SysEvent> events,
            BindingResult result) throws CommSystemProfileException {
        getValidator().validate(events, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysEventService.publishEvent(requestContext, events));
    }

    /**
     * 关闭活动（撤销发布）.
     * 
     * @param request
     *            请求上下文
     * @param eventId
     *            活动id
     * @return 活动id
     */
    @RequestMapping(value = "/em/event/close")
    @ResponseBody
    public ResponseData closeEvent(HttpServletRequest request, Long eventId) {
        IRequest requestContext = createRequestContext(request);
        List<Long> list = new ArrayList<>();
        Long closeEvent = sysEventService.closeEvent(requestContext, eventId);
        list.add(closeEvent);
        return new ResponseData(list);
    }
    
    /**
     * 获取分配了此会员的、状态为“有效”的、类型为“旅游”的活动的数量.
     * 
     * @param request
     *            请求上下文
     * @param memberId 会员ID
     * @return 旅游次数
     */
    @RequestMapping(value = "em/event/getTravelCountByMember")
    @ResponseBody
    public ResponseData getTravelCountByMember(HttpServletRequest request, Long memberId) {
        List<Long> list = new ArrayList<>();
        Long travelCount = sysEventService.getTravelCountByMember(createRequestContext(request), memberId);
        list.add(travelCount);
        return new ResponseData(list);
    }

}

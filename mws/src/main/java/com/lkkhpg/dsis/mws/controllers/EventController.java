/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISysEventService;
import com.lkkhpg.dsis.common.system.dto.SysEvent;
import com.lkkhpg.dsis.mws.constant.ProductConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * mws事件controller.
 * 
 * @author ZhangYang
 *
 */
@Controller
public class EventController extends BaseController {

    @Autowired
    private ISysEventService sysEventService;
    
    /**
     * 获取事件列表
     * @param request
     * @param event
     * @param page
     * @param pagesize
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/event/queryEvent")
    public ResponseData queryEvents(HttpServletRequest request, SysEvent event,
            @RequestParam(defaultValue = ProductConstants.PAGE) int page,
            @RequestParam(defaultValue = ProductConstants.PAGESIZE) int pagesize) {
         return new ResponseData(sysEventService.queryEventsForWms(createRequestContext(request),
         event, page, pagesize));
    }
    
    /**
     * 获取当前事件下该用户参与人数
     * @param request
     * @param eventId
     * @param memberId
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/event/queryAttendanceByEventIdAndMemberId" ,method = RequestMethod.POST)
    public ResponseData queryAttendanceByEventIdAndMemberId(HttpServletRequest request,Long eventId,String memberId) {
        List <Long> list=new ArrayList<Long>();
        Long attendance=sysEventService.getAttendanceByMemberIdAndEventId(createRequestContext(request), eventId, memberId);
        list.add(attendance);
        return new ResponseData(list);
    }
    
    /**
     * 会员退出该活动
     * @param request
     *          统一上下文
     * @param eventId
     *          退出事件Id
     * @param memberId
     *          退出会员Id
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/event/quitEvent" ,method = RequestMethod.POST)
    public ResponseData quitEvent(HttpServletRequest request,Long eventId,Long memberId) {
        try {
            sysEventService.cancelEventForWms(createRequestContext(request), eventId, memberId);
        } catch (CommSystemProfileException e) {
            ResponseData data= new ResponseData(false);
            data.setMessage(e.getMessage());
             return data;
        }
        return new ResponseData(true);
    }
    
    /**
     * 会员加入活动
     * @param request
     *          统一上下文
     * @param eventId
     *          加入事件Id
     * @param memberId
     *          加入会员Id
     * @param paticipants
     *          加入人数
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/event/addMember" ,method = RequestMethod.POST)
    public ResponseData addMember(HttpServletRequest request,Long eventId,Long memberId,BigDecimal paticipants) {
        try {
            sysEventService.joinEvent(createRequestContext(request), eventId, memberId, paticipants);
        } catch (CommSystemProfileException e) {
           ResponseData data= new ResponseData(false);
           data.setMessage(e.getMessage());
            return data;
        }
        return new ResponseData(true);
    }
    

}

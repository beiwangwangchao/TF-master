/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.system.dto.SysEvent;
import com.lkkhpg.dsis.common.system.dto.SysEventAssign;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 事件管理Service.
 * 
 * @author wangc
 */
public interface ISysEventService extends ProxySelf<ISysEventService> {

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
     * @return 事件列表
     */
    List<SysEvent> queryEvents(IRequest request, SysEvent event, int page, int pagesize);

    /**
     * 查询事件(wms使用).
     * 
     * @param request
     *            请求上下文
     * @param event
     *            事件dto
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return 事件列表
     */
    List<SysEvent> queryEventsForWms(IRequest request, SysEvent event, int page, int pagesize);

    /**
     * wms取消活动.
     * 
     * @param request
     *            请求上下文
     * @param eventId
     *            事件id
     * @param memberId
     *            会员id
     * @return 会员
     */
    Long cancelEventForWms(IRequest request, Long eventId, Long memberId) throws CommSystemProfileException;

    /**
     * 保存事件.
     * 
     * @param request
     *            请求上下文
     * @param events
     *            事件列表
     * @return 事件列表
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    List<SysEvent> saveSysEvent(IRequest request, @StdWho List<SysEvent> events) throws CommSystemProfileException;

    /**
     * 根据id查询事件.
     * 
     * @param request
     *            请求上下文
     * @param eventId
     *            事件id
     * @return 事件dto
     */
    SysEvent getEventById(IRequest request, Long eventId);

    /**
     * 根据事件id查询会员.
     * 
     * @param request
     *            请求上下文
     * @param eventId
     *            事件id
     * @return 会员列表
     */
    List<SysEventAssign> getMemsByEventId(IRequest request, Long eventId);

    /**
     * 删除分配会员.
     * 
     * @param requestContext
     *            请求上下文
     * @param eventAssigns
     *            活动分配
     * @return 分配会员列表
     */
    List<SysEventAssign> deleteMember(IRequest requestContext, List<SysEventAssign> eventAssigns);

    /**
     * wms会员加入活动接口.
     * 
     * @param request
     *            请求上下文
     * @param eventId
     *            活动id
     * @param memberId
     *            会员id
     * @param paticipants
     *            参与人数
     * @return 会员id
     * @throws CommSystemProfileException
     *             基础系统异常
     */
    Long joinEvent(IRequest request, Long eventId, Long memberId, BigDecimal paticipants)
            throws CommSystemProfileException;

    /**
     * 失效活动.
     * 
     * @param request
     *            请求上下文
     * @param events
     *            活动dto
     * @return 活动列表
     * @throws CommSystemProfileException
     *             系统基础异常
     */
    List<SysEvent> invalidEvent(IRequest request, List<SysEvent> events) throws CommSystemProfileException;

    /**
     * 发布活动.
     * 
     * @param request
     *            请求上下文
     * @param events
     *            活动dto
     * @return 活动列表
     * @throws CommSystemProfileException
     *             系统基础异常
     */
    List<SysEvent> publishEvent(IRequest request, List<SysEvent> events) throws CommSystemProfileException;

    /**
     * 关闭活动.
     * 
     * @param request
     *            请求上下文
     * @param eventId
     *            活动id
     * @return 是否关闭
     */
    Long closeEvent(IRequest request, Long eventId);

    /**
     * 
     * @param request
     *            请求上下文
     * @param eventId
     *            事件ID
     * @param memberId
     *            memberID
     * @return
     */
    Long getAttendanceByMemberIdAndEventId(IRequest request, Long eventId, String memberId);

    /**
     * 验证活动导入的会员列表.
     * 
     * @param request
     *            请求上下文
     * @param list
     *            会员列表List
     * @param idType
     *            单据类型
     * @param mentionId
     *            单据id
     * @param marketId
     *            市场id
     * @return List 会员列表List
     * @throws CommMemberException
     *             会员统一异常
     */
    Long validate(IRequest request, List<MemberList> list, String idType, Long mentionId, Long marketId)
            throws CommMemberException;

    /**
     * 导入会员.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     *            批id
     * @return 批id
     * @throws CommSystemProfileException
     *             系统配置统一异常.
     */
    Long importMemebers(IRequest request, Long groupId, Long mentionId, BigDecimal maxMember)
            throws CommSystemProfileException;

    /**
     * 新增会员.
     * 
     * @param request
     *            请求上下文
     * @param assgins
     *            会员列表
     * @return 会员列表
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    List<SysEventAssign> addMembers(IRequest request, List<SysEventAssign> assigns, Long eventId, Long maxMember)
            throws CommSystemProfileException;

    /**
     * 获取分配了此会员的、状态为“有效”的、类型为“旅游”的活动的数量.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员ID
     * @return 旅游次数
     */
    Long getTravelCountByMember(IRequest request, Long memberId);
}

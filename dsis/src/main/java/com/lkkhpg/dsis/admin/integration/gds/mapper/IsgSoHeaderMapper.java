/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgSoHeader;

/**
 * 小批量同步销售资料.
 * 
 * @author wuyichu
 */
public interface IsgSoHeaderMapper {
    int deleteByPrimaryKey(Long interfaceId);

    int insert(IsgSoHeader isgSoHeader);

    int insertSelective(IsgSoHeader isgSoHeader);

    IsgSoHeader selectByPrimaryKey(Long interfaceId);

    int updateByPrimaryKeySelective(IsgSoHeader isgSoHeader);

    int updateByPrimaryKey(IsgSoHeader isgSoHeader);

    /**
     * 根据通知号以及订单标号修改处理状态.
     * 
     * @param adviseNo
     *            通知号
     * @param soNo
     *            订单编号
     * @param ProccessStatus
     *            处理状态
     * @param message
     *            消息
     * @param proccessTime
     *            处理时间
     * @return 处理条数.
     */
    int updateProccessStatusByAdviseNoAndSONO(@Param("adviseNo") String adviseNo, @Param("soNo") String soNo,
            @Param("processStatus") String ProccessStatus, @Param("processMessage") String message,
            @Param("processDate") Date proccessTime);

    /**
     * 根据订单编号统计发送的次数.
     * 
     * @param soNo
     *            订单编号
     * @return 发送的次数
     */
    Long countBySoNo(@Param("soNo") String soNo);

    List<IsgSoHeader> selectByParams(Map<String, Object> params);
}
/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgSoLine;

/**
 * 小批量同步销售行资料.
 * 
 * @author wuyichu
 */
public interface IsgSoLineMapper {
    int deleteByPrimaryKey(Long interfaceDetailId);

    int insert(IsgSoLine isgSoLine);

    int insertSelective(IsgSoLine isgSoLine);

    IsgSoLine selectByPrimaryKey(Long interfaceDetailId);

    int updateByPrimaryKeySelective(IsgSoLine isgSoLine);

    int updateByPrimaryKey(IsgSoLine isgSoLine);

    /**
     * 根据通知号以及订单编码修改处理状态.
     * 
     * @param adviseNo
     *            通知号
     * @param soNo
     *            订单编码
     * @param status
     *            状态
     * @param message
     *            消息
     * @param processTime
     *            处理时间
     * @return 修改行数.
     */
    int updateProccessStatusByAdviseNoAndSONO(@Param("adviseNo") String adviseNo, @Param("soNo") String soNo,
            @Param("status") String status, @Param("message") String message, @Param("processTime") Date processTime);

    List<IsgSoLine> selectByParams(Map<String, Object> params);
}
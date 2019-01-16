package com.lkkhpg.dsis.common.discount.mapper;

import com.lkkhpg.dsis.common.discount.dto.DiscountTrxHeadDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.util.List;

/**
 * Project: pos2
 * Package: com.lkkhpg.dsis.common.discount.mapper
 * User: 11816
 * Date: 2018/1/16
 * Time: 15:00
 */

public interface DiscountTrxHeadMapper {

    /*------------------------insert-----------------------*/

    /**
     * 通用insert
     *
     * @param discountTrxHeadDto
     * @return
     */
    int insert(DiscountTrxHeadDto discountTrxHeadDto);

    /*------------------------delete-----------------------*/

    /**
     * 删除折扣行
     *
     * @param discountTrxHeadDto
     * @return
     */
    int deleteDiscount(DiscountTrxHeadDto discountTrxHeadDto);

    /**
     * 根据主键进行删除
     *
     * @param discountTrxHeadDto
     * @return
     */
    int deleteByPrimaryKey(DiscountTrxHeadDto discountTrxHeadDto);

    /*------------------------updaate-----------------------*/

    /**
     * 根据主键对选中的部分进行更新
     *
     * @param discountTrxHeadDto
     * @return
     */
    int updateByPrimaryKeySelective(DiscountTrxHeadDto discountTrxHeadDto);

    /**
     * 根据主键进行更新
     *
     * @param discountTrxHeadDto
     * @return
     */
    int updateByPrimaryKey(DiscountTrxHeadDto discountTrxHeadDto);

    /*------------------------select-----------------------*/

    /**
     * 根据主键进行查询
     *
     * @param discountTrxHeadId
     * @return
     */
    DiscountTrxHeadDto selectByPrimaryKey(@Param("discountTrxHeadId") Long discountTrxHeadId);

    /**
     * 获取自增id
     *
     * @return
     */
    Long getDiscountTrxHeadId();

    /**
     * 查询折扣事务处理头
     * @param discountTrxHeadDto
     * @return
     */
    List<DiscountTrxHeadDto> queryDiscountTrxHead(DiscountTrxHeadDto discountTrxHeadDto);

    /**
     * 基础查询
     * @param discountTrxHeadDto
     * @param list
     * @return
     */
    List<DiscountTrxHeadDto> queryDiscountTrxHead(@Param("discountTrxHeadDto") DiscountTrxHeadDto discountTrxHeadDto,
                                                  @Param("list") List<String> list);

    /**
     * 更新状态
     * @param discountTrxHeadId
     * @return
     */
    int updateDiscountTrxStatus(@Param("discountTrxHeadId") Long discountTrxHeadId);

    /**
     * 取消
     * @param orderNumber
     * @return
     */
    int canclDiscountTrxStatus(@Param("orderNumber") String  orderNumber);

    /*
    * 根据会员id和订单号查询对应订的折扣/优惠
    * 根据会员id和订单号查询对应订的折扣/优惠
    * */
    DiscountTrxHeadDto queryByMemOrd(@Param("memberId") Long memberId,@Param("orderNumber")String orderNumber);
}

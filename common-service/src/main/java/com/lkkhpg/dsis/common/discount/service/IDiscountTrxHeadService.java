package com.lkkhpg.dsis.common.discount.service;

import com.lkkhpg.dsis.common.discount.dto.DiscountTrxHeadDto;
import com.lkkhpg.dsis.common.discount.exception.DiscountException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.util.List;

public interface IDiscountTrxHeadService extends ProxySelf<IDiscountTrxHeadService> {
    /**
     * 基础插入
     * @param request
     * @param discountTrxHeadDto
     * @return
     */
    public long insert(IRequest request, DiscountTrxHeadDto discountTrxHeadDto);

       /**
     * 保存
     * @param request
     * @param discountTrxHeadDto
     * @return
     * @throws DiscountException
     */
    List<DiscountTrxHeadDto> createUPTrx(IRequest request, @StdWho DiscountTrxHeadDto discountTrxHeadDto) throws DiscountException;

    /**
     * 取消
     * @param orderNumber
     * @return
     * @throws DiscountException
     */
    void canclTrx(String orderNumber) ;

    /**
     * 查询折扣事务头荷航
     * @param request
     * @param discountTrxHeadDto
     * @param page
     * @param pagesize
     * @return
     */
    List<DiscountTrxHeadDto> queryDiscountTrxHead(IRequest request, DiscountTrxHeadDto discountTrxHeadDto, int page, int pagesize);

    /**
     * 获取折扣事务头
     * @param request
     * @param discountTrxHeadId
     * @return
     * @throws DiscountException
     */
    DiscountTrxHeadDto getDiscountTrx(IRequest request, Long discountTrxHeadId) throws DiscountException;

    /**
     * 提交事务（不保存）
     * @param requestContext
     * @param discountTrxHeadDto
     * @return
     * @throws DiscountException
     */
    List<DiscountTrxHeadDto> submitDiscountTrx(IRequest requestContext, DiscountTrxHeadDto discountTrxHeadDto) throws DiscountException;

    /**
     * 保存并提交事务
     * @param request
     * @param discountTrxHeadDto
     * @return
     * @throws DiscountException
     */
    List<DiscountTrxHeadDto> submitDiscountTransaction(IRequest request, DiscountTrxHeadDto discountTrxHeadDto) throws DiscountException;

    /*
    * 根据会员id和订单号查询对应订的折扣/优惠
    * update up 15750 at 2018/04/19
    * */
    DiscountTrxHeadDto queryByMemOrd(Long memberId,String orderNumbe);
}

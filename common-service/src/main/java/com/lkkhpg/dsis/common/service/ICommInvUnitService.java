/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;
import com.lkkhpg.dsis.common.product.dto.InvUnitOfMeasureB;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 单位 Service.
 * 
 * @author houmin
 *
 */
public interface ICommInvUnitService extends ProxySelf<ICommInvUnitService> {

    /**
     * 查询单位信息.
     * 
     * @param request
     *            统一 上下文
     * @param invUnitOfMeasureB
     *            单位对象信息
     * @param pageNum
     *            页码
     * @param pageSize
     *            每页显示条数
     * @return 单位信息集合
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    List<InvUnitOfMeasureB> queryUnitNamesOfUomManage(IRequest request, InvUnitOfMeasureB invUnitOfMeasureB,
            int pageNum, int pageSize) throws CommSystemProfileException;

    /**
     * 保存单位信息.
     * 
     * @param request
     *            统一上下文
     * @param invUnitOfMeasureBs
     *            待保存的单位信息
     * @return 保存后返回的单位信息
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    List<InvUnitOfMeasureB> batchUpdateUoms(IRequest request, @StdWho List<InvUnitOfMeasureB> invUnitOfMeasureBs)
            throws CommSystemProfileException;

    /**
     * 保存商品单位转换信息.
     * 
     * @param request
     *            统一上下文
     * @param invUnitConverts
     *            单位转换信息
     * @return 保存后返回的单位转换信息
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    List<InvUnitConvert> batchUpdateInvUnitConverts(IRequest request, @StdWho List<InvUnitConvert> invUnitConverts)
            throws CommSystemProfileException;

    /**
     * 根据itemId查询商品对应单位转换信息.
     * 
     * @param request
     *            统一上下文
     * @param itemId
     *            商品Id
     * @param pageNum
     *            页码
     * @param pageSize
     *            每页显示记录数
     * @return 单位转换关系信息
     * @throws CommSystemProfileException
     *             系统配置异常
     */
    List<InvUnitConvert> queryInvUnitConverts(IRequest request, Long itemId, int pageNum, int pageSize)
            throws CommSystemProfileException;

    /**
     * 通过编码code查找编码.
     * 
     * @param uomCode
     *            单位编码
     * @return 单位
     */
    InvUnitOfMeasureB queryInvUnitConvertByCode(String uomCode);
}

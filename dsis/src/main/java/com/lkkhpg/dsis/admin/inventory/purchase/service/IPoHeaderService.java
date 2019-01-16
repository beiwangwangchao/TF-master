/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.purchase.service;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.purchase.dto.PoHeader;
import com.lkkhpg.dsis.common.inventory.purchase.dto.PoLine;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 订单采购service.
 * @author HuangJiaJing
 *
 */
public interface IPoHeaderService {
    
    
    /**
     * 查询采购单.
     * @param request
     *              请求上下文
     * @param poHeader
     *          采购单dto
     * @param page
     *          页数
     * @param pagesize
     *          每页显示行数
     * @return 采购单集合
     */
    List<PoHeader> queryPoHeader(IRequest request, PoHeader poHeader, int page, int pagesize);
    
    /**
     * 保存采购单和商品行表.
     * @param request
     *              请求上下文
     * @param poHeaders
     *          采购单集合
     * @return 采购单集合
     */
    List<PoHeader> savePoHeader(IRequest request, @StdWho List<PoHeader> poHeaders);


    /**
     * 提交采购单和商品行表.
     * 将采购的商品入库
     * @param request
     *              请求上下文
     * @param poHeaders
     *          采购单集合
     * @return 采购单集合
     */
    List<PoHeader> submitPoHeader(IRequest request, @StdWho List<PoHeader> poHeaders);
    
    /**
     * 查询行表数据.
     * @param request
     *              请求上下文
     * @param poLine
     *          商品表dto
     * @param page
     *          页数
     * @param pagesize
     *          每页显示行数
     * @return 商品行表集合
     */
    List<PoLine> queryPoLine(IRequest request, PoLine poLine, int page, int pagesize);
    
    /**
     * 查询行表数据(不分页).
     * @param request
     *              请求上下文
     * @param poLine
     *          商品表dto
     * @param page
     *          页数
     * @param pagesize
     *          每页显示行数
     * @return 商品行表集合
     */
    List<PoLine> queryPoLineNoPage(IRequest request, PoLine poLine);
    
}

/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lkkhpg.dsis.admin.product.exception.ItemException;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.product.dto.*;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 商品/商品包资料查询接口.
 * 
 * @author linxixin
 */
public interface IItemService extends ProxySelf<IItemService> {

    /**
     * 查询商品、商品包.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Dto
     * @param page
     *            页数
     * @param pageSize
     *            记录条数
     * @return 商品列表
     */
    List<InvItem> queryItemAndPrice(IRequest request, InvItem item, int page, int pageSize);

    /**
     * 采购订单商品表查询.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Dto
     * @param page
     *            页数
     * @param pagesize
     *            记录条数
     * @return 商品列表
     */
    List<InvItem> queryPoHeaderItem(IRequest request, InvItem item, int page, int pagesize);

    /**
     * 根据类别查询商品.
     * 
     * @param request
     *            请求上下文
     * @param category
     *            商品类别
     * @param page
     *            页数
     * @param pageSize
     *            记录条数
     * @return 商品列表
     */
    List<InvItem> queryProductByCategory(IRequest request, InvCategoryB category, int page, int pageSize);

    /**
     * 未分配类别商品.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Id
     * @param page
     *            页数
     * @param pageSize
     *            记录条数
     * @return 商品列表
     */
    List<InvItem> queryItemFilterByCategory(IRequest request, InvItem item, int page, int pageSize);

    /**
     * 查询底层的商品类别作为下拉框选择.
     * 
     * @param request
     *            请求上下文
     * @param itemType
     *            商品类型
     * @return 商品类别记录
     */
    List<InvCategoryB> queryCategoryLov(IRequest request, String itemType);

    /**
     * 根据itemId查询商品包.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param mode
     *            单据创建类型
     * @return 商品记录
     */
    InvItem queryItemByItemId(IRequest request, Long itemId, String mode);

    /**
     * 根据itemId查询商品包语言.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品多语言记录
     */
    List<InvItemTl> queryItemTlsByItemId(IRequest request, Long itemId);

    /**
     * 根据itemId查询商品包可用性.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品可用性列表
     */
    List<InvItemAvailability> queryItemAvailabilityByItemId(IRequest request, Long itemId);

    /**
     * 根据itemId查询商品包参数语言.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品参数多语言
     * @throws UnsupportedEncodingException
     *             字符格式转换异常
     */
    List<ItemAttrDto> queryItemAttrTlsByItemId(IRequest request, Long itemId) throws UnsupportedEncodingException;

    /**
     * 根据itemId查询商品包组织分配.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 商品包组织分配
     */
    List<InvItemAssign> queryItemAssignByItemId(IRequest request, Long itemId);

    /**
     * 根据itemId查询包含商品.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 包含商品列表
     */
    List<InvItemHierarchy> queryItemHierarchyByItemId(IRequest request, Long itemId);

    /**
     * 保存商品(包).
     * 
     * @param request
     *            请求上下文
     * @param itemAllDto
     *            商品保存参数
     * @throws ItemException
     *             商品统一异常
     * @throws UnsupportedEncodingException
     *             编码异常
     */
    void savePackageAll(IRequest request, @StdWho ItemAllDto itemAllDto)
            throws ItemException, UnsupportedEncodingException;

    /**
     * 保存商品信息.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品dto
     * @throws ItemException
     *             商品统一异常
     */
    void saveItem(IRequest request, @StdWho InvItem item) throws ItemException;

    /**
     * 查询销售区域.
     * 
     * @param request
     *            请求上下文
     * @param org
     *            销售区域Dto
     * @param page
     *            页数
     * @param pageSize
     *            记录条数
     * @return 销售区域列表
     */
    List<SpmSalesOrganization> queryOrgs(IRequest request, SpmSalesOrganization org, int page, int pageSize);

    /**
     * 查询价格属性.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param currency
     *            货币
     * @param uomCode
     *            单位
     * @param salesOrgId
     *            销售组织ID
     * @return 价格行记录
     */
    List<PriceListDetail> queryPriceByItemId(IRequest request, Long itemId, String currency, String uomCode,
            Long salesOrgId);

    /**
     * 查询价格属性.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * 
     * @return 价格行记录
     */
    List<PriceListDetail> queryPriceByItem(IRequest request, Long itemId);

    /**
     * 查询库存组织lov.
     * 
     * @param request
     *            请求上下文
     * @param spmInvOrganization
     *            库存组织Dto
     * @return 库存组织记录
     */
    List<SpmInvOrganization> querySpmInvOrganization(IRequest request, SpmInvOrganization spmInvOrganization);

    /**
     * 撤销发布.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @return 是否发布成功
     * @throws ItemException
     *             商品统一异常
     */
    Long unPublishItem(IRequest request, Long itemId) throws ItemException;

    /**
     * 查询库存属性.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param organizationId
     *            库存组织Id
     * @return 库存组织Dto
     */
    InvItemPropertyDto queryPropertyType(IRequest request, Long itemId, Long organizationId);

    /**
     * 获取新增记录的itemId.
     * 
     * @param request
     *            请求上下文
     * @return 商品Id
     */
    Long getNextItemId(IRequest request);

    /**
     * 查询商品，限制市场.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Dto
     * @param page
     *            页码
     * @param pageSize
     *            每页记录数
     * @return 商品列表
     */
    List<InvItem> queryItem(IRequest request, InvItem item, int page, int pageSize);
    
    /**
     * 
     * 查询商品，不限制市场.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Dto
     * @param page
     *            页码
     * @param pageSize
     *            每页记录数
     * @return 商品列表
     */
    List<InvItem> queryItemForOrder(IRequest request, InvItem item, int page, int pageSize);

    /**
     * 查询库存组织下的商品.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Dto
     * @param page
     *            页码
     * @param pageSize
     *            每页记录数
     * @return 商品列表
     */
    List<InvItem> queryItemWithOrg(IRequest request, InvItem item, int page, int pageSize);

    /**
     * 根据条件查出商品信息.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品Dto
     * @param invOrgId
     *            库存组织ID
     * @param trxType
     *            调整类型
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @return 商品列表
     */
    List<InvItem> queryItemBs(IRequest request, InvItem item, Long invOrgId, String trxType, int page, int pagesize);

    /**
     * 查询单位下拉框.
     * 
     * @param request
     *            请求上下文
     * @param invUom
     *            单位Dto
     * @param page
     *            页数
     * @param pagesize
     *            分页条数
     * @return 单位列表
     */
    List<InvUnitOfMeasureB> queryUomCodes(IRequest request, InvUnitOfMeasureB invUom, int page, int pagesize);

    /**
     * 查询可以移库的商品.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品DTO
     * @param outOrg
     *            转出库存组织
     * @param inOrg
     *            转入库存组织
     * @param page
     *            页码
     * @param pagesize
     *            每页大小
     * @return 商品list
     */
    List<InvItem> queryTransferItems(IRequest request, InvItem item, Long outOrg, Long inOrg, int page, int pagesize);

    /**
     * 根据多个商品是否在指定库存组织下.
     * 
     * @param request
     *            请求上下文
     * @param itemIds
     *            商品Id集合
     * @param orgId
     *            库存Id
     * @return 库存id
     * @throws ItemException
     *             商品统一异常
     */
    Long checkOrgIncludeItems(IRequest request, List<Long> itemIds, Long orgId) throws ItemException;

    /**
     * 查询商品是否在指定库存内.
     * 
     * @param requestContext
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param invOrgIds
     *            库存Id集合
     * @return 商品id
     * @throws ItemException
     *             商品统一异常
     */
    Long checkIncludeItemOrgs(IRequest requestContext, Long itemId, List<Long> invOrgIds) throws ItemException;

    /**
     * 查询计算库存为'计算此商品'的商品.
     * 
     * @param request
     *            请求上下文
     * @param item
     *            商品
     * @param itemId
     *            商品Id
     * @param page
     *            页码
     * @param pageSize
     *            页面记录数
     * @return 商品列表
     */
    List<InvItem> queryItemsByCountStock(IRequest request, InvItem item, Long itemId, int page, int pageSize);

    /**
     * 查询所有底层商品类别.
     * 
     * @param request
     *            请求上下文
     * @return 商品类别列表
     */
    List<InvCategoryB> queryAllBottomCategory(IRequest request);

    /**
     * 发布商品.
     * 
     * @param request
     *            请求上下文
     * @param itemAllDto
     *            商品传输dto
     * @return 商品ID
     * @throws UnsupportedEncodingException
     *             编码异常
     * @throws ItemException
     *             商品异常
     */
    Long publishItem(IRequest request, @StdWho ItemAllDto itemAllDto)
            throws ItemException, UnsupportedEncodingException;

    /**
     * 保存价格行，头.
     * 
     * @param request
     *            请求上下文
     * 
     * @param itemPriceDetails
     *            价格行dto
     * @param itemId
     *            商品id
     * @param uomCode
     *            单位code
     */
    void savePriceListDetail(IRequest request, @StdWho List<PriceListDetail> itemPriceDetails, Long itemId,
            String uomCode);

    /**
     * 保存商品类别.
     * 
     * @param request
     *            请求上下文
     * @param invItemCategory
     *            商品类别
     */
    void saveInvItemCategory(IRequest request, @StdWho InvItemCategory invItemCategory);

    /**
     * 保存商品有效性.
     * 
     * @param request
     *            请求上下文
     * @param invItemAvailability
     *            商品有效性
     */
    void saveInvItemAvalibility(IRequest request, @StdWho InvItemAvailability invItemAvailability);

    /**
     * 保存库存组织.
     * 
     * @param itemAssigns
     *            库存组织
     * @param itemInfo
     *            商品信息
     */
    void saveItemAssign(IRequest request, @StdWho List<InvItemAssign> itemAssigns, InvItem itemInfo);

    /**
     * 保存参数信息.
     * 
     * @param invItemAttrTl
     *            商品参数
     */
    void addItemAttrTls(IRequest request, @StdWho InvItemAttrTl invItemAttrTl);

    /**
     * 保存参数信息.
     * 
     * @param invItemAttrTl
     *            商品参数
     */
    void updateItemAttrTls(IRequest request, @StdWho InvItemAttrTl invItemAttrTl);

    /**
     * 检查库存组织是否启用
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品Id
     * @param invOrgId
     *            库存组织Id
     * @return 是否可用
     */
    Boolean checkInvOrgEnable(IRequest request, Long itemId, Long invOrgId);
    
    List<InvItemAttrTl> getWhetherHide(IRequest request, InvItemAttrTl invItemAttrTl);


    /**
     * 查询商品价格
     *
     * @param request
     *            请求上下文
     * @param itemId
     *            商品ID
     * @param currency
     *            货币
     * @param uomCode
     *            单位
     * @param salesOrgId
     *            销售组织ID
     * @param orderType
     *            订单类型
     * @param itemSalestype
     *            销售类型
     * @return 订单使用的商品价格
     */
    OrderItemPrice queryItemPriceForOrder(IRequest request, Long itemId, String currency, String uomCode,
                                          Long salesOrgId, String orderType, String itemSalestype);
}

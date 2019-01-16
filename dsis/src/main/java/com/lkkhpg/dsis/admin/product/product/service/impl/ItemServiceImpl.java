/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.product.category.service.IInvCategoryService;
import com.lkkhpg.dsis.admin.product.exception.ItemException;
import com.lkkhpg.dsis.admin.product.product.service.IItemService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmInvOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmOrgDefinationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.ProductConstants;
import com.lkkhpg.dsis.common.product.dto.*;
import com.lkkhpg.dsis.common.product.mapper.*;
import com.lkkhpg.dsis.common.service.ICommItemService;
import com.lkkhpg.dsis.common.service.IOrgParamService;
import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssign;
import com.lkkhpg.dsis.common.system.mapper.SysUserRoleAssignMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 商品资料管理实现类.
 * 
 * @author linxixin
 */
@Service
@Transactional
public class ItemServiceImpl implements IItemService {

    private final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ICommItemService commItemService;

    @Autowired
    private InvItemMapper invItemMapper;

    @Autowired
    private InvItemTlMapper invItemTlMapper;

    @Autowired
    private SysUserRoleAssignMapper sysUserRoleAssignMapper;

    @Autowired
    private InvItemAvailabilityMapper invItemAvailabilityMapper;

    @Autowired
    private InvItemAttrTlMapper invItemAttrTlMapper;

    @Autowired
    private InvItemAssignMapper invItemAssignMapper;

    @Autowired
    private InvItemHierarchyMapper invItemHierarchyMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private SpmOrgDefinationMapper spmOrgDefinationMapper;

    @Autowired
    private InvItemCategoryMapper invItemCategoryMapper;

    @Autowired
    private PriceListDetailMapper priceListDetailMapper;

    @Autowired
    private SpmInvOrganizationMapper spmInvOrganizationMapper;

    @Autowired
    private IInvCategoryService invCategoryService;

    @Autowired
    private InvItemPropertyMapper invItemPropertyMapper;

    @Autowired
    private PriceListMapper priceListMapper;

    @Autowired
    private InvUnitOfMeasureBMapper invUnitOfMeasureBMapper;

    @Autowired
    private InvCategoryBMapper invCategoryBMapper;

    @Autowired
    private IOrgParamService orgParamService;

    @Autowired
    private PriceChangeHistoryMapper priceChangeHistoryMapper;

    @Override
    public List<InvItem> queryItemAndPrice(IRequest request, InvItem item, int page, int pageSize) {
        // 进行分页
        PageHelper.startPage(page, pageSize);
        List<InvItem> items = invItemMapper.queryItemAndPrice(item);
        return items;
    }

    @Override
    public List<InvItem> queryProductByCategory(IRequest request, InvCategoryB category, int page, int pageSize) {
        // 进行分页
        PageHelper.startPage(page, pageSize);
        List<InvItem> items = invItemMapper.queryProductByCategory(category);
        return items;
    }

    @Override
    public List<InvItem> queryItemFilterByCategory(IRequest request, InvItem item, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<InvItem> items = invItemMapper.queryItemFilterByCategory(item);
        return items;
    }

    @Override
    public List<InvCategoryB> queryCategoryLov(IRequest request, String itemType) {
        return invCategoryService.queryBottomCategory(request, itemType);
    }

    @Override
    public InvItem queryItemByItemId(IRequest request, Long itemId, String mode) {
        if (DTOStatus.UPDATE.equals(mode)) {
            // 商品类别
            InvItem itemById = invItemMapper.getItemByIdByOrg(itemId);
            //InvItem itemById = invItemMapper.getItemById(itemId);

            List<InvCategoryB> invCategoryList = itemById.getInvCategory();
            StringBuilder sb = new StringBuilder();
            StringBuilder sbIds = new StringBuilder();
            if (invCategoryList != null && !invCategoryList.isEmpty()) {
                for (InvCategoryB invCategory : invCategoryList) {
                    /*modified by furong.tang 单选商品类别*/
                    sb.append(invCategory.getCategoryName());
                    sbIds.append(invCategory.getCategoryId());
                    //sb.append(invCategory.getCategoryName()).append(";");
                    //sbIds.append(invCategory.getCategoryId()).append(";");
                }
            }
            itemById.setCategoryDesc(sb.toString());
            itemById.setCategoryIdList(sbIds.toString());
            // 可兑换行
            itemById.setRedeemFlagField(itemById.getRedeemFlag());
            // 套装类型
            itemById.setStarterAidField(itemById.getStarterAid());
            // 有效性
            List<InvItemAvailability> itemAvailabilities = invItemAvailabilityMapper.getItemAvailabilities(itemId);
            if (itemAvailabilities != null) {
                for (InvItemAvailability availability : itemAvailabilities) {
                    if (availability != null) {
                        String functionArea = availability.getFunctionArea();
                        if (ProductConstants.FUNCTION_AREA_IN_STORE.equals(functionArea)) {
                            itemById.setInStore(ProductConstants.YES);
                        } else if (ProductConstants.FUNCTION_AREA_DISTRIBUTOR_WEB.equals(functionArea)) {
                            itemById.setAgencyWeb(ProductConstants.YES);
                        } else if (ProductConstants.FUNCTION_AREA_DISTRIBUTOR_APP.equals(functionArea)) {
                            itemById.setAgencyApp(ProductConstants.YES);
                        } else if (ProductConstants.FUNCTION_AREA_AUTOSHIP.equals(functionArea)) {
                            itemById.setAutoDeliver(ProductConstants.YES);
                        }
                    }
                }
            }
            return itemById;
        } else {
            InvItem addItem = new InvItem();
            addItem.setItemId(itemId);
            addItem.setPublishStatus(ProductConstants.PUBLISH_STATUS_UNPUBLISHED);
            addItem.setRedeemFlagField(ProductConstants.NO);
            addItem.setMinOrderQuantity(new BigDecimal(1));
            return addItem;
        }
    }

    @Override
    public List<InvItemTl> queryItemTlsByItemId(IRequest request, Long itemId) {
        return invItemTlMapper.getItemTlsByItemId(itemId);
    }

    @Override
    public List<InvItemAvailability> queryItemAvailabilityByItemId(IRequest request, Long itemId) {
        return invItemAvailabilityMapper.getItemAvailabilities(itemId);
    }

    @Override
    public List<ItemAttrDto> queryItemAttrTlsByItemId(IRequest request, Long itemId)
            throws UnsupportedEncodingException {
        List<ItemAttrDto> result = new ArrayList<ItemAttrDto>();
        List<InvItemAttrTl> itemAttrs = invItemAttrTlMapper.getItemAttrTlsByItemId(itemId);
        Map<String, List<InvItemAttrTl>> attrMap = new HashMap<String, List<InvItemAttrTl>>();
        for (InvItemAttrTl itemAttr : itemAttrs) {
            String lang = itemAttr.getLang();
            if (attrMap.containsKey(lang)) {
                List<InvItemAttrTl> list = attrMap.get(lang);
                list.add(itemAttr);
            } else {
                List<InvItemAttrTl> list = new ArrayList<InvItemAttrTl>();
                list.add(itemAttr);
                attrMap.put(lang, list);
            }
        }

        Set<String> langs = attrMap.keySet();
        for (String lang : langs) {
            List<InvItemAttrTl> attrList = attrMap.get(lang);
            ItemAttrDto attr = new ItemAttrDto();
            attr.setLanguage(lang);
            attr.setItemId(itemId);
            for (InvItemAttrTl itemAttr : attrList) {
                String attrName = itemAttr.getName();
                if(null == itemAttr.getContent() || "".equals(itemAttr.getContent())){
                    //result.add(attr);
                }else{
                    String content = new String(itemAttr.getContent(), ProductConstants.ENCODING_UTF8);
                    switch (attrName) {
                    case ProductConstants.ATTR_DESCRIPTION:
                        attr.setPackageIntroduce(content);
                        break;
                    case ProductConstants.ATTR_SPECIFICATION:
                        attr.setStandardParam(content);
                        break;
    //                case ProductConstants.ATTR_AFTER_SERVICE:
    //                    attr.setAfterService(content);
    //                    break;
                    case ProductConstants.ATTR_USER_DIRECTIONS:
                        attr.setUserGuide(content);
                        break;
    //                case ProductConstants.ATTR_NOTICE_ITEM:
    //                    attr.setNotes(content);
    //                    break;
                    case ProductConstants.ATTR_DOSE:
                        //attr.setSaveMethod(content);
                        attr.setDose(content);
                        break;
                    default:
                        break;
                    }
                }
            }
            result.add(attr);
        }
        return result;
    }

    @Override
    public List<InvItemAssign> queryItemAssignByItemId(IRequest request, Long itemId) {
        return invItemAssignMapper.getItemAssignsByItemId(itemId);
    }

    @Override
    public List<InvItemHierarchy> queryItemHierarchyByItemId(IRequest request, Long itemId) {
        return invItemHierarchyMapper.getHierarchyByItemId(itemId);
    }

    @Override
    public List<SpmSalesOrganization> queryOrgs(IRequest request, SpmSalesOrganization org, int page, int pageSize) {
        // 进行分页
        PageHelper.startPage(page, pageSize);
        List<SpmSalesOrganization> orgs = spmSalesOrganizationMapper.queryBySalesOrganization(org);
        return orgs;
    }

    @Override
    public List<PriceListDetail> queryPriceByItemId(IRequest request, Long itemId, String currency, String uomCode,
            Long salesOrgId) {
        return priceListDetailMapper.selectByItemId(itemId, currency, uomCode, salesOrgId);
    }

    @Override
    public List<PriceListDetail> queryPriceByItem(IRequest request, Long itemId) {
        List<PriceListDetail> priceListDetails= new ArrayList<>();
        /*查出当前商品的价格行信息*/
        List<PriceListDetail> priceListDetailList=priceListDetailMapper.selectByItem(itemId);
        /*查询当前用户所关联的销售组织 */
        SysUserRoleAssign sur = new SysUserRoleAssign();
        sur.setUserId(request.getAttribute("userId"));
        sur.setRoleId(request.getRoleId());
        List<SysUserRoleAssign> sysUserRoleAssignList=sysUserRoleAssignMapper.selectOrgAssign(sur);
        List<Long> userSales=new ArrayList<>();
        for(SysUserRoleAssign sysUserRoleAssign:sysUserRoleAssignList){
            /*将用户所关联的销售组织id放到userSales中*/
            if(sysUserRoleAssign.getAssignType().equals(SysUserRoleAssign.SALES_ORG_ASSIGN_TYPE)){
                userSales.add(sysUserRoleAssign.getAssignValue());
            }
        }

        for(PriceListDetail priceListDetail:priceListDetailList){
            for(Long userSale :userSales){
                if(priceListDetail.getSalesOrgId().equals(userSale)){
                    priceListDetails.add(priceListDetail);
                    break;
                }
            }

        }
        return priceListDetails;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePackageAll(IRequest request, @StdWho ItemAllDto itemAllDto)
            throws ItemException, UnsupportedEncodingException {
        Long itemId = itemAllDto.getItemId();
        // 商品信息
        InvItem itemInfo = itemAllDto.getItemInfo();
        checkItem(itemInfo);
        String itemType = itemInfo.getItemType();
        itemInfo.setItemId(itemId);
        // 页面中form可能重复字段
        itemInfo.setRedeemFlag(itemInfo.getRedeemFlagField());
        itemInfo.setStarterAid(itemInfo.getStarterAidField());
        itemInfo.setOrderFlag(ProductConstants.YES);
        itemInfo.setInventoryFlag(ProductConstants.YES);
        // 校验商品日期
        checkItemDate(itemInfo);
        self().saveItem(request, itemInfo);
        // 商品类别
        saveItemCategory(request, itemId, itemInfo);
        // 商品有效性,只做新增和删除操作
        saveItemAvalibility(request, itemId, itemInfo);

        /* 校验包含商品 ，库存组织 */
        List<InvItemAssign> itemAssignsAll = itemAllDto.getItemAssignsAll();
        if (itemAssignsAll == null || itemAssignsAll.isEmpty()) {
            throw new ItemException(ItemException.ITEM_HAS_NO_ASSIGN_INFO, new Object[] {});
        }
        List<InvItemAssign> itemAssigns = itemAllDto.getItemAssigns();
        if (ProductConstants.ITEM_TYPE_PACKAGE.equals(itemType)) {
            List<InvItemHierarchy> itemIncludesAll = itemAllDto.getItemIncludesAll();
            checkItemInclude(itemIncludesAll);
            List<InvItemHierarchy> itemIncludes = itemAllDto.getItemIncludes();
            saveItemInculde(itemIncludes, itemId);
            /* 校验包含商品,库存组织 */
            checkIncludeItemAssign(request, itemAssigns, itemIncludes);
        } else {
            /* 校验计算库存商品,库存组织 */
            checkItemAssign(request, itemAssigns, itemInfo);
        }
        self().saveItemAssign(request, itemAssigns, itemInfo);

        /* 价格 */
        List<PriceListDetail> itemPriceDetailAll = itemAllDto.getItemPriceDetailAll();
        List<PriceListDetail> itemPriceDetailAllType = itemAllDto.getItemPriceDetailAllType();

        if (itemPriceDetailAll == null || itemPriceDetailAll.isEmpty()) {
            //如果未设置初始价格  而且价格信息行为空  则报商品没有价格数据
            if(itemAllDto.getFirstPrice()== null) {
                throw new ItemException(ItemException.ITEM_HAS_NO_PRICE_INFO, new Object[]{});
            }
        }

        /*如果参数信息新增而没有维护操作  则报错*/

        // 日期
        /*不运行商品时间地点的校验
        *  @author miaoyifan
        * @time 2017/12/19
        * */
       //validatePriceAll(itemPriceDetailAll, itemInfo);
        // 兑换积分
        validatePriceRedeemType(itemPriceDetailAllType, itemInfo.getRedeemFlag());
        List<PriceListDetail> itemPriceDetails = itemAllDto.getItemPriceDetail();
        if (itemPriceDetails != null) {
            validatePrice(itemPriceDetails, itemInfo.getRedeemFlagField());
            /*modified by furong.tang 2018.2.9 15:02*/
            //无论什么情况都将是否计税设置为是，前台隐藏该属性的设置
            for(PriceListDetail priceListDetail : itemPriceDetails ){
                priceListDetail.setDisableTaxFlag("N");
            }
        }

        //设置商品初始价格
        BigDecimal fp=itemAllDto.getFirstPrice();
        Long salesOrgId =request.getAttribute("salesOrgId");
        /*查询该用户维护的销售组织 和 该用户所在市场下对应公司的销售组织   两者的交集*/
        List<Long> sOrgIds= spmOrgDefinationMapper.querySameCompanySalesOrgId(salesOrgId);

        if(fp != null){
            for(Long saleOrgId:sOrgIds){
                if(saleOrgId != null){
                    Long priceListIdByOrgId = priceListMapper.getPriceListIdByOrgId(saleOrgId);
                    PriceListDetail priceListDetail=new PriceListDetail();
                    if (priceListIdByOrgId == null) {
                        // 新增价格头
                        PriceList priceList = new PriceList();
                        priceList.setSalesOrgId(saleOrgId);
                        priceList.setName(itemId.toString());
                        priceList.setGlobalFlag(ProductConstants.YES);
                        priceListMapper.insertSelective(priceList);
                        Long priceListId = priceListMapper.getPriceListIdByOrgId(saleOrgId);
                        priceListDetail.setPriceListId(priceListId);
                    } else {
                        priceListDetail.setPriceListId(priceListIdByOrgId);
                    }
                    // 价格行
                    priceListDetail.setItemId(itemId);
                    priceListDetail.setEnabledFlag(ProductConstants.YES);
                    priceListDetail.setUomCode(itemInfo.getUomCode());
                    //设置价格为初始价格
                    priceListDetail.setUnitPrice(fp);
                    //设置价格类型为经销商价格
                    priceListDetail.setPriceType("DIS");
                    //设置币种为CNY   待修改!!!!!!!!!!!!!!!
                    String orgIdStr = saleOrgId.toString();
                    List<String> cur= orgParamService.getParamValues(request, "SPM.CURRENCY", 10003l, orgIdStr, "SALES");

                    priceListDetail.setCurrency(cur.get(0));
                    //设置默认为计税
                    priceListDetail.setDisableTaxFlag("N");
                    //设置createdBY
                    priceListDetail.setCreatedBy(request.getAttribute("createdBy"));
                    priceListDetail.setLastUpdatedBy(-1l);
                    //设置开始时间为当前系统时间
                    /*  @author miaoyifan
                     *  @time 2017/12/19*/
                    priceListDetail.setStartActiveDate(new Date());
                    priceListDetailMapper.insert(priceListDetail);

                }
            }
        }
        self().savePriceListDetail(request, itemPriceDetails, itemId, itemInfo.getUomCode());

        /* 商品参数 */
        List<ItemAttrDto> itemAttrsTlsAll = itemAllDto.getItemAttrsTlsAll();
        checkItemAttrsTls(itemAttrsTlsAll);
        List<ItemAttrDto> itemAttrsTls = itemAllDto.getItemAttrsTls();
//        if (itemAttrsTls != null) {
//            for(ItemAttrDto attrDto : itemAttrsTls){
//                // 若商品介绍是否隐藏没选且商品介绍内容为空
//                if(attrDto.getHideProductIntroduce()==null){
//                    if(attrDto.getPackageIntroduce()==null){
//                        throw new ItemException(ItemException.REPEAT_ITEM_ATTRS_PARAMETERS_NULL, new Object[] {});
//                    }
//                }
//            }
//            saveItemAttrTls(request, itemAttrsTls, itemId);
//        }
        saveItemAttrTls(request, itemAttrsTls, itemId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveItem(IRequest request, @StdWho InvItem itemInfo) throws ItemException {
        String mode = itemInfo.getMode();
        String countStockFlag = itemInfo.getCountStockFlag();
        if (ProductConstants.NO.equals(countStockFlag)) {
            itemInfo.setQuantityCountType(ProductConstants.NO);
        }
        if (DTOStatus.ADD.equals(mode)) {
            checkItemCode(itemInfo);
            invItemMapper.insert(itemInfo);
        } else {
            invItemMapper.updateByPrimaryKey(itemInfo);
        }
    }

    /**
     * 保存参数信息.
     * 
     * @param itemAttrsTls
     *            商品参数
     * @param itemId
     *            商品id
     * @throws UnsupportedEncodingException
     *             编码异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void saveItemAttrTls(IRequest request, List<ItemAttrDto> itemAttrsTls, Long itemId)
            throws UnsupportedEncodingException, ItemException {
        for (ItemAttrDto attrDto : itemAttrsTls) {
            String language = attrDto.getLanguage();
            // 商品介绍
            InvItemAttrTl introduce = new InvItemAttrTl();
            introduce.setLang(language);
            introduce.setItemId(itemId);
            introduce.setHide(attrDto.getHideProductIntroduce());
            String packageIntroduce = attrDto.getPackageIntroduce();
            introduce.setName(ProductConstants.ATTR_DESCRIPTION);
            if (packageIntroduce!=null) {
                introduce.setContent(packageIntroduce.getBytes(ProductConstants.ENCODING_UTF8));
            }

            // 规格参数
            InvItemAttrTl standard = new InvItemAttrTl();
            standard.setLang(language);
            standard.setItemId(itemId);
            standard.setHide(attrDto.getHideStandardParam());
            String standardParam = attrDto.getStandardParam();
            standard.setName(ProductConstants.ATTR_SPECIFICATION);
            if (standardParam!=null) {
                standard.setContent(standardParam.getBytes(ProductConstants.ENCODING_UTF8));
            }

            // 售后服务
            /*InvItemAttrTl service = new InvItemAttrTl();
            service.setLang(language);
            service.setItemId(itemId);
            String afterService = attrDto.getAfterService();
            service.setName(ProductConstants.ATTR_AFTER_SERVICE);
            service.setContent(afterService.getBytes(ProductConstants.ENCODING_UTF8));*/

            // 使用说明
            InvItemAttrTl guide = new InvItemAttrTl();
            guide.setLang(language);
            guide.setItemId(itemId);
            guide.setHide(attrDto.getHideUseInstruction());
            String userGuide = attrDto.getUserGuide();
            guide.setName(ProductConstants.ATTR_USER_DIRECTIONS);
            if (userGuide!=null) {
                guide.setContent(userGuide.getBytes(ProductConstants.ENCODING_UTF8));
            }

            // 注意事项
            /*InvItemAttrTl notice = new InvItemAttrTl();
            notice.setLang(language);
            notice.setItemId(itemId);
            String notes = attrDto.getNotes();
            notice.setName(ProductConstants.ATTR_NOTICE_ITEM);
            notice.setContent(notes.getBytes(ProductConstants.ENCODING_UTF8));*/

            // 保存方式
            InvItemAttrTl method = new InvItemAttrTl();
            method.setLang(language);
            method.setItemId(itemId);
            method.setHide(attrDto.getHideDose());
            String dose = attrDto.getDose();
            method.setName(ProductConstants.ATTR_DOSE);
            if (dose!=null) {
                method.setContent(dose.getBytes(ProductConstants.ENCODING_UTF8));
            }

            // 根据商品id,语言，参数名称更新商品参数表
            if (DTOStatus.UPDATE.equals(attrDto.get__status())) {
                self().updateItemAttrTls(request, introduce);
                self().updateItemAttrTls(request, standard);
                //self().updateItemAttrTls(request, service);
                self().updateItemAttrTls(request, guide);
                //self().updateItemAttrTls(request, notice);
                self().updateItemAttrTls(request, method);
            }
            if (DTOStatus.ADD.equals(attrDto.get__status())) {
                // 若商品介绍是否隐藏没选且商品介绍内容为空
                if(attrDto.getHideProductIntroduce()==null && attrDto.getPackageIntroduce()==null){
                    throw new ItemException(ItemException.REPEAT_ITEM_ATTRS_PARAMETERS_NULL, new Object[] {});
                }else {
                    self().addItemAttrTls(request, introduce);
                    self().addItemAttrTls(request, standard);
                    //self().addItemAttrTls(request, service);
                    self().addItemAttrTls(request, guide);
                    //self().addItemAttrTls(request, notice);
                    self().addItemAttrTls(request, method);
                }
            }
            if (DTOStatus.DELETE.equals(attrDto.get__status())) {
                InvItemAttrTl record = new InvItemAttrTl();
                record.setItemId(itemId);
                record.setLang(attrDto.getLanguage());
                invItemAttrTlMapper.deleteByLang(record);
            }
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addItemAttrTls(IRequest request, @StdWho InvItemAttrTl invItemAttrTl) {
        invItemAttrTlMapper.insert(invItemAttrTl);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateItemAttrTls(IRequest request, @StdWho InvItemAttrTl invItemAttrTl) {
        invItemAttrTlMapper.updateAttrContent(invItemAttrTl);
    }

    /**
     * 校验商品基本信息.
     * 
     * @param itemInfo
     *            商品dto
     * @throws ItemException
     *             商品基础异常
     */
    private void checkItem(InvItem itemInfo) throws ItemException {
        String itemType = itemInfo.getItemType();
        String countStockFlag = itemInfo.getCountStockFlag();
        BigDecimal quantityAlert = itemInfo.getQuantityAlert();
        if (ProductConstants.ITEM_TYPE_PACKAGE.equals(itemType)) {
            // 商品包
            if (ProductConstants.YES.equals(countStockFlag)) {
                String quantityCountType = itemInfo.getQuantityCountType();
                if (quantityCountType == null || "".equals(quantityCountType)) {
                    throw new ItemException(ItemException.COUNT_STOCK_TYPE_NULL_EXCEPTION, new Object[] {});
                }
                if (ProductConstants.ITEM_COUNT_TYPE_PACKG.equals(quantityCountType)) {
                    if (quantityAlert == null) {
                        throw new ItemException(ItemException.ITEM_QUANTITY_ALERT_IS_NULL, new Object[] {});
                    }
                }
            }
        } else if (ProductConstants.ITEM_TYPE_ITEM.equals(itemType)) {
            // 商品
            Long countItemId = itemInfo.getCountItemId();
            if (ProductConstants.ITEM_COUNT_STOCK_OTHER.equals(countStockFlag)) {
                if (countItemId == null) {
                    throw new ItemException(ItemException.COUNT_ITEM_ID_NULL_EXCEPTION, new Object[] {});
                }
            }
            if (ProductConstants.ITEM_COUNT_STOCK_OWN.equals(countStockFlag)) {
                if (quantityAlert == null) {
                    throw new ItemException(ItemException.ITEM_QUANTITY_ALERT_IS_NULL, new Object[] {});
                }
            }
        }
    }

    /**
     * 校验商品日期.
     * 
     * @param itemInfo
     *            商品dto
     * @throws ItemException
     *             商品基础异常
     */
    private void checkItemDate(InvItem itemInfo) throws ItemException {
        Date validateFrom = itemInfo.getValidateFrom();
        Date validateTo = itemInfo.getValidateTo();
        if (validateTo != null) {
            if (validateFrom.getTime() > validateTo.getTime()) {
                throw new ItemException(ItemException.ITEM_VALIDATE_DATE_ERROR, new Object[] {});
            }
        }

    }

    /**
     * 校验价格行日期.
     * 
     * (1)商品有效期是否覆盖价格有效期 (2)同一个销售区域有效区间不能重叠
     * 
     * @param itemPriceDetailAll
     *            商品价格列表
     * @param itemInfo
     *            商品基本信息
     * @throws ItemException
     *             商品基础异常
     */
    private void validatePriceAll(List<PriceListDetail> itemPriceDetailAll, InvItem itemInfo) throws ItemException {
        Map<String, List<PriceListDetail>> orgPriceMap = new HashMap<>();
        for (PriceListDetail priceListDetail : itemPriceDetailAll) {
            Long salesOrgId = priceListDetail.getSalesOrgId();
            String currency = priceListDetail.getCurrency();
            String key = currency + "_" + salesOrgId;
            if (orgPriceMap.containsKey(key)) {
                List<PriceListDetail> priceList = (List<PriceListDetail>) orgPriceMap.get(key);
                priceList.add(priceListDetail);
            } else {
                List<PriceListDetail> priceList = new ArrayList<PriceListDetail>();
                priceList.add(priceListDetail);
                orgPriceMap.put(key, priceList);
            }
        }

        // 商品有效日期从
        Long itemBeginDate = itemInfo.getValidateFrom().getTime();
        // 商品有效日期至
        Long itemEndDate = (itemInfo.getValidateTo() != null) ? itemInfo.getValidateTo().getTime() : null;

        for (String key : orgPriceMap.keySet()) {
            List<PriceListDetail> priceList = (List<PriceListDetail>) orgPriceMap.get(key);
            int maxEndDateCount = 0;
            for (PriceListDetail priceItem : priceList) {
                Long startActiveDate = priceItem.getStartActiveDate().getTime();
                Long endActiveDate = priceItem.getEndActiveDate() != null ? priceItem.getEndActiveDate().getTime()
                        : Long.MAX_VALUE;

                // (0)有效期至小于有效期从
                if (startActiveDate > endActiveDate) {
                    throw new ItemException(ItemException.END_DATE_EXCEED_START_DATE_EXCEPTION, new Object[] {});
                }

                // (1)商品有效期是否覆盖价格有效期
                if ((startActiveDate < itemBeginDate)
                        || (itemEndDate != null && endActiveDate != null && endActiveDate > itemEndDate)) {
                    throw new ItemException(ItemException.BEYOND_PRICE_TIME_EXCEPTION, new Object[] {});
                }

                // (2)同一个销售区域有效区间不能重叠
                if (endActiveDate == Long.MAX_VALUE) {
                    maxEndDateCount++;
                    if (maxEndDateCount > 1) {
                        throw new ItemException(ItemException.REPEAT_PRICE_TIME_EXCEPTION, new Object[] {});
                    }
                }
                for (PriceListDetail item : priceList) {
                    Long startDate = item.getStartActiveDate().getTime();
                    Long endDate = item.getEndActiveDate() != null ? item.getEndActiveDate().getTime() : Long.MAX_VALUE;
                    if (item == priceItem) {
                        continue;
                    }
                    if ((startActiveDate >= startDate && startActiveDate <= endDate)
                            || (endActiveDate >= startDate && endActiveDate <= endDate)) {
                        throw new ItemException(ItemException.REPEAT_PRICE_TIME_EXCEPTION, new Object[] {});
                    }
                }
            }
        }
    }

    /**
     * 校验兑换积分价格.
     * 
     * @param itemPriceDetailAllType
     *            商品价格行
     * @param redeemFlag
     *            商品可兑换性
     * @throws ItemException
     *             商品基础异常
     */
    private void validatePriceRedeemType(List<PriceListDetail> itemPriceDetailAllType, String redeemFlag)
            throws ItemException {
        for (PriceListDetail priceListDetail : itemPriceDetailAllType) {
            String priceType = priceListDetail.getPriceType();
            BigDecimal unitPrice = priceListDetail.getUnitPrice();
            if ((ProductConstants.PRICE_TYPE_REDEEM_POINT.equals(priceType))) {
                if ((unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) == 0)
                        && ProductConstants.YES.equals(redeemFlag)) {
                    throw new ItemException(ItemException.REDEEM_POINT_PRICE_IS_NULL_EXCEPTION, new Object[] {});
                }
                if (unitPrice != null && unitPrice.compareTo(BigDecimal.ZERO) != 0
                        && ProductConstants.NO.equals(redeemFlag)) {
                    throw new ItemException(ItemException.REDEEM_POINT_PRICE_IS_NOT_NULL_EXCEPTION, new Object[] {});
                }
            }
        }

    }

    /**
     * 校验价格行数据.
     * 
     * @param itemPriceDetails
     *            价格行dto
     * @param redeemFlag
     *            可兑换行
     * @throws ItemException
     *             商品基础异常
     */
    private void validatePrice(List<PriceListDetail> itemPriceDetails, String redeemFlag) throws ItemException {
        for (PriceListDetail priceListDetail : itemPriceDetails) {
            String priceType = priceListDetail.getPriceType();
            String currency = priceListDetail.getCurrency();
            BigDecimal unitPrice = priceListDetail.getUnitPrice();
            if (ProductConstants.PRICE_TYPE_REDEEM_POINT.equals(priceType)
                    || ProductConstants.PRICE_TYPE_PV.equals(priceType)) {
                if (StringUtils.isEmpty(currency)) {
                    throw new ItemException(ItemException.CURRENCY_IS_NOT_MATCH_PRICE_TYPE_EXCEPTION, new Object[] {});
                }
                if ((ProductConstants.PRICE_TYPE_REDEEM_POINT.equals(priceType))
                        && ProductConstants.YES.equals(redeemFlag)) {
                    if (unitPrice.compareTo(BigDecimal.ZERO) == 0) {
                        throw new ItemException(ItemException.REDEEM_POINT_PRICE_IS_NULL_EXCEPTION, new Object[] {});
                    }
                }
            }
            if (unitPrice != null && unitPrice.compareTo(BigDecimal.ZERO) == -1) {
                throw new ItemException(ItemException.UNIT_PRICE_IS_NEGATIVE_VALUE_EXCEPTION, new Object[] {});
            }
        }
    }

    /**
     * 商品（包）code不能重复.
     * 
     * @param itemInfo
     *            商品基本信息
     * 
     * @throws ItemException
     *             商品基础异常
     */
    private void checkItemCode(InvItem itemInfo) throws ItemException {
        Integer result = invItemMapper.queryItemByCodeAndId(itemInfo.getItemId(), itemInfo.getItemNumber());
        if (result > 0) {
            throw new ItemException(ItemException.REPEAT_ITEM_NUMBER_EXCEPTION, new Object[] {});
        }
    }

    /**
     * 校验商品参数.
     * 
     * @param itemAttrsTlsAll
     *            商品参数dto
     * @throws ItemException
     *             商品基础异常
     */
    private void checkItemAttrsTls(List<ItemAttrDto> itemAttrsTlsAll) throws ItemException {
        if (itemAttrsTlsAll != null) {
            for (int i = 0; i < itemAttrsTlsAll.size() - 1; i++) {
                String lang = itemAttrsTlsAll.get(i).getLanguage();
                for (int j = i + 1; j < itemAttrsTlsAll.size(); j++) {
                    String repeatLang = itemAttrsTlsAll.get(j).getLanguage();
                    if (repeatLang.equals(lang)) {
                        throw new ItemException(ItemException.REPEAT_ITEM_ATTRS_LANGUAGE_EXCEPTION, new Object[] {});
                    }
                }
            }
        }
    }

    /**
     * 校验包含商品.
     * 
     * @param itemIncludesAll
     *            包含商品dto
     * 
     * @throws ItemException
     *             商品基础异常
     */
    private void checkItemInclude(List<InvItemHierarchy> itemIncludesAll) throws ItemException {
        if (itemIncludesAll == null || itemIncludesAll.isEmpty()) {
            throw new ItemException(ItemException.PACKAGE_INCLUDE_NULL_EXCEPTION, new Object[] {});
        }
        for (int i = 0; i < itemIncludesAll.size() - 1; i++) {
            String itemNumber = itemIncludesAll.get(i).getItemNumber();
            for (int j = i + 1; j < itemIncludesAll.size(); j++) {
                String itemNumberRepeat = itemIncludesAll.get(j).getItemNumber();
                if (itemNumber.equals(itemNumberRepeat)) {
                    throw new ItemException(ItemException.REPEAT_INCLUDE_ITEM_EXCEPTION, new Object[] {});
                }
            }
        }
    }

    /**
     * 保存包含商品.
     * 
     * @param itemIncludes
     *            包含商品dto
     * @param itemId
     *            商品基础异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveItemInculde(List<InvItemHierarchy> itemIncludes, Long itemId) {
        if (itemIncludes != null) {
            for (InvItemHierarchy invItemHierarchy : itemIncludes) {
                if (DTOStatus.ADD.equals(invItemHierarchy.get__status())) {
                    invItemHierarchy.setParentItemId(itemId);
                    invItemHierarchyMapper.insertSelective(invItemHierarchy);
                } else if (DTOStatus.DELETE.equals(invItemHierarchy.get__status())) {
                    invItemHierarchyMapper.deleteByPrimaryKey(invItemHierarchy);
                } else if (DTOStatus.UPDATE.equals(invItemHierarchy.get__status())) {
                    invItemHierarchyMapper.updateByPrimaryKeySelective(invItemHierarchy);
                }
            }
        }
    }

    /**
     * 校验计算库存商品是否属于该库存.
     * 
     * @param request
     *            请求上下文
     * 
     * @param //itemAssignsAll
     *            商品库存dto
     * @throws ItemException
     *             商品基础异常
     */
    private void checkItemAssign(IRequest request, List<InvItemAssign> itemAssigns, InvItem itemInfo)
            throws ItemException {
        // 计算库存商品是否属于该库存，且库存为启用的
        Long countItemId = itemInfo.getCountItemId();
        if (countItemId == null) {
            return;
        }
        List<Long> invList = new ArrayList<Long>();
        if (itemAssigns != null) {
            for (InvItemAssign assign : itemAssigns) {
                Long invOrgId = assign.getAssignValue();
                String enabledFlag = assign.getEnabledFlag();
                if (ProductConstants.YES.equals(enabledFlag)) {
                    invList.add(invOrgId);
                }
            }
        }
        if (!invList.isEmpty()) {
            try {
                self().checkIncludeItemOrgs(request, countItemId, invList);
            } catch (ItemException e) {
                throw new ItemException(ItemException.COUNT_ITEMS_IS_NOT_ENABLE_TO_ORG_EXCEPTION, new Object[] {});
            }
        }
    }

    /**
     * 校验包含商品是否属于所有库存.
     * 
     * @param request
     *            请求上下文
     * @param itemAssigns
     *            商品库存
     * @param itemIncludes
     *            包含商品
     * @throws ItemException
     */
    private void checkIncludeItemAssign(IRequest request, List<InvItemAssign> itemAssigns,
            List<InvItemHierarchy> itemIncludes) throws ItemException {
        if (itemAssigns != null) {
            for (InvItemAssign assign : itemAssigns) {
                String enabledFlag = assign.getEnabledFlag();
                Long invOrgId = 0L;
                if (ProductConstants.YES.equals(enabledFlag)) {
                    invOrgId = assign.getAssignValue();
                }
                List<Long> itemIdList = new ArrayList<Long>();
                if (itemIncludes != null && !itemIncludes.isEmpty()) {
                    for (InvItemHierarchy include : itemIncludes) {
                        Long itemId = include.getItemId();
                        itemIdList.add(itemId);
                    }
                }
                if (invOrgId != 0 && itemIdList != null && !itemIdList.isEmpty()) {
                    try {
                        self().checkOrgIncludeItems(request, itemIdList, invOrgId);
                    } catch (ItemException e) {
                        throw new ItemException(ItemException.INCLUDE_ITEMS_IS_NOT_ENABLE_TO_ORG_EXCEPTION,
                                new Object[] {});
                    }
                }

            }
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveItemAssign(IRequest request, List<InvItemAssign> itemAssigns, InvItem itemInfo) {
        if (itemAssigns != null) {
            for (InvItemAssign invItemAssign : itemAssigns) {
                if (DTOStatus.ADD.equals(invItemAssign.get__status())) {
                    invItemAssign.setItemId(itemInfo.getItemId());
                    invItemAssign.setAssignType(ProductConstants.ASSIGN_TYPE_INV);
                    invItemAssignMapper.insertSelective(invItemAssign);
                    saveItemProperty(itemInfo, invItemAssign);
                }
                if (DTOStatus.UPDATE.equals(invItemAssign.get__status())) {
                    invItemAssignMapper.updateByPrimaryKeySelective(invItemAssign);
                    saveItemProperty(itemInfo, invItemAssign);
                }
                if (DTOStatus.DELETE.equals(invItemAssign.get__status())) {
                    invItemAssignMapper.deleteByPrimaryKey(invItemAssign.getAssignId());
                }
            }
        }
    }

    /**
     * 更新库存属性.
     * 
     * @param itemInfo
     *            商品dto
     * @param invItemAssign
     *            库存dto
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveItemProperty(InvItem itemInfo, InvItemAssign invItemAssign) {
        String savePropertyFlag = invItemAssign.getSavePropertyFlag();
        if (ProductConstants.YES.equals(savePropertyFlag)) {
            BigDecimal quantityAlert = invItemAssign.getQuantityAlert();
            String expiryAlert = invItemAssign.getExpiryAlert();
            InvItemProperty invItemProperty = new InvItemProperty();
            invItemProperty.setItemId(itemInfo.getItemId());
            invItemProperty.setOrganizationId(invItemAssign.getAssignValue());

            clearInvItemProperty(invItemProperty);
            if (quantityAlert != null) {
                saveInvItemProperty(invItemProperty, quantityAlert.toString(),
                        ProductConstants.PROPERTY_TYPE_QUANTITY_ALERT);
            }
            saveInvItemProperty(invItemProperty, expiryAlert, ProductConstants.PROPERTY_TYPE_EXPIRY_ALERT);
        } else {
            BigDecimal quantityAlert = itemInfo.getQuantityAlert();
            String expiryAlert = itemInfo.getExpiryAlert();
            InvItemProperty invItemProperty = new InvItemProperty();
            invItemProperty.setItemId(itemInfo.getItemId());
            invItemProperty.setOrganizationId(invItemAssign.getAssignValue());

            clearInvItemProperty(invItemProperty);
            if (quantityAlert != null) {
                saveInvItemProperty(invItemProperty, quantityAlert.toString(),
                        ProductConstants.PROPERTY_TYPE_QUANTITY_ALERT);
            }
            saveInvItemProperty(invItemProperty, expiryAlert, ProductConstants.PROPERTY_TYPE_EXPIRY_ALERT);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePriceListDetail(IRequest request, @StdWho List<PriceListDetail> itemPriceDetails, Long itemId,
            String uomCode) {
        if (itemPriceDetails != null) {
            for (PriceListDetail priceListDetail : itemPriceDetails) {
                if (DTOStatus.DELETE.equals(priceListDetail.get__status())) {
                    priceListDetailMapper.deleteByPrimaryKey(priceListDetail.getListDetailId());
                } else if (priceListDetail.getListDetailId() == null) {
                    Long salesOrgId = priceListDetail.getSalesOrgId();
                    Long priceListIdByOrgId = priceListMapper.getPriceListIdByOrgId(salesOrgId);
                    if (priceListIdByOrgId == null) {
                        // 新增价格头
                        PriceList priceList = new PriceList();
                        priceList.setSalesOrgId(salesOrgId);
                        priceList.setName(itemId.toString());
                        priceList.setGlobalFlag(ProductConstants.YES);
                        priceListMapper.insertSelective(priceList);
                        Long priceListId = priceListMapper.getPriceListIdByOrgId(salesOrgId);
                        priceListDetail.setPriceListId(priceListId);
                    } else {
                        priceListDetail.setPriceListId(priceListIdByOrgId);
                    }
                    // 价格行
                    priceListDetail.setItemId(itemId);
                    priceListDetail.setEnabledFlag(ProductConstants.YES);
                    priceListDetail.setUomCode(uomCode);
                    //设置开始时间为当前系统时间
                    /*  @author miaoyifan
                     *  @time 2017/12/19*/
                    priceListDetail.setStartActiveDate(new Date());
                    priceListDetailMapper.insert(priceListDetail);
                } else {
                    Long salesOrgId = priceListDetail.getSalesOrgId();
                    Long priceListIdByOrgId = priceListMapper.getPriceListIdByOrgId(salesOrgId);
                    if (priceListIdByOrgId == null) {
                        // 新增价格头
                        PriceList priceList = new PriceList();
                        priceList.setSalesOrgId(salesOrgId);
                        priceList.setName(itemId.toString());
                        priceList.setGlobalFlag(ProductConstants.YES);
                        priceListMapper.insertSelective(priceList);
                        Long priceListId = priceListMapper.getPriceListIdByOrgId(salesOrgId);
                        priceListDetail.setPriceListId(priceListId);
                    } else {
                        priceListDetail.setPriceListId(priceListIdByOrgId);
                    }
                    /*将变动的价格保存到价格历史变动表*/
                    PriceChangeHistory priceChangeHistory=new PriceChangeHistory();
                    //查找原始价格
                    Long ldi=priceListDetail.getListDetailId();
                    PriceListDetail bPriceListD=priceListDetailMapper.selectByPrimaryKey(ldi);
                    BigDecimal originalPrice=bPriceListD.getUnitPrice();
                    //修改的价格
                    BigDecimal changePrice=priceListDetail.getUnitPrice();
                    //商品ID
                    Long itemId1=bPriceListD.getItemId();
                    //销售组织ID
                    Long salesOrgId1=salesOrgId;
                    //币种
                    String currency1=bPriceListD.getCurrency();
                    //价格类型
                    String priceType1=bPriceListD.getPriceType();
                    priceChangeHistory.setCreatedBy(request.getAttribute("createdBy"));
                    priceChangeHistory.setItemId(itemId1);
                    priceChangeHistory.setSalesOrgId(salesOrgId1);
                    priceChangeHistory.setCurrency(currency1);
                    priceChangeHistory.setOriginalPrice(originalPrice);
                    priceChangeHistory.setChangePrice(changePrice);
                    priceChangeHistory.setPriceType(priceType1);
                    //当原始价格和修改的价格不相等时，在价格历史记录表中插入该数据
                    if(changePrice.equals( originalPrice) == false) {
                        priceChangeHistoryMapper.insert(priceChangeHistory);
                    }
                    priceListDetail.setUomCode(uomCode);
                    priceListDetailMapper.updateByPrimaryKey(priceListDetail);
                }
            }
        }
    }

    /**
     * 保存商品有效性.
     * 
     * @param itemId
     *            商品id
     * @param itemInfo
     *            商品基本信息
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveItemAvalibility(IRequest request, Long itemId, InvItem itemInfo) {
        String inStore = itemInfo.getInStore();
        InvItemAvailability availabilityInStore = new InvItemAvailability();
        availabilityInStore.setItemId(itemId);
        availabilityInStore.setFunctionArea(ProductConstants.FUNCTION_AREA_IN_STORE);
        availabilityInStore.setEnabledFlag(ProductConstants.YES);
        InvItemAvailability existInStore = invItemAvailabilityMapper.selectByPrimaryKey(availabilityInStore);
        boolean existInStoreFlag = true;
        if (existInStore == null) {
            existInStoreFlag = false;
        }
        if (ProductConstants.YES.equals(inStore) && !existInStoreFlag) {
            self().saveInvItemAvalibility(request, availabilityInStore);
            // invItemAvailabilityMapper.insertSelective(availabilityInStore);
        } else if (ProductConstants.NO.equals(inStore) && existInStoreFlag) {
            invItemAvailabilityMapper.deleteByPrimaryKey(availabilityInStore);
        }

        String agencyWeb = itemInfo.getAgencyWeb();
        InvItemAvailability availabilityAgency = new InvItemAvailability();
        availabilityAgency.setItemId(itemId);
        availabilityAgency.setFunctionArea(ProductConstants.FUNCTION_AREA_DISTRIBUTOR_WEB);
        availabilityAgency.setEnabledFlag(ProductConstants.YES);
        InvItemAvailability existAgencyWeb = invItemAvailabilityMapper.selectByPrimaryKey(availabilityAgency);
        boolean existAgencyWebFlag = true;
        if (existAgencyWeb == null) {
            existAgencyWebFlag = false;
        }
        if (ProductConstants.YES.equals(agencyWeb) && !existAgencyWebFlag) {
            self().saveInvItemAvalibility(request, availabilityAgency);
            // invItemAvailabilityMapper.insertSelective(availabilityAgency);
        } else if (ProductConstants.NO.equals(agencyWeb) && existAgencyWebFlag) {
            invItemAvailabilityMapper.deleteByPrimaryKey(availabilityAgency);
        }

        String agencyApp = itemInfo.getAgencyApp();
        InvItemAvailability availabilityApp = new InvItemAvailability();
        availabilityApp.setItemId(itemId);
        availabilityApp.setFunctionArea(ProductConstants.FUNCTION_AREA_DISTRIBUTOR_APP);
        availabilityApp.setEnabledFlag(ProductConstants.YES);
        InvItemAvailability existAgencyApp = invItemAvailabilityMapper.selectByPrimaryKey(availabilityApp);
        boolean existAgencyAppFlag = true;
        if (existAgencyApp == null) {
            existAgencyAppFlag = false;
        }
        if (ProductConstants.YES.equals(agencyApp) && !existAgencyAppFlag) {
            self().saveInvItemAvalibility(request, availabilityApp);
            // invItemAvailabilityMapper.insertSelective(availabilityApp);
        } else if (ProductConstants.NO.equals(agencyApp) && existAgencyAppFlag) {
            invItemAvailabilityMapper.deleteByPrimaryKey(availabilityApp);
        }

        String autoDeliver = itemInfo.getAutoDeliver();
        InvItemAvailability availabilityDeliver = new InvItemAvailability();
        availabilityDeliver.setItemId(itemId);
        availabilityDeliver.setFunctionArea(ProductConstants.FUNCTION_AREA_AUTOSHIP);
        availabilityDeliver.setEnabledFlag(ProductConstants.YES);
        InvItemAvailability existDeliver = invItemAvailabilityMapper.selectByPrimaryKey(availabilityDeliver);
        boolean existDeliverFlag = true;
        if (existDeliver == null) {
            existDeliverFlag = false;
        }
        if (ProductConstants.YES.equals(autoDeliver) && !existDeliverFlag) {
            self().saveInvItemAvalibility(request, availabilityDeliver);
            // invItemAvailabilityMapper.insertSelective(availabilityDeliver);
        } else if (ProductConstants.NO.equals(autoDeliver) && existDeliverFlag) {
            invItemAvailabilityMapper.deleteByPrimaryKey(availabilityDeliver);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveInvItemAvalibility(IRequest request, @StdWho InvItemAvailability invItemAvailability) {
        invItemAvailabilityMapper.insertSelective(invItemAvailability);
    }

    /**
     * 保存商品类别.
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品id
     * @param itemInfo
     *            商品基本信息
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveItemCategory(IRequest request, Long itemId, InvItem itemInfo) {
        String categoryIdList = itemInfo.getCategoryIdList();
        if (categoryIdList != null && !"".equals(categoryIdList)) {
            List<String> categoryIds = convertToArray(categoryIdList);
            List<InvItemCategory> oldCategoryItems = invItemCategoryMapper.getCategoryIdByItemId(itemId);
            List<String> delCategoryIds = new ArrayList<String>();
            List<InvItemCategory> delOldCategoryIds = new ArrayList<InvItemCategory>();
            for (String categoryIdStr : categoryIds) {
                Long categoryId = Long.parseLong(categoryIdStr);
                for (InvItemCategory oldInvItemCategory : oldCategoryItems) {
                    if (categoryId.longValue() == oldInvItemCategory.getCategoryId().longValue()) {
                        delCategoryIds.add(categoryIdStr);
                        delOldCategoryIds.add(oldInvItemCategory);
                    }
                }
            }
            categoryIds.removeAll(delCategoryIds);
            oldCategoryItems.removeAll(delOldCategoryIds);

            // 增加新的
            for (String categoryIdStr : categoryIds) {
                Long categoryId = Long.parseLong(categoryIdStr);
                if (logger.isDebugEnabled()) {
                    logger.debug("增加的新id: {}", categoryId);
                }
                InvItemCategory addCategory = new InvItemCategory();
                addCategory.setItemId(itemId);
                addCategory.setCategoryId(categoryId);
                self().saveInvItemCategory(request, addCategory);
                // invItemCategoryMapper.insertSelective(addCategory);
            }
            // 删除旧的
            if (logger.isDebugEnabled()) {
                logger.debug("删除条数：{}", oldCategoryItems.size());
            }
            if (!oldCategoryItems.isEmpty()) {
                for (InvItemCategory deleteICategory : oldCategoryItems) {
                    deleteICategory.setItemId(itemId);
                    invItemCategoryMapper.deleteByPrimaryKey(deleteICategory);
                }
            }
        } else {
            invItemCategoryMapper.deleteByItemId(itemId);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveInvItemCategory(IRequest request, InvItemCategory invItemCategory) {
        invItemCategoryMapper.insertSelective(invItemCategory);
    }

    /**
     * 分割带；的字符串，返回字符串list.
     * 
     * @param str
     *            字符串
     * 
     * @return 字符串数组
     */
    public List<String> convertToArray(String str) {
        List<String> strList = new ArrayList<String>();
        String[] strArray = str.split(";");
        strList.addAll(Arrays.asList(strArray));
        return strList;
    }

    @Override
    public List<SpmInvOrganization> querySpmInvOrganization(IRequest request, SpmInvOrganization spmInvOrganization) {
        return spmInvOrganizationMapper.querySpmInvOrganizations(spmInvOrganization);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long unPublishItem(IRequest request, Long itemId) throws ItemException {
        InvItem oldItem = invItemMapper.getItemById(itemId);
        String oldStatus = oldItem.getPublishStatus();
        if (ProductConstants.NO.equals(oldStatus)) {
            throw new ItemException(ItemException.PRODUCT_UNPUBLISH_REPEAT, new Object[] {});
        }
        invItemMapper.unPublishItem(itemId);
        return itemId;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void clearInvItemProperty(InvItemProperty invItemProperty) {
        if (invItemProperty != null && invItemProperty.getItemId() != null
                && invItemProperty.getOrganizationId() != null) {
            invItemPropertyMapper.deleteByItemIdAndOrgId(invItemProperty);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveInvItemProperty(InvItemProperty invItemProperty, String value, String type) {
        if (invItemProperty != null) {
            invItemProperty.setPropertyType(type);
            invItemProperty.setPropertyValue(value);
            invItemPropertyMapper.insertSelective(invItemProperty);
        }
    }

    @Override
    public InvItemPropertyDto queryPropertyType(IRequest request, Long itemId, Long organizationId) {
        InvItemProperty invItemProperty = new InvItemProperty();
        invItemProperty.setItemId(itemId);
        invItemProperty.setOrganizationId(organizationId);
        List<InvItemProperty> queryInvProperty = invItemPropertyMapper.queryInvProperty(invItemProperty);
        InvItemPropertyDto invItemPropertyDto = new InvItemPropertyDto();
        for (InvItemProperty property : queryInvProperty) {
            String propertyType = property.getPropertyType();
            String propertyValue = property.getPropertyValue();
            invItemPropertyDto.setItemId(itemId);
            invItemPropertyDto.setInvOrganizationId(organizationId);
            if (ProductConstants.PROPERTY_TYPE_QUANTITY_ALERT.equals(propertyType)) {
                invItemPropertyDto.setQuantityAlert(new BigDecimal(propertyValue));
            }
            if (ProductConstants.PROPERTY_TYPE_EXPIRY_ALERT.equals(propertyType)) {
                invItemPropertyDto.setExpiryAlert(propertyValue);
            }
        }
        return invItemPropertyDto;
    }

    @Override
    public Long getNextItemId(IRequest request) {
        return invItemMapper.getNextItemKey();
    }

    @Override
    public List<InvItem> queryItem(IRequest request, InvItem item, int page, int pageSize) {
        if(item.getFunctionArea() != null){
            List<String> faList = Arrays.asList(item.getFunctionArea().split(";"));
            item.setFunctionAreas(faList);
        }
        return commItemService.queryItems(request, item, page, pageSize);
    }
    
    @Override
    public List<InvItem> queryItemForOrder(IRequest request, InvItem item, int page, int pageSize) {
        return commItemService.queryItemsForOrder(request, item, page, pageSize);
    }

    @Override
    public List<InvItem> queryItemWithOrg(IRequest request, InvItem item, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return invItemMapper.queryItemWithOrg(item);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<InvItem> queryItemBs(IRequest request, InvItem item, Long invOrgId, String trxType, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        return invItemMapper.queryItemBs(item, invOrgId, trxType);
    }

    @Override
    public List<InvUnitOfMeasureB> queryUomCodes(IRequest request, InvUnitOfMeasureB invUom, int page, int pagesize) {
        return invUnitOfMeasureBMapper.selectUomCodeLov(invUom);
    }

    @Override
    public List<InvItem> queryTransferItems(IRequest request, InvItem item, Long outOrg, Long inOrg, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);

        return invItemMapper.queryTransferItems(item, outOrg, inOrg);
    }

    @Override
    public Long checkOrgIncludeItems(IRequest request, List<Long> itemIds, Long orgId) throws ItemException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemIds", itemIds);
        params.put("orgId", orgId);
        Integer result = invItemAssignMapper.queryOrgIncludeItems(params);
        if (itemIds.size() != result) {
            throw new ItemException(ItemException.INV_SALE_ORG_IS_NOT_INCLUDE_ITEMS_EXCEPTION, new Object[] {});
        }
        return orgId;
    }

    @Override
    public Long checkIncludeItemOrgs(IRequest requestContext, Long itemId, List<Long> invOrgIds) throws ItemException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemId", itemId);
        params.put("invOrgIds", invOrgIds);
        Integer result = invItemAssignMapper.queryIncludeItemOrgs(params);
        // 所选商品是否在所有库存组织下，并且库存组织启用标志为是
        if (invOrgIds.size() != result) {
            throw new ItemException(ItemException.INCLUDE_ITEMS_IS_NOT_BELONG_TO_ORG_EXCEPTION, new Object[] {});
        }
        return itemId;
    }

    @Override
    public List<InvItem> queryItemsByCountStock(IRequest request, InvItem item, Long itemId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        item.setItemId(itemId);
        return invItemMapper.queryItemsByCountStock(item);
    }

    @Override
    public List<InvCategoryB> queryAllBottomCategory(IRequest request) {
        return invCategoryBMapper.queryAllBottomCategory();
    }

    @Override
    public List<InvItem> queryPoHeaderItem(IRequest request, InvItem item, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<InvItem> list = invItemMapper.queryByPoHeader(item);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long publishItem(IRequest request, @StdWho ItemAllDto itemAllDto)
            throws ItemException, UnsupportedEncodingException {
        Long itemId = itemAllDto.getItemId();
        InvItem itemInfo = itemAllDto.getItemInfo();
        String publishStatus = itemInfo.getPublishStatus();
        if (ProductConstants.YES.equals(publishStatus)) {
            throw new ItemException(ItemException.PRODUCT_PUBLISH_REPEAT, new Object[] {});
        }
        String agencyWeb = itemInfo.getAgencyWeb();
        String agencyApp = itemInfo.getAgencyApp();
        if (!ProductConstants.YES.equals(agencyWeb) && !ProductConstants.YES.equals(agencyApp)) {
            throw new ItemException(ItemException.PRODUCT_PUBLISH_AVALIABILITY, new Object[] {});
        }
        self().savePackageAll(request, itemAllDto);
        invItemMapper.publishItem(itemId);
        return itemId;
    }

    @Override
    public Boolean checkInvOrgEnable(IRequest request, Long itemId, Long invOrgId) {
        Long itemCount = invItemMapper.queryItemInvOrgEnabled(itemId, invOrgId);
        Long packgCount = invItemMapper.queryPackgInvOrgEnabled(itemId, invOrgId);
        Long total = itemCount + packgCount;
        if (total > 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    @Override
    public List<InvItemAttrTl> getWhetherHide(IRequest request, InvItemAttrTl invItemAttrTl) {
        return invItemAttrTlMapper.selectHideOptions(invItemAttrTl);
    }

    @Override
    public OrderItemPrice queryItemPriceForOrder(IRequest request, Long itemId, String currency, String uomCode,
                                                 Long salesOrgId, String orderType, String itemSalestype) {

        return commItemService.queryItemPriceForOrder(request, itemId, currency, uomCode, salesOrgId, orderType,
                itemSalestype);

    }
}

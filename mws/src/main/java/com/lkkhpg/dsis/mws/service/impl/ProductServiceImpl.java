/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmSupply;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.config.mapper.SpmCompanyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmCurrencyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSupplyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.common.product.dto.InvItemAttrTl;
import com.lkkhpg.dsis.common.product.mapper.InvCategoryBMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemAttrTlMapper;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.mws.constant.ProductConstants;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.mapper.ProductMapper;
import com.lkkhpg.dsis.mws.service.IProductService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigDecimal;
import java.util.*;

/**
 * 商品接口实现.
 *
 * @author xiawang.liu
 */
@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    private static final String CHECK_STATUS_Y = "Y";

    @Autowired
    private InvCategoryBMapper invCategoryBMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private IParamService paramService;

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private SpmTaxMapper spmTaxMapper;

    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;

    @Autowired
    private InvItemAttrTlMapper invItemAttrTlMapper;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private SpmSupplyMapper spmSupplyMapper;

    @Autowired
    private SpmCompanyMapper spmCompanyMapper;

    @Override
    public List<InvCategoryB> getProductCategorys(IRequest requestContext) {
        InvCategoryB invCategoryB = new InvCategoryB();
        invCategoryB.setAttribute2(String.valueOf(requestContext.getAttributeMap().get("_marketId")));
        return invCategoryBMapper.queryAllBottomCategory();
    }

    @Override
    public Map<String,Object> getProductCateryItem(IRequest requestContext){
            List<InvCategoryB> items= invCategoryBMapper.queryAllCategoryItem();
            Map<String,Object>categories=new LinkedHashMap<String,Object>();
            Map<String,Object>categoriesSubItems=new LinkedHashMap<String,Object>();
            List<InvCategoryB> mainLists=new ArrayList<InvCategoryB>();
            for(InvCategoryB item:items){
                if(String.valueOf(item.getParentCategoryId()).equals("0")){
                    mainLists.add(item);
                }
            }
            for(InvCategoryB inv:mainLists){
                items.remove(inv);
                if(inv.getLeafFlag().equalsIgnoreCase("N")){
                    categories.put(inv.getCategoryId() + ProductConstants.CATEGORY_PARAM + inv.getCategoryName(),
                            Traversal(inv.getCategoryId(),items));
                }else{
                    categories.put(inv.getCategoryId() + ProductConstants.CATEGORY_PARAM + inv.getCategoryName(), null);
                }
            }

        return categories;
    }


    private  Map<String,Object> Traversal(Long categoryId, List<InvCategoryB> data){
        Map<String,Object>categoriesSubItems=new LinkedHashMap<String,Object>();
        List<InvCategoryB> categoryBList=new ArrayList<InvCategoryB>();

        for(InvCategoryB item:data){
            if(String.valueOf(item.getParentCategoryId()).equals(String.valueOf(categoryId))){
                categoryBList.add(item);
            }
        }
        for(InvCategoryB bList:categoryBList){
            data.remove(bList);
            if(bList.getLeafFlag().equals("N")){
                categoriesSubItems.put(bList.getCategoryId() + ProductConstants.CATEGORY_PARAM + bList.getCategoryName(),
                        Traversal(bList.getCategoryId(),data));
            }else{
                categoriesSubItems.put(bList.getCategoryId() + ProductConstants.CATEGORY_PARAM + bList.getCategoryName(),
                        null);
            }

        }
        return categoriesSubItems;
    }

    @Override
    public Map<String, Object> getProductsByInCategorys(IRequest requestContext) {
        Product product = new Product();
        String priceType = requestContext.getAttribute(MemberConstants.FIELD_MEMBER_ROLE);
        Long memberId = requestContext.getAttribute(Member.FIELD_MEMBER_ID);
        Member member = commMemberService.getMember(requestContext, memberId);
        SpmCompany spmCompany = spmCompanyMapper.selectByPrimaryKey(member.getCompanyId());
        String isCheck = spmCompany.getAttribute3() == null ? "N" : spmCompany.getAttribute3();

        /*
         * 将会员类型为直营店的商品价格设为经销商价格
         * updated by 15750 at 2017/12/20
         * */
        if (priceType.equals("DSS")) {
            priceType = new String("DIS");
        }
        Map<String, Object> map = new LinkedHashMap<>();
        List<String> currencyCodes = paramService.getSalesParamValues(requestContext,
                ProductConstants.CURRENCY_CODE_PARAM, requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));

        //updated by 11816 at 2017/12/05 09:46
        InvCategoryB invCategoryB2 = new InvCategoryB();
        invCategoryB2.setAttribute2(String.valueOf(requestContext.getAttributeMap().get("_marketId")));
        List<InvCategoryB> list = invCategoryBMapper.queryAllBottomCategory();


        for (InvCategoryB invCategoryB : list) {

            /*SpmSupply spmSupply = new SpmSupply();
            spmSupply.setSalesOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
            List<SpmSupply> spmSupplyList = spmSupplyMapper.queryInvOrgBySpmSupply(spmSupply);
            if (null == spmSupplyList || spmSupplyList.size() == 0) {
                product.setOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
                product.setCurrencyCode(currencyCodes.get(ProductConstants.INDEX));
                product.setCategoryId(invCategoryB.getCategoryId());
                product.setFunctionArea(ProductConstants.WEB_STYLE);
                *//*modified by furong.tang*//*
                List<Product> pro_list = new ArrayList<Product>();
                //如果是新天丰公司，保留量含有审批中状态
                if (null != companyCode && OrderConstants.SALES_ORDER_COM_CODE.equals(companyCode)) {
                    pro_list = productMapper.getSimpleProductsByWhereClause(product, priceType);
                } else {
                    pro_list = productMapper.getSimpleProductsByWhereClause2(product, priceType);
                }
                List<Product> productList = getProdects(requestContext, pro_list);
                map.put(invCategoryB.getCategoryId() + ProductConstants.CATEGORY_PARAM + invCategoryB.getCategoryName(),
                        productList);
            } else {
                List<Product> pro_list = new ArrayList<Product>();
                for(SpmSupply supply : spmSupplyList){
                    product.setOrgId(supply.getInvOrgId());
                    product.setOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
                    product.setCurrencyCode(currencyCodes.get(ProductConstants.INDEX));
                    product.setCategoryId(invCategoryB.getCategoryId());
                    product.setFunctionArea(ProductConstants.WEB_STYLE);
                    *//*modified by furong.tang*//*

                    //如果是新天丰公司，保留量含有审批中状态
                    if (null != companyCode && OrderConstants.SALES_ORDER_COM_CODE.equals(companyCode)) {
                        pro_list.addAll(productMapper.getSimpleProductsByWhereClause(product, priceType));
                    } else {
                        pro_list.addAll(productMapper.getSimpleProductsByWhereClause2(product, priceType));
                    }
                }
                List<Product> productList = getProdects(requestContext, pro_list);
                map.put(invCategoryB.getCategoryId() + ProductConstants.CATEGORY_PARAM + invCategoryB.getCategoryName(),
                        productList);
            }*/
            product.setOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
            product.setCurrencyCode(currencyCodes.get(ProductConstants.INDEX));
            product.setCategoryId(invCategoryB.getCategoryId());
            product.setFunctionArea(ProductConstants.WEB_STYLE);

            SpmSupply spmSupply = new SpmSupply();
            spmSupply.setSalesOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
            List<SpmSupply> spmSupplyList = spmSupplyMapper.queryInvOrgBySpmSupply(spmSupply);
            if (null == spmSupplyList || spmSupplyList.size() == 0) {
                product.setAttribute2(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID).toString());
            } else {
                product.setAttribute2(spmSupplyList.get(0).getInvOrgId().toString());
            }
            //*modified by furong.tang*//
            List<Product> pro_list = new ArrayList<Product>();
            //如果是新天丰公司，保留量含有审批中状态
            if (isCheck.equals(CHECK_STATUS_Y)) {
                pro_list.addAll(productMapper.getSimpleProductsByWhereClause(product, priceType));
            } else {
                pro_list.addAll(productMapper.getSimpleProductsByWhereClause2(product, priceType));
            }

            List<Product> productList = getProdects(requestContext, pro_list);
            map.put(invCategoryB.getCategoryId() + ProductConstants.CATEGORY_PARAM + invCategoryB.getCategoryName(),
                    productList);
        }
        return map;
    }

    @Override
    public List<Product> getDetailProductsByWhereClause(IRequest requestContext, Product product) {
        String priceType = requestContext.getAttribute(MemberConstants.FIELD_MEMBER_ROLE);
        Long memberId = requestContext.getAttribute(Member.FIELD_MEMBER_ID);
        Member member = commMemberService.getMember(requestContext, memberId);
        SpmCompany spmCompany = spmCompanyMapper.selectByPrimaryKey(member.getCompanyId());
        String isCheck = spmCompany.getAttribute3() == null ? "N" : spmCompany.getAttribute3();
        List<String> currencyCodes = paramService.getSalesParamValues(requestContext,
                ProductConstants.CURRENCY_CODE_PARAM, requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        product.setOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        product.setCurrencyCode(currencyCodes.get(ProductConstants.INDEX));
        /*modified by furong.tang*/
        SpmSupply spmSupply = new SpmSupply();
        spmSupply.setSalesOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        List<SpmSupply> spmSupplyList = spmSupplyMapper.queryInvOrgBySpmSupply(spmSupply);
        if (null == spmSupplyList || spmSupplyList.size() == 0) {
            product.setAttribute2(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID).toString());
        } else {
            product.setAttribute2(spmSupplyList.get(0).getInvOrgId().toString());
        }
        List<Product> pro_list = new ArrayList<Product>();
        if (isCheck.equals(CHECK_STATUS_Y)) {
            pro_list = productMapper.getDetailProductsByWhereClause(product, priceType);
        } else {
            pro_list = productMapper.getDetailProductsByWhereClause2(product, priceType);
        }

        if (pro_list.size() == 0) {
            return pro_list;
        }
        List<Product> categoryNames = productMapper.getProductsCategoryNamesByItemId(product);
        String categoryName = "";
        for (Product product2 : categoryNames) {
            categoryName += " " + product2.getCategoryName();
        }
        pro_list.get(0).setCategoryName(categoryName);
        List<Product> productList = getProdects(requestContext, pro_list);
        List<Product> products = getImageForProducts(requestContext, productList);
        return products;
    }

    @Override
    public List<Product> getSimpleProductsByWhereClause(IRequest requestContext, Product product, int page,
                                                        int pagesize) {

        String priceType = requestContext.getAttribute(MemberConstants.FIELD_MEMBER_ROLE);
        Long memberId = requestContext.getAttribute(Member.FIELD_MEMBER_ID);
        Member member = commMemberService.getMember(requestContext, memberId);
       // SpmCompany spmCompany = spmCompanyMapper.selectByPrimaryKey(member.getCompanyId());
       // String isCheck = spmCompany.getAttribute3() == null ? "N" : spmCompany.getAttribute3();
        String isCheck ="N";
        List<String> currencyCodes = paramService.getSalesParamValues(requestContext,
                ProductConstants.CURRENCY_CODE_PARAM, requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
      //  product.setCurrencyCode(currencyCodes.get(ProductConstants.INDEX));
        product.setCurrencyCode("CNY");

        /*
         * 将会员类型为直营店的商品价格设为经销商价格
         * updated by 15750 at 2017/12/20
         * */
        if(priceType == null)
        {
            priceType ="DIS";
        }
        if (priceType.equals("DSS")) {
            priceType = new String("DIS");
        }

        if (product.getOrgId() == null) {
            product.setOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        }
        if (product.getFunctionArea() == null) {
            product.setFunctionArea(ProductConstants.WEB_STYLE);
        }

        List<Product> pro_list = new ArrayList<>();

        SpmSupply spmSupply = new SpmSupply();
        spmSupply.setSalesOrgId(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
   //     List<SpmSupply> spmSupplyList = spmSupplyMapper.queryInvOrgBySpmSupply(spmSupply);
 /*       if (null == spmSupplyList || spmSupplyList.size() == 0) {
            product.setAttribute2(requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID).toString());
        } else {
            product.setAttribute2(spmSupplyList.get(0).getInvOrgId().toString());
        }*/

        if (pagesize != -1) {
            PageHelper.startPage(page, pagesize);
        }

        //updated by 11816 at 2018/01/30 13:45
        if (isCheck.equals(CHECK_STATUS_Y)) {
            pro_list = productMapper.getSimpleProductsByWhereClause(product, priceType);
        } else {
            pro_list = productMapper.getSimpleProductsByWhereClause2(product, priceType);

        }
        List<Product> productList = getProdects(requestContext, pro_list);
        List<Product> products = getImageForProducts(requestContext, productList);


        return products;
    }

    @Override
    public List<Product> getFastProductsByWhereClause(IRequest requestContext, List<Long> itemIds, Long saleOrgId,
                                                      String itemName) {
        String priceType = requestContext.getAttribute(MemberConstants.FIELD_MEMBER_ROLE);
        List<String> currencyCodes = paramService.getSalesParamValues(requestContext,
                ProductConstants.CURRENCY_CODE_PARAM, saleOrgId);
        List<Product> pro_list = productMapper.getFastProductsByWhereClause(itemIds, saleOrgId,
                currencyCodes.get(ProductConstants.INDEX), itemName, priceType);
        List<Product> productList = getProdects(requestContext, pro_list);
        return productList;
    }

    @Override
    public List<Product> getImageForProducts(IRequest requestContext, List<Product> pro_list) {
        for (Product list : pro_list) {
            List<SysFile> pic_list = sysFileService.selectFilesByTypeAndKey(requestContext,
                    ProductConstants.PACKAGE_IMAGE_PARAM, list.getItemId());
            if (pic_list != null) {
                for (SysFile sysFile : pic_list) {
                    String fileRealPath = sysFile.getFilePath();
                    if (!fileRealPath.isEmpty()) {
                        sysFile.setFilePath(fileRealPath.substring(
                                fileRealPath.lastIndexOf("/", fileRealPath.lastIndexOf("/") - 1) + 1,
                                fileRealPath.length()));
                    }
                }
            }
            list.setItemImg(pic_list);
        }
        return pro_list;
    }

    /**
     * @param pro_list       List<Product>
     * @param requestContext 统一上下文
     * @return 返回信息
     */
    public List<Product> getProdects(IRequest requestContext, List<Product> pro_list) {
        // 根据币种符号获得进度
        /*String currencyCode = paramService.getSalesParamValues(requestContext,
                ProductConstants.CURRENCY_CODE_PARAM, requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID)).get(ProductConstants.INDEX);*/
        String currencyCode = "CNY";
        SpmCurrency spmCurrency = new SpmCurrency();
        spmCurrency.setCurrencyCode(currencyCode);
        SpmCurrency SpmCurrency = spmCurrencyMapper.querySpmCurrency(spmCurrency).get(ProductConstants.INDEX);
        int precision = SpmCurrency.getPrecision().intValue();

        // 是否启用税
        String enableTax = new String();
        List<String> enableTaxs = paramService.getSalesParamValues(requestContext, ProductConstants.ENABLE_TAXE_PARAM,
                requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        if (!(enableTaxs == null || enableTaxs.isEmpty())) {
            enableTax = enableTaxs.get(ProductConstants.INDEX);
            for (Product pro : pro_list) {
                pro.setSign(enableTax);
            }
        }
        List<String> taxCode = paramService.getSalesParamValues(requestContext, ProductConstants.TAX_CODE_PARAM,
                requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        if (taxCode == null||taxCode.isEmpty()) {
            for (Product pro : pro_list) {
                pro.setSign("N");
            }
        }
        // 价格是否包含税
        String enableFlag = new String();
        List<String> enableFlags = paramService.getSalesParamValues(requestContext,
                ProductConstants.PRICE_INCLUDE_TAX_PARAM,
                requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        if (!(enableFlags == null || enableFlags.isEmpty())) {
            enableFlag = enableFlags.get(ProductConstants.INDEX);
        }

        // 获取组织参数税码
        // 计算税率
        List<String> taxCodes = paramService.getSalesParamValues(requestContext, ProductConstants.TAX_CODE_PARAM,
                requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        SpmTax spmTax = new SpmTax();
        // 如果组织参数税码不为空
        if (!(taxCodes == null || taxCodes.isEmpty())) {
            spmTax.setTaxCode(taxCodes.get(ProductConstants.INDEX));
            spmTax = spmTaxMapper.queryByTax(spmTax).get(ProductConstants.INDEX);
        }
        // 判断如果启用税，并且价格不含税
        // 则计算税
        if (!enableTax.isEmpty() && ProductConstants.YES.equals(enableTax) && !enableFlag.isEmpty()
                && ProductConstants.NO.equals(enableFlag)) {

            // 获取组织参数税码
            // 计算税率
            /*
             * List<String> taxCodes = paramService.getSalesParamValues(requestContext, ProductConstants.TAX_CODE_PARAM,
             * requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID)); SpmTax spmTax = new SpmTax(); //
             * 如果组织参数税码不为空 if (!(taxCodes == null || taxCodes.isEmpty())) {
             * spmTax.setTaxCode(taxCodes.get(ProductConstants.INDEX)); spmTax =
             * spmTaxMapper.queryByTax(spmTax).get(ProductConstants.INDEX); }
             */
            if (spmTax.getTaxPercent() != null) {
                for (Product list : pro_list) {
                    list.setTax(spmTax.getTaxPercent());
                    list.setOrginalAmt(list.getDisAmt().setScale(precision, BigDecimal.ROUND_HALF_UP));
                    Double disAmt = list.getDisAmt().doubleValue();
                    Double taxPercent = spmTax.getTaxPercent().doubleValue();
                    disAmt = disAmt * (1 + taxPercent / ProductConstants.TAX_PERCENT);
                    list.setDisAmt(new BigDecimal(disAmt).setScale(precision, BigDecimal.ROUND_HALF_UP));
                }
            } else {
                for (Product list : pro_list) {
                    list.setOrginalAmt(list.getDisAmt().setScale(precision, BigDecimal.ROUND_HALF_UP));
                    list.setTax(new BigDecimal(0));
                }
            }
        } else {
            if (spmTax.getTaxPercent() != null) {
                for (Product list : pro_list) {
                    list.setOrginalAmt(list.getDisAmt().setScale(precision, BigDecimal.ROUND_HALF_UP));
                    list.setTax(spmTax.getTaxPercent());
                }
            } else {
                for (Product list : pro_list) {
                    list.setOrginalAmt(list.getDisAmt().setScale(precision, BigDecimal.ROUND_HALF_UP));
                    list.setTax(new BigDecimal(0));
                }
            }
        }
        return pro_list;
    }

    @Override
    public List<InvItemAttrTl> getWhetherHide(IRequest request, Long itemId) {
        InvItemAttrTl invItemAttrTl = new InvItemAttrTl();
        invItemAttrTl.setItemId(itemId);
        invItemAttrTl.setLang(request.getLocale());
        return invItemAttrTlMapper.selectHideOptions(invItemAttrTl);
    }

    @Override
    public Integer getXtfSum(Long marketId) {
        return productMapper.getXtfSum(marketId);
    }
}
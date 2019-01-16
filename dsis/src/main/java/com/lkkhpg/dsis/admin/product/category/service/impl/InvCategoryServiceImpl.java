/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.category.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.product.category.service.IInvCategoryService;
import com.lkkhpg.dsis.admin.product.exception.ItemException;
import com.lkkhpg.dsis.admin.product.product.service.IItemService;
import com.lkkhpg.dsis.common.constant.ProductConstants;
import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvItemCategory;
import com.lkkhpg.dsis.common.product.mapper.InvCategoryBMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemCategoryMapper;
import com.lkkhpg.dsis.common.service.ICommItemService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 商品类别管理实现类.
 *
 * @author linxiaodong
 */
@Service
@Transactional
public class InvCategoryServiceImpl implements IInvCategoryService {


    private static final Long PARENT_CATEGORY_ID_NULL = 0L;

    @Autowired
    private IItemService itemService;

    @Autowired
    private ICommItemService commItemService;

    @Autowired
    private InvCategoryBMapper invCategoryBMapper;

    @Autowired
    private InvItemCategoryMapper invItemCategoryMapper;

    @Override
    public List<InvCategoryB> queryCategory(IRequest request, InvCategoryB invCategoryB) {
        List<InvCategoryB> categoryList = commItemService.queryCategory(request, invCategoryB);
        return categoryList;
    }

    @Override
    public List<InvCategoryB> queryTopCategory(IRequest request) {
        List<InvCategoryB> topCategoryList = invCategoryBMapper.queryTopCategory();
        if (topCategoryList == null) {
            return null;
        }
        for (InvCategoryB invCategoryB : topCategoryList) {
            List<InvCategoryB> childrenList = self().queryChildrenCategory(request, invCategoryB);
            invCategoryB.setChildren(childrenList);
        }
        return topCategoryList;
    }

    @Override
    public List<InvCategoryB> queryChildrenCategory(IRequest request, InvCategoryB invCategoryB) {
        List<InvCategoryB> categoryList = invCategoryBMapper.queryChildrenCategory(invCategoryB);
        if (categoryList == null) {
            return null;
        }
        for (InvCategoryB invCategory : categoryList) {
            List<InvCategoryB> childrenList = self().queryChildrenCategory(request, invCategory);
            invCategory.setChildren(childrenList);
        }
        return categoryList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvCategoryB saveOrUpdate(IRequest request, InvCategoryB invCategoryB) throws ItemException {
        // 校验父节点是否已经分配了商品
        InvCategoryB parentCategory = new InvCategoryB();

        Long parentCategoryId = invCategoryB.getParentCategoryId();

        if (parentCategoryId != null && !PARENT_CATEGORY_ID_NULL.equals(parentCategoryId)) {
            parentCategory.setCategoryId(invCategoryB.getParentCategoryId());

            // 查找父类别
            List<InvCategoryB> results = self().queryCategory(request, parentCategory);
            if (results != null && !results.isEmpty()) {
                parentCategory = results.get(0);
            }

            List<InvItem> result = itemService.queryProductByCategory(request, parentCategory, 1, 1);
            if (result != null && result.size() > 0) {
                throw new ItemException(ItemException.SUBCATEGORY_FAIL_EXCEPTION, new Object[]{});
            }
        }

        // 判别保存or更新
        if (invCategoryB.getCategoryId() == null) {
            invCategoryB.setLeafFlag(ProductConstants.YES);
            invCategoryB.setStartActiveDate(invCategoryB.getCreationDate());
            invCategoryBMapper.insert(invCategoryB);
            // 更新父节点的leafFlag为N
            parentCategory.setLeafFlag(ProductConstants.NO);
            invCategoryBMapper.updateByPrimaryKeySelective(parentCategory);
        } else {
            List<InvItem> items = invCategoryB.getItems();
            if (items != null) {
                for (InvItem item : items) {
                    InvItemCategory itemCategory = new InvItemCategory();
                    itemCategory.setItemId(item.getItemId());
                    itemCategory.setCategoryId(invCategoryB.getCategoryId());
                    itemCategory.setLastUpdatedBy(request.getAccountId());
                    itemCategory.setCreatedBy(request.getAccountId());

                    if (DTOStatus.ADD.equals(item.get__status())) {
                        // 验证是否已经分配了该商品
                        InvItemCategory ic = invItemCategoryMapper.selectByPrimaryKey(itemCategory);
                        if (ic != null) {
                            throw new ItemException(ItemException.PRODUCT_CANT_ASSIGN, new Object[]{});
                        }

                        invItemCategoryMapper.insert(itemCategory);
                    } else if (DTOStatus.DELETE.equals(item.get__status())) {
                        invItemCategoryMapper.deleteByPrimaryKey(itemCategory);
                    }
                }
            }
            invCategoryBMapper.updateByPrimaryKeySelective(invCategoryB);
        }

        return invCategoryB;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCategory(IRequest request, InvCategoryB invCategoryB) throws ItemException {
        // 更新子类别的parentId为自己的parentId
        List<InvCategoryB> subCategories = invCategoryBMapper.queryChildrenCategory(invCategoryB);
        if (subCategories != null) {
            for (InvCategoryB subCategory : subCategories) {
                subCategory.setParentCategoryId(invCategoryB.getParentCategoryId());
                invCategoryBMapper.updateByPrimaryKey(subCategory);
            }
        }
        // 删除类别后该类别下分配的商品去除该类别
        invItemCategoryMapper.deleteByCategoryId(invCategoryB.getCategoryId());
        return invCategoryBMapper.deleteByPrimaryKey(invCategoryB) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDelete(IRequest request, InvCategoryB invCategoryB) throws ItemException {
        // 检查底层类别是否分配商品
        if (!checkCategory(request, invCategoryB)) {
            throw new ItemException(ItemException.SUBCATEGORY_FAIL_EXCEPTION, new Object[]{});
        }
        // 检查通过删除所有的类别
        deleteCategories(request, invCategoryB);
        return true;
    }

    /**
     * 删除类别执行方法.
     *
     * @param request      统一上下文.
     * @param invCategoryB 要删除的类别.
     */
    private void deleteCategories(IRequest request, InvCategoryB invCategoryB) {
        List<InvCategoryB> subCategories = invCategoryBMapper.queryChildrenCategory(invCategoryB);
        if (subCategories != null) {
            for (InvCategoryB subCategory : subCategories) {
                deleteCategories(request, subCategory);
            }
        }
        invCategoryBMapper.deleteByPrimaryKey(invCategoryB);
    }

    /**
     * 检查类别树上是否有分配商品.
     *
     * @param request      统一上下文.
     * @param invCategoryB 要检查的类别.
     * @return true/false
     */
    private boolean checkCategory(IRequest request, InvCategoryB invCategoryB) {
        List<InvCategoryB> subCategories = invCategoryBMapper.queryChildrenCategory(invCategoryB);
        boolean flag = true;
        if (subCategories != null && !subCategories.isEmpty()) {
            for (InvCategoryB subCategory : subCategories) {
                flag = checkCategory(request, subCategory);
                if (!flag) {
                    return flag;
                }
            }
        } else {
            List<InvItem> result = itemService.queryProductByCategory(request, invCategoryB, 1, 1);
            if (result.size() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<InvCategoryB> queryBottomCategory(IRequest iRequest, String itemType) {
        List<InvCategoryB> categories = invCategoryBMapper.queryBottomCategory(itemType);
        return categories;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<InvCategoryB> queryCategorySelection(IRequest request) {
        return invCategoryBMapper.queryCategorySelection();
    }

    @Override
    public List<InvCategoryB> queryParentCates(IRequest request) {
        return invCategoryBMapper.queryParentCates();
    }

    public Long beforeInsert(IRequest request, InvCategoryB invCategoryB) {
        return invCategoryBMapper.beforeInsert(invCategoryB);
    }
}

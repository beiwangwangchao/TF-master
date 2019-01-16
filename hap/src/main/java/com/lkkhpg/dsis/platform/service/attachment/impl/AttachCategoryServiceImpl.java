/*
 *
 */
package com.lkkhpg.dsis.platform.service.attachment.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.attachment.AttachCategory;
import com.lkkhpg.dsis.platform.exception.StoragePathNotExsitException;
import com.lkkhpg.dsis.platform.mapper.attachment.AttachCategoryMapper;
import com.lkkhpg.dsis.platform.service.attachment.IAttachCategoryService;

/**
 * 附件类型service.
 * 
 * @author hua.xiao@hand-china.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttachCategoryServiceImpl implements IAttachCategoryService {
    

    /**
     * 找不到对应的路径.
     **/
    public static final String CATEGORY_PATH_NOT_CORRECT = "category_path_not_correct";

    @Autowired
    private AttachCategoryMapper categoryMapper;

    @Override
    public AttachCategory insert(IRequest requestContext, @StdWho AttachCategory category) {
        if (StringUtils.isBlank(category.getSourceType())) {
            category.setSourceType(AttachCategory.DEFAULT_SOURCETYPE);
        }
        if (StringUtils.isBlank(category.getIsUnique())) {
            category.setIsUnique(BaseConstants.NO);
        }
        if (StringUtils.isBlank(category.getStatus())) {
            category.setStatus(AttachCategory.STATUS_NORMAL);
        }
        // 更新其父节点不为空
        AttachCategory parent = categoryMapper.selectByPrimaryKey(category.getParentCategoryId());
        if (parent != null && AttachCategory.LEAF_TRUE.equals(parent.getLeafFlag())) {
            parent.setLeafFlag(AttachCategory.LEAF_FALSE);
            categoryMapper.updateAttachCateory(parent);
        }
        categoryMapper.insert(category);
        return category;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public AttachCategory selectAttachByCode(IRequest requestContext, String sourceCode) {
        if (StringUtils.isBlank(sourceCode)) {
            return null;
        }
        AttachCategory params = new AttachCategory();
        params.setSourceType(sourceCode);
        params.setStatus(AttachCategory.STATUS_NORMAL);
        return categoryMapper.selectCategory(params);
    }

    /*
     * 根据sourceType 查找绝对路径 比如：/home/upload/image/2015/1
     * 
     * @see
     * IAttachCategoryService#selectPathByCode(
     * IRequest, java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public String selectPathByCode(IRequest requestContext, String sourceType) throws StoragePathNotExsitException {
        AttachCategory params = new AttachCategory();
        params.setSourceType(sourceType);
        AttachCategory category = categoryMapper.selectCategory(params);

        if (category == null || category.getCategoryPath().length() <= 0) {
            throw new StoragePathNotExsitException();
        }
        return category.getCategoryPath();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<AttachCategory> selectCategories(AttachCategory category) {
        return categoryMapper.selectCategories(category);
    }

    @Override
    public List<AttachCategory> batchUpdate(IRequest requestCtx, @StdWho List<AttachCategory> categories) {
        for (AttachCategory category : categories) {

            if (category.getCategoryId() == null) {
                self().insert(requestCtx, category);
            } else {
                categoryMapper.updateAttachCateory(category);
            }
        }
        return categories;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public AttachCategory selectByPrimaryKey(IRequest requestContext, Long categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public boolean remove(AttachCategory category) {
        category = categoryMapper.selectByPrimaryKey(category.getCategoryId());
        if (category == null || AttachCategory.LEAF_FALSE.equals(category.getLeafFlag())) {
            return false;
        } else {
            category.setStatus(AttachCategory.STATUS_DELETED);
            categoryMapper.updateAttachCateory(category);
            return true;
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<AttachCategory> selectCategories(IRequest requestContext, AttachCategory category) {
        return categoryMapper.selectCategories(category);
    }

    @Override
    public AttachCategory validateType(List<AttachCategory> categories) {
        List<String> types = categoryMapper.selectAllSourceTypes();
        for (AttachCategory category : categories) {
            if (category.getCategoryId() == null && types.contains(category.getSourceType())) {
                return category;
            }
        }
        return null;
    }
    
    
    public List<AttachCategory> selectCategoryBreadcrumbList(IRequest requestContext, Long categoryId) {
        List<AttachCategory> cates = categoryMapper.selectAllParentCategory(categoryId);
        if (cates != null && !cates.isEmpty()) {
            cates.sort((a, b) -> {
                if (a.getCategoryId().equals(b.getParentCategoryId())) {
                    return -1;
                }
                if (a.getParentCategoryId().equals(b.getCategoryId())) {
                    return 1;
                }
                return 0;
            });
            cates.forEach(a -> {
                a.setCategoryName(StringUtils.abbreviate(a.getCategoryName(), 15));
            });
        }
        return cates;
    }

}

/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.attachment;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.attachment.AttachCategory;

/**
 * Created by xiaohua on 16/2/1.
 * @author hua.xiao@hand-china.com
 */
public interface AttachCategoryMapper {

    /**
     * 根据主健查找AttachCategory.
     * 
     * @param categoryId AttachCategory主健id
     * @return AttachCategory AttachCategory对象
     */
    AttachCategory selectByPrimaryKey(Long categoryId);

    /**
     * 返回分类列表.
     * 
     * @param category AttachCategory参数
     * @return List AttachCategory列表
     */
    List<AttachCategory> selectCategories(AttachCategory category);

    /**
     * 插入分类.
     * 
     * @param attachCategory  AttachCategory对象
     * @return int 插入成功条数
     */
    int insert(AttachCategory attachCategory);

    /**
     * 根据参数查找AttachCategory.
     * 
     * @param attachCategory AttachCategory对象
     * @return AttachCategory 插入的AttachCategory对象
     */
    AttachCategory selectCategory(AttachCategory attachCategory);
    
    /**
     * 查询所有的SourceTypes 不重复.
     * 
     * @return List SourceType的List
     */
    List<String> selectAllSourceTypes();

    /**
     * 更新目录分类.
     * @param attachCategory AttachCategory
     * @return int 更新成功条数
     */
    int  updateAttachCateory(AttachCategory attachCategory);
    
    
    /**
     * 查询父节点目录.
     * 
     * @param categoryId
     * @return List
     */ 
    List<AttachCategory> selectAllParentCategory(Long categoryId);
    
    
    

}

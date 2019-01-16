/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.exception;

import com.lkkhpg.dsis.common.exception.CommInventoryException;

/**
 * 分包单Exception.
 * 
 * @author hua.xiao@hand-china.com
 *
 */
public class RepackTrxException extends CommInventoryException {

    /**
     * 
     */
    private static final long serialVersionUID = 3875501548732374558L;
    /**
     * 完成的分包单不允许删除.
     */
    public static final String COMPLETE_NOT_ALLOWD_DELETE = "type.com.lkkhpg.dsis.common.inventory.stock.complete_not_allowd_delete";
    /**
     * repack无对应的分包行信息.
     */
    public static final String NOT_INCLUDE_DETAILS = "type.com.lkkhpg.dsis.common.inventory.repack.not_include_details";
    /**
     * 批次行中有多余的行信息.
     */
    public static final String INCLUDE_MORE_DETAILS = "type.com.lkkhpg.dsis.common.inventory.repack.include_more_details";
    /**
     * 批次行中没有实际扣库存的itemId.
     */
    public static final String NO_COUNT_ITEMID = "type.com.lkkhpg.dsis.common.inventory.repack.no_count_itemid";
    
    /**
     * 重复提交.
     */
    public static final String REPEAT_SUBMIT = "type.com.lkkhpg.dsis.common.inventory.repack.repeat_submit";
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public RepackTrxException(String descriptionKey, Object[] parameters) {
        super(descriptionKey, parameters);
    }

}

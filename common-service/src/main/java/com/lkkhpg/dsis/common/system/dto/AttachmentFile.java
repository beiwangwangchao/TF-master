/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import com.lkkhpg.dsis.platform.dto.attachment.SysFile;

/**
 * 附件查询DTO.
 * 
 * @author njq.niu@hand-china.com
 *
 */
public class AttachmentFile extends SysFile {

    private static final long serialVersionUID = 3574863894807251972L;

    private Long categoryId;

    private String categoryName;
    
    private String marketCode;

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}

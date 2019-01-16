/*
 *
 */
package com.lkkhpg.dsis.platform.dto.attachment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 附件DTO.
 * 
 * @author hua.xiao
 */
@Table(name = "sys_attachment")
public class Attachment extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键，供其他表做外键.
     **/
    public static final String NORMAL = "1";

    /**
     * 表ID，主键，供其他表做外键.
     **/
    @Id
    @Column(name = "attachment_id", nullable = false)
    private Long attachmentId;
    /***
     * 分类id.
     */
    @NotEmpty
    private Long categoryId;

    /**
     * 附件名称.
     */
    @Column(name = "name")
    @NotEmpty
    private String name;

    /**
     * 附件所属业务类型.
     */
    @Column(name = "source_type")
    @NotEmpty
    private String sourceType;

    /**
     * 附件所属业务表的id.
     */
    @Column(name = "source_key")
    @NotEmpty
    private String sourceKey;

    /**
     * 附件状态.
     */
    @Column(name = "status")
    private String status;

    /**
     * 存活开始日期.
     */
    @Column(name = "start_active_date")
    private Date startActiveDate;

    /**
     * 存活结束日期.
     */
    @Column(name = "end_active_date")
    private Date endActiveDate;
    
    

    public Attachment() {
        super();
    }
    
    

    public Attachment(Long categoryId) {
        super();
        this.categoryId = categoryId;
    }



    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

}

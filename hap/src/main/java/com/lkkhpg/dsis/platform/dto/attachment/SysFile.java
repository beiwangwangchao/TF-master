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
 * 文件DTO.
 * @author hua.xiao
 */
@Table(name = "sys_file")
public class SysFile extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 文件DTO.
     * 
     * @author hua.xiao
     */
    @Id
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    /**
     * 对应的附件id.
     */
    @Column(name = "attachment_id")
    private Long attachmentId;

    /**
     * 文件名称.
     */
    @Column(name = "file_name")
    @NotEmpty
    private String fileName;

    /**
     * 文件相对项目的虚拟路径.
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 文件大小.
     */
    @Column(name = "file_size")
    @NotEmpty
    private Long fileSize;

    /**
     * 文件类型.
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 上传日期.
     */
    @Column(name = "upload_date")
    private Date uploadDate;

    public SysFile() {
        super();
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

}

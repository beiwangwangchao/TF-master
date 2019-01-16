/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 资源DTO.
 * 
 * @author wuyichu
 */
@MultiLanguage
@Table(name = "sys_resource_b")
public class Resource extends BaseDTO {

    private static final long serialVersionUID = 4235924417363951462L;

    @NotEmpty
    private String accessChecked;

    @com.lkkhpg.dsis.platform.annotation.MultiLanguageField
    @Column(name = "description")
    private String description;

    @NotEmpty
    private String loginRequire;

    @com.lkkhpg.dsis.platform.annotation.MultiLanguageField
    @Column(name = "name")
    @NotEmpty
    private String name;

    @Id
    @Column(name = "resource_id")
    private Long resourceId;

    @NotEmpty
    private String type;

    @NotEmpty
    private String url;

    public String getAccessChecked() {
        return accessChecked;
    }

    public String getDescription() {
        return description;
    }

    public String getLoginRequire() {
        return loginRequire;
    }

    public String getName() {
        return name;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setAccessChecked(String accessChecked) {
        this.accessChecked = accessChecked;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setLoginRequire(String loginRequire) {
        this.loginRequire = loginRequire == null ? null : loginRequire.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }


}
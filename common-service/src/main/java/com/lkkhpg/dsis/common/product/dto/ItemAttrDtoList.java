/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 商品参数信息集合dto.
 * 
 * @author wangchao
 *
 */
public class ItemAttrDtoList implements Serializable {
    private String language;
    private Map<String, String> content;

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}

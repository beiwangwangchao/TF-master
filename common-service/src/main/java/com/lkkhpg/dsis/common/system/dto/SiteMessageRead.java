/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * @author shiliyan
 *
 */
public class SiteMessageRead extends BaseDTO {

    @Id
    private Long readId;
    private Long messageId;
    private Long accountId;
    private String status;

    private String subject;
    private String content;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date publishDate;
    private Date publishDateFromTo;
    private Date publishDateFromDo;
    private Date publishEDate;
    private Long userId;
    private String userName;
    private String sort;
    private String sortname;
    private String sortorder;
    



    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Date getPublishDateFromTo() {
        return publishDateFromTo;
    }

    public void setPublishDateFromTo(Date publishDateFromTo) {
        this.publishDateFromTo = publishDateFromTo;
    }

    public Date getPublishDateFromDo() {
        return publishDateFromDo;
    }

    public void setPublishDateFromDo(Date publishDateFromDo) {
        this.publishDateFromDo = publishDateFromDo;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getReadId() {
        return readId;
    }

    public void setReadId(Long readId) {
        this.readId = readId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishEDate() {
        return publishDateFromDo == null ? null : addDate(publishDateFromDo);
    }

    private Date addDate(Date publishDateFromDo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(publishDateFromDo);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1); // 让日期加1
        Date time = calendar.getTime();
        return time;
    }

    public void setPublishEDate(Date publishEDate) {
        this.publishEDate = publishEDate;
    }

}

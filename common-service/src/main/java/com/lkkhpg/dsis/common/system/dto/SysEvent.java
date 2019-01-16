/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
/**
 * 事件dto.
 * 
 * @author wangc
 *
 */
@Table(name = "sys_event")
public class SysEvent extends BaseDTO {
    
    @Id
    @Column(name = "event_id")
    private Long eventId;
   
    @NotNull
    private Long marketId;

    private String eventNumber;

    @NotEmpty
    private String eventName;

    @NotEmpty
    private String eventAddress;

    @NotEmpty
    private String eventSecret;

    @NotEmpty
    private String eventType;

    @NotNull
    private Date eventDate;

    private String status;

    private BigDecimal maxMember;

    private BigDecimal participants;

    @NotEmpty
    private String description;

    @NotNull
    private Long fileId;
    
    private String marketName;
    
    private List<SysEventAssign> eventAssigns;
    
    private List<SysEventAssign> eventAssignsAll;
    
    private SpmLocation spmLocation;
    
    public SpmLocation getSpmLocation() {
        return spmLocation;
    }

    public void setSpmLocation(SpmLocation spmLocation) {
        this.spmLocation = spmLocation;
    }

    public List<SysEventAssign> getEventAssigns() {
        return eventAssigns;
    }

    public void setEventAssigns(List<SysEventAssign> eventAssigns) {
        this.eventAssigns = eventAssigns;
    }

    public List<SysEventAssign> getEventAssignsAll() {
        return eventAssignsAll;
    }

    public void setEventAssignsAll(List<SysEventAssign> eventAssignsAll) {
        this.eventAssignsAll = eventAssignsAll;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(String eventNumber) {
        this.eventNumber = eventNumber == null ? null : eventNumber.trim();
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress == null ? null : eventAddress.trim();
    }

    public String getEventSecret() {
        return eventSecret;
    }

    public void setEventSecret(String eventSecret) {
        this.eventSecret = eventSecret == null ? null : eventSecret.trim();
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType == null ? null : eventType.trim();
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(BigDecimal maxMember) {
        this.maxMember = maxMember;
    }

    public BigDecimal getParticipants() {
        return participants;
    }

    public void setParticipants(BigDecimal participants) {
        this.participants = participants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

}
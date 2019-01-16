/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 用户对象.
 * 
 * @author njq.niu@hand-china.com
 */
public class User extends Account {

    public static final String FILED_USER_ID = "userId";
    public static final String FIELD_USER_NAME = "userName";
    public static final String FIELD_USER_TYPE = "userType";

    private static final long serialVersionUID = -1938818306103232890L;

    // 默认内部用户，acv状态
    public static final String TYPE_INNER = "INNER";
    public static final String STATUS_ACTIVE = "ACTV";
    
    // 用户类型 - IPOINT用户
    public static final String USER_TYPE_IPOINT = "IPONT";
    
    // 邮箱
    @JsonInclude(Include.NON_NULL)
    @NotEmpty
    private String email;
    // 手机
    @JsonInclude(Include.NON_NULL)
    @NotEmpty
    private String phoneNo;

    @JsonInclude(Include.NON_NULL)
    private String countryCode;

    @JsonInclude(Include.NON_NULL)
    private String areaCode;

    @JsonInclude(Include.NON_NULL)
    private String phoneExtNo;
    // 状态
    @JsonInclude(Include.NON_NULL)
    private String status;

    private Long userId;

    private String areaPhoneUser;
    // 用户名
    @NotEmpty
    private String userName;
    // 用户类型
    @JsonInclude(Include.NON_NULL)
    private String userType;

    public String getAreaPhoneUser() {
        if (areaCode == null) {
            return phoneNo;
        } else {
            return areaCode + phoneNo;
        }
    }

    public void setAreaPhoneUser(String areaPhoneUser) {
        this.areaPhoneUser = areaPhoneUser;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoneExtNo() {
        return phoneExtNo;
    }

    public void setPhoneExtNo(String phoneExtNo) {
        this.phoneExtNo = phoneExtNo;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

}
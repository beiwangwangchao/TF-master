/*
 *
 */
package com.lkkhpg.dsis.common.user.dto;

import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * ipoint用户.
 * 
 * @author runbai.chen
 */
public class IpointUser extends User {

    private static final long serialVersionUID = 1L;

    private Long memberId;

    private String englishName;

    private String chineseName;

    private String memberNumber;
    
    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    
}

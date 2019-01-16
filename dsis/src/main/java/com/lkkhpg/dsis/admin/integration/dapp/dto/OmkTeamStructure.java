/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * Team Structure DTO.
 * 
 * @author linyuheng
 *
 */
public class OmkTeamStructure {

    private String dealerNo;

    private Long cnt10k;

    private Long cnt100k;

    private Long cnt500k;

    private Long orgMemberCnt;

    private Long orgMemberCntNew;

    private String orgCode;

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public Long getCnt10k() {
        return cnt10k;
    }

    public void setCnt10k(Long cnt10k) {
        this.cnt10k = cnt10k;
    }

    public Long getCnt100k() {
        return cnt100k;
    }

    public void setCnt100k(Long cnt100k) {
        this.cnt100k = cnt100k;
    }

    public Long getCnt500k() {
        return cnt500k;
    }

    public void setCnt500k(Long cnt500k) {
        this.cnt500k = cnt500k;
    }

    public Long getOrgMemberCnt() {
        return orgMemberCnt;
    }

    public void setOrgMemberCnt(Long orgMemberCnt) {
        this.orgMemberCnt = orgMemberCnt;
    }

    public Long getOrgMemberCntNew() {
        return orgMemberCntNew;
    }

    public void setOrgMemberCntNew(Long orgMemberCntNew) {
        this.orgMemberCntNew = orgMemberCntNew;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}

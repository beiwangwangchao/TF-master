/*
 *
 */
package com.lkkhpg.dsis.common.bonus.dto;

import java.util.Date;
import java.util.List;

import com.lkkhpg.dsis.common.member.dto.MemWithdraw;

/**
 * Bonus Release Combine DTO.
 * @author runbai.chen
 */
public class BonusReleaseCombine {

    private Long periodId;
    
    private Long marketId;
    
    private String bonusType;
    
    private Date remitDate;
    
    private List<BonusRetransfer> retransfers;
    
    private List<MemWithdraw> memWithdraws;

    private String retransferStatus;
    
    private String releaseStatus;
    
    

    public String getRetransferStatus() {
        return retransferStatus;
    }

    public void setRetransferStatus(String retransferStatus) {
        this.retransferStatus = retransferStatus;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public List<BonusRetransfer> getRetransfers() {
        return retransfers;
    }

    public void setRetransfers(List<BonusRetransfer> retransfers) {
        this.retransfers = retransfers;
    }

    public List<MemWithdraw> getMemWithdraws() {
        return memWithdraws;
    }

    public void setMemWithdraws(List<MemWithdraw> memWithdraws) {
        this.memWithdraws = memWithdraws;
    }

    public Date getRemitDate() {
        return remitDate;
    }

    public void setRemitDate(Date remitDate) {
        this.remitDate = remitDate;
    }
    
}

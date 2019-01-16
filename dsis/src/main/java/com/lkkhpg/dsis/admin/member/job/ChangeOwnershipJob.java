/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.ChangeOwnership;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.job.AbstractJob;

/**
 * 定时变更会员所有权.
 * 
 * @author frank.li
 */
public class ChangeOwnershipJob extends AbstractJob {

    private Logger logger = LoggerFactory.getLogger(ChangeOwnershipJob.class);

    @Autowired
    private IMemberService memberService;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 自动变更会员所有权.
     * 
     * @param context
     *            计划任务上下文
     */
    @Override
    public void safeExecute(JobExecutionContext context) throws IntegrationException {

        List<ChangeOwnership> changeOwnerships = memberMapper.selectWaitingChgMember();
        String results = null;
        for (ChangeOwnership changeOwnership : changeOwnerships) {

            if (logger.isDebugEnabled()) {
                logger.debug("sourceMemberId : {}", changeOwnership.getSourceMemberId());
                logger.debug("tempMemberId : {}", changeOwnership.getTempMemberId());
            }

            IRequest request = RequestHelper.newEmptyRequest();
            request.setAccountId(-1L);
            request.setLocale(BaseConstants.DEFAULT_LANG);
            try {
                memberService.changeOwnership(request, changeOwnership.getSourceMemberId(),
                        changeOwnership.getTempMemberId());
                results = (results == null ? "" : (results + ",")) + changeOwnership.getTempMemberCode()
                        + " change success";
                // 只显示2000个字符加上省略号
                if (results.length() > MemberConstants.CHANGE_OWNER_RESULT_MAX_LENGTH) {
                    results = results.substring(0, MemberConstants.CHANGE_OWNER_RESULT_MAX_LENGTH) + "...";
                }
            } catch (MemberException e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
                results = (results == null ? "" : (results + ",")) + changeOwnership.getTempMemberCode()
                        + " change failed";
                // 只显示2000个字符加上省略号
                if (results.length() > MemberConstants.CHANGE_OWNER_RESULT_MAX_LENGTH) {
                    results = results.substring(0, MemberConstants.CHANGE_OWNER_RESULT_MAX_LENGTH) + "...";
                }
            }
        }

        this.setExecutionSummary(results);

    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        // TODO Auto-generated method stub
        return false;
    }

}

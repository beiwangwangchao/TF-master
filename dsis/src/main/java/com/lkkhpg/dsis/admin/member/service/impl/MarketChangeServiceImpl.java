/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IDeleteGdealerChgOrgAppService;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindGdealerChgOrgAppAuditListService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.integration.gds.service.IIsgMarketChangeListService;
import com.lkkhpg.dsis.admin.integration.gds.service.ISaveGdealerChgOrgAppService;
import com.lkkhpg.dsis.admin.member.service.IMarketChangeService;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.admin.system.DsisServiceRequest;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemMarketChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.IManualMessageService;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTResponse;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;

/**
 * 会员市场变更接口实现类.
 *
 * @author linyuheng
 */
@Service
@Transactional
public class MarketChangeServiceImpl implements IMarketChangeService {

    private Logger logger = LoggerFactory.getLogger(MarketChangeServiceImpl.class);

    @Autowired
    private MemMarketChangeMapper memMarketChangMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IFindGdealerChgOrgAppAuditListService findGdealerChgOrgAppAuditListService;

    @Autowired
    private IIsgMarketChangeListService isgMarketChangeListService;

    @Autowired
    private ISaveGdealerChgOrgAppService saveGdealerChgOrgAppService;

    @Autowired
    private IDeleteGdealerChgOrgAppService deleteGdealerChgOrgAppService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IManualMessageService manualMessageService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IMarketParamService marketParamService;

    /**
     * 查询市场变更记录.
     *
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 会员变更市场记录列表
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MemMarketChange> queryMarketChange(IRequest request, MemMarketChange memMarketChange, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<MemMarketChange> marketChanges = memMarketChangMapper.queryMarketChange(memMarketChange);

        for (MemMarketChange marketchange_tmp : marketChanges) {
            setMemberName(marketchange_tmp);
        }

        return marketChanges;
    }

    /**
     * 查询市场变更记录(审核中).
     *
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 会员变更市场记录列表
     */
    @Override
    public List<MemMarketChange> queryApprovingMarketChange(IRequest request, MemMarketChange memMarketChange, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        // memMarketChange.setApprovalStatus(MemberConstants.MM_MARKETCHANGE_NEW);
        List<MemMarketChange> marketchanges = memMarketChangMapper.queryApprovingMarketChange(memMarketChange);

        for (MemMarketChange marketchange_tmp : marketchanges) {
            setMemberName(marketchange_tmp);
        }

        return marketchanges;
    }

    private void setMemberName(MemMarketChange marketchange_tmp) {
        String chineseName = marketchange_tmp.getChineseName();
        String englishName = marketchange_tmp.getEnglishName();
        if (englishName != null && chineseName != null) {
            marketchange_tmp.setMemberName(englishName + "/" + chineseName);
        } else if (englishName != null) {
            marketchange_tmp.setMemberName(englishName);
        } else {
            marketchange_tmp.setMemberName(chineseName);
        }
    }

    /**
     * 批准市场变更.
     *
     * @param iRequest
     *            请求上下文
     * @param request
     *            原生请求上下文
     * @param marketChange
     *            会员变更市场DTO
     * @return 更新条数
     * @throws Exception
     *             异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approveMarketChange(IRequest iRequest, HttpServletRequest request, MemMarketChange marketChange)
            throws Exception {
        return isgMarketChangeListService.saveGdealerChgOrgAppAudit(iRequest, marketChange);
    }

    /**
     * 查询原市场.
     *
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @return 会员变更市场DTO集合
     */
    @Override
    public List<MemMarketChange> queryOldMarket(IRequest request, MemMarketChange memMarketChange) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberCode", memMarketChange.getMemberCode());
        map.put("marketId", memMarketChange.getMarketId());
        Member member = memberMapper.selectValidMemberByMemberCode(map);
        ArrayList<MemMarketChange> memMarketChanges = new ArrayList<MemMarketChange>();
        if (member != null) {

            if (member.getStatus().equals(MemberConstants.MEMBER_STATUS_ACTIVE)) {
                Member member_marketid = memberService.queryMarketId(request, member.getMemberId());
                SpmMarket spmMarket = spmMarketMapper.selectByPrimaryKey(member_marketid.getMarketId());
                String marketName = spmMarket.getName();
                memMarketChange.setMarketName(marketName);
                memMarketChange.setMemberId(member.getMemberId());
                memMarketChange.setFromMarketId(spmMarket.getMarketId());
                memMarketChange.setMemberCode(memMarketChange.getMemberCode());
                memMarketChanges.add(memMarketChange);
                return memMarketChanges;
            } else {
                memMarketChange.setFromMarketId(null);
                memMarketChange.setMarketName("");
                memMarketChanges.add(memMarketChange);
            }
        } else {
            memMarketChange.setFromMarketId(null);
            memMarketChange.setMarketName("");
            memMarketChanges.add(memMarketChange);
        }
        return memMarketChanges;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String submitUpstreamChange(IRequest request, MemMarketChange memMarketChange) throws IntegrationException {

        // 将变更申请传给 gds
        ApplicationsPOSTResponse response = saveGdealerChgOrgAppService.saveGdealerChgOrgApp(request, memMarketChange);

        return response.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MemMarketChange> queryNewMarketChangeFromGds(IRequest request, String subOrg, String orgCode)
            throws Exception {
        return findGdealerChgOrgAppAuditListService.findGdealerChgOrgAppAuditList(request, subOrg, orgCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMarketChange(IRequest iRequest, List<MemMarketChange> memMarketChanges)
            throws IntegrationException {
        String orgCode = gdsUtilService.getCurrentOrgCode(iRequest);
        for (MemMarketChange memMarketChange : memMarketChanges) {
            Boolean flag = deleteGdealerChgOrgAppService.deleteGdealerChgOrgApp(iRequest, memMarketChange.getGdsId(),
                    orgCode);
            if (flag) {
                memMarketChangMapper.deleteMarketChangeByChangeId(memMarketChange.getChangeId());
            } else {
                throw new IntegrationException(IntegrationException.GDS_INVOKE_FAILED, null);
            }
        }
    }

    /**
     * 给市场变更审核人发送站内信.
     *
     * @param request
     *            统一上下文
     * @param salesOrgId
     *            销售组织Id
     * @return true-发送成功；false-发送失败
     * @throws Exception
     *             异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean sendMessage(DsisServiceRequest request, Long salesOrgId, Long marketId) throws Exception {
        // 获取市场变更审核人
        List<String> lists = marketParamService.getParamValues(request,
                SystemProfileConstants.PARAM_CHANGE_MARKET_AUDITER, SystemProfileConstants.MARKET,
                String.valueOf(marketId), SystemProfileConstants.ORG_TYPE_MARKET);
        if (lists.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("MarketChange approval user not found! marketId : {}", marketId);
                return false;
            }
        }
        // 根据id列表获取审批人DTO列表
        List<User> users = new ArrayList<User>();
        for (String userId : lists) {
            Long _userId = Long.parseLong(userId);
            // Long userId = 1L;
            User user = new User();
            user.setUserId(_userId);
            List<User> _users = userMapper.selectUsers(user);
            if (_users == null || _users.isEmpty() || _users.get(0).getAccountId() == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The User not found Account information!User : {}", new Object[] { userId });
                }
            } else {
                users.add(_users.get(0));
            }
        }
        // 组装站内信模板
        SysMsMessage sysMsMessage = new SysMsMessage();
        sysMsMessage.setPublishChannel(String.valueOf(MessageTypeEnum.DSIS));
        sysMsMessage.setSenderCode("-1");
        sysMsMessage.setSender(1L); // admin用户

        for (User _user : users) {
            List<SysMsMessageAssign> sysMsMessageAssigns = new ArrayList<SysMsMessageAssign>();
            SysMsMessageAssign sysMsMessageAssign = new SysMsMessageAssign();
            sysMsMessageAssign.setAssignType(SysMsMessageAssign.USER);
            sysMsMessageAssign.setAssignValue(String.valueOf(_user.getUserId()));
            sysMsMessageAssign.setAccountId(_user.getAccountId());
            sysMsMessageAssigns.add(sysMsMessageAssign);
            Map<String, Object> data = new HashMap<String, Object>();
            manualMessageService.publishMessage(sysMsMessage, sysMsMessageAssigns,
                    MemberConstants.MM_MARKETCHANGE_SEND_MESSAGE, data, null);
        }

        return true;
    }
}

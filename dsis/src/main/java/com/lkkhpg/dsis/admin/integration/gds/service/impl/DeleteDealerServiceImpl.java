/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgDiffCheck;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgDiffCheckMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IDeleteDealerService;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * 删除会员资料实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class DeleteDealerServiceImpl implements IDeleteDealerService {

    private static final String PROFILE_NAME = "itm.auto_invoke_delete_member_int";

    private Logger logger = LoggerFactory.getLogger(DeleteDealerServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IsgDiffCheckMapper isgDiffCheckMapper;

    /**
     * 获取差异会员卡号列表.
     */
    @Override
    public List<BatchDeletePOSTBody> getDealers(String adviseNo) {
        List<BatchDeletePOSTBody> batchDeletePOSTBodies = new ArrayList<BatchDeletePOSTBody>();
        List<IsgDiffCheck> isgDiffChecks = isgDiffCheckMapper.selectByAdviseNo(adviseNo);
        IRequest iRequest = RequestHelper.newEmptyRequest();
        String profileValue = profileService.getProfileValue(iRequest, PROFILE_NAME);
        if (BaseConstants.YES.equals(profileValue)) {
            for (IsgDiffCheck isgDiffCheck : isgDiffChecks) {
                String checkEntityNo = isgDiffCheck.getCheckEntityNo();
                // 与POS的会员表进行比对
                // Member memberById =
                // memberMapper.selectValidMemberByMemberCode(checkEntityNo);
                // 若POS会员表不存在该条数据则添加进差异会员卡号列表中
                // if (memberById == null) {
                BatchDeletePOSTBody batchDeletePOSTBody = new BatchDeletePOSTBody();
                batchDeletePOSTBody.setDealerNo(checkEntityNo);
                batchDeletePOSTBodies.add(batchDeletePOSTBody);
                // }
            }
            return batchDeletePOSTBodies;
        } else {
            return null;
        }
    }

    /**
     * 删除GDS会员表更新.
     */
    @Override
    public void deleteDealers(String adviseNo, List<BatchDeletePOSTBody> requestData,
            List<BatchDeletePOSTResponse> response, Exception exception) {
        if (exception == null) {
            for (BatchDeletePOSTResponse batchDeletePOSTResponse : response) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("adviseNo", adviseNo);
                map.put("checkEntityType", "*DLR");
                map.put("checkEntityNo", batchDeletePOSTResponse.getDealerNo());
                // map.put("synStatus", batchDeletePOSTResponse.getCode());
                // map.put("synMessage", batchDeletePOSTResponse.getMessage());
                isgDiffCheckMapper.updateUploadFlag(map);
            }
        } else {
            if (logger.isErrorEnabled()) {
                logger.error(exception.getMessage(), exception);
            }
            throw new RuntimeException(new IntegrationException(exception.getMessage(), null));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDealers(String orgCode, String adviseNo, String checkEntityNo) throws IntegrationException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("adviseNo", adviseNo);
        map.put("checkEntityType", IntegrationConstant.CHECK_ENTITY_TYPE_DLR);
        map.put("checkEntityNo", checkEntityNo);
        try {
            List<BatchDeletePOSTBody> batchDeletePOSTBodies = new ArrayList<BatchDeletePOSTBody>();
            map.put("processStatus", IntegrationConstant.PROCESS_STATUS_S);
            map.put("processMessage", IntegrationConstant.PROCESS_MESSAGE_SUCCESS);
            map.put("uploadFlag", IntegrationConstant.YES);
            map.put("memberCode", checkEntityNo);
            memberMapper.updateStatusByMemberCode(map);
            // }
            BatchDeletePOSTBody batchDeletePOSTBody = new BatchDeletePOSTBody();
            batchDeletePOSTBody.setDealerNo(checkEntityNo);
            batchDeletePOSTBodies.add(batchDeletePOSTBody);
            // }
            List<BatchDeletePOSTResponse> responses = gdsService.deleteDealer(adviseNo, orgCode, batchDeletePOSTBodies);
            for (BatchDeletePOSTResponse response : responses) {
                if (Objects.equals(response.getDealerNo(), checkEntityNo) && response.getSuccess()) {
                    return true;
                } else {
                    map.put("processStatus", IntegrationConstant.PROCESS_STATUS_E);
                    map.put("processMessage", response.getMessage());
                    map.put("uploadFlag", IntegrationConstant.NO);
                    throw new IntegrationException(response.getMessage(), null);
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_DELETE_DEALER, e);
            }
            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_DELETE_DEALER,
                    new Object[] { ((IntegrationException) e).getMessage() });
        } finally {
            self().updateInterfaceStatus(map);
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateInterfaceStatus(Map<String, Object> map) {
        isgDiffCheckMapper.updateByAdviseAndEntityNo(map);
    }
}

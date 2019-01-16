/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgSaleBranchList;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgSaleBranchListMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindSaleBranchListService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.service.ISpmMarketService;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.orgList.model.OrgListGETResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 获取销售分公司列表.
 * 
 * @author chuangsheng.zhang.
 */
@Transactional
@Service
public class FindSaleBranchListServiceImpl implements IFindSaleBranchListService {

    private Logger logger = LoggerFactory.getLogger(FindSaleBranchListServiceImpl.class);
    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IsgSaleBranchListMapper isgSaleBranchListMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private ISpmMarketService spmMarketService;
    
    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<SpmMarket> findSaleBranchList(IRequest request, Long fromMarketId) throws IntegrationException {
        List<SpmMarket> newSpmMarkets = new ArrayList<SpmMarket>();
        try {

            String orgCode = gdsUtilService.getCurrentOrgCode(request);

            orgCode = gdsUtilService.getGdsOrgCode(request, orgCode);

            // 查询POS中旧市场的ID
            SpmMarket tmpMarket = spmMarketService.queryByMarketId(request, fromMarketId);
            if (tmpMarket == null) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_OLD_MARKET_NOT_NULL, null);
            }
            // 转换成GDS的编码
            String saleOrgCode = gdsUtilService.getGdsOrgCode(request, tmpMarket.getCode());

            List<OrgListGETResponse> branchList = gdsService.findSaleOrgInfoList(saleOrgCode, request.getLocale(),
                    orgCode);

            if (!branchList.isEmpty()) {
                for (OrgListGETResponse branch : branchList) {
                    String gdsOrgCode = branch.getCode();
                    String posOrgCode;

                    try {
                        // 将gds返给的编码解释成pos的编码
                        posOrgCode = gdsUtilService.getOrgCode(request, gdsOrgCode);
                        if (StringUtils.isEmpty(posOrgCode)) {

                            if (logger.isDebugEnabled()) {
                                logger.debug("gdsSaleOrgCode : {} is not exist in the mapping", gdsOrgCode);
                            }
                            continue;
                        }
                    } catch (Exception e) {
                        // gdsOrgCode未在快码ISG.GDS_ORG_CODE_MAPPING维护映射，不显示，继续获取下一个orgCode
                        continue;
                    }

                    // 查询市场
                    SpmMarket market = new SpmMarket();
                    market.setCode(posOrgCode);
                    market = spmMarketMapper.selectMarketByCode(posOrgCode);
                    if (market == null) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("posSaleOrgCode : {} is not exist ,or multi value", posOrgCode);
                        }
                        continue;
                    }

                    newSpmMarkets.add(market);
                }
            }

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, e);
            }
            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_ORG_LIST,
                    new Object[] { e.toString(), ":", e.getMessage() });
        }

        return newSpmMarkets;
    }

    /**
     * 批量保存销售分公司列表.
     * 
     * @param request
     *            请求上下文
     * @param branchList
     *            接口返回的值
     * @param errorMessage
     *            错误消息
     * @return 销售分公司列表
     */
    @Transactional(rollbackFor = Exception.class)
    public List<OrgListGETResponse> batchUpdate(IRequest request, List<OrgListGETResponse> branchList,
            String errorMessage) {
        if (branchList == null || branchList.isEmpty()) {
            IsgSaleBranchList saleBranch = new IsgSaleBranchList();
            Date newDate = new Date();
            saleBranch.setProcessDate(newDate);
            saleBranch.setProcessMessage(errorMessage);
            saleBranch.setProcessStatus(IntegrationConstant.PROCESS_STATUS_E);
            isgSaleBranchListMapper.insert(saleBranch);

            return null;
        }
        List<OrgListGETResponse> isgSaleBranchLists = new ArrayList<OrgListGETResponse>();
        for (OrgListGETResponse response : branchList) {

            IsgSaleBranchList saleBranch = new IsgSaleBranchList();

            saleBranch.setSaleOrgCode(response.getCode());
            saleBranch.setSaleBranchCode(null);
            saleBranch.setSaleBranchDesc(response.getValue());
            saleBranch.setSaleBranchDesc2(null);
            saleBranch.setSaleRegionCode(null);
            Date newDate = new Date();
            saleBranch.setProcessDate(newDate);
            saleBranch.setProcessMessage(errorMessage);
            saleBranch.setProcessStatus(IntegrationConstant.PROCESS_STATUS_S);

            isgSaleBranchListMapper.insert(saleBranch);

        }

        return isgSaleBranchLists;
    }

}

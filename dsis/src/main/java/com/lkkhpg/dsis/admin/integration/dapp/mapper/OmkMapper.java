/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.mapper;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkAcctBalanceRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkAcctBalanceResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDealerDetallRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDealerDetallResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDownlinePerforRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDownlinePerforResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamBalanceRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamBalanceResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructure;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructureRequest;

/**
 * OmkMapper.
 * 
 * @author zhenyang.he
 */
public interface OmkMapper {

    /**
     * 根据会员卡号，语言类型查询会员账号余额.
     * 
     * @param omkAcctBalanceRequest
     *            请求的数据
     * @return 相应数据
     */
    OmkAcctBalanceResponse getAccountBalance(OmkAcctBalanceRequest omkAcctBalanceRequest);

    /**
     * 根据会员卡号查询会员详细信息.
     * 
     * @param omkDealerDetallRequest
     *            请求的数据
     * @return 会员详细信息
     */
    OmkDealerDetallResponse getDealerDetall(OmkDealerDetallRequest omkDealerDetallRequest);

    /**
     * 根据会员卡号查询团队余额.
     * 
     * @param omkTeamBalanceRequest
     *            请求数据
     * @return 团队余额信息
     */
    OmkTeamBalanceResponse getTeamBalance(OmkTeamBalanceRequest omkTeamBalanceRequest);

    /**
     * 根据会员卡号和期间查询.
     * 
     * @param omkTeamStructureRequest
     *            请求数据
     * @return Team Structure
     */
    List<OmkTeamStructure> getTeamStructure(OmkTeamStructureRequest omkTeamStructureRequest);

    /**
     * 根据推荐人卡号，语言类型查询.
     * 
     * @param omkDownlinePerforRequest
     *            请求的数据
     * @return 相应数据
     */
    List<OmkDownlinePerforResponse> getOmkDownlinePerfor(OmkDownlinePerforRequest omkDownlinePerforRequest);
}

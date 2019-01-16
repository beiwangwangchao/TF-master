/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructureRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructureResponse;

/**
 * TeamStructure 接口类.
 * 
 * @author linyuheng
 */
public interface IOmkTeamStructureService {

    /**
     * 获取Team Structure.
     * 
     * @param omkTeamStructureResquest
     *            TeamStructure 请求DTO.
     * @return OmkTeamStructureResponse TeamStructure 返回DTO
     */
    OmkTeamStructureResponse getTeamStructure(OmkTeamStructureRequest omkTeamStructureResquest);
}

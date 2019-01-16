/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructure;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructureRegion;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructureRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructureResponse;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.OmkMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkTeamStructureService;

/**
 * TeamStructure 实现类.
 * 
 * @author linyuheng
 *
 */
@Service
public class OmkTeamStructureServiceImpl implements IOmkTeamStructureService {

    @Autowired
    private OmkMapper omkMapper;

    @Override
    public OmkTeamStructureResponse getTeamStructure(OmkTeamStructureRequest omkTeamStructureRequest) {

        List<OmkTeamStructure> teamStructures = omkMapper.getTeamStructure(omkTeamStructureRequest);
        OmkTeamStructureResponse omkTeamStructureResponse = new OmkTeamStructureResponse();
        if (teamStructures != null && teamStructures.size() > 0) {
            OmkTeamStructure teamStructure = teamStructures.get(0);
            omkTeamStructureResponse.setDistributorID(teamStructure.getDealerNo());
            omkTeamStructureResponse.setTotal(String.valueOf(teamStructure.getOrgMemberCnt()));
            omkTeamStructureResponse.setTotalNew(String.valueOf(teamStructure.getOrgMemberCntNew()));
            omkTeamStructureResponse.setCnt10K(String.valueOf(teamStructure.getCnt10k()));
            omkTeamStructureResponse.setCnt100K(String.valueOf(teamStructure.getCnt100k()));
            omkTeamStructureResponse.setCnt500K(String.valueOf(teamStructure.getCnt500k()));

            List<OmkTeamStructureRegion> regions = new ArrayList<OmkTeamStructureRegion>();
            for (OmkTeamStructure omkTeamStructure : teamStructures) {
                OmkTeamStructureRegion region = new OmkTeamStructureRegion();
                region.setCntNew(String.valueOf(omkTeamStructure.getOrgMemberCntNew()));
                region.setRegionCode(omkTeamStructure.getOrgCode());
                region.setTotal(String.valueOf(omkTeamStructure.getOrgMemberCnt()));

                regions.add(region);
            }

            omkTeamStructureResponse.setRegion(regions);
        }
        return omkTeamStructureResponse;
    }
}

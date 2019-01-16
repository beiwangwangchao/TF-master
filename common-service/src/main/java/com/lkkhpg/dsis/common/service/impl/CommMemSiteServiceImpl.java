/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.service.ICommMemSiteService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员地点Service接口实现.
 * 
 * @author frank.li
 */
@Service
@Transactional
public class CommMemSiteServiceImpl implements ICommMemSiteService {

    private final Logger logger = LoggerFactory.getLogger(CommMemSiteServiceImpl.class);

    @Autowired
    private MemSiteMapper memSiteMapper;

    @Autowired
    private SpmLocationMapper spmLocationMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public MemSite saveMemSite(IRequest request, MemSite memSite) {
        if (memSite.getSiteId() == null) {
            // 如果locationId为空则先创建SpmLocation
            if (memSite.getLocationId() == null) {
                spmLocationMapper.insert(memSite.getSpmLocation());
            }
            memSite.setLocationId(memSite.getSpmLocation().getLocationId());
            // 创建会员地点
            memSiteMapper.insert(memSite);
            if (BaseConstants.YES.equals(memSite.getDefaultFlag())) {
                memSiteMapper.updateDefaultFlag(memSite.getMemberId(), memSite.getSiteUseCode(), memSite.getLocationId());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Create memSite.");
                logger.debug("memSite.siteId: {}", new Object[] { memSite.getSiteId() });
                if (memSite.getSpmLocation() != null && memSite.getSpmLocation().getLocationId() != null) {
                    logger.debug("memSite.spmLocation.locationId: {}",
                            new Object[] { memSite.getSpmLocation().getLocationId() });
                }
            }
        } else {
            // 更新会员地点
            memSiteMapper.updateByPrimaryKeySelective(memSite);
            if (BaseConstants.YES.equals(memSite.getDefaultFlag())) {
                memSiteMapper.updateDefaultFlag(memSite.getMemberId(), memSite.getSiteUseCode(), memSite.getLocationId());
            }
            // 更新地址
            if (memSite.getSpmLocation() != null) {
                self().updateSpmLocation(memSite.getSpmLocation());
            }
        }

        return memSite;
    };

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean deleteMemSite(IRequest request, MemSite site) {
        // 删除地址
        MemSite memSite = memSiteMapper.selectByPrimaryKey(site.getSiteId());
        SpmLocation sl = new SpmLocation();
        sl.setLocationId(memSite.getLocationId());
        spmLocationMapper.deleteByPrimaryKey(sl);

        // 删除会员地点
        memSiteMapper.deleteByPrimaryKey(site);

        return true;
    }

    @Override
    public List<MemSite> queryMemSite(IRequest request, MemSite memSite) {
        return memSiteMapper.selectMemSite(memSite);
    };
    
    /**
     * 更新SPM地点.
     * @param spmLocation 地点DTO
     * @return 执行结果boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateSpmLocation(SpmLocation spmLocation) {
        //如果地点代码和名称为空则以ID填充
        if (spmLocation.getLocationCode() == null) {
            spmLocation.setLocationCode(spmLocation.getLocationId().toString());
        }
        if (spmLocation.getName() == null) {
            spmLocation.setName(spmLocation.getLocationId().toString());
        }
        if (spmLocation.getAddressLine2() == null) {
            spmLocation.setAddressLine2("");
        }
        if (spmLocation.getAddressLine3() == null) {
            spmLocation.setAddressLine3("");
        }
        if (spmLocation.getAddressLine4() == null) {
            spmLocation.setAddressLine4("");
        }
        if (spmLocation.getAddressLine5() == null) {
            spmLocation.setAddressLine5("");
        }
        if (spmLocation.getZipCode() == null) {
            spmLocation.setZipCode("");
        }
        spmLocationMapper.updateByPrimaryKeySelective(spmLocation);
        return true;
    }
}
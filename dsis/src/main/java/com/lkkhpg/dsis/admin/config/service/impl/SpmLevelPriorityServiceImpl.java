/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmLevelPriorityService;
import com.lkkhpg.dsis.common.config.dto.SpmLevelPriority;
import com.lkkhpg.dsis.common.config.mapper.SpmLevelPriorityMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
/**
 * 组织层次优先级.
 * @author liuxuan
 *
 */
@Service
@Transactional
public class SpmLevelPriorityServiceImpl implements ISpmLevelPriorityService {
    @Autowired
    private SpmLevelPriorityMapper spmLevelPriorityMapper;

    @Override
    public List<SpmLevelPriority> selectLevel(IRequest request, SpmLevelPriority spmLevelPriority, int page,
            int pagesize) {
       List<SpmLevelPriority> orgs = spmLevelPriorityMapper.selectAllLevel(spmLevelPriority);
     
        return orgs;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SpmLevelPriority> saveLevelPrioty(IRequest request, List<SpmLevelPriority> spmLevelPriority)
            throws SystemProfileException {
        for (SpmLevelPriority spmLevelPrioritys : spmLevelPriority) {
            if (spmLevelPriorityMapper.selectSamePriority(spmLevelPrioritys) > 0) {
                throw new SystemProfileException(SystemProfileException.MSG_ERROR_SPM_PRIORITY_VALUE_REPEAT, null);
            }
            spmLevelPriorityMapper.updateLevel(spmLevelPrioritys);
        }
        return spmLevelPriority;  
    }
}

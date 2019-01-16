/*
 *
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.SysPreferences;
import com.lkkhpg.dsis.platform.mapper.system.SysPreferencesMapper;
import com.lkkhpg.dsis.platform.service.ISysPreferencesService;
/**
 * 系统首选项service.
 * 
 * @author zhangYang
 */
@Service
@Transactional
public class SysPreferencesServiceImpl implements ISysPreferencesService {
    private Logger logger = LoggerFactory.getLogger(SysPreferencesServiceImpl.class);

    @Autowired
    private SysPreferencesMapper sysPreferencesMapper;
    /**
     * 保存首选项.
     * 
     * @param requestContext
     *          系统上下文
     * @param preferences
     *          首选项信息集合
     * @return List<SysPreferences>
     *          返回保存结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SysPreferences> saveSysPreferences(IRequest requestContext, List<SysPreferences> preferences) {
        if(preferences.isEmpty()){
            if (logger.isDebugEnabled()) {
                logger.debug("sysPreferences is null");
            }
        }
        for(SysPreferences sysPreferences:preferences){
          //判断该首选项信息是否存在，存在更新，否则新增
            if(self().querySysPreferencesLine(requestContext, sysPreferences)==null){
                sysPreferencesMapper.insertSelective(sysPreferences);
            }else{
                sysPreferencesMapper.updatePreferLine(sysPreferences);
            }
            
        }
        return preferences;
    }
    
    /**
     * 查询首选项.
     * 
     * @param request
     * @param sysPreferences
     *          根据SysPreferences.accountId;SysPreferences.preferencesLevel查询条件
     *          查询当前首选项
     * @return
     */
    @Override
    public List<SysPreferences> querySysPreferences(IRequest requestContext, SysPreferences preferences) {
        return sysPreferencesMapper.selectPreferences(preferences);
    }

    /**
     * 查询当前用户首选项集合
     * 
     * @param request
     * @param sysPreferences
     *            根据SysPreferences.accountId;SysPreferences.preferencesLevel查询条件
     *            查询当前首选项
     * @return
     */
    @Override
    public SysPreferences querySysPreferencesLine(IRequest requestContext, SysPreferences preferences) {
        return sysPreferencesMapper.selectPreferLine(preferences);
    }
    
    

}

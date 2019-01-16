/*
 *
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.SysPreferences;

/**
 * 系统首选项接口.
 * 
 * @author zhangYang
 *
 */
public interface ISysPreferencesService extends ProxySelf<ISysPreferencesService> {

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
    List<SysPreferences> saveSysPreferences(IRequest requestContext, @StdWho List<SysPreferences> preferences);
    
    /**
     * 查询首选项.
     * 
     * @param requestContext
     *          系统上下文
     * @param preferences
     *          查询参数
     * @return
     *          返回查询得到的首选项集合
     */
    List<SysPreferences> querySysPreferences(IRequest requestContext, SysPreferences preferences);
    
    /**
     * 查询首选项具体一项.
     * 
     * @param requestContext
     *          系统上下文
     * @param preferences
     *          查询参数
     * @return
     */
    SysPreferences querySysPreferencesLine(IRequest requestContext, SysPreferences preferences);
    
}

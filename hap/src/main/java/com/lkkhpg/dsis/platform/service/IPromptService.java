/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Prompt;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IPromptService extends ProxySelf<IPromptService> {

    /**
     * 创建.
     * 
     * @param prompt
     *            新数据
     * @return 是否成功
     */
    Prompt createPrompt(Prompt prompt);

    /**
     * 删除.
     * 
     * @param prompt
     *            待删数据
     */
    void deletePrompt(Prompt prompt);

    /**
     * 更新.
     * 
     * @param prompt
     *            新数据
     * @return 是否成功
     */
    Prompt updatePrompt(Prompt prompt);

    /**
     * 查询.
     * 
     * @param prompt
     *            包含条件
     * @param page
     *            起始页
     * @param pagesize
     *            页大小
     * @return 查询结果
     */
    List<Prompt> selectPrompts(Prompt prompt, int page, int pagesize);

    /**
     * 批量增删.
     * 
     * @param request
     *            请求上下文
     * @param prompts
     *            数据
     * @return 新的数据
     */
    List<Prompt> batchUpdate(IRequest request, @StdWho List<Prompt> prompts);

    /**
     * 批量删除.
     * 
     * @param prompts
     *            待删数据
     */
    void batchDelete(List<Prompt> prompts);

}

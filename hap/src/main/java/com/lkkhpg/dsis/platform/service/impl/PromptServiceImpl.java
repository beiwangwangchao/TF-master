/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.cache.CacheDelete;
import com.lkkhpg.dsis.platform.cache.CacheSet;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Prompt;
import com.lkkhpg.dsis.platform.mapper.system.PromptMapper;
import com.lkkhpg.dsis.platform.service.IPromptService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
@Transactional
public class PromptServiceImpl implements IPromptService {

    @Autowired
    private PromptMapper promptMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Prompt> selectPrompts(Prompt prompt, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return promptMapper.select(prompt);
    }

    @Override
    public List<Prompt> batchUpdate(IRequest request, List<Prompt> prompts) {
        for (Prompt p : prompts) {
            if (p.getPromptId() == null) {
                self().createPrompt(p);
            } else {
                self().updatePrompt(p);
                // updatePrompt(p);
            }
        }
        return prompts;
    }

    @Override
    public void batchDelete(List<Prompt> prompts) {
        prompts.forEach(self()::deletePrompt);
    }

    @Override
    @CacheSet(cache = "prompt")
    public Prompt createPrompt(Prompt prompt) {
        promptMapper.insert(prompt);
        return prompt;
    }

    @Override
    @CacheDelete(cache = "prompt")
    public void deletePrompt(Prompt prompt) {
        promptMapper.deleteByPrimaryKey(prompt.getPromptId());
    }

    @Override
    @CacheSet(cache = "prompt")
    public Prompt updatePrompt(Prompt prompt) {
        promptMapper.updateByPrimaryKeySelective(prompt);
        return prompt;
    }
}

/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.MemDistributor;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * @author runbai.chen
 */
public interface IMemDistributorService extends ProxySelf<IMemDistributorService> {

    /**
     * 保存会员转经销商记录.
     * @param request
     * @param mmDistributor
     * @return 记录
     */
    MemDistributor saveMemDistributor(IRequest request, @StdWho MemDistributor mmDistributor);

    /**
     * 获取会员.
     * @param mmDistributor
     * @return 记录列表
     */
    List<MemDistributor> getMemDistributor(MemDistributor mmDistributor);
}

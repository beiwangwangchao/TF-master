/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.mapper;


import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.IsgDappAuthCode;

/**
 *
 * @author shengyang.zhou@hand-china.com
 */
public interface IsgDappAuthCodeMapper {
    int deleteByPrimaryKey(String appCode);

    int insert(IsgDappAuthCode record);

    IsgDappAuthCode selectByPrimaryKey(String appCode);

    int updateByPrimaryKey(IsgDappAuthCode record);

    List<IsgDappAuthCode> selectList(IsgDappAuthCode code);
}
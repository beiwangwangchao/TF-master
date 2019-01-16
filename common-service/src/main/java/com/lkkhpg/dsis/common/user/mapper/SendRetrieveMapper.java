/*
 *
 */
package com.lkkhpg.dsis.common.user.mapper;

import com.lkkhpg.dsis.common.user.dto.SendRetrieve;

/**
 * 发送次数限制接口.
 * @author Zhao
 *
 */
public interface SendRetrieveMapper {
    int insert(SendRetrieve record);

    int query(SendRetrieve sendRetrieve);
}
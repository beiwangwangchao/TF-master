/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.service;

import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackBody;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackResponse;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IDAppCallbackService {
    /**
     *
     * @param body
     *            message body
     * @return notify result
     */
    DAppCallbackResponse callback(DAppCallbackBody body);
}

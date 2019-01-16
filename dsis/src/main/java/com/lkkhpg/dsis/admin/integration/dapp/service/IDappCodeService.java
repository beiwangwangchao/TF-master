/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.IsgDappAuthCode;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IDappCodeService extends ProxySelf<IDappCodeService> {

    /**
     * select list.
     * 
     * @param code
     *            example
     * @param pageNum
     *            page num
     * @param pageSize
     *            page size
     * @return select result
     */
    List<IsgDappAuthCode> selectCodes(IsgDappAuthCode code, int pageNum, int pageSize);

    /**
     * create new code.
     * 
     * @param code
     *            code to create.
     * @return the code created
     */
    IsgDappAuthCode insertCode(IsgDappAuthCode code);

    /**
     * select code by id.
     * 
     * @param code
     *            code with id.
     * @return code match the id
     */
    IsgDappAuthCode select(IsgDappAuthCode code);

    /**
     * delete code by id.
     * 
     * @param code
     *            code with id
     */
    void deleteCode(IsgDappAuthCode code);

    /**
     * update code by id.
     * 
     * @param code
     *            code with id.
     * @return code after update
     */
    IsgDappAuthCode updateCode(IsgDappAuthCode code);

    /**
     * batch operation.
     * 
     * @param codes
     *            list of codes
     * @return the list after operation
     */
    List<IsgDappAuthCode> batchUpdate(List<IsgDappAuthCode> codes);

}

/*
 *
 */
package com.lkkhpg.dsis.common.service;

import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员账务事务处理接口.
 * 
 * @author houmin
 *
 */
public interface IMemberBalanceTrxService extends ProxySelf<IMemberBalanceTrxService> {

    /**
     * 会员帐户余额变更事务处理.
     * 
     * @param request
     *            统一上下文
     * @param memAccountingTrx
     *            会员资产交易信息
     * @throws CommMemberException
     *             账务事务处理异常
     */
    void processAccountingTrx(IRequest request, MemAccountingTrx memAccountingTrx) throws CommMemberException;

    /**
     * 账务类型不是PV时的事务处理.
     * 
     * @param request
     *            统一上下文
     * @param memAccountingTrx
     *            会员账务交易信息
     * @throws CommMemberException
     *             账务类型不是PV时的事务处理异常
     */
    void processTransferTrxBalance(IRequest request, MemAccountingTrx memAccountingTrx) throws CommMemberException;

    /**
     * 账务类型是PV时的事务处理.
     * 
     * @param request
     *            统一上下文
     * @param memAccountingTrx
     *            会员账务交易信息
     * @throws CommMemberException
     *             账务类型是PV时的事务处理异常
     */
    void processTransferTrxPV(IRequest request, MemAccountingTrx memAccountingTrx) throws CommMemberException;

    /**
     * 账务事务处理属性校验.
     * 
     * @param request
     *            统一上下文
     * @param memAccountingTrx
     *            会员账务事务处理DTO
     * @throws CommMemberException
     *             会员统一异常
     */
    void valiAccountingTrx(IRequest request, MemAccountingTrx memAccountingTrx) throws CommMemberException;

}

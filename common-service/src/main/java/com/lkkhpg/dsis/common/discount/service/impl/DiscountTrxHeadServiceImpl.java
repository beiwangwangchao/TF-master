package com.lkkhpg.dsis.common.discount.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.discount.constract.DiscountConstract;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxHeadDto;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxLineDto;
import com.lkkhpg.dsis.common.discount.exception.DiscountException;
import com.lkkhpg.dsis.common.discount.mapper.DiscountTrxHeadMapper;
import com.lkkhpg.dsis.common.discount.mapper.DiscountTrxLineMapper;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxHeadService;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Transactional
@Service
public class DiscountTrxHeadServiceImpl implements IDiscountTrxHeadService {


    private Logger logger = LoggerFactory.getLogger(DiscountTrxHeadServiceImpl.class);

    @Autowired
    private DiscountTrxHeadMapper discountTrxHeadMapper;

    @Autowired
    private DiscountTrxLineMapper discountTrxLineMapper;

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public long insert(IRequest request, DiscountTrxHeadDto discountTrxHeadDto) {
        return discountTrxHeadMapper.insert(discountTrxHeadDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DiscountTrxHeadDto> createUPTrx(IRequest request, DiscountTrxHeadDto discountTrxHeadDto) throws
            DiscountException {
        discountTrxHeadDto.setCreatedBy(request.getAttribute("createdBy"));
        discountTrxHeadDto.setCreationDate(request.getAttribute("creationDate"));
        discountTrxHeadDto.setLastUpdatedBy(request.getAttribute("lastUpdatedBy"));
        discountTrxHeadDto.setLastUpdateDate(request.getAttribute("lastUpdateDate"));
        DiscountTrxHeadDto discountTemp;

        System.out.println("++++++++++++++++++++++++++++=");
        System.out.println(discountTrxHeadDto.getLastUpdatedBy());
        System.out.println(discountTrxHeadDto.getCreatedBy());


        discountTemp = discountTrxHeadMapper.selectByPrimaryKey(discountTrxHeadDto.getDiscountTrxHeadId());

        if (discountTemp != null && DiscountConstract.TRX_ADJ_STATUS_COMP.equals(discountTrxHeadDto.getStatus())) {
            throw new DiscountException(DiscountException.MSG_ERROR_TRX_ALREADY_FINISHED, null);
        }

        Date trxDate = discountTrxHeadDto.getProcessDate();
        if (trxDate.getTime() > new Date().getTime()) {
            if (logger.isDebugEnabled()) {
                logger.debug("TrxDate is bigger than SystemDate: {}", new Object[]{trxDate});
            }
            throw new DiscountException(DiscountException.MSG_ERROR_NOT_ALLOWED_TRX_DATE, null);
        }

        if (discountTrxHeadDto.getDiscountTrxHeadId() != null) {
            //todo
//            discountTrxHeadMapper.updateByPrimaryKey(discountTrxHeadDto);

            this.updateTrx(request, discountTrxHeadDto);
        } else {
            DocSequence docSequence;
            String orgId = 0 + discountTrxHeadDto.getSalesOrgId().toString();
            String trxNumberStr = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());

            //警告：该用户已存在折扣
            Iterator<DiscountTrxLineDto> discountTrxLineDtos = discountTrxHeadDto.getDiscountTrxLineDto().iterator();
            DiscountTrxLineDto discountTrxDetails = null;
            Member member = new Member();
            while (discountTrxLineDtos.hasNext()) {
                discountTrxDetails = discountTrxLineDtos.next();

                member.setMemberId(discountTrxDetails.getMemberId());
                member.setDiscountAmt(discountTrxDetails.getDiscountAdjustAmt());
                Long nullDiscountAmtFlag = memberMapper.queryIfDiscountAmtIsNull(member);
                if ("NEW".equals(discountTrxHeadDto.getStatus())) {
                    if ("DCIN".equals(discountTrxHeadDto.getAdjustType())) {
                        if (nullDiscountAmtFlag > 0) {
                        } else {
                            throw new DiscountException(DiscountException.MSG_WARNING_DISCOUNT_AMT_ALREADY_HAVE, null);
                        }

                    }
                }
            }

                if (DiscountConstract.TRX_TYPE_DCIN.equals(discountTrxHeadDto.getAdjustType())) {
                docSequence = new DocSequence(DiscountConstract.TRX_TYPE_DCIN, orgId, date,
                        null, null, null);

                trxNumberStr = docSequenceService.getSequence(request, docSequence, date,
                        DiscountConstract.STOCKIO_SEQ_LENGTH, DiscountConstract.STOCKIO_START_NUMBER);

                trxNumberStr = DiscountConstract.TRX_TYPE_DCIN + trxNumberStr;

            } else if (DiscountConstract.TRX_TYPE_ADIN.equals(discountTrxHeadDto.getAdjustType())) {
                docSequence = new DocSequence(DiscountConstract.TRX_TYPE_ADIN, orgId, date,
                        null, null, null);

                trxNumberStr = docSequenceService.getSequence(request, docSequence, date,
                        DiscountConstract.STOCKIO_SEQ_LENGTH, DiscountConstract.STOCKIO_START_NUMBER);

                trxNumberStr = DiscountConstract.TRX_TYPE_ADIN + trxNumberStr;

            } else if (DiscountConstract.TRX_TYPE_ADDE.equals(discountTrxHeadDto.getAdjustType())) {
                docSequence = new DocSequence(DiscountConstract.TRX_TYPE_ADDE, orgId, date,
                        null, null, null);
                trxNumberStr = docSequenceService.getSequence(request, docSequence, date,
                        DiscountConstract.STOCKIO_SEQ_LENGTH, DiscountConstract.STOCKIO_START_NUMBER);

                trxNumberStr = DiscountConstract.TRX_TYPE_ADDE + trxNumberStr;
                    //订单消耗  update up 15750 at 2018/04/19
            } else if (DiscountConstract.TRX_TYPE_USED.equals(discountTrxHeadDto.getAdjustType())) {

                docSequence = new DocSequence(DiscountConstract.TRX_TYPE_USED, orgId, date,
                        null, null, null);

                trxNumberStr = docSequenceService.getSequence(request, docSequence, date,
                        DiscountConstract.STOCKIO_SEQ_LENGTH, DiscountConstract.STOCKIO_START_NUMBER);

                trxNumberStr = DiscountConstract.TRX_TYPE_USED + trxNumberStr;

            }//留个自动事务
             else if (DiscountConstract.TRX_ADJ_REASON_INCREASE.equals(discountTrxHeadDto.getAdjustReason())) {

                docSequence = new DocSequence(DiscountConstract.TRX_ADJ_REASON_INCREASE, orgId, date,
                        null, null, null);

                trxNumberStr = docSequenceService.getSequence(request, docSequence, date,
                        DiscountConstract.STOCKIO_SEQ_LENGTH, DiscountConstract.STOCKIO_START_NUMBER);

                trxNumberStr = DiscountConstract.TRX_TYPE_ADIN + trxNumberStr;
            }

            discountTrxHeadDto.setDiscountTrxNum(trxNumberStr);
            Long trxId = discountTrxHeadMapper.getDiscountTrxHeadId();
            discountTrxHeadDto.setDiscountTrxHeadId(trxId);


            discountTrxHeadMapper.insert(discountTrxHeadDto);

            if (discountTrxHeadDto.getDiscountTrxLineDto() != null) {
                discountTrxHeadDto = processTrxDetails(request, discountTrxHeadDto);
            }
        }


        List<DiscountTrxHeadDto> discountTrxHeadDtos = new ArrayList<DiscountTrxHeadDto>();
        discountTrxHeadDtos.add(0, discountTrxHeadDto);
        return discountTrxHeadDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public DiscountTrxHeadDto updateTrx(IRequest request, DiscountTrxHeadDto discountTrxHeadDto) throws DiscountException {
        List<DiscountTrxLineDto> lists = discountTrxHeadDto.getDiscountTrxLineDto();

        DiscountTrxLineDto discountTrxDetails = null;
        Iterator<DiscountTrxLineDto> discountTrxLineDtoIterator = discountTrxHeadDto.getDiscountTrxLineDto().iterator();
        while (discountTrxLineDtoIterator.hasNext()) {
            discountTrxDetails = discountTrxLineDtoIterator.next();
            if (discountTrxDetails.getDiscountTrxLineId() != null) {
                discountTrxLineMapper.updateSelectiveByPrimarkey(discountTrxDetails);
            } else {
                discountTrxDetails.setDiscountTrxHeadId(discountTrxHeadDto.getDiscountTrxHeadId());

                discountTrxLineMapper.baseInsert(discountTrxDetails);
            }
        }

        return discountTrxHeadDto;
    }

    /**
     * 取消信用额度/折扣
     * @param orderNumber
     * @return
     * @throws DiscountException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void canclTrx(String orderNumber){

        if(orderNumber != null ){
            discountTrxHeadMapper.canclDiscountTrxStatus(orderNumber);
        }
    }
    /**
     * save discount transaction line data
     *
     * @param request
     * @param discountTrxHeadDto
     * @return
     * @throws DiscountException
     */
    @Transactional(rollbackFor = Exception.class)
    public DiscountTrxHeadDto processTrxDetails(IRequest request, DiscountTrxHeadDto discountTrxHeadDto) throws DiscountException {
        int index = 0;
        Long discountTrxHeadId = discountTrxHeadDto.getDiscountTrxHeadId();
        String adjustType = discountTrxHeadDto.getAdjustType();

        List<DiscountTrxLineDto> details = discountTrxHeadDto.getDiscountTrxLineDto();
        Iterator<DiscountTrxLineDto> discountTrxLineDtos = discountTrxHeadDto.getDiscountTrxLineDto().iterator();
        DiscountTrxLineDto discountTrxDetails = null;

        //flag use for
        int countMember = 0;

        while (discountTrxLineDtos.hasNext()) {
            discountTrxDetails = discountTrxLineDtos.next();
            discountTrxDetails.setDiscountTrxHeadId(discountTrxHeadId);

            if (DiscountConstract.TRX_TYPE_ADDE.equals(discountTrxHeadDto.getAdjustType())) {
                if (discountTrxDetails.getDiscountAmt().compareTo(discountTrxDetails.getDiscountAdjustAmt()) < 0) {
                    throw new DiscountException(DiscountException.MSG_ERROR_ADJAMT_IS_BIGGER_THAN_DISCOUNT, null);
                }
            }

            discountTrxDetails.setLastUpdateDate(discountTrxHeadDto.getLastUpdateDate());
            discountTrxDetails.setLastUpdatedBy(discountTrxHeadDto.getLastUpdatedBy());
            discountTrxDetails.setCreatedBy(discountTrxHeadDto.getCreatedBy());
            discountTrxDetails.setCreationDate(discountTrxHeadDto.getCreationDate());

            if (discountTrxDetails.getDiscountAdjustAmt().compareTo(BigDecimal.ZERO) <= 0 || null == discountTrxDetails.getDiscountAdjustAmt()) {
                throw new DiscountException(DiscountException.MSG_ERROR_EMPTY_ADJUST_AMT, null);
            } else if ("".equals(discountTrxDetails.getMemberId())) {
                throw new DiscountException(DiscountException.MSG_ERROR_MEMBER_CAN_NOT_BE_NULL, null);
            } else if ("".equals(discountTrxDetails.getDiscountTrxHeadId())) {
                throw new DiscountException(DiscountException.MSG_ERROR_MISSING_TRX_HEAD_ID, null);
            } else {

                countMember = memberMapper.calculateMemberCount(discountTrxDetails.getMemberId());

                if (countMember > 0) {

                    if (discountTrxDetails.getDiscountTrxLineId() == null) {
                        if (discountTrxLineMapper.baseInsert(discountTrxDetails) > 0) {

                            details.set(index, discountTrxDetails);

                        } else {

                            throw new DiscountException(DiscountException.MSG_ERROR_TRX_PROCESS_ERROR, null);

                        }
                    } else {
                        discountTrxLineMapper.updateSelectiveByPrimarkey(discountTrxDetails);
                    }
                } else {
                    throw new DiscountException(DiscountException.MSG_ERROR_MEMBER_NOT_EXISTS, null);
                }
            }
        }

        discountTrxHeadDto.setDiscountTrxLineDto(details);
        return discountTrxHeadDto;
    }


    @Override
    public List<DiscountTrxHeadDto> queryDiscountTrxHead(IRequest request, DiscountTrxHeadDto discountTrxHeadDto, int page, int pagesize) {

        PageHelper.startPage(page, pagesize);
        return discountTrxHeadMapper.queryDiscountTrxHead(discountTrxHeadDto);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public DiscountTrxHeadDto getDiscountTrx(IRequest request, Long discountTrxHeadId) throws DiscountException {

        DiscountTrxHeadDto discountTrxHeadDto = discountTrxHeadMapper.selectByPrimaryKey(discountTrxHeadId);

        List<DiscountTrxLineDto> discountTrxDetail = discountTrxLineMapper.queryDiscountDetail(discountTrxHeadId);
        if (!discountTrxDetail.isEmpty()) {
            discountTrxHeadDto.setDiscountTrxLineDto(discountTrxDetail);
        }

        return discountTrxHeadDto;

    }


    /**
     * 事务提交
     *
     * @param request
     * @param discountTrxHeadDto
     * @return
     * @throws DiscountException
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<DiscountTrxHeadDto> submitDiscountTrx(IRequest request, DiscountTrxHeadDto discountTrxHeadDto) throws DiscountException {

        DiscountTrxHeadDto discountTrxHeadDtoFlag = new DiscountTrxHeadDto();
        List<DiscountTrxLineDto> details = discountTrxHeadDto.getDiscountTrxLineDto();
        Iterator<DiscountTrxLineDto> discountTrxLineDtos = discountTrxHeadDto.getDiscountTrxLineDto().iterator();
        DiscountTrxLineDto discountTrxDetails = null;
        Long discountTrxHeadId = discountTrxHeadDto.getDiscountTrxHeadId();

        Member member = new Member();

        long executeFlag = 0;
        long nullDiscountAmtFlag = 0;

        while (discountTrxLineDtos.hasNext()) {
            discountTrxDetails = discountTrxLineDtos.next();
            discountTrxDetails.setDiscountTrxHeadId(discountTrxHeadId);

            member.setMemberId(discountTrxDetails.getMemberId());
            member.setDiscountAmt(discountTrxDetails.getDiscountAdjustAmt());

            nullDiscountAmtFlag = memberMapper.queryIfDiscountAmtIsNull(member);

            if ("NEW".equals(discountTrxHeadDto.getStatus())) {
                if ("DCIN".equals(discountTrxHeadDto.getAdjustType())) {
                    if (nullDiscountAmtFlag > 0) {
                        executeFlag = memberMapper.addNewDiscountAmt(member);
                    } else {
                        throw new DiscountException(DiscountException.MSG_WARNING_DISCOUNT_AMT_ALREADY_HAVE, null);
                    }

                } else if ("ADIN".equals(discountTrxHeadDto.getAdjustType())) {
                    executeFlag = memberMapper.adjustDiscountAmtIn(member);
                } else if ("ADDE".equals(discountTrxHeadDto.getAdjustType())) {
                    executeFlag = memberMapper.adjustDiscountAmtOut(member);
                    //update up 15750 at 2018/04/19
                } else if ("USED".equals(discountTrxHeadDto.getAdjustType())) {
                    executeFlag = memberMapper.adjustDiscountAmtOut(member);
                } else {
                    throw new DiscountException(DiscountException.MSG_ERROR_ADJ_TYPE_NOT_RIGHT, null);
                }
            } else {
                throw new DiscountException(DiscountException.MSG_ERROR_STATUS_NOT_RIGHT, null);
            }
        }
        discountTrxHeadMapper.updateDiscountTrxStatus(discountTrxHeadId);
        List<DiscountTrxHeadDto> discountTrxHeadDtos = new ArrayList<DiscountTrxHeadDto>();
        discountTrxHeadDtos.add(0, discountTrxHeadDto);
        return discountTrxHeadDtos;
    }


    /**
     * 保存并提交事务
     *
     * @param request
     * @param discountTrxHeadDto
     * @return
     * @throws DiscountException
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<DiscountTrxHeadDto> submitDiscountTransaction(IRequest request, DiscountTrxHeadDto discountTrxHeadDto) throws DiscountException {

        List<DiscountTrxHeadDto> discountTrxHeadDtoList = this.createUPTrx(request, discountTrxHeadDto);

        this.submitDiscountTrx(request, discountTrxHeadDto);

        List<DiscountTrxHeadDto> discountTrxHeadDtos = new ArrayList<DiscountTrxHeadDto>();
        discountTrxHeadDtos.add(0, discountTrxHeadDto);
        return discountTrxHeadDtos;
    }

    //根据会员id和订单号查询对应订的折扣/优惠   update up 15750 at 2018/04/19
    public DiscountTrxHeadDto queryByMemOrd(Long memberId,String orderNumbe){
        DiscountTrxHeadDto discountTrxHeadDtoList = discountTrxHeadMapper.queryByMemOrd(memberId, orderNumbe);
        return discountTrxHeadDtoList;
    }
}

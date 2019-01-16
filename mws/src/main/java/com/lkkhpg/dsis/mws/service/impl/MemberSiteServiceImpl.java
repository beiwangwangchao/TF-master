/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.mapper.MemAccountMapper;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.mws.dto.MemberInfo;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.mapper.MemberSiteMapper;
import com.lkkhpg.dsis.mws.service.IMemberInfoService;
import com.lkkhpg.dsis.mws.service.IMemberSiteService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员地址service实现.
 * 
 * @author guanghui.liu
 */
@Service
@Transactional
public class MemberSiteServiceImpl implements IMemberSiteService {

    private final Logger logger = LoggerFactory.getLogger(MemberSiteServiceImpl.class);

    @Autowired
    private MemberSiteMapper memberSiteMapper;

    @Autowired
    private SpmLocationMapper spmLocationMapper;
    
    @Autowired
    private IMemberInfoService memberInfoService;
    
    @Autowired
    private MemAccountMapper memAccountMapper;
    
    @Autowired
    private MemSiteMapper memSiteMapper;

    @Override
    public int updateDefaultFlag(IRequest request, MemSite memSite) {
    	//触发审计流程
    	MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMemberId(memSite.getMemberId());
		memberInfoService.updateMemberLastUpdateDate(memberInfo);
		//account
		memberInfoService.updateMemberAccountArchive(memSite.getMemberId());
		//home
		memberInfoService.updateMemberHomeAddressArchive(memSite.getMemberId());
		//ctact
		memberInfoService.updateMemberCtactAddressArchive(memSite.getMemberId());
		
        // 直接更新标记-因为前台只提供更新为Y
        int count = memberSiteMapper.updateByPrimaryKeySelective(memSite);
        // 如果标记为Y,将其他同类型同member的地址设置为N
        if (BaseConstants.YES.equals(memSite.getDefaultFlag())) {
        	/*memberSiteMapper.updateDefaultFlag2(memSite);*/
            memberSiteMapper.updateDefaultFlag(memSite.getRequestId(), memSite.getProgramId(), memSite.getLastUpdatedBy(),
            		memSite.getMemberId(), memSite.getSiteUseCode(), memSite.getSiteId());
        }
        return count;
    }

    @Override
    public List<MemSite> queryMemSites(IRequest request, MemSite memSite) {
        return memberSiteMapper.selectMemSites(memSite);
    }

    @Override
    public MemSite saveMemSite(IRequest request, MemSite memSite) {
    	
    	//mws审计update会员头表最后修改时间。触发审计流程。 联系地址会自动触发-忽略
    	if (!"CTACT".equals(memSite.getSiteUseCode())) {
    		MemberInfo memberInfo = new MemberInfo();
    		memberInfo.setMemberId(memSite.getMemberId());
    		memberInfoService.updateMemberLastUpdateDate(memberInfo);
    		//account
    		memberInfoService.updateMemberAccountArchive(memSite.getMemberId());
    		//home
    		memberInfoService.updateMemberHomeAddressArchive(memSite.getMemberId());
    		//ctact
    		memberInfoService.updateMemberCtactAddressArchive(memSite.getMemberId());
    	}
    	
        if (memSite.getSiteId() == null) {
            // 如果locationId为空则先创建SpmLocation
            if (memSite.getLocationId() == null) {
                spmLocationMapper.insert(memSite.getSpmLocation());
                if (logger.isDebugEnabled()) {
                    logger.debug("Create SpmLocation with locationId: {}, countryCode: {}, stateCode: {}, cityCode: {}",
                            memSite.getSpmLocation().getLocationId(), memSite.getSpmLocation().getCountryCode(),
                            memSite.getSpmLocation().getStateCode(), memSite.getSpmLocation().getCityCode());
                }
            }
            memSite.setLocationId(memSite.getSpmLocation().getLocationId());
            // 创建会员地点
            memberSiteMapper.insert(memSite);
            if (logger.isDebugEnabled()) {
                logger.debug("Create MemSite with siteId: {}, memberId: {}, siteUseCode: {}", memSite.getSiteId(),
                        memSite.getMemberId(), memSite.getSiteUseCode());
            }
            if (BaseConstants.YES.equals(memSite.getDefaultFlag())) { // 如果设为默认地址,则更新其他地址为非默认
                memberSiteMapper.updateDefaultFlag(memSite.getRequestId(), memSite.getProgramId(), memSite.getLastUpdatedBy(),
                		memSite.getMemberId(), memSite.getSiteUseCode(), memSite.getSiteId());
            }
        } else {
            // 更新会员地点
            memberSiteMapper.updateByPrimaryKeySelective(memSite);
            if (BaseConstants.YES.equals(memSite.getDefaultFlag())) { // 如果设为默认地址,则更新其他地址为非默认
                memberSiteMapper.updateDefaultFlag(memSite.getRequestId(), memSite.getProgramId(), memSite.getLastUpdatedBy(),
                		memSite.getMemberId(), memSite.getSiteUseCode(), memSite.getSiteId());
            }
            // 更新地址
            if (memSite.getSpmLocation() != null) {
                spmLocationMapper.updateByPrimaryKeySelective(memSite.getSpmLocation());
            }
        }
        // 地址保存成功后,为了取出最新的多语言的country,state,city的name,重新查询一次
        MemSite temp = new MemSite();
        temp.setSiteId(memSite.getSiteId());
        memSite = memberSiteMapper.selectMemSites(temp).iterator().next();
        return memSite;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long deleteMemSite(IRequest request, Long memSiteId) throws MemberException {
        MemSite memSite = memberSiteMapper.selectByPrimaryKey(memSiteId);
        
      //触发审计流程
    	MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMemberId(memSite.getMemberId());
		memberInfoService.updateMemberLastUpdateDate(memberInfo);
		//account
		memberInfoService.updateMemberAccountArchive(memSite.getMemberId());
		//home
		memberInfoService.updateMemberHomeAddressArchive(memSite.getMemberId());
		//ctact
		memberInfoService.updateMemberCtactAddressArchive(memSite.getMemberId());
        
        
        // 如果删除的是默认地址,则设置该会员的第一条同类型的数据为默认地址
        if (BaseConstants.YES.equals(memSite.getDefaultFlag())) {
            // 先设置其他地址为默认,如果update成功
            if (memberSiteMapper.updateOtherSiteDefault(memSite) > 0) {
                // 删除地址 - 1.删除spmLocation
            	SpmLocation spmLocation = new SpmLocation();
            	spmLocation.setLocationId(memSite.getLocationId());
                spmLocationMapper.deleteByPrimaryKey(spmLocation);
                // 删除地址 - 2.删除memSite
                memberSiteMapper.deleteByPrimaryKey(memSite);
            } else {// 如果update失败,说明该会员该类型只有这一条地址,不允许删除
                throw new MemberException(MemberException.ONLY_ONE_SITE_NOT_DELETE, null);
            }
        } else { // 如果删除的不是默认地址,检查是否有其他地址,如果没有,则不允许删除
            if (memberSiteMapper.selectCountByMemberAndType(memSite) > 0) {
                // 删除地址 - 1.删除spmLocation
            	SpmLocation spmLocation = new SpmLocation();
            	spmLocation.setLocationId(memSite.getLocationId());
                spmLocationMapper.deleteByPrimaryKey(spmLocation);
                // 删除地址 - 2.删除memSite
                memberSiteMapper.deleteByPrimaryKey(memSite);
            } else {
                throw new MemberException(MemberException.ONLY_ONE_SITE_NOT_DELETE, null);
            }
        }
        MemSite ms = new MemSite();
        ms.setMemberId(memSite.getMemberId());
        ms.setSiteUseCode(memSite.getSiteUseCode());
        return memberSiteMapper.selectDefaultSiteByMemberAndType(ms);
    }

	@Override
	public MemSite saveCtactMemSite(IRequest request, MemSite memSite) {		
		return this.saveMemSite(request, memSite);
	}

}
/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgSaleBranchList;

/**
 * 销售分公司列表mappper.
 * 
 * @author chuangsheng.zhang.
 */
public interface IsgSaleBranchListMapper {
    int deleteByPrimaryKey(Long interfaceId);

    int insert(IsgSaleBranchList record);

    int insertSelective(IsgSaleBranchList record);

    IsgSaleBranchList selectByPrimaryKey(Long interfaceId);

    int updateByPrimaryKeySelective(IsgSaleBranchList record);

    int updateByPrimaryKey(IsgSaleBranchList record);

    /**
     * 查询销售分公司列表.
     * 
     * @param isgSaleBranchList 销售分公司dto
     * @return 销售分公司列表
     */
    List<IsgSaleBranchList> queryIsgSaleBranchLists(IsgSaleBranchList isgSaleBranchList);

    List<IsgSaleBranchList> selectByParams(Map<String, Object> params);
}
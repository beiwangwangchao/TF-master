/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.MWSImages;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;

public interface MWSImagesMapper {
    
    int deleteByPrimaryKey(Long imageId);

    int insert(MWSImages record);

    MWSImages selectByPrimaryKey(Short imageId);

    int updateByPrimaryKey(MWSImages record);
    
    List<MWSImages> selectByMarketIdAndType(MWSImages record);
    
    List<SysFile> selectFileByImageId(List<Long> list);
}
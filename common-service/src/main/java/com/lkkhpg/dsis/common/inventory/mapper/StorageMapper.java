package com.lkkhpg.dsis.common.inventory.mapper;

import com.lkkhpg.dsis.common.inventory.dto.Storage;

import java.util.List;

/**
 * Created by hand on 2018/6/4.
 */
public interface  StorageMapper {
   List<Storage> queryStorage(Storage storage);
}

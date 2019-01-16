/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.mapper.menu;

import java.util.List;
import java.util.Map;

/**
 * @author chenjingxiong on 16/1/7.
 */
public interface MenuMapper {

    List<Map<String, Object>> getUserRole(Long usrId, String language);

    List<Map<String, Object>> getRoles();

    List<Map<String, Object>> getRoleFunction(Long roleId, String language);

    List<Map<String, Object>> getSubFunctions(Long functionId, String language);

}

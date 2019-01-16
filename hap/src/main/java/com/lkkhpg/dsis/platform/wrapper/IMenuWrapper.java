/*
 *
 */

package com.lkkhpg.dsis.platform.wrapper;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.menu.MenuItem;

/**
 * 菜单转换接口.
 * @author chenjingxiong.
 */
public interface IMenuWrapper {

    Object wrapMenuList(List<MenuItem> menuItemList);
}

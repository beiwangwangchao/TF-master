/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.util.List;

/**
 * 获取业务员列表响应DTO.
 * 
 * @author linyuheng
 */
public class GetDistributorsResponse {

    private List<DAppMember> members;

    public List<DAppMember> getMembers() {
        return members;
    }

    public void setMembers(List<DAppMember> members) {
        this.members = members;
    }

}

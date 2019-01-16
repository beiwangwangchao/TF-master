/*
 *
 */

package com.lkkhpg.dsis.integration.gds.service.impl;

import java.util.Arrays;
import java.util.List;

import com.lkkhpg.dsis.platform.message.profile.GlobalProfileListener;

/**
 * gds 主动接口开关.
 * @author shenqb
 *
 */

public class GdsSwitch implements GlobalProfileListener {

    private boolean switchFlag = true;

    public boolean isSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(boolean switchFlag) {
        this.switchFlag = switchFlag;
    }


    @Override
    public List<String> getAcceptedProfiles() {
        return Arrays.asList("ISG.GDS_SWITCH");
    }

    @Override
    public void updateProfile(String profileName, String profileValue) {
        setSwitchFlag(Boolean.valueOf(profileValue));
    }
}

/*
 *
 */

package com.lkkhpg.dsis.integration.gds.controllers;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Controller
@RequestMapping("/restful/gds/test")
public class FakeTestController {

    private static final Object SINGLE = new HashMap<>();

    private static final Object ARRAY = Arrays.asList();

    @RequestMapping(value = "/checkResults", method = RequestMethod.GET)
    @ResponseBody
    public Object checkResults() {
        return ARRAY;
    }

    @RequestMapping(value = "/periods/close", method = RequestMethod.POST)
    @ResponseBody
    public Object periodsClose() {
        return SINGLE;
    }

    @RequestMapping(value = "/dealers", method = RequestMethod.POST)
    @ResponseBody
    public Object dealers() {
        return SINGLE;
    }

    @RequestMapping(value = "/dealers/batchSave", method = RequestMethod.POST)
    @ResponseBody
    public Object dealersBatchSave() {
        return ARRAY;
    }

    @RequestMapping(value = "/dealers/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public Object dealersBatchDelete() {
        return ARRAY;
    }

    @RequestMapping(value = "/dealers/foreignSponsors", method = RequestMethod.GET)
    @ResponseBody
    public Object dealersForeignSponsors() {
        return ARRAY;
    }

    @RequestMapping(value = "/dealers/{dealerNo}", method = RequestMethod.PUT)
    @ResponseBody
    public Object dealersDealerNo() {
        return SINGLE;
    }

    @RequestMapping(value = "/dealers/{dealerNo}/sponsorVerify", method = RequestMethod.POST)
    @ResponseBody
    public Object dealersDealerNoSponsorVerify() {
        return SINGLE;
    }

    @RequestMapping(value = "/gtreeAlters/moveLine/results", method = RequestMethod.GET)
    @ResponseBody
    public Object gtreeAlters111() {
        return ARRAY;
    }

    @RequestMapping(value = "/gtreeAlters/moveLine/notify", method = RequestMethod.POST)
    @ResponseBody
    public Object gtreeAlters112() {
        return SINGLE;
    }

    @RequestMapping(value = "/gtreeAlters/moveLine/applications/{appNo}", method = RequestMethod.PUT)
    @ResponseBody
    public Object gtreeAlters113() {
        return SINGLE;
    }

    @RequestMapping(value = "/gtreeAlters/terminate/results", method = RequestMethod.GET)
    @ResponseBody
    public Object gtreeAlters121() {
        return ARRAY;
    }

    @RequestMapping(value = "/gtreeAlters/terminate/notify", method = RequestMethod.POST)
    @ResponseBody
    public Object gtreeAlters122() {
        return SINGLE;
    }

    @RequestMapping(value = "/gtreeAlters/terminate/applications/{appNo}", method = RequestMethod.PUT)
    @ResponseBody
    public Object gtreeAlters123() {
        return SINGLE;
    }

    @RequestMapping(value = "/gtreeAlters/applications/{appNo}/status", method = RequestMethod.GET)
    @ResponseBody
    public Object gtreeAlters131() {
        return SINGLE;
    }

    @RequestMapping(value = "/orders/batchSave", method = RequestMethod.POST)
    @ResponseBody
    public Object orders111() {
        return ARRAY;
    }

    @RequestMapping(value = "/orgList", method = RequestMethod.GET)
    @ResponseBody
    public Object orgList() {
        return ARRAY;
    }

    @RequestMapping(value = "/marketChanges/transferOut/results", method = RequestMethod.GET)
    @ResponseBody
    public Object marketChanges111() {
        return ARRAY;
    }

    @RequestMapping(value = "/marketChanges/transferOut/notify", method = RequestMethod.POST)
    @ResponseBody
    public Object marketChanges112() {
        return ARRAY;
    }

    @RequestMapping(value = "/marketChanges/transferOut/applications", method = RequestMethod.GET)
    @ResponseBody
    public Object marketChanges113() {
        return ARRAY;
    }

    @RequestMapping(value = "/marketChanges/transferOut/applications", method = RequestMethod.POST)
    @ResponseBody
    public Object marketChanges114() {
        return SINGLE;
    }

    @RequestMapping(value = "/marketChanges/transferOut/applications/{appId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object marketChanges115() {
        return SINGLE;
    }

    @RequestMapping(value = "/marketChanges/transferIn/results", method = RequestMethod.GET)
    @ResponseBody
    public Object marketChanges121() {
        return ARRAY;
    }

    @RequestMapping(value = "/marketChanges/transferIn/notify", method = RequestMethod.POST)
    @ResponseBody
    public Object marketChanges122() {
        return ARRAY;
    }

    @RequestMapping(value = "/marketChanges/transferIn/applications", method = RequestMethod.GET)
    @ResponseBody
    public Object marketChanges123() {
        return ARRAY;
    }

    @RequestMapping(value = "/marketChanges/transferIn/applications/{appId}/audit", method = RequestMethod.POST)
    @ResponseBody
    public Object marketChanges124() {
        return SINGLE;
    }

}

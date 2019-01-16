/*
 *
 */

package com.lkkhpg.dsis.platform.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Profile;
import com.lkkhpg.dsis.platform.dto.system.ProfileValue;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * @author frank.li
 */
@Controller
public class ProfileController extends BaseController {


    @Autowired
    private IProfileService profileService;

    @RequestMapping(value = "/sys/profile/query")
    @ResponseBody
    public ResponseData getProfiles(Profile profile, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(profileService.selectProfiles(profile, page, pagesize));
    }

    @RequestMapping(value = "/sys/profilevalue/query")
    @ResponseBody
    public ResponseData getProfileValues(ProfileValue value) {
        return new ResponseData(profileService.selectProfileValues(value));
    }

    @RequestMapping(value = "/sys/profilevalue/querylevelvalues")
    @ResponseBody
    public ResponseData getLevelValues(Long levelId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(profileService.selectLevelValues(levelId, page, pagesize));
    }

    @RequestMapping(value = "/sys/profile/submit", method = RequestMethod.POST)
    public ResponseData submitProfiles(@RequestBody List<Profile> profiles, BindingResult result,
            HttpServletRequest request) {
        // validator.validate(profiles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(profileService.batchUpdate(requestContext, profiles));
    }

    @RequestMapping(value = "/sys/profile/remove", method = RequestMethod.POST)
    public ResponseData deleteProfile(@RequestBody List<Profile> profiles, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        profileService.batchDelete(requestContext, profiles);

        return new ResponseData();
    }

    @RequestMapping(value = "/sys/profilevalue/remove", method = RequestMethod.POST)
    public ResponseData removeProfileValues(@RequestBody List<ProfileValue> profileValues, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        profileService.batchDeleteValues(requestContext, profileValues);
//        System.out.println("removeProfileValues");
        return new ResponseData();
    }
}

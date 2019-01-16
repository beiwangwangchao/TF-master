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
import com.lkkhpg.dsis.platform.dto.system.Prompt;
import com.lkkhpg.dsis.platform.exception.TokenException;
import com.lkkhpg.dsis.platform.service.IPromptService;

/**
 * PromptController.
 * 
 * @author shengyang.zhou@hand-china.com
 */
@Controller
public class PromptController extends BaseController {

    @Autowired
    private IPromptService promptService;

    /**
     * 描述信息查询.
     * 
     * @param prompt
     *            Prompt
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/prompt/query")
    @ResponseBody
    public ResponseData getPrompts(Prompt prompt, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(promptService.selectPrompts(prompt, page, pagesize));
    }

    /**
     * 描述信息保存.
     * 
     * @param prompts
     *            prompts
     * @param result
     *            BindingResult
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     * @throws TokenException
     */
    @RequestMapping(value = "/sys/prompt/submit", method = RequestMethod.POST)
    public ResponseData submitPrompt(@RequestBody List<Prompt> prompts, BindingResult result,
            HttpServletRequest request) throws TokenException {
        checkToken(request, prompts);
        getValidator().validate(prompts, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(promptService.batchUpdate(requestCtx, prompts));
    }

    /**
     * 描述信息删除.
     * 
     * @param request
     * @param prompts
     * @return ResponseData
     * @throws TokenException
     */
    @RequestMapping(value = "/sys/prompt/remove", method = RequestMethod.POST)
    public ResponseData remove(HttpServletRequest request, @RequestBody List<Prompt> prompts) throws TokenException {
        checkToken(request, prompts);
        promptService.batchDelete(prompts);
        return new ResponseData();
    }
}

/*
 *
 */

package com.lkkhpg.dsis.intergration.controllers;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Controller
@RequestMapping(value = "/restful", method = RequestMethod.GET)
public class InterfaceSummaryController {

    private static final String DAPP = "/dapp";

    @Autowired
    private BeanFactory beanFactory;

    @RequestMapping(DAPP)
    public ModelAndView dapp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("interfaceName", "Distributor App");
        List<String> list = new ArrayList<>();
        modelAndView.addObject("list", list);
        try {
            Object obj = beanFactory.getBean("DAppController");
            if (obj != null) {
                RequestMapping rm0 = obj.getClass().getAnnotation(RequestMapping.class);
                String base = rm0.value()[0];
                base = StringUtils.substringAfter(base, DAPP);
                modelAndView.addObject("interfaceVersion", StringUtils.substringAfterLast(base, "/"));
                Method[] methods = obj.getClass().getMethods();
                Arrays.sort(methods, (a1, a2) -> a1.getName().compareTo(a2.getName()));
                for (Method m : methods) {
                    RequestMapping rm;
                    if (m.getModifiers() != Modifier.PUBLIC || (rm = m.getAnnotation(RequestMapping.class)) == null) {
                        continue;
                    }
                    String value = base + rm.value()[0];
                    RequestMethod[] requestMethod = rm.method();
                    if (requestMethod.length == 1) {
                        value = requestMethod[0].name() + " " + value;
                    }
                    list.add(value);
                }
            }
        } catch (Exception e) {
            // ignore
        }
        modelAndView.setViewName("restful/summary");
        return modelAndView;
    }

    @RequestMapping("/gds-listener")
    public ModelAndView gdsListener() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("interfaceName", "GDS Callback Listener");
        List<String> list = new ArrayList<>();
        modelAndView.addObject("list", list);

        modelAndView.setViewName("restful/summary");
        return modelAndView;
    }
}

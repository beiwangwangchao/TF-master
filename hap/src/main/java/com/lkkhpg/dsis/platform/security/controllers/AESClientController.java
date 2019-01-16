/*
 *
 */
package com.lkkhpg.dsis.platform.security.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.security.dto.Encrypt;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;

/**
 * @author shiliyan
 *
 */
@Controller
public class AESClientController {

    private Logger logger = LoggerFactory.getLogger(AESClientController.class);
    @Autowired
    private IAESClientService aesClientService;

    // @RequestMapping("/test/aes/encrypt")
    @ResponseBody
    public String encrypt(String text) throws IOException {
        String encrypt = aesClientService.encrypt(text);
        return encrypt;
    }

    @RequestMapping("/aes/encrypt")
    @ResponseBody
    public String encrypt(@RequestBody Encrypt enc) {
        //String text = enc.getText();
        //return aesClientService.encrypt(text);
        
        // 批量加密，以回车符分割
        String text = enc.getText();
        String[] texts = text.split("\n"); 
        
        String encrypt = "";
        for (String record : texts) {
            String result = aesClientService.encrypt(record);
            if ("".equals(encrypt)) {
                encrypt = result;
            } else {
                encrypt = encrypt + "\n" + result;
            }
        }
        return encrypt;
    }

    // @RequestMapping("/test/aes/decrypt")
    @ResponseBody
    public String decrypt(String text) throws IOException {
        String encrypt = aesClientService.decrypt(text);
        return encrypt;
    }

}

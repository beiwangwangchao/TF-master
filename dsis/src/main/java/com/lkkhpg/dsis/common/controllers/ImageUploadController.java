package com.lkkhpg.dsis.common.controllers;

import com.lkkhpg.dsis.admin.util.ImageUploadUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @name:
 * @author:liangxian.chen@hand-china.com
 * @date: Created in 09:17 2018/04/02
 * @Modified By:
 * @description：
 */
@Controller
public class ImageUploadController {

    /**
     * ckeditor图片上传
     *
     * @Title imageUpload
     * @param request
     * @param response
     */
    @RequestMapping(value = "/pm/imageUpload", method = RequestMethod.POST)
    public void imageUpload(HttpServletRequest request, HttpServletResponse response) {
        String DirectoryName = "resources/upload";

        // 判断提交的请求是否包含文件
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        try {
            ImageUploadUtil.ckeditor(request, response, DirectoryName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

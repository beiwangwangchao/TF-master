package com.lkkhpg.dsis.admin.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;



/**
 * @name:
 * @author:liangxian.chen@hand-china.com
 * @date: Created in 18:01 2018/03/30
 * @Modified By:
 * @description：图片上传工具类，包括ckeditor操作
 */
public class ImageUploadUtil {

    /*protected final Logger logger = LoggerFactory
            .getLogger(ImageUploadUtil.class);*/

    // 图片类型
    private static List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".bmp");
        fileTypes.add(".gif");
        fileTypes.add(".png");
    }

    /**
     * 图片上传
     *
     * @Title upload
     * @param request
     * @param DirectoryName
     *            文件上传目录：比如upload(无需带前面的/) upload/news ..
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String upload(HttpServletRequest request,HttpServletResponse response, String DirectoryName) throws IllegalStateException,
            IOException {


        response.setContentType("text/html;charset=UTF-8");
        String callback = request.getParameter("CKEditorFuncNum");
        PrintWriter out = response.getWriter();


        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        // 图片名称
        String fileName = null;
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                // int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        // 获得图片的原始名称
                        String originalFilename = file.getOriginalFilename();
                        // 获得图片后缀名称,如果后缀不为图片格式，则不上传
                        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
                        if (!fileTypes.contains(suffix)) {
                            out.println("<script type=\"text/javascript\">");
                            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件格式不正确（必须为.jpeg/.jpg/.gif/.bmp/.png文件）');");
                            out.println("</script>");
                            //return null;
                            continue;
                        }
                        // 获得上传路径的绝对路径地址(resources/upload)-->
                        String realPath = request.getSession().getServletContext().getRealPath("/" + DirectoryName);
                        System.out.println(realPath);
                        // 如果路径不存在，则创建该路径
                        File realPathDirectory = new File(realPath);
                        if (realPathDirectory == null || !realPathDirectory.exists()) {
                            realPathDirectory.mkdirs();
                        }
                        // 重命名上传后的文件名采用UUID的方式随即命名.jpg
                        fileName = java.util.UUID.randomUUID().toString() + suffix;
                        // 定义上传路径 ...resources/upload/xxxx.jpg
                        File uploadFile = new File(realPathDirectory + "/" + fileName);
                        //System.out.println(uploadFile);
                        file.transferTo(uploadFile);
                    }
                }

            }
        }
        return fileName;

//        // 图片名称
//        String fileName = null;
//
//        FileItemFactory factory = new DiskFileItemFactory();
//
//        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
//        //servletFileUpload.setFileSizeMax(MAX_FILE_SIZE);
//
//        try {
//            List<FileItem> fileitem = servletFileUpload.parseRequest(request);
//            Iterator<FileItem> fileitemIndex = fileitem.iterator();
//            if (fileitemIndex.hasNext()) {
//                FileItem file = fileitemIndex.next();
//                String fileClientName = getFileName(file.getName());
//                String suffix = fileClientName.substring(fileClientName.lastIndexOf(".")).toLowerCase();
//                if (!fileTypes.contains(suffix)) {
//                    out.println("<script type=\"text/javascript\">");
//                    out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件格式不正确（必须为.jpeg/.jpg/.gif/.bmp/.png文件）');");
//                    out.println("</script>");
//                    return null;
//                    //continue;
//                }
//                String realPath = request.getSession().getServletContext().getRealPath("/" + DirectoryName);
//                System.out.println(realPath);
//                // 如果路径不存在，则创建该路径
//                File realPathDirectory = new File(realPath);
//                if (realPathDirectory == null || !realPathDirectory.exists()) {
//                    realPathDirectory.mkdirs();
//                }
//                // 重命名上传后的文件名采用UUID的方式随即命名.jpg
//                 fileName = java.util.UUID.randomUUID().toString() + suffix;
//                // 定义上传路径 ...resources/upload/xxxx.jpg
//                File uploadFile = new File(realPathDirectory + "/" + fileName);
//                file.write(uploadFile);
//
//            }
//
//
//
//
//        } catch (FileUploadException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return fileName;

    }

    /**
     * ckeditor文件上传功能，回调，传回图片路径，实现预览效果。
     *
     * @Title ckeditor
     * @param request
     * @param response
     * @param DirectoryName
     *            文件上传目录：比如upload(无需带前面的/) upload/..
     * @throws IOException
     */
    public static void ckeditor(HttpServletRequest request, HttpServletResponse response, String DirectoryName)
            throws IOException {
        String fileName = upload(request,response,DirectoryName);
        // 结合ckeditor功能
        // imageContextPath为图片在服务器地址，如resources/upload/xxxx.jpg,非绝对路径
        if(fileName!=null){
            String imageContextPath = request.getContextPath() + "/" + DirectoryName + "/" + fileName;
            response.setContentType("text/html;charset=UTF-8");
            String callback = request.getParameter("CKEditorFuncNum");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageContextPath + "',''" + ")");
            out.println("</script>");
            out.flush();
            out.close();
        }
    }

    /**
     * 获取文件名称
     * @param str
     * @return
     */
    private static String getFileName(String str){
        int index = str.lastIndexOf("/");
        if(-1 != index){
            return str.substring(index);
        } else {
            return str;
        }
    }
}

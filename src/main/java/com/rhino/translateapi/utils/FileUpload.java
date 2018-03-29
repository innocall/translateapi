package com.rhino.translateapi.utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * 文件上传
 */
public class FileUpload {

    private static final long uploadSize = 12000000;
    public static String uploadImage(MultipartFile file, HttpServletRequest request, Map<String, Object> param) {
        String destPath = "";
        if(!file.isEmpty()) {
            String fileType = file.getContentType();
            long fileSize = file.getSize();
            if(!"application/octet-stream".equals(fileType) && !"audio/flac".equals(fileType)&& !"audio/mp3".equals(fileType)) {
                param.put("success", Boolean.valueOf(false));
                param.put("msg", "请选择正确的文件类型!");
            } else if(fileSize < uploadSize) {
                MultipartFile image = file;
                String imageSaveFile = request.getSession().getServletContext().getRealPath("/") + File.separator + "upload";
                String uploadDirPath =  imageSaveFile + File.separator + "raw" + File.separator + DateUtils.getDate2();
                File destFile = new File(uploadDirPath);
                try {
                    if(!destFile.exists()) {
                        destFile.mkdirs();
                    }
                    String e = file.getOriginalFilename();
                    e = e.substring(e.lastIndexOf("."), e.length());
                    File uploadFile = new File(destFile, System.currentTimeMillis() + e);
                    FileCopyUtils.copy(image.getBytes(), uploadFile);
                    destPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "upload" + File.separator + "raw" + File.separator + DateUtils.getDate2() + File.separator + uploadFile.getName();
                    param.put("destPath", destPath);
                    param.put("success", Boolean.valueOf(true));
                } catch (Exception var16) {
                    param.put("msg", "上传文件失败！");
                    param.put("success", Boolean.valueOf(false));
                }
            } else {
                param.put("success", Boolean.valueOf(false));
                param.put("msg", "文件过大!");
            }
        } else {
            param.put("success", Boolean.valueOf(false));
            param.put("msg", "上传文件不能为空!");
        }
        return destPath;
    }
}

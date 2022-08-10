package com.dc18669.upload.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
public class UploadWithProgressController {

    @RequestMapping("upload_with_progress_bar")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        System.out.println("=========================请求成功=================================");
        String uploadPath = "G:\\upload\\";
        File f = new File(uploadPath);
        boolean isMkdirs = true;
        if (!f.exists()) {
            isMkdirs = f.mkdirs();
        }

        if (isMkdirs) {
            f = new File(uploadPath, createFileName(Objects.requireNonNull(file.getOriginalFilename())));
            file.transferTo(f);
            return "上传成功";
        } else {
            return "上传失败";
        }


    }

    private String createFileName(String oldFileName) {
        String first = "New_Upload" + System.currentTimeMillis();
        String suffix = oldFileName.substring(oldFileName.lastIndexOf('.'));
        return first + suffix;
    }

}

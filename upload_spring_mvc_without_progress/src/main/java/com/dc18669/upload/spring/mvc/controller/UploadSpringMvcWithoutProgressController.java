package com.dc18669.upload.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Controller
public class UploadSpringMvcWithoutProgressController {

    @RequestMapping("/upload_without_progress")
    public void uploadSpringMvcWithoutProgress(MultipartFile uploadFileName) throws IOException {
        System.out.println("=========================请求成功======================");
        String uploadPath = "G:\\upload\\";
        File file = new File(uploadPath);
        boolean isMkdir = true;
        if (!file.exists()) {
            isMkdir = file.mkdirs();
        }

        if (isMkdir) {
            String name = uploadFileName.getName();
            System.out.println("name = " + name);
            String submittedFileName = uploadFileName.getOriginalFilename();
            System.out.println(submittedFileName);

            assert submittedFileName != null;
            file = new File(uploadPath + createFileName(submittedFileName));

            uploadFileName.transferTo(file);
        }
    }


    private String createFileName(String oldFileName) {
        String first = "New_upload" + System.currentTimeMillis();
        String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
        return first + suffix;
    }

}

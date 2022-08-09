package com.dc18669.upload.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadWithProgressController {

    @RequestMapping("upload_with_progress_bar")
    public String upload(MultipartFile file) {
        return null;
    }

}

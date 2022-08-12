package com.dc18669.upload.spring.mvc.controller;

import com.dc18669.upload.spring.mvc.bean.Progress;
import com.dc18669.upload.spring.mvc.utils.ToolUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
public class UploadWithProgressController {

    @RequestMapping("upload_with_progress_bar")
    @ResponseBody
    public String upload(MultipartFile file, HttpSession session) throws IOException {
        System.out.println("=========================请求成功=================================");
        String uploadPath = "G:\\upload\\";
        File f = new File(uploadPath);
        boolean isMkdirs = true;
        if (!f.exists()) {
            isMkdirs = f.mkdirs();
        }

        if (isMkdirs) {
            f = new File(uploadPath, ToolUtils.createFileName(Objects.requireNonNull(file.getOriginalFilename())));
            file.transferTo(f);
            session.removeAttribute("progress");
            return "success";
        } else {
            return "failed";
        }
    }
}

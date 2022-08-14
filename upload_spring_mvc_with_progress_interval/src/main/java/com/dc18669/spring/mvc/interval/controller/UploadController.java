package com.dc18669.spring.mvc.interval.controller;

import com.dc18669.spring.mvc.interval.model.ProgressBar;
import com.dc18669.spring.mvc.interval.utils.ToolUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
@SessionAttributes("progress")
public class UploadController {

    @RequestMapping("upload_with_progress_bar")
    @ResponseBody
    public String uploadFile(MultipartFile file) throws IOException {
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
            return "success";
        } else {
            return "failed";
        }
    }

    @RequestMapping("upload_progress")
    @ResponseBody
    public ProgressBar getProgress(HttpSession session) {
        System.out.println("==================请求获取进度====================");
        System.out.println("获取进度信息：" + session.getAttribute("progress"));
        return (ProgressBar) session.getAttribute("progress");
    }
}

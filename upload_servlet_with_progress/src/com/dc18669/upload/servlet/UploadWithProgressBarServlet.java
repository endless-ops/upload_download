package com.dc18669.upload.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@MultipartConfig
public class UploadWithProgressBarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uploadPath = "G:\\upload\\";
        File file = new File(uploadPath);
        boolean mkdirs = true;
        if (!file.exists()) {
            mkdirs = file.mkdirs();
        }

        if (mkdirs) {
            Part part = req.getPart("file");
            System.out.println(part);
            String oldFileName = part.getSubmittedFileName();
            String newFileName = createFileName(oldFileName);
            file = new File(uploadPath + newFileName);
            part.write(file.getAbsolutePath());
        } else {
            System.out.println("待存放文件的目录为创建");
        }
    }

    private String createFileName(String oldFileName) {
        String first = "New_Upload_" + System.currentTimeMillis();
        String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
        return first + suffix;
    }
}

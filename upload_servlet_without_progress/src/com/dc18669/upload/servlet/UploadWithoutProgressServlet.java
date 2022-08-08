package com.dc18669.upload.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@MultipartConfig
public class UploadWithoutProgressServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("==================请求成功================");

        String contentType = req.getContentType();
        System.out.println(contentType);

        String uploadFileName = req.getParameter("uploadFileName");  // 不可用


        Part part = req.getPart("uploadFileName");
        String submittedFileName = part.getSubmittedFileName();
        System.out.println(submittedFileName);

        String uploadPath = "G:\\upload\\";
        File file = new File(uploadPath);
        boolean isMkdir = true;
        if (!file.exists()) {
            isMkdir = file.mkdirs();
        }

        if (isMkdir) {
            file = new File(uploadPath + createFileName(submittedFileName));
            part.write(file.getAbsolutePath());
        } else {
            System.out.println("创建目录失败！！！");
        }

    }

    private String createFileName(String oldFileName) {
        String first = "Upload" + System.currentTimeMillis();
        String suffix = oldFileName.substring(oldFileName.indexOf("."));
        return first + suffix;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}

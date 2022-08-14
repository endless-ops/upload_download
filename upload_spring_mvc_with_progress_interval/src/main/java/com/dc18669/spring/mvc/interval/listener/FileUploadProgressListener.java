package com.dc18669.spring.mvc.interval.listener;

import com.dc18669.spring.mvc.interval.model.ProgressBar;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;


@Component
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public void update(long pBytesRead, long pContentLength, int pItems) {
        System.out.println("===============FileUploadProgressListener========update====================");
        ProgressBar progressBar = new ProgressBar();//保存上传状态
        progressBar.setpBytesRead(pBytesRead);
        progressBar.setpContentLength(pContentLength);
        progressBar.setpItems(pItems);
        session.setAttribute("progress", progressBar);

        // System.out.println(">>>>>>>>>>>>>>>>>>>>" + session.getAttribute("progress"));
    }

}

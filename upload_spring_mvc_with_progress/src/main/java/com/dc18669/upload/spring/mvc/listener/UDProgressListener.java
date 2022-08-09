package com.dc18669.upload.spring.mvc.listener;

import com.dc18669.upload.spring.mvc.bean.Progress;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UDProgressListener implements ProgressListener {

    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * 更新进度
     *
     * @param l  已读取了多少个字节
     * @param l1 文件总大小
     * @param i  已解析到第几个
     */
    @Override
    public void update(long l, long l1, int i) {

        Progress progress = new Progress();

        // 文件总大小
        progress.setTotalFileSize(l1);
        // 已进行大小
        progress.setSizeDone(l);
        // 剩余大小
        progress.setRemainingSize(0l);
        // 已进行时间
        progress.setPerformedOn(0l);
        // 剩余时间
        progress.setTimeRemaining(0l);
        // 下载速度
        progress.setSpeed("");
        // 百分比
        String percentage = String.format("%.2f", ((l * 1.0 / l1) * 100));
        System.out.println("percentage = " + percentage);
        progress.setPercentage(percentage + "%");
        // 总耗时
        progress.setTotalTime(0l);
        // 已解析到第几个
        progress.setCertain(i);

        session.setAttribute("progress", progress);

    }

}

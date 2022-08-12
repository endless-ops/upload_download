package com.dc18669.upload.spring.mvc.listener;

import com.dc18669.upload.spring.mvc.bean.Progress;
import com.dc18669.upload.spring.mvc.utils.ToolUtils;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UDProgressListener implements ProgressListener {

    private String timeRemaining;


    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        System.out.println("session 当前时间 ：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        this.session.setAttribute("start", System.currentTimeMillis());

        Progress progress = new Progress();
        this.session.setAttribute("progress", progress);
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
        long end = System.currentTimeMillis();
        Progress progress = (Progress) this.session.getAttribute("progress");

        // 已解析到第几个
        progress.setCertain(i);

        // 文件总大小 字节
        progress.setTotalFileSize(l1);
        progress.setTotalSize(ToolUtils.getSize(l1));

        // 已进行大小
        progress.setSizeDone(l);
        progress.setDoneSize(ToolUtils.getSize(l));

        // 剩余大小
        progress.setRemainingSize((l1 - l));
        progress.setRemainSize(ToolUtils.getSize(l1 - l));

        // 百分比
        progress.setPercentage(ToolUtils.getPercentage(l, l1));

        // 已进行时间
        progress.setPerformedOn(ToolUtils.calPerformedOn(end, (Long) session.getAttribute("start")));



        /*  以下三个需要重新设计 */
        // 下载速度
        // progress.setSpeed(getSpeed(0, l, session, end));

        // 总耗时
        // progress.setTotalTime(calTotalTime(session, l1, end, l));
        // 剩余时间
        // progress.setTimeRemaining(timeRemaining);


        // System.out.println(progress);

        this.session.setAttribute("progress", progress);
    }

    // 总耗时、速度、剩余时间 都需要重新设计
}

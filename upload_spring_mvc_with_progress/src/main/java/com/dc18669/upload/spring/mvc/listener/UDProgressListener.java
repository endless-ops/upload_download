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
        Progress progress = new Progress();
        this.session.setAttribute("progress", progress);


        System.out.println("UDProgressListener session  : " + this.session);
        System.out.println("session 当前时间 ：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        this.session.setAttribute("start", System.currentTimeMillis());


    }

    /**
     * 更新进度
     *
     * @param pBytesRead  已读取了多少个字节
     * @param pContentLength 文件总大小
     * @param pItems  已解析到第几个
     */
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        long end = System.currentTimeMillis();
        Progress progress = (Progress) this.session.getAttribute("progress");

        progress.setpBytesRead(pBytesRead);
        progress.setpContentLength(pContentLength);
        progress.setpItems(pItems);






        // 已解析到第几个
        progress.setCertain(pItems);

        // 文件总大小 字节
        progress.setTotalFileSize(pContentLength);
        progress.setTotalSize(ToolUtils.getSize(pContentLength));

        // 已进行大小
        progress.setSizeDone(pBytesRead);
        progress.setDoneSize(ToolUtils.getSize(pBytesRead));

        // 剩余大小
        progress.setRemainingSize((pContentLength - pBytesRead));
        progress.setRemainSize(ToolUtils.getSize(pContentLength - pBytesRead));

        // 百分比
        progress.setPercentage(ToolUtils.getPercentage(pBytesRead, pContentLength));

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

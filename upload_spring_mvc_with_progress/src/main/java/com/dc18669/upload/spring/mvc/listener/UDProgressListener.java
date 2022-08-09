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
     * @param l 已读取了多少个字节
     * @param l1 文件总大小
     * @param i 已解析到第几个
     */
    @Override
    public void update(long l, long l1, int i) {

        Progress progress = new Progress();

        // 文件总大小

        // 已进行大小

        // 剩余大小

        // 已进行时间

        // 剩余时间

        // 下载速度

        // 百分比


        // 总耗时

        // 已解析到第几个


    }

}

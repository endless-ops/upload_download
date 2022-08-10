package com.dc18669.upload.spring.mvc.listener;

import com.dc18669.upload.spring.mvc.bean.Progress;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UDProgressListener implements ProgressListener {

    private final static long KB = 1024;
    private final static long MB = KB * KB;
    private final static long GB = MB * KB * KB;
    private final static long MS_HOUR = 60 * 60 * 1000;
    private final static long MS_MINUTE = 60 * 1000;
    private final static long MS_SECOND = 1000;


    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        System.out.println("session 当前时间 ：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        this.session.setAttribute("start", System.currentTimeMillis());
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
        Progress progress = new Progress();

        // 文件总大小
        progress.setTotalFileSize(l1);
        // 已进行大小
        progress.setSizeDone(l);
        // 剩余大小
        progress.setRemainingSize((l1 - l));
        // 已进行时间
        progress.setPerformedOn(calPerformedOn(end, session));
        // 下载速度
        progress.setSpeed(getSpeed(0, l, session, end));
        // 百分比
        String percentage = String.format("%.2f", ((l * 1.0 / l1) * 100));
        System.out.println("percentage = " + percentage);
        progress.setPercentage(percentage + "%");
        // 总耗时
        progress.setTotalTime("");
        // 剩余时间
        progress.setTimeRemaining("");
        // 已解析到第几个
        progress.setCertain(i);

        session.setAttribute("progress", progress);
    }

    /**
     * @param bytes 字节
     * @return 返回 千字节、兆字节和吉字节
     */
    protected Map<String, Object> calculate(long bytes) {
        Map<String, Object> map = new HashMap<>();
        double cal = 0;
        // 转为吉字节
        if (bytes > GB) {
            cal = bytes * 1.0 / GB;
            map.put("unit", "GB");
        } else if (bytes > MB) {
            cal = bytes * 1.0 / MB;
            map.put("unit", "MB");
        } else if (bytes > KB) {
            cal = bytes * 1.0 / KB;
            map.put("unit", "KB");
        } else {
            cal = bytes;
            map.put("unit", "B");
        }
        map.put("cal", cal);
        return map;
    }

    /**
     * -计算时间差
     *
     * @param time1   时间1
     * @param time2   时间2
     * @param session 存储的是开始的时间
     * @param type    计算类型
     * @return 返回值
     */
    public String calTime(long time1, HttpSession session, long time2, int type) {
        String calTime = null;
        long ms = 0;

        if (1 == type) {
            if (session != null) {
                long start = (long) session.getAttribute("start");
                ms = time1 - start;
            }
        } else if (2 == type) {
            ms = time2 - time1;
        } else if (3 == type) {
            ms = time1;
        }

        long hour = 0;
        long hour_mod = 0;
        if (ms >= MS_HOUR) {
            hour = ms / MS_HOUR;
            hour_mod = ms - MS_HOUR * hour;
        }

        long minute = 0;
        long minute_mod = 0;
        if (hour_mod >= MS_MINUTE) {
            minute = hour_mod / MS_MINUTE;
            minute_mod = hour_mod - MS_MINUTE * minute;
            hour_mod = 0;
        } else if (ms >= MS_MINUTE) {
            minute = ms / MS_MINUTE;
            minute_mod = ms - MS_MINUTE * minute;
        }

        long second = 0;
        if (hour_mod >= MS_SECOND) {
            second = hour_mod / MS_SECOND;
        } else if (minute_mod >= MS_SECOND) {
            second = minute_mod / MS_SECOND;
        } else if (ms >= MS_SECOND) {
            second = ms / MS_SECOND;
        }

        if (second < 10 && second > 0) {
            calTime = "0" + second;
        } else if (second >= 10) {
            calTime = second + "";
        } else {
            calTime = "00";
        }

        if (minute > 0 && minute < 10) {
            calTime = "0" + minute + ":" + calTime;
        } else if (minute >= 10) {
            calTime = minute + ":" + calTime;
        } else {
            calTime = "00:" + calTime;
        }

        if (hour > 0 && hour < 10) {
            calTime = "0" + hour + ":" + calTime;
        } else if (hour >= 10) {
            calTime = hour + ":" + calTime;
        }

        return calTime;
    }

    /**
     * @param time1 目前时间
     * @param time2 总时间
     * @param type  type的值为2
     * @return 返回剩余时间
     */
    protected String calTimeRemaining(long time1, long time2, int type) {
        return calTime(time1, null, time2, type);
    }

    /**
     * -
     *
     * @param time1   目前时间
     * @param session 开始上传时的时间
     * @return 返回已经上传的多长时间
     */
    protected String calPerformedOn(long time1, HttpSession session) {
        return calTime(time1, session, 0, 1);
    }


    protected Map<String, Object> calSpeed(long startSize, long endSize, HttpSession session, long endTime) {
        Map<String, Object> map = calculate(endSize);
        Map<String, Object> m = new HashMap<>();
        long start = (long) session.getAttribute("start");
        double speed = (double) map.get("cal") / (endTime - start) / 1000;
        m.put("speed", speed);
        m.put("unit", map.get("unit"));
        return m;
    }

    protected String getSpeed(long startSize, long endSize, HttpSession session, long endTime) {
        Map<String, Object> map = calSpeed(startSize, endSize, session, endTime);
        double speed = (double) map.get("speed");
        String unit = (String) map.get("unit");
        return speed + unit + "/s";
    }
}

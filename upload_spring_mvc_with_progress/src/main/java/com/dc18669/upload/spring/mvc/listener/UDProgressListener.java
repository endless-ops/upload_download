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
//        progress.setCertain(i);

        // 文件总大小 字节
        progress.setTotalFileSize(l1);
        progress.setTotalSize(getSize(calculate(l1)));

        // 已进行大小
        progress.setSizeDone(l);
        progress.setDoneSize(getSize(calculate(l)));

        // 剩余大小
        progress.setRemainingSize((l1 - l));
        progress.setRemainSize(getSize(calculate((l1 - l))));

        // 百分比
        String percentage = String.format("%.2f", ((l * 1.0 / l1) * 100));
        progress.setPercentage(percentage + "%");
        System.out.println("当前进度 ： " + percentage);

        // 已进行时间
        progress.setPerformedOn(calPerformedOn(end, session));



        /*  以下三个需要重新设计 */
        // 下载速度
        // progress.setSpeed(getSpeed(0, l, session, end));

        // 总耗时
        // progress.setTotalTime(calTotalTime(session, l1, end, l));
        // 剩余时间
        // progress.setTimeRemaining(timeRemaining);


        // System.out.println(progress);

//        session.setAttribute("progress", progress);
    }

    /**
     * -将字节 转换为 字节、千字节、兆字节以及吉字节
     *
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
     * -将毫秒转换为 时分秒
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
        long hour = 0;
        long hour_mod = 0;
        long minute = 0;
        long minute_mod = 0;
        long second = 0;

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

        if (ms >= MS_HOUR) {
            hour = ms / MS_HOUR;
            hour_mod = ms - MS_HOUR * hour;
        }


        if (hour_mod >= MS_MINUTE) {
            minute = hour_mod / MS_MINUTE;
            minute_mod = hour_mod - MS_MINUTE * minute;
        } else if (hour_mod >= MS_SECOND) {
            second = hour_mod / MS_SECOND;
        } else if (ms >= MS_MINUTE && ms < MS_HOUR) {
            minute = ms / MS_MINUTE;
            minute_mod = ms - MS_MINUTE * minute;
        }

        if (minute_mod >= MS_SECOND) {
            second = minute_mod / MS_SECOND;
        } else if (ms >= MS_SECOND && ms < MS_MINUTE) {
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
     * - 计算剩余时间
     *
     * @param time1 目前时间
     * @param time2 总时间
     * @param type  type的值为2
     * @return 返回剩余时间
     */
    protected String calTimeRemaining(long time1, long time2, int type) {
        return calTime(time1, null, time2, type);
    }

    /**
     * -获取已经上传或下载的时间
     *
     * @param time1   目前时间
     * @param session 开始上传时的时间
     * @return 返回已经上传的多长时间
     */
    protected String calPerformedOn(long time1, HttpSession session) {
        return calTime(time1, session, 0, 1);
    }


    /**
     * -计算速度
     *
     * @param startSize
     * @param endSize
     * @param session
     * @param endTime
     * @return
     */
    protected Map<String, Object> calSpeed(long startSize, long endSize, HttpSession session, long endTime) {
        Map<String, Object> map = calculate(endSize);
        Map<String, Object> m = new HashMap<>();
        long start = (long) session.getAttribute("start");
        long timeDifference = (endTime - start) / 1000;
        double speed = (double) map.get("cal") / timeDifference;
        m.put("speed", speed);
        m.put("unit", map.get("unit"));
        return m;
    }

    /**
     * -计算速度
     *
     * @param startSize
     * @param endSize
     * @param session
     * @param endTime
     * @return
     */
    protected String getSpeed(long startSize, long endSize, HttpSession session, long endTime) {
        Map<String, Object> map = calSpeed(startSize, endSize, session, endTime);
        double speed = (double) map.get("speed");
        String unit = (String) map.get("unit");
        return String.format("%.2f", speed) + unit + "/s";
    }

    /**
     * -获取带有单位的大小
     *
     * @param map 计算后的大小
     * @return 返回 转换后的大小
     */
    protected String getSize(Map<String, Object> map) {
        double size = (double) map.get("cal");
        String unit = (String) map.get("unit");
        return String.format("%.2f", size) + unit;
    }

    /**
     * -总耗时
     *
     * @param session
     * @param totalSize
     * @param endTime
     * @param endSize
     * @return
     */
    protected String calTotalTime(HttpSession session, long totalSize, long endTime, long endSize) {
        long start = (long) session.getAttribute("start");
        System.out.println("start : " + start);
        long timeDifference = (endTime - start) / 1000;
        System.out.println("timeDifference : " + timeDifference);
        double speed = endSize * 1.0 / timeDifference;
        System.out.println("speed : " + speed);
        double v = totalSize / speed;
        System.out.println("v : " + v);
        long tt = (long) v;
        System.out.println("总耗时 ：" + tt);

        timeRemaining = calTimeRemain(session, endTime, tt);

        return calTime(tt, null, 0, 3);
    }


    /**
     * -剩余时间
     *
     * @param session
     * @param endTime
     * @param totalTime
     * @return
     */
    protected String calTimeRemain(HttpSession session, long endTime, long totalTime) {
        long start = (long) session.getAttribute("start");
        return calTimeRemaining((endTime - start), totalTime, 2);
    }


    // 总耗时、速度、剩余时间 都需要重新设计
}

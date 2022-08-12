package com.dc18669.upload.spring.mvc.utils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class ToolUtils {

    private final static long KB = 1024;
    private final static long MB = KB * KB;
    private final static long GB = MB * KB * KB;

    private final static long MS_HOUR = 60 * 60 * 1000;
    private final static long MS_MINUTE = 60 * 1000;
    private final static long MS_SECOND = 1000;

    public static String createFileName(String oldFileName) {
        String first = "New_Upload" + System.currentTimeMillis();
        String suffix = oldFileName.substring(oldFileName.lastIndexOf('.'));
        return first + suffix;
    }

    /**
     * -将字节 转换为 字节、千字节、兆字节以及吉字节
     *
     * @param bytes 字节
     * @return 返回 千字节、兆字节和吉字节
     */
    public static Map<String, Object> calculate(long bytes) {
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


    public static String getSize(long bytes) {
        Map<String, Object> calculate = calculate(bytes);
        double size = (double) calculate.get("cal");
        String unit = (String) calculate.get("unit");
        return String.format("%.2f", size) + unit;
    }


    public static String getPercentage(long l1, long l2) {
        return String.format("%.2f", getPercentage(l1, l2, 0)) + "%";
    }


    public static double getPercentage(long l1, long l2, int i) {
        return (l1 * 1.0 / l2) * 100;
    }

    /**
     * -将毫秒转换为 时分秒
     *
     * @param time1 时间1
     * @param time2 时间2
     * @param start 存储的是开始的时间
     * @param type  计算类型
     * @return 返回值
     */
    public static String calTime(long time1, long start, long time2, int type) {
        String calTime = null;
        long ms = 0;
        long hour = 0;
        long hour_mod = 0;
        long minute = 0;
        long minute_mod = 0;
        long second = 0;

        if (1 == type) {
            ms = time1 - start;
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
     * -获取已经上传或下载的时间
     *
     * @param time1 目前时间
     * @param time2 开始上传时的时间
     * @return 返回已经上传的多长时间
     */
    public static String calPerformedOn(long time1, long time2) {
        return calTime(time1, time2, 0, 1);
    }

}

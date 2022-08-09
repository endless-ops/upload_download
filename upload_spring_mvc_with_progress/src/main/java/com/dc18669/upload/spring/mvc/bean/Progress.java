package com.dc18669.upload.spring.mvc.bean;

public class Progress {
    // 总耗时
    private long totalTime;

    // 已进行时间
    private long performedOn;

    // 剩余时间
    private long timeRemaining;

    // 文件总大小
    private long totalFileSize;

    // 已进行大小
    private long sizeDone;

    // 剩余大小
    private long remainingSize;

    // 上传下载速度
    private String speed;

    // 百分比
    private String percentage;

    // 第几个文件
    private int certain;

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getPerformedOn() {
        return performedOn;
    }

    public void setPerformedOn(long performedOn) {
        this.performedOn = performedOn;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(long totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public long getSizeDone() {
        return sizeDone;
    }

    public void setSizeDone(long sizeDone) {
        this.sizeDone = sizeDone;
    }

    public long getRemainingSize() {
        return remainingSize;
    }

    public void setRemainingSize(long remainingSize) {
        this.remainingSize = remainingSize;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public int getCertain() {
        return certain;
    }

    public void setCertain(int certain) {
        this.certain = certain;
    }
}

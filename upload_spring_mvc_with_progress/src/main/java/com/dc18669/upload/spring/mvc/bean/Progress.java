package com.dc18669.upload.spring.mvc.bean;

public class Progress {
    // 总耗时
    private String totalTime;

    // 已进行时间
    private String performedOn;

    // 剩余时间
    private String timeRemaining;

    // 文件总大小
    private long totalFileSize;
    // 文件总大小
    private String totalSize;

    // 已进行大小
    private long sizeDone;
    // 已进行大小
    private String doneSize;

    // 剩余大小
    private long remainingSize;
    // 剩余大小
    private String remainSize;

    // 上传下载速度
    private String speed;

    // 百分比
    private String percentage;

    // 第几个文件
    private int certain;

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getPerformedOn() {
        return performedOn;
    }

    public void setPerformedOn(String performedOn) {
        this.performedOn = performedOn;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
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

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getDoneSize() {
        return doneSize;
    }

    public void setDoneSize(String doneSize) {
        this.doneSize = doneSize;
    }

    public String getRemainSize() {
        return remainSize;
    }

    public void setRemainSize(String remainSize) {
        this.remainSize = remainSize;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "totalTime='" + totalTime + '\'' +
                ", performedOn='" + performedOn + '\'' +
                ", timeRemaining='" + timeRemaining + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }
}

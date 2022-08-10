package com.dc18669.upload.spring.mvc.utils;

public class ToolUtils {

    public static String createFileName(String oldFileName) {
        String first = "New_Upload" + System.currentTimeMillis();
        String suffix = oldFileName.substring(oldFileName.lastIndexOf('.'));
        return first + suffix;
    }
}

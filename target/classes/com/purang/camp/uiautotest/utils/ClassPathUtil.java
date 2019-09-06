package com.purang.camp.uiautotest.utils;

import java.net.URL;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class ClassPathUtil {
/**
    *获取类的路径
    * copied from csdn http://blog.csdn.net/sunyujia/article/details/2957481
    * @param clazz
    * @return
*/
    public static File getClassFile(Class<?> clazz) {
        URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".class");
        if (path == null) {
            String name = clazz.getName().replaceAll("[.]", "/");
            path = clazz.getResource("/" + name + ".class");
        }
        return new File(path.getFile());
    }

    public static String getClassFilePath(Class<?> clazz) {
        try {
            return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static File getClassPathFile(Class<?> clazz) {
        File file = getClassFile(clazz);
        for (int i = 0, count = clazz.getName().split("[.]").length; i < count; i++) {
            file = file.getParentFile();
        }
        if (file.getName().toUpperCase().endsWith(".JAR!")) {
            file = file.getParentFile();
        }
        return file;
    }

    public static String getClassPath(Class<?> clazz) {
        try {
            return java.net.URLDecoder.decode(getClassPathFile(clazz).getAbsolutePath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}

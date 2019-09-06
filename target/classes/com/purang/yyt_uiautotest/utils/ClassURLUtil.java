package com.purang.yyt_uiautotest.utils;

import java.net.URL;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class ClassURLUtil {

	/**
	 * copied from csdn by liuyi027, from
	 * http://blog.csdn.net/sunyujia/article/details/2957481
	 * 
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

	/**
	 * copied from csdn by liuyi027, from
	 * http://blog.csdn.net/sunyujia/article/details/2957481
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassFilePath(Class<?> clazz) {
		try {
			return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * copied from csdn by liuyi027, from
	 * http://blog.csdn.net/sunyujia/article/details/2957481
	 * 
	 * @param clazz
	 * @return
	 */
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

	/**
	 * copied from csdn by liuyi027, from
	 * http://blog.csdn.net/sunyujia/article/details/2957481
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassPath(Class<?> clazz) {
		try {
			return java.net.URLDecoder.decode(getClassPathFile(clazz).getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
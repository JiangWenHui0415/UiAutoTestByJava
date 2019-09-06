package com.purang.yyt_uiautotest.utils;

import java.io.IOException;

/**
 * @author 测试仔刘毅
 */

import java.util.Properties;

public class ReadConfiguration {

	private final Properties property = new Properties();

	/**
	 * construct with parameter intialize
	 * 
	 * @param fileName
	 *            whole path and name of config file
	 * @throws RuntimeException
	 *             , IllegalArgumentException
	 */
	public ReadConfiguration(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("the parameter can not be null!");
		}
		try {
			property.load(this.getClass().getResourceAsStream(fileName));
		} catch (IOException ioe) {
			LoggerUtils.error(ioe);
			throw new RuntimeException(ioe);
		}
	}

	/**
	 * get specified key in config files
	 * 
	 * @param key
	 *            the key name to get value
	 */
	public String get(String key) {
		String keyValue = null;
		if (property.containsKey(key)) {
			keyValue = (String) property.get(key);
		}
		return keyValue;
	}
}
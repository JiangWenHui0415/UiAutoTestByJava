package com.purang.yyt_uiautotest.utils;

import java.util.HashMap;
import java.util.Map;
import com.purang.yyt_uiautotest.core.webdriver.WebDriverWebPublic;
import com.purang.yyt_uiautotest.logging.webdriver.LogMapKey;

public class StackTraceUtils {

	/**
	 * get the trace level index of the running test class.
	 * 
	 * @param trace
	 *            the StackTraceElement array.
	 * @return the index of the trace deepth of class.
	 */
	public int getTraceClassLevel(StackTraceElement[] trace) {
		for (int i = 1; i < trace.length - 1; i++) {
			if (!trace[i].getClassName().equals(WebDriverWebPublic.class.getName())) {
				return i;
			}
		}
		return 2;
	}

	/**
	 * get the trace level index of the running test method.
	 * 
	 * @param trace
	 *            the StackTraceElement array.
	 * @return the index of the trace deepth of method.
	 */
	public int getTraceMethodLevel(StackTraceElement[] trace) {
		for (int i = trace.length - 1; i > 0; i--) {
			if (trace[i].getMethodName().equals("invoke0")) {
				return i - 2;
			}
		}
		return 0;
	}

	/**
	 * Description: record log for listeners.
	 * 
	 * @param traces
	 *            the StackTraceElement.
	 * @param message
	 *            the user defined message info.
	 */
	public Map<String, String> traceRecord(StackTraceElement[] traces, String status, String message) {
		Map<String, String> logMap = new HashMap<String, String>();
		String methodName = null;
		for (int i = 0; i < traces.length; i++) {
			if (traces[i].getMethodName().equals("getStackTrace")) {
				methodName = traces[i + 2].getMethodName();
				break;
			}
		}
		StackTraceElement classTrace = traces[getTraceClassLevel(traces)];
		logMap.put(LogMapKey.METHOD_NAME_KEY.getValue(), methodName);
		logMap.put(LogMapKey.RUN_STATUS_KEY.getValue(), status);
		logMap.put(LogMapKey.MESSAGE_KEY.getValue(), message);
		logMap.put(LogMapKey.CLASS_NAME_KEY.getValue(), getClassTrace(classTrace));
		return logMap;
	}

	public Map<String, String> traceRecord(StackTraceElement[] traces, String status, String message, Integer level) {
		Map<String, String> logMap = new HashMap<String, String>();
		String methodName = null;
		for (int i = 0; i < traces.length; i++) {
			if (traces[i].getMethodName().equals("getStackTrace")) {
				methodName = traces[i + level].getMethodName();
				break;
			}
		}
		StackTraceElement classTrace = traces[getTraceClassLevel(traces)];
		logMap.put(LogMapKey.METHOD_NAME_KEY.getValue(), methodName);
		logMap.put(LogMapKey.RUN_STATUS_KEY.getValue(), status);
		logMap.put(LogMapKey.MESSAGE_KEY.getValue(), message);
		logMap.put(LogMapKey.CLASS_NAME_KEY.getValue(), getClassTrace(classTrace));
		return logMap;
	}

	/**
	 * Description: get the class and execute line number.
	 * 
	 * @param trace
	 *            the StackTraceElement.
	 * @return class name and running line number.
	 */
	public String getClassTrace(StackTraceElement trace) {
		return trace.getClassName() + " # " + trace.getLineNumber();
	}
}
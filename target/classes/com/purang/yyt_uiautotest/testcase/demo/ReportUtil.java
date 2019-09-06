package com.purang.yyt_uiautotest.testcase.demo;

import java.util.Calendar;

import org.testng.Reporter;

public class ReportUtil {
	private static String reportName = "YYT_AutoTest Report";

	private static String splitTimeAndMsg = "===";
	public static void log(String msg) {
		long timeMillis = Calendar.getInstance().getTimeInMillis();
		Reporter.log(timeMillis + splitTimeAndMsg + msg, true);
	}

	public static String getReportName() {
		return reportName;
	}

	public static String getSpiltTimeAndMsg() {
		return splitTimeAndMsg;
	}

	public static void setReportName(String reportName) {
		if(StringUtil.isNotEmpty(reportName)){
			ReportUtil.reportName = reportName;
		}
	}

}

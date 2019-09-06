package com.purang.camp.uiautotest.utils;

import java.util.Calendar;
import org.testng.Reporter;
import java.time.LocalDate;

public class ReportUtil {
    private static String reportName = "Camp_AutoTest Report_" + LocalDate.now();

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


package com.purang.camp.uiautotest.logging.webdriver;

import java.util.Map;
import java.util.logging.Logger;
import com.purang.camp.uiautotest.logging.webdriver.HtmlFormatter;

public class HtmlLogger {
    private String className;
    private String logPath;
    private String charSet;
    private static HtmlFormatter htmlWritter;
    private static Logger logger;

    /**
     * @param clsName
     *            the class name to be logged.
     * @param path
     *            the path where the log file to be located.
     * @param charSet
     *            the file charSet of log files.
     */
    public HtmlLogger(String clsName, String path, String charSet) {
        this.className = clsName;
        this.logPath = path;
        this.charSet = charSet;
    }

    /**
     * Description: initialize the Logger instance.
     *
     * @param startTime
     *            the time of test begins, by unit of millisecond.
     */
    public void init(long startTime) {
        logger = Logger.getLogger(className);
        htmlWritter = new HtmlFormatter(logPath + className + ".html");
        htmlWritter.setEncoding(charSet);
        htmlWritter.init(className, startTime);
    }

    /**
     * Description: appned log info line be line.
     *
     * @param logMap
     *            the log info hashmap.
     */
    public void recorder(Map<String, String> logMap) {
        logger.info(logMap.get(LogMapKey.MESSAGE_KEY.getValue()));
        htmlWritter.write(logMap);// HTML写入
    }

    /**
     * Description: write tail of log file and close file output stream.
     *
     * @param endTime
     *            the time of test ends, by unit of millisecond.
     */
    public void logDestory(long endTime) {
        htmlWritter.changeTime(endTime);
        htmlWritter.destory();
    }
}

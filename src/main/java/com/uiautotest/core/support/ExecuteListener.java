package com.purang.camp.uiautotest.core.support;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.purang.camp.uiautotest.logging.webdriver.HtmlLogger;
import com.purang.camp.uiautotest.utils.LoggerUtil;
import com.purang.camp.uiautotest.utils.ReadConfiguration;
import com.purang.camp.uiautotest.utils.StackTraceUtil;
import com.purang.camp.uiautotest.utils.StringBufferUtil;

public class ExecuteListener implements WebDriverEventListener {
    private final ReadConfiguration config = new ReadConfiguration("/com/purang/camp/uiautotest/core/webdriver/webdriver.properties");
    private final String cap_msg = config.get("CAPTURE_MESSAGE");
    private final RuntimeSupport support = new RuntimeSupport();
    private final StackTraceUtil stack = new StackTraceUtil();

    private String className = ExecuteListener.class.getName();
    private String filePath = ".\\log\\";
    private Map<String, String> infoMap;
    private HtmlLogger logger;

    public ExecuteListener() {
        throw new IllegalArgumentException("you must config the parameter correctly!");
    }

    public ExecuteListener(String location) {
        this.filePath = location.endsWith("/") || location.endsWith("\\") ? location : location + "/";
    }

    public ExecuteListener(String location, HtmlLogger logger) {
        this.filePath = location.endsWith("/") || location.endsWith("\\") ? location : location + "/";
        setLogger(logger);
    }

    public void setLogger(HtmlLogger logger) {
        this.logger = logger;
    }

    public void setClassName(String runClassName) {
        this.className = runClassName;
    }

    /**
     * Description: see if exception is instanceof WebDriverException.
     *
     * @param exception
     *            runtime exceptions.
     * @param driver
     *            the webdriver instance.
     * @throws RuntimeException
     */
    private void onWebDriverException(Throwable exception, WebDriver driver) {
        String fileName = filePath + className + StringBufferUtil.formatedTime("_yyyyMMdd_HHmmssSSS") + ".png";
        if (exception instanceof WebDriverException) {
            support.screenShot(driver, fileName);
            String message = "run " + cap_msg + " [" + fileName + "]";
            infoMap = stack.traceRecord(Thread.currentThread().getStackTrace(), "failed", message);
            logger.recorder(infoMap);
        }
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
    }

    public void afterClickOn(By by, WebDriver driver) {
    }

    public void afterChangeValueOf(By by, WebDriver driver) {
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {

    }

    /**
     * Description: override the onException method of WebDriverEventListener.
     *
     * @param exception
     *            runtime exceptions.
     * @param driver
     *            the webdriver instance.
     * @throws RuntimeException
     */
    @Override
    public void onException(Throwable exception, WebDriver driver) {
        try {
            onWebDriverException(exception, driver);
        } catch (Throwable unexpected) {
            LoggerUtil.error(unexpected);
            throw new RuntimeException(unexpected);
        }
    }

    @Override
    public void afterAlertAccept(WebDriver arg0) {

    }

    @Override
    public void afterAlertDismiss(WebDriver arg0) {

    }

    @Override
    public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver arg0) {

    }

    @Override
    public void beforeAlertAccept(WebDriver arg0) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver arg0) {

    }

    @Override
    public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver arg0) {

    }
}

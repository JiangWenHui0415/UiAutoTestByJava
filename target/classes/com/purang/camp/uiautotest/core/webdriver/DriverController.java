package com.purang.camp.uiautotest.core.webdriver;

import com.purang.camp.uiautotest.logging.webdriver.HtmlLogger;
import org.openqa.selenium.WebDriver;


public interface DriverController {
    WebDriver getDriver();

    HtmlLogger getLogger();

    void startServer(String clsName, Long startTime) throws Exception;

    void stopServer() throws Exception;

    void startWebDriver();

    void closeWebDriver();

    void quitWebDriver();

    void cleanBrowserProcess();

    void cleanDriverProcess();
}

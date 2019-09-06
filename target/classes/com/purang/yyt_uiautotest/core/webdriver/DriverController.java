package com.purang.yyt_uiautotest.core.webdriver;

import org.openqa.selenium.WebDriver;

import com.purang.yyt_uiautotest.logging.webdriver.HTMLLogger;

public interface DriverController {

	WebDriver getDriver();

	HTMLLogger getLogger();
	
	void startServer(String clsName, Long startTime) throws Exception;
	
	void stopServer() throws Exception;
	
	void startWebDriver();
	
	void closeWebDriver();
	
	void quitWebDriver();

	void cleanBrowserProcess();

	void cleanDriverProcess();
}
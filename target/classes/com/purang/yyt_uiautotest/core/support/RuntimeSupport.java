package com.purang.yyt_uiautotest.core.support;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.purang.yyt_uiautotest.tools.wingui.Win32GuiByAu3;
import com.purang.yyt_uiautotest.utils.LoggerUtils;

public class RuntimeSupport {

	private WebDriver driver;

	public RuntimeSupport() {
	}

	public RuntimeSupport(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * get random number between to int number
	 * @param min min number for random
	 * @param max max number for random
	 * @return random number
	 */
	public int getRadomNumber(int min, int max) {
		return (int) Math.round(Math.random() * max + min);
	}

	/**
	 * get free port of cureent machine
	 * @return the port of cureent machine
	 */
	public int getAvilibalePort() {
		int min = 11111;
		int max = 65535;
		int port = 1024;
		for (int i = min; i < max; i++) {
			ServerSocket socket = null;
			port = getRadomNumber(min, max);
			try {
				socket = new ServerSocket(port);
				socket.close();
				return i;
			} catch (IOException e) {
				System.out.println("============port " + port + " of localhost unavilibale=============");
			}
		}
		return 0;
	}

	/**
	 * take a screen shot and save the file by path and name
	 * 
	 * @param driver
	 *            the WebDriver instance.
	 * @param fileName
	 *            the file path&name of the screenshot to be saved.
	 */
	public void screenShot(WebDriver driver, String fileName) {
		try {
			screenShot(fileName, driver);
		} catch (WebDriverException we) {
			LoggerUtils.error("RemoteWebDriver截图异常，下面使用Autoit重新截屏！");
			new Win32GuiByAu3().screenCapture(fileName);
		} catch (IOException ie) {
			LoggerUtils.error("RemoteWebDriver截图保存异常！");
			new Win32GuiByAu3().screenCapture(fileName);
		}
	}

	/**
	 * take a screen shot and save the file by path and name
	 * 
	 * @param fileName
	 *            the file path&name of the screenshot to be saved
	 * @throws IllegalArgumentException WebDriverException IOException
	 */
	public void screenShot(String fileName) {
		if (null == driver) {
			throw new IllegalArgumentException("the construction must have WebDriver object parameter!");
		}
		try {
			screenShot(fileName, driver);
		} catch (WebDriverException we) {
			LoggerUtils.error("RemoteWebDriver截图异常，下面使用Autoit重新截屏！");
			new Win32GuiByAu3().screenCapture(fileName);
		} catch (IOException ie) {
			LoggerUtils.error("RemoteWebDriver截图保存异常！");
			new Win32GuiByAu3().screenCapture(fileName);
		}
	}

	/**
	 * take a screen shot and save the file.
	 * 
	 * @param fileName
	 *            the file path&name of the screenshot to be saved
	 * @param driver
	 *            the WebDriver instance.
	 * @throws WebDriverException IOException
	 */
	public void screenShot(String fileName, WebDriver driver) throws WebDriverException, IOException {
		RemoteWebDriver remotes = (RemoteWebDriver) new Augmenter().augment(driver);
		File file = ((TakesScreenshot) remotes).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(fileName));
	}

	/**
	 * Description: get the xpath string of webelements.
	 * 
	 * @param driver
	 *            webdriver instance.
	 * @param element
	 *            the webelement.
	 * @return the xpath string.
	 */
	public String getElementXpath(WebDriver driver, WebElement element) {
		String js = JScriptCollection.GET_ELEMENT_XPATH.getValue();
		return (String) ((JavascriptExecutor) driver).executeScript(js, element);
	}
}
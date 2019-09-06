package com.purang.yyt_uiautotest.core.webdriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.purang.yyt_uiautotest.core.support.ExecuteListener;
import com.purang.yyt_uiautotest.logging.webdriver.HTMLLogger;
import com.purang.yyt_uiautotest.tools.wingui.Win32GuiByVbs;
import com.purang.yyt_uiautotest.utils.LoggerUtils;

public class FirefoxDriverController implements DriverController {

	private WebDriver driver;
	private HTMLLogger logger;

	private final WebDriverSettings setter = new WebDriverSettings();
	private final Win32GuiByVbs vbs = new Win32GuiByVbs();
	private final String result = System.getProperty("user.dir") + "\\log\\";

	private ExecuteListener listener = new ExecuteListener(result);
	private int maxWaitfor = setter.MAX_WAIT;// 单步操作超时时间
	private int maxLoadTime = setter.MAX_LOAD_WAIT;// 页面加载超时时间
	private String executable;

	@Override
	public WebDriver getDriver() {
		return driver;
	}

	@Override
	public HTMLLogger getLogger() {
		return this.logger;
	}

	/**
	 * Description: set HTMLLogger.</BR>
	 * 内容描述：配置HTMLLogger。
	 * 
	 * @param className
	 *            the running test class name.
	 */
	private void setLogger(String className) {
		this.logger = new HTMLLogger(className, result, "GBK");
	}

	/**
	 * Description: start the selenium server.</BR>
	 * 内容描述：启动selenium/webdriver的代理服务。
	 *
	 * @param runClassName
	 *            the runtime class name
	 * @param startTime
	 *            the start time of test class running
	 * @throws Exception
	 */
	@Override
	public void startServer(String runClassName, Long startTime) throws Exception {
		cleanDriverProcess();

		setLogger(runClassName);
		logger.init(startTime);
		listener.setLogger(logger);
	}

	/**
	 * Description: stop the selenium server</BR>
	 * 内容描述：停止WebDriver的服务，无论什么模式。
	 *
	 * @throws Exception
	 */
	@Override
	public void stopServer() throws Exception {
		cleanDriverProcess();
	}

	/**
	 * Description: start webdirver</BR>
	 * 内容描述：启动WebDriver实例。
	 */
	@Override
	public void startWebDriver() {
		cleanBrowserProcess();
		firefoxEnviromentSetter();
		try {
			driver = new EventFiringWebDriver(new FirefoxDriver(getCapabilities())).register(listener);
			driver.manage().timeouts().pageLoadTimeout(maxLoadTime, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(maxWaitfor, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
			driver.get("about:blank");
			driver.manage().window().maximize();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Description: closeWebDriver, close current session opened by
	 * webdriver.</BR>
	 * 内容描述：关闭当前WebDriver创建的当前浏览器进程。
	 */
	@Override
	public void closeWebDriver() {
		if (driver != null) {
			driver.close();
		}
	}

	/**
	 * Description: quitWebDriver, close webdriver instance and clear all
	 * sessions.</BR>
	 * 内容描述：销毁WebDriver实例。
	 */
	@Override
	public void quitWebDriver() {
		if (driver != null) {
			driver.quit();
		}
	}

	/**
	 * Description: kill process mey be started by webdriver.
	 * 内容描述：杀掉可能由WebDriver启动的windows进程
	 */
	@Override
	public void cleanBrowserProcess() {
		vbs.killWin32Process("firefox");
		try {
			Thread.currentThread().join(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		vbs.killWin32Process("werfault");
	}

	/**
	 * Description: kill process mey be started by webdriver.
	 * 内容描述：杀掉可能由WebDriver启动的windows进程
	 */
	@Override
	public void cleanDriverProcess() {
		cleanBrowserProcess();
	}

	/**
	 * Description: set DesiredCapabilities platform.</BR>
	 * 内容描述：配置WebDriver运行平台配置信息。
	 *
	 * @throws Exception
	 */
	private DesiredCapabilities getCapabilities() throws Exception {
		DesiredCapabilities capability = DesiredCapabilities.firefox();

		capability.setCapability(CapabilityType.SUPPORTS_ALERTS, setter.SUPPORTS_ALERTS);
		capability.setCapability(CapabilityType.TAKES_SCREENSHOT, setter.TAKES_SCREENSHOT);
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, setter.ACCEPT_SSL_CERTS);
		capability.setCapability(CapabilityType.HAS_NATIVE_EVENTS, setter.HAS_NATIVE_EVENTS);
		capability.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, setter.SUPPORTS_JAVASCRIPT);
		capability.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, setter.ELEMENT_SCROLL_BEHAVIOR);
		capability.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, setter.SUPPORTS_FINDING_BY_CSS);
		capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, setter.UNEXPECTED_ALERT_BEHAVIOUR);

		capability.setCapability(FirefoxDriver.PROFILE, new ProfilesIni().getProfile("default"));
		capability.setCapability(FirefoxDriver.BINARY, new FirefoxBinary(new File(executable)));
		return capability;
	}

	/**
	 * Description: config the executable exe file of chromedriver.exe</BR>
	 * 内容描述：寻找可执行chrome安装位置，为ChromeDriver配置环境变量。
	 */
	private void firefoxEnviromentSetter() {
		final String EXEC = "\\Mozilla Firefox\\firefox.exe";
		if (new File(System.getenv("PROGRAMFILES") + EXEC).exists()) {
			executable = System.getenv("PROGRAMFILES") + EXEC;
		} else if (new File(System.getenv("PROGRAMFILES(X86)") + EXEC).exists()) {
			executable = System.getenv("PROGRAMFILES(X86)") + EXEC;
		} else if (new File(System.getenv("LOCALAPPDATA") + EXEC).exists()) {
			executable = System.getenv("LOCALAPPDATA") + EXEC;
		} else {
			throw new RuntimeException("Firefox was not installed correctly!");
		}
		System.setProperty("webdriver.firefox.bin", executable);
	}
}

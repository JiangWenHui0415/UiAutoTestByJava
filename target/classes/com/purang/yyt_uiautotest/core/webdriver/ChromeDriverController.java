package com.purang.yyt_uiautotest.core.webdriver;

/**
 * @author 测试仔刘毅
 */

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriverService.Builder;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.purang.yyt_uiautotest.core.support.ExecuteListener;
import com.purang.yyt_uiautotest.logging.webdriver.HTMLLogger;
import com.purang.yyt_uiautotest.tools.wingui.Win32GuiByVbs;
import com.purang.yyt_uiautotest.utils.LoggerUtils;

public class ChromeDriverController implements DriverController {

	private ChromeDriverService service;
	private WebDriver driver;
	private HTMLLogger logger;

	private final WebDriverSettings setter = new WebDriverSettings();
	private final Win32GuiByVbs vbs = new Win32GuiByVbs();
	private final String result = System.getProperty("user.dir") + "\\log\\";
	private final String executable = "./exec/chromedriver.exe";

	private ExecuteListener listener = new ExecuteListener(result);
	private int maxWaitfor = setter.MAX_WAIT;// 单步操作超时时间
	private int maxLoadTime = setter.MAX_LOAD_WAIT;// 页面加载超时时间

	@Override
	public HTMLLogger getLogger() {
		return this.logger;
	}

	@Override
	public WebDriver getDriver() {
		return this.driver;
	}

	/**
	 * Description: set HTMLLogger.</BR>
	 * 内容描述：配置HTMLLogger。
	 * @param className the running test class name.
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
		
		startDriverService(new File(result + runClassName + "_" + startTime + ".log"));
		System.out.println("==============webdriver server on " + System.getenv("USERNAME") + "@"
				+ System.getenv("COMPUTERNAME") + " successfully started at: " + service.getUrl().toString()
				+ "==============");
	}

	/**
	 * Description: stop the selenium server</BR>
	 * 内容描述：停止WebDriver的服务，无论什么模式。
	 *
	 * @throws Exception
	 */
	@Override
	public void stopServer() throws Exception {
		if (null != service) {
			service.stop();
		}
		cleanDriverProcess();
	}

	/**
	 * Description: start webdirver</BR>
	 * 内容描述：启动WebDriver实例。
	 */
	@Override
	public void startWebDriver() {
		cleanBrowserProcess();
		chromeDriverEnviromentSetter();
		try {
			driver = new EventFiringWebDriver(new ChromeDriver(service, getCapabilities())).register(listener);
			driver.manage().timeouts().pageLoadTimeout(maxLoadTime, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(maxWaitfor, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Description: closeWebDriver, close current session opened by webdriver.</BR>
	 * 内容描述：关闭当前WebDriver创建的当前浏览器进程。
	 */
	@Override
	public void closeWebDriver() {
		if (driver != null) {
			driver.close();
		}
	}

	/**
	 * Description: quitWebDriver, close webdriver instance and clear allsessions.</BR>
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
	public void cleanBrowserProcess(){
		vbs.killWin32Process("chrome");
		vbs.killWin32Process("werfault");		
	}
	
	/**
	 * Description: kill process mey be started by webdriver.
	 * 内容描述：杀掉可能由WebDriver启动的windows进程
	 */
	@Override
	public void cleanDriverProcess(){
		vbs.killWin32Process("chromedriver");
		vbs.killWin32Process("chrome");
		vbs.killWin32Process("werfault");		
	}

	/**
	 * Description: start iedirver service.</BR>
	 * 内容描述：启动IEDirverServer服务。
	 *
	 * @param log
	 *            the log file File.
	 * @throws Exception
	 */
	private void startDriverService(File log) throws Exception {
		Builder builder = new ChromeDriverService.Builder();
		if (setter.OUTPUT_ON) {
			service = builder.usingDriverExecutable(new File(executable)).usingAnyFreePort().withLogFile(log).build();
		} else {
			service = builder.usingDriverExecutable(new File(executable)).usingAnyFreePort().build();
		}
		service.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				service.stop();
			}
		});
	}

	/**
	 * Description: set DesiredCapabilities platform.</BR>
	 * 内容描述：配置WebDriver运行平台配置信息。
	 *
	 * @throws Exception
	 */
	private DesiredCapabilities getCapabilities() throws Exception {
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--lang=zh-CN","allow-outdated-plugins");

		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setCapability(ChromeOptions.CAPABILITY, option);

		capability.setCapability(CapabilityType.SUPPORTS_ALERTS, setter.SUPPORTS_ALERTS);
		capability.setCapability(CapabilityType.TAKES_SCREENSHOT, setter.TAKES_SCREENSHOT);
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, setter.ACCEPT_SSL_CERTS);
		capability.setCapability(CapabilityType.HAS_NATIVE_EVENTS, setter.HAS_NATIVE_EVENTS);
		capability.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, setter.SUPPORTS_JAVASCRIPT);
		capability.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, setter.ELEMENT_SCROLL_BEHAVIOR);
		capability.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, setter.SUPPORTS_FINDING_BY_CSS);
		capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, setter.UNEXPECTED_ALERT_BEHAVIOUR);
		return capability;
	}

	/**
	 * Description: config the executable exe file of chromedriver.exe</BR>	 *
	 * 内容描述：寻找可执行chrome安装位置，为ChromeDriver配置环境变量。
	 * @throws RuntimeException
	 */
	private void chromeDriverEnviromentSetter() {
		String _installed_p1 = System.getenv("LOCALAPPDATA");
		String _installed_p2 = System.getenv("PROGRAMFILES");
		String _installed_p3 = System.getenv("PROGRAMFILES(X86)");
		String CHROMEEXE = "\\Google\\Chrome\\Application\\chrome.exe";

		if (new File(_installed_p1 + CHROMEEXE).exists()) {
			System.setProperty("webdriver.chrome.bin", _installed_p1 + CHROMEEXE);
		} else if (new File(_installed_p2 + CHROMEEXE).exists()) {
			System.setProperty("webdriver.chrome.bin", _installed_p2 + CHROMEEXE);
		} else if (!System.getProperty("os.arch").equalsIgnoreCase("x86")
				&& new File(_installed_p3 + CHROMEEXE).exists()) {
			System.setProperty("webdriver.chrome.bin", _installed_p3 + CHROMEEXE);
		} else {
			throw new RuntimeException("Chrome was not installed correctly!");
		}
		if (new File(executable).exists()){
			System.setProperty("webdriver.chrome.driver", executable);			
		}else {
			throw new RuntimeException("the file chromedriver.exe was not placed correctly!");			
		}
	}
}

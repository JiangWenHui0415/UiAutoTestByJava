package com.purang.camp.uiautotest.core.webdriver;

/**
 * @author 测试仔刘毅
 */

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService.Builder;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.purang.camp.uiautotest.core.support.ExecuteListener;
import com.purang.camp.uiautotest.logging.webdriver.HtmlLogger;
import com.purang.camp.uiautotest.tools.wingui.Win32GuiByAu3;
import com.purang.camp.uiautotest.tools.wingui.Win32GuiByVbs;
import com.purang.camp.uiautotest.utils.LoggerUtil;

public class IEDriverController implements DriverController {

    private InternetExplorerDriverService service;
    private WebDriver driver;
    private HtmlLogger logger;

    private final WebDriverSettings setter = new WebDriverSettings();
    private final Win32GuiByVbs vbs = new Win32GuiByVbs();
    private final Win32GuiByAu3 AU3 = new Win32GuiByAu3();
    private final String result = System.getProperty("user.dir") + "\\log\\";

    private ExecuteListener listener = new ExecuteListener(result);
    private int maxWaitfor = setter.MAX_WAIT;// 单步操作超时时间
    private int maxLoadTime = setter.MAX_LOAD_WAIT;// 页面加载超时时间

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    @Override
    public HtmlLogger getLogger() {
        return this.logger;
    }

    /**
     * Description: set HTMLLogger.</BR>
     * 内容描述：配置HTMLLogger。
     * @param className the running test class name.
     */
    private void setLogger(String className) {
        this.logger = new HtmlLogger(className, result, "GBK");
    }

    /**
     * Description: start the selenium server.</BR>
     * 内容描述：启动selenium/webdriver的代理服务。
     *
     * @param runClassName
     *            the runtime class name
     *
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
        if (service != null) {
            service.stop();
        }
        cleanDriverProcess();
    }

    /**
     * Description: start webdirver</BR>
     * 内容描述：启动WebDriver实例。
     *
     * @throws RuntimeException
     */
    @Override
    public void startWebDriver() {
        cleanBrowserProcess();
        try {
            initWebDriver();
            driver.manage().timeouts().pageLoadTimeout(maxLoadTime, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(maxWaitfor, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Description: closeWebDriver, close current session opened bywebdriver.</BR>
     * 内容描述：关闭当前WebDriver创建的当前浏览器进程。
     */
    @Override
    public void closeWebDriver() {
        if (driver != null) {
            driver.close();
        }
    }

    /**
     * Description: quitWebDriver, close webdriver instance and clear all sessions.</BR>
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
        AU3.closeWindow("IEDriverServer.exe - 应用程序错误", 1);
        vbs.killWin32Process("iexplore");
        vbs.killWin32Process("werfault");
    }

    /**
     * Description: kill process mey be started by webdriver.
     * 内容描述：杀掉可能由WebDriver启动的windows进程
     */
    @Override
    public void cleanDriverProcess(){
        AU3.closeWindow("IEDriverServer.exe - 应用程序错误", 1);
        vbs.killWin32Process("IEDriverServer");
        vbs.killWin32Process("iexplore");
        vbs.killWin32Process("werfault");
    }

    /**
     * Description: init webdriver new instance.</BR>
     * 内容描述：初始化新的WebDriver实例。
     *
     * @throws Exception
     */
    private void initWebDriver() throws Exception {
        this.driver = new EventFiringWebDriver(new InternetExplorerDriver(service, getCapabilities()))
                .register(listener);
    }

    /**
     * Description: set DesiredCapabilities platform.</BR>
     * 内容描述：配置WebDriver运行平台配置信息。
     *
     * @throws Exception
     */
    private DesiredCapabilities getCapabilities() throws Exception {
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();

        capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, setter.IGNORE_ZOOM_SETTING);
        capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                setter.IGNORE_PROTECTED_MODE_SETTINGS);
        capability.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,
                setter.ENABLE_ELEMENT_CACHE_CLEANUP);
        capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, setter.ENABLE_PERSISTENT_HOVERING);
        capability.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, setter.REQUIRE_WINDOW_FOCUS);
        capability.setCapability(InternetExplorerDriver.BROWSER_ATTACH_TIMEOUT, setter.BROWSER_ATTACHT_IMEOUT);

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
     * Description: start iedirver service.</BR> 内容描述：启动IEDirverServer服务。
     *
     * @param log
     *            the log file File.
     * @throws Exception
     */
    private void startDriverService(File log) throws Exception {
        ieDriverEnvironmentSetter();
        Builder builder = new InternetExplorerDriverService.Builder();
        if (setter.OUTPUT_ON) {
            service = builder.usingAnyFreePort().withLogFile(log).withLogLevel(setter.LOG_LEVEL).build();
        } else {
            service = builder.usingAnyFreePort().withLogLevel(setter.LOG_LEVEL).build();
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
     * Description: config the executable exe file of IEDriverServer.exe</BR>
     * 内容描述：指定IEDriverServer.exe所在的位置，并且配置环境变量。
     *
     * @throws RuntimeException
     */
    private void ieDriverEnvironmentSetter() {
        String executable;
        if (System.getProperty("os.arch").equalsIgnoreCase("x86")) {
            executable = "./exec/IEDriverServer_X32.exe";
        } else {
            executable = "./exec/IEDriverServer_X64.exe";
        }
        if (new File(executable).exists()) {
            System.setProperty("webdriver.ie.driver", executable);
        } else {
            throw new RuntimeException("the file IEDriverServer.exe was not placed correctly!");
        }
    }
}


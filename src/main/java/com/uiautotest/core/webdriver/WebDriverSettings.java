package com.purang.camp.uiautotest.core.webdriver;

import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import com.purang.camp.uiautotest.utils.ReadConfiguration;

public class WebDriverSettings {
    public final static ReadConfiguration config = new ReadConfiguration("/com/purang/camp/uiautotest/core/webdriver/webdriver.properties");

    public int MAX_WAIT;// 单步操作超时时间

    public int MAX_LOAD_WAIT;// 页面加载超时时间

    public int SLEEP_UNIT;// 单次循环思考时间

    public Boolean OUTPUT_ON; // 是否打开server端文本日志

    public InternetExplorerDriverLogLevel LOG_LEVEL;// server端文本日志级别

    public boolean IGNORE_PROTECTED_MODE_SETTINGS; // 是否忽略IE保护模式的影响

    public boolean IGNORE_ZOOM_SETTING;// 是否忽略IE缩放比例的影响

    public boolean SUPPORTS_JAVASCRIPT;// 是否允许执行JS

    public boolean ENABLE_ELEMENT_CACHE_CLEANUP;// 是否清理对象缓存

    public boolean SUPPORTS_ALERTS;// 是否处理弹出对话框

    public String UNEXPECTED_ALERT_BEHAVIOUR;// 对异常（预期之外的）弹出框的处理方式

    public boolean SUPPORTS_FINDING_BY_CSS;// 是否支持CSS定位

    public boolean TAKES_SCREENSHOT;// 是否支持浏览器截图

    public boolean HAS_NATIVE_EVENTS;// 是否支持本地事件：运行时人工干预

    public boolean ENABLE_PERSISTENT_HOVERING;// 是否支持鼠标稳定的悬停事件

    public boolean REQUIRE_WINDOW_FOCUS;// 是否支持IE始终在任务栏激活且在桌面聚焦

    public boolean ACCEPT_SSL_CERTS;// 是否支持IE始终在任务栏激活且在桌面聚焦

    public boolean ELEMENT_SCROLL_BEHAVIOR;//

    public int BROWSER_ATTACHT_IMEOUT;// 浏览器通讯超时时间

    public WebDriverSettings() {
        this.MAX_WAIT = Integer.parseInt(config.get("STEP_TIMEOUT"));
        this.MAX_LOAD_WAIT = Integer.parseInt(config.get("PAGE_LOAD_TIMEOUT"));
        this.SLEEP_UNIT = Integer.parseInt(config.get("SLEEP_INTERVAL"));
        this.LOG_LEVEL = InternetExplorerDriverLogLevel.valueOf(config.get("LOG_LEVEL"));
        this.OUTPUT_ON = Boolean.parseBoolean(config.get("OUTPUT_ON"));

        // only for son of bitch : ie~
        this.IGNORE_ZOOM_SETTING = Boolean.parseBoolean(config.get("IGNORE_ZOOM_SETTING"));
        this.REQUIRE_WINDOW_FOCUS = Boolean.parseBoolean(config.get("REQUIRE_WINDOW_FOCUS"));
        this.BROWSER_ATTACHT_IMEOUT = Integer.parseInt(config.get("BROWSER_ATTACH_TIMEOUT"));
        this.ENABLE_ELEMENT_CACHE_CLEANUP = Boolean.parseBoolean(config.get("ENABLE_ELEMENT_CACHE_CLEANUP"));
        this.IGNORE_PROTECTED_MODE_SETTINGS = Boolean.parseBoolean(config.get("IGNORE_PROTECTED_MODE_SETTINGS"));

        // for all browsers
        this.SUPPORTS_ALERTS = Boolean.parseBoolean(config.get("SUPPORTS_ALERTS"));
        this.UNEXPECTED_ALERT_BEHAVIOUR = config.get("UNEXPECTED_ALERT_BEHAVIOUR");
        this.ACCEPT_SSL_CERTS = Boolean.parseBoolean(config.get("ACCEPT_SSL_CERTS"));
        this.TAKES_SCREENSHOT = Boolean.parseBoolean(config.get("TAKE_SSCREENSHOT"));
        this.HAS_NATIVE_EVENTS = Boolean.parseBoolean(config.get("HAS_NATIVE_EVENTS"));
        this.SUPPORTS_JAVASCRIPT = Boolean.parseBoolean(config.get("SUPPORTS_JAVASCRIPT"));
        this.ELEMENT_SCROLL_BEHAVIOR = Boolean.parseBoolean(config.get("ELEMENT_SCROLL_BEHAVIOR"));
        this.SUPPORTS_FINDING_BY_CSS = Boolean.parseBoolean(config.get("SUPPORTS_FINDING_BY_CSS"));
        this.ENABLE_PERSISTENT_HOVERING = Boolean.parseBoolean(config.get("ENABLE_PERSISTENT_HOVERING"));
    }
}

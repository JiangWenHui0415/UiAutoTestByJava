package com.purang.camp.uiautotest.core.frame;

import org.testng.annotations.*;
import com.purang.camp.uiautotest.core.webdriver.WebDriverWebPublic;
import com.purang.camp.uiautotest.utils.StringBufferUtil;

public class WebDriverBaseCase extends WebDriverWebPublic {

    private final String className = this.getClass().getName();
    private long starts = 0;

    /**
     * test initialize: start selenium-server, create log bufferwriter
     *
     * @throws RuntimeException
     **/

    @BeforeTest(alwaysRun = true, timeOut = 60000)
    protected void testSetup() {
        String begins = StringBufferUtil.formatedTime("yyyy-MM-dd HH-mm-ss-SSS");
        starts = System.currentTimeMillis();
        System.out.println("==============" + begins + "：案例【" + className + "】开始==============");
        testCunstruction(className);
        tableRefresh();
        startWebDriver();
        windowMaximize();
        setMaxWaitTime(30);
        get("https://testcamp.purang.com");
    }

    /**
     * test clear: stop selenium,close log bufferwriter, stop selenium-server.
     *
     * @throws RuntimeException
     **/
    @AfterTest(alwaysRun = true, timeOut = 60000)
    protected void tearDown() {
        testTermination();

        String ends = StringBufferUtil.formatedTime("yyyy-MM-dd HH-mm-ss-SSS");
        long stops = System.currentTimeMillis();
        System.out.println("开始===" + starts + "结束===" + stops);
        System.out.println("==============" + ends + "：案例【" + className + "】结束==============");
        System.out.println("==============本次运行消耗时间 " + (stops - starts) / 1000 + " 秒！==============");
    }
}

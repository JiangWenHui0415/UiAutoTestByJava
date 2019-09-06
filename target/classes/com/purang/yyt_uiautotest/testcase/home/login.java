package com.purang.yyt_uiautotest.testcase.home;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.junit.Assert.assertTrue;

public class login extends WebDriverBaseCase {
    @Test
    public void login() throws InterruptedException {       //异常检查机制
//        startWebDriver();
//        windowMaximize();
//        setMaxWaitTime(30);

        get("https://testcamp.purang.com/index.html");
        waitForElementVisible(By.id("login"), 5);
        click(By.id("login"));
        sendKeys(By.id("phoneNo2"), "15810766617");
        waitFor(2*1000);
        sendKeys(By.id("password2"), "111aaa");
        waitFor(2*1000);
        click(By.xpath("//*[@id='passwordLoginButton1']"));
//        assertTrue(elementExists(By.id("usernameMsg"), 2));
//        assertTrue(elementExists(By.id("passwordMsg"), 2));
        waitFor(2*1000);
        closeWebDriver();
    }


}

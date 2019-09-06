package com.purang.yyt_uiautotest.testcase.home;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class register extends WebDriverBaseCase {
    @Test
    public void register() throws InterruptedException {       //异常检查机制


        get("https://testcamp.purang.com/index.html");
        waitForElementVisible(By.id("register"), 5);
        click(By.id("register"));
        sendKeys(By.id("phoneNo3"), "15810766617");
        waitFor(2*1000);
        click(By.xpath("//*[@text='获取动态码']"));
        sendKeys(By.id("plainPassword"), "111aaa");
        waitFor(2*1000);
        sendKeys(By.id("repeatedPlainPassword"), "111aaa");

        click(By.xpath("//*[@id='registerButton']"));
//        assertTrue(elementExists(By.id("usernameMsg"), 2));
//        assertTrue(elementExists(By.id("passwordMsg"), 2));
        waitFor(2*1000);

    }


}

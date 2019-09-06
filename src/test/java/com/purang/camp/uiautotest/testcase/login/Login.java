package com.purang.camp.uiautotest.testcase.login;

import com.purang.camp.uiautotest.core.frame.WebDriverBaseCase;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import java.time.LocalDateTime;
import static org.junit.Assert.assertTrue;

public class Login extends WebDriverBaseCase {
    @Test
    public void login() throws InterruptedException {       //异常检查机制
        get("https://testcamp.purang.com/index.html");
        waitForElementVisible(By.id("login"), 5);
        click(By.id("login"));
        sendKeys(By.id("phoneNo2"), "13856012627");
        waitFor(2*1000);
        sendKeys(By.id("password2"), "123456");
        waitFor(2*1000);
        click(By.xpath("//*[@id='passwordLoginButton1']"));
//        assertTrue(elementExists(By.id("usernameMsg"), 2));
//        assertTrue(elementExists(By.id("passwordMsg"), 2));
        waitFor(2*1000);
//        closeWebDriver();
    }

}


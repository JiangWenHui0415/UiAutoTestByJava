package com.purang.yyt_uiautotest.testcase.finance;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;

public class Login extends WebDriverBaseCase {              //继承父类（WebDriverBaseCase）
	@Test
	public void login() throws InterruptedException {       //异常检查机制
		startWebDriver();
		windowMaximize();
		setMaxWaitTime(30);
		
		get("http://10.10.96.150:8011/index.htm");
		waitForElementVisible(By.xpath("//*[@href='/logon.htm']"), 5);
		click(By.xpath("//*[@href='/logon.htm']"));
		sendKeys(By.id("username"), "15810766617");
		waitFor(2*1000);
		sendKeys(By.id("password"), "111aaa");		
		waitFor(2*1000);
		click(By.xpath("//*[@id='loginBtn']"));
		assertTrue(elementExists(By.id("usernameMsg"), 2));
		assertTrue(elementExists(By.id("passwordMsg"), 2));
		waitFor(2*1000);		
		
	}

}

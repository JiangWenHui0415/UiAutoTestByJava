package com.purang.yyt_uiautotest.testcase.personalcenter;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;

public class Login_personal extends WebDriverBaseCase {


	private final String indexUrl = "indexUrl";

	@Test(priority = 1)
	public void Login() throws InterruptedException {

		startWebDriver();
		windowMaximize();
		setMaxWaitTime(30);


	}
	@Parameters({"indexUrl"})
	@Test(priority = 2)
	public void Login_personal(String indexUrl) {

		Register newuser = new Register();
		System.out.println(indexUrl);
		get(indexUrl);
		waitForElementVisible(By.xpath("//*[@href='/logon.htm']"), 5);
		click(By.xpath("//*[@href='/logon.htm']"));
		sendKeys(By.id("username"), newuser.Tel);
		sendKeys(By.id("password"), newuser.psw);
		waitFor(1 * 1000);
		click(By.xpath("//*[@id='loginBtn']"));
		waitFor(1 * 1000);

	}
	
	@Parameters({"indexUrl"})
	@Test(priority = 3)
	public void Logoff_personal(String indexUrl) {

		get(indexUrl+"/index.htm");
		click(By.xpath("//*[@href='/logout.htm']"));

	}
	
	
	@Parameters({"backUrl"})
	@Test(priority = 4)
	public void Login_admin(String backUrl) {

		get(backUrl+"/index.htm");
		sendKeys(By.id("username"), "admin");
		sendKeys(By.id("password"), "111aaa");
		waitFor(1 * 1000);
		click(By.xpath("//*[@id='loginBtn']"));
		waitFor(1 * 1000);

	}

	@Parameters({"backUrl"})
	@Test(priority = 5)
		public void Logoff_admin(String backUrl) {
			
			get(backUrl+"/index.htm");
			String url=driver.getCurrentUrl();
			if(url!=backUrl+"/login.htm") {
			click(By.xpath("//*[@onclick='logout();']"));
			}else{
				System.out.println("已退出");
			}
	}
	

}

package com.purang.yyt_uiautotest.testcase.information;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import com.purang.yyt_uiautotest.testcase.personalcenter.Login_personal;

public class Information extends WebDriverBaseCase {
	
	@Test(priority = 1)
	@Parameters({"backUrl"})
	public void newNotice(String backUrl) {
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(15);
		Login_personal ad = new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='资讯']"));
		click(By.xpath("//*[text()='资讯管理']"));
		
		driver.switchTo().frame("content");
		click(By.xpath("//button[text()='新增']"));
		waitFor(1000);
		sendKeys(By.name("title"), "测试资讯标题");
		
		String str = driver.findElement(By.xpath("//*[text()='系统功能']")).getAttribute("id");
		System.out.println(str);
		String str1 = str.replace("span", "check");
		System.out.println(str1);
		click(By.id("newsTypeName"));
		click(By.id(str1));
		System.out.println("选择成功！");
//		click(By.name("summary"));
		
		sendKeys(By.name("summary"), "测试资讯摘要");
		
		sendKeys(By.name("source"), "测试资讯来源");
		
//		click(By.name("publishDate"));
//		
//		click(By.xpath("//*[@class='day today active']"));
//		
//		click(By.xpath("//*[@class='hour active']"));
//		
//		click(By.xpath("//span[@class='minute active']"));
		String value="2018-04-24 10:09";
		jsExecutor("var elements=document.getElementsByName('publishDate');elements[0].value='"+value+"'", "setValue");
		driver=(WebDriver)driver;
		waitFor(3000);
		driver.findElement(By.id("file2")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		driver.switchTo().frame(0);
		sendKeys(By.xpath("//*[@class='ke-content']"), "测试资讯内容");
		driver.switchTo().parentFrame();
		click(By.xpath("//*[@onclick='publish(1);']"));
		click(By.xpath("//button[text()='确定']"));
		ad.Logoff_admin(backUrl);
	}

	// *[@id="form"]/div[10]/span[2]
	@Test(priority = 2)
	@Parameters({"backUrl"})
	public void editNotice(String backUrl) {
		Login_personal ad = new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='资讯']"));
		click(By.xpath("//*[text()='资讯管理']"));
		
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"), "测试资讯标题");
		click(By.id("searchbutton"));
		waitFor(1000);
		click(By.xpath("//*[text()='修改']"));
		waitFor(1000);
		
		driver.switchTo().frame(0);
		sendKeys(By.xpath("//*[@class='ke-content']"), "测试资讯内容修改");
		driver.switchTo().parentFrame();
		click(By.xpath("//*[@onclick='publish(1);']"));
		click(By.xpath("//button[text()='确定']"));
		ad.Logoff_admin(backUrl);
	}

	@Test(priority = 3)
	@Parameters({"backUrl"})
	public void publishNotice(String backUrl) {
		Login_personal ad = new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='资讯']"));
		click(By.xpath("//*[text()='资讯管理']"));
		
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"), "测试资讯标题");
		click(By.id("searchbutton"));
		waitFor(1000);
		click(By.xpath("//*[text()='发表']"));
		click(By.xpath("//button[text()='确定']"));
		try {
			ensrueBeforeAlert();
			ad.Logoff_admin(backUrl);
		} catch (UnhandledAlertException e) {
			System.out.println("弹窗未解决");
			driver.quit();
			startWebDriver();
			windowMaximize();
			setMaxWaitTime(30);

		}
	}

	@Test(priority = 4)
	@Parameters({"backUrl"})
	public void revokeNotice(String backUrl) {
		Login_personal ad = new Login_personal();
		ad.Login_admin(backUrl);
		                                                                                                                                                                               
		click(By.xpath("//*[text()='资讯']"));
		click(By.xpath("//*[text()='资讯管理']"));
		
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"), "测试资讯标题");
		click(By.id("searchbutton"));
		waitFor(1000);
		click(By.xpath("//*[text()='下线']"));
		click(By.xpath("//button[text()='确定']"));
		try {
			ensrueBeforeAlert();
			ad.Logoff_admin(backUrl);
		} catch (UnhandledAlertException e) {
			System.out.println("弹窗未解决");
			driver.quit();
			startWebDriver();
			windowMaximize();
			setMaxWaitTime(30);

		}
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));

	}

	@Test(priority = 5)
	@Parameters({"backUrl"})
	public void removeNotice(String backUrl) {
		Login_personal ad = new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='资讯']"));
		click(By.xpath("//*[text()='资讯管理']"));
//		click(By.xpath("//*[@href='/listNews.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"), "测试资讯标题");
		click(By.id("searchbutton"));
		waitFor(2000);
		click(By.xpath("//*[text()='删除']"));
		click(By.xpath("//button[text()='确定']"));
		try {
			ensrueBeforeAlert();
			ad.Logoff_admin(backUrl);
		} catch (UnhandledAlertException e) {
			System.out.println("弹窗未解决");
			driver.quit();
			startWebDriver();
			windowMaximize();
			setMaxWaitTime(30);

		}
		
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));
	}

	
	public void addNoticeType() {

	}

}

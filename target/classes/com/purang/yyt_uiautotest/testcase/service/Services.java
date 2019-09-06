package com.purang.yyt_uiautotest.testcase.service;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import com.purang.yyt_uiautotest.testcase.personalcenter.Login_personal;

public class Services extends WebDriverBaseCase {
	Login_personal ad = new Login_personal();

	// 新增服务类型
	@Test(priority = 1)
	@Parameters({"backUrl"})
	public void addServiceType(String backUrl) {
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(15);
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceType.htm']"));
		driver.switchTo().frame("content");
		click(By.xpath("//button[text()='新增']"));
		waitFor(1000);
		sendKeys(By.id("title"), "1测试分类名称");
		sendKeys(By.id("orderNum"), "2");
		driver.findElement(By.id("file2")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		click(By.xpath("//button[@onclick='saveServiceType();']"));
		click(By.xpath("//button[text()='确定']"));
		ad.Logoff_admin(backUrl);
		// driver.switchTo().defaultContent();
		// click(By.xpath("//*[@onclick='logout();']"));
	}

	// 编辑服务类型
	@Test(priority = 2)
	@Parameters({"backUrl"})
	public void editServiceType(String backUrl) {

		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceType.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("title"), "1测试分类名称");
		click(By.id("searchbutton"));
		waitFor(1000);
		click(By.xpath("//*[text()='修改']"));
		sendKeys(By.id("orderNum"), "1");
		click(By.xpath("//button[@onclick='saveServiceType();']"));
		click(By.xpath("//button[text()='确定']"));
		ad.Logoff_admin(backUrl);

	}

	// 移除服务类型
	@Test(priority = 10)
	@Parameters({"backUrl"})
	public void removeServiceType(String backUrl) {

		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceType.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("title"), "1测试分类名称");
		click(By.id("searchbutton"));
		waitFor(3000);
		click(By.xpath("//*[text()='删除']"));
		click(By.xpath("//button[text()='确定']"));
		ad.Logoff_admin(backUrl);

	}

	// 新增服务产品
	@Test(priority = 3)
	@Parameters({"backUrl"})
	public void addServiceProduct(String backUrl) {

		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceProduct.htm']"));
		driver.switchTo().frame("content");
		click(By.xpath("//button[text()='新增']"));
		sendKeys(By.id("name"), "测试产品名称");
		click(By.id("serviceTypeName"));

		String str = driver.findElement(By.xpath("//*[text()='1测试分类名称']")).getAttribute("id");
		String str1 = str.replace("span", "check");
		click(By.id(str1));

		selectByVisibleText(By.id("target"), "个人");
		driver.findElement(By.id("file2")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		waitFor(1000);
		driver.switchTo().frame(0);

		sendKeys(By.xpath("//body[@class='ke-content']"), "测试产品介绍");
		driver.switchTo().parentFrame();
		click(By.xpath("//button[@onclick='saveServiceProduct();']"));
		click(By.xpath("//button[text()='确定']"));
		ad.Logoff_admin(backUrl);
		System.out.println("产品新增成功！");
	}

	// 修改及提交
	@Test(priority = 4)
	@Parameters({"backUrl"})
	public void editServiceProduct(String backUrl) {

		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceProduct.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyWord"), "测试");
		click(By.id("searchbutton"));
		waitFor(1000);
		click(By.xpath("//button[@class='btn btn-xs btn-success']"));// 修改
		waitFor(1000);
		// driver.switchTo().frame(0);
		// sendKeys(By.xpath("//*[@class='ke-content']"), "测试产品介绍1");
		// driver.switchTo().parentFrame();
		click(By.xpath("//button[@onclick='submitServiceProduct();']"));
		click(By.xpath("//button[@class='ajs-button btn btn-primary']"));
		ad.Logoff_admin(backUrl);
		System.out.println("产品修改成功！");

	}

	@Test(priority = 6)
	@Parameters({"backUrl"})
	public void submitServiceProduct(String backUrl) {

		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceProduct.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyWord"), "测试");
		click(By.id("searchbutton"));
		waitFor(1000);

		click(By.xpath("//button[@class='btn btn-xs btn-info']")); // 提交
		waitFor(1000);
		click(By.xpath("//button[@class='ajs-button btn btn-primary']"));
		try {
			ensrueBeforeAlert();
			ad.Logoff_admin(backUrl);
		} catch (UnhandledAlertException e) {
			System.out.println("ok");
			driver.quit();
			startWebDriver();
			windowMaximize();
			setMaxWaitTime(30);

		}

	}

	// 撤销产品
	@Test(priority = 8)
	@Parameters({"backUrl"})
	public void ServiceProductRevoke(String backUrl) {

		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceProduct.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyWord"), "测试");
		click(By.id("searchbutton"));
		waitFor(1000);

		click(By.xpath("//button[@class='btn btn-xs btn-warning']")); // ("//button[text()='撤销']")
		click(By.xpath("//button[@class='ajs-button btn btn-primary']"));
		try {
			ensrueBeforeAlert();
			ad.Logoff_admin(backUrl);
		} catch (UnhandledAlertException e) {

			System.out.println("ok");
			driver.quit();
			startWebDriver();
			windowMaximize();
			setMaxWaitTime(30);

		}

	}

	// 删除
	@Test(priority = 9)
	@Parameters({"backUrl"})
	public void ServiceProductRemove(String backUrl) {

		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceProduct.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyWord"), "测试");
		click(By.id("searchbutton"));
		waitFor(1000);

		click(By.xpath("//button[text()='删除']"));
		click(By.xpath("//button[@class='ajs-button btn btn-primary']"));
		try {
			ensrueBeforeAlert();
			ad.Logoff_admin(backUrl);
		} catch (UnhandledAlertException e) {
			System.out.println("ok");
			driver.quit();
			startWebDriver();
			windowMaximize();
			setMaxWaitTime(30);

		}

	}

	@Test(priority = 7)
	@Parameters({"backUrl"})
	public void addServiceProductPass(String backUrl) {
		Login_personal ad = new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceProduct.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyWord"), "测试");
		click(By.id("searchbutton"));
		waitFor(1000);
		click(By.xpath("//button[text()='审核']"));// 审核btn btn-xs btn-info
		waitFor(1000);

		click(By.xpath("//button[starts-with(text(),'通')]"));
		click(By.xpath("//button[@class='ajs-button btn btn-primary']"));
		try {
			ensrueBeforeAlert();
			ad.Logoff_admin(backUrl);
		} catch (UnhandledAlertException e) {
			System.out.println("ok");
			driver.quit();
			startWebDriver();
			windowMaximize();
			setMaxWaitTime(30);

		}

	}

	@Test(priority = 5)
	@Parameters({"backUrl"})
	public void addServiceProductReject(String backUrl) {
		Login_personal ad = new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='服务']"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/serviceProduct.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyWord"), "测试");
		click(By.id("searchbutton"));
		waitFor(3000);
		

		click(By.xpath("//button[@class='btn btn-xs btn-info']"));// 审核btn btn-xs btn-info
	
		sendKeys(By.id("authorizedComments"), "不通过");

		click(By.xpath("//button[starts-with(text(),'不')]"));

		click(By.xpath("//button[@class='ajs-button btn btn-primary']"));
		try {
			ensrueBeforeAlert();
			ad.Logoff_admin(backUrl);
		} catch (UnhandledAlertException e) {
			System.out.println("ok");
			driver.quit();
			startWebDriver();
			windowMaximize();
			setMaxWaitTime(30);

		}

		System.out.println("Reject done...");
	}
}

package com.purang.yyt_uiautotest.testcase.market;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import com.purang.yyt_uiautotest.testcase.personalcenter.Login_personal;
import com.purang.yyt_uiautotest.testcase.personalcenter.Register;

public class MarketJob extends WebDriverBaseCase {
	public static String JobID;
	@Test(priority = 1)
	@Parameters({"indexUrl","backUrl"})
	public void marketJobReject(String indexUrl, String backUrl)throws AWTException {
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(5);
		Login_personal user = new Login_personal();
		user.Login_personal(indexUrl);
		
//		jobReject(backUrl);
		
	}
	
//	@Test(dependsOnMethods= {"marketJobReject"})
	@Parameters({"indexUrl","backUrl"})
	public void marketJobPass(String indexUrl, String backUrl){

		Login_personal user = new Login_personal();
		user.Login_personal(indexUrl);
		job();
//		jobPass(backUrl);
//		cancel(backUrl);
	}
	@Test(priority = 2)
	public void job() {
		click(By.linkText("集市"));
		click(By.xpath("//*[@class='market_job']"));
		sendKeys(By.id("name"), "招聘长期工");
		click(By.xpath("//span[@value='1']"));
		sendKeys(By.id("amount"), "20");
		sendKeys(By.id("price"), "30.2");
		sendKeys(By.id("jobPlace"), "测试市测试区测试路1024号");
		sendKeys(By.id("descri"), "腿脚好");
		click(By.xpath("//*[text()='提交']"));
		waitFor(3 * 1000);

	}
	@Test(priority = 3)
	public String getJobID() {
		String str = driver.findElement(By.xpath("//p[contains(text(),'编号招工信息报单')]")).getText();

		JobID = str.substring(2, 21);

		if (JobID != null) {
			System.out.println(JobID);
			click(By.linkText("退出"));
		} else {
			System.out.println("单号获取失败!");
		}
		return JobID;
	}
	@Parameters({"backUrl"})
	private void jobPass(String backUrl) {
		JobID=getJobID();
		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='集市']"));
		click(By.linkText("招工管理"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),JobID);
		click(By.xpath("//*[@class='btn btn-info searchbutton']"));
		waitFor(3*1000);
		click(By.xpath("//button[text()='审核']"));
		
		sendKeys(By.id("note"),"OK");
		click(By.xpath("//*[text()='通过发布']"));
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
//		waitFor(1000);
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));
	}
	@Parameters({"backUrl"})
	private void jobReject(String backUrl){
		JobID=getJobID();
		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='集市']"));
		click(By.linkText("招工管理"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),JobID);
		click(By.xpath("//*[@class='btn btn-info searchbutton']"));
		waitFor(3*1000);
		click(By.xpath("//button[text()='审核']"));
		
		sendKeys(By.id("note"),"OK");
		click(By.xpath("//*[text()='通过发布']"));
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
//		waitFor(1000);
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));
	}
	@Parameters({"backUrl"})
	private void cancel(String backUrl){
		waitFor(3*1000);
		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='集市']"));
		click(By.linkText("招工管理"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),JobID);
		click(By.xpath("//*[@class='btn btn-info searchbutton']"));
		waitFor(5*1000);
		click(By.xpath("//button[@class='btn btn-xs btn-warning']"));//下架
		
		sendKeys(By.id("cancelReason"),"过期");
		click(By.xpath("//*[text()='确定']"));
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
//		waitFor(1000);
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));
		
	}
}
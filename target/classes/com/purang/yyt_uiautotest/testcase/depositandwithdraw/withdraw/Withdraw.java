package com.purang.yyt_uiautotest.testcase.depositandwithdraw.withdraw;


import java.awt.AWTException;
import java.sql.SQLException;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import com.purang.yyt_uiautotest.testcase.personalcenter.Login_personal;

public class Withdraw  extends WebDriverBaseCase {
	public static String requestID;
	Login_personal ad =new Login_personal();
	
	@Parameters({"indexUrl","backUrl"})
	@Test(priority = 1)
	public void withdrawBankSubmitAndDone(String indexUrl,String backUrl) {
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(15);
		Login_personal withdrawUser=new Login_personal();
		withdrawUser.Login_personal(indexUrl);
		withdrawRequestBank();
		getRequestID();
		withdrawDone(backUrl);
		
	}
	@Parameters({"indexUrl","backUrl"})
	@Test(priority = 2)
	public void withdrawBankSubmitAndCancel(String indexUrl,String backUrl) {
		
		Login_personal withdrawUser=new Login_personal();
		withdrawUser.Login_personal(indexUrl);
		withdrawRequestBank();
		getRequestID();
		withdrawCancel(backUrl);
	}
	@Parameters({"indexUrl","backUrl"})
	@Test(priority = 3)
	public void withdrawHomeSubmitAndDone(String indexUrl,String backUrl) {
		
		Login_personal withdrawUser=new Login_personal();
		withdrawUser.Login_personal(indexUrl);
		withdrawRequestHome();
		getRequestID();
		withdrawDone(backUrl);
	}
	@Parameters({"indexUrl","backUrl"})
	@Test(priority = 4)
	public void withdrawHomeSubmitAndCancel(String indexUrl,String backUrl) {
	
		Login_personal withdrawUser=new Login_personal();
		withdrawUser.Login_personal(indexUrl);
		withdrawRequestHome();
		getRequestID();
		withdrawCancel(backUrl);
	}
	
	
	public void withdrawRequestBank() {
		click(By.xpath("//*[@href='/depoindex.htm']"));	
		click(By.xpath("//*[@href='/drawappoint.htm?mode=2']"));
		driver.findElement(By.xpath("//ul[@class='tab-ul']/li[3]")).click();
		click(By.id("appDateOptions"));
		click(By.xpath("//*[@id='appDateOptions']/option[8]"));
		click(By.id("appTimeOptions"));
		click(By.xpath("//*[@id='appTimeOptions']/option[8]"));
		sendKeys(By.id("appointAccount"),"5.35");
		//selectByVisibleText(By.id("showQukuanTerm"), "定期");
		selectByVisibleText(By.id("websiteoptions"), "西塘支行");
		click(By.xpath("//*[@onclick='applyDepoOrder()']"));	
		}
	
	public void withdrawRequestHome() {
		click(By.xpath("//*[@href='/depoindex.htm']"));	
		click(By.xpath("//*[@href='/drawappoint.htm?mode=2']"));
		driver.findElement(By.xpath("//ul[@class='tab-ul']/li[2]")).click();
		click(By.xpath("//*[@onclick='switchMode(1)']"));
		click(By.id("appDateOptions"));
		click(By.xpath("//*[@id='appDateOptions']/option[8]"));
		click(By.id("appTimeOptions"));
		click(By.xpath("//*[@id='appTimeOptions']/option[8]"));
		sendKeys(By.id("appointAccount"),"6.25");
		//selectByVisibleText(By.id("showQukuanTerm"), "定期");
		//selectByVisibleText(By.id("showCurrencyType"), "港币 ");
		selectByVisibleText(By.id("websiteoptions"), "西塘支行");
		click(By.xpath("//*[@onclick='applyDepoOrder()']"));	
		}
	
	
	
	public String getRequestID() { 
		click(By.xpath("//*[text()='查看我的申请']"));
		driver.findElement(By.xpath("//ul[@class='options']/li[3]"));
		click(By.xpath("//span[text()='待处理']"));
		String str=driver.findElement(By.xpath("//div[@class='info']/div[1]")).getText();
		requestID=str.substring(str.length()-20);
		click(By.linkText("退出"));
		return requestID;
	}
	
	@Parameters({"backUrl"})
	public void withdrawDone(String backUrl) {
	
		
		ad.Login_admin(backUrl);
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("取出"));
//		click(By.xpath("//*[@href='/depositProductOrder.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"),requestID);
		click(By.id("btn-search"));
		waitFor(3000);
		click(By.xpath("//*[text()='完成']"));
		click(By.id("orderHeRen"));
		waitFor(1000);
		ad.Logoff_admin(backUrl);
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));
	}
	
	//存取款取消
	@Parameters({"backUrl"})
	public void withdrawCancel(String backUrl) { 
		
		ad.Login_admin(backUrl);
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("取出"));
		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"),requestID);
		click(By.id("btn-search"));
		waitFor(3*1000);
		click(By.xpath("//*[text()='取消']"));
		click(By.xpath("//*[@onclick='cancel();']"));
		ad.Logoff_admin(backUrl);
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));
	}	
	
}

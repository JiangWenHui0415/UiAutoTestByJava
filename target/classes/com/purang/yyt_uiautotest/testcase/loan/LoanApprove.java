package com.purang.yyt_uiautotest.testcase.loan;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import com.purang.yyt_uiautotest.testcase.personalcenter.Login_personal;
import com.purang.yyt_uiautotest.testcase.personalcenter.Register;

public class LoanApprove extends WebDriverBaseCase{
	@Test
	@Parameters({"indexUrl","backUrl"})
	public void approve(String indexUrl,String backUrl){
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(5);
		MgrApprove(indexUrl,backUrl);
		
	}
	@Parameters({"indexUrl"})
	public String getloanOrder(String indexUrl) {
		Login_personal user =new Login_personal();
		user.Login_personal(indexUrl);

		click(By.xpath("//*[@href='/usercenter.htm']"));
		click(By.id("mybusinessparent"));
		click(By.xpath("//*[@href='/userloanorder.htm']"));
		
		String str=driver.findElement(By.xpath("//*[text()='信用授信']")).getAttribute("href");
		String OderId = str.substring(str.length()-19);
		
		return OderId;
	}
	
	@Parameters({"indexUrl","backUrl"})
	public void MgrApprove(String indexUrl,String backUrl) {
		
		String OderId=getloanOrder(indexUrl);
		System.out.println(OderId);
		get(backUrl);
		sendKeys(By.id("username"),"333322");
		sendKeys(By.id("password"),"111aaa");
		click(By.id("loginBtn"));
		
		driver.switchTo().frame("content");
		click(By.xpath("//button[text()='指派' and contains(@onclick,'"+OderId+"')]"));
		click(By.xpath("//*[@class='btn btn-success']"));
		
		click(By.xpath("//button[text()='审核' and contains(@onclick,'"+OderId+"')]"));
		waitFor(2 * 1000);
		click(By.id("pass_button_-20"));
		click(By.id("button_-20"));
		
	}
	
	
	@Parameters({"indexUrl","backUrl"})
	public void GovernorApprove(String indexUrl,String backUrl) {
		
		String OderId=getloanOrder(indexUrl);
		System.out.println(OderId);
		get(backUrl);
		sendKeys(By.id("username"),"333322");
		sendKeys(By.id("password"),"111aaa");
		click(By.id("loginBtn"));
		
		driver.switchTo().frame("content");
	
		click(By.xpath("//button[text()='审核' and contains(@onclick,'"+OderId+"')]"));
		waitFor(2 * 1000);
		click(By.id("pass_button_-20"));
		click(By.id("button_-20"));
		
		driver.switchTo().defaultContent();
		click(By.xpath("//*[@onclick='logout();']"));
		
	}
	

}

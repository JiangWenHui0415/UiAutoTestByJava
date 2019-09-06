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

public class MarketBuy extends WebDriverBaseCase {
	public static String BuyID;
	@Test
	@Parameters({"indexUrl","backUrl"})
	public void marketBuyReject(String indexUrl,String backUrl){
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(5);
		Login_personal user = new Login_personal();
		user.Login_personal(indexUrl);
		buy();
		BuyReject(backUrl);
		
	}
	@Parameters({"indexUrl","backUrl"})
	@Test(dependsOnMethods= {"marketBuyReject"})
	public void marketBuyPass(String indexUrl,String backUrl)throws AWTException  {
		Login_personal user = new Login_personal();
		user.Login_personal(indexUrl);
		buy();
		BuyPass(backUrl);
		cancel(backUrl);
	}

	public void buy() {
		click(By.linkText("集市"));
		click(By.xpath("//*[@class='market_buy']"));
		sendKeys(By.id("title"), "我要买的东西");
		click(By.xpath("//span[@value='5']"));
		sendKeys(By.id("price"), "16.5");
		driver.findElement(By.xpath("//*[@id='unitOption']/option[4]")).click();
		sendKeys(By.id("amount"), "20");

		sendKeys(By.id("regProvinceName"), "河北");
		sendKeys(By.id("regCityName"), "石家庄");
		sendKeys(By.id("regCountryName"), "平山县");
		sendKeys(By.id("descri"), "社会社会");
		click(By.xpath("//*[text()='提交']"));
		waitFor(3 * 1000);
		
		
	}
	
	
	public String getBuyID() {
		String str = driver.findElement(By.xpath("//p[contains(text(),'编号商品购买报单')]")).getText();
		
		BuyID = str.substring(2, 16);
		
		if (BuyID != null) {
			System.out.println(BuyID);
			click(By.linkText("退出"));
		} else {
			System.out.println("单号获取失败!");
		}
		return BuyID;
		
		
	}
	
	@Parameters({"backUrl"})
	private void BuyPass(String backUrl){
		BuyID=getBuyID();
		waitFor(1*1000);
		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='集市']"));
		waitFor(1*1000);
		click(By.xpath("//*[text()='产品管理']"));
		waitFor(1*1000);
		click(By.xpath("//*[@id=\"marketProduct\"]/div[1]/a[2]"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),BuyID);
		click(By.xpath("//*[@class='btn btn-info searchbutton']"));
		waitFor(2*1000);
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
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));
		
	}
	@Parameters({"backUrl"})
	private void BuyReject(String backUrl){
		BuyID=getBuyID();
		waitFor(1*1000);

		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		waitFor(1000);
		click(By.xpath("//*[text()='集市']"));
		waitFor(1*1000);
		click(By.xpath("//*[text()='产品管理']"));
		waitFor(1*1000);
		click(By.xpath("//*[@id=\"marketProduct\"]/div[1]/a[2]"));
		waitFor(1*1000);
		
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),BuyID);
		click(By.xpath("//*[@class='btn btn-info searchbutton']"));
		waitFor(2*1000);
		click(By.xpath("//button[@class='btn btn-xs btn-info']"));//审核
		
		sendKeys(By.id("note"),"No");
		click(By.xpath("//*[text()='不通过']"));
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
	@Parameters({"backUrl"})
	private void cancel(String backUrl ) {
		waitFor(1*1000);
		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='集市']"));
		waitFor(1*1000);
		click(By.xpath("//*[text()='产品管理']"));
		waitFor(1*1000);
		click(By.xpath("//*[@id=\"marketProduct\"]/div[1]/a[2]"));
//		click(By.xpath("//*[@url='marketProduct.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),BuyID);
		click(By.xpath("//*[@class='btn btn-info searchbutton']"));
		waitFor(3*1000);
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
//		driver.switchTo().defaultContent();
//		click(By.xpath("//*[@onclick='logout();']"));
		
	}
	
	
	
	
	
	
}
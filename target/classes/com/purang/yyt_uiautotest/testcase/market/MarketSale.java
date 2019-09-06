package com.purang.yyt_uiautotest.testcase.market;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import com.purang.yyt_uiautotest.testcase.personalcenter.Login_personal;
import com.purang.yyt_uiautotest.testcase.personalcenter.Register;

public class MarketSale extends WebDriverBaseCase{
	public static String SaleID;
	@Test
	@Parameters({"indexUrl","backUrl"})
	public void marketSallReject(String indexUrl, String backUrl){
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(5);
		Login_personal user = new Login_personal();
		user.Login_personal(indexUrl);
		sale();
		SaleReject(backUrl);
		
	}
	
	@Test(dependsOnMethods= {"marketSallReject"})
	@Parameters({"indexUrl","backUrl"})
	public void marketSallPass(String indexUrl, String backUrl){

		Login_personal user = new Login_personal();
		user.Login_personal(indexUrl);
		sale();
		SalePass(backUrl);
		cancel(backUrl) ;
	}
	
	public void sale() {
		click(By.linkText("集市"));
		click(By.xpath("//*[@class='market_sale']"));
		sendKeys(By.id("title"), "西安特产");
		click(By.xpath("//span[@value='3']"));
		
		sendKeys(By.id("price"), "19.2");
		driver.findElement(By.xpath("//*[@id='unitOption']/option[2]")).click();
		sendKeys(By.id("amount"), "1000");
		sendKeys(By.id("regProvinceName"), "安徽");
		sendKeys(By.id("regCityName"), "合肥");
		sendKeys(By.id("regCountryName"), "长丰县");
		
		sendKeys(By.id("descri"), "物品好，无瑕疵");
		driver.findElement(By.id("file2")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		driver.findElement(By.id("file3")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		click(By.xpath("//*[text()='提交']"));
		waitFor(3 * 1000);
		
	}
	
	public String getSaleID() {
		String str = driver.findElement(By.xpath("//p[contains(text(),'编号商品出售报单')]")).getText();
		
		SaleID = str.substring(2, 16);
		
		if (SaleID != null) {
			System.out.println(SaleID);
			click(By.linkText("退出"));
		} else {
			System.out.println("单号获取失败!");
		}
		return SaleID;
		
		
	}
	
	@Parameters({"backUrl"})
	private void SalePass(String backUrl) {
		SaleID=getSaleID();
		waitFor(3*1000);
		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='集市']"));
		click(By.xpath("//*[text()='产品管理']"));
//		click(By.xpath("//a[text()='出售']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),SaleID);
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
	private void SaleReject(String backUrl){
		SaleID=getSaleID();
		waitFor(3*1000);

		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='集市']"));
		click(By.xpath("//*[text()='产品管理']"));
//		click(By.xpath("//*[@id='marketOrder']/div[1]/a[1]"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),SaleID);
		click(By.xpath("//*[@class='btn btn-info searchbutton']"));
		waitFor(1000);
		click(By.xpath("//button[text()='审核']"));
		
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
	private void cancel(String backUrl) {
		waitFor(3*1000);
		Login_personal ad=new Login_personal();
		ad.Login_admin(backUrl);
		click(By.xpath("//*[text()='集市']"));
		click(By.xpath("//*[text()='产品管理']"));
//		click(By.xpath("//a[text()='出售']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("keyword"),SaleID);
		click(By.xpath("//*[@class='btn btn-info searchbutton']"));
		waitFor(1000);
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

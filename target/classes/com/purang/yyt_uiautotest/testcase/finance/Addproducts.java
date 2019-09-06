package com.purang.yyt_uiautotest.testcase.finance;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.gargoylesoftware.htmlunit.OnbeforeunloadHandler;
import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;

public class Addproducts extends WebDriverBaseCase {                          // 继承父类（WebDriverBaseCase）
	@Test
	public void login() throws InterruptedException, AWTException {           // 异常检查机制
		startWebDriver(); // 窗口最大化
		windowMaximize(); // 窗口最大化
		setMaxWaitTime(30); // 窗口最大化等待时间（30秒）
		get("http://10.10.96.150:8080/login.htm"); // 请求IP地址
		// waitForElementVisible(By.xpath("//*[@href='/logon.htm']"), 5);     //老方法查找元素
		sendKeys(By.id("username"), "admin");                                 // 调用继承的父类中的方法，输入用户名
		sendKeys(By.id("password"), "111aaa");
		click(By.xpath("//*[@id='loginBtn']"));                               // 定位查找元素并点击
		click(By.linkText("理财"));
		click(By.linkText("产品管理"));
		click(By.linkText("理财产品"));
		selectFrameWithTimeout("content", 3); // 进入嵌套网页
		click(By.xpath("//*[text()='新增']"));
		waitFor(1 * 1000);
		sendKeys(By.id("name"), new Random().nextInt(10000) + "产品");        // 输入产品名称：随机输入10000以内的数字+产品两个字
		sendKeys(By.id("issueOrg"), new Random().nextInt(10000) + "机构");
		selectByIndex(By.id("riskType"), new Random().nextInt(3));            // 点击下拉框，并随机选择排序3以内的内容（0、1、2）
		sendKeys(By.id("amount"), new Random().nextInt(10000) + 1 + "");
		sendKeys(By.id("rate"), new Random().nextInt(100) + 1 + "");
		sendKeys(By.id("limitDay"),"10");

		WebElement sellStartDate = driver.findElement(By.id("sellStartDate")); // 查找页面元素为sellStartDate的元素，接下
		WebElement sellEndDate = driver.findElement(By.id("sellEndDate"));
        WebElement overValueDate=driver.findElement(By.id("overValueDate"));
        
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].value=\""+LocalDate.now()+"\";arguments[1].value=\""+LocalDate.now().plusDays(2)+"\"", sellStartDate, sellEndDate);
		// 先将WebDriver方法改为JavascriptExecutor方法，再直接赋予sellStartDate值

		// ((JavascriptExecutor)driver).executeScript("arguments[0].value=\"2018-01-17\"",
		// sellEndDate); //写成一句或写成两句都行

		driver = (WebDriver) driver;                                           // 再将JavascriptExecutor方法改回WebDriver方法

		sendKeys(By.id("startAmount"), new Random().nextInt(10000) + 1 + "");

		WebElement valueDate = driver.findElement(By.id("valueDate"));
		((JavascriptExecutor) driver).executeScript("arguments[0].value=\""+LocalDate.now().plusDays(3)+"\"", valueDate);
		((JavascriptExecutor) driver).executeScript("arguments[0].value=\""+LocalDate.now().plusDays(13)+"\"", overValueDate);
		
		
//		click(By.xpath("//*[@class='glyphicon glyphicon-calendar']"));          //点击日历
//		click(By.xpath("//*[text()='29']"));                                    //选择日期
		driver = (WebDriver) driver;
		WebElement payDate = driver.findElement(By.id("payDate"));
		((JavascriptExecutor) driver).executeScript("arguments[0].value=\""+LocalDate.now().plusDays(14)+"\"", payDate);    //自动赋值日期，日期为当前日期+14天
		driver = (WebDriver) driver;

		sendKeys(By.id("payMethod"), "暴力收款");
		sendKeys(By.id("payMethodTips"), "喷漆、泼粪、卸腿、骚扰、非法拘禁");
		waitFor(1 * 1000);
		Robot robot = new Robot();                          //new一个robot
		robot.keyPress(KeyEvent.VK_TAB);                    //键盘自动输入tab键
		robot.keyPress(KeyEvent.VK_A);                      //键盘自动输入字母a

		String js = "var q=document.documentElement.scrollTop=10000";      //
		((JavascriptExecutor) driver).executeScript(js);                   //
		driver = (WebDriver) driver;                                        //以上三句是页面下拉到最底语句

//		 WebElement element =
//		driver.findElement(By.xpath("//*[@onclick='submitFinaProduct()']"));
//		JavascriptExecutor js = (JavascriptExecutor) driver;		
//		js.executeScript("arguments[0].scrollIntoView(true)", element);
//		element.click();
				
		click(By.xpath("//*[@onclick='submitFinaProduct();']"));
//或		click(By.xpath("//*[contains(text(),'提')]"));

		click(By.xpath("//*[@class='ajs-button btn btn-primary']"));
		waitFor(1 * 1000);
		click(By.xpath("//*[@class='btn btn-xs btn-info']"));
		waitFor(1 * 1000);
		String xl = "var q=document.documentElement.scrollTop=10000";
		((JavascriptExecutor) driver).executeScript(xl);
		driver = (WebDriver) driver;
//		click(By.xpath("//*[text()='通  过']"));
		click(By.xpath("//*[contains(text(),'通')]"));
		click(By.xpath("//*[contains(text(),'确定')]"));
		waitFor(3 * 1000);

		}
	
	

}

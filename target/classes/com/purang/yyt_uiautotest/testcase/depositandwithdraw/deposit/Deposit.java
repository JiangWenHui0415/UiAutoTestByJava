package com.purang.yyt_uiautotest.testcase.depositandwithdraw.deposit;

import java.awt.AWTException;
import java.sql.SQLException;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import com.purang.yyt_uiautotest.testcase.personalcenter.Login_personal;

public class Deposit extends WebDriverBaseCase {
	public static String requestID1;
	public static String requestID2;
	public static String requestID3;
	public static String requestID4;
	Login_personal ad = new Login_personal();

	@Parameters({ "indexUrl", "backUrl" })
	@Test(priority = 1)
	public void depositBankSubmitAndDone(String indexUrl, String backUrl) {
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(15);
		Login_personal depositUser = new Login_personal();
		depositUser.Login_personal(indexUrl);

	}

	@Test(priority = 2)
	public void depositRequestBank() {
		click(By.xpath("//*[@href='/depoindex.htm']"));

		click(By.xpath(
				"//*[@href='/depoproductappoint.htm?mode=1&id=6358314183938801763&&termId=6462483869764419683']"));
		click(By.id("appDateOptions"));
		click(By.xpath("//*[@id='appDateOptions']/option[8]"));
		click(By.id("appTimeOptions"));
		click(By.xpath("//*[@id='appTimeOptions']/option[8]"));
		sendKeys(By.id("appointAccount"), "2.35");
		selectByVisibleText(By.id("websiteoptions"), "西塘支行");
		click(By.xpath("//*[@onclick='applyDepoOrder()']"));
		requestID1=getRequestID1();
		// 申请已成功提交！
	}

	// 获取存取款申请ID
//	@Test(priority = 3)
	public String getRequestID1() {
		click(By.xpath("//*[text()='查看我的申请']"));

		click(By.xpath("//div[@class='hkdeadline']/span[text()='2.35']"));
		String str = driver.findElement(By.xpath("//div[@class='info']/div[1]")).getText();
		requestID1 = str.substring(str.length() - 20);
		System.out.println("requestID1:" + requestID1);
		click(By.linkText("退出"));
		return requestID1;
	}

	// 存取款完成
	@Test(priority = 3)
	@Parameters({ "backUrl" })
	public void depositAssi(String backUrl) {
		
		ad.Login_admin(backUrl);
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("存入"));
		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"), requestID1);
		click(By.id("btn-search"));
		waitFor(1 * 1000);
		click(By.xpath("//button[@class='btn btn-xs btn-danger']"));
		click(By.xpath("//*[@class='ajs-button btn btn-primary']"));

	}

	// 存取款完成
	@Test(priority = 4)
	@Parameters({ "backUrl" })
	public void depositDone(String backUrl) {
		ad.Login_admin(backUrl);
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("存入"));

		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"), requestID1);
		click(By.id("btn-search"));
		waitFor(1 * 1000);
		click(By.xpath("//button[@class='btn btn-xs btn-danger']"));
		click(By.id("orderHeRen"));
		ad.Logoff_admin(backUrl);

	}

	@Test(priority = 5)
	@Parameters({ "indexUrl", "backUrl" })
	public void depositBankSubmitAndCancel(String indexUrl, String backUrl) {

		Login_personal depositUser = new Login_personal();
		depositUser.Login_personal(indexUrl);

	}

	// 柜台存款
	@Test(priority = 6)
	public void depositRequestBank2() {
		click(By.xpath("//*[@href='/depoindex.htm']"));

		click(By.xpath(
				"//*[@href='/depoproductappoint.htm?mode=1&id=6358314183938801763&&termId=6462483869764419683']"));
		click(By.id("appDateOptions"));
		click(By.xpath("//*[@id='appDateOptions']/option[8]"));
		click(By.id("appTimeOptions"));
		click(By.xpath("//*[@id='appTimeOptions']/option[8]"));
		sendKeys(By.id("appointAccount"), "3.35");
		selectByVisibleText(By.id("websiteoptions"), "西塘支行");
		click(By.xpath("//*[@onclick='applyDepoOrder()']"));

		// 申请已成功提交！

	}

//	@Test(priority = 8)
	public String getRequestID2() {
		click(By.xpath("//*[text()='查看我的申请']"));

		click(By.xpath("//div[@class='hkdeadline']/span[text()='3.35']"));
		String str = driver.findElement(By.xpath("//div[@class='info']/div[1]")).getText();
		requestID2 = str.substring(str.length() - 20);
		System.out.println("requestID2:" + requestID2);
		click(By.linkText("退出"));
		return requestID2;
	}

	// 存取款取消
	@Test(priority = 7)
	@Parameters({ "backUrl" })
	public void depositCancel(String backUrl) {
		requestID2=getRequestID2();
		depositAssi1(backUrl);
		ad.Login_admin(backUrl);
		
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("存入"));

		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"), requestID2);
		click(By.id("btn-search"));
		waitFor(1 * 1000);
		click(By.xpath("//button[@class='btn btn-xs btn-warning']"));

		click(By.xpath("//*[@onclick='cancel();']"));
		ad.Logoff_admin(backUrl);

	}
	
	
	@Parameters({ "backUrl" })
	public void depositAssi1(String backUrl) {
		
		ad.Login_admin(backUrl);
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("存入"));
		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"), requestID2);
		click(By.id("btn-search"));
		waitFor(1 * 1000);
		click(By.xpath("//button[@class='btn btn-xs btn-danger']"));
		click(By.xpath("//*[@class='ajs-button btn btn-primary']"));

	}

	@Parameters({ "indexUrl", "backUrl" })
	@Test(priority = 8)
	public void depositHomeSubmitAndDone(String indexUrl, String backUrl) {

		Login_personal depositUser = new Login_personal();
		depositUser.Login_personal(indexUrl);

		depositDone2(backUrl);
	}

	// 上门存款
	@Test(priority = 9)
	public void depositRequestHome() {
		click(By.xpath("//*[@href='/depoindex.htm']"));
		click(By.xpath(
				"//*[@href='/depoproductappoint.htm?mode=1&id=6358314183938801763&&termId=6462483869764419683']"));
		click(By.xpath("//*[@onclick='switchMode(1)']"));
		click(By.id("appDateOptions"));
		click(By.xpath("//*[@id='appDateOptions']/option[8]"));
		click(By.id("appTimeOptions"));
		click(By.xpath("//*[@id='appTimeOptions']/option[8]"));
		sendKeys(By.id("appointAccount"), "4.35");
		selectByVisibleText(By.id("websiteoptions"), "西塘支行");
		click(By.xpath("//*[@onclick='applyDepoOrder()']"));
		requestID3=getRequestID3();
		// 申请已成功提交！
	}

//	@Test(priority = 12)
	public String getRequestID3() {
		click(By.xpath("//*[text()='查看我的申请']"));
		// driver.findElement(By.xpath("//ul[@class='options']/li[3]"));
		// click(By.xpath("//*[@onclick='statusSearch('0,1')']"));
		// click(By.xpath("/html/body/div[2]/div/div/div[3]/div[1]/div[2]/div[2]/div[1]/span"));
		click(By.xpath("//div[@class='hkdeadline']/span[text()='4.35']"));
		String str = driver.findElement(By.xpath("//div[@class='info']/div[1]")).getText();

		requestID3 = str.substring(str.length() - 20);
		System.out.println("requestID3:" + requestID3);
		click(By.linkText("退出"));
		return requestID3;
	}

	// 存取款完成
	@Test(priority = 10)
	@Parameters({ "backUrl" })
	public void depositDone2(String backUrl) {
		
		ad.Login_admin(backUrl);
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("存入"));
		// click(By.xpath("//*[@href='/depositProductOrder.htm']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"), requestID3);
		click(By.id("btn-search"));
		waitFor(1 * 1000);
		click(By.xpath("//button[@class='btn btn-xs btn-danger']")); // 指派
		click(By.xpath("//*[@class='ajs-button btn btn-primary']"));
		driver.switchTo().defaultContent();
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("存入"));
		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"), requestID3);
		click(By.id("btn-search"));
		waitFor(1 * 1000);
		click(By.xpath("//button[@class='btn btn-xs btn-danger']"));
		click(By.id("orderHeRen"));
		ad.Logoff_admin(backUrl);
		// driver.switchTo().defaultContent();
		// click(By.xpath("//*[@onclick='logout();']"));
	}

	@Parameters({ "indexUrl", "backUrl" })
	@Test(priority = 14)
	public void depositHomeSubmitAndCancel(String indexUrl, String backUrl) {

		Login_personal depositUser = new Login_personal();
		depositUser.Login_personal(indexUrl);

	}

	@Test(priority = 15)
	public void depositRequestHome2() {
		click(By.xpath("//*[@href='/depoindex.htm']"));
		click(By.xpath(
				"//*[@href='/depoproductappoint.htm?mode=1&id=6358314183938801763&&termId=6462483869764419683']"));
		click(By.xpath("//*[@onclick='switchMode(1)']"));
		click(By.id("appDateOptions"));
		click(By.xpath("//*[@id='appDateOptions']/option[8]"));
		click(By.id("appTimeOptions"));
		click(By.xpath("//*[@id='appTimeOptions']/option[8]"));
		sendKeys(By.id("appointAccount"), "5.35");
		selectByVisibleText(By.id("websiteoptions"), "西塘支行");
		click(By.xpath("//*[@onclick='applyDepoOrder()']"));
		requestID4= getRequestID4();
		// 申请已成功提交！
	}

//	@Test(priority = 16)
	public String getRequestID4() {
		click(By.xpath("//*[text()='查看我的申请']"));
		// driver.findElement(By.xpath("//ul[@class='options']/li[3]"));
		// click(By.xpath("//*[@onclick='statusSearch('0,1')']"));
		// click(By.xpath("/html/body/div[2]/div/div/div[3]/div[1]/div[2]/div[2]/div[1]/span"));
		click(By.xpath("//div[@class='hkdeadline']/span[text()='5.35']"));
		String str = driver.findElement(By.xpath("//div[@class='info']/div[1]")).getText();
		requestID4 = str.substring(str.length() - 20);
		System.out.println("requestID4:" + requestID4);
		click(By.linkText("退出"));
		return requestID4;
	}

	// 存取款取消
	@Test(priority = 17)
	@Parameters({ "backUrl" })
	public void depositCancel2(String backUrl) {
		
		ad.Login_admin(backUrl);
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		click(By.linkText("储蓄"));
		click(By.linkText("存入"));
		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"), requestID4);
		click(By.id("btn-search"));
		waitFor(1 * 1000);
		click(By.xpath("//button[@class='btn btn-xs btn-danger']")); // 指派
		click(By.xpath("//*[@class='ajs-button btn btn-primary']"));
		driver.switchTo().defaultContent();
		click(By.linkText("存取款"));
		click(By.linkText("业务状态"));
		driver.switchTo().frame("content");
		sendKeys(By.id("orderId"), requestID4);
		click(By.id("btn-search"));
		waitFor(1 * 1000);
		click(By.xpath("//button[@class='btn btn-xs btn-warning']"));

		click(By.xpath("//*[@onclick='cancel();']"));
		ad.Logoff_admin(backUrl);

	}

	// 添加存款产品 已经固定改功能
	@Parameters({ "backUrl" })
	public void depositAddProduct(String backUrl) {

		ad.Login_admin(backUrl);
		click(By.linkText("存取款"));
		click(By.xpath("//*[text()='产品管理']"));
		click(By.xpath("//*[@href='/depositProductManage.htm']"));
		driver.switchTo().frame("content");
		click(By.xpath("//*[@title='新增']"));
		sendKeys(By.id("productName"), "测试存款产品");
		selectByVisibleText(By.id("depositType"), "人民币");
		sendKeys(By.id("serviceAmount"), "1");
		sendKeys(By.id("serviceObjects"), "服务对象");

		selectByVisibleText(By.id("rmbTermType"), "定期存款");
		selectByVisibleText(By.id("rmbTerm"), "三个月");
		sendKeys(By.name("rmbBaseRate"), "1.2%");
		sendKeys(By.name("rmbRate"), "1.45%");
		sendKeys(By.name("floatRate"), "30%");
		sendKeys(By.name("businessProfile"), "测试业务简介");
		click(By.id("deptName"));
		click(By.id("treeDept_8_span"));

	}

	public void depositEditProduct() {

	}

	public void depositSubmitProduct() {

	}

	public void depositProductApprove() {

	}

	public void depositProductReject() {

	}

	public void depositProductRemove() {

	}
}

package com.purang.yyt_uiautotest.testcase.loan;

import java.awt.AWTException;
import java.sql.SQLException;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;
import com.purang.yyt_uiautotest.testcase.personalcenter.IdentityCertify;
import com.purang.yyt_uiautotest.testcase.personalcenter.Register;

public class LoanRequest extends WebDriverBaseCase {
	

	@Test(priority = 1)
	@Parameters({"indexUrl"})
	public void request(String indexUrl)throws InterruptedException, AWTException, SQLException  {
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(15);
		
		
	}
	
	@Test(priority = 2)
	@Parameters({"indexUrl"})
	public void creditorderapply(String indexUrl) {
		Register newuser = new Register();
		get(indexUrl);
		click(By.xpath("//*[@href='/logon.htm']"));
		sendKeys(By.id("username"), newuser.Tel);

		sendKeys(By.id("password"), newuser.psw);
		waitFor(2 * 1000);
		click(By.xpath("//*[@id='loginBtn']"));// 登陆
		waitFor(2 * 1000);
		click(By.xpath("//*[@href='/usercenter.htm']"));
		click(By.xpath("//*[@href='/usercreditmoney.htm']"));
		click(By.xpath("//*[@href='javascript:checkInProcessCredit();']"));//申请授信
		
		click(By.id("dkyt"));
		click(By.xpath("//*[contains(text(),'购生产工具')]"));
		sendKeys(By.id("loanMoney"), "49.00");
		click(By.id("loanOrgSelectDetail"));
		driver.findElement(By.xpath("//*[@id='loanOrgSelectDetail']/option[4]")).click();//xitangzhihang
		
		click(By.id("creditType"));
		click(By.xpath("//*[contains(text(),'信用授信')]"));
		click(By.xpath("//*[@onclick='submitCreditApply()']"));
		
		click(By.xpath("//*[@onclick='submitLoanOrder()']"));
	}
	
	@Test(priority = 3)
	@Parameters({"backUrl"})
	public void creditorderAssi(String backUrl) {
		Register register = new Register();
		String real_name=register.real_name;
		get(backUrl);
		sendKeys(By.id("username"),"admin");
		sendKeys(By.id("password"),"111aaa");
		click(By.xpath("//*[@id='loginBtn']"));
		 
		click(By.xpath("//*[text()='业务状态']"));
		
		driver.switchTo().frame("content");
		sendKeys(By.id("personName"),real_name);
		click(By.id("btn-search"));
		waitFor(1000);
		click(By.xpath("//*[@class='btn btn-sm btn-info']"));
		click(By.xpath("//*[@class='btn btn-success']"));
	}
	
	@Test(priority = 4)
	@Parameters({"backUrl"})
	public void creditorderAppr1(String backUrl) {
		Register register = new Register();
		String real_name=register.real_name;
		get(backUrl);
		sendKeys(By.id("username"),"2222222");
		sendKeys(By.id("password"),"111aaa");
		click(By.xpath("//*[@id='loginBtn']"));
		
		click(By.xpath("//*[text()='业务状态']"));
		driver.switchTo().frame("content");
		sendKeys(By.id("personName"),real_name);
		click(By.id("btn-search"));
		waitFor(1000);
		click(By.xpath("//*[@class='btn btn-sm btn-info']"));
		sendKeys(By.id("month"),"24");
		click(By.id("pass_button_1"));
		click(By.id("button_1"));
		
	}
	
	@Test(priority = 5)
	@Parameters({"backUrl"})
	public void creditorderAppr2(String backUrl) {
		Register register = new Register();
		String real_name=register.real_name;
		get(backUrl);
		sendKeys(By.id("username"),"111111");
		sendKeys(By.id("password"),"111aaa");
		click(By.xpath("//*[@id='loginBtn']"));
		click(By.xpath("//*[text()='业务状态']"));
		
		driver.switchTo().frame("content");
		sendKeys(By.id("personName"),real_name);
		click(By.id("btn-search"));
		waitFor(1000);
		click(By.xpath("//*[@class='btn btn-sm btn-info']"));
		 
		click(By.id("pass_button_2"));
		click(By.id("button_2"));
		
	}
	
	@Test(priority = 6)
	@Parameters({"backUrl"})
	public void creditInvest(String backUrl) {
		Register register = new Register();
		String real_name=register.real_name;
		get(backUrl);
		sendKeys(By.id("username"),"333322");
		sendKeys(By.id("password"),"111aaa");
		click(By.xpath("//*[@id='loginBtn']"));
		click(By.xpath("//*[text()='业务状态']"));
		
		driver.switchTo().frame("content");
		sendKeys(By.id("personName"),real_name);
		click(By.id("btn-search"));
		waitFor(1000);
		click(By.xpath("//*[@class='btn btn-sm btn-info']"));
		 
		click(By.id("pass_button_3"));
		click(By.id("button_3"));
		
	}
	
	
	@Test(priority = 7)
	@Parameters({"backUrl"})
	public void presidentAppr(String backUrl) {
		Register register = new Register();
		String real_name=register.real_name;
		get(backUrl);
		sendKeys(By.id("username"),"333322");
		sendKeys(By.id("password"),"111aaa");
		click(By.xpath("//*[@id='loginBtn']"));
		click(By.xpath("//*[text()='业务状态']"));
		
		driver.switchTo().frame("content");
		sendKeys(By.id("personName"),real_name);
		click(By.id("btn-search"));
		waitFor(1000);
		click(By.xpath("//*[@class='btn btn-sm btn-info']"));
		
		click(By.id("pass_button_4"));
		click(By.id("button_4"));
		
	}
	
	@Test(priority = 7)
	@Parameters({"backUrl"})
	public void Appr(String backUrl) {
		Register register = new Register();
		String real_name=register.real_name;
		get(backUrl);
		sendKeys(By.id("username"),"333322");
		sendKeys(By.id("password"),"111aaa");
		click(By.xpath("//*[@id='loginBtn']"));
		click(By.xpath("//*[text()='业务状态']"));
		
		driver.switchTo().frame("content");
		sendKeys(By.id("personName"),real_name);
		click(By.id("btn-search"));
		waitFor(1000);
		click(By.xpath("//*[@class='btn btn-sm btn-info']"));
		
		click(By.id("pass_button_5"));
		click(By.id("button_5"));
		
	}
	
	
 	@Test(priority = 8)
	@Parameters({"indexUrl"})
	public void loanRequest(String indexUrl) throws InterruptedException, AWTException, SQLException {

		
		Register newuser = new Register();
		get(indexUrl);
		click(By.xpath("//*[@href='/logon.htm']"));
		sendKeys(By.id("username"), newuser.Tel);
		click(By.xpath("//*[@id='loginBtn']"));
		sendKeys(By.id("password"), newuser.psw);
		waitFor(2 * 1000);
		click(By.xpath("//*[@id='loginBtn']"));// 登陆
		waitFor(2 * 1000);
		if (elementExists(By.id("usernameMsg"), 2)) {
			Register register = new Register();
			register.reguser(indexUrl);

		} else {
			System.out.println("是注册用户！");
		}

		click(By.xpath("//*[@href='/usercenter.htm']"));

		if (elementExists(By.xpath("//button[@class='wstype rz valid']"), 2)) {

			System.out.println("账号已认证"); // 判定账号认证
		} else {
			System.out.println("未认证");
			click(By.xpath("//*[@href='/loanindex.htm']"));
			click(By.xpath("//*[contains(text(),'金燕职贷通')]"));
			click(By.xpath("//*[@href='/loanorderapply.htm?productId=6376960187453931619']"));
			ASSERT.assertTrue(elementExists(By.id("subBtn")));// 非认证用户需要进行实名认证

			IdentityCertify certify = new IdentityCertify();// 执行认证
			certify.certify(indexUrl);// 进行认证操作

		}

		click(By.xpath("//*[@href='/loanindex.htm']"));
		click(By.xpath("//*[contains(text(),'信用贷款')]"));
	
		click(By.xpath("//*[contains(text(),'质押贷款')]"));
		click(By.xpath("//*[contains(text(),'三权贷款')]"));
		click(By.xpath("//*[contains(text(),'抵押贷款')]"));
		click(By.xpath("//*[@onclick='checkInProcessCredit('6385463917757333603')']"));
		click(By.id("dkyt"));
		click(By.xpath("//*[contains(text(),'购生产工具')]"));
		sendKeys(By.id("loanMoney"), "34.1");
		sendKeys(By.id("loanMonth"), "22");
		driver.findElement(By.xpath("//*[@id='loanOrgSelectDetail']/option[4]")).click();
		sendKeys(By.id("recommendCode"), "1234");
		click(By.xpath("//button[@onclick='submitCreditApply()']"));
		waitFor(2 * 1000);
		click(By.xpath("//*[@href='/usercenter.htm']"));// 个人中心
		click(By.id("mybusinessparent"));// 我的业务

		click(By.xpath("//*[@href='/userloanorder.htm']"));// 贷款

		ASSERT.assertTrue(elementExists(By.xpath("//*[contains(text(),'34.10万')]"), 2));
		ASSERT.assertTrue(elementExists(By.xpath("//*[contains(text(),'22个月')]"), 2));
		if (elementExists(By.xpath("//*[contains(text(),'补充资料')]"), 2)) {
			FillInfo fill =new FillInfo();
			
			fill.fillinfo();

		} else {
			System.out.println("资料已经补充完毕！待贷款审核");
		}
		
		
		
	}
	
	

}

package com.purang.yyt_uiautotest.testcase.personalcenter;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;

public class UserMenu extends WebDriverBaseCase {
	@Test(priority = 1)
	@Parameters({"indexUrl"})
	public void usermenu(String indexUrl) throws InterruptedException {

		startWebDriver();
		windowMaximize();
		setMaxWaitTime(30);

		menu(indexUrl);
	}
	@Parameters({"indexUrl"})
	public void menu(String indexUrl ) throws InterruptedException {
		Login_personal user = new Login_personal();
		user.Login_personal(indexUrl);

		click(By.xpath("//*[@href='/usercenter.htm']"));

		click(By.id("myinfoparent"));
		click(By.id("myinfoparent"));
		click(By.xpath("//*[@href='/usercenter.htm']"));
	}
	@Test(priority = 2)
	public void Myinfo() throws InterruptedException {
		click(By.linkText("我的资料"));
		if (driver.getPageSource().contains("头像")) {

		} else {
			System.out.println("头像_栏异常");
		}

		if (driver.getPageSource().contains("性别")) {

		} else {
			System.out.println("性别异常");
		}
		if (driver.getPageSource().contains("住房")) {

		} else {
			System.out.println("住房_栏异常");
		}
		if (driver.getPageSource().contains("配偶")) {

		} else {
			System.out.println("配偶_栏异常");
		}
		if (driver.getPageSource().contains("家庭成员")) {

		} else {
			System.out.println("家庭成员_栏异常");
		}

		click(By.linkText("担保人信息"));
		click(By.xpath("//*[@id='guarantor-add']"));
		click(By.id("backEnsurePersonList"));

		click(By.linkText("银行卡管理"));
		click(By.id("card-add"));
		click(By.linkText("银行卡管理"));

		click(By.linkText("安全中心"));
		if (driver.getPageSource().contains("安全评分")) {

		} else {
			System.out.println("安全评分_栏异常");
		}
		if (driver.getPageSource().contains("密码修改")) {

		} else {
			System.out.println("密码修改_栏异常");
		}
		if (driver.getPageSource().contains("绑定手机")) {

		} else {
			System.out.println("绑定手机_栏异常");
		}
	}
	@Test(priority = 3)
	public void mybusinessparent() throws InterruptedException {
		click(By.id("mybusinessparent"));
		click(By.xpath("//*[@href='/userdepoorder.htm']"));

		if (driver.getPageSource().contains("累计存入金额")) {

		} else {
			System.out.println("累计存入金额_栏异常");
		}
		if (driver.getPageSource().contains("累计存入笔数")) {

		} else {
			System.out.println("累计存入笔数_栏异常");
		}
		if (driver.getPageSource().contains("累计取出金额")) {

		} else {
			System.out.println("累计取出金额_栏异常");
		}
		if (driver.getPageSource().contains("存款余额")) {

		} else {
			System.out.println("存款余额_栏异常");
		}
	}
	@Test(priority = 4)
	public void userloanorder() throws InterruptedException {
		click(By.xpath("//*[@href='/userloanorder.htm']"));

		if (driver.getPageSource().contains("贷款金额")) {

		} else {
			System.out.println("贷款金额_栏异常");
		}
		if (driver.getPageSource().contains("贷款期限")) {

		} else {
			System.out.println("贷款期限_栏异常");
		}
		if (driver.getPageSource().contains("业务状态")) {

		} else {
			System.out.println("业务状态_栏异常");
		}

		click(By.xpath("//*[@href='/userfinaorder.htm']"));

		if (driver.getPageSource().contains("累计收益")) {

		} else {
			System.out.println("累计收益_栏异常");
		}
		if (driver.getPageSource().contains("待收本金")) {

		} else {
			System.out.println("待收本金_栏异常");
		}
		if (driver.getPageSource().contains("待收收益")) {

		} else {
			System.out.println("待收收益_栏异常");
		}
		if (driver.getPageSource().contains("订单状态")) {

		} else {
			System.out.println("订单状态_栏异常");
		}
	}
	@Test(priority = 5)
	public void usermarket() throws InterruptedException {
		click(By.xpath("//*[@href='/usermarket.htm']"));

		if (elementExists(By.xpath("//*[@src='/images/btn-market-buy.png']"), 2)) {

		} else {
			System.out.println("我要买_栏异常");
		}
		if (elementExists(By.xpath("//*[@src='/images/btn-market-sale.png']"), 2)) {

		} else {
			System.out.println("我要卖_栏异常");
		}
		if (elementExists(By.xpath("//*[@src='/images/btn-market-job.png']"), 2)) {

		} else {
			System.out.println("我要招工_栏异常");
		}
		if (driver.getPageSource().contains("我的收藏")) {

		} else {
			System.out.println("我的收藏_栏异常");
		}
		if (driver.getPageSource().contains("我的发布")) {

		} else {
			System.out.println("我的发布_栏异常");
		}
		if (driver.getPageSource().contains("发布商品")) {

		} else {
			System.out.println("发布商品_栏异常");
		}
		if (driver.getPageSource().contains("累计成交")) {

		} else {
			System.out.println("累计成交_栏异常");
		}
	}
	@Test(priority = 6)
	public void userservice() throws InterruptedException {
		click(By.xpath("//*[@href='/userservice.htm']"));

		if (driver.getPageSource().contains("保险")) {

		} else {
			System.out.println("保险_栏异常");
		}
		if (driver.getPageSource().contains("助学金")) {

		} else {
			System.out.println("助学金_栏异常");
		}
		if (driver.getPageSource().contains("法律援助")) {

		} else {
			System.out.println("法律援助_栏异常");
		}
		if (driver.getPageSource().contains("疫诊咨询")) {

		} else {
			System.out.println("疫诊咨询_栏异常");
		}

		click(By.xpath("//*[@href='/usercreditmoney.htm']"));

		click(By.xpath("//*[@href='/notice.htm']"));

	}
}
package com.purang.yyt_uiautotest.testcase.loan;

import java.awt.AWTException;
import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import com.purang.yyt_uiautotest.testcase.personalcenter.Login_personal;
import com.purang.yyt_uiautotest.testcase.personalcenter.Register;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;

public class FillInfo extends WebDriverBaseCase {
	@Test
	@Parameters({"indexUrl"})
	public void beforfill(String indexUrl) throws InterruptedException {
		startBrower();

		this.setMaxWaitTime(15);
		get(indexUrl);
		waitForElementVisible(By.xpath("//*[@href='/logon.htm']"), 5);
		click(By.xpath("//*[@href='/logon.htm']"));
		Register newuser = new Register();

		sendKeys(By.id("username"), newuser.Tel);
		sendKeys(By.id("password"), newuser.psw);
		waitFor(1 * 1000);
		click(By.xpath("//*[@id='loginBtn']"));
		waitFor(2 * 1000);
		click(By.xpath("//*[@href='/usercenter.htm']"));
		click(By.id("mybusinessparent"));
		click(By.xpath("//*[@href='/userloanorder.htm']"));

		if (elementExists(By.xpath("//*[contains(text(),'补充资料')]"), 2)) {
			fillinfo();

		} else {
			System.out.println("资料已经补充完毕！待贷款审核");
		}

	}

	public void fillinfo() {

		click(By.linkText("补充资料"));
		String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
		Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			if (currentWindow != it.next()) {
				WebDriver window = driver.switchTo().window(it.next());// 切换到新窗口
			}
		}
		click(By.xpath("//*[@id='marrStatus3']"));
		click(By.xpath("//*[@id='repayType2']"));
		driver.findElement(By.xpath("//*[@selectname='kehuleixing']/option[2]")).click();
		sendKeys(By.xpath("//*[@nameinfo='本地居住年限']"), "3");

		driver.findElement(By.xpath("//*[@selectname='zhufangqingkuang']/option[3]")).click();

		sendKeys(By.xpath("//*[@nameinfo='姓名']"), "王誉珩");
		sendKeys(By.xpath("//*[@nameinfo='身份证号码']"), "413029198306083119");
		sendKeys(By.xpath("//*[@nameinfo='联系方式']"), "13937662966");
		driver.findElement(By.xpath("//*[@selectname='zhiye']/option[2]")).click();
		sendKeys(By.xpath("//*[@key='shouru']"), "15014");
		click(By.xpath("//*[contains(text(),'添加家庭成员')]"));

		sendKeys(By.xpath("//*[@repeatpos='guanxi_0']"), "父子");
		sendKeys(By.xpath("//*[@repeatpos='chengyuanxingming_0']"), "父子abc");
		sendKeys(By.xpath("//*[@repeatpos='chengyuanshenfenz_0']"), "413029198306083119");
		driver.findElement(By.xpath("//*[@repeatpos='chengyuanxueli_0']/option[3]")).click();
		driver.findElement(By.xpath("//*[@repeatpos='chengyuanzhiye_0']/option[3]")).click();

		sendKeys(By.xpath("//*[@nameinfo='种养殖项目']"), "水稻");
		sendKeys(By.xpath("//*[@nameinfo='种养殖规模']"), "20亩");
		sendKeys(By.xpath("//*[@nameinfo='种养殖年限']"), "15年");
		sendKeys(By.xpath("//*[@nameinfo='家庭劳动力数量']"), "5");
		sendKeys(By.xpath("//*[@nameinfo='上年度经营净利润(元)']"), "24万");

		sendKeys(By.xpath("//*[@nameinfo='经营项目']"), "小卖部");
		sendKeys(By.xpath("//*[@nameinfo='经营年限']"), "2年");
		sendKeys(By.xpath("//*[@nameinfo='经营地址']"), "测试路测试号");
		sendKeys(By.xpath("//*[@nameinfo='上年度商业净利润(元)']"), "500000");
		sendKeys(By.xpath("//*[@nameinfo='本人控制或参与投资、经营的其他经营项目']"), "无、没有");

		sendKeys(By.xpath("//*[@nameinfo='工作单位']"), "平时有限公司");
		sendKeys(By.xpath("//*[@nameinfo='职务']"), "Coder");
		sendKeys(By.xpath("//*[@nameinfo='年收入(元)']"), "200000");

		click(By.xpath("//*[@key='yuanshuxinyong']"));
		click(By.xpath("//*[@value='商品房']"));

		click(By.xpath("//*[@value='存单']"));
		click(By.xpath("//*[@value='自然人保证']"));

		click(By.xpath("//*[contains(text(),'+添加房屋资产')]"));
		sendKeys(By.xpath("//*[@repeatpos='fangwumingcheng_0']"), "测试路100号");
		driver.findElement(By.xpath("//*[@repeatpos='fangwushuliang_0']/option[2]")).click();
		sendKeys(By.xpath("//*[@repeatpos='fangwumianji_0']"), "95");
		sendKeys(By.xpath("//*[@repeatpos='fangwuguzhi_0']"), "1000");

		click(By.xpath("//*[contains(text(),'+添加现金资产')]"));
		sendKeys(By.xpath("//*[@repeatpos='xianjinmingcheng_0']"), "银行存款");
		sendKeys(By.xpath("//*[@repeatpos='xianjinqixian_0']"), "30年");
		sendKeys(By.xpath("//*[@repeatpos='xianjinyinhang_0']"), "中国银行");
		sendKeys(By.xpath("//*[@repeatpos='xianjinjine_0']"), "100");

		click(By.xpath("//*[contains(text(),'+添加其他资产')]"));
		sendKeys(By.xpath("//*[@repeatpos='qitamingcheng_0']"), "股票资产");
		sendKeys(By.xpath("//*[@repeatpos='qitayongtu_0']"), "股票");
		sendKeys(By.xpath("//*[@repeatpos='qitashuliang_0']"), "100");
		sendKeys(By.xpath("//*[@repeatpos='qitaguzhi_0']"), "100");

		driver.findElement(By.id("file2")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		driver.findElement(By.id("file3")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		driver.findElement(By.id("file4")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		driver.findElement(By.id("file5")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		driver.findElement(By.id("file6")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		driver.findElement(By.id("file7")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");

		click(By.xpath("//button[@onclick='submitLoanOrder()']"));
		waitFor(1 * 1000);
		ASSERT.assertTrue(driver.getPageSource().contains("恭喜您,您的信贷申请已提交"));// 恭喜您,您的信贷申请已提交
	}

	public void startBrower() {
		startWebDriver();
		windowMaximize();
		setMaxWaitTime(30);

	}

}

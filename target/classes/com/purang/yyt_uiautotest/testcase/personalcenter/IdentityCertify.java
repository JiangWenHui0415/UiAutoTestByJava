package com.purang.yyt_uiautotest.testcase.personalcenter;

import java.io.File;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;

public class IdentityCertify extends WebDriverBaseCase {

	@Test(priority = 1)
	@Parameters({"indexUrl"})
	public void startweb(String indexUrl) throws InterruptedException {
		startWebDriver();
		windowMaximize();
		setMaxWaitTime(30);

		
	}
	@Test(priority = 2)
	@Parameters({"indexUrl"})
	public void certify(String indexUrl) throws InterruptedException {

		Register register = new Register();
		get(indexUrl+"/logon.htm");

		sendKeys(By.id("username"), register.Tel);
		sendKeys(By.id("password"), register.psw);
		click(By.xpath("//*[@id='loginBtn']"));
		waitFor(1 * 1000);

		String idNo =  register.id_no;
		String realName =  register.real_name;
		String telephone_pre = "0021";
		String telephone_suf = "60651010";
		String regProvinceName = "河北";
		String regCityName = "石家庄";
		String regCountryName = "平山县";
		String reg_add = "河北省石家庄市平山县35路25号";
		String locProvinceName = "山东";
		String locCityName = "德州";
		String locCountryName = "宁津县";
		String loc_add = "山东省德州市宁津县测试路25号";
		String company = "ABC有限公司";

		click(By.xpath("//*[@href='/usercenter.htm']"));
		click(By.xpath("//button[@title='实名认证']"));

		sendKeys(By.id("realName"), realName);
		sendKeys(By.id("idNo"), "34282819"); // 错误身份证号码
		ASSERT.assertTrue(elementExists(By.id("idNoErrId"), 2)); // 断言"身份证号不合法"

		sendKeys(By.id("idNo"), idNo);

		waitFor(2 * 1000);
		// click(By.xpath("//*[@onclick='setSex(0)']")); //系统会按照身份证号码自动判断男女

		driver.findElement(By.id("file2")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard1.jpg");
		waitFor(1 * 1000);
		driver.findElement(By.id("file3")).sendKeys(System.getProperty("user.dir")+File.separator+"src\\test\\resources\\IDcard2.jpg");
		waitFor(1 * 1000);
		sendKeys(By.id("telephone_pre"), telephone_pre);
		sendKeys(By.id("telephone_suf"), telephone_suf);

		sendKeys(By.id("regProvinceName"), regProvinceName);
		sendKeys(By.id("regCityName"), regCityName);
		sendKeys(By.id("regCountryName"), regCountryName);

		sendKeys(By.id("reg_add"), reg_add);

		sendKeys(By.id("locProvinceName"), locProvinceName);
		sendKeys(By.id("locCityName"), locCityName);
		sendKeys(By.id("locCountryName"), locCountryName);

		sendKeys(By.id("loc_add"), loc_add);

		click(By.id("job"));
		click(By.xpath("//*[@id=\"job\"]/ul/li[2]"));

		sendKeys(By.id("company"), company);

		click(By.xpath("//button[@id='subBtn']"));
	}
		
	@Test(priority = 3)
	@Parameters({ "indexUrl" })
	public void reCheck(String indexUrl) throws InterruptedException {
			if(driver.getPageSource().contains("认证成功")) {
				System.out.println("认证成功");
				
			}else {
				System.out.println("认证失败");
				certify(indexUrl);
			}

			
		}
	}


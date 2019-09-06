package com.purang.yyt_uiautotest.testcase.demo;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;

public class DemoCase extends WebDriverBaseCase {

	@Test
	public void baiduSearchExam() {
		String searchText = "砖家叫兽";
		
		startWebDriver();
		windowMaximize();
		this.setMaxWaitTime(5);

		get("http://www.baidu.com/");
		sendKeys(By.id("kw"), searchText);
		click(By.id("su"));
		click(By.linkText(searchText + "_百度图片"));
		ASSERT.assertTrue(elementExists(By.linkText(searchText + "_百度图片"), 2));

//		closeWebDriver();
	}
}
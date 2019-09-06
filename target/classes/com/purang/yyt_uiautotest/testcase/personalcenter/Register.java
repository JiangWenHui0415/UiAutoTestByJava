package com.purang.yyt_uiautotest.testcase.personalcenter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import com.purang.yyt_uiautotest.core.frame.WebDriverBaseCase;

public class Register extends WebDriverBaseCase {

	public static String Tel;
	public static String real_name;
	public static String id_no;
	public static String psw = "111aaa";
	public static String a;

	@Test(priority = 1)
	public void getUser() throws AWTException, SQLException {

		Connection con1,con2;

		String driver = "com.mysql.jdbc.Driver";

		String url1 = "jdbc:mysql://10.10.96.145:3306/sky";
		String url2 = "jdbc:mysql://10.10.96.151:3306/sky_purang";
		String user1 = "dataSelect";

		String password1 = "dataSelect";

		try {
			
			Class.forName(driver);
			
			con1 = DriverManager.getConnection(url1, user1, password1);
			
			con2 = DriverManager.getConnection(url2, user1, password1);
			Statement statement = con1.createStatement();
			Statement statement2 = con2.createStatement();

			String sql1 = "SELECT A.mobile,A.real_name,A.id_no from sky_xinxian_prd.`user` A where  A.`name` IS NOT NULL and A.id_no IS NOT NULL";
			
			ResultSet rs1 = statement.executeQuery(sql1);
			do {
			
			
			
				rs1.next();
				Tel = rs1.getString(1);
				real_name = rs1.getString(2);
				id_no = rs1.getString(3);	
				
				
				String sql2 = "Select * from user where mobile="+Tel;
				
				a=null;
				ResultSet rs2 = statement2.executeQuery(sql2);
				while(rs2.next() ) {
				
				 a=rs2.getString(1);
				 }
			}while(a!=null);
				
				
			
			
			rs1.close();
			con1.close();
			
			con2.close();
			
			
			
		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Test(priority = 2)
	public void startweb() throws AWTException, SQLException {
		startWebDriver();
		windowMaximize();
		setMaxWaitTime(30);

	}

	@Test(priority = 5)
	@Parameters({"indexUrl"})
	public void afterReg(String indexUrl) throws AWTException, SQLException {

//		click(By.cssSelector(".yytBtn.red"));
		waitFor(2 * 1000);
		get(indexUrl);
		click(By.xpath("//*[@href='/logout.htm']"));
		click(By.linkText("注册"));
		sendKeys(By.id("mobile"), Tel);
		click(By.id("code_generate"));
		assertTrue(elementExists(By.id("mobileMsg"), 2));// 断言出现手机号码已占用
		waitFor(3 * 1000);
		

	}

	@Test(priority = 3)
	@Parameters({"indexUrl"})
	public void reguser(String indexUrl) throws AWTException, SQLException {

		get(indexUrl);
		click(By.xpath("//*[@href='/logon.htm']"));
		click(By.linkText("立即注册"));

		sendKeys(By.id("mobile"), Tel);
		click(By.id("code_generate"));
		waitFor(3000);
	}

	@Test(priority = 4)
	public void validateCode() throws AWTException, SQLException {
		Connection con;

		String driver = "com.mysql.jdbc.Driver";

		String url = "jdbc:mysql://10.10.96.151:3306/sky_purang";

		String user = "dataSelect";

		String password = "dataSelect";

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			Statement statement = con.createStatement();

			String sql = "select code from mobile_validate where mobile ='" + Tel + "' ORDER BY send_date DESC";

			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			String code = rs.getString(1);

			sendKeys(By.id("mobileCode"), code);
			rs.close();
			con.close();
		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		sendKeys(By.id("password"), psw);
		sendKeys(By.id("rpassword"), psw);

		click(By.id("regBtn"));

	}

}
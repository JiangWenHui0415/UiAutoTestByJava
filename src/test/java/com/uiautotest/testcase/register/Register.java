package com.uiautotest.testcase.register;

import com.purang.camp.uiautotest.core.frame.WebDriverBaseCase;
import com.purang.camp.uiautotest.utils.JdbcUtilByJwh;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.time.LocalTime;

public class Register extends WebDriverBaseCase {
    @Test(enabled = true)
    public void register() throws InterruptedException{
        String phone_num1 = "0069";
        String phone_num = "1381111" + phone_num1;
//        String statement = "select * from mobile_validate where phone =" + phone_num;
        String code;
        System.out.println("注册");
        click(By.id("register"));
        sendKeys(By.id("phoneNo3"),phone_num);
        click(By.xpath("//*[@id=\"registerForm\"]/div/div[3]/div[1]/button"));
        System.out.println("连接数据库，查询验证码");
        JdbcUtilByJwh.getConnection();
        code = JdbcUtilByJwh.dbDataMethod(phone_num);
        System.out.println("验证码=" + code);
        waitFor(2*1000);
        sendKeys(By.id("dynamicCode3"), code);
        String userName = "AT" + phone_num1;
        System.out.println("输入用户名");
        sendKeys(By.id("name"),userName);
        waitFor(2*1000);
        System.out.println("输入密码");
        sendKeys(By.id("plainPassword"),"111111");
        System.out.println("确认密码");
        sendKeys(By.id("repeatedPlainPassword"),"111111");
        waitFor(2*1000);
        System.out.println("输入机构名称");
        String fullName = "AutoTestCompany";
        sendKeys(By.id("fullName"),fullName);
        click(By.xpath("//*[@id=\"fullNameList\"]/span[1]"));
        waitFor(2*1000);
        System.out.println("输入邀请码");
        sendKeys(By.id("inviteCode"),"asdf123");
        waitFor(2*1000);
        /*
        * 上传文件时，若有input标签，可以直接使用sendkey来传文件
        * 否则需要借助AutoIt来协助
        * */
        System.out.println("上传名片");
        click(By.xpath("//*[@id=\"card\"]"));
        Runtime exe = Runtime.getRuntime();
        // Java 的Runtime 模块的getruntime.exec()方法可以调用exe 程序并执行。
        try {
            String str = "./src/main/java/com/purang/camp/uiautotest/tools/autoIT/upload.exe";
            exe.exec(str);
            // 运行指定位置的.exe文件
        } catch (IOException e) {
            System.out.println("Error to run the exe");
            e.printStackTrace();
        }
        waitFor(4*1000);
        click(By.id("registerButton"));
        waitFor(3*1000);
        System.out.println("注册完成");
    }
}

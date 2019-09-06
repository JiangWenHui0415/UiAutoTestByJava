package com.uiautotest.testcase.point;

import com.purang.camp.uiautotest.core.frame.WebDriverBaseCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Point extends WebDriverBaseCase{

    @Test(priority = 0,enabled = true)
    public void point (){
        System.out.println("Click the point button");
        click(By.linkText("观点"));
        waitFor(2*1000);
        WebElement iframe = driver.findElement(By.className("ke-edit-iframe"));
        driver.switchTo().frame(iframe);
        click(By.className("ke-content"));
        waitFor(2*1000);
        sendKeys(By.className("ke-content"),"UIAutotest" + LocalDateTime.now());
        driver.switchTo().defaultContent();
        click(By.id("shortFilePublish"));
        waitFor(3*1000);
    }
    @Test(priority = 1)
    public void comment() {
        System.out.println("点击评论");
        click(By.xpath("//*[@id=\"articleShow\"]/div[1]/div[1]/div[4]/a[3]"));
        sendKeys(By.id("text0"),"UIAutoComment" + LocalDateTime.now());
        waitFor(5*1000);
        System.out.println("点击提交评论");
        click(By.xpath("//*[@id='comment0']"));
        System.out.println("评论成功");
        waitFor(3*1000);
    }
    @Test(priority = 2)
    public void collect(){
        System.out.println("点击收藏");
        click(By.xpath("//*[@id=\"collect0\"]"));
        //后续需要断言是否收藏成功
    }
    @Test(priority = 3)
    public void like(){
        System.out.println("点赞");
        click(By.xpath("//*[@id=\"like0\"]"));
        //后续需要断言是否点赞成功
    }
    @Test(priority = 4)
    public void detail(){
        System.out.println("进入文章详情页");
        //由于新开了一个页面，需要先切换句柄
        List<String> it1 = new ArrayList<String>(driver.getWindowHandles());
        System.out.println("点击之前的句柄" + it1);
        System.out.println("点击之前的url=" + driver.getCurrentUrl());
        click(By.xpath("//*[@id='articleShow']/div[1]/div[1]/div[2]/h4"));
        waitFor(5*1000);
        System.out.println("等待5s,并切换窗口");
        List<String> it2 = new ArrayList<String>(driver.getWindowHandles());
        System.out.println("点击之后的句柄" + it2);
        System.out.println("当前标题==" + driver.getTitle());
        //通过句柄索引进入当前窗口
        driver.switchTo().window(it2.get(1));
        System.out.println("点击之后的url=" + driver.getCurrentUrl());
        System.out.println("切换后的标题=" + driver.getTitle());
        sendKeys(By.id("textarea"),"AutoTest_Comment_detailPage" + LocalDateTime.now());
//        click(By.xpath("//*[@id=\"textarea\"]"));
//        sendKeys(By.xpath("//*[@id=\"textarea\"]"),"AutoTest_Comment_detailPage" + LocalDateTime.now());
        click(By.id("comment"));
        System.out.println("评论成功");
    }
}

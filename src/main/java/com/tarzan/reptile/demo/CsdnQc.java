package com.tarzan.reptile.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CsdnQc {
    private final static String webDriver = "webdriver.chrome.driver";
    private final static String webDriverPath ="E:\\chromedriver\\chromedriver.exe";


    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty(webDriver, webDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        queryQualityScore(driver,"https://tarzan.blog.csdn.net/article/details/131881380?spm=1001.2014.3001.5502");
    }



    public static Integer queryQualityScore(WebDriver driver,String postUrl) throws InterruptedException {
        driver.get("https://www.csdn.net/qc");
        Thread.sleep(1000);
        WebElement  ele=driver.findElement(By.className("el-input__inner"));
        ele.sendKeys(postUrl);
        Thread.sleep(1000);
        WebElement  qcEle=driver.findElement(By.className("trends-input-box-btn"));
        qcEle.click();
        WebElement  element=driver.findElement(By.xpath("//div[@class='csdn-body-right']/p[1]"));
        System.out.println(element.getText());
        return Integer.parseInt(element.getText());
    }

}

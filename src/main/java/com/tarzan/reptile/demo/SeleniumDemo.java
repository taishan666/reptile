package com.tarzan.reptile.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

public class SeleniumDemo {
    private final static String webDriver = "webdriver.chrome.driver";
    private final static String webDriverPath ="E:\\chromedriver\\chromedriver.exe";


    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty(webDriver, webDriverPath);
        WebDriver driver= new ChromeDriver();
        driver.get("https://www.douyin.com/");
        Thread.sleep(1000);
        WebElement login=driver.findElement(By.xpath("//*[text()='登录']"));
        login.click();
        Thread.sleep(1000);
        WebElement passwordTab=driver.findElement(By.xpath("//*[text()='密码登录']"));
        passwordTab.click();
        WebElement phone=driver.findElement(By.name("normal-input"));
        phone.sendKeys("18838811955");
        WebElement password=driver.findElement(By.name("button-input"));
        password.sendKeys("a1334512682");
        WebElement submit=driver.findElement(By.className("web-login-button"));
        submit.click();
        Thread.sleep(1000);
        WebElement captcha=driver.findElement(By.id("captcha-verify-image"));
        System.out.println(captcha.getAttribute("src"));

        WebElement captchaImg=driver.findElement(By.xpath("//div[contains(@class,\"captcha_verify_img--wrapper\")]"));
        // 截图操作
        File sourceFile = captchaImg.getScreenshotAs(OutputType.FILE);
        // 截图存储
        FileCopyUtils.copy(sourceFile, new File("E:\\screenshot\\"+driver.getTitle()+".png"));


    }


}

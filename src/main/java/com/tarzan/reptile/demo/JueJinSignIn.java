package com.tarzan.reptile.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;


public class JueJinSignIn {

    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\work_space\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";
    private static WebDriver driver = null;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty(webDriver, webDriverPath);
        // ChromeOptions
        ChromeOptions chromeOptions = new ChromeOptions();
        // 设置后台静默模式启动浏览器
        //   chromeOptions.addArguments("--headless");
        //添加用户cookies数据
        chromeOptions.addArguments("--user-data-dir=C:\\Users\\liuya\\AppData\\Local\\Google\\Chrome\\User Data1");
        //启动浏览器
        driver = new ChromeDriver(chromeOptions);
        while (true){
            start();
        }
    }

    public static void start() throws InterruptedException {
        driver.get("https://juejin.cn/");
        Thread.sleep(500);
        WebElement signedin = driver.findElement(By.xpath("//button[@class='btn signedin-btn']"));
        signedin.sendKeys(Keys.ENTER);
        Thread.sleep(500);
        WebElement signed = driver.findElement(By.xpath("//button[@class='signedin btn']"));
        signed.sendKeys(Keys.ENTER);
        Thread.sleep(500);
        WebElement btn = driver.findElement(By.xpath("//div[@class='btn-area']/button[@class='btn']"));
        btn.sendKeys(Keys.ENTER);
        Thread.sleep(500);
        WebElement LuckyDrawBtn = driver.findElement(By.id("turntable-item-0"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", LuckyDrawBtn);
        LuckyDrawBtn.click();
        Thread.sleep(500);
        System.out.println(driver.getPageSource());
        List<WebElement> elements = (List<WebElement>) jse.executeScript("return jQuery.find('.lottery_modal byte-modal lottery_modal_lucky v-transfer-dom')");
        WebElement reward= elements.get(0).findElement(By.xpath("//div[@class='wrapper']/button[@class='submit']"));
        reward.sendKeys(Keys.ENTER);
        //等待24小时
        Thread.sleep(24*60*60*1000);
    }

}

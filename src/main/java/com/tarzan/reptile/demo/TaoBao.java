package com.tarzan.reptile.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.util.ClassUtils;

import java.util.Objects;

/**
 * 淘宝模拟登录
 *
 * @author tarzan
 * @version 1.0
 * @date 2020/8/7
 * @since JDK1.8
 */
public class TaoBao {

    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath = ClassUtils.getDefaultClassLoader().getResource("chromedriver/chromedriver.exe").getPath();
    private static String mobileUrl = "https://login.m.taobao.com/login.htm";
    private static WebDriver driver = null;

    private static String username = "18838811955";
    private static String password = "a1334512682";


    public static void main(String[] args) {
        start();
    }


    /**
     * 方法描述: 启动
     *
     * @throws
     * @author tarzan Liu
     * @date 2020年08月07日 12:42:02
     */
    public static void start() {
        System.setProperty(webDriver, webDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        options.addArguments("Accept-Encoding=gzip, deflate, sdch");
        options.addArguments("Accept-Language=zh-CN,zh;q=0.8");
        options.addArguments("Connection=keep-alive");
        options.addArguments("Host=activityunion-marketing.meituan.com");
        options.addArguments("Upgrade-Insecure-Requests=1");
        options.addArguments("User-Agent=Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4");
        //不打开浏览器，后台运行
       // options.addArguments("--headless");
        try {
            if (Objects.isNull(driver)){
                driver = new ChromeDriver(options);
                userLogin(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(driver)) {
                //  driver.close();
            }
        }

    }


    /**
     * 登录模块
     */
    private static void userLogin(WebDriver driver) throws Exception {
        driver.get(mobileUrl);
        Thread.sleep(200);//等待0.2秒
        WebElement usernameWebElement = driver.findElement(By.id("fm-login-id"));
        usernameWebElement.sendKeys(username);
        WebElement passwordWebElement = driver.findElement(By.id("fm-login-password"));
        passwordWebElement.sendKeys(password);
        //模拟滑动
        Thread.sleep(1000);
        WebElement draggable = driver.findElement(By.id("nc_1_n1z"));//定位元素
        Actions bu = new Actions(driver); // 声明action对象
        bu.clickAndHold(draggable).build().perform(); // clickAndHold鼠标左键按下draggable元素不放
        bu.moveByOffset(380, 2).perform(); // 平行移动鼠标
        Thread.sleep(200);
        bu.moveByOffset(400, 2).perform(); // 平行移动鼠标
        Thread.sleep(200);
        bu.moveByOffset(420, 2).perform(); // 平行移动鼠标
        Thread.sleep(1500);
        WebElement btnWebElement = driver.findElement(By.xpath("//button[@class='fm-button fm-submit password-login']"));
        btnWebElement.click();
        System.out.println("登录成功");
    }

}

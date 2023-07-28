package com.tarzan.reptile.demo;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;

/**
 * @author Lenovo
 */
public class SeleniumTest {

    public static void main(String[] args) throws Exception{
            //创建DesiredCapabilities对象，并给出浏览器类型、版本号、平台类型
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities("chrome", "81.0.4044.92", Platform.LINUX);
            //创建ChromeOptions对象,设置启动浏览器相关参数
            ChromeOptions chromeOptions = new ChromeOptions().merge(desiredCapabilities);
            chromeOptions.addArguments("--no-sandbox");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("download.default_directory", "指定下载路径字符串");
            chromeOptions.setExperimentalOption("prefs", hashMap);
            //创建远程driver对象
            WebDriver driver=new RemoteWebDriver(new URL("http://172.16.10.201:4444/wd/hub/"), chromeOptions);
            driver.manage().window().maximize();
            driver.get("http://www.baidu.com");
            System.out.println("打开百度");
            Thread.sleep(2000);
            driver.quit();
    }
}

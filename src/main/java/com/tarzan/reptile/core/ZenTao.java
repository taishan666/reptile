package com.tarzan.reptile.core;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.tarzan.reptile.domain.InfoResult;
import com.tarzan.reptile.domain.Platform;
import com.tarzan.reptile.domain.PlatformResult;
import com.tarzan.reptile.utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

/**
 * Created by tarzan liu on 2018/2/2.
 */
public class ZenTao {
    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath = ZenTao.class.getResource("/chromedriver/chromedriver.exe").getPath();
    private static String targetPath = "http://172.16.10.26:12345/zentao/user-login-L3plbnRhby9teS5odG1s.html";
    private static String searchPath = "http://172.16.10.26:12345/zentao/my-task-assignedTo.html";

    private static WebDriver driver = null;

    private static String username = null;
    private static String password = null;


    static {
        ResourceBundle rb = ResourceBundle.getBundle("zenTao");
        username = rb.getString("zenTao.username");
        password = rb.getString("zenTao.password");
    }

    public static void main(String[] args) {
        crawling();
    }


    public static void crawling() {
        System.setProperty(webDriver, webDriverPath);
        try {
            if (Objects.isNull(driver)){
                driver = new ChromeDriver();
                userLogin(driver);
            }
           // String token = getToken(driver);
              getPlatform(driver);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(driver)) {
                    driver.close();
            }
        }
    }



    /**
     * 获取网页信息
     */
    private static String getPlatform(WebDriver driver) throws Exception {
        Map<String, String> searchNameParams = new HashMap<>();
        searchPath = HttpUtils.setParams(searchPath, searchNameParams);
        driver.get(searchPath);
        Document preDocument = Jsoup.parse(driver.getPageSource());
        Elements preList = preDocument.select("tbody");
        Element e= preList.get(0);
        Elements list=e.select("tr");
        list.forEach(item->{
            String taskCode=  item.select("td").get(0).text();
            String projectName=  item.select("td").get(2).select("a").text();
            String taskName=  item.select("td").get(3).select("a").text();
            String userName=  item.select("td").get(4).text();
            String planHours=  item.select("td").get(7).text();
            String useHours=  item.select("td").get(8).text();
            String status=  item.select("td").get(11).select("span").text();
            System.out.println("taskCode="+taskCode +" projectName= "+projectName+" taskName="+taskName+" userName="+userName+" planHours="+planHours+" useHours="+useHours+" status="+status);
        });

        return null;
    }

    /**
     * 获取token
     */
    private static String getToken(WebDriver driver) throws Exception {
        String current = driver.getCurrentUrl();
        if (StringUtils.isBlank(current)) {
            throw new Exception("获取token链接有误");
        }
        String token = current.split("token=")[1];
        if (StringUtils.isBlank(token)) {
            throw new Exception("token错误");
        }
        return token;
    }

    /**
     * 登录模块
     */
    private static void userLogin(WebDriver driver) throws Exception {
        driver.get(targetPath);

        WebElement usernameWebElement = driver.findElement(By.name("account"));
        usernameWebElement.clear();
        usernameWebElement.sendKeys(username);

        WebElement passwordWebElement = driver.findElement(By.name("password"));
        passwordWebElement.clear();
        passwordWebElement.sendKeys(password);

        WebElement helpWebElement = driver.findElement(By.id("keepLoginon"));
        helpWebElement.click();

        WebElement btnWebElement = driver.findElement(By.id("submit"));
        btnWebElement.click();

        Thread.sleep(1000);

    }
}

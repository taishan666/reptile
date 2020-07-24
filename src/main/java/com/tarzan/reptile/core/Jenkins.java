package com.tarzan.reptile.core;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

/**
 * Jenkins自动化
 *
 * @author liu yapeng
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/7/10$ 16:57$
 * @since JDK1.8
 */
public class Jenkins {
    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="F:\\idea_workspace\\JavaDemo\\chromedriver_win32\\chromedriver.exe";

    private static String targetPath = "http://172.16.10.26:8080/login?from=%2F";

    private static WebDriver driver = null;

    private static String username = "meikuang";
    private static String password = "123456";

    //项目名称（必须和禅道项目名称一致）
    private static String projectName = "煤矿项目";

    //任务名称
    private static String moduleName = "colliery-admin";




    public static void main(String[] args) {
        start();
    }


    public static void start() {
        System.setProperty(webDriver, webDriverPath);
        try {
            if (Objects.isNull(driver)){
                driver = new ChromeDriver();
                userLogin(driver);
            }
            work(driver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(driver)) {
               // driver.close();
            }
        }

    }



    /**
     * 开始工作
     */
    private static String work(WebDriver driver) throws Exception {

        WebElement projectWebElement = driver.findElement(By.xpath("//tr[@id='job_"+projectName+"']/td/a[@class=\"model-link inside\"]"));
        projectWebElement.click();

        WebElement moduleWebElement = driver.findElement(By.xpath("//tr[@id='job_"+moduleName+"']/td/a[@class=\"model-link inside\"]"));
        moduleWebElement.click();

        WebElement taskWebElement = driver.findElement(By.xpath("//div[@id='tasks']/div[@class='task']/a[contains(text(),\"Build Now\")]"));
        taskWebElement.click();



        return null;
    }




    /**
     * 获取token
     */
    private static String getToken(WebDriver driver) throws Exception {
        if (StringUtils.isBlank(driver.getCurrentUrl())) {
            throw new Exception("获取token链接有误");
        }
        String token = driver.getCurrentUrl().split("token=")[1];
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

        WebElement usernameWebElement = driver.findElement(By.name("j_username"));
        usernameWebElement.clear();
        usernameWebElement.sendKeys(username);

        WebElement passwordWebElement = driver.findElement(By.name("j_password"));
        passwordWebElement.clear();
        passwordWebElement.sendKeys(password);

        WebElement btnWebElement = driver.findElement(By.name("Submit"));
        btnWebElement.click();

        Thread.sleep(200);

    }
}

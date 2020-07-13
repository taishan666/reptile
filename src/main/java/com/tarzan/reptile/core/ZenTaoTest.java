package com.tarzan.reptile.core;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.common.collect.Lists;
import com.tarzan.reptile.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

/**
 * 禅达快速创建任务
 * Created by tarzan liu on 2018/2/2.
 */
public class ZenTaoTest {
    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="F:\\idea_workspace\\JavaDemo\\chromedriver_win32\\chromedriver.exe";
    private static String targetPath = "http://172.16.10.26:12345/zentao/user-login-L3plbnRhby9teS5odG1s.html";
    private static String searchPath = "http://172.16.10.26:12345/zentao/project-index.html";

    private static WebDriver driver = null;

    private static String username = null;
    private static String password = null;

    //项目名称（必须和禅道项目名称一致）
    private static String projectName = "智能矿山项目";

    //任务名称
    private static String taskName = "历史警报数据-增加测点模糊搜索";

    //任务类型 （必须和禅道任务类型一致）
    private static String taskType = "开发";

    //任务指派人（L首个汉字的拼音首字母大写）
    private static String  people = "L:刘亚鹏";

    //预估时长
    private static String  estimate = "2";


    static {
        ResourceBundle rb = ResourceBundle.getBundle("zenTao");
        username = rb.getString("zenTao.username");
        password = rb.getString("zenTao.password");
    }

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
                    driver.close();
                }
            }

    }



    /**
     * 开始工作
     */
    private static String work(WebDriver driver) throws Exception {
        Map<String, String> searchNameParams = new HashMap<>();
        searchPath = HttpUtils.setParams(searchPath, searchNameParams);
        driver.get(searchPath);

        WebElement taskButtonWebElement = driver.findElement(By.xpath("//li[@data-id='task']/a"));
        taskButtonWebElement.click();

        WebElement createWebElement = driver.findElement(By.xpath("//div[@id='mainMenu']/div[@class='btn-toolbar pull-right']/a[@class='btn btn-primary']"));
        createWebElement.click();

        WebElement projectWebElement = driver.findElement(By.xpath("//*[@id='project_chosen']/a"));
        projectWebElement.click();
        Thread.sleep(200);//等待0.2秒
        projectWebElement.findElement(By.xpath("//li[@title='"+projectName+"']")).click();

        WebElement typeWebElement = driver.findElement(By.xpath("//*[@id='type_chosen']/a"));
        typeWebElement.click();
        Thread.sleep(200);//等待0.2秒
        typeWebElement.findElement(By.xpath("//li[@title='"+taskType+"']")).click();

        WebElement peopleWebElement = driver.findElement(By.xpath("//*[@id='assignedTo_chosen']/a"));
        peopleWebElement.click();
        Thread.sleep(200);//等待0.2秒
        peopleWebElement.findElement(By.xpath("//li[@title='"+people+"']")).click();

        WebElement taskWebElement = driver.findElement(By.name("name"));
        taskWebElement.clear();
        taskWebElement.sendKeys(taskName);

        WebElement estimateWebElement = driver.findElement(By.name("estimate"));
        estimateWebElement.clear();
        estimateWebElement.sendKeys(estimate);

        WebElement submitWebElement = driver.findElement(By.id("submit"));
        submitWebElement.click();

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

        Thread.sleep(200);

    }
}

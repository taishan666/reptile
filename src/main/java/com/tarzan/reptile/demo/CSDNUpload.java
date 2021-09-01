package com.tarzan.reptile.demo;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class CSDNUpload {
    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\work_space\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";

    //登录地址
    private static String loginUrl = "https://passport.csdn.net/login";

    //获取文章列表接口
    private static String moreBlogsApi = "https://blog.csdn.net/api/articles?type=more&category=ops&shown_offset=0";
    //用户状态检测接口
    private static String checkUserApi = "https://passport.csdn.net/v1/api/check/userstatus";
    //评论接口
    private static String commentBlogApi = "https://blog.csdn.net/phoenix/web/v1/comment/submit";

    //评论列表
    private static String commentListApi = "https://blog.csdn.net/phoenix/web/v1/comment/list/";

    private static WebDriver driver = null;

    private static String username;
    private static String password;

    private static List<String> commentList= Lists.newArrayList();

    private static StringBuffer buffer=new StringBuffer();
    private static  String cookieStr="";



    public static void main(String[] args)  {
        start();
    }




    public static void start() {
        System.setProperty(webDriver, webDriverPath);
        try {
            if (Objects.isNull(driver)){
                // ChromeOptions
                ChromeOptions chromeOptions = new ChromeOptions();
                // 设置后台静默模式启动浏览器
                //   chromeOptions.addArguments("--headless");
                //启动浏览器
                driver = new ChromeDriver(chromeOptions);
                //登录
                userLogin(driver);
            }
            while (true){
                commentBlog(driver);
                Thread.sleep(1000);
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
     * 评论博文
     *
     * @throws Exception
     */
    public static void commentBlog(WebDriver driver)  {
        driver.get("https://mp.csdn.net/mp_download/creation/uploadResources");
        driver.findElement(By.name("file")).sendKeys("C:\\Users\\liuya\\Downloads\\tetris.png");
    }





    /**
     * 登录csdn页面,评论当然需要登录了
     *
     * @throws Exception
     */
    public static void userLogin(WebDriver driver) throws InterruptedException {

        driver.get(loginUrl);
        Thread.sleep(1000);

        WebElement element = driver.findElement(By.xpath("//div[@class='main-select']/ul/li[1]/a"));
        element.click();
        System.out.println("等待微信扫码。。。。。。。。。。");
        Thread.sleep(15000);

        Set<Cookie> cookies =driver.manage().getCookies();
        cookies.forEach(e->{
            cookieStr=cookieStr+e.getName()+"="+e.getValue()+";";
        });

    }

}

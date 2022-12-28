package com.tarzan.reptile.demo;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.*;

public class CSDNVote {
    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\work_space\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";

    //登录地址
    private static String loginUrl = "https://passport.csdn.net/login";

    //获取文章列表接口
    private static String voteUrl = "https://bbs.csdn.net/forums/blogstar2022";


    private static WebDriver driver = null;

    private static String username;
    private static String password;

    private static List<String> commentList= Lists.newArrayList();

    private static StringBuffer buffer=new StringBuffer();
    private static  String cookieStr="";


    static {
        ResourceBundle rb = ResourceBundle.getBundle("csdn");
        username = rb.getString("csdn.username");
        password = rb.getString("csdn.password");
        commentList.add("9级原力，5星已投，诚信支持，诚信互助：\n" +
                "https://bbs.csdn.net/topics/611391339\n" +
                "五星好评互评 欢迎更多博主来撩**");
    }


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
                //添加用户cookies
                chromeOptions.addArguments("--user-data-dir=C:\\Users\\Lenovo\\AppData\\Local\\Google\\Chrome\\User Data1");
                //启动浏览器
                driver = new ChromeDriver(chromeOptions);
                //登录
             //   userLogin(driver);
            }
            voteUrl(driver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(driver)) {
                //  driver.close();
            }
        }

    }



    @Data
    static class Blog{
        private String  title;
        private String  url;
        private String product_type;
        private String product_id;
    }



    /**
     * 评论博文
     *
     * @throws Exception
     */
    public static void voteUrl(WebDriver driver)  {
        int num=611391339;
        for (int i = 0; i <10000; i++) {
            commentOneBlogUrl("https://bbs.csdn.net/topics/"+num);
            num++;
        }
    }


    public static void commentOneBlogUrl(String voteSite)  {
            try {
                driver.get(voteSite);
                String html= driver.getPageSource();
               if(html.contains("2022年「博客之星」参赛博主")){
                 String name =driver.findElement(By.className("name")).getText();
                   System.out.println(name);
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(1000,5000)));
                   List<WebElement> offStarsEle = driver.findElements(By.xpath("//span[@class='el-rate__item']/i[@class='el-rate__icon el-icon-star-off']"));
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(1000,5000)));
                   int starNum=5;
                   if(CollectionUtils.isNotEmpty(offStarsEle)&&offStarsEle.size()>=starNum){
                       WebElement offFiveStar=offStarsEle.get(starNum-1);
                       offFiveStar.click();
                       System.out.println("网址 "+voteSite+" 已投票"+starNum+"星");
                   }
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(1000,5000)));
                   WebElement commentPlugin=driver.findElement(By.className("comment-plugin"));
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(1000,5000)));
                   commentPlugin.isEnabled();
                   commentPlugin.click();
                   WebElement textarea=driver.findElement(By.tagName("textarea"));
                   String comment=RandomUtil.randomEle(commentList);
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(1000,5000)));
                   textarea.sendKeys(comment);
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(1000,5000)));
                   WebElement replayBtm=driver.findElement(By.className("replay-btm"));
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(1000,5000)));
                   replayBtm.click();
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(1000,5000)));
                   System.out.println("已经评论 ： "+comment);
               }else{
                   System.out.println("普通帖子");
               }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
    }

}

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
import java.util.concurrent.TimeUnit;

public class CSDNVote {
    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\work_space\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";

    //登录地址
    private static String loginUrl = "https://passport.csdn.net/login";

    //获取文章列表接口
    private static String voteUrl = "https://bbs.csdn.net/topics/603955514";

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


    static {
        ResourceBundle rb = ResourceBundle.getBundle("csdn");
        username = rb.getString("csdn.username");
        password = rb.getString("csdn.password");
        commentList.add("**2021年「博客之星」参赛博主：洛阳泰山\n" +
                "PC端 https://blog.csdn.net/weixin_40986713 手机端：https://bbs.csdn.net/topics/603955514\n" +
                "五星好评互评 原力值满级 只赚不亏 欢迎加博主好友来撩**");
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
                //启动浏览器
                driver = new ChromeDriver(chromeOptions);
                //登录
                userLogin(driver);
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
        int num=603956368;
        for (int i = 0; i <10000; i++) {
            commentOneBlogUrl("https://bbs.csdn.net/topics/"+num);
            num++;
        }
    }


    public static void commentOneBlogUrl(String voteSite)  {
            try {
                driver.get(voteSite);
                String html= driver.getPageSource();
               if(html.contains("2021年「博客之星」参赛博主")){
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(5000,10000)));
                   List<WebElement> starsEle = driver.findElements(By.xpath("//span[@class='el-rate__item']/i[@class='el-rate__icon el-icon-star-off']"));
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(5000,10000)));
                   int starNum=4;
                   if(CollectionUtils.isNotEmpty(starsEle)&&starsEle.size()>=starNum){
                       WebElement fiveStar=starsEle.get(starNum-1);
                       fiveStar.click();
                       System.out.println("网址 "+voteSite+" 已投票"+starNum+"星");
                   }
                 /*  Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(5000,10000)));
                   WebElement helpWebElement = driver.findElement(By.xpath("//div[@class='comment-plugin']"));
                   helpWebElement.click();
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(5000,10000)));
                   WebElement textWebElement = driver.findElement(By.xpath("//div[@class='md_textarea isFocus']/textarea"));
                   String comment=RandomUtil.randomEle(commentList);
                   textWebElement.sendKeys(comment);
                   System.out.println("网址 "+voteSite+" 评论内容："+comment);
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(5000,10000)));
                   WebElement submit = driver.findElement(By.xpath("//div[@class='replay-btm']"));
                   submit.click();
                   Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(5000,10000)));*/
               }else{
                   System.out.println("普通帖子");
               }
              //  Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(5000,10000)));
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
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

  /*      WebElement usernameWebElement = driver.findElement(By.id("all"));
        usernameWebElement.sendKeys(username);
        Thread.sleep(1000);

        WebElement passwordWebElement = driver.findElement(By.id("password-number"));
        passwordWebElement.sendKeys(password);
        Thread.sleep(1000);


        WebElement btnWebElement = driver.findElement(By.xpath("//button[@data-type='account']"));
        btnWebElement.click();
        Thread.sleep(1000);
*/
        Set<Cookie> cookies =driver.manage().getCookies();
        cookies.forEach(e->{
            cookieStr=cookieStr+e.getName()+"="+e.getValue()+";";
        });

    }


}

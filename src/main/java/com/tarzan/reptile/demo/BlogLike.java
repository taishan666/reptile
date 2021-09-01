package com.tarzan.reptile.demo;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

/**
 *
 *  csdn自动点赞
 * @version 1.0
 * @since JDK1.8
 * @author tarzan Liu
 * @date 2021年03月10日 17:25:05
 */
public class BlogLike{

    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\work_space\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";

    //登录地址
    private static String loginUrl = "https://passport.csdn.net/login";

    //获取文章列表接口
    private static String moreBlogsApi = "https://blog.csdn.net/api/articles?type=more&category=other&shown_offset=0";

    //评论接口
    private static String likeBlogApi = "https://blog.csdn.net//phoenix/web/v1/article/like";

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
    }


    public static void main(String[] args) throws IOException {
        start();
    }


    public static void start() {
        System.setProperty(webDriver, webDriverPath);
        try {
            if (Objects.isNull(driver)){
                // ChromeOptions
                ChromeOptions chromeOptions = new ChromeOptions();
                // 设置后台静默模式启动浏览器
                chromeOptions.addArguments("--headless");
                //后台运行模式
                driver = new ChromeDriver(chromeOptions);
                userLogin(driver);
            }
            while (true){
                likeBlog(driver);
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
     * 获得更多博文
     *
     * @throws Exception
     */
    public static List<Blog> getMoreBlogs(String url) {
        List<Blog> blogList= Lists.newArrayList();
        String body=HttpUtil.createGet(url).header("Cookie",cookieStr).execute().body();
        JSONObject json=JSONObject.parseObject(body);
        if("true".equals(json.getString("status"))){
            JSONArray array=json.getJSONArray("articles");
            blogList=array.toJavaList(Blog.class);
        }
        return  blogList;
    }

    @Data
    static class Blog{
        private String  title;
        private String  url;
        private String product_type;
        private String product_id;
    }

    /**
     * 点赞博文
     *
     * @throws Exception
     */
    public static void likeBlog(WebDriver driver) throws InterruptedException {
        List<Blog> blogList=getMoreBlogs(moreBlogsApi);
        //文章去重请求
        blogList.forEach(e->{
            buffer.append("article_id[]="+e.getProduct_id()+"&");
        });
        HttpUtil.createPost("https://blog.csdn.net/api/ArticleDigg/isDiggList").header("Cookie",cookieStr).body(buffer.toString()).execute().body();
        //写入文章评论
        likeOneBlogUrl(likeBlogApi,blogList);
    }

    /**
     * 点赞单个博文
     *
     * @throws Exception
     */
    public static void likeOneBlogUrl(String url, List<Blog> titleLinks)  {
        Set<Cookie> cookies =driver.manage().getCookies();
        cookieStr="";
        cookies.forEach(e->{
            cookieStr=cookieStr+e.getName()+"="+e.getValue()+";";
        });
        titleLinks.forEach(e->{
            try {
                String param="articleId="+e.getProduct_id();
                System.out.println("文章《"+e.getTitle()+"》网址 "+e.getUrl());
                String result= HttpUtil.createPost(url).header("Cookie",cookieStr).body(param).execute().body();
                System.out.println(result);
                Thread.sleep(30000);//10秒评论一次
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }

    /**
     * 登录csdn页面,评论当然需要登录了
     *
     * @throws Exception
     */
    public static void userLogin(WebDriver driver) throws InterruptedException {

        driver.get(loginUrl);
        Thread.sleep(1000);

        WebElement element = driver.findElement(By.xpath("//div[@class='main-select']/ul/li[2]/a"));
        element.click();
        Thread.sleep(1000);

        WebElement usernameWebElement = driver.findElement(By.id("all"));
        usernameWebElement.sendKeys(username);
        Thread.sleep(1000);

        WebElement passwordWebElement = driver.findElement(By.id("password-number"));
        passwordWebElement.sendKeys(password);
        Thread.sleep(1000);


        WebElement btnWebElement = driver.findElement(By.xpath("//button[@data-type='account']"));
        btnWebElement.click();
        Thread.sleep(1000);

        Set<Cookie> cookies =driver.manage().getCookies();
        cookies.forEach(e->{
            cookieStr=cookieStr+e.getName()+"="+e.getValue()+";";
        });

    }


}

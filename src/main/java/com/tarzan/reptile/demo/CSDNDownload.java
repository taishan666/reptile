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

public class CSDNDownload {
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

    private static String likeBlogApi = "https://blog.csdn.net//phoenix/web/v1/article/like";

    private static WebDriver driver = null;

    private static String username;
    private static String password;

    private static List<String> commentList= Lists.newArrayList();

    private static StringBuffer buffer=new StringBuffer();
    private static  String cookieStr="";


    static {
        commentList.add("技术牛逼，文章出奇，文采飞扬，愿与君共勉！！！");

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
                //添加用户cookies数据
                chromeOptions.addArguments("--user-data-dir=C:\\Users\\liuya\\AppData\\Local\\Google\\Chrome\\User Data1");
                //启动浏览器
                driver = new ChromeDriver(chromeOptions);
                driver.get("https://blog.csdn.net/");
                //登录
               // userLogin(driver);
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
     * 获得更多博文
     *
     * @throws Exception
     */
    public static List<BlogComment.Blog> getMoreBlogs(String url) {
        List<BlogComment.Blog> blogList= Lists.newArrayList();
        String body= HttpUtil.createGet(url).header("Cookie",cookieStr).execute().body();
        JSONObject json=JSONObject.parseObject(body);
        if("true".equals(json.getString("status"))){
            JSONArray array=json.getJSONArray("articles");
            blogList=array.toJavaList(BlogComment.Blog.class);
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
     * 评论博文
     *
     * @throws Exception
     */
    public static void commentBlog(WebDriver driver)  {
        List<BlogComment.Blog> blogList=getMoreBlogs(moreBlogsApi);
        //文章去重请求
        blogList.forEach(e->{
            buffer.append("article_id[]="+e.getProduct_id()+"&");
        });
        HttpUtil.createPost("https://blog.csdn.net/api/ArticleDigg/isDiggList").header("Cookie",cookieStr).body(buffer.toString()).execute().body();
        //获取用户id
        String  UserName=driver.manage().getCookieNamed("UserName").getValue();
        //过滤评论过的问斩
        List<BlogComment.Blog> unCommentBlogs=isCommentBlog(commentListApi,blogList,UserName);
        if(CollectionUtils.isNotEmpty(unCommentBlogs)){
            //写入文章评论
            commentOneBlogUrl(unCommentBlogs,UserName);
        }
    }
    public static void commentOneBlogUrl(List<BlogComment.Blog> titleLinks,String  UserName)  {
        for (BlogComment.Blog e : titleLinks) {
            try {
            driver.get(e.getUrl());
                Thread.sleep(2000);
             /*   driver.findElement(By.id("comment_content")).click();
                Thread.sleep(1000);*/
                likeOneBlogUrl(e);
            WebElement helpWebElement = driver.findElement(By.id("comment_content"));
          //  helpWebElement.click();
            String comment=RandomUtil.randomEle(commentList);
            helpWebElement.sendKeys(comment);
                System.out.println("文章《"+e.getTitle()+"》网址 "+e.getUrl()+" 评论内容："+comment);
                Thread.sleep(RandomUtil.randomEle(Lists.newArrayList(2000,3000,4000,5000)));
            WebElement submit = driver.findElement(By.xpath("//input[@class='btn-comment btn-comment-input']"));
            if(submit==null){
                submit = driver.findElement(By.xpath("//input[@class='btn-comment btn-comment-input']"));
            }
            submit.click();
            Thread.sleep(30000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    /**
     * 点赞单个博文
     *
     * @throws Exception
     */
    public static void likeOneBlogUrl(BlogComment.Blog blog)  {
        Set<Cookie> cookies =driver.manage().getCookies();
        cookieStr="";
        cookies.forEach(e->{
            cookieStr=cookieStr+e.getName()+"="+e.getValue()+";";
        });
            try {
                String param="articleId="+blog.getProduct_id();
                String result= HttpUtil.createPost(likeBlogApi).header("Cookie",cookieStr).body(param).execute().body();
                System.out.println(result);
                JSONObject json=JSONObject.parseObject(result);
                String message=json.getString("message");
                if(!"success".equals(message)){
                    System.exit(0);
                }
                Thread.sleep(30000);//10秒评论一次
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
    }


    /**
     * 提交单个评论
     *
     */
    public static void commentOneBlogUrl(String url, BlogComment.Blog blog, String  UserName)  {
        Set<Cookie> cookies =driver.manage().getCookies();
        cookieStr="";
        cookies.forEach(e->{
            cookieStr=cookieStr+e.getName()+"="+e.getValue()+";";
        });
            try {
                String comment= RandomUtil.randomEle(commentList);
                String checkParam="{\"username\":\"weixin_40986713\"}";
                String param="commentId=&content="+comment+"&articleId="+blog.getProduct_id();
                String body= HttpUtil.createPost(checkUserApi).header("Cookie",cookieStr).body(checkParam).execute().body();
                System.out.println("body="+body);
                String result= HttpUtil.createPost(url).header("Cookie",cookieStr).body(param).execute().body();
                System.out.println("文章《"+blog.getTitle()+"》网址 "+blog.getUrl()+" 评论内容："+comment);
                System.out.println(result);
                JSONObject resultJson=JSONObject.parseObject(result);
                if(resultJson.getInteger("code")!=200){
                    //System.exit(1);
                    Thread.sleep(60*60*1000);//休息1个小时
                }
                Thread.sleep(45000);//10秒评论一次
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                System.exit(0);
            }
    }

    //过滤已经评论过的文章
    public static List<BlogComment.Blog> isCommentBlog(String url, List<BlogComment.Blog> blogList, String username)  {
        List<BlogComment.Blog> unCommentBlogs= Lists.newArrayList();
        blogList.forEach(e->{
            if(!isCommentOneBlog(url+e.getProduct_id(),username)){
                unCommentBlogs.add(e);
            }
        });
        return  unCommentBlogs;
    }



    //检查是否评论过
    public static boolean isCommentOneBlog(String url,String username)  {
        List<String> userNames= Lists.newArrayList();
        String param="page=1&size=10&commentId=";
        String result= HttpUtil.createPost(url).header("Cookie",cookieStr).body(param).execute().body();
        JSONObject resultJson=JSONObject.parseObject(result);
        Integer pages=resultJson.getJSONObject("data").getInteger("pageCount");
        for (int i = 0; i < pages; i++) {
            param="page="+(i+1)+"&size=10&commentId=";
            result= HttpUtil.createPost(url).header("Cookie",cookieStr).body(param).execute().body();
            JSONArray array= resultJson.getJSONObject("data").getJSONArray("list");
            for (int j = 0; j < array.size(); j++) {
                String userName=array.getJSONObject(j).getJSONObject("info").getString("userName");
                userNames.add(userName);
            }
        }
        return userNames.contains(username);
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

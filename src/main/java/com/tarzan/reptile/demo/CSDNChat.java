package com.tarzan.reptile.demo;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.*;

/**
 *
 *  csdn 聊天消息
 * @version 1.0
 * @since JDK1.8
 * @author tarzan Liu
 * @company 北京华夏天信研究院
 * @date 2021年03月10日 17:25:45
 */
public class CSDNChat {

        private static String webDriver = "webdriver.chrome.driver";
        private static String webDriverPath ="E:\\project\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";

        //登录地址
        private static String loginUrl = "https://passport.csdn.net/login";

        //获取文章列表接口
        private static String moreBlogsApi = "https://blog.csdn.net/api/articles?type=more&category=other&shown_offset=0";

        //消息接口
        private static String messageApi = "https://msg.csdn.net/v1/im/send/message";

        private static WebDriver driver = null;

        private static String username;
        private static String password;

        private static List<String> commentList= Lists.newArrayList();

        private static StringBuffer buffer=new StringBuffer();
        private static  String cookieStr="";

        //私信内容
        private static  String  messageInfo="[奸笑]I find you!!!";

    static {
        ResourceBundle rb = ResourceBundle.getBundle("csdn");
        username = rb.getString("csdn.username");
        password = rb.getString("csdn.password");
    }


    public static void main(String[] args){
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
                    privateChats();
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
            String body= HttpUtil.createGet(url).header("Cookie",cookieStr).execute().body();
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
            private String  user_name;
            private String  product_type;
            private String  product_id;
        }

        /**
         * 私信
         *
         * @throws Exception
         */
        public static void privateChats()  {
            List<Blog> blogList=getMoreBlogs(moreBlogsApi);
            //文章去重请求
            blogList.forEach(e->{
                buffer.append("article_id[]="+e.getProduct_id()+"&");
            });
            HttpUtil.createPost("https://blog.csdn.net/api/ArticleDigg/isDiggList").header("Cookie",cookieStr).body(buffer.toString()).execute().body();
            //发送消息
            sendMessage(messageApi,blogList);
        }

        /**
         * 发送消息
         *
         * @throws Exception
         */
        public static void sendMessage(String url, List<Blog> blogList)  {
            String  deviceId=driver.manage().getCookieNamed("uuid_tt_dd").getValue();
            String  UserName=driver.manage().getCookieNamed("UserName").getValue();
            Set<Cookie> cookies =driver.manage().getCookies();
            cookieStr="";
            cookies.forEach(e->{
                cookieStr=cookieStr+e.getName()+"="+e.getValue()+";";
            });
            blogList.forEach(e->{
                if(!e.getUser_name().equals(UserName)){
                    try {
                        Map<String,Object> param= Maps.newConcurrentMap();
                        param.put("toUsername",e.getUser_name());
                        param.put("messageType",0);
                        param.put("messageBody",messageInfo);
                        param.put("fromClientType","WEB");
                        param.put("fromDeviceId",deviceId);
                        param.put("appId","CSDN-PC");
                        String result= HttpUtil.createPost(url).header("Cookie",cookieStr).form(param).execute().body();
                        System.out.println("给用户"+e.getUser_name()+"发送消息"+messageInfo);
                        System.out.println(result);
                        Thread.sleep(10000);//10秒发送一次
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
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

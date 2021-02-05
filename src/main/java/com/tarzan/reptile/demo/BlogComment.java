package com.tarzan.reptile.demo;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BlogComment {

    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\project\\reptile\\target\\classes\\chromedriver\\chromedriver.exe";

    private static String loginUrl = "https://passport.csdn.net/login";

    private static String websiteUrl = "https://blog.csdn.net";

    private static String moreBlogsApi = "https://blog.csdn.net/api/articles?type=more&category=java&shown_offset=0";

    private static WebDriver driver = null;

    private static String username = "18838811955";
    private static String password = "A1334512682";

    private static List<String> commentList= Lists.newArrayList();

    private static StringBuffer buffer=new StringBuffer();
    private static  String cookie="";



    static {
        try {
            Map<String, String>    cookies = Jsoup.connect(websiteUrl).execute().cookies();
            for(String key : cookies.keySet()){
                String value = cookies.get(key);
                cookie=cookie+key+"="+value+";";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        commentList.add("学会了，感谢大佬分享");
        commentList.add("你TND真是个天才！！");
        commentList.add("赞赞赞！给你赞！！！");
        commentList.add("学习了，感谢分享！");
        commentList.add("谢谢分享，大佬的文章让我受益颇多！");
        commentList.add("写的好，很nice!欢迎一起交流!");
        commentList.add("这写的什么啊你这样的人我不想多说什么， 直接一键三连等我水平够了再回来看，留下“牛X”二字");
        commentList.add("好文，鉴定完毕！");
        commentList.add("666，反手就是一个赞，欢迎回赞哦~");
        commentList.add("慕名而来，大佬你真的惊艳到我了！要是再能受到大佬的回访，那就太好了");
        commentList.add("牛蛙牛蛙，以后跟着大佬学习");
        commentList.add("忍不住就是一个赞，写得很棒，欢迎回赞哦~");
        commentList.add("文采四溢，大佬这是被耽搁的文学家啊!");
        commentList.add("学习的道路上一起进步，也期待你的关注与支持！");
        commentList.add("放弃不难，但坚持一定很酷！");
        commentList.add("给大佬递茶，望有空互粉互访点赞!");
        commentList.add("帮助很大，拒绝白嫖，点赞评论留个在此一游");
        commentList.add("代码之路任重道远，愿跟博主努力习之。");
        commentList.add("干货满满，很详细，评论占个坑。欢迎一起交流!");
        commentList.add("在最美的年华，做最好的自己，加油！");
        commentList.add("我真是服了，像你这种人就是欠赞和关注，哼~");
        commentList.add("看完大佬的文章，我的心情竟是久久不能平静。正如老子所云：大音希声，大象无形。我现在终于明白我缺乏的是什么了。");
        commentList.add("由浅入深，适合有基础的技术人员。大佬可否给小弟回个赞感谢万分");
        commentList.add("君之妙笔，令鄙不及，佩服佩服 ，如若能给小弟回个赞，必将感激涕尽,哇，好棒啊，崇拜的小眼神，欢迎回赞，回评哦~~~");
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
             //   driver = new ChromeDriver();
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
     * 获得更多博文
     *
     * @throws Exception
     */
    public static List<Blog> getMoreBlogs(String url) throws InterruptedException {
        List<Blog> blogList= Lists.newArrayList();
        String body=HttpUtil.createGet(url).header("Cookie",cookie).execute().body();
        Thread.sleep(1000);
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
     * 评论博文
     *
     * @throws Exception
     */
    public static void commentBlog(WebDriver driver) throws InterruptedException {
        List<Blog> blogList=getMoreBlogs(moreBlogsApi);
        //文章去重请求(暂不需要)
/*        blogList.forEach(e->{
            buffer.append("article_id[]="+e.getProduct_id()+"&");
        });
       HttpUtil.post("https://blog.csdn.net/api/ArticleDigg/isDiggList",buffer.toString());*/
        //写入文章评论
       commentOneBlog(driver,blogList);
    }

    /**
     * 提交单个评论
     *
     * @throws Exception
     */
    public static void commentOneBlog(WebDriver driver, List<Blog> titleLinks) throws InterruptedException {
        titleLinks.forEach(e->{
            try {
                driver.get(e.getUrl());
                WebElement element = driver.findElement(By.xpath("//form[@id='commentform']/textarea"));
                String comment=RandomUtil.randomEle(commentList);
                element.sendKeys(comment);
                System.out.println("文章《"+e.getTitle()+"》网址 "+e.getUrl()+" 评论内容："+comment);
                Thread.sleep(1000);
                WebElement submit = driver.findElement(By.xpath("//input[@class='btn btn-sm btn-comment']"));
                submit.click();
                Thread.sleep(30000);//一分钟评论一次
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


    }


}

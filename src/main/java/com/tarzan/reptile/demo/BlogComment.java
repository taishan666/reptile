package com.tarzan.reptile.demo;

import cn.hutool.core.util.RandomUtil;
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

public class BlogComment{

    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\project\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";

    //登录地址
    private static String loginUrl = "https://passport.csdn.net/login";

    //获取文章列表接口
    private static String moreBlogsApi = "https://blog.csdn.net/api/articles?type=more&category=db&shown_offset=0";

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

        commentList.add("话不多说，我先赞为敬！！！");
        commentList.add("学会了，感谢大佬分享，继续努力！");
        commentList.add("赞赞赞，你真是个小天才！！");
        commentList.add("我一般不评论的，看到这篇文章了，我忍不住想对你说，真的写的太赞了。");
        commentList.add("学习了，感谢您的分享，让我受益良多！");
        commentList.add("谢谢分享，大佬的文章让我受益颇多！");
        commentList.add("写的好，很nice!欢迎一起交流!");
        commentList.add("这写的什么啊你这样的人我不想多说什么， 直接一键三连等我水平够了再回来看，留下“牛X”二字");
        commentList.add("不错的好文，鉴定完毕！");
        commentList.add("666，反手就是一个赞，欢迎回赞哦~");
        commentList.add("慕名而来，大佬你真的惊艳到我了！要是再能受到大佬的回访，那就太好了");
        commentList.add("牛蛙牛蛙，以后跟着大佬学习");
        commentList.add("忍不住就是一个赞，写得很棒，欢迎回赞哦~");
        commentList.add("文采四溢，大佬这是被耽搁的文学家啊!");
        commentList.add("学习的道路上一起进步，也期待你的关注与支持！");
        commentList.add("放弃不难，但坚持一定很酷,加油，奥里给！");
        commentList.add("给大佬递茶，翻起我牌，到我博客空间逛逛!");
        commentList.add("帮助很大，拒绝白嫖，点赞评论留个在此一游");
        commentList.add("代码之路任重道远，愿跟博主努力习之。");
        commentList.add("干货满满，很详细，评论占个坑。欢迎一起交流!");
        commentList.add("在最美的年华，做最好的自己，加油！");
        commentList.add("我真是服了，像你这种人就是欠赞和关注，哼~");
        commentList.add("看完大佬的文章，我的心情竟是久久不能平静。正如老子所云：大音希声，大象无形。我现在终于明白我缺乏的是什么了。");
        commentList.add("由浅入深，适合有基础的技术人员。大佬可否给小弟回个赞感谢万分");
        commentList.add("君之妙笔，令鄙不及，佩服佩服 ，如若能给小弟回个赞，必将感激涕尽,哇，好棒啊，崇拜的小眼神，欢迎回赞，回评哦~~~");
        commentList.add("满满的干货，我嗅到的知识的芬芳！");
        commentList.add("写的挺不错的，继续加油哦！ 最近我也在学习写博客,有空来看看我呀，一起互相学习。期待你的关注与支持");
        commentList.add("这篇文章对我很有帮助！作为一名java开发程序员，想和你一起交流自己的困惑和见解，可以到我主页留言私信一起交流吧！");
        commentList.add("给大佬递茶，最近也在学习相关知识，希望得到大佬的肯定和支持!");
        commentList.add("创作不易，给你打气,继续创作优质好文！");
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
     * 评论博文
     *
     * @throws Exception
     */
    public static void commentBlog(WebDriver driver)  {
        List<Blog> blogList=getMoreBlogs(moreBlogsApi);
        //文章去重请求
        blogList.forEach(e->{
            buffer.append("article_id[]="+e.getProduct_id()+"&");
        });
        HttpUtil.createPost("https://blog.csdn.net/api/ArticleDigg/isDiggList").header("Cookie",cookieStr).body(buffer.toString()).execute().body();
         //获取用户id
         String  UserName=driver.manage().getCookieNamed("UserName").getValue();
         //过滤评论过的问斩
        List<Blog> unCommentBlogs=isCommentBlog(commentListApi,blogList,UserName);
        //写入文章评论
         commentOneBlogUrl(commentBlogApi,unCommentBlogs);
    }

    /**
     * 提交单个评论
     *
     */
    public static void commentOneBlogUrl(String url, List<Blog> titleLinks)  {
        Set<Cookie> cookies =driver.manage().getCookies();
        cookieStr="";
        cookies.forEach(e->{
            cookieStr=cookieStr+e.getName()+"="+e.getValue()+";";
        });
        titleLinks.forEach(e->{
            try {
            String comment=RandomUtil.randomEle(commentList);
            String param="commentId=&content="+comment+"&articleId="+e.getProduct_id();
            String result= HttpUtil.createPost(url).header("Cookie",cookieStr).body(param).execute().body();
            System.out.println("文章《"+e.getTitle()+"》网址 "+e.getUrl()+" 评论内容："+comment);
            //todo code 做判断
            System.out.println(result);
            JSONObject resultJson=JSONObject.parseObject(result);
            if(resultJson.getInteger("code")!=200){
               //System.exit(1);
                Thread.sleep(30*60*1000);//休息半个小时
            }
            Thread.sleep(45000);//10秒评论一次
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                System.exit(0);
            }
        });
    }

    //过滤已经评论过的文章
    public static List<Blog> isCommentBlog(String url, List<Blog> blogList,String username)  {
        List<Blog> unCommentBlogs= Lists.newArrayList();
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

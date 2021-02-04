package com.tarzan.reptile.demo;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.compress.utils.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Objects;

public class BlogComment {

    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\project\\reptile\\target\\classes\\chromedriver\\chromedriver.exe";

    private static String targetPath = "https://passport.csdn.net/login";

    private static WebDriver driver = null;

    private static String username = "yourAccount";
    private static String password = "yourPassword";

    private static List<String> commentList= Lists.newArrayList();

    static {
        commentList.add("学会了，感谢大佬分享");
        commentList.add("你TND真是个天才！！");
        commentList.add("赞赞赞！给你赞！！！");
        commentList.add("学习了，感谢分享！");
        commentList.add("谢谢分享，很有用！");
        commentList.add("写的好，很nice!欢迎一起交流!");
        commentList.add("这写的什么啊你这样的人我不想多说什么， 直接一键三连等我水平够了再回来看，留下“牛X”二字");
        commentList.add("好文，鉴定完毕！");
        commentList.add("666，反手就是一个赞，欢迎回赞哦~");
        commentList.add("慕名而来，大佬你真的惊艳到我了！要是再能受到大佬的回访，那就太好了");
        commentList.add("牛蛙牛蛙，以后跟着大佬学习");
        commentList.add("忍不住就是一个赞，写得很棒，欢迎回赞哦~");
    }


    public static void main(String[] args) {
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
              //  driver = new ChromeDriver(chromeOptions);
                driver = new ChromeDriver();
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
    public static void commentBlog(WebDriver driver) throws InterruptedException {
        driver.get("https://blog.csdn.net/nav/java");
        Thread.sleep(1000);
        List<String> titleLinks= Lists.newArrayList();
        List<WebElement> elements = driver.findElements(By.xpath("//ul[@id='feedlist_id']/li/div/div/h2/a"));
        for (int i = 0; i < elements.size(); i++) {
            String link=  elements.get(i).getAttribute("href");
            titleLinks.add(link);
        }
        commentOneBlog(driver,titleLinks);
    }

    /**
     * 提交单个评论
     *
     * @throws Exception
     */
    public static void commentOneBlog(WebDriver driver, List<String> titleLinks) throws InterruptedException {
        titleLinks.forEach(url->{
            try {
                driver.get(url);
                WebElement element = driver.findElement(By.xpath("//form[@id='commentform']/textarea"));
                element.sendKeys(RandomUtil.randomEle(commentList));
                Thread.sleep(1000);
                WebElement submit = driver.findElement(By.xpath("//input[@class='btn btn-sm btn-comment']"));
                submit.click();
                Thread.sleep(60000);//一分钟评论一次
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

        driver.get(targetPath);
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

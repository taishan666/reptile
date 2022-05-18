package com.tarzan.reptile.demo;


import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章采集
 * @author tarzan
 * @date 2021/5/31
 */
public class ArticleCollect {

    //网站地址
    private static String webUrl="https://blog.csdn.net/weixin_40986713";

    private static String webDriver = "webdriver.chrome.driver";
    private static String webDriverPath ="E:\\work_space\\reptile\\src\\main\\resources\\chromedriver\\chromedriver.exe";
    private static WebDriver driver = null;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty(webDriver, webDriverPath);
        // ChromeOptions
        ChromeOptions chromeOptions = new ChromeOptions();
        // 设置后台静默模式启动浏览器
        //   chromeOptions.addArguments("--headless");
        //添加用户cookies数据
        chromeOptions.addArguments("--user-data-dir=C:\\Users\\liuya\\AppData\\Local\\Google\\Chrome\\User Data1");
        //启动浏览器
        driver = new ChromeDriver(chromeOptions);
        readArticle("https://blog.csdn.net/weixin_40986713/article/details/122666131");
    }

    public static void start(Article article) {
        driver.get("https://juejin.cn/editor/drafts/new?v=2");
        try {
            Thread.sleep(500);
            WebElement titleEle = driver.findElement(By.xpath("//textarea[@class='title-input title-input']"));
            titleEle.sendKeys(article.getTitle());
            WebElement contentEle = driver.findElement(By.xpath("//div[@class='rich-text-editor-content medium-editor-element medium-editor-placeholder']"));
            System.out.println(article.getContent());
            contentEle.sendKeys(article.getContent());
           // System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public  void  collect(){
        int pageNum=0;
        while (true){
            pageNum++;
            if(!readPage(webUrl,pageNum)){
                break;
            }
        }
    }


    /**
     * @param url 访问路径
     * @return
     */
    public static Document getDocument(String url) {
        try {
            //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static   Article readArticle(String url) {
        Article article=new Article();
        Document doc=  getDocument(url);
        //获取文章标题
        Elements title = doc.select("h1[id=articleContentId]");
        //获取文章内容
        Elements content = doc.select("[class=htmledit_views]");
        System.out.println(title.text());
        article.setTitle(title.text());
        article.setContent(content.html());
        start(article);
        return article;
    }

    public  boolean readPage(String webUrl,int pageNum) {
        Document doc = getDocument(webUrl+"/article/list/"+pageNum);
        // 获取目标HTML代码
        Elements elements = doc.select("[class=article-list]");
        //文章列表
        Elements articles = elements.select("[class=article-item-box csdn-tracking-statistics]");
        if (articles.size() == 0) {
            return false;
        }
        List<Article> list=new ArrayList<>();
        articles.forEach(e -> {
            String url = e.select("a").attr("href");
            list.add(readArticle(url));
            try {
                //等待3秒
                Thread.sleep(200);
            } catch (InterruptedException interruptedException) {
                System.out.println("线程中断故障");
            }
        });

        return true;
    }

    @Data
    static  class Article{
        private String title;
        private String content;
    }

}

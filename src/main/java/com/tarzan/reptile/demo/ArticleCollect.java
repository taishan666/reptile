package com.tarzan.reptile.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 文章采集
 * @author tarzan
 * @date 2021/5/31
 */
@Component
@AllArgsConstructor
public class ArticleCollect {


    //网站地址
    private final static String webUrl="https://blog.csdn.net/weixin_40986713";
    private final static String webDriver = "webdriver.chrome.driver";
    private final static String webDriverPath ="E:\\chromedriver\\chromedriver.exe";
    private  static WebDriver driver=null;
    private final static int score=88;

    static {
        System.setProperty(webDriver, webDriverPath);
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        collect();
    }


    public static void  collect(){
        int pageNum=0;
        while (true){
            pageNum++;
            System.out.println("当前页 :"+pageNum);
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



    public static Article readArticle(String url) {
        Article article=new Article();
        Integer qc=queryQualityScore(driver,url);
        if(qc<score){
            Document doc=  getDocument(url);
            //获取文章标题
            Elements title = doc.select("h1[id=articleContentId]");
            System.out.println(title.text()+"   网址："+url+"   质量分："+qc);
        }
        return article;
    }

    public static boolean readPage(String webUrl,int pageNum) {
        Document doc = getDocument(webUrl+"/article/list/"+pageNum);
        // 获取目标HTML代码
        Elements elements = doc.select("[class=article-list]");
        //文章列表
        Elements articles = elements.select("[class=article-item-box csdn-tracking-statistics]");
        if (articles.size() == 0) {
            return false;
        }
        articles.forEach(e -> {
            String url = e.select("a").attr("href");
            readArticle(url);
            try {
                Integer[] times=new Integer[]{10000,20000,30000};
                //等待3秒
                Thread.sleep(Arrays.stream(times).findAny().get());
            } catch (InterruptedException interruptedException) {
                System.out.println("线程中断故障");
            }
        });

        return true;
    }

    public static Integer queryQualityScore(WebDriver driver, String postUrl){
        driver.get("https://www.csdn.net/qc");
       // Thread.sleep(1000);
        WebElement ele=driver.findElement(By.className("el-input__inner"));
        ele.sendKeys(postUrl);
      //  Thread.sleep(1000);
        WebElement  qcEle=driver.findElement(By.className("trends-input-box-btn"));
        qcEle.click();
        WebElement  element=driver.findElement(By.xpath("//div[@class='csdn-body-right']/p[1]"));
       // System.out.println(element.getText());
        return Integer.parseInt(element.getText());
    }


    @Data
    static  class Article{
        private String title;
        private String url;
        private Integer score;
    }

}

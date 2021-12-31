package com.tarzan.reptile.demo;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 文章采集
 * @author tarzan
 */
@Component
@AllArgsConstructor
public class ArticleRead {


    //网站地址
    private static String webUrl="https://blog.csdn.net/weixin_40986713";

    public static void main(String[] args) {
        read();
    }

    public static void  read(){
        int i=0;
        while (true){
            i++;
            long start=System.currentTimeMillis();
            System.out.println("第"+i+"轮阅读开始....");
            collect();
            System.out.println("第"+i+"轮阅读结束.... 耗时"+(System.currentTimeMillis()-start)+"ms");
        }
    }

    public static void  collect(){
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



    public static void readArticle(String url) {
        Document doc=  getDocument(url);
        //获取文章标题
        Elements title = doc.select("h1[id=articleContentId]");
        System.out.println(title.text());
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
            String readNum=e.select("span[class=read-num]").get(0).text();
            if(Integer.valueOf(readNum)<=10000){
                readArticle(url);
            }
            try {
                //等待3秒
                Thread.sleep(200);
            } catch (InterruptedException interruptedException) {
                System.out.println("线程中断故障");
            }
        });
        return true;
    }

}

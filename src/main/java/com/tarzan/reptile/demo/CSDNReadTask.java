package com.tarzan.reptile.demo;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CSDN刷阅读数
 *
 * @author tarzan Liu
 * @version 1.0
 * @date 2020/12/14
 * @since JDK1.8
 */
public class CSDNReadTask implements Runnable {

    String pageUrl;

    public CSDNReadTask(String pageUrl) {
        this.pageUrl=pageUrl;
    }

    /**
     *
     * @param url 访问路径
     * @return
     */
    public Document getDocument (String url){
        try {
            //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args)  {

        ExecutorService es = Executors.newFixedThreadPool(3);
        CSDNReadTask page1 = new CSDNReadTask("https://blog.csdn.net/weixin_40986713/article/list/1?orderby=ViewCount");
        CSDNReadTask page2 = new CSDNReadTask("https://blog.csdn.net/weixin_40986713/article/list/2?orderby=ViewCount");
        CSDNReadTask page3 = new CSDNReadTask("https://blog.csdn.net/weixin_40986713/article/list/3?orderby=ViewCount");
        es.execute(page1);
        es.execute(page2);
        es.execute(page3);


    }


    public void run() {
        readPage();
    }


    public  void readPage(){
        Document doc = this.getDocument(pageUrl);
        // 获取目标HTML代码
        Elements elements = doc.select("[class=article-list]");
        //文章列表
        Elements titles = elements.select("[class=article-item-box csdn-tracking-statistics]");
        int i=0;
        while (true) {
            titles.forEach(e->{
                String url = e.select("a").attr("href");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                System.out.println(url);
                HttpUtil.createGet(url).execute().body();
            });
            i++;
            System.out.println("第"+i+"次阅读");
            try {
                Thread.sleep(60 * 1000L);
            } catch (InterruptedException e) {
                Console.log(e.getMessage());
            }
        }

    }





}

package com.tarzan.reptile.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 百度热点
 *
 * @author tarzan
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/5/31$ 16:48$
 * @since JDK1.8
 */
public class BaiduHot {

    /**
     *
     * @param url 访问路径
     * @return
     */
    public static Document getDocument (String url){
        try {
            //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static void main(String[] args) {
        Document doc=  getDocument("http://top.baidu.com/buzz?b=1");
        // 获取目标HTML代码
        Elements table = doc.select("[class=list-table]");
        Elements trs=table.select("tr");

        for (Element tr : trs) {
            Elements tds=tr.select("td");
            if(tds.size()==4){
                String rank=  tds.get(0).select("span").text();
                String title=  tds.get(1).select("a").get(0).text();
                String link=  tds.get(1).select("a").get(0).attr("href");
                String index=  tds.get(3).select("span").text();
                System.out.print("  rank="+rank);
                System.out.print("  title="+title);
                System.out.print("  link="+link);
                System.out.println("  index="+index);
            }



        }

    }


}

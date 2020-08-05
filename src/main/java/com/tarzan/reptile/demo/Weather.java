package com.tarzan.reptile.demo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class Weather {

    /**
     *
     * @param url 访问路径
     * @return
     */
    public  Document getDocument (String url){
        try {
            //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) {

        Weather   w = new Weather();
        Document doc = w.getDocument("http://www.weather.com.cn/weather/101230101.shtml");
        // 获取目标HTML代码
        Elements elements1 = doc.select("[class=t clearfix]");
        //今天
        Elements elements2 = elements1.select("[class=sky skyid lv2 on]");
        String today = elements2.get(0).text();
        System.out.println("今日天气： "+today);

        //几号
        Elements elements3 = elements1.select("h1");
        String number = elements3.get(0).text();
        System.out.println("日期： "+number);

        // 天气
        Elements elements4 = elements1.select("[class=wea]");
        String rain = elements4.get(0).text();
        System.out.println("天气： "+rain);

        // 最高温度
        Elements elements5 = elements1.select("span");
        String highTemperature = elements5.get(0).text()+"°C";
        System.out.println("最高温："+highTemperature);

        // 最低温度
        Elements elements6 = elements1.select(".tem i");
        String lowTemperature = elements6.get(0).text();
        System.out.println("最低温："+lowTemperature);

        // 风力
        Elements elements7 = elements1.select(".win i");
        String wind = elements7.get(2).text();
        System.out.println("风力："+wind);
    }
}

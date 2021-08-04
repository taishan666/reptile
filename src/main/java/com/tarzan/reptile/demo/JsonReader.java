package com.tarzan.reptile.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class JsonReader implements Runnable {


    public  static String folderPath="C:\\Users\\liuya\\Desktop\\接口数据\\json数据\\巷道.json";

    //主方法
    public static void main(String[] args) {

        JsonReader.attack();

    }

    public static void attack() {
     ExecutorService es = Executors.newFixedThreadPool(10);
        long start=System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            JsonReader reader = new JsonReader();
            es.execute(reader);
        }
        System.out.println("耗时"+(System.currentTimeMillis()-start)+"ms");
    }

    public static String getData() {

        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream out = new FileInputStream(folderPath);
            InputStreamReader reader = new InputStreamReader(out, StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(reader);
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line+"\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return sb.toString();
    }

    public void run() {
            getData();
        }
}

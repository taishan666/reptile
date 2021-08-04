package com.tarzan.reptile.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Data
@Slf4j
public class FileDataSource {


    public  static String folderPath="C:\\Users\\liuya\\Desktop\\接口数据\\json数据\\巷道.json";

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            getData();
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



}


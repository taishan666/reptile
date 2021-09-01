package com.tarzan.reptile.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Data
@Slf4j
public class FileDataSource {


    public  static String folderPath="C:\\Users\\liuya\\Downloads\\Promo Fast Opener\\Promo Fast Opener.aep";

    public static void main(String[] args) {
        System.out.println(getData());

    }


    public static String getData() {
        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream out = new FileInputStream(folderPath);
            InputStreamReader reader = new InputStreamReader(out, Charsets.ISO_8859_1);
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


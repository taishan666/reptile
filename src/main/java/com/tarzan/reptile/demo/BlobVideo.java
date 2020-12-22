package com.tarzan.reptile.demo;

import cn.hutool.http.HttpUtil;

import java.io.FileOutputStream;
import java.io.IOException;


public class BlobVideo {

    //要下载的创建的视频地址
    public  final static String fileSavePath="d:/ts/new.mp4";
    //视频源
    public final static String url="https://video.csdnimg.cn/73c634ff71c1479f96b7fb7b1f9292de/video/f00e7e9b70e8445e81bad8ce322869cd-655657db1aadfdcf51ed91e91162df27-video-fd.m3u8?auth_key=video/f00e7e9b70e8445e81bad8ce322869cd-655657db1aadfdcf51ed91e91162df27-video-fd.m3u8?auth_key=1608622392-407a739bc488462a9c1781fe208e1fb9-0-d5b52c300da51ae7e4aadfdec024cd86";


    public static void main(String[] args) {
        String API="https://video.csdnimg.cn/73c634ff71c1479f96b7fb7b1f9292de/f00e7e9b70e8445e81bad8ce322869cd.m3u8?auth_key=1608622392-407a739bc488462a9c1781fe208e1fb9-0-9625912f0ef91ae3150fb8ac66cbc6b4";
        String result1= HttpUtil.get(API);
        System.out.println(result1);
        downVideo(url,fileSavePath);
    }

    public static void downVideo(String url,String fileSavePath){
        try {
            String result= HttpUtil.get(url);
            String[] lines= result.split("\n");
            url= url.substring(0,url.indexOf("/video/")+7);
            FileOutputStream fs = new FileOutputStream(fileSavePath);
            for (String line : lines) {
                if(!line.contains("#")){
                    byte [] bytes= HttpUtil.downloadBytes(url+line);
                    fs.write(bytes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

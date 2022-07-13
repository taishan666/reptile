package com.tarzan.reptile.videoImage;

import cn.hutool.http.HttpUtil;
import com.tarzan.reptile.utils.HttpUtils;

import java.io.FileOutputStream;
import java.io.IOException;

public class BiliVideo {


    //要下载的创建的视频地址
    public  final static String fileSavePath="e:/ts/new.mp4";
    //视频源
    public final static String url="https://upos-sz-mirrorcoso1.bilivideo.com/upgcxcode/37/19/736371937/736371937-1-416.mp4?e=ig8euxZM2rNcNbRghWdVhwdlhWN1hwdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1654498236&gen=playurlv2&os=coso1bv&oi=665072720&trid=f5561cc5fc8547c492bb39ace3bd2c76p&platform=pc&upsig=a33f9990a69efd0eabc847d13d5e51bd&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=663794085&bvc=vod&nettype=0&orderid=1,3&agrr=1&bw=121338&logo=40000000%22,%20%22https://upos-sz-mirrorcos.bilivideo.com/upgcxcode/37/19/736371937/736371937-1-416.mp4?e=ig8euxZM2rNcNbRghWdVhwdlhWN1hwdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1654498236&gen=playurlv2&os=cosbv&oi=665072720&trid=f5561cc5fc8547c492bb39ace3bd2c76p&platform=pc&upsig=742f0a328d267aaa4d0103c45baedffb&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=663794085&bvc=vod&nettype=0&orderid=2,3&agrr=1&bw=121338&logo=40000000";


    public static void main(String[] args) {
      //  String API="https://video.csdnimg.cn/73c634ff71c1479f96b7fb7b1f9292de/f00e7e9b70e8445e81bad8ce322869cd.m3u8?auth_key=1608622392-407a739bc488462a9c1781fe208e1fb9-0-9625912f0ef91ae3150fb8ac66cbc6b4";
      //  String result1= HttpUtil.get(API);
     //   System.out.println(result1);
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

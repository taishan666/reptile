package com.tarzan.reptile.demo;


import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

@Slf4j
public class NetEaseCloudMusic {

    //音乐保存目录
    private static final String videoSavePath="d:/音乐/";

    public static void main(String[] args) {
        //替换歌曲ID和歌曲名称
        getDownMusicURL("27483167","唯一");
    }


    /**
     * 方法描述: 获得下载音乐连接
     *
     * @param songID 歌曲ID
     * @param songName 歌曲名称
     * @author tarzan
     * @date 2020年11月10日 10:33:40
     */
    public static void getDownMusicURL(String songID,String songName) {
        String musicPath="http://music.163.com/song/media/outer/url?id="+songID;
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
        String redirectUrl = HttpUtil.createGet(musicPath).addHeaders(headers).execute().header("Location");
        log.info("-----音频真实地址链接-----\n"+redirectUrl);
        //下载音乐到本地
        downMusic(redirectUrl,songName,"网易云");
    }

    /**
     * 方法描述: 下载方法
     *
     * @param httpUrl
     * @param title
     * @author tarzan
     * @date 2020年11月10日 10:34:09
     */
    public static void downMusic(String httpUrl,String title,String source) {
        String fileAddress = videoSavePath+"/"+source+"/"+title+".mp3";
        int byteRead;
        try {
            URL url = new URL(httpUrl);
            //获取链接
            URLConnection conn = url.openConnection();
            //输入流
            InputStream inStream = conn.getInputStream();
            //封装一个保存文件的路径对象
            File fileSavePath = new File(fileAddress);
            //注:如果保存文件夹不存在,那么则创建该文件夹
            File fileParent = fileSavePath.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            //写入文件
            FileOutputStream fs = new FileOutputStream(fileSavePath);
            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            inStream.close();
            fs.close();
            log.info("\n-----音频保存路径-----\n"+fileSavePath.getAbsolutePath());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}

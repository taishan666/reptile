package com.tarzan.reptile.demo;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * 酷狗VIP音频解析
 *
 * @author liu yapeng
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/8/6$ 10:30$
 * @since JDK1.8
 */
@Slf4j
public class KuGou {

    //视频保存目录
    private static final String videoSavePath="d:/音乐/";

    //分享链接（手动修改）
    private static String musicName = "大城小爱";


    public static void main(String[] args) {
        kgParseUrl(musicName);
    }


    /**
     * 方法描述: 解析酷狗音乐
     *
     * @param searchName
     * @author tarzan Liu
     * @date 2020年08月06日 18:45:02
     */
    public static void kgParseUrl(String searchName) {
        String url = "https://songsearch.kugou.com/song_search_v2?keyword="+searchName+"&platform=WebFilter";
        String jsonStr=HttpUtil.createGet(url).execute().body();
        List<HttpCookie>  cookies =HttpUtil.createGet(url).execute().getCookies();
        JSONObject json =JSONObject.parseObject(jsonStr);
        String  fileHash= json.getJSONObject("data").getJSONArray("lists").getJSONObject(0).getString("FileHash");
        String  audioName= json.getJSONObject("data").getJSONArray("lists").getJSONObject(0).getString("FileName");
        String songUrl = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&hash="+fileHash+"&mid=1";
        String  jsonStr1= HttpUtil.createGet(songUrl).execute().body();
        JSONObject json1 =JSONObject.parseObject(jsonStr1);
        String  playUrl= json1.getJSONObject("data").getString("play_url");
         log.info("\n-----音频下载地址-----\n"+playUrl);
        downLoad(playUrl,audioName,"酷狗");
    }


    /**
     * 方法描述: 视频文件下载
     *
     * @param httpUrl
     * @param title
     * @param source
     * @author tarzan Liu
     * @date 2020年08月06日 18:45:44
     */
    public static void downLoad(String httpUrl,String title,String source) {
        String fileAddress = videoSavePath+"/"+source+"/"+title+".mp4";
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

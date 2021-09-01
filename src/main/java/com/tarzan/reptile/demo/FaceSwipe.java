package com.tarzan.reptile.demo;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class FaceSwipe {

    public static void main(String[] args) {
        String url_detect = "https://api-cn.faceplusplus.com/facepp/v3/detect";
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("api_key","UmeTHKmayj5KFi6SCTt3-0rwc8XSx9k9");
        paramMap.put("api_secret","KlpfpUnIK_0FllNu3JbREJM3WKppz1OJ");
        paramMap.put("image_file","");
        paramMap.put("return_landmark",2);
        String result= HttpUtil.post(url_detect,paramMap);
    }
}

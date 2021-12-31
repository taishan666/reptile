package com.tarzan.reptile.videoImage;


import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import com.baidu.aip.util.Util;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "25393592";
    public static final String API_KEY = "OkRDD6FQwm5hTKGSMIEL9RN4";
    public static final String SECRET_KEY = "ONAxohflnqL2HwBEQB2iGUCjmO5lgywp";

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            BufferedImage image = splitting("C:\\Users\\liuya\\Desktop\\image\\"+i+".png");
        }
    }
    public static BufferedImage splitting(BufferedImage image){
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"png",out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splitting(out.toByteArray());
    }

    public static BufferedImage splitting(byte[] image){
        // 初始化一个AipBodyAnalysis
        AipBodyAnalysis client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", "foreground");
        // 参数为本地路径
        JSONObject res = client.bodySeg(image, options);
       // System.out.println(res.get("foreground").toString());
        return  convert(res.get("foreground").toString());
    }

    public static BufferedImage splitting(String imgPath){
        try {
            byte[]   data = Util.readFileByBytes(imgPath);
            return splitting(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static BufferedImage convert(String labelmapBase64) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(labelmapBase64);
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(is);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
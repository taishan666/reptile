package com.tarzan.reptile.demo;

import sun.font.FontDesignMetrics;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Couplet {

    //设置生成文件存放文件夹
    final static String imageFolderPath = "C:\\Users\\liuya\\Desktop\\img\\";
    //设置背景图
    final static String sourceFilePath = "C:\\Users\\liuya\\Desktop\\img\\background.png";
    //设置字体
    final static String fontName = "宋体";


    public static void main(String[] args) {
        write();
    }

    public static  void write(){
        BufferedImage image = new BufferedImage(640*4, 640+2*640*7, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.drawImage(writeH("恭喜发财"),0,0,640*4,640,null);
        g.drawImage(writeV("三江进宝百业旺"),0,640,640*2,2*640*7,null);
        g.drawImage(writeV("四海来财万福春"),2*640,640,640*2,2*640*7,null);
        //输出文件
        try {
            ImageIO.write(image,"png",new File(imageFolderPath +"春联.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static  BufferedImage writeH(String text){
        char[] ch= text.toCharArray();
        BufferedImage image = new BufferedImage(640*ch.length, 640, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            g.drawImage(fontMark(String.valueOf(c)),640*i,0,640,640,null);
        }
        g.dispose();
        return image;
    }


    public static  BufferedImage writeV(String text){
        char[] ch= text.toCharArray();
        BufferedImage image = new BufferedImage(2*640, 2*640*ch.length, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            g.drawImage(fontMark(String.valueOf(c)),0,2*640*i,2*640,2*640,null);
        }
        g.dispose();
        return image;
    }


    public static BufferedImage fontMark(String content) {
        try {
            BufferedImage image = ImageIO.read(new File(sourceFilePath));
           return fontMark(image, content);
        } catch (IOException e) {
            return null;
        }
    }

    //文字标记
    public static BufferedImage fontMark(BufferedImage bufImg, String content) {
        Font font = new Font(fontName, Font.BOLD, 400);
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        Graphics2D graphics = bufImg.createGraphics();
        graphics.setColor(Color.black);
        graphics.setFont(font);
        graphics.drawString(content, 120, 460);
        graphics.dispose();
        System.out.println(content);
        return bufImg;
    }

}

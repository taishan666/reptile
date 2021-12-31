package com.tarzan.reptile.videoImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageColor {


    public static void converter(String imgFilePath,String format,String formatFilePath) {
        File imgFile=new File(imgFilePath);
        File formatFile=new File(formatFilePath);
        imgFile.canRead();
        try {
            BufferedImage bi =  ImageIO.read(imgFile);
            BufferedImage newBufferedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bi, 0, 0, Color.WHITE, null);
            ImageIO.write(newBufferedImage, format, formatFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //失真处理
    public static BufferedImage converter(BufferedImage image){
        BufferedImage newBufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            ImageIO.write(newBufferedImage, "png", out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            return ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }



    }





    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 205; i++) {
            converter("C:\\Users\\liuya\\Desktop\\people\\"+i+".png","png","C:\\Users\\liuya\\Desktop\\ceshi\\"+i+".png");
        }

    }
}

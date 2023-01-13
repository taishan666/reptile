package com.tarzan.reptile.image;


import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author TARZAN
 */
public class Picture {
    public static void main(String[] args) throws IOException {
        String sliderImagePath="E:\\screenshot\\certificate.png";
        Mat sliderMat = opencv_imgcodecs.imread(sliderImagePath, opencv_imgcodecs.IMREAD_GRAYSCALE);
        opencv_imgcodecs.imwrite("E:\\screenshot\\certificate_grey.png",sliderMat);
        BufferedImage sourceImage = ImageIO.read(new File("E:\\screenshot\\certificate.png"));
        BufferedImage greyImage = ImageIO.read(new File("E:\\screenshot\\certificate_grey.png"));
        Set<Integer> bgRgbs=getRgbs(greyImage);
        Integer min=bgRgbs.stream().reduce(Integer::min).get();
        Integer max=bgRgbs.stream().reduce(Integer::max).get();
        int replaceRgb=toARGB(Color.red);
        // 遍历Y轴的像素
        for (int y = sourceImage.getMinY(); y < sourceImage.getHeight(); y++) {
            // 遍历X轴的像素
            for (int x = sourceImage.getMinX(); x < sourceImage.getWidth(); x++) {
                int argb = sourceImage.getRGB(x, y);
                // 核心代码：但是这样会有误差，还需要优化边缘、人像边框
                if(bgRgbs.contains(greyImage.getRGB(x, y))){
                    argb = replaceRgb;
                }
                sourceImage.setRGB(x,y,argb);
            }
        }
        ImageIO.write(sourceImage, "png", new File("E:\\screenshot\\certificate_clone.png"));
    }

    public static Set<Integer> getRgbs(BufferedImage image){
        Set<Integer> set=new HashSet<>(image.getWidth());
        for (int y = image.getMinY(); y < 10; y++) {
            for (int x = image.getMinX(); x < image.getWidth(); x++) {
                set.add(image.getRGB(x, y));
            }
        }
        for (int x = image.getMinX(); x < 10; x++) {
            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                set.add(image.getRGB(x, y));
            }
        }
        return set;
    }


    public static Color toRGB(int color) {
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        return new Color(red,green,blue);
    }

    public static String toRGBStr(int color) {
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        return String.format("%03d",red)+","+String.format("%03d",green)+","+String.format("%03d",blue);
    }

    private static int toARGB(Color color){
        int r= color.getRed();
        int g= color.getGreen();
        int b= color.getBlue();
        int argb = 255 << 24;//设置A(透明度)的值 左移24位是放到最前面
        argb += (r << 16);
        argb += (g << 8);
        argb += b;
        return argb;
    }
}

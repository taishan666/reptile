package com.tarzan.reptile.demo;

import com.aspose.cad.internal.A.S;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageBlack {
    public static void main(String[] args) throws IOException {
        String sliderImagePath="E:\\screenshot\\certificate.png";
        BufferedImage sliderImage = ImageIO.read(new File(sliderImagePath));
        String whiteRgb = "255,255,255";
        String transparencyRgb = "0,0,0";
        // 遍历Y轴的像素
        for (int y = sliderImage.getMinY(); y < sliderImage.getHeight(); y++) {
            // 遍历X轴的像素
            for (int x = sliderImage.getMinX(); x < sliderImage.getWidth(); x++) {
                int argb = sliderImage.getRGB(x, y);
        /*        // 设置为透明背景
                if (removeRgb.equals(convertRgbStr(rgb))) {
                    alpha = 0;
                } else {
                    alpha = 255;
                }*/

          //     System.out.print(toRgb(argb)+" ");
              //  rgb = (alpha << 24) | (rgb & 0x00ffffff);
                if (!transparencyRgb.equals(toRGB(argb))) {
                    argb = toARGB(transparencyRgb);
                }
                sliderImage.setRGB(x, y, argb);
            }
            System.out.println();
        }

        ImageIO.write(sliderImage, "png", new File("E:\\screenshot\\slider_clone.png"));


    }

    public static String toRGB(int color) {
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        return red + "," + green + "," + blue;
    }

    private static int toARGB(String color){
        String[] rgb=color.split(",");
        int r= Integer.parseInt(rgb[0]);
        int g= Integer.parseInt(rgb[1]);
        int b= Integer.parseInt(rgb[2]);
        int argb = 255 << 24;//设置A(透明度)的值 左移24位是放到最前面
        argb += (r << 16);
        argb += (g << 8);
        argb += b;
        return argb;
    }


    public void test(){
        String sliderImage="E:\\screenshot\\slider.png";
        //1.从本地读取背景原图,灰度处理
        // Mat sliderMat = opencv_imgcodecs.imread(sliderImage, opencv_imgcodecs.IMREAD_GRAYSCALE);
        Mat sliderMat = opencv_imgcodecs.imread(sliderImage);
        //1、灰度化图片

        opencv_imgproc.cvtColor(sliderMat,sliderMat,opencv_imgproc.COLOR_BGR2GRAY);
        opencv_imgcodecs.imwrite("E:\\screenshot\\slider_grey.jpeg",sliderMat);
//3、inRange二值化转黑白图
        // Core.inRange(slideBlockMat,Scalar.all(96), Scalar.all(96), slideBlockMat);
        //2.二值化转黑白图
        opencv_imgproc.threshold(sliderMat,sliderMat,127,255, opencv_imgproc.THRESH_BINARY);
        //保存为黑白图片
        opencv_imgcodecs.imwrite("E:\\screenshot\\slider_black.jpeg",sliderMat);
    }
}

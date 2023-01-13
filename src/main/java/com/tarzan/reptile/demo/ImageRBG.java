package com.tarzan.reptile.demo;

import java.awt.*;

public class ImageRBG {
    public static void main(String[] args) {
       // int blackRgb = 0;
        System.out.println(toARGB("0,0,0"));
        System.out.println(toRGB(0));
        System.out.println(Color.white.getRGB());
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
        int g= Integer.parseInt(rgb[0]);
        int b= Integer.parseInt(rgb[0]);
        int argb = 255 << 24;//设置A(透明度)的值 左移24位是放到最前面
        argb += (r << 16);
        argb += (g << 8);
        argb += b;
        return argb;
    }

}

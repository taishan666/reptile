package com.tarzan.reptile.image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author  tarzan
 */
public class ImageBgChange {



    public static void main(String[] args) {
            //白色
            changeBgColor ("E:\\screenshot\\certificate.png",Color.WHITE,45);
            //黑色
            changeBgColor("E:\\screenshot\\certificate.png",new Color(0,0,0,0),45);
            //绿色
            changeBgColor("E:\\screenshot\\certificate.png",Color.GREEN,45);
            //蓝色
            changeBgColor("E:\\screenshot\\certificate.png",Color.BLUE,45);
            //黄色
            changeBgColor("E:\\screenshot\\certificate.png",Color.YELLOW,45);
            //红色
            changeBgColor("E:\\screenshot\\certificate.png",Color.RED,45);
            //黑色透明（color对象最后一个参数0为完全透明255为完全不透明）
            changeBgColor("E:\\screenshot\\certificate.png",new Color(0,0,0,0),45);
            //白色透明（color对象最后一个参数0为完全透明255为完全不透明）
            changeBgColor("E:\\screenshot\\certificate.png",new Color(255,255,255,0),45);
    }


    /**
     * 方法描述: 改变背景色
     *
     * @param path 图片路径
     * @param color 要变成的颜色
     * @param range 颜色误差范围（取50左右最佳）
     * @date 2023年01月13日 17:48:52
     */
    private static void changeBgColor(String path,Color color,int range) {
        try {
            BufferedImage image;
            //读取原图
            BufferedImage origin = ImageIO.read(new File(path));
            if(BufferedImage.TYPE_4BYTE_ABGR!=origin.getType()){
                //转换图片类型
                image = new BufferedImage(origin.getWidth(), origin.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                image.createGraphics().drawImage(origin,0,0,null);
            }else{
                image=origin;
            }
            //边缘背景rgb
            int bgRGB=image.getRGB(image.getMinX(), image.getMinY());
            // 遍历Y轴的像素
            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                // 遍历X轴的像素
                for (int x = image.getMinX(); x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int r = (rgb & 0xff0000) >>16;
                    int g = (rgb & 0xff00) >> 8;
                    int b = (rgb & 0xff);
                    int R = (bgRGB & 0xff0000) >>16;
                    int G = (bgRGB & 0xff00) >> 8;
                    int B = (bgRGB & 0xff);
                    //颜色误差范围
                    if(Math.abs(R-r) < range && Math.abs(G-g) < range && Math.abs(B-b) < range ) {
                        image.setRGB(x,y,color.getRGB());
                    }
                }
            }
            String suffix=path.substring(path.lastIndexOf("."));
            String filePath =path.replace(suffix,"_"+color.getRGB()+suffix);
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 方法描述: argb 转rgb
     *
     * @param color BufferedImage对象的rgb值
     * @return {@link String}
     */
    public static String toRGB(int color) {
        // 获取color(RGB)中R位
        int red = (color & 0xff0000) >> 16;
        // 获取color(RGB)中G位
        int green = (color & 0x00ff00) >> 8;
        // 获取color(RGB)中B位
        int blue = (color & 0x0000ff);
        return red + "," + green + "," + blue;
    }

    /**
     * 方法描述: rgb 转argb （带透明度的rgb）
     *
     * @param color rgb 三色值
     * @return {@link String}
     */
    private static int toARGB(String color){
        String[] rgb=color.split(",");
        int r= Integer.parseInt(rgb[0]);
        int g= Integer.parseInt(rgb[0]);
        int b= Integer.parseInt(rgb[0]);
        //设置A(透明度)的值 左移24位是放到最前面
        int argb = 255 << 24;
        argb += (r << 16);
        argb += (g << 8);
        argb += b;
        return argb;
    }

}


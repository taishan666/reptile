package com.tarzan.reptile.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author TARZAN
 */
public class CartoonImage {



    public static void main(String[] args) {
        changeBgColor ("E:\\screenshot\\certificate.png");
    }

    private static void newImage(String path) {
        try {
            BufferedImage origin = ImageIO.read(new File(path));
            int h=origin.getHeight();
            int w=origin.getWidth();
            BufferedImage image=new BufferedImage(h,w,BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics= image.createGraphics();
            BasicStroke stokeLine = new BasicStroke(2.0f);
            graphics.setColor(Color.BLACK);
            graphics.setStroke(stokeLine);
            int lineW=w-w/20;
            int h1=h/3;
            int h2=h/3*2;
            Polygon p1=new Polygon();
            p1.addPoint(0,20);
            p1.addPoint(lineW, 20);
            p1.addPoint(lineW, h1);
            p1.addPoint(0,h1-50);
            graphics.drawPolygon(p1);

            Polygon p2=new Polygon();
            p2.addPoint(50,h1-10);
            p2.addPoint(lineW, h1+40);
            p2.addPoint(lineW, h2);
            p2.addPoint(50,h2+50);
            graphics.drawPolygon(p2);

            int w1=lineW-lineW/4;
            Polygon p3=new Polygon();
            p3.addPoint(50,h2+80);
            p3.addPoint(w1, h2+30);
            p3.addPoint(w1, h-20);
            p3.addPoint(50,h-20);
            graphics.drawPolygon(p3);


            Polygon p4=new Polygon();
            p4.addPoint(w1+20,h2+30);
            p4.addPoint(lineW, h2+20);
            p4.addPoint(lineW, h-20);
            p4.addPoint(w1+20,h-20);
            graphics.drawPolygon(p4);

            graphics.dispose();
            String suffix=path.substring(path.lastIndexOf("."));
            String filePath =path.replace(suffix,"_cartoon"+suffix);
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void changeBgColor(String path) {
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
            int h=image.getHeight();
            int w=image.getWidth();
            Graphics2D graphics= image.createGraphics();
            BasicStroke stokeLine = new BasicStroke(2.0f);
            graphics.setColor(Color.BLACK);
            graphics.setStroke(stokeLine);
            int lineW=w-w/20;
            int h1=h/3;
            int h2=h/3*2;
            Polygon p1=new Polygon();
            p1.addPoint(0,20);
            p1.addPoint(lineW, 20);
            p1.addPoint(lineW, h1);
            p1.addPoint(0,h1-50);
            graphics.drawPolygon(p1);

            Polygon p2=new Polygon();
            p2.addPoint(50,h1-10);
            p2.addPoint(lineW, h1+40);
            p2.addPoint(lineW, h2);
            p2.addPoint(50,h2+50);
            graphics.drawPolygon(p2);

            int w1=lineW-lineW/4;
            Polygon p3=new Polygon();
            p3.addPoint(50,h2+80);
            p3.addPoint(w1, h2+30);
            p3.addPoint(w1, h-20);
            p3.addPoint(50,h-20);
            graphics.drawPolygon(p3);


            Polygon p4=new Polygon();
            p4.addPoint(w1+20,h2+30);
            p4.addPoint(lineW, h2+20);
            p4.addPoint(lineW, h-20);
            p4.addPoint(w1+20,h-20);
            graphics.drawPolygon(p4);
            graphics.dispose();

            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                for (int x = image.getMinX(); x < image.getWidth(); x++) {
                      if(!p1.contains(x,y)&&!p2.contains(x,y)&&!p3.contains(x,y)&&!p4.contains(x,y)){
                          image.setRGB(x,y,Color.white.getRGB());
                      }
                }
            }
            String suffix=path.substring(path.lastIndexOf("."));
            String filePath =path.replace(suffix,"_cartoon"+suffix);
            ImageIO.write(image, "png", new File(filePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
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

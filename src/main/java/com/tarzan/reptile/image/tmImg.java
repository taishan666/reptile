package com.tarzan.reptile.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 描述 The type Tm img.
 *
 * @author Jack Que
 * @created 2021 -07-08 10:25:10
 */
public class tmImg {


    /**
     * 描述 The entry point of application.
     *
     * @param args the input arguments
     * @author Jack Que
     * @created 2021 -07-08 10:25:10
     */
    public static void main(String[] args) {
        try {
            changeImgColor("E:\\screenshot\\certificate.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将背景替换为透明
     *
     * @param imgBytes the img bytes
     * @return
     * @throws IOException the io exception
     * @author Jack Que
     * @created 2021 -07-08 10:25:10 Change img color.
     */
    public static void changeImgColor(String path) throws IOException {

        File file = new File(path);
        String fileName = file.getName();
        BufferedImage bi =  ImageIO.read(file);
        Image image = (Image) bi;
        //将原图片的二进制转化为ImageIcon
        ImageIcon imageIcon = new ImageIcon(image);
        int width = imageIcon.getIconWidth();
        int height = imageIcon.getIconHeight();
//
        //图片缓冲流
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

        int alpha = 255;

        //这个背景底色的选择，我这里选择的是比较偏的位置，可以修改位置。背景色选择不知道有没有别的更优的方式（比如先过滤一遍获取颜色次数最多的，但是因为感觉做起来会比较复杂没去实现），如果有可以评论。
        int RGB=bufferedImage.getRGB(width-1, height-1);

        for(int i = bufferedImage.getMinX(); i < width; i++) {
            for(int j = bufferedImage.getMinY(); j < height; j++) {

                int rgb = bufferedImage.getRGB(i, j);

                int r = (rgb & 0xff0000) >>16;
                int g = (rgb & 0xff00) >> 8;
                int b = (rgb & 0xff);
                int R = (RGB & 0xff0000) >>16;
                int G = (RGB & 0xff00) >> 8;
                int B = (RGB & 0xff);
                //a为色差范围值，渐变色边缘处理，数值需要具体测试，50左右的效果比较可以
                int a = 45;
                if(Math.abs(R-r) < a && Math.abs(G-g) < a && Math.abs(B-b) < a ) {
                    alpha = 0;
                } else {
                    alpha = 255;
                }
                rgb = (alpha << 24)|(rgb & 0x00ffffff);
                bufferedImage.setRGB(i,j,rgb);
            }
        }

//        graphics2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

        //新建字节输出流，用来存放替换完背景的图片
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String[] split = fileName.split("\\.");
        fileName = split[0]+"(已转换)."+split[1];
        ImageIO.write(bufferedImage, "png", new File("D:\\"+fileName));
    }

    public static String convertRgbStr(int color) {
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        return red + "," + green + "," + blue;
    }
}


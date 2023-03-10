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


    private static void changeBgColor(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
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
            String suffix=path.substring(path.lastIndexOf("."));
            String filePath =path.replace(suffix,"_cartoon"+suffix);
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????: ???????????????
     *
     * @param path ????????????
     * @param color ??????????????????
     * @param range ????????????????????????50???????????????
     * @date 2023???01???13??? 17:48:52
     */
    private static void changeBgColor(String path,Color color,int range) {
        try {
            BufferedImage image;
            //????????????
            BufferedImage origin = ImageIO.read(new File(path));
            if(BufferedImage.TYPE_4BYTE_ABGR!=origin.getType()){
                //??????????????????
                image = new BufferedImage(origin.getWidth(), origin.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                image.createGraphics().drawImage(origin,0,0,null);
            }else{
                image=origin;
            }
            //????????????rgb
            int bgRGB=image.getRGB(image.getMinX(), image.getMinY());
            // ??????Y????????????
            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                // ??????X????????????
                for (int x = image.getMinX(); x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int r = (rgb & 0xff0000) >>16;
                    int g = (rgb & 0xff00) >> 8;
                    int b = (rgb & 0xff);
                    int R = (bgRGB & 0xff0000) >>16;
                    int G = (bgRGB & 0xff00) >> 8;
                    int B = (bgRGB & 0xff);
                    //??????????????????
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
     * ????????????: argb ???rgb
     *
     * @param color BufferedImage?????????rgb???
     * @return {@link String}
     */
    public static String toRGB(int color) {
        // ??????color(RGB)???R???
        int red = (color & 0xff0000) >> 16;
        // ??????color(RGB)???G???
        int green = (color & 0x00ff00) >> 8;
        // ??????color(RGB)???B???
        int blue = (color & 0x0000ff);
        return red + "," + green + "," + blue;
    }

    /**
     * ????????????: rgb ???argb ??????????????????rgb???
     *
     * @param color rgb ?????????
     * @return {@link String}
     */
    private static int toARGB(String color){
        String[] rgb=color.split(",");
        int r= Integer.parseInt(rgb[0]);
        int g= Integer.parseInt(rgb[0]);
        int b= Integer.parseInt(rgb[0]);
        //??????A(?????????)?????? ??????24?????????????????????
        int argb = 255 << 24;
        argb += (r << 16);
        argb += (g << 8);
        argb += b;
        return argb;
    }

}

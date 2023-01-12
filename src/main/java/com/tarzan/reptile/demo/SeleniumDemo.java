package com.tarzan.reptile.demo;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SeleniumDemo {
    private final static String webDriver = "webdriver.chrome.driver";
    private final static String webDriverPath ="E:\\chromedriver\\chromedriver.exe";


    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty(webDriver, webDriverPath);
        WebDriver driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.douyin.com/");
        Thread.sleep(1000);
        WebElement login=driver.findElement(By.xpath("//*[text()='登录']"));
        Thread.sleep(1000);
        JavascriptExecutor jse= (JavascriptExecutor)driver;
       // login.click();
        //指定元素位置执行js点击事件
        jse.executeScript("arguments[0].click();", login);
        WebElement passwordTab=driver.findElement(By.xpath("//*[text()='密码登录']"));
        Thread.sleep(1000);
        passwordTab.click();
        WebElement phone=driver.findElement(By.name("normal-input"));
        phone.sendKeys("18838811955");
        WebElement password=driver.findElement(By.name("button-input"));
        password.sendKeys("a1334512682");
        WebElement submit=driver.findElement(By.className("web-login-button"));
        submit.click();
        Thread.sleep(1000);
        WebElement  dragElement=driver.findElement(By.xpath("//div[contains(@class,'secsdk-captcha-drag-icon')]"));
        String bgImage="E:\\screenshot\\bj.jpeg";
        String sliderImage="E:\\screenshot\\slider.png";
        Actions actions = new Actions(driver);
        for (int i = 0; i < 6; i++) {
            WebElement bgImg=driver.findElement(By.id("captcha-verify-image"));
            WebElement  sliderImg=driver.findElement(By.xpath("//img[contains(@class,'captcha_verify_img_slide react-draggable')]"));
            String bgImgSrc=bgImg.getAttribute("src");
            String sliderImgSrc=sliderImg.getAttribute("src");
            Thread.sleep(500);
            //下载操作
            HttpUtil.downloadFile(bgImgSrc,bgImage);
            HttpUtil.downloadFile(sliderImgSrc,sliderImage);
            Thread.sleep(3000);
            sliderImage=toBlack(sliderImage);
            double slideDistance=getMoveDist(bgImage,sliderImage);
            System.out.println(slideDistance);
            int  moveDist = (int)slideDistance+10;
            actions.clickAndHold(dragElement).perform();
            for (int j = 0; j < moveDist;) {
                int move= RandomUtil.randomInt(1,moveDist);
                actions.moveByOffset(move, 0).perform();
                j=j+move;
            }
            Thread.sleep(200);
            actions.release().perform();
            Thread.sleep(2000);
        }

    }

    private static String toBlack(String imagePath){
        BufferedImage sliderImage = null;
        try {
            sliderImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String transparencyRgb = "0,0,0";
        // 遍历Y轴的像素
        for (int y = sliderImage.getMinY(); y < sliderImage.getHeight(); y++) {
            // 遍历X轴的像素
            for (int x = sliderImage.getMinX(); x < sliderImage.getWidth(); x++) {
                int argb = sliderImage.getRGB(x, y);
                if (!transparencyRgb.equals(toRGB(argb))) {
                    argb = toARGB("255,0,0");
                }
                sliderImage.setRGB(x, y, argb);
            }
        }
        try {
            ImageIO.write(sliderImage, "png", new File(imagePath.replace("slider.png","slider_clone.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "E:\\screenshot\\slider_clone.png";

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
    /**
     * 计算滑块移动距离（经过反复测试只有在颜色比较深的时候，才能计算转圈）
     *
     */
    private static double getMoveDist(String bgImage,String sliderImage){
        //1.从本地读取背景原图,灰度处理
        Mat sliderMat = opencv_imgcodecs.imread(sliderImage, opencv_imgcodecs.IMREAD_GRAYSCALE);
        Mat bgMat = opencv_imgcodecs.imread(bgImage , opencv_imgcodecs.IMREAD_GRAYSCALE);
        //2.二值化转黑白图
        opencv_imgproc.threshold(sliderMat,sliderMat,127,255, opencv_imgproc.THRESH_BINARY);
        opencv_imgproc.threshold(bgMat,bgMat,127,255, opencv_imgproc.THRESH_BINARY);
        Mat result = new Mat();
        //3.匹配小图在大图中的位置  用标准模式去比较 然后把返回结果给result
        opencv_imgproc.matchTemplate(sliderMat, bgMat, result, opencv_imgproc.TM_CCORR_NORMED);
        opencv_core.normalize(result, result, 0, 1, opencv_core.NORM_MINMAX, -1, new Mat());
        DoublePointer pointer = new DoublePointer(new double[2]);
        org.bytedeco.opencv.opencv_core.Point maxLoc = new org.bytedeco.opencv.opencv_core.Point();
        //4.获取匹配结果坐标
        opencv_core.minMaxLoc(result, null, pointer, null, maxLoc, null);
        //5.在图上做标记
  /*      opencv_imgproc.rectangle(sliderMat, maxLoc,
                new Point(maxLoc.x() + bgMat.cols(), maxLoc.y() + bgMat.rows()),
                new Scalar(0, 255, 0,1));*/
        //保存为黑白图片
        opencv_imgcodecs.imwrite("E:\\screenshot\\slider_black.png",sliderMat);
        opencv_imgcodecs.imwrite("E:\\screenshot\\bg_black.jpg",bgMat);
        long start=System.currentTimeMillis();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("耗时"+(System.currentTimeMillis()-start)+" ms");
        System.out.println("二维中坐标的位置："+maxLoc.x()+","+maxLoc.y());
        return maxLoc.x();
    }


    /**
     * 判断某个元素是否存在
     */
    public static boolean isExistElement(WebDriver webDriver, By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}

package com.tarzan.reptile.demo;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SwipeCaptcha2 {

    private final static String webDriver = "webdriver.chrome.driver";
    private final static String webDriverPath ="E:\\chromedriver\\chromedriver.exe";

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty(webDriver, webDriverPath);
        WebDriver driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://dun.163.com/trial/jigsaw");
        //浏览器最大化
        driver.manage().window().maximize();
        //滑块按钮
        WebElement sliderBtn=driver.findElement(By.className("yidun_slider"));
        //点击触发滑块拼图
        sliderBtn.click();
        Thread.sleep(500);
        Actions actions = new Actions(driver);
        String bgImage="E:\\screenshot\\bj.jpg";
        String sliderImage="E:\\screenshot\\slider.png";
        for (int i = 0; i < 10; i++) {
            WebElement bgImg=driver.findElement(By.className("yidun_bg-img"));
            WebElement sliderImg=driver.findElement(By.className("yidun_jigsaw"));
            String bgImgSrc=bgImg.getAttribute("src");
            String sliderImgSrc=sliderImg.getAttribute("src");
            Thread.sleep(500);
            //下载操作
            HttpUtil.downloadFile(bgImgSrc,bgImage);
            HttpUtil.downloadFile(sliderImgSrc,sliderImage);
            double slideDistance=getMoveDist(bgImage,sliderImage);
            //修正误差
            int  moveDist = (int)slideDistance+10;
            actions.clickAndHold(sliderBtn).perform();
            //循环一点点的移动
            for(int j=moveDist;j>=1;) {
                int move=1;
                if(j>1){
                    move= RandomUtil.randomInt(1,j);
                    actions.moveByOffset(move, 0).perform();
                }else {
                    actions.moveByOffset(move, 0).perform();
                }
                j=j-move;
            }
            Thread.sleep(200);
            actions.release().perform();
            Thread.sleep(1000);
            if(isExistElement(driver,By.xpath("//div[contains(@class,'yidun--success')]"))){
                break;
            }
        }
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
        //保存为黑白图片
        //opencv_imgcodecs.imwrite("E:\\screenshot\\slider_black.png",sliderMat);
        //opencv_imgcodecs.imwrite("E:\\screenshot\\bg_black.jpg",bgMat);
        Mat result = new Mat();
        //3.匹配小图在大图中的位置  用标准模式去比较 然后把返回结果给result
        opencv_imgproc.matchTemplate(sliderMat, bgMat, result, opencv_imgproc.TM_CCORR_NORMED);
        opencv_core.normalize(result, result, 0, 1, opencv_core.NORM_MINMAX, -1, new Mat());
        DoublePointer pointer = new DoublePointer(new double[2]);
        org.bytedeco.opencv.opencv_core.Point maxLoc = new org.bytedeco.opencv.opencv_core.Point();
        //4.获取匹配结果坐标
        opencv_core.minMaxLoc(result, null, pointer, null, maxLoc, null);
        //5.在图上做标记
        opencv_imgproc.rectangle(sliderMat, maxLoc,
                new Point(maxLoc.x() + bgMat.cols(), maxLoc.y() + bgMat.rows()),
                new Scalar(0, 255, 0,1));
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

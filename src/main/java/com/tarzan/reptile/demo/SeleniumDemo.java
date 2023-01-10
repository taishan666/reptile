package com.tarzan.reptile.demo;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
        Actions actions = new Actions(driver);
        for (int i = 0; i < 100; i++) {
            WebElement bgImg=driver.findElement(By.id("captcha-verify-image"));
            String bgImage="E:\\screenshot\\bj.png";
            String sliderImage="E:\\screenshot\\slider.png";
            // 截图操作
            File bjFile = bgImg.getScreenshotAs(OutputType.FILE);
            FileCopyUtils.copy(bjFile, new File(bgImage));
            // System.out.println(bgImg.getAttribute("src"));
            Thread.sleep(500);
            WebElement  sliderImg=driver.findElement(By.xpath("//img[contains(@class,'captcha_verify_img_slide react-draggable')]"));
            File sliderFile = sliderImg.getScreenshotAs(OutputType.FILE);
            FileCopyUtils.copy(sliderFile, new File(sliderImage));
            //System.out.println(sliderImg.getAttribute("src"));
            double slideDistance=getMoveDist(bgImage,sliderImage);
            System.out.println(slideDistance);
            WebElement  dragElement=driver.findElement(By.xpath("//div[contains(@class,'secsdk-captcha-drag-icon')]"));
            slideDistance = slideDistance * 280 / 680 - 23;
            actions.clickAndHold(dragElement).perform();
            actions.moveByOffset((int)slideDistance, 0).perform();
            Thread.sleep(200);
            actions.release().perform();
            Thread.sleep(2000);
        }

    }

    private static double getMoveDist(String bgImage,String sliderImage){
        //11.5、从本地读取背景原图
        Mat src = opencv_imgcodecs.imread("E:\\screenshot\\slider.png", opencv_imgcodecs.IMREAD_GRAYSCALE);
        Mat srcBenDiHK = opencv_imgcodecs.imread("E:\\screenshot\\bj.jpeg" , opencv_imgcodecs.IMREAD_GRAYSCALE);
        //11.6、创建一个新的背景图，方便做标记
        Mat clone = src.clone();
        Mat result = new Mat();
        //11.7、匹配小图在大图中的位置  用标准模式去比较 然后把返回结果给result
        opencv_imgproc.matchTemplate(src, srcBenDiHK, result, opencv_imgproc.TM_CCORR_NORMED);
        opencv_core.normalize(result, result, 0, 1, opencv_core.NORM_MINMAX, -1, new Mat());
        DoublePointer pointer = new DoublePointer(new double[2]);
        org.bytedeco.opencv.opencv_core.Point maxLoc = new org.bytedeco.opencv.opencv_core.Point();
        //11.8、获取匹配结果坐标
        opencv_core.minMaxLoc(result, null, pointer, null, maxLoc, null);
        //11.9、在图上做标记
        opencv_imgproc.rectangle(clone, maxLoc,
                new Point(maxLoc.x() + srcBenDiHK.cols(), maxLoc.y() + srcBenDiHK.rows()),
                new Scalar(0, 255, 0,1));
        System.out.println(maxLoc.get());
        System.out.println(maxLoc.x()+","+maxLoc.y()+"  x-y="+(maxLoc.x()-maxLoc.y()));
       return maxLoc.x();
    }


}

package com.tarzan.reptile.demo;


import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.imgproc.Imgproc;


public class SwipeCaptcha {
    static {
        //1、加载openCV类库
        System.load("E:\\work_space\\java_opencv\\src\\main\\resources\\WEB-INF\\lib\\x64\\opencv_java2413.dll");
    }


    public static void main(String[] args) {
         //对滑块进行处理
        Mat slideBlockMat=  opencv_imgcodecs.imread("E:\\screenshot\\huakuai.png");
         //1、灰度化图片
        opencv_imgproc.cvtColor(slideBlockMat,slideBlockMat,opencv_imgproc.COLOR_BGR2GRAY);
        //2、去除周围黑边
  /*      for (int row = 0; row < slideBlockMat.height(); row++) {
            for (int col = 0; col < slideBlockMat.width(); col++) {
                if (slideBlockMat.get(row, col)[0] == 0) {
                    slideBlockMat.put(row, col, 96);
                }
            }
        }*/
        //3、inRange二值化转黑白图
     //   Core.inRange(slideBlockMat, Scalar.all(96), Scalar.all(96), slideBlockMat);
       //2、二值化
        opencv_imgproc.threshold(slideBlockMat,slideBlockMat,127,255, opencv_imgproc.THRESH_BINARY);
        opencv_imgcodecs.imwrite("E:\\screenshot\\bj_grey.jpeg",slideBlockMat);
    }
}

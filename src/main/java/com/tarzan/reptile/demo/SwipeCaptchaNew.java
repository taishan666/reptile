package com.tarzan.reptile.demo;


import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;

public class SwipeCaptchaNew {
    public static void main(String[] args) {
        //11.5、从本地读取背景原图
        Mat src = opencv_imgcodecs.imread("E:\\screenshot\\huakuai.png", opencv_imgcodecs.IMREAD_GRAYSCALE);
        Mat srcBenDiHK = opencv_imgcodecs.imread("E:\\screenshot\\bj.png", opencv_imgcodecs.IMREAD_GRAYSCALE);
        //11.6、创建一个新的背景图，方便做标记
        Mat clone = src.clone();
        Mat result = new Mat();
        //11.7、匹配小图在大图中的位置  用标准模式去比较 然后把返回结果给result
        opencv_imgproc.matchTemplate(src, srcBenDiHK, result, opencv_imgproc.TM_CCORR_NORMED);
        opencv_core.normalize(result, result, 0, 1,opencv_core.NORM_MINMAX, -1, new Mat());
        //11.8、获取匹配结果坐标
        DoublePointer pointer = new DoublePointer(new double[2]);
        opencv_core.minMaxLoc(result,pointer);
        Point maxLoc = new Point(pointer);
        //11.9、在图上做标记
        opencv_imgproc.rectangle(clone, maxLoc,
                new Point(maxLoc.x() + srcBenDiHK.cols(), maxLoc.y() + srcBenDiHK.rows()),
                new Scalar(0, 255, 0,0));
       // opencv_imgcodecs.imwrite("D:\\img\\close.jpg", clone);
        //11.10、将背景图存储在本地
      //  opencv_imgcodecs.imwrite("D:\\img\\beijing", src);
      //  opencv_imgcodecs.imwrite("D:\\img\\huakuai", srcBenDiHK);
        //double distance = maxLoc.x() - maxLoc.y();
        System.out.println(maxLoc.x());

    }
}

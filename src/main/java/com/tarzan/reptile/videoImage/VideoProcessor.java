package com.tarzan.reptile.videoImage;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.ffmpeg.global.avcodec;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

public class VideoProcessor {
    //设置APPID/AK/SK
    public static final String APP_ID = "25393592";
    public static final String API_KEY = "OkRDD6FQwm5hTKGSMIEL9RN4";
    public static final String SECRET_KEY = "ONAxohflnqL2HwBEQB2iGUCjmO5lgywp";


    final static String videoFolderPath = "C:/Users/liuya/Desktop/video/";
    final static String videoName = "demo.mp4";
    final static String imageFolderPath = "C:/Users/liuya/Desktop/people/";


    public static void main(String[] args) throws Exception {
        videoProcess(videoFolderPath + videoName);
    }

    //视频水印
    public static void videoProcess(String filePath) {
        //抓取视频图像资源
        FFmpegFrameGrabber videoGrabber = new FFmpegFrameGrabber(filePath);
        //抓取视频图像资源
        FFmpegFrameGrabber audioGrabber = new FFmpegFrameGrabber(filePath);
        try {
            videoGrabber.start();
            audioGrabber.start();
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(videoFolderPath + "new" + videoName, videoGrabber.getImageWidth(), videoGrabber.getImageHeight(), videoGrabber.getAudioChannels());
            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.start();
            //处理图像
            int videoSize = videoGrabber.getLengthInVideoFrames();
            for (int i = 0; i < videoSize; i++) {
                Frame videoFrame = videoGrabber.grabImage();
                if (videoFrame != null && videoFrame.image != null) {
                    System.out.println("视频共" + videoSize + "帧，正处理第" + (i + 1) + "帧图片");
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage bi=converter.getBufferedImage(videoFrame);
                    BufferedImage bufferedImage = splitting(bi);
                    recorder.record(converter.convert(bufferedImage));
                }
            }
            //处理音频
            for (int i = 0; i < audioGrabber.getLengthInAudioFrames(); i++) {
                Frame audioFrame = audioGrabber.grabSamples();
                if (audioFrame != null && audioFrame.samples != null) {
                    recorder.recordSamples(audioFrame.sampleRate, audioFrame.audioChannels, audioFrame.samples);
                }
            }
            recorder.stop();
            recorder.release();
            videoGrabber.stop();
            audioGrabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage splitting(BufferedImage image){
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"png",out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splitting(out.toByteArray());
    }

    public static BufferedImage splitting(byte[] image){
        // 初始化一个AipBodyAnalysis
        AipBodyAnalysis client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", "foreground");
        // 参数为本地路径
        JSONObject res = client.bodySeg(image, options);
        return  convert(res.get("foreground").toString());
    }


    public static BufferedImage convert(String labelmapBase64) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(labelmapBase64);
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(is);
            //失真处理
            BufferedImage newBufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            ImageIO.write(newBufferedImage, "png", out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            return ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}

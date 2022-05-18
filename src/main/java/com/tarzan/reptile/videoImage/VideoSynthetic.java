package com.tarzan.reptile.videoImage;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class VideoSynthetic {

    final static String videoFolderPath = "C:/Users/liuya/Desktop/video/";
    final static String videoName = "demo.mp4";
    final static String imageFolderPath = "C:/Users/liuya/Desktop/image/";


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
        return null;
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

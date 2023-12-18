package com.tarzan.reptile.videoImage;

import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author tarzan
 */
public class VideoMergeJoin {

    final static String videoPath1 = "E:\\test.mp4";
    final static String videoPath2 = "E:\\aivideo\\20231217-190828_with_snd.mp4";
    final static String mergePath= "E:\\test_merge.mp4";
    final static String joinPath= "E:\\test_join.mp4";

    public static void main(String[] args) throws Exception {
        long start=System.currentTimeMillis();
        mergeVideo(videoPath1,videoPath2,mergePath);
       // joinVideo(videoPath1,videoPath2,joinPath);
        System.out.println("耗时 "+(System.currentTimeMillis()-start)+" ms");
    }

    public static void joinVideo(String videoPath1,String videoPath2, String outPath) throws Exception {
        // 初始化视频源
        FFmpegFrameGrabber grabber1 = new FFmpegFrameGrabber(videoPath1);
        FFmpegFrameGrabber grabber2 = new FFmpegFrameGrabber(videoPath2);
        grabber1.start();
        grabber2.start();
        // 初始化目标视频
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(joinPath, grabber1.getImageWidth(), grabber1.getImageHeight(),grabber1.getAudioChannels());
        // 录制视频
        recorder.start();
        while (true){
            Frame frame= grabber1.grab();
            if (frame == null) {
                break;
            }
            recorder.record(frame);
        }
        while (true){
            Frame frame= grabber2.grab();
            if (frame == null) {
                break;
            }
            recorder.record(frame);
        }
        // 释放资源
        grabber1.stop();
        grabber2.stop();
        recorder.stop();
    }

    public static void mergeVideo(String videoPath1,String videoPath2, String outPath) throws Exception {
        // 初始化视频源
        FFmpegFrameGrabber grabber1 = new FFmpegFrameGrabber(videoPath1);
        FFmpegFrameGrabber grabber2 = new FFmpegFrameGrabber(videoPath2);
        grabber1.start();
        grabber2.start();
        // 检查帧率是否一样
        if (grabber1.getFrameRate() != grabber2.getFrameRate()) {
            throw new Exception("Video frame rates are not the same!");
        }
        // 初始化目标视频
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mergePath, (grabber1.getImageWidth()+grabber2.getImageWidth()), grabber1.getImageHeight(),grabber1.getAudioChannels());
        // 录制视频
        recorder.start();
        int i=1;
        int videoSize = grabber1.getLengthInVideoFrames();
        while (true){
            Frame frame1= grabber1.grab();
            if (frame1 == null) {
                break;
            }
            if (frame1.image != null) {
                Frame frame2=grabber2.grabImage();
                System.out.println("视频共" + videoSize + "帧，正处理第" + i  + "帧图片 ");
                // 将两个帧合并为一个画面
                BufferedImage image1 =Java2DFrameUtils.toBufferedImage(frame1);
                BufferedImage image2 =Java2DFrameUtils.toBufferedImage(frame2);

                // 创建一个新的 BufferedImage 用于合并画面
                BufferedImage combinedImage = new BufferedImage(grabber1.getImageWidth() * 2, grabber1.getImageHeight(), BufferedImage.TYPE_3BYTE_BGR);
                Graphics2D g2d = combinedImage.createGraphics();
                // 在合并画面上绘制两个视频帧
                g2d.drawImage(image1, 0, 0, null);
                g2d.drawImage(image2, grabber1.getImageWidth(), 0, null);
                g2d.dispose();
                // ImageIO.write(combinedImage,"png",new File("E:\\images1\\combinedImage"+i+".png"));
                // 将合并后的 BufferedImage 转换为帧并录制到目标视频中
                recorder.record(Java2DFrameUtils.toFrame(combinedImage));
                i++;
            }
            if (frame1.samples != null) {
                recorder.recordSamples(frame1.sampleRate, frame1.audioChannels, frame1.samples);
            }
        }
        // 释放资源
        grabber1.stop();
        grabber2.stop();
        recorder.stop();
    }

    public static void mergeVideo1() throws Exception {
        // 初始化视频源
        FFmpegFrameGrabber grabber1 = new FFmpegFrameGrabber(videoPath1);
        FFmpegFrameGrabber grabber2 = new FFmpegFrameGrabber(videoPath2);
        FFmpegFrameGrabber audioGrabber = new FFmpegFrameGrabber(videoPath1);
        grabber1.start();
        grabber2.start();
        audioGrabber.start();
        // 检查帧率是否一样
        if (grabber1.getFrameRate() != grabber2.getFrameRate()) {
            throw new Exception("Video frame rates are not the same!");
        }
        // 初始化目标视频
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mergePath, (grabber1.getImageWidth()+grabber2.getImageWidth()), grabber1.getImageHeight(),grabber1.getAudioChannels());
        // 录制视频
        recorder.start();
        int i=1;
        int videoSize = grabber1.getLengthInVideoFrames();
        while (true){
            Frame frame1= grabber1.grabImage();
            Frame frame2=grabber2.grabImage();
            Frame audioFrame = audioGrabber.grabSamples();
            if (frame1 == null && frame2 == null && audioFrame == null) {
                break;
            }
            if (frame1 != null && frame2 != null) {
                System.out.println("视频共" + videoSize + "帧，正处理第" + i  + "帧图片");
                // 将两个帧合并为一个画面
                BufferedImage image1 =Java2DFrameUtils.toBufferedImage(frame1);
                BufferedImage image2 =Java2DFrameUtils.toBufferedImage(frame2);

                // 创建一个新的 BufferedImage 用于合并画面
                BufferedImage combinedImage = new BufferedImage(grabber1.getImageWidth() * 2, grabber1.getImageHeight(), BufferedImage.TYPE_3BYTE_BGR);
                Graphics2D g2d = combinedImage.createGraphics();
                // 在合并画面上绘制两个视频帧
                g2d.drawImage(image1, 0, 0, null);
                g2d.drawImage(image2, grabber1.getImageWidth(), 0, null);
                g2d.dispose();
                // ImageIO.write(combinedImage,"png",new File("E:\\images1\\combinedImage"+i+".png"));
                // 将合并后的 BufferedImage 转换为帧并录制到目标视频中
                recorder.record(Java2DFrameUtils.toFrame(combinedImage));
                i++;
            }
            if (audioFrame != null) {
                recorder.recordSamples(audioFrame.sampleRate, audioFrame.audioChannels, audioFrame.samples);
            }
        }
        // 释放资源
        grabber1.stop();
        grabber2.stop();
        audioGrabber.stop();
        recorder.stop();
    }
}

package com.tarzan.reptile.videoImage;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavaCV {
    final static String imagePath = "C:/Users/liuya/Desktop/people/";
    final static String videoPath = "C:/Users/liuya/Desktop/video/";
    final static String videoName = "demo123.mp4";
    final static String imageMat = "png";


    public static void main(String[] args) throws Exception {
        //视频按帧数分成图片
    //    videoToImages(videoPath + videoName, imagePath + videoName.substring(0, videoName.lastIndexOf(".")));
        //图片合成视频
    //    imagesToVideo(videoPath + "newVideo.mp4", imagePath);

        createMp4(videoPath+videoName,imagePath);
    }


    public static void videoToImages(String filePath, String fileTargetName) throws Exception {
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();
        System.out.println("视频编码:" + ff.getVideoCodec());
        System.out.println("像素格式:" + ff.getPixelFormat());
        System.out.println("帧速率:" + ff.getFrameRate());
        System.out.println("比特率:" + ff.getVideoBitrate());
        System.out.println("图片高度:" + ff.getImageHeight());
        System.out.println("图片宽度:" + ff.getImageWidth());
        int grabSize = ff.getLengthInFrames();
        for (int i = 0; i < grabSize; i++) {
            Frame frame = ff.grabImage();
            doExecuteFrame(frame, fileTargetName, i);
        }
        ff.stop();
    }

    public static void doExecuteFrame(Frame frame, String targetFileName, int index) {
        if (frame == null || frame.image == null) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        String fileName = targetFileName + "/" + index + "." + imageMat;
        BufferedImage bi = converter.getBufferedImage(frame);
        File output = new File(fileName);
        if (!output.exists()) {
            output.mkdirs();
        }
        try {
            ImageIO.write(bi, imageMat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imagesToVideo(String saveMp4name, String imagesPath) throws Exception {
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(saveMp4name, 640, 480);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 13
        recorder.setFormat("mp4");
        recorder.setFrameRate(24.148380638004276);
        recorder.setPixelFormat(0);
        recorder.setVideoBitrate(846472);
        recorder.setImageHeight(736);
        recorder.setImageWidth(544);
        recorder.start();
        // 列出目录中所有的图片
        File file = new File(imagesPath );
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            String imgPath = imagesPath  + i + "." + imageMat;
            System.out.println(imgPath);
            Java2DFrameConverter frameConverter = new Java2DFrameConverter();
            BufferedImage image = ImageIO.read(new File(imgPath));

            recorder.record(frameConverter.convert(image));
        }
        recorder.stop();
        recorder.release();
    }

    private static void createMp4(String mp4SavePath, String imgFolder) throws IOException {
        File file = new File(imgFolder);
        File[] files = file.listFiles();
        Map<Integer, File> imgMap = new HashMap<Integer, File>();
        int num = 0;
        for (File imgFile : files) {
            imgMap.put(num, imgFile);
            num++;
        }
        BufferedImage image = ImageIO.read(imgMap.get(0));
        //视频宽高最好是按照常见的视频的宽高  16：9  或者 9：16
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mp4SavePath, image.getWidth(), image.getHeight());
        //设置视频编码层模式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        //设置视频为25帧每秒
        recorder.setFrameRate(25);
        //设置视频图像数据格式
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFormat("mp4");
        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();
            //录制一个22秒的视频
            for (int i = 0; i < num; i++) {
                BufferedImage read = ImageIO.read(imgMap.get(i));
                recorder.record(converter.getFrame(read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最后一定要结束并释放资源
            recorder.stop();
            recorder.release();
        }
    }



    public static boolean mergeAudioAndVideo(String videoPath, String audioPath, String outPut) throws Exception {
        boolean isCreated = true;
        File file = new File(videoPath);
        if (!file.exists()) {
            return false;
        }
        FrameRecorder recorder = null;
        FrameGrabber grabber1 = null;
        FrameGrabber grabber2 = null;
        try {
            //抓取视频帧
            grabber1 = new FFmpegFrameGrabber(videoPath);
            //抓取音频帧
            grabber2 = new FFmpegFrameGrabber(audioPath);
            grabber1.start();
            grabber2.start();
            //创建录制
            recorder = new FFmpegFrameRecorder(outPut,
                    grabber1.getImageWidth(), grabber1.getImageHeight(),
                    grabber2.getAudioChannels());

            recorder.setFormat("mp4");
            recorder.setFrameRate(grabber1.getFrameRate());
            recorder.setSampleRate(grabber2.getSampleRate());
            recorder.start();

            Frame frame1;
            Frame frame2 ;
            //先录入视频
            while ((frame1 = grabber1.grabFrame()) != null ){
                recorder.record(frame1);
            }
            //然后录入音频
            while ((frame2 = grabber2.grabFrame()) != null) {
                recorder.record(frame2);
            }
            grabber1.stop();
            grabber2.stop();
            recorder.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (recorder != null) {
                    recorder.release();
                }
                if (grabber1 != null) {
                    grabber1.release();
                }
                if (grabber2 != null) {
                    grabber2.release();
                }
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }
        return isCreated;

    }



}
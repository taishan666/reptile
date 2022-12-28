package com.tarzan.reptile.image;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ImageCompress1 {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Lenovo\\Desktop\\demo\\bizhi.jpeg");
        File newFile = new File("C:\\Users\\Lenovo\\Desktop\\demo\\bbcompress2.png");
        Thumbnails.of(file).scale(1f).outputQuality(0.3f).toFile(newFile);
    }
}

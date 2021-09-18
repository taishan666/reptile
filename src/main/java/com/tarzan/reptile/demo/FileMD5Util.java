package com.tarzan.reptile.demo;



import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.charset.Charset;

public class FileMD5Util {

    public static void main(String[] args) throws IOException {
        //需要修改的文件
        File file=new File("C:\\Users\\liuya\\Desktop\\pdf\\最新企业笔试面试题大全.pdf");
        //查询md5值
        System.out.println(getFileMD5(file));
        //修改md5值
        FileMD5Util.writeToFile(file,"123456",true);
        //查询md5值
        System.out.println(getFileMD5(file));
    }


    public static String getFileMD5(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

    public static void writeToFile(File file,String data) {
        writeToFile(file, data, Charsets.UTF_8, false);
    }

    public static void writeToFile(File file,String data,boolean append) {
        writeToFile(file, data, Charsets.UTF_8, append);
    }

    public static void writeToFile(File file, final String data,Charset encoding) {
        writeToFile(file, data, encoding, false);
    }

    public static void writeToFile(File file,String data,Charset encoding, boolean append) {
        try {
            OutputStream out = new FileOutputStream(file, append);
            Throwable var5 = null;

            try {
                write(data, out, encoding);
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (out != null) {
                    if (var5 != null) {
                        try {
                            out.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        out.close();
                    }
                }

            }

        } catch (IOException var17) {
            var17.printStackTrace();
        }
    }

    public static void write(String data,OutputStream output,Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(encoding));
        }

    }

}

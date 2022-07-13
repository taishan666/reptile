package com.tarzan.reptile.pdf;


import com.aspose.cad.Color;
import com.aspose.cad.Image;
import com.aspose.cad.License;
import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.JpegOptions;
import com.aspose.cad.imageoptions.PdfOptions;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Description:CAD文件工具类
 * @Author: Tarzan Liu
 * @Date: 2020/1/8 14:23
 */
public class CadUtil{


    public static void main(String[] args) {
        License license = new License();
        license.setLicense("C:\\Users\\liuya\\Desktop\\jar\\Aspose.License.xml");
        //CADFileToPDF("C:\\Users\\liuya\\Desktop\\dwg\\test.dwg");
        CADFileToImage("C:\\Users\\liuya\\Desktop\\dwg\\test.dwg");
    }

    /**
     * 当前cad预览运行状态（保证cad预览线程，同步只有一个人操作）
     */
    public static volatile  boolean RUNNING = false;

    /**
     *方法描述  CAD文件转换为PDF流
     * @param srcFile 选择CAD文件路径
     * @param dataDir 保存pdf文件路径
     * @author Tarzan Liu
     * @date 2020年01月08日 15:08:50
     */
    public static void CADFileToPDF(String srcFile,String dataDir){
        Image objImage = Image.load(srcFile);
        CadRasterizationOptions cadRasterizationOptions = new CadRasterizationOptions();
        cadRasterizationOptions.setBackgroundColor(Color.getWhite());
        cadRasterizationOptions.setPageWidth(1600);
        cadRasterizationOptions.setPageHeight(1600);
        PdfOptions pdfOptions = new PdfOptions();
        pdfOptions.setVectorRasterizationOptions(cadRasterizationOptions);
        objImage.save(dataDir + System.currentTimeMillis()+".pdf", pdfOptions);
    }


    /**
     *方法描述  CAD文件转换为PDF流
     * @param srcFile 选择CAD文件路径
     * @author Tarzan Liu
     * @date 2020年01月08日 15:08:50
     */
    public static void CADFileToPDF(String srcFile){
        CadRasterizationOptions cadRasterizationOptions = new CadRasterizationOptions();
        cadRasterizationOptions.setBackgroundColor(Color.getWhite());
        cadRasterizationOptions.setPageWidth(1600);
        cadRasterizationOptions.setPageHeight(1600);
        PdfOptions pdfOptions = new PdfOptions();
        pdfOptions.setVectorRasterizationOptions(cadRasterizationOptions);
        String[] str = StringUtils.split(srcFile,".");
        Image objImage = Image.load(srcFile);
        objImage.save(str[0]+".pdf", pdfOptions);
    }

    /**
     *方法描述  CAD文件转换为PDF流
     * @param srcFile 选择CAD文件路径
     * @author Tarzan Liu
     * @date 2020年01月08日 15:08:50
     */
    public static void CADFileToImage(String srcFile){
        CadRasterizationOptions cadRasterizationOptions = new CadRasterizationOptions();
        cadRasterizationOptions.setBackgroundColor(Color.getWhite());
        cadRasterizationOptions.setPageWidth(1600);
        cadRasterizationOptions.setPageHeight(1600);
        JpegOptions jpegOptions = new JpegOptions();
        jpegOptions.setVectorRasterizationOptions(cadRasterizationOptions);
        String[] str = StringUtils.split(srcFile,".");
        Image objImage = Image.load(srcFile);
        objImage.save(str[0]+".jpeg", jpegOptions);
    }
    /**
     * 方法描述  CAD文件转换为PDF流
     * @param inputStream 选择CAD文件流
     * @author Tarzan Liu
     * @date 2020年01月08日 15:08:50
     */
    public static  InputStream CADFileToPDF(InputStream inputStream) throws Exception {
        RUNNING = true;
        Image image = Image.load(inputStream);
        CadRasterizationOptions cadRasterizationOptions = new CadRasterizationOptions();
        cadRasterizationOptions.setBackgroundColor(Color.getWhite());
        cadRasterizationOptions.setPageWidth(1600);
        cadRasterizationOptions.setPageHeight(1600);
        PdfOptions pdfOptions = new PdfOptions();
        pdfOptions.setVectorRasterizationOptions(cadRasterizationOptions);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.save(out,pdfOptions);
        return  outputStreamConvertInputStream(out);
    }


    /**
     * 方法描述  CAD文件转换为PDF(处理网络文件)
     *
     * @param netFileUrl 网络文件路径
     * @return InputStream 转换后文件输入流
     * @author Tarzan Liu
     * @date 2020年01月08日 15:08:50
     */
    public static InputStream convertNetFile(String netFileUrl) throws Exception {
        // 创建URL
        URL url = new URL(netFileUrl);
        // 试图连接并取得返回状态码
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        int httpResult = httpURLConnection.getResponseCode();
        if (httpResult == HttpURLConnection.HTTP_OK) {
            return httpURLConnection.getInputStream();
        }
        return null;
    }


    /**
     * 方法描述 outputStream转inputStream
     * @param out
     * @author Tarzan Liu
     * @date 2020年01月08日 15:08:50
     */
    public static ByteArrayInputStream outputStreamConvertInputStream(final OutputStream out) throws Exception {
        ByteArrayOutputStream byteOut=(ByteArrayOutputStream) out;
        return new ByteArrayInputStream(byteOut.toByteArray());
    }


/*    public static void main(String[] args) {
        String srcFile="F:/file/工业广场平面图201809.dwg";
        String dataDir="F:/file/";
        long a= System.currentTimeMillis();
        CADFileToPDF(srcFile);
       // CADFileToImage(srcFile);
        long b=System.currentTimeMillis();
        System.out.println(b-a);
    }*/

}
package com.tarzan.reptile.demo;

import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class PDFUtil {

    public static void main(String[] args) throws IOException {
      //  pdfToDocText("C:/Users/liuya/Desktop/my_pdf.pdf");
        extractImages("C:/Users/liuya/Desktop/HttpClient入门.pdf");
    }

    public static void pdfToDocText(String path){
        try {
            File fdf = new File(path);
            //通过文件名加载文档
            PDDocument pdd = PDDocument.load(fdf);
            //获取文档的页数
            int pageNumber = pdd.getNumberOfPages();
            //剥离器（读取pdf文件）
            PDFTextStripper stripper = new PDFTextStripper();
            //排序
            stripper.setSortByPosition(true);
            //设置要读取的起始页码
            stripper.setStartPage(1);
            //设置要读取的结束页码
            stripper.setEndPage(pageNumber);
            //生成的word的文件路径
            String docPath =path.substring(0,path.lastIndexOf("."))+".doc";
            File doc = new File(docPath);
            if(!doc.exists()){
                doc.createNewFile();
            }
            //文件输出流
            FileOutputStream fos = new FileOutputStream(doc);
            Writer writer = new OutputStreamWriter(fos, "utf-8");
            stripper.writeText(pdd, writer);
            writer.close();
            fos.close();
            System.out.println("转码完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 提取
     * @return
     */
    public static boolean extractImages(String path) {
        boolean result = true;
        try{
            File fdf = new File(path);
            //通过文件名加载文档
            PDDocument document = PDDocument.load(fdf);
            PDPageTree pages = document.getPages();
            Iterator<PDPage> iter = pages.iterator();
            while(iter.hasNext()){
                PDPage page = iter.next();
                PDResources resources  =page.getResources();
                resources.getXObjectNames().forEach(e->{
                    try {
                        PDXObject pdxObject=resources.getXObject(e);
                        PDImageXObject imageXObject=new PDImageXObject(pdxObject.getStream(),resources);
                        BufferedImage bufferedImage= imageXObject.getImage();
                   //     System.out.println(bufferedImage);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                System.out.println(resources.getColorSpaceNames());
                resources.getFontNames().forEach(f->{
                    try {
                        PDFont pdFont=resources.getFont(f);
                       // System.out.println(pdFont.getName());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                System.out.println("66666----------------------------------------------");

            }
        } catch(IOException ex){
            ex.printStackTrace();
            return false;
        }

        return result;
    }


}

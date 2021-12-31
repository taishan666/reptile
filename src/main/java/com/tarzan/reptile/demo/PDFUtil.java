package com.tarzan.reptile.demo;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class PDFUtil {

    public static void main(String[] args) throws IOException {
        //提取图片
        extractImages("C:\\Users\\liuya\\Desktop\\word\\帆软报表帮助文档.pdf");
        //提取文字
       // extractText("C:\\Users\\liuya\\Desktop\\word\\帆软报表帮助文档.pdf");

    }


    /**
     * 提取文本
     * @return
     */
    public static void extractText(String path){
        try {
            File fdf = new File(path);
            //通过文件名加载文档
            PDDocument pdd = Loader.loadPDF(fdf);
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
          //  System.out.println(stripper.getText(pdd));
            //生成的txt的文件路径
            String docPath =path.substring(0,path.lastIndexOf("."))+".txt";
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
            System.out.println("提取文本完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 提取图片
     * @return
     */
    public static boolean extractImages(String path) {
        boolean result = true;
        try{
            File fdf = new File(path);
            //通过文件名加载文档
            PDDocument document = Loader.loadPDF(fdf);
            PDPageTree pages = document.getPages();
            Iterator<PDPage> iter = pages.iterator();
            //生成的txt的文件路径
            String  imagePath =path.substring(0,path.lastIndexOf("."));
            while(iter.hasNext()){
                PDPage page = iter.next();
                PDResources resources  =page.getResources();
                resources.getXObjectNames().forEach(e->{
                    try {
                        if(resources.isImageXObject(e)){
                            PDImageXObject imageXObject=(PDImageXObject)resources.getXObject(e);
                            BufferedImage bufferedImage= imageXObject.getImage();
                            System.out.println(bufferedImage);
                            ImageIO.write(bufferedImage,"jpg",new File(imagePath+"_"+e+".jpg"));
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                System.out.println("----------------------------------------------");
            }
            System.out.println("提取图片完成");
        //    document.save(fdf);
            document.close();
        } catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
        return result;
    }


}

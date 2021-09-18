package com.tarzan.reptile.demo;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.encryption.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class PDFUtil {

    public static void main(String[] args) throws IOException {
       // pdfToDocText("C:/Users/liuya/Desktop/pdf/test.pdf");
     //   extractImages("C:/Users/liuya/Desktop/pdf/test.pdf");
        encrypt1("C:/Users/liuya/Desktop/pdf/HttpClient入门.pdf");
    }

    public static void pdfToDocText(String path){
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
            String content = stripper.getText(pdd);
            System.out.println(content);
            //生成的word的文件路径
            String docPath =path.substring(0,path.lastIndexOf("."))+".doc";
            File doc = new File(docPath);
            if(!doc.exists()){
                doc.createNewFile();
            }
            //文件输出流
            FileOutputStream fos = new FileOutputStream(doc);
            Writer writer = new OutputStreamWriter(fos, "utf-8");
           // stripper.writeText(pdd, writer);
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
            PDDocument document = Loader.loadPDF(fdf);
            PDPageTree pages = document.getPages();
            Iterator<PDPage> iter = pages.iterator();
            while(iter.hasNext()){
                PDPage page = iter.next();
                PDResources resources  =page.getResources();
                resources.getXObjectNames().forEach(e->{
                    try {
                        if(resources.isImageXObject(e)){
                            PDImageXObject imageXObject=(PDImageXObject)resources.getXObject(e);
                            BufferedImage bufferedImage= imageXObject.getImage();
                            System.out.println(bufferedImage);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                System.out.println("66666----------------------------------------------");
            }
            document.save(fdf);
            document.close();
        } catch(IOException ex){
            ex.printStackTrace();
            return false;
        }

        return result;
    }

    public static void encrypt1(String path) throws IOException {
        String ownerPassWord = "123";
        String userPassWord = "456";
        File file = new File(path);
        long start = System.currentTimeMillis();
        PDDocument load = Loader.loadPDF(file);
        AccessPermission permissions = new AccessPermission();
        permissions.setCanExtractContent(false);
        permissions.setCanModify(false);
        StandardProtectionPolicy p = new StandardProtectionPolicy(ownerPassWord , userPassWord, permissions);
        SecurityHandler sh = new StandardSecurityHandler(p);
        sh.prepareDocumentForEncryption(load);
        PDEncryption encryptionOptions= new PDEncryption();
        encryptionOptions.setSecurityHandler(sh);
        load.setEncryptionDictionary(encryptionOptions);
        String folderPath= file.getParent();
        String fileName=file.getName().substring(0,file.getName().lastIndexOf("."));
        System.out.println(folderPath+File.separator+fileName+"-加密"+".pdf");
        //保存文档
        load.save(folderPath+File.separator+fileName+"-加密"+".pdf");


    }

    public static boolean encrypt(String path,String pwd){
        try {
            File file = new File(path);
            //通过文件名加载文档
            PDDocument   document = Loader.loadPDF(file);
            AccessPermission permissions = new AccessPermission();
            permissions.setCanExtractContent(false);
            permissions.setCanModify(false);
            StandardProtectionPolicy p = new StandardProtectionPolicy(pwd , pwd, permissions);
            SecurityHandler sh = new StandardSecurityHandler(p);
            sh.prepareDocumentForEncryption(document);
            PDEncryption encryptionOptions= new PDEncryption();
            encryptionOptions.setSecurityHandler(sh);
            document.setEncryptionDictionary(encryptionOptions);

            System.out.println("pdf加密成功！");
            String folderPath= file.getParent();
            String fileName=file.getName().substring(0,file.getName().lastIndexOf("."));
            System.out.println(folderPath+File.separator+fileName+"-加密"+".pdf");
            //保存文档
            document.save(folderPath+File.separator+fileName+"-加密"+".pdf");
            document.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }


}

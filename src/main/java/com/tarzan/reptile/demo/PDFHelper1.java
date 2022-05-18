package com.tarzan.reptile.demo;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

import java.awt.*;
import java.io.*;
import java.util.Iterator;

public class PDFHelper1 {

    public static void main(String[] args) throws Exception {
     //  addWatermark(new File("C:\\Users\\liuya\\Desktop\\pdf\\test.pdf"), "tarzan");
        removeWatermark(new File("C:\\Users\\liuya\\Desktop\\word\\帆软报表帮助文档.pdf"));
    }

    public static void pdf2doc(String pdfPath) {

    }

    /**
     * pdf添加文字水印
     */
    private static void addWatermark(File file, String text) {
        try {
            PDDocument document = Loader.loadPDF(file);
            document.setAllSecurityToBeRemoved(true);
            for (PDPage page : document.getPages()) {
                PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
                //pdf扩展图形对象
                PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
                // 透明度
                graphicsState.setNonStrokingAlphaConstant(0.2f);
                graphicsState.setAlphaSourceFlag(true);
                cs.setGraphicsStateParameters(graphicsState);
                cs.setNonStrokingColor(Color.red);//Red
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_OBLIQUE, 50.0f);
                // 获取旋转实例
                cs.setTextMatrix(Matrix.getRotateInstance(20, 350f, 490f));
                cs.showText(text);
                cs.endText();
                cs.close();
            }
            String folderPath= file.getParent();
            String fileName=file.getName().substring(0,file.getName().lastIndexOf("."));
            document.save(folderPath+File.separator+fileName+"_waterMark"+".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * pdf去除水印
     */
    public static boolean removeWatermark(File file) {
        try{
            //通过文件名加载文档
            PDDocument document = Loader.loadPDF(file);
            PDPageTree pages = document.getPages();
            Iterator<PDPage> iter = pages.iterator();
            while(iter.hasNext()){
                PDPage page = iter.next();
                PDResources resources  =page.getResources();
                resources.getExtGStateNames().forEach(ext->{
                    PDExtendedGraphicsState gState=resources.getExtGState(ext);
                    gState.setNonStrokingAlphaConstant(0.0f);
                });
            }
            String folderPath= file.getParent();
            String fileName=file.getName().substring(0,file.getName().lastIndexOf("."));
            document.save(folderPath+File.separator+fileName+"_noMark"+".pdf");
            document.close();
            return true;
        } catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }




}





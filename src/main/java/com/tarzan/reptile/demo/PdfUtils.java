package com.tarzan.reptile.demo;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class PdfUtils {

    public static void main(String[] args) {
        excelToPdf("C:\\Users\\liuya\\Desktop\\excel\\附件1-2021年洛阳市市直事业单位招聘工作人员职位表.xls");
    }

    /**
     * Excel文件转换
     * @param excelPath 需要被转换的excel全路径带文件名
     * @Return void
     */
    public static void excelToPdf(String excelPath) {
        long old = System.currentTimeMillis();
        try {
            //新建一个pdf文档
            String pdfPath=excelPath.substring(0,excelPath.lastIndexOf("."))+".pdf";
            //Excel文件数据
            Workbook wb = new Workbook(excelPath);
            FileOutputStream fileOS = new FileOutputStream(pdfPath);
            //保存为pdf文件
            wb.save(fileOS, SaveFormat.PDF);
            fileOS.close();
            removeWatermark(new File(pdfPath));
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("EXCEL 转 Pdf 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //移除文字水印
    public static boolean removeWatermark(File file) {
        try {
            //通过文件名加载文档
            PDDocument document = Loader.loadPDF(file);
            PDPageTree pages = document.getPages();
            Iterator<PDPage> iter = pages.iterator();
            while (iter.hasNext()) {
                PDPage page = iter.next();
                removeFont(page,"FAAACD");
            }
            file.delete();
            document.save(file);
            document.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }


    //移除文字水印
    public static void removeFont(PDPage page, String cosName) {
        PDResources resources = page.getResources();
        COSDictionary dict1 = resources.getCOSObject();
        resources.getFontNames().forEach(e -> {
                COSDictionary dict2 = dict1.getCOSDictionary(COSName.FONT);
                if (e.getName().equals(cosName)) {
                    dict2.removeItem(e);
                }
            page.setResources(new PDResources(dict1));
        });
    }





}

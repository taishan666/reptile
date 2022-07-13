package com.tarzan.reptile.pdf;

import com.aspose.cells.License;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

import java.io.FileOutputStream;

public class PdfUtils {

    public static void main(String[] args) {
        excelToPdf("C:\\Users\\liuya\\Desktop\\excel\\test.xlsx");
    }

    /**
     * Excel文件转换
     * @param excelPath 需要被转换的excel全路径带文件名
     * @Return void
     */
    public static void excelToPdf(String excelPath) {
        License license = new License();
        license.setLicense("C:\\Users\\liuya\\Desktop\\jar\\Aspose.License.xml");
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
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("EXCEL 转 Pdf 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
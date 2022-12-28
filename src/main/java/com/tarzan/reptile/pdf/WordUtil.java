package com.tarzan.reptile.pdf;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.aspose.words.internal.zzZj0;

import java.io.File;
import java.io.FileOutputStream;

public class WordUtil {


    public static void main(String[] args) throws Exception {
        doc2pdf("C:\\Users\\Lenovo\\Desktop\\北京54转国家2000坐标转换代码实现.docx");
    }

    //doc文件转pdf(目前最大支持21页)
    public static void doc2pdf(String wordPath) throws Exception {
     License license = new License();
      license.setLicense("C:\\Users\\Lenovo\\Desktop\\jar\\Aspose.License.xml");
        long old = System.currentTimeMillis();
        try {
            //新建一个pdf文档
            String pdfPath=wordPath.substring(0,wordPath.lastIndexOf("."))+".pdf";
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);
            doc.save(os, SaveFormat.PDF);
            os.close();
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Word 转 Pdf 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Word 转 Pdf 失败...");
            e.printStackTrace();
        }
    }

}

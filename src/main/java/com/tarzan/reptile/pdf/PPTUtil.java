package com.tarzan.reptile.pdf;



import com.aspose.slides.License;
import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;

import java.io.*;


public class PPTUtil {


    public static void main(String[] args) {
            ppt2pdf("C:\\Users\\liuya\\Desktop\\pdf\\示例文件.pptx","C:\\Users\\liuya\\Desktop\\pdf\\示例文件123.pdf");
    }

    /**
     * ppt 转为 pdf 输出
     *
     * @param inPath  ppt 文件
     * @param outPath pdf 输出文件目录
     */
    public static String ppt2pdf(String inPath, String outPath) {
        License license = new License();
        license.setLicense("C:\\Users\\liuya\\Desktop\\jar\\Aspose.License.xml");
        FileOutputStream os = null;
        try {
            String path = outPath.substring(0, outPath.lastIndexOf(File.separator));
            File file = new File(path);
            // 创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }
            // 新建一个空白pdf文档
            file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的PPT幻灯片
            Presentation pres = new Presentation(new FileInputStream(inPath));
            pres.save(os, SaveFormat.Pdf);
            os.close();
        } catch (Exception e) {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return outPath;
    }




}

package com.tarzan.reptile.demo;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.assertj.core.util.Lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class PDFHelper {

    public static void main(String[] args) {
        separate(new File("C:/Users/liuya/Desktop/pdf//my_pdf.pdf"),5);
        File file1=new File("C:/Users/liuya/Desktop/pdf//my_pdf1~5.pdf");
        File file2=new File("C:/Users/liuya/Desktop/pdf//my_pdf6~770.pdf");
        List<File> files= Lists.newArrayList(file1, file2);
        merge(files,"C:/Users/liuya/Desktop/pdf//my_pdfnew.pdf");
    }



    public static boolean separate(File file, int page){
        try {
            PDDocument document = Loader.loadPDF(file);
            int pages=  document.getNumberOfPages();
            if(pages>page){
                separate(file,1,page);
                return separate(file,page+1,pages);
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
    public static boolean separate(File file,int startPage,int endPage){
        return separate(file,endPage-startPage+1,startPage,endPage);
    }
    public static boolean separate(File file,int page,int startPage,int endPage){
        try {
            //加载pdf文件
            PDDocument document = Loader.loadPDF(file);
            //初始化分离器
            Splitter splitter = new Splitter();
            splitter.setStartPage(startPage);
            splitter.setSplitAtPage(page);
            splitter.setEndPage(endPage);
            //分离后文件列表
            List<PDDocument> Pages = splitter.split(document);
            //创建遍历器
            Iterator<PDDocument> iterator = Pages.listIterator();
            //保存分离后的文件
            int i = 1;
            while (iterator.hasNext()) {
                PDDocument pd = iterator.next();
                String folderPath= file.getParent();
                String fileName=file.getName().substring(0,file.getName().lastIndexOf("."));
                int start=startPage+(i-1)*page;
                int end=i*page>endPage?i*page:endPage;
                pd.save(folderPath+File.separator+fileName+(start+"~"+end)+".pdf");
            }
            System.out.println("pdf分离完毕");
            document.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean merge(List<File> files,String path){
        PDFMergerUtility merger = new PDFMergerUtility();
        //设置合并文件地址
        merger.setDestinationFileName(path);
        files.forEach(f->{
            try {
                merger.addSource(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        try {
        //合并文件
        merger.mergeDocuments(null);
        System.out.println("合并完成");
        } catch (IOException e) {
            return  false;
        }
        return true;
    }

}

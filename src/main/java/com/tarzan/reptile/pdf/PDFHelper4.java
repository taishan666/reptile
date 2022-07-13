package com.tarzan.reptile.pdf;


import com.aspose.pdf.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PDFHelper4 {
    public static void main(String[] args) throws IOException {
        txt2pdf("C:\\Users\\liuya\\Desktop\\html\\test.txt");
       // md2pdf("C:\\Users\\liuya\\Desktop\\html\\h5.md");
    }
    //图片转pdf
    public static void image2pdf(String imagePath) throws IOException {
        Document doc = new Document();
        Page page = doc.getPages().add();
        Image image = new Image();
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String ext=imagePath.substring(imagePath.lastIndexOf("."));
        ImageIO.write(bufferedImage, ext, os);
        os.flush();
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        page.getParagraphs().add(image);
        image.setImageStream(is);
        String pdfPath=imagePath.substring(0,imagePath.lastIndexOf("."))+".pdf";
        doc.save(pdfPath);
    }

    //文本转pdf
    public static void txt2pdf(String txtPath) throws IOException {
        Document pdfDocument = new Document();
        Page page = pdfDocument.getPages().add();
        FileInputStream fis = new FileInputStream(txtPath);
        String txt= copyToString(fis,StandardCharsets.UTF_8);
        TextFragment text = new TextFragment(txt);
        page.getParagraphs().add(text);
        String pdfPath=txtPath.substring(0,txtPath.lastIndexOf("."))+".pdf";
        pdfDocument.save(pdfPath);
    }

    //markdown转pdf
    public static void md2pdf(String mdPath)  {
        MdLoadOptions options = new MdLoadOptions();
        Document document = new Document(mdPath, options);
        String pdfPath=mdPath.substring(0,mdPath.lastIndexOf("."))+".pdf";
        document.save(pdfPath);
    }

    public static String copyToString(InputStream in, Charset charset) throws IOException {
        if (in == null) {
            return "";
        } else {
            StringBuilder out = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, charset);
            char[] buffer = new char[4096];
            int bytesRead;
            while((bytesRead = reader.read(buffer)) != -1) {
                out.append(buffer, 0, bytesRead);
            }
            return out.toString();
        }
    }







    }

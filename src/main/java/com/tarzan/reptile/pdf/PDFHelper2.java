package com.tarzan.reptile.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class PDFHelper2 {


    static String pdfPwd = null;

    public static void main(String[] args) {
        //加密
       // encrypt("C:/Users/liuya/Desktop/pdf/HttpClient入门.pdf", "555555");
        //解密（去除密码）
      //  decryption("C:/Users/liuya/Desktop/pdf/HttpClient入门-加密.pdf", "555555");
        //纯数字密码暴力破解 num代表数字位数
       breakCode("C:/Users/liuya/Desktop/pdf/HttpClient入门-加密.pdf", 6);
    }

    //多线程处理
    public static String breakCode(String path, int num) {
        Long start = System.currentTimeMillis();
        List<String> pwdList = pwdList(num);
        int pageNum = 10000;
        int pages = pwdList.size() % pageNum == 0 ? pwdList.size() / pageNum : pwdList.size() / pageNum + 1;
        CountDownLatch cdLatch = new CountDownLatch(pages);
        for (int i = 0; i < pages; i++) {
            task(path, pwdList, i, pageNum, cdLatch);
        }
        try {
            cdLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(pdfPwd);
        System.out.println(System.currentTimeMillis() - start + "ms");
        return pdfPwd;
    }

    private static void breakCode(File file, List<String> pwdList) {
        int i = 0;
        int total = pwdList.size();
        double middle = (double) total / 2;
        while (i < middle) {
            if (pdfPwd != null) {
                break;
            }
            if (validate(file, pwdList.get(i))) {
                break;
            }
            if (i >= (int) middle) {
                break;
            }
            if (validate(file, pwdList.get(total - 1 - i))) {
                break;
            }
            ++i;
        }
        // System.out.println(Thread.currentThread().getName()+" "+i+"ds");
    }

    private static List<String> pwdList(int num) {
        List<String> list = new ArrayList<>();
        String max = "";
        for (int i = 0; i < num; i++) {
            max = max + 9;
        }
        int maxNum = Integer.valueOf(max);
        for (int i = 0; i <= maxNum; i++) {
            list.add(String.format("%0" + num + "d", i));
        }
        return list;
    }

    private static boolean validate(File file, String pwd) {
        try {
            Loader.loadPDF(file, pwd);
            pdfPwd = pwd;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean encrypt(String path, String pwd) {
        try {
            File file = new File(path);
            PDDocument load = Loader.loadPDF(file, pwd);
            AccessPermission permissions = new AccessPermission();
            permissions.setCanExtractContent(false);
            permissions.setCanModify(false);
            StandardProtectionPolicy p = new StandardProtectionPolicy(pwd, pwd, permissions);
            SecurityHandler sh = new StandardSecurityHandler(p);
            sh.prepareDocumentForEncryption(load);
            PDEncryption encryptionOptions = new PDEncryption();
            encryptionOptions.setSecurityHandler(sh);
            load.setEncryptionDictionary(encryptionOptions);
            String folderPath = file.getParent();
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            System.out.println("加密成功！");
            //保存文档
            load.save(folderPath + File.separator + fileName + "-加密" + ".pdf");
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean decryption(String path, String pwd) {
        try {
            File file = new File(path);
            PDDocument load = Loader.loadPDF(file, pwd);
            load.setAllSecurityToBeRemoved(true);
            String folderPath = file.getParent();
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            System.out.println("解密成功！");
            //保存文档
            load.save(folderPath + File.separator + fileName + "-解密" + ".pdf");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private static void task(String path, List<String> pwdList, int i, int pageNum, CountDownLatch cdLatch) {
        int endIndex = Math.min((i + 1) * pageNum, pwdList.size());
        List<String> list = pwdList.subList(i * pageNum, endIndex);
        Thread thread = new CRThread(path, list, cdLatch);
        thread.start();
    }

    static class CRThread extends Thread {
        private final String path;
        private final CountDownLatch cdLatch;
        private final List<String> list;

        public CRThread(String path, List<String> list, CountDownLatch cdLatch) {
            super();
            this.path = path;
            this.list = list;
            this.cdLatch = cdLatch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " " + list.get(0) + "~" + list.get(list.size() - 1));
            if (pdfPwd == null) {
                File file = new File(path);
                breakCode(file, list);
            }
            this.cdLatch.countDown();
        }

    }


}

package com.tarzan.reptile.pdf;

import javassist.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class ExcelJarCrack {
    public static void main(String[] args) throws Exception {
        String jarPath = "C:\\Users\\liuya\\Desktop\\jar\\aspose-cells-21.8.jar";
        crack(jarPath);
    }

    private static void crack(String jarName) throws NotFoundException, CannotCompileException, IOException {
        //这一步是完整的jar包路径
        ClassPool.getDefault().insertClassPath(jarName);
        CtClass LicenseClass = ClassPool.getDefault().getCtClass("com.aspose.cells.License");
        CtMethod[] aMethods = LicenseClass.getDeclaredMethods("a");
        for (CtMethod aMethod : aMethods) {
            CtClass returnType=aMethod.getReturnType();
            if(returnType.getName().equals("boolean")){
                aMethod.setBody("{return true;}");
                break;
            }
        }
        //将文件名命名成备份文件
       File file=new File(jarName);
       LicenseClass.writeFile(file.getParent());
       disposeJar(jarName);
    }

    private static void disposeJar(String jarName) {
        List<String> deletes = new ArrayList<>();
        deletes.add("META-INF/37E3C32D.SF");
        deletes.add("META-INF/37E3C32D.RSA");
        List<String> replaces = new ArrayList<>();
        replaces.add("com/aspose/cells/License.class");
        File oriFile = new File(jarName);
        if (!oriFile.exists()) {
            System.out.println("######Not Find File:" + jarName);
            return;
        }
        //将文件名命名成备份文件
        String bakJarName = jarName.substring(0, jarName.length() - 3) + "cracked.jar";
        try {
            //创建文件（根据备份文件并删除部分）
            JarFile jarFile = new JarFile(jarName);
            JarOutputStream jos = new JarOutputStream(new FileOutputStream(bakJarName));
            Enumeration entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                if (!deletes.contains(entry.getName())) {
                    if(replaces.contains(entry.getName())){
                        System.out.println("Replace:-------" +entry.getName());
                        JarEntry jarEntry = new JarEntry(entry.getName());
                        jos.putNextEntry(jarEntry);
                        FileInputStream fin = new FileInputStream(oriFile.getParent()+ "/"+entry.getName());
                        byte[] bytes = readStream(fin);
                        jos.write(bytes, 0, bytes.length);
                    }else {
                        jos.putNextEntry(entry);
                        byte[] bytes = readStream(jarFile.getInputStream(entry));
                        jos.write(bytes, 0, bytes.length);
                    }
                } else {
                    System.out.println("Delete:-------" + entry.getName());
                }
            }
            jos.flush();
            jos.close();
            jarFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

}


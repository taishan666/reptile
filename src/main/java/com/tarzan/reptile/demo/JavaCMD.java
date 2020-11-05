package com.tarzan.reptile.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;


/**
 * @author tarzan Liu
 */
public class JavaCMD {

    public static void main(String[] args) {
        wlanList();
        queryWlanPwd("华夏天信");
    }

    /**
     * java 执行cmd命令
     * */
    public static String run(String command) {
        Runtime runtime = Runtime.getRuntime();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(runtime.exec(command).getInputStream(), Charset.forName("GBK")));
            String line = null;
            StringBuffer buff = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buff.append(line + "\n");
            }
            return buff.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查看已经连接过的wifi名称
     * */
    public static void wlanList() {
      String result=  run("netsh wlan show profiles");
      String[] lines=result.split("\n");
      Arrays.asList(lines).forEach(e->{
          if(e.contains("所有用户配置文件")){
              System.out.println(e.replace("所有用户配置文件","已连接过的WIFI名称"));
          }
      });
    }

    /**
     * 查看已经连接过的wifi的密码
     * */
    public static String queryWlanPwd(String wlanName) {
        String result=  run("netsh wlan show profiles "+wlanName+" key=clear");
        String[] lines=result.split("\n");
        Arrays.asList(lines).forEach(e->{
            if(e.contains("关键内容")){
                System.out.println(e.replace("关键内容           ","WIFI密码"));
            }
        });
        return  result;
    }


}

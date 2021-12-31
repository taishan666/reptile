package com.tarzan.reptile.demo;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestUpload {


    public static String FilePost(String url, File value) throws  Exception {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, "--------------------"+BOUNDARY, Charset.defaultCharset());
        multipartEntity.addPart("categoryId",new StringBody("1",Charset.forName("UTF-8")));
        multipartEntity.addPart("storageType",new StringBody("1",Charset.forName("UTF-8")));
        multipartEntity.addPart("file", new FileBody(value));
        HttpPost request = new HttpPost(url);
        request.setEntity(multipartEntity);
        request.addHeader("Authorization","Basic c2FiZXI6c2FiZXJfc2VjcmV0");
        request.addHeader("Content-Type", "multipart/form-data; boundary=--------------------"+BOUNDARY);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);
        InputStream is = response.getEntity().getContent();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        System.out.println("发送消息收到的返回："+buffer);
        return buffer.toString();
    }

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(20);
        while(true){
            Thread thread= new Thread(() ->{
                try {
                    FilePost("http://172.16.10.201:20010/unify-resource/oss/endpoint/put-file-attach",new File("C:\\Users\\liuya\\Desktop\\图书馆.bmz"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            es.execute(thread);
        }
    }
}

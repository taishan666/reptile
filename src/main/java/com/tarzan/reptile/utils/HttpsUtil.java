package com.tarzan.reptile.utils;

import com.tarzan.reptile.enums.RequestMethodEnum;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by tarzan liu on 2020/5/5.
 */
public final class HttpsUtil {

    private HttpsUtil() { }

    public static String httpsRequest(String targetUrl, RequestMethodEnum methodEnum,
                                      String accessToken, String outputData) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(targetUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setRequestProperty("Content-Type", "application/json");
            httpUrlConn.setRequestProperty("Accept", "application/json");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            if (accessToken != null && !"".equals(accessToken)) {
                httpUrlConn.setRequestProperty("access-token", accessToken);
            }
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(methodEnum.getMethodName());
            if (RequestMethodEnum.GET == methodEnum) {
                httpUrlConn.connect();
            }
            if (outputData != null && !"".equals(outputData)) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputData.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String partResult;
            while ((partResult = bufferedReader.readLine()) != null) {
                sb.append(partResult);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            throw new Exception("网络请求出错");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
//        String request = httpsRequest("url",
//                RequestMethodEnum.GET, "47f02c9d51724879a93d8ac27e6ef71a", null);
//        System.out.println(request);

//        String request = httpsRequest("url",
//                RequestMethodEnum.POST, "47f02c9d51724879a93d8ac27e6ef71a", null);
//        System.out.println(request);

//        String request = httpsRequest("url",
//                RequestMethodEnum.DELETE, "47f02c9d51724879a93d8ac27e6ef71a", null);
//        System.out.println(request);

        String request = httpsRequest("http://top.baidu.com/buzz?b=1",
                RequestMethodEnum.GET, null, null);
        System.out.println(request);
    }
}

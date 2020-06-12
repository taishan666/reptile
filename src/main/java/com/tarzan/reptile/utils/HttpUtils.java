package com.tarzan.reptile.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tarzan liu on 2020/5/5.
 */
public final class HttpUtils {

    private HttpUtils() {}

    /**
     * contentType: application/json
     */
    public static String post(String targetUrl, Map<String, String> headers, String jsonString) {
        StringBuilder sb = new StringBuilder();
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(targetUrl);
            if (StringUtils.isNotBlank(jsonString)) {
                StringEntity stringEntity = new StringEntity(jsonString);
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
            }
            if (MapUtils.isNotEmpty(headers)) {
                setHeaders(httpPost, headers);
            }
            HttpResponse response = httpclient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
               sb.append(EntityUtils.toString(response.getEntity()));
            }
            httpPost.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * contentType: key/value
     */
    public static String post(String url, Map<String, String> headers,  Map<String, String> paramsMap) {
        StringBuilder sb = new StringBuilder();
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(StringUtils.trim(url));
            if (MapUtils.isNotEmpty(paramsMap)) {
                List<NameValuePair> params = getParams(paramsMap);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            }
            if (MapUtils.isNotEmpty(headers)) {
                setHeaders(httpPost, headers);
            }
            CloseableHttpResponse response = httpclient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                sb.append(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
            httpPost.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * GET (INCLUDE HEADERS)
     */
    public static String get(String url, Map<String, String> headers) {
        StringBuilder sb = new StringBuilder();
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(StringUtils.trim(url));
            if (MapUtils.isNotEmpty(headers)) {
                setHeaders(httpGet, headers);
            }
            CloseableHttpResponse response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                sb.append(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
            httpGet.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void setHeaders(HttpRequestBase request, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.setHeader(entry.getKey(), entry.getValue());
        }
    }

    private static List<NameValuePair> getParams(Map<String, String> paramsMap) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (String key : paramsMap.keySet()) {
            params.add(new BasicNameValuePair(key, paramsMap.get(key)));
        }
        return params;
    }

    public static String setParams(String address, Map<String, String> params) {
        if (MapUtils.isNotEmpty(params)) {
            StringBuilder sb = new StringBuilder(address);
            sb.append("?");
            for (String key : params.keySet()) {
                sb.append(key + "=" + params.get(key));
                sb.append("&");
            }
            String origin = sb.toString();
            return origin.substring(0, origin.length() - 1);
        }
        return address;
    }

    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("access-token", "523726c76fb1419dbb5114cd2f446f58");

        String result = get("targetUrl", headers);
        System.out.println(result);

//        String result = post("targetUrl", headers, "{\n" +
//                "  \"channelOrderNo\": \"1111\",\n" +
//                "  \"money\": 1000\n" +
//                "}");
//        System.out.println(result);
    }
}

package com.wangpeng.bms.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.omg.IOP.Encoding;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

public class HttpTemplate {
    public static String httpGet(String urlIn) throws IOException, JSONException {
        URL url = new URL(urlIn); // 替换为你的 URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        StringBuilder res= new StringBuilder();
        // 设置接受 Gzip 编码的请求头
        connection.setRequestProperty("Accept-Encoding", "gzip");

        // 检查服务器响应是否为 Gzip 编码
        if ("gzip".equals(connection.getContentEncoding())) {
            try (GZIPInputStream gzip = new GZIPInputStream(connection.getInputStream());
                 BufferedReader reader = new BufferedReader(new InputStreamReader(gzip,"UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                   res.append(line);
                }
            }
        } else {
            // 如果响应未压缩，则正常读取
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }

        JSONObject jsonObj = new JSONObject(res.toString());
        JSONObject now = jsonObj.getJSONObject("now");

        if(jsonObj.getString("code").equals("200")){
            String temp = now.getString("temp");
            String finalRes ="温度: "+now.getString("temp")+"\n体感温度:"+now.getString("feelsLike")+"\n天气: "+now.getString("text");
            return finalRes;
        }
        else{
            return "网络异常，请稍后再试";
        }
// 从 now 对象中提取 temp 属性

    }
    //网上的方法，提交的东西不确定，最好别用
    public static String httpPost(String url,String name) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(url, name, String.class).getBody();
    }
}

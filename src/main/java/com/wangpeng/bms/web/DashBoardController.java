package com.wangpeng.bms.web;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.wangpeng.bms.utils.HttpTemplate;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {
    @RequestMapping("/getWeather")
    public String getWeather() throws IOException, JSONException {
        //参数key和location不能改
        String s=HttpTemplate.httpGet("https://devapi.qweather.com/v7/weather/now?location=101190301&key=5401731109174491b87acfe0cf847b14");
        return s;
    }
}

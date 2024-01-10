package com.wangpeng.bms.web;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.wangpeng.bms.model.BookType;
import com.wangpeng.bms.service.BookInfoService;
import com.wangpeng.bms.service.BookTypeService;
import com.wangpeng.bms.utils.HttpTemplate;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {
    @Autowired
    BookTypeService bookTypeService;
    @Autowired
    BookInfoService bookInfoService;
    @RequestMapping("/getWeather")
    @ResponseBody
    public String getWeather() throws IOException, JSONException {
        //参数key和location不能改
        String s=HttpTemplate.httpGet("https://devapi.qweather.com/v7/weather/now?location=101190301&key=5401731109174491b87acfe0cf847b14");
        return s;
    }

    @RequestMapping("/getPieChartInfo")
    @ResponseBody
    public Map<String,Integer>getPieChartInfo(){
        List<BookType> types=bookTypeService.queryBookTypes();
        Map<String,Integer> map=new HashMap<>();
        for(int i=0;i<types.size();i++){
            map.put(types.get(i).getBooktypename(),bookInfoService.getTypeCount(i));
        }
        return map;
    }
}

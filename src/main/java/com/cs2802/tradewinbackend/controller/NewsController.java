package com.cs2802.tradewinbackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.service.NewsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("dashboard")
@CrossOrigin
public class NewsController {
    @Resource
    private NewsService newsService=new NewsService();
    @GetMapping("news")
    public JSONArray newsApi() throws IOException {
        String api_url = "https://www.alphavantage.co/query?function=NEWS_SENTIMENT&tickers=COIN,CRYPTO:BTC,FOREX:USD&time_from=20220410T0130&limit=200&apikey=KITRCC1SJYWEBN7I";
        /*OkHttp_Get okHttpGet=new OkHttp_Get();
        String str=okHttpGet.run(api_url);
        JSONObject jsonObject= JSON.parseObject(str);*/
        JSONObject jsonObject = newsService.getNewsService(api_url);

        String values = jsonObject.getString("feed");
        JSONArray valuesArray = JSON.parseArray(values);

        int len = valuesArray.size();
        JSONArray resultArray = new JSONArray();
        for (int i = 0; i < len; i++) {
            String innerValueStr = valuesArray.getString(i);
            JSONObject innerValueObject = JSON.parseObject(innerValueStr);
            JSONObject eachNew = new JSONObject();
            String summary = innerValueObject.getString("summary");  //content
            String url = innerValueObject.getString("url");  // href
            String title = innerValueObject.getString("title");  // title
            String banner_image = innerValueObject.getString("banner_image");  // avatar
            eachNew.put("href", url);
            eachNew.put("title", title);
            eachNew.put("avatar", banner_image);
            eachNew.put("content", summary);
            resultArray.add(i, eachNew);
        }
        return resultArray;
    }
}

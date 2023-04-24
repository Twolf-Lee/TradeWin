package com.cs2802.tradewinbackend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NewsService {
    public JSONObject getNewsService(String api_url) throws IOException {
        OkHttp_Get okHttpApi = new OkHttp_Get();
        String run=okHttpApi.run(api_url);
        JSONObject jsonObject= JSON.parseObject(run);
        System.out.println(jsonObject);
        return jsonObject;
    }
}

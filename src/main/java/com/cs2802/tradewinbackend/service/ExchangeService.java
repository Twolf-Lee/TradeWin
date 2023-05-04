package com.cs2802.tradewinbackend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ExchangeService {

    public JSONObject getExchangeService(String api_url) throws IOException {
        OkHttp_Get okHttpApi = new OkHttp_Get();
        String run=okHttpApi.run(api_url);
        JSONObject jsonObject= JSON.parseObject(run);
        return jsonObject;
    }

    public JSONArray getExchangeServiceArray(String api_url) throws IOException {
        OkHttp_Get okHttpApi = new OkHttp_Get();
        String run=okHttpApi.run(api_url);
        JSONArray jsonArray= JSON.parseArray(run);
        return jsonArray;
    }
}

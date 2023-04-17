package TestApi;

import com.alibaba.fastjson.JSON;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;

public class TestOpenExchangeRatesApi {
    public static void main(String[] args) throws IOException {
        OkHttp_Get api=new OkHttp_Get();
        String run=api.run("https://openexchangerates.org/api/latest.json?app_id=61b6e7c5be9b4e329bec58eea49c3688");
        JSON json= JSON.parseObject(run);
        System.out.println(json);
    }
}

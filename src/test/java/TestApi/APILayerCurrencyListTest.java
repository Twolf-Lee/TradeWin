package TestApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;

public class APILayerCurrencyListTest {
    public static void main(String[] args) throws IOException {
        String api_url = "https://api.apilayer.com/currency_data/convert?from=CNY&to=IDR&amount=100";
        OkHttp_Get okHttpGet = new OkHttp_Get();
        String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");
        JSONObject jsonObject= JSON.parseObject(run);
        JSONObject info=jsonObject.getJSONObject("info");
        String price=info.getString("quote");
        String title="CNY to IDR";
        JSONObject one_result=new JSONObject();
        one_result.put(title,price);

        System.out.println(one_result);
    }
}

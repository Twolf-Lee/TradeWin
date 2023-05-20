package TestApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;

public class coinMarketCapTest {
    public static void main(String[] args) throws IOException {
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String run=okHttpGet.run("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=DOGE","X-CMC_PRO_API_KEY","4c2d5cbd-6aaa-4025-af79-f23305a89e7e"); //4c2d5cbd-6aaa-4025-af79-f23305a89e7e
        JSONObject jsonObject= JSON.parseObject(run);
        String price=jsonObject.getJSONObject("data").getJSONObject("DOGE").getJSONObject("quote").getJSONObject("USD").getString("price");
        System.out.println(price);

    }
}

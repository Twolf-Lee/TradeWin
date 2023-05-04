package TestApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;
import java.util.Iterator;

public class currencylayerApiTest {
    public static void main(String[] args) throws IOException {
        // xvhJYlbcBPOOfYbJsyliEougAxTcqigr
        String api_url="https://api.apilayer.com/currency_data/timeframe?&source=CNY&currencies=IDR&start_date=2022-09-01&end_date=2023-04-20";
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String run=okHttpGet.run(api_url,"apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");
        JSONObject jsonObject= JSON.parseObject(run);
        JSONObject date_value=jsonObject.getJSONObject("quotes");
        System.out.println(date_value);
        JSONObject one_result=new JSONObject();
        Iterator<String> keys= date_value.keySet().iterator();
        while (keys.hasNext()){
            String one_key=keys.next();
            JSONObject price_pair=date_value.getJSONObject(one_key);
            String price=price_pair.getString("CNYIDR");
            one_result.put(one_key,price);
        }
        System.out.println(one_result);
    }
}

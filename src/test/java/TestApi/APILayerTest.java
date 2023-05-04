package TestApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;
import java.util.Iterator;

public class APILayerTest {
    public static void main(String[] args) throws IOException {
        JSONObject result=new JSONObject();
        String[] from_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        String[] to_list=new String[]{"CNY","IDR","THB","PHP","MYR","INR","AED","SAR","BRL","MOP","ZAR","TRY","KRW","TWD"};
        for(int i=0;i<5;i++) {
            for (int j = 0; j < 5; j++) {
                if (j != i) {
                    String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=" + from_list[i] + "&currencies=" + to_list[j] + "&start_date=2022-05-01&end_date=2023-04-20";
                    OkHttp_Get okHttpGet = new OkHttp_Get();
                    String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");

                    JSONObject jsonObject=JSON.parseObject(run);
                    JSONObject date_value=jsonObject.getJSONObject("quotes");
                    JSONObject one_result=new JSONObject();
                    Iterator<String> keys= date_value.keySet().iterator();
                    while (keys.hasNext()){
                        String one_key=keys.next();
                        JSONObject price_pair=date_value.getJSONObject(one_key);
                        String price=price_pair.getString(from_list[i]+to_list[j]);
                        one_result.put(one_key,price);
                    }
                    result.put(from_list[i]+" to "+to_list[j],one_result);
                }
            }
        }
        System.out.println(result);
    }
}

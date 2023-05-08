package SearchBar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;
import java.io.IOException;
import java.util.Iterator;

public class searchchartTest {
    public static void main(String[] args) throws IOException {
        String from="CNY";
        String to="IDR";
        String earliest_day="2022-11-01";
        String today="2023-05-01";
        String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=" + from + "&currencies=" + to + "&start_date="+earliest_day+"&end_date="+today;
        OkHttp_Get okHttpGet = new OkHttp_Get();

        String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");
        System.out.println(run);
        JSONObject jsonObject= JSON.parseObject(run);
        JSONObject date_value=jsonObject.getJSONObject("quotes");
        System.out.println(date_value);

        JSONObject one_result=new JSONObject();
        Iterator<String> keys= date_value.keySet().iterator();
        while (keys.hasNext()){
            String one_key=keys.next();
            JSONObject price_pair=date_value.getJSONObject(one_key);
            String price=price_pair.getString(from+to);
            one_result.put(one_key,price);
        }
        System.out.println(one_result);
    }
}

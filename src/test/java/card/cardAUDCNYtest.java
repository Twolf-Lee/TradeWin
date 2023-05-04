package card;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class cardAUDCNYtest {
    public static void main(String[] args) throws IOException {
        // Optimize part
        // 获取当天日期，并格式化日期
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String today = dateFormat.format(date);
        System.out.println("today: "+today);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -300);
        date=calendar.getTime();
        String earliest_day = dateFormat.format(date);
        System.out.println("earliest day: "+earliest_day);
        // Optimize end

        String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=AUD&currencies=CNY&start_date=" + earliest_day + "&end_date=" + today;
        //String api_url = "https://api.apilayer.com/currency_data/timeframe?&source=AUD&currencies=CNY&start_date=2022-07-08&end_date=2023-05-04";
        OkHttp_Get okHttpGet = new OkHttp_Get();
        String run = okHttpGet.run(api_url, "apikey", "xvhJYlbcBPOOfYbJsyliEougAxTcqigr");
        JSONArray result = new JSONArray();

        JSONObject jsonObject = JSON.parseObject(run);
        JSONObject date_value = jsonObject.getJSONObject("quotes");
        JSONObject date_price = new JSONObject();
        Iterator<String> keys = date_value.keySet().iterator();
        while (keys.hasNext()) {
            String one_key = keys.next();
            JSONObject price_pair = date_value.getJSONObject(one_key);
            String price = price_pair.getString("AUDCNY");
            date_price.put(one_key, price);
        }

        calendar.add(Calendar.DAY_OF_MONTH, +300);
        date=calendar.getTime();
        calendar.setTime(date);
        String first_day = dateFormat.format(date);   // 有数据的最新一天
        while (date_price.getString(first_day) == null) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            first_day = dateFormat.format(date);
        }
        System.out.println(first_day);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String second_day = dateFormat.format(date);  // 有数据的最新一天的前一天
        while (date_price.getString(second_day) == null) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            second_day = dateFormat.format(date);
        }
        System.out.println(second_day);
        String first_day_price_str = date_price.getString(first_day);
        String second_day_price_str = date_price.getString(second_day);
        System.out.println(first_day_price_str);
        System.out.println(second_day_price_str);
        Double first_day_price = Double.valueOf(first_day_price_str);
        Double second_day_price = Double.valueOf(second_day_price_str);
        Double calculation = (first_day_price - second_day_price) / second_day_price;

        calculation = calculation * 100;
        BigDecimal bd = new BigDecimal(calculation);
        Double cal_format = bd.setScale(2, RoundingMode.DOWN).doubleValue();
        JSONObject one_pair_number = new JSONObject();
        one_pair_number.put("title", "AUDtoCNY");
        one_pair_number.put("rate", cal_format);
        result.add(0, one_pair_number);
        System.out.println(result);
    }
}

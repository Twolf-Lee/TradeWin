package calculator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cs2802.tradewinbackend.utils.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class calculatorTest {
    public static void main(String[] args) {
        // 模拟前端传过来的参数
        String from="CNY";
        String to="MYR";

        String host = "https://ali-waihui.showapi.com";
        String path = "/waihui-transform";
        String method = "GET";
        String appcode = "ebcc48918b4641c0863b277230e119d8";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("fromCode", from);
        querys.put("money", "100");
        querys.put("toCode", to);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println("***"+response.getEntity().getContent());
            //获取response的body
            HttpEntity entity = response.getEntity();
            String responsebody= EntityUtils.toString(entity);
            System.out.println(responsebody);
            String[] lines = responsebody.split("\n");
            Pattern pattern = Pattern.compile("\"showapi_res_body\":\\s*(\\{.*?\\})");
            Matcher matcher = pattern.matcher(lines[5]);
            if (matcher.find()) {
                String result = matcher.group(1);
                System.out.println(result);
                JSONObject jsonObject= JSON.parseObject(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

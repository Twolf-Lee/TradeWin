package TestApi;

import com.alibaba.fastjson.JSON;
import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestAlphaApi {
    public static void main(String[] args) throws IOException {
        /*OkHttpApi_Get okHttpApi = new OkHttpApi_Get();
        String run=okHttpApi.run("https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=EUR&to_symbol=USD&apikey=KITRCC1SJYWEBN7I");
        System.out.println("Test the daily FX prices(from EUR to USD):");
        System.out.println(run);
        JSON jsonObject= JSON.parseObject(run);*/
        //System.out.println(jsonObject);

        OkHttp_Get api=new OkHttp_Get();

        List<List<String>> data=new ArrayList<>();
        String filePath="./src/main/resources/CSV/physical_currency_list.csv";
        FileReader fr=new FileReader(filePath);
        BufferedReader br=new BufferedReader(fr);
        String line=br.readLine();
        while(line!=null){
            List<String> linedata= Arrays.asList(line.split(","));
            data.add(linedata);
            line=br.readLine();
        }
        for(int i=1;i<data.size();i++){
            String run=api.run(String.format("https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=%s&to_symbol=USD&apikey=KITRCC1SJYWEBN7I",data.get(i).get(0)));
            JSON jsonObject=JSON.parseObject(run);
            System.out.println(jsonObject);
        }
    }
}

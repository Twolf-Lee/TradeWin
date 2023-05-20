package TestApi;

import com.cs2802.tradewinbackend.utils.OkHttp_Get;

import java.io.IOException;

public class MiFengChaAPI {
    public static void main(String[] args) throws IOException {
        OkHttp_Get okHttpGet=new OkHttp_Get();
        String run=okHttpGet.run("https://data.block.cc/api/v1/price/history","X-API-KEY","BQDOKSQBRZQER2FPKYD28DVFTOCEMMJ1VYV15UBO"); //BQDOKSQBRZQER2FPKYD28DVFTOCEMMJ1VYV15UBO
        System.out.println(run);
    }
}

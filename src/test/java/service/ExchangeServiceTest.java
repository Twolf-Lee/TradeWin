package service;
import com.alibaba.fastjson.JSON;
import com.cs2802.tradewinbackend.service.ExchangeService;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeServiceTest {
    static String currencyString="AED AFN ALL AMD ANG AOA ARS AUD AWG AZN BAM BBD BDT BGN BHD BIF BMD BND BOB BRL BSD BTN BWP BZD CAD CDF CHF CLF CLP CNH CNY COP CUP CVE CZK DJF DKK DOP DZD EGP ERN ETB EUR FJD FKP GBP GEL GHS GIP GMD GNF GTQ GYD HKD HNL HRK HTG HUF ICP IDR ILS INR IQD IRR ISK JEP JMD JOD JPY KES KGS KHR KMF KPW KRW KWD KYD KZT LAK LBP LKR LRD LSL LYD MAD MDL MGA MKD MMK MNT MOP MRO MRU MUR MVR MWK MXN MYR MZN NAD NGN NOK NPR NZD OMR PAB PEN PGK PHP PKR PLN PYG QAR RON RSD RUB RUR RWF SAR SBDf SCR SDG SDR SEK SGD SHP SLL SOS SRD SYP SZL THB TJS TMT TND TOP TRY TTD TWD TZS UAH UGX USD UYU UZS VND VUV WST XAF XCD XDR XOF XPF YER ZAR ZMW ZWL";
    static String[] currencyArray = currencyString.split(" ");
    //static String api_url="https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=EUR&to_symbol=USD&apikey=KITRCC1SJYWEBN7I";

    public static void main(String[] args) throws IOException {

        List<String> api_string=new ArrayList<String>();
        for(int i=0;i<currencyArray.length;i++){
            for(int j=i+1;j<currencyArray.length-1;j++){
                api_string.add("https://www.alphavantage.co/query?function=FX_DAILY&from_symbol="+currencyArray[i]+"&to_symbol="+currencyArray[j]+"&apikey=KITRCC1SJYWEBN7I");
            }
        }
//        for (int i=0;i<api_string.size();i++){
//            System.out.println(api_string);
//        }
//        ExchangeService exchangeService = new ExchangeService();
//        for(int i=0;i<api_string.size();i++){
//            JSON result=exchangeService.getExchangeService(api_string.get(i));
//        }
    }
}

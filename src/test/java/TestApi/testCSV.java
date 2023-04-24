package TestApi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testCSV {
    public static void main(String[] args) throws IOException {
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
        for(int i=0;i<data.size();i++){
            for(int j=i+1;j<data.size()-1;j++){
                System.out.println("from "+data.get(i).get(0)+" to "+data.get(j).get(0));
            }
        }
    }
}

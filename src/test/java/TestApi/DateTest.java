package TestApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        date=calendar.getTime();
        String yesterday=dateFormat.format(date);
        System.out.println(yesterday);
    }
}

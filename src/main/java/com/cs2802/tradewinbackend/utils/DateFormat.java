package com.cs2802.tradewinbackend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    // 该工具类提供一些日期处理的方法
    public String getTodayDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String today=dateFormat.format(date);
        return today;
    }
}

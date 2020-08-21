package com.example.locationsave.HEP.KSH;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KSH_Date {

    public String nowDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        String date = format.format(time);
        return date;
    }

    public String testDate(){
        return "testDate";
    }
}

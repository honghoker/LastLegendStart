package com.example.locationsave.HEP.KSH;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KSH_Date {

    public String nowDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date time = new Date();

        String date = format.format(time);
        Log.d("5", "date 확인   " + date);
        return date;
    }

    public String testDate(){
        return "testDate";
    }
}

package com.example.locationsave.HEP.Hep.hep_LocationSave;

import java.util.ArrayList;

public class hep_HashTagArr {
    private static ArrayList<String> hashTagArr = null;

    public ArrayList<String> getHashTagArr(){
        if(hashTagArr == null)
            hashTagArr = new ArrayList<>();
        return hashTagArr;
    }

}

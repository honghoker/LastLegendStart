package com.example.locationsave.HEP.Hep;

import java.util.ArrayList;

public class hep_locationImageDataArr {

    private static ArrayList<hep_ImageData> imageDataArrayList;

    public ArrayList<hep_ImageData> getImageDataArrayInstance(){
        if(imageDataArrayList == null)
            imageDataArrayList = new ArrayList<>();
        return imageDataArrayList;
    }
}

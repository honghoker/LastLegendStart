package com.example.locationsave.HEP.Hep.hep_LocationSave;

import java.util.ArrayList;

public class hep_locationImageDataArr {

    private static ArrayList<hep_ImageData> imageDataArrayList;

    public ArrayList<hep_ImageData> getImageDataArrayInstance(){
        if(imageDataArrayList == null)
            imageDataArrayList = new ArrayList<>();
        return imageDataArrayList;
    }

    public void setImageDataArraySize(int size){
        for(int i = 0; i < size; i++)
            imageDataArrayList.add(new hep_ImageData());
    }
}

package com.example.locationsave.HEP.Hep.hep_DTO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class hep_LocationImages {
    public String Image0;
    public String Image1;
    public String Image2;
    public String Image3;
    public String Image4;
    //public String LocationId;

    public hep_LocationImages(){

    }

    public ArrayList<String> getImageBitmapArr(){
        ArrayList<String> ImageList = new ArrayList<>();
        ImageList.add(Image0);
        ImageList.add(Image1);
        ImageList.add(Image2);
        ImageList.add(Image3);
        ImageList.add(Image4);

        return ImageList;
    }
}

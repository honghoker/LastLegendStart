package com.example.locationsave.HEP.Hep.hep_DTO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public ArrayList<Bitmap> getImageBitmapArr(){
        ArrayList<String> Base64ImageList = new ArrayList<>();
        Base64ImageList.add(Image0);
        Base64ImageList.add(Image1);
        Base64ImageList.add(Image2);
        Base64ImageList.add(Image3);
        Base64ImageList.add(Image4);

        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            if(Base64ImageList.get(i) != null){
                byte[] decodedString = Base64.decode(Base64ImageList.get(i), Base64.NO_WRAP);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                bitmapArrayList.add(decodedByte);
            }
        }
        return bitmapArrayList;
    }
}

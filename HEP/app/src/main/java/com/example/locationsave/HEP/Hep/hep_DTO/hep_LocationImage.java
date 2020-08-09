package com.example.locationsave.HEP.Hep.hep_DTO;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class hep_LocationImage {
    public String locationid;
    public String imageid;

    public hep_LocationImage(){

    }

    public String getLocationid() {
        return locationid;
    }

    public String getImageid() {
        return imageid;
    }
}

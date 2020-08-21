package com.example.locationsave.HEP.KMS.Location;

public class KMS_LocationFlagManager {
    private static final KMS_LocationFlagManager LocationInstance = new KMS_LocationFlagManager();

    private KMS_LocationFlagManager() {}

    public static KMS_LocationFlagManager getInstanceLocation(){
        return LocationInstance;
    }

    boolean locationFlag = false;

    public boolean flagGetLocation(){
        return locationFlag;
    }

    public void flagSetTrueLocation(){
        locationFlag = true;
    }

    public void flagSetFalseLocation(){
        locationFlag = false;
    }
}
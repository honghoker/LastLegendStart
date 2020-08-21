package com.example.locationsave.HEP.KMS.Map;

public class KMS_MarkerInformationFlagManager {
    private static final KMS_MarkerInformationFlagManager markerInformationFlagManagerInstance = new KMS_MarkerInformationFlagManager();

    private KMS_MarkerInformationFlagManager() {}

    public static KMS_MarkerInformationFlagManager getMarkerInformationFlagManagerInstance(){
        return markerInformationFlagManagerInstance;
    }

    boolean markerInformationFlag = false;

    public boolean flagGetMarkerInformationFlag(){
        return markerInformationFlag;
    }

    public void flagSetTrueMarkerInformation(){
        markerInformationFlag = true;
    }

    public void flagSetFalseMarkerInformation(){
        markerInformationFlag = false;
    }
}
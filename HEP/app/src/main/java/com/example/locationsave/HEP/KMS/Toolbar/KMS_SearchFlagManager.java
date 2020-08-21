package com.example.locationsave.HEP.KMS.Toolbar;

public class KMS_SearchFlagManager {
    private static final KMS_SearchFlagManager searchInstance = new KMS_SearchFlagManager();

    private KMS_SearchFlagManager() {}

    public static KMS_SearchFlagManager getInstanceSearch(){
        return searchInstance;
    }

    boolean searchFlag = false;

    public boolean flagGetSearch(){
        return searchFlag;
    }

    public void flagSetTrueSearch(){
        searchFlag = true;
    }

    public void flagSetFalseSearch(){
        searchFlag = false;
    }
}
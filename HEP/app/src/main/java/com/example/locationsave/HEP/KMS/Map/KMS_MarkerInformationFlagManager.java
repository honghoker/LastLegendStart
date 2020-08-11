package com.example.locationsave.HEP.KMS.Map;

import android.util.Log;

import com.example.locationsave.HEP.KMS.Toolbar.KMS_SearchFlagManager;

public class KMS_MarkerInformationFlagManager {
    private static final KMS_MarkerInformationFlagManager markerInformationFlagManagerInstance = new KMS_MarkerInformationFlagManager(); //정적 변수에 인스턴스를 만들어 바로 초기화
    //정적 변수는 객체가 생성되기 전 클래스가 메모리에 로딩될 때 만들어진다. 따라서, 초기에 한번 생성된 인스턴스를 반환.

    private KMS_MarkerInformationFlagManager() {}

    public static KMS_MarkerInformationFlagManager getMarkerInformationFlagManagerInstance(){
        return markerInformationFlagManagerInstance;
    }

/*private static KMS_MarkerInformationFlagManager markerInformationFlagManagerInstance = null;

    //public KMS_MarkerManager() {}

    public KMS_MarkerInformationFlagManager getInstanceMarkerInformationFlagManager(){
        if(markerInformationFlagManagerInstance == null)
            markerInformationFlagManagerInstance = new KMS_MarkerInformationFlagManager();
        return markerInformationFlagManagerInstance;
    }*/

    boolean markerInformationFlag = false;

    public void flagChangeMarkerInformation(){
        if(markerInformationFlag == true) {
            flagSetFalseMarkerInformation();
        }

        else if(markerInformationFlag == false){
            flagSetTrueMarkerInformation();
        }
    }

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

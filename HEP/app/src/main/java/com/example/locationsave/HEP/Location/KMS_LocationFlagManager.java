package com.example.kms_lastlegendstart.Location;

import android.util.Log;

public class KMS_LocationFlagManager {
    private static final KMS_LocationFlagManager LocationInstance = new KMS_LocationFlagManager(); //정적 변수에 인스턴스를 만들어 바로 초기화
    //정적 변수는 객체가 생성되기 전 클래스가 메모리에 로딩될 때 만들어진다. 따라서, 초기에 한번 생성된 인스턴스를 반환.

    private KMS_LocationFlagManager() {}

    public static KMS_LocationFlagManager getInstanceLocation(){
        return LocationInstance;
    }

        /*
    1. flag 선언
    2. flag 변경
    3. flag 확인
    4. flag set true
    5. flag set false
     */

    boolean locationFlag = false;

    public void flagChangeRLocation(){
        if(locationFlag == true) {
            flagSetFalseLocation();
            Log.d("####Location","flagChange Location sington change flag = " + locationFlag);
        }

        else if(locationFlag == false){
            flagSetTrueLocation();
            Log.d("####Location","flagChange Location sington change flag = " + locationFlag);
        }
    }

    public boolean flagGetLocation(){
        Log.d("####Location","flagChange Location sington check flag = " + locationFlag);
        return locationFlag;

    }

    public void flagSetTrueLocation(){
        locationFlag = true;
        Log.d("####Location","Location sington " + locationFlag);
    }

    public void flagSetFalseLocation(){
        locationFlag = false;
        Log.d("####Location","Location sington " + locationFlag);
    }
}

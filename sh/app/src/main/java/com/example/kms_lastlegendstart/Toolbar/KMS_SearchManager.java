package com.example.kms_lastlegendstart.Toolbar;

import android.util.Log;

public class KMS_SearchManager {
    private static final KMS_SearchManager searchInstance = new KMS_SearchManager(); //정적 변수에 인스턴스를 만들어 바로 초기화
    //정적 변수는 객체가 생성되기 전 클래스가 메모리에 로딩될 때 만들어진다. 따라서, 초기에 한번 생성된 인스턴스를 반환.

    private KMS_SearchManager() {}

    public static KMS_SearchManager getInstanceSearch(){
        return searchInstance;
    }

        /*
    1. flag 선언
    2. flag 변경
    3. flag 확인
    4. flag set true
    5. flag set false
     */

    boolean searchFlag = false;

    public void flagChangeSearch(){
        if(searchFlag == true) {
            flagSetFalseSearch();
            Log.d("####searchFlag","searchFlag Change  = " + searchFlag);
        }

        else if(searchFlag == false){
            flagSetTrueSearch();
            Log.d("####searchFlag","searchFlag Change  = " + searchFlag);
        }
    }

    public boolean flagGetSearch(){
        Log.d("####searchFlag","Searchflag get = " + searchFlag);
        return searchFlag;

    }

    public void flagSetTrueSearch(){
        searchFlag = true;
        Log.d("####searchFlag","recycleView sington " + searchFlag);
    }

    public void flagSetFalseSearch(){
        searchFlag = false;
        Log.d("####searchFlag","recycleView sington " + searchFlag);
    }
}

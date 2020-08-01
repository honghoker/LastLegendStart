package com.example.locationsave.HEP.KMS.Toolbar;

import android.util.Log;

public class KMS_RecycleVIewManager {
    private static final KMS_RecycleVIewManager recyclevIewInstance = new KMS_RecycleVIewManager(); //정적 변수에 인스턴스를 만들어 바로 초기화
    //정적 변수는 객체가 생성되기 전 클래스가 메모리에 로딩될 때 만들어진다. 따라서, 초기에 한번 생성된 인스턴스를 반환.

    private KMS_RecycleVIewManager() {}

    public static KMS_RecycleVIewManager getInstanceRecycleView(){
        return recyclevIewInstance;
    }

        /*
    1. flag 선언
    2. flag 변경
    3. flag 확인
    4. flag set true
    5. flag set false
     */

    boolean recycleViewFlag = false;

    public void flagChangeRecycleView(){
        if(recycleViewFlag == true) {
            flagSetFalseRecycleView();
            Log.d("####Bottombar","flagChange recycleView sington change flag = " + recycleViewFlag);
        }

        else if(recycleViewFlag == false){
            flagSetTrueRecycleView();
            Log.d("####recycleView","flagChange recycleView sington change flag = " + recycleViewFlag);
        }
    }

    public boolean flagCheckRecycleView(){
        Log.d("####recycleView","flagChange recycleView sington check flag = " + recycleViewFlag);
        return recycleViewFlag;

    }

    public void flagSetTrueRecycleView(){
        recycleViewFlag = true;
        Log.d("####recycleView","recycleView sington " + recycleViewFlag);
    }

    public void flagSetFalseRecycleView(){
        recycleViewFlag = false;
        Log.d("####recycleView","recycleView sington " + recycleViewFlag);
    }
}

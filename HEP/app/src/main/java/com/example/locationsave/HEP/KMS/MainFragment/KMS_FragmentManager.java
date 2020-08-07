package com.example.locationsave.HEP.KMS.MainFragment;

import android.util.Log;


public class KMS_FragmentManager {
    private static final KMS_FragmentManager fragmentInstance = new KMS_FragmentManager(); //정적 변수에 인스턴스를 만들어 바로 초기화
                            //정적 변수는 객체가 생성되기 전 클래스가 메모리에 로딩될 때 만들어진다. 따라서, 초기에 한번 생성된 인스턴스를 반환.

    private KMS_FragmentManager() {}


    public static KMS_FragmentManager getInstanceFragment(){
        return fragmentInstance;
    }
    /*
    1. flag 선언
    2. flag 변경
    3. flag 확인
    4. flag set true
    5. flag set false
     */
    boolean fragmentFlag = true; //1. flag 선언


    public void flagChangeFragment(){ //2. flag 변경
        if(fragmentFlag == true) {
            flagSetFalseFragment();
            Log.d("####Bottombar","flagChangeFragment sington change flag = " + fragmentFlag);
        }

        else if(fragmentFlag == false){
            flagSetTrueFragment();
            Log.d("####Bottombar","flagChangeFragment sington change flag = " + fragmentFlag);
        }
    }

    public boolean flagCheckFragment(){ //3. flag 확인
        Log.d("####Bottombar","flagChangeFragment sington check flag = " + fragmentFlag);
        return fragmentFlag;

    }

    public void flagSetTrueFragment(){ //4. flag set true
        fragmentFlag = true;
        Log.d("####Bottombar","FragmentManager sington " + fragmentFlag);
    }

    public void flagSetFalseFragment(){ //5. flag set false
        fragmentFlag = false;
        Log.d("FragmentManager sington " + fragmentFlag,"####");
    }


}

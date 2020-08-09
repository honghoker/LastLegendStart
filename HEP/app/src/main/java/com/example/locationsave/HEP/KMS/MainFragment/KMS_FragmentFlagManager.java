package com.example.locationsave.HEP.KMS.MainFragment;

import androidx.fragment.app.ListFragment;

import com.example.locationsave.HEP.pcs_RecyclerView.locationList.Pcs_LocationRecyclerView;
import com.example.locationsave.R;

import static com.example.locationsave.HEP.KMS_MainActivity.LocationFragmet;
import static com.example.locationsave.HEP.KMS_MainActivity.fragmentManager;
import static com.example.locationsave.HEP.KMS_MainActivity.mapFragment;

public class KMS_FragmentFlagManager {
    private static final KMS_FragmentFlagManager fragmentInstance = new KMS_FragmentFlagManager(); //정적 변수에 인스턴스를 만들어 바로 초기화
                            //정적 변수는 객체가 생성되기 전 클래스가 메모리에 로딩될 때 만들어진다. 따라서, 초기에 한번 생성된 인스턴스를 반환.

    private KMS_FragmentFlagManager() {}

    public static KMS_FragmentFlagManager getInstanceFragment(){
        return fragmentInstance;
    }

    boolean fragmentFlag = true; //1. flag 선언

    public void flagChangeFragment(){ //2. flag 변경
        if(fragmentFlag == true) {
            flagSetFalseFragment();
        }

        else if(fragmentFlag == false){
            flagSetTrueFragment();
        }
    }

    public boolean flagCheckFragment(){ //3. flag 확인
        return fragmentFlag;
    }

    public void flagSetTrueFragment(){ //4. flag set true
        fragmentFlag = true;
    }

    public void flagSetFalseFragment(){ //5. flag set false
        fragmentFlag = false;
    }

    public void setFragmentMapLayout(){ //맵 프레그먼트 출력 함수
        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commit();/*프래그먼트 매니저가 프래그먼트를 담당한다!*/
        if (mapFragment == null) {
            mapFragment = new ListFragment();
            fragmentManager.beginTransaction().add(R.id.frameLayout, mapFragment).commit();
        }
        if (mapFragment != null) {
            //clearSearchBar(ac); //서치바 초기화
            fragmentManager.beginTransaction().show(mapFragment).commit();
            //Toast.makeText(this, "맵 생성완료", Toast.LENGTH_SHORT).show();
        }
        if (LocationFragmet != null)
            fragmentManager.beginTransaction().hide(LocationFragmet).commit();
    }

    public void setFragmentLocationListLayout() {  //리스트 프레그먼트 출력 시
        if (LocationFragmet == null) {
            LocationFragmet = new Pcs_LocationRecyclerView();
            fragmentManager.beginTransaction().add(R.id.frameLayout, LocationFragmet).commit();
        }
        if (mapFragment != null) {
            fragmentManager.beginTransaction().hide(mapFragment).commit();
        }
        if (LocationFragmet != null){
            fragmentManager.beginTransaction().show(LocationFragmet).commit();
        }
    }

}

package com.example.locationsave.HEP.KMS.MainFragment;

import androidx.fragment.app.ListFragment;

import com.example.locationsave.HEP.pcs_RecyclerView.locationList.Pcs_LocationRecyclerView;
import com.example.locationsave.R;

import static com.example.locationsave.HEP.KMS_MainActivity.LocationFragmet;
import static com.example.locationsave.HEP.KMS_MainActivity.fragmentManager;
import static com.example.locationsave.HEP.KMS_MainActivity.mapFragment;

public class KMS_FragmentFlagManager {
    private static final KMS_FragmentFlagManager fragmentInstance = new KMS_FragmentFlagManager();

    private KMS_FragmentFlagManager() {}

    public static KMS_FragmentFlagManager getInstanceFragment(){
        return fragmentInstance;
    }

    boolean fragmentFlag = true;

    public boolean flagCheckFragment(){ //3. flag 확인
        return fragmentFlag;
    }

    public void flagSetTrueFragment(){ //4. flag set true
        fragmentFlag = true;
    }

    public void flagSetFalseFragment(){ //5. flag set false
        fragmentFlag = false;
    }

    public void setFragmentMapLayout(){
        if (mapFragment == null) {
            mapFragment = new ListFragment();
            fragmentManager.beginTransaction().add(R.id.frameLayout, mapFragment).commit();
        }
        if (mapFragment != null) {
            fragmentManager.beginTransaction().show(mapFragment).commit();
        }
        if (LocationFragmet != null)
            fragmentManager.beginTransaction().hide(LocationFragmet).commit();
    }

    public void setFragmentLocationListLayout() {
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
package com.example.locationsave.HEP.KMS.BottomBar;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locationsave.HEP.KMS.MainFragment.KMS_FragmentFlagManager;

public class KMS_BottomBar extends AppCompatActivity {
    KMS_FragmentFlagManager kms_fragmentFlagManager = KMS_FragmentFlagManager.getInstanceFragment();

    public void setBottomBar(LinearLayout bottomBar, boolean Flag) { //searchFlag 에 맞게 하단 바 가리기
        if (Flag == true)
            bottomBar.setVisibility(View.GONE);
        else
            bottomBar.setVisibility(View.VISIBLE);
    }
//
//    public void onBottomBarClicked(View v) { //Change Fragment
//        switch (v.getId()) {
//            case R.id.btnMain:
//                //fragmentFlag = true;
//                kms_fragmentFlagManager.flagSetTrueFragment(); //프레그먼트 플래그 true 로 변경
//                kms_fragmentFlagManager.setFragmentMapLayout();
//                break;
//            case R.id.btnLocationList:
//                //fragmentFlag = false;
//                kms_fragmentFlagManager.flagSetFalseFragment(); //프래그먼트 플래그 false 로 변경
//                kms_fragmentFlagManager.setFragmentLocationListLayout();
//                break;
//            default:
//                break;
//        }
//    }
}

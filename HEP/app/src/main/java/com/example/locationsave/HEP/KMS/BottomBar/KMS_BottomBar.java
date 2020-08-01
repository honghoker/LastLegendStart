package com.example.locationsave.HEP.KMS.BottomBar;

import android.view.View;
import android.widget.LinearLayout;

public class KMS_BottomBar {

    public void setBottomBar(LinearLayout bottomBar, boolean Flag) { //searchFlag 에 맞게 하단 바 가리기
        if (Flag == true)
            bottomBar.setVisibility(View.GONE);
        else
            bottomBar.setVisibility(View.VISIBLE);
    }
}

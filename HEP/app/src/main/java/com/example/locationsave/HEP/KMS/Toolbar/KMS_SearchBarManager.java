package com.example.locationsave.HEP.KMS.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class KMS_SearchBarManager {
    public void setOffLoadLocationSearchBar(RelativeLayout relativeLayout){
        if (relativeLayout.getVisibility() == View.VISIBLE) {
            Log.d("####KMS_SelectLocation","SetResultRecyclerLayout 숨김");
            //Toast.makeText(context, "검색 바 / 서브 툴바 미출력", Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public void setOnLoadLocationSearchBar(RelativeLayout relativeLayout) {
        if (relativeLayout.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
            Log.d("####KMS_SelectLocation", "SetResultRecyclerLayout 보임");
            //Toast.makeText(context, "검색 바 / 서브 툴바 출력", Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
}

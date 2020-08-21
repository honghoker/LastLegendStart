package com.example.locationsave.HEP.KMS.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class KSH_LoadLocation extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void SetLinearLayout(Context context, RelativeLayout relativeLayout) {
        Log.d("####KMS_SelectLocation.SetLinearLayout", "샛리니어레이아웃 실행");
        if (relativeLayout.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
            //Toast.makeText(context, "검색 바 / 서브 툴바 출력", Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.VISIBLE);
        } else if (relativeLayout.getVisibility() == View.VISIBLE) {
            //Toast.makeText(context, "검색 바 / 서브 툴바 미출력", Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.GONE);
        } //서브 툴바 숨기기
    }

    public void setOnSearchResultRecyclerView(Context context, RecyclerView recyclerView) {
        if (recyclerView.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
            //Toast.makeText(context, "검색 바 / 서브 툴바 출력", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void setOffSearchResultRecyclerView2(Context context, RecyclerView recyclerView) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            //Toast.makeText(context, "검색 바 / 서브 툴바 미출력", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
        }
    }
}

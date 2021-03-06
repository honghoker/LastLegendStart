package com.example.locationsave.HEP.KMS.Location;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class KMS_SelectLocation extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void SetLinearLayout(Context context, RelativeLayout relativeLayout) {
        Log.d("####KMS_SelectLocation.SetLinearLayout","샛리니어레이아웃 실행");
        if (relativeLayout.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
            Log.d("####KMS_SelectLocation.SetLinearLayout","장소추가 안보이면 보이게 변경");
            //Toast.makeText(context, "검색 바 / 서브 툴바 출력", Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.VISIBLE);
        } else if (relativeLayout.getVisibility() == View.VISIBLE) {
            Log.d("####KMS_SelectLocation.SetLinearLayout","장소추가 보이면 안보이게 변경");
            //Toast.makeText(context, "검색 바 / 서브 툴바 미출력", Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.GONE);
        } //서브 툴바 숨기기
//            setSupportActionBar(MainActivity.toolbarMain); //메인 툴바에 권한줌
//            getActionBar().show(); //메인 툴바 출력
//            toolbarSub.setTitle("메인툴바 출력");
//            li.setVisibility(View.GONE);
    }

    public void setSearchResultRecyclerView(Context context, RecyclerView recyclerView){
        if (recyclerView.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
            Log.d("####KMS_SelectLocation","SetResultRecyclerLayout 보임");
            //Toast.makeText(context, "검색 바 / 서브 툴바 출력", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.VISIBLE);
        } else if (recyclerView.getVisibility() == View.VISIBLE) {
            Log.d("####KMS_SelectLocation","SetResultRecyclerLayout 숨김");
            //Toast.makeText(context, "검색 바 / 서브 툴바 미출력", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
        }
    }

    public void clickBtn(View v){
        switch (v.getId()){

        }

    }

}

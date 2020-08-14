package com.example.locationsave.HEP.KSH.NavIntent;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.HEP.KSH.KSH_RecyAdapter;
import com.example.locationsave.HEP.KSH.sunghunTest;
import com.example.locationsave.R;

import java.util.ArrayList;


public class KSH_NoticeIntent extends AppCompatActivity {
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    KSH_NoticeAdapter ksh_noticeAdapter;

    // 공지사항 쓸일 있으면 array에 담아서 adapter로 넘겨줘야함
    ArrayList<KSH_NoticeEntity> arrayList;

    public void init(){
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("공지사항");
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.notice_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_intent_notice);
        init();

        // 뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ksh_noticeAdapter = new KSH_NoticeAdapter();
        recyclerView.setAdapter(ksh_noticeAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

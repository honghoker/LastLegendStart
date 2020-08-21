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

import com.example.locationsave.R;


public class KSH_NoticeIntent extends AppCompatActivity {
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    KSH_NoticeAdapter ksh_noticeAdapter;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ksh_noticeAdapter = new KSH_NoticeAdapter();
        recyclerView.setAdapter(ksh_noticeAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

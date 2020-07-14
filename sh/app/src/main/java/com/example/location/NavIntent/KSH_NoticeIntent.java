package com.example.location.NavIntent;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.location.R;

public class KSH_NoticeIntent extends AppCompatActivity {
    private Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_intent_notice);
        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("공지사항");
        setSupportActionBar(toolbar);

        // 뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
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

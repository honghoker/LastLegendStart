package com.example.location;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.NavIntent.KSH_NoticeIntent;
import com.example.location.RecyclerView.KSH_RecyAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KSH_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private boolean recyFrag = false;
    private RecyclerView recyclerView;
    private View rView;
    private View fView;
    private View allSeeView;
    private RecyclerView.Adapter recyAdapter;
    private ArrayList<KSH_TestEntity> arrayList;
    private ArrayList<String> arrayKey;
    private DatabaseReference databaseReference;
    private String directoryKey;
    private Spinner spinner;
    private Toolbar toolbar;
    private NavigationView navigationView;

    public void init(){
        setContentView(R.layout.ksh_activity_main);
        toolbar = findViewById(R.id.dra_toolbar);
        setSupportActionBar(toolbar);
        spinner = findViewById(R.id.spinner);
        rView = findViewById(R.id.include_recyclerView);
        // frameLayout 위에 recyclerView가 나타나야함으로 frameLayout 선언
        fView = findViewById(R.id.frameLayout);
        allSeeView = findViewById(R.id.recy_allSee);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        arrayList = new ArrayList<>();  // 객체 담아서 adapter로 보낼 arraylist
        arrayKey = new ArrayList<>();
//        firebaseDatabase = FirebaseDatabase.getInstance();  // firebase db 연동
//        databaseReference = firebaseDatabase.getReference().child("Test");  // db table 연결
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // 싱글톤
        KSH_FireBase firebaseDatabase = KSH_FireBase.getInstance(getApplicationContext());
        databaseReference = firebaseDatabase.databaseReference;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                // firebase db의 data를 받아오는 곳
                arrayList.clear();
                arrayKey.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    KSH_TestEntity ksh_testEntity = snapshot.getValue(KSH_TestEntity.class); // 만들어둔 Test 객체에 데이터를 담는다
                    String key = snapshot.getKey();
                    String title = ksh_testEntity.getTitle();
                    arrayList.add(ksh_testEntity);  // 담은 데이터들을 arraylist에 넣고 recyclerview로 보낼 준비
                    arrayKey.add(key);
                }
                recyAdapter.notifyDataSetChanged(); // list 저장 및 새로고침
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // db 가져오던 중 error 발생 시
                Log.d("1", " error "+String.valueOf(error.toException()));
            }
        });

        recyAdapter = new KSH_RecyAdapter(this,arrayList,directoryKey);
        recyclerView.setAdapter(recyAdapter);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // spinner 터치(클릭) 시 이벤트처리
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && recyFrag == false) {
                    setSpinner();
                } else if (event.getAction() == MotionEvent.ACTION_DOWN && recyFrag == true) {
                    setSpinner();
                }
                return true;
            }

            public void setSpinner(){
                if(recyFrag == true){
                    Animation animationH = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translatehide);
                    rView.setAnimation(animationH);
                    rView.setVisibility(fView.GONE);
                    recyFrag = false;
                }
                else {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
                    rView.setAnimation(animation);
                    rView.setVisibility(fView.VISIBLE);
                    recyFrag = true;
                }
            }
        });

        allSeeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fView.getContext(), KSH_AllSeeActivity.class);
                intent.putExtra("array",arrayList);
                intent.putExtra("key",arrayKey);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

    }

    // 어플키면 먼저 map 보여주기위한 fragmentManager.begin 코드 들어가야함 !
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_notice:
                Intent intent = new Intent(this, KSH_NoticeIntent.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                // 밑에는 실수로 fragment
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KSH_NoticetFragment()).commit();
                break;
            case R.id.nav_excel:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KSH_ExceltFragment()).commit();
                break;
            case R.id.nav_call:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KSH_CalltFragment()).commit();
                break;
            case R.id.nav_help:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KSH_HelptFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

}

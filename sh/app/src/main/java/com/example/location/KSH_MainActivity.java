package com.example.location;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KSH_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    boolean recyFrag = false;
    RecyclerView recyclerView;
    View rView;
    View fView;
    View allSeeView;
    KSH_RecyAdapter recyAdapter;

    // firebase test
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("test");

    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String text = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_activity_main);

        Toolbar toolbar = findViewById(R.id.dra_toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = findViewById(R.id.spinner);

        rView = findViewById(R.id.include_recyclerView);

        // frameLayout 위에 recyclerView가 나타나야함으로 frameLayout 선언
        fView = findViewById(R.id.frameLayout);

        allSeeView = findViewById(R.id.recy_allSee);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyAdapter = new KSH_RecyAdapter(this);
        recyclerView.setAdapter(recyAdapter);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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
                // firebase realtime db test
                conditionRef.setValue("확인");
//                Log.d("1","allSeebtn 확인");
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
//                this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
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

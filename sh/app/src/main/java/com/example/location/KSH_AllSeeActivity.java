package com.example.location;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class KSH_AllSeeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView.Adapter allSeeRecyAdapter;
    private ArrayList<KSH_TestEntity> arrayList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private Intent intent;
    private ArrayList<String> arrayKey;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_allsee_main);
        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("전체보기");
        setSupportActionBar(toolbar);
        // 뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.allsee_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        intent = getIntent();


//        arrayList = (ArrayList<KSH_TestEntity>) intent.getExtras().get("array");
        arrayList = (ArrayList<KSH_TestEntity>) intent.getSerializableExtra("array");
        arrayKey = (ArrayList<String>) intent.getSerializableExtra("key");
        Log.d("1","Allsee array size " + arrayList.size());
//        arrayList = new ArrayList<>();  // 객체 담아서 adapter로 보낼 arraylist

        firebaseDatabase = FirebaseDatabase.getInstance();  // firebase db 연동
        databaseReference = firebaseDatabase.getReference("Test");  // db table 연결
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrayList.clear();
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    KSH_TestEntity ksh_testEntity = snapshot.getValue(KSH_TestEntity.class); // 만들어둔 Test 객체에 데이터를 담는다
//                    arrayList.add(ksh_testEntity);  // 담은 데이터들을 arraylist에 넣고 recyclerview로 보낼 준비
//                }
//                allSeeRecyAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        allSeeRecyAdapter = new KSH_AllSeeAdapter(this,arrayList,databaseReference,firebaseDatabase,arrayKey);
        recyclerView.setAdapter(allSeeRecyAdapter);
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

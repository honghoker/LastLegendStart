package com.example.locationsave.HEP.KSH;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;
import com.example.locationsave.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KSH_AllSeeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView.Adapter allSeeRecyAdapter;
    private ArrayList<KSH_DirectoryEntity> arrayList;
//    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference TagdatabaseReference;
    private RecyclerView recyclerView;
    private Intent intent;
    private ArrayList<String> arrayKey;

    public void init(){
        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("전체보기");
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.allsee_recycler_view);

        // 싱글톤
        KSH_FireBase firebaseDatabase = KSH_FireBase.getInstance();
        databaseReference = firebaseDatabase.databaseReference;
        TagdatabaseReference = firebaseDatabase.TagdatabaseReference;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_allsee_main);
        init();


        // 모르겠다 ㅅㅂ
        TagdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    hep_Tag hep_tag = snapshot.getValue(hep_Tag.class);
//                    Log.d("aaaa", hep_tag.name);
                    String key = snapshot.getKey();
                    Log.d("aaaa",key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // 뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        intent = getIntent();

        arrayList = (ArrayList<KSH_DirectoryEntity>) intent.getSerializableExtra("array");
        arrayKey = (ArrayList<String>) intent.getSerializableExtra("key");
        Log.d("1","Allsee array size " + arrayList.size());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    KSH_DirectoryEntity ksh_directoryEntity = snapshot.getValue(KSH_DirectoryEntity.class); // 만들어둔 Test 객체에 데이터를 담는다
                    arrayList.add(ksh_directoryEntity);  // 담은 데이터들을 arraylist에 넣고 recyclerview로 보낼 준비
                }
                allSeeRecyAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        View view = getLayoutInflater().inflate(R.layout.ksh_allsee_update,null);
        allSeeRecyAdapter = new KSH_AllSeeAdapter(this,arrayList,arrayKey);
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

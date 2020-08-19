package com.example.locationsave.HEP.KMS.HashTag;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxFlagManager.hashTagText;
import static com.example.locationsave.HEP.KMS_MainActivity.msHashTag;
import static com.example.locationsave.HEP.KMS_MainActivity.params;

public class KMS_HashTagCheckBoxManager {

    private int TAG_TABLE_ENTITY_COUNT = 0;
    CheckBox checkBoxAllHashTag; //체크박스 명 선언
    Context context;
    View view;
    String imsi = "";
    private KMS_HashTag[] kms_hashTags;

    public KMS_HashTag[] get_hashTags() {
        return kms_hashTags;
    }

    public KMS_HashTagCheckBoxManager(Context context, View view){
        this.context = context;
        this.view = view;
    }

    //해시태그 선택
    public class HasTagOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d("6","asdasdasd");
            OnHashTagClickListener();
        }
    }
    HasTagOnClickListener hasTagOnClickListener = new HasTagOnClickListener();

    public void initHashTag(final Pcs_HashTagCallback pcs_hashTagCallback) { //초기 해시태그 세팅
        setHashTagSize(new Pcs_HashTagCallback() {
            @Override
            public void onSuccess(final KMS_HashTag[] kms_hashTags) {
                new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag").orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int i = 1;
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                kms_hashTags[i] = new KMS_HashTag(context);
                                kms_hashTags[i].setOnClickListener(hasTagOnClickListener);
                                kms_hashTags[i].setId(i);
                                kms_hashTags[i].init(dataSnapshot.getValue(hep_Tag.class).getName(), "#22FFFF", R.drawable.hashtagborder, params);
//                        Log.d("tag", i + " " + dataSnapshot.getValue(hep_Tag.class).getName());
                                ((KMS_FlowLayout) view.findViewById(R.id.HashTagflowlayout)).addView(kms_hashTags[i]);
                                i++;
                            }
                        }
                        pcs_hashTagCallback.onSuccess(kms_hashTags);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }//addHashTag 종료

    public void checkAllHashTag() { //해시태그 모두 선택
        checkBoxAllHashTag = view.findViewById(R.id.checkBox);
        checkBoxAllHashTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxAllHashTag.isChecked()) {
//                    kms_hashTagCheckBoxFlagManager.CheckBoxAllClick(context);
                } else
                    CheckBoxAllUnClick(context);
            }
        });
    } //checkAllHashTag 종료

    public void AddClickHashTag(Context context) {
        //어레이 리스트를 스트링으로 변경 후 쿼리문 날리기
        String[] stockArr = new String[hashTagText.size()]; //어레이 리스트 스트링으로 변환
        stockArr = hashTagText.toArray(stockArr);

        for(String s : stockArr)
            imsi += s + " ";
        Toast.makeText(context,"Hashtag : " + imsi ,Toast.LENGTH_SHORT).show();
        imsi = "";
    }
    public void CheckBoxAllUnClick(Context context) {
        KMS_HashTag[] hs = kms_hashTags;
        for (int j = 1; j < hs.length; j++) {
            hs[j].init(hs[j].getHashText(), "#3F729B", R.drawable.hashtagunclick, params);
            hs[j].setId(j);
            hashTagText.clear();
        } //for 종료
    }


    private void setHashTagSize(final Pcs_HashTagCallback pcs_hashTagCallback) {
        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    TAG_TABLE_ENTITY_COUNT = (int) snapshot.getChildrenCount()+1; //Start 1
                }
                pcs_hashTagCallback.onSuccess(new KMS_HashTag[TAG_TABLE_ENTITY_COUNT]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void OnHashTagClickListener(){
        for (int j = 1; j < kms_hashTags.length; j++) {
            if (view.getId() == j) {
                kms_hashTags[j].init(kms_hashTags[j].getHashText(), "#3F729B", R.drawable.hashtagclick, params);
                kms_hashTags[j].setId(-j);
                Toast.makeText(context,"id : " + kms_hashTags[j].getId() + "/ text : " + kms_hashTags[j].getHashText(),Toast.LENGTH_SHORT).show();
                hashTagText.add(kms_hashTags[j].getHashText());
                break;
            } else if (view.getId() == -j) {
                kms_hashTags[j].init(kms_hashTags[j].getHashText(), "#3F729B", R.drawable.hashtagunclick, params);
                kms_hashTags[j].setId(j);
                hashTagText.remove(kms_hashTags[j].getHashText());
                break;
            }
        }//for 문 종료
    }
}

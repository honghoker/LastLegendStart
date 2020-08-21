package com.example.locationsave.HEP.KMS.HashTag;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.View.inflate;
import static com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxFlagManager.hashTagText;
import static com.example.locationsave.HEP.KMS_MainActivity.hastagView;
import static com.example.locationsave.HEP.KMS_MainActivity.msHashTag;
import static com.example.locationsave.HEP.KMS_MainActivity.params;

public class KMS_HashTagCheckBoxManager {

    private int TAG_TABLE_ENTITY_COUNT = 0;
    CheckBox checkBoxAllHashTag; //체크박스 명 선언
    View view;
    String imsi = "";
    private Animation animation;
    private Activity activity;
    private ArrayList<String> selectedHashTagOfKey = new ArrayList<>();
    private ArrayList<Integer> undoSeleted = new ArrayList<>();


    public KMS_HashTagCheckBoxManager(Activity activity, View view, Animation animation){
        this.view = view;
        this.activity = activity;
        this.animation = animation;

    }
    public void onHashTagClickListener(View v){
        KMS_HashTag[] kms_hashTags = msHashTag;
        for (int j = 1; j < kms_hashTags.length; j++) {
            if (v.getId() == j) {
                kms_hashTags[j].init(kms_hashTags[j].getHashText(), "#3F729B", R.drawable.hashtagclick, params);
                kms_hashTags[j].setId(-j);
                Toast.makeText(activity,"id : " + kms_hashTags[j].getId() + "/ text : " + kms_hashTags[j].getHashText(),Toast.LENGTH_SHORT).show();
                selectedHashTagOfKey.add(kms_hashTags[j].getTagKey());
                //When user Click tag, store UndoSeleted.
                //If user click cancel button, user  return clicked tag to unclicked tag layout
                undoSeleted.add(-j);
                for(int a : undoSeleted){
                    Log.d("tag", "select " + a);
                }
                break;
            } else if (v.getId() == -j) {
                kms_hashTags[j].init(kms_hashTags[j].getHashText(), "#22FFFF", R.drawable.hashtagborder, params);
                kms_hashTags[j].setId(j);
                selectedHashTagOfKey.remove(kms_hashTags[j].getTagKey());
                undoSeleted.add(j);
                for(int a : undoSeleted){
                    Log.d("tag", "dis " + a);
                }

                break;
            }
        }//for 문 종료
    }

    public ArrayList<String> getSelectedHashTagOfKey() {
        return selectedHashTagOfKey;
    }

    //해시태그 선택
    public class HasTagOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d("6","This is HashTag Clicked Tag ");
            onHashTagClickListener(v);
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
                            Log.d("6","This is HashTag Clicked Tag For ");
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                kms_hashTags[i] = new KMS_HashTag(activity);
                                kms_hashTags[i].setOnClickListener(hasTagOnClickListener);
                                kms_hashTags[i].setId(i);
                                kms_hashTags[i].setTagKey(dataSnapshot.getKey());
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
                    CheckBoxAllUnClick(activity);
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
//        KMS_HashTag[] hs = kms_hashTags;
//        for (int j = 1; j < hs.length; j++) {
//            hs[j].init(hs[j].getHashText(), "#3F729B", R.drawable.hashtagunclick, params);
//            hs[j].setId(j);
//            hashTagText.clear();
//        } //for 종료
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


    public boolean pcs_setHashtagFlag(Boolean hashTagLayoutFlag, View mView){
        Log.d("tag", String.valueOf(hashTagLayoutFlag));
        if(hashTagLayoutFlag){
            hastagView.setAnimation(animation);
            hastagView.setVisibility(mView.GONE);
            pcs_UndoTag();
        }else{
            hastagView.setAnimation(animation);
            hastagView.setVisibility(mView.VISIBLE);
        }
        return hastagView.getVisibility() == VISIBLE;
    }
    //If user Press seletButton,
    public boolean pcs_setHashtagFlag(Boolean hashTagLayoutFlag, View mView, String callName){
        if(hashTagLayoutFlag){
            hastagView.setAnimation(animation);
            hastagView.setVisibility(mView.GONE);
            pcs_wrapItUp();
        }else{
            hastagView.setAnimation(animation);
            hastagView.setVisibility(mView.VISIBLE);
        }
        return hastagView.getVisibility() == VISIBLE;
    }

    public void pcs_wrapItUp(){
        undoSeleted.clear();
    }

    public void pcs_UndoTag(){
        for(int a : undoSeleted){
            Log.d("tag", "Undo seleted " + a);
        }

        if(!undoSeleted.isEmpty()){
            //When you Click tag, store UndoSeleted
            for(int i = 0; i < undoSeleted.size(); i++){
                for(int j = 1; j< msHashTag.length; j++){
                    Log.d("tag", "msHashTag Id " + msHashTag[j].getId());
                    if(msHashTag[j].getId() == undoSeleted.get(i)){{
                        if((int)msHashTag[j].getId() < 0){
                            Log.d("tag", " ");
                            //select -> deselect
                            msHashTag[j].init(msHashTag[j].getHashText(), "#22FFFF", R.drawable.hashtagborder, params);
                            msHashTag[j].setId(-j);
                            selectedHashTagOfKey.remove(msHashTag[j].getTagKey());
                        }else{
                            //Select -> deselect

                            msHashTag[j].init(msHashTag[j].getHashText(), "#3F729B", R.drawable.hashtagclick, params);
                            msHashTag[j].setId(-j);
                            selectedHashTagOfKey.add(msHashTag[j].getTagKey());
                        }
                    }
                    }
                }
            }
        }
        pcs_wrapItUp();
    }



}

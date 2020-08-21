package com.example.locationsave.HEP.KMS.Toolbar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_HangulUtils;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSave_AutoCompleteTextView;
import com.example.locationsave.R;

import java.util.ArrayList;

import static com.example.locationsave.HEP.KMS_MainActivity.autoCompleteLocationList;
import static com.example.locationsave.HEP.KMS_MainActivity.ksh_loadLocation;
import static com.example.locationsave.HEP.KMS_MainActivity.loadRecyclerView;
import static com.example.locationsave.HEP.KMS_MainActivity.mLinearLayoutManager;


public class KMS_ClearableEditText_LoadLocation_auto extends RelativeLayout {

    LayoutInflater inflater = null;
    public static hep_LocationSave_AutoCompleteTextView editText_1;
    Button btnClear;
    public static Context mContext;
    static InputMethodManager ime = null;

    public KMS_ClearableEditText_LoadLocation_auto(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setLayout();
        setinit();
        ime = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void setLayout(){
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.kms_clearable_edit_text_load_location, this, true);
    }

    public void setinit(){
        editText_1 = findViewById(R.id.clearable_edit_load_location); //저장된 장소 검색
        editText_1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //오토 완성 코드
                ime.hideSoftInputFromWindow(editText_1.getWindowToken(),0);
                return false;
            }
        });

        btnClear = (Button) findViewById(R.id.clearable_load_location_button_clear);
        btnClear.setVisibility(RelativeLayout.INVISIBLE);

        clearText();
        showHideClearButton();
    }

    //X버튼이 나타났다 사라지게하는 메소드
    private void showHideClearButton() {
        //TextWatcher를 사용해 에디트 텍스트 내용이 변경 될 때 동작할 코드를 입력
        editText_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //에디트 텍스트 안 내용이 변경될 때마다 호출되는 메소드
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hep_LocationSave_AutoCompleteTextView autoCompleteTextView = editText_1;

                if (s.length() > 0) {
                    btnClear.setVisibility(RelativeLayout.VISIBLE);

                    KSH_LoadResultAdapter mAdapter = new KSH_LoadResultAdapter(setChangeListData(s.toString()));
                    loadRecyclerView.setAdapter(mAdapter);

                    if(String.valueOf(setChangeListData(s.toString())).equals("[]")){
                        ksh_loadLocation.setOffSearchResultRecyclerView2(mContext, loadRecyclerView);
                        loadRecyclerView.setLayoutManager(mLinearLayoutManager);
                    }
                    if(!String.valueOf(setChangeListData(s.toString())).equals("[]")){
                        ksh_loadLocation.setOnSearchResultRecyclerView(mContext, loadRecyclerView);
                        loadRecyclerView.setLayoutManager(mLinearLayoutManager);
                    }

                } else {
                    ksh_loadLocation.setOffSearchResultRecyclerView2(mContext, loadRecyclerView);
                    loadRecyclerView.setLayoutManager(mLinearLayoutManager);
                    btnClear.setVisibility(RelativeLayout.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public ArrayList<hep_Location> setChangeListData(String searchKeyword){
        ArrayList<hep_Location> temp = new ArrayList<>();

        for(int i = 0; i < autoCompleteLocationList.size(); i++){
            boolean isAdd = false;
            String iniName = hep_HangulUtils.getHangulInitialSound(autoCompleteLocationList.get(i).name, searchKeyword.toString());
            if(iniName.indexOf(searchKeyword.toString()) >= 0){
                isAdd = true;
            }
            if(isAdd){
                temp.add(autoCompleteLocationList.get(i));
            }
        }

        return temp;
    }


    private void clearText() {
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_1.setText("");
            }
        });
    }
}

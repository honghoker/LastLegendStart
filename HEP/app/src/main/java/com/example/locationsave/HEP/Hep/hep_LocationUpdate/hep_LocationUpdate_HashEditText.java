package com.example.locationsave.HEP.Hep.hep_LocationUpdate;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_HangulUtils;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSave_AutoCompleteTextView;
import com.example.locationsave.R;

import java.util.ArrayList;
import java.util.List;

public class hep_LocationUpdate_HashEditText extends RelativeLayout {
    LayoutInflater inflater = null;
    hep_LocationSave_AutoCompleteTextView hashEditText;
    Button btnClearHashEditText;
    public static Context mContext;

    public hep_LocationUpdate_HashEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setLayout();
        setinit();
    }

    public void setLayout(){
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hep_hashedittext, this, true);
    }

    public void setinit(){
        hashEditText = findViewById(R.id.HashTagText);
        btnClearHashEditText = findViewById(R.id.btnClearHashTagText);
        btnClearHashEditText.setVisibility(RelativeLayout.INVISIBLE);
        clearHashEditText();
        showHideClearButton();
    }

    //X버튼이 나타났다 사라지게하는 메소드
    private void showHideClearButton() {
        //TextWatcher를 사용해 에디트 텍스트 내용이 변경 될 때 동작할 코드를 입력
        hashEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //에디트 텍스트 안 내용이 변경될 때마다 호출되는 메소드
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hep_LocationSave_AutoCompleteTextView autoCompleteTextView = hashEditText;

                if (s.length() > 0) {
                    btnClearHashEditText.setVisibility(RelativeLayout.VISIBLE);

                    //LocationRepository locationRepository = new LocationRepository(mContext.getApplicationContext());

                    //String query = searchSql(s.toString()); // 초성검색 SQL
                    //List list = locationRepository.searchTag(query);

                    autoCompleteTextView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, setChangeListData(s.toString())));
                    autoCompleteTextView.showDropDown();
                } else {
                    btnClearHashEditText.setVisibility(RelativeLayout.INVISIBLE);
                    autoCompleteTextView.dismissDropDown();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(" ")) {
                    ((hep_LocationUpdateActivity) mContext).hashTagAdd(s.toString().replaceAll(" ", "").trim());
                }
            }
        });
    }

    /* 초성검색
    public String searchSql(String searchStr) {
        String sql = "SELECT tag FROM Tag_Database WHERE " + ChoSearchQuery.makeQuery(searchStr);
        return sql;
    }
    */

    private void clearHashEditText() {
        btnClearHashEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hashEditText.setText("");
            }
        });
    }

    public List setChangeListData(String searchKeyword) {
        List temp = new ArrayList<>();

        ArrayList<String> tag = ((hep_LocationUpdateActivity) mContext).getTagDataArrayList();

        for (String t : tag) {
            boolean isAdd = false;
            String iniName = hep_HangulUtils.getHangulInitialSound(t, searchKeyword);
            if (iniName.indexOf(searchKeyword) >= 0) {
                isAdd = true;
            }
            if (isAdd) {
                temp.add(t);
            }
        }
        return temp;
    }
}

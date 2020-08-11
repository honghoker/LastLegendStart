package com.example.locationsave.HEP.KMS.Toolbar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_HangulUtils;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSave_AutoCompleteTextView;
import com.example.locationsave.R;

import java.util.ArrayList;
import java.util.List;


public class KMS_ClearableEditText_LoadLocation_auto extends RelativeLayout {

    LayoutInflater inflater = null;
    hep_LocationSave_AutoCompleteTextView editText;
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
        editText = findViewById(R.id.clearable_edit_load_location); //저장된 장소 검색
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //오토 완성 코드
                Toast.makeText(getContext(),"저장된 장소 검색 : " + editText.getText(),Toast.LENGTH_SHORT).show();
                Log.d("####키보드 완료 ","ㅇㅇ");

                ime.hideSoftInputFromWindow(editText.getWindowToken(),0);
                Log.d("####키보드 완료 장소검색 클릭","ㅇㅇ");

                return false;
            }
        });

        btnClear = (Button) findViewById(R.id.clearable_load_location_button_clear);
        btnClear.setVisibility(RelativeLayout.INVISIBLE);

        TextView textView;
        textView = (TextView) findViewById(R.id.searchLordResult_RecyclerVIew);
        textView.setVisibility(RelativeLayout.VISIBLE);

        clearText();
        showHideClearButton();
    }

    //X버튼이 나타났다 사라지게하는 메소드
    private void showHideClearButton() {
        //TextWatcher를 사용해 에디트 텍스트 내용이 변경 될 때 동작할 코드를 입력
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //에디트 텍스트 안 내용이 변경될 때마다 호출되는 메소드
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hep_LocationSave_AutoCompleteTextView autoCompleteTextView = editText;

                if (s.length() > 0) {
                    btnClear.setVisibility(RelativeLayout.VISIBLE);

                    //LocationRepository locationRepository = new LocationRepository(mContext.getApplicationContext());

                    //String query = searchSql(s.toString()); // 초성검색 SQL
                    //List list = locationRepository.searchTag(query);

                    autoCompleteTextView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, setChangeListData(s.toString())));
                    autoCompleteTextView.showDropDown();
                } else {
                    btnClear.setVisibility(RelativeLayout.INVISIBLE);
                    autoCompleteTextView.dismissDropDown();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(" ")) {
                    ((hep_LocationSaveActivity) mContext).hashTagAdd(s.toString().replaceAll(" ", "").trim());
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
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    public List setChangeListData(String searchKeyword){
        List temp = new ArrayList<>();
        ArrayList<String> tag = ((hep_LocationSaveActivity)mContext).getTagDataArrayList();
        for(String t: tag){
            boolean isAdd = false;
            String iniName = hep_HangulUtils.getHangulInitialSound(t, searchKeyword);
            if(iniName.indexOf(searchKeyword) >= 0){
                isAdd = true;
            }
            if(isAdd){
                temp.add(t);
            }
        }
        return temp;
    }


    private void clearText() {
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }
}

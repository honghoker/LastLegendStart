package com.example.locationsave.HEP.KMS.Location;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.locationsave.HEP.Address.AreaSearch;
import com.example.locationsave.HEP.Address.GeocodingArrayEntity;
import com.example.locationsave.HEP.Address.SearchAreaArrayEntity;
import com.example.locationsave.R;

import java.util.ArrayList;

/**
 * Created by TonyChoi on 2016. 4. 4..
 */

public class KMS_ClearableEditText_SearchLocation extends RelativeLayout {

    LayoutInflater inflater = null;
    AutoCompleteTextView editText;
    Button btnClear;
    public static Context mContext;
    FragmentManager fm;

    public KMS_ClearableEditText_SearchLocation(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout();
        mContext = context;
    }

    private void setLayout() {
        //레이아웃을 설정
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.kms_clearable_edit_text_search_location, this, true);

        editText = findViewById(R.id.clearable_edit_search_location);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        // 공백이면
                        if(editText.getText().toString().equals("")){
                            Log.d("6","공백입니다");
                            Toast.makeText(getContext(),"공백입니다. . .",Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        AreaSearch areaSearch = new AreaSearch();
                        ArrayList<SearchAreaArrayEntity> searchAreaArrayResult = areaSearch.SearchArea(editText.getText().toString());
                        ArrayList<GeocodingArrayEntity> geocodingArrayResult = areaSearch.Geocoding(editText.getText().toString());

                        if(searchAreaArrayResult.size()==0 && geocodingArrayResult.size()==0){
                            Log.d("6","검색결과가 없습니다");
                        }
                        // ex) 신당동 164
                        else if(searchAreaArrayResult.size()==0){
                            for(int i=0; i<geocodingArrayResult.size();i++){
                                Log.d("6",i + " jibunAddress "+ geocodingArrayResult.get(i).getJibunAddress()
                                        + " roadAddress " + geocodingArrayResult.get(i).getRoadAddress() + " 위도 " +geocodingArrayResult.get(i).getLatitude()
                                        + " 경도 " + geocodingArrayResult.get(i).getLongitude());
                            }
                        }
                        // ex) 계명대학교
                        else{
                            ArrayList<GeocodingArrayEntity> temp;
                            for(int i=0; i<searchAreaArrayResult.size();i++){
                                temp = areaSearch.Geocoding(searchAreaArrayResult.get(i).getAddress());
                                if(searchAreaArrayResult.get(i).getRoadAddress().equals("")){
                                    Log.d("6",i+"title "+searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                            +" address " + searchAreaArrayResult.get(i).getAddress()
                                            + " 위도 "+ temp.get(0).getLatitude() + " 경도 " + temp.get(0).getLongitude());
                                }
                                else{
                                    Log.d("6",i+"title "+searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                            +" roadAddress " + searchAreaArrayResult.get(i).getRoadAddress()
                                            + " 위도 "+ temp.get(0).getLatitude() + " 경도 " + temp.get(0).getLongitude());
                                }
                                temp.clear();
                            }
                        }
                    return true;
                } //키입력 했을 시 종료
                return false;
            } //key 입력 이벤트 종료
        });

        btnClear = (Button) findViewById(R.id.clearable_search_location_button_clear);
        btnClear.setVisibility(RelativeLayout.INVISIBLE);

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
                if (s.length() > 0) {
                    btnClear.setVisibility(RelativeLayout.VISIBLE);
                } else {
                    btnClear.setVisibility(RelativeLayout.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(s.toString().contains(" ")){
//                    ((MainActivity)mContext).hashtag_Add(s.toString().replaceAll(" ", "").trim());
//                }
            }

        });
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
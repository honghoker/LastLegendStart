package com.example.locationsave.HEP.KMS.Toolbar;

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

import androidx.fragment.app.FragmentManager;

import com.example.locationsave.HEP.Address.GeocodingArrayEntity;
import com.example.locationsave.HEP.Address.GeocodingAsyncTask;
import com.example.locationsave.HEP.Address.GeocodingGetAddress;
import com.example.locationsave.HEP.Address.SearchAreaArrayEntity;
import com.example.locationsave.HEP.Address.SearchAreaAsyncTask;
import com.example.locationsave.HEP.Address.SearchAreaGetAddress;
import com.example.locationsave.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


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
                    // searchArea = ex) 계명대학교, 경북대학교 이렇게 단순히 건물 이름 검색했을 때 주소 list
                    // 여기서 나오는 address, roadAddress 를 이용해서 다시 geocoding 돌리면
                    SearchAreaAsyncTask searchAreaAsyncTask = new SearchAreaAsyncTask(editText.getText().toString());
                    SearchAreaGetAddress searchAreaGetAddress = new SearchAreaGetAddress();
                    // geocoding = ex) 신당동 164 이렇게 주소를 입력했을 때 관련된 상세주소 list
                    GeocodingAsyncTask geocodingAsyncTask = new GeocodingAsyncTask(editText.getText().toString());
                    GeocodingGetAddress geocodingGetAddress = new GeocodingGetAddress();

                    try {
                        ArrayList<SearchAreaArrayEntity> searchAreaArrayResult = searchAreaGetAddress.getJsonString(searchAreaAsyncTask.execute().get());
                        ArrayList<GeocodingArrayEntity> geocodingArrayResult = geocodingGetAddress.getJsonString(geocodingAsyncTask.execute().get());

                        if(searchAreaArrayResult.size()==0 && geocodingArrayResult.size()==0){
                            Log.d("6","검색결과가 없습니다");
                        }
                        else if(searchAreaArrayResult.size()==0){
                            for(int i=0; i<geocodingArrayResult.size();i++){
                                Log.d("6",i + " jibunAddress "+ geocodingArrayResult.get(i).getJibunAddress()
                                        + " roadAddress " + geocodingArrayResult.get(i).getRoadAddress() + " 위도 " +geocodingArrayResult.get(i).getLatitude()
                                        + " 경도 " + geocodingArrayResult.get(i).getLongitude());
                            }
                        }
                        else{
                            for(int i=0; i<searchAreaArrayResult.size();i++){
                                Log.d("6",i + " title " + searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                        + " address " + searchAreaArrayResult.get(i).getAddress()
                                        + " roadAddress " + searchAreaArrayResult.get(i).getRoadAddress());
                            }
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(getApplicationContext(), String.valueOf(ac.getText().toString()), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
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
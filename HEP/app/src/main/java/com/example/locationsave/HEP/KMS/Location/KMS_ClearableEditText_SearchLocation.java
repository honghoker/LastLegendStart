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
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Address.AreaSearch;
import com.example.locationsave.HEP.Address.GeocodingArrayEntity;
import com.example.locationsave.HEP.Address.SearchAreaArrayEntity;
import com.example.locationsave.HEP.KMS_MainActivity;
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

    static InputMethodManager ime = null;
    KMS_SelectLocation selectLocation = new KMS_SelectLocation();
    RecyclerView searchRecyclerView = findViewById(R.id.searchResult_RecyclerVIew);


    public KMS_ClearableEditText_SearchLocation(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout();
        mContext = context;

        ime = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void setLayout() {
        //레이아웃을 설정

        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.kms_clearable_edit_text_search_location, this, true);

        editText = findViewById(R.id.clearable_edit_search_location);
        Log.d("6","####에딧 선언");

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
        Log.d("6","####에딧 엑스  클리어 내부");
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                Log.d("6","####에딧 엑스 클릭 클리어 내부");

            }
        });
    }
}
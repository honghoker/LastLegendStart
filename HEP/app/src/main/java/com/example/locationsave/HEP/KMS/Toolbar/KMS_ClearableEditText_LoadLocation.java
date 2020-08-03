package com.example.locationsave.HEP.KMS.Toolbar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentManager;

import com.example.locationsave.R;


/**
 * Created by TonyChoi on 2016. 4. 4..
 */

public class KMS_ClearableEditText_LoadLocation extends RelativeLayout {

    LayoutInflater inflater = null;
    AutoCompleteTextView editText;
    Button btnClear;
    public static Context mContext;
    FragmentManager fm;

    public KMS_ClearableEditText_LoadLocation(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout();
        mContext = context;
    }

    private void setLayout() {
        //레이아웃을 설정
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.kms_clearable_edit_text_load_location, this, true);

        editText = findViewById(R.id.clearable_edit_load_location);
        btnClear = (Button) findViewById(R.id.clearable_load_location_button_clear);
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
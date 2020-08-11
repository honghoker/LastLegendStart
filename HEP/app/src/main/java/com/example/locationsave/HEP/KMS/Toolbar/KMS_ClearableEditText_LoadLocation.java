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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;

import java.util.ArrayList;

import static com.example.locationsave.HEP.KMS_MainActivity.ksh_loadLocation;
import static com.example.locationsave.HEP.KMS_MainActivity.loadRecyclerView;
import static com.example.locationsave.HEP.KMS_MainActivity.mLinearLayoutManager;
import static com.example.locationsave.HEP.KMS_MainActivity.test_1;


/**
 * Created by TonyChoi on 2016. 4. 4..
 */

public class KMS_ClearableEditText_LoadLocation extends RelativeLayout {

    LayoutInflater inflater = null;
    AutoCompleteTextView editText;
    Button btnClear;
    public static Context mContext;

    //test
    int count_test = 0;
    ArrayList<String> test = new ArrayList<>();
    /////
    public static int SET_LOAD_RECYCLER_FLAG = 0;

    static InputMethodManager ime = null;


    public KMS_ClearableEditText_LoadLocation(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout();
        mContext = context;

        ime = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void setLayout() {
        //레이아웃을 설정
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.kms_clearable_edit_text_load_location, this, true);



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
                    Log.d("6","확인 "+s.toString());
                    btnClear.setVisibility(RelativeLayout.VISIBLE);

                    // test_1 은 arraylist 5개 걸그룹 내가 임의로 넣은거
                    // test는 arraylist, 같으면 add 해서 adapter에 넣기
                    for(int i=0; i<test_1.size();i++){
                        if(test_1.get(i).equals(s.toString())){
                            test.add(s.toString());
                            count_test++;
                        }
                    }
                    if(count_test>0){
                        Log.d("6","ㅁㄴㅇㅁㄴㅇ");
                        KSH_LoadResultAdapter mAdapter = new KSH_LoadResultAdapter(test);
                        loadRecyclerView.setAdapter(mAdapter);

                        // setSearchResultRecyclerView : gone -> visible, visible -> gone
                        ksh_loadLocation.setSearchResultRecyclerView(mContext, loadRecyclerView);
                        loadRecyclerView.setLayoutManager(mLinearLayoutManager);
                        // recyclerview, list 1개 이상이면 1 아니면 0
                        // 쓸일 있을지 모르겠다 이건
                        SET_LOAD_RECYCLER_FLAG = 1;
                    }

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
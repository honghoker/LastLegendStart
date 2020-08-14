package com.example.locationsave.HEP.KMS.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;

import org.w3c.dom.Text;


public class KMS_AddLocationFragment extends Fragment {
    Activity activity;
    private OnTimePickerSetListener onTimePickerSetListener;
    TextView textView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context); //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능

        if(context instanceof OnTimePickerSetListener){
            onTimePickerSetListener = (OnTimePickerSetListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OntimePicker");
        }

        activity = getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();

        onTimePickerSetListener = null;

        //이제 더이상 엑티비티 참초가안됨
        activity = null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //프래그먼트 메뉴를 인플레이트해주고 컨테이너에 붙여달라는 뜻임
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.kms_location_fragment,container,false);
        setHasOptionsMenu(true);

        textView = rootView.findViewById(R.id.fragment);

        String a2 = getArguments().getString("Title");
        String a3 = getArguments().getString("Address");

        Log.d("#####액티비티 -> 프레그먼트로 넘어온 값 title : ", a2 + " / address : " + a3);

        onTimePickerSetListener.onTimePickerSet(2131232,3, textView.getText().toString()); //값 넘겨줌
        Log.d("#####", "값 넘겨줌");

        return rootView;
    }

    public interface OnTimePickerSetListener{  //보낼 데이터 인터페이스 생성
        void onTimePickerSet(int hour, int min, String text);

    }
}
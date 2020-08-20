package com.example.locationsave.HEP.KMS.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.locationsave.HEP.Address.ReverseGeocodingAsyncTask;
import com.example.locationsave.HEP.Address.ReverseGetAddress;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.KMS.Map.KMS_CameraManager;
import com.example.locationsave.HEP.KMS.Map.KMS_MapOption;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;


public class KMS_AddLocationFragment extends Fragment implements OnMapReadyCallback {
    Activity activity;
    private OnTimePickerSetListener onTimePickerSetListener;
    TextView textView;
    KMS_CameraManager kms_cameraManager = KMS_CameraManager.getInstanceCameraManager();
    //현재 위치
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //장소추가 및 선택 시 맵 선언
    public static NaverMap AddMap;

    //맵옵션
    KMS_MapOption mapOption = KMS_MapOption.getInstanceMapOption();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("%%%%%로케 프래그먼트","객체 온크리트");

        //프래그먼트
        FragmentManager addFragmentManager = getChildFragmentManager();
        MapFragment addMapFragment = (MapFragment) addFragmentManager.findFragmentById(R.id.addmap); //맵프래그먼트 객체 생성 map id 가져와서
/*
        if (addMapFragment == null) { //맵프래그먼트 생성된 적 없으면
            addMapFragment = MapFragment.newInstance(mapOption.setFirstOptions()); //새로 만들어주고   // 1-8. 초기옵션 추가
            Toast.makeText(getContext(), "add맵 생성 완료", Toast.LENGTH_SHORT).show();
            addFragmentManager.beginTransaction().add(R.id.map, addMapFragment).commit(); // 프래그매니저에게 명령 map 레이아웃에 생성된 맵 객체를 add
            Log.d("####맵생성안됨",   "프레그먼트 추가");
        }         */

        addMapFragment = MapFragment.newInstance(mapOption.getFirstAddOptions()); //새로 만들어주고   // 1-8. 초기옵션 추가
        addFragmentManager.beginTransaction().add(R.id.addmap, addMapFragment).commit(); // 프래그매니저에게 명령 map 레이아웃에 생성된 맵 객체를 add

        addMapFragment.getMapAsync(this); //이거 만들면 onMapReady 사용 가능
        Log.d("%%%%%로케 프래그먼트", "온맵레디");


        //현재 위치
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        Log.d("%%%%%로케 프래그먼트", "현재위치");
        CameraPosition cameraPosition = KMS_MapFragment.NMap.getCameraPosition();
        double latitude = cameraPosition.target.latitude;
        double longitude = cameraPosition.target.longitude;

/*

        LatLng latLng = new LatLng(135.857654, 128.498123);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng); //카메라 업데이트 위해 클릭마커 좌표 입력


        cameraUpdate.animate(CameraAnimation.Fly); //애니메이션
        Log.d("%%%%%로케 프래그먼트", "카메라를 위경도 위치로 이동" + " " + latitude + "  " + longitude);
*/

        //AddMap.moveCamera(cameraUpdate);

        Log.d("%%%%%로케 프래그먼트", "온크리트");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context); //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        Log.d("%%%%%로케 프래그먼트", "onAttach 시작");

        if (context instanceof OnTimePickerSetListener) {
            onTimePickerSetListener = (OnTimePickerSetListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OntimePicker");
        }

        activity = getActivity();
        Log.d("%%%%%로케 프래그먼트", "onAttach 종료");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("%%%%%로케 프래그먼트", "onDetach");
        onTimePickerSetListener = null;

        //이제 더이상 엑티비티 참초가안됨
        activity = null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //프래그먼트 메뉴를 인플레이트해주고 컨테이너에 붙여달라는 뜻임
        Log.d("%%%%%로케 프래그먼트", "onCreateView 실행");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.kms_location_fragment, container, false);
        Log.d("%%%%%로케 프래그먼트", "onCreateView");
        setHasOptionsMenu(true);

        String a2 = getArguments().getString("Title");
        String a3 = getArguments().getString("Address");


        Log.d("%%%%%액티비티 -> 프레그먼트로 넘어온 값 title : ", a2 + " / address : " + a3);

        onTimePickerSetListener.onTimePickerSet(2131232, 3, "스트링 테스트"); //값 넘겨줌
        Log.d("%%%%%", "값 넘겨줌");


/*        Button btn = rootView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kms_cameraManager.MoveCameraOnLatlngPosition(37.5670135, 126.9783740, AddMap);
                Log.d("#####", "맵 이동");
            }
        });*/

        return rootView;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d("%%%%%", "온맵레디");

        AddMap = naverMap; //전역에 naverMap 당겨옴
        AddMap.addOnCameraIdleListener(new NaverMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition cameraPosition = AddMap.getCameraPosition(); //현재 위치 정보 반환하는 메소드

                ReverseGeocodingAsyncTask asyncTask = new ReverseGeocodingAsyncTask(cameraPosition.target.latitude, cameraPosition.target.longitude);
                ReverseGetAddress reverseGetAddress = new ReverseGetAddress();
                try {
                    String resultAddr = reverseGetAddress.getJsonString(asyncTask.execute().get());
                    //((TextView)activity.findViewById(R.id.selectLocation_AddressInfo)).setText(resultAddr);
                    Log.d("%%%%%", "주소 : " + resultAddr);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),
                        "현재위치 = 대상 지점 위도: " + cameraPosition.target.latitude + ", " +
                                "대상 지점 경도: " + cameraPosition.target.longitude, Toast.LENGTH_SHORT);
                Log.d("%%%%%AddMap", "onCameraIdle 위도 : " + cameraPosition.target.latitude + "경도 : " + cameraPosition.target.longitude);

                Log.d("%%%%%", "온카메라아이들");

            }
        });

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        //줌컨트롤ui
        uiSettings.setZoomControlEnabled(false);  //줌 컨트롤 여부
        AddMap.setLocationSource(locationSource); //얘가 있으면 버튼이 활성화

    }

    public interface OnTimePickerSetListener {  //보낼 데이터 인터페이스 생성
        void onTimePickerSet(int hour, int min, String text);

    }
}
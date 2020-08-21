package com.example.locationsave.HEP.KMS.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Address.AreaSearch;
import com.example.locationsave.HEP.Address.GeocodingArrayEntity;
import com.example.locationsave.HEP.Address.ReverseGeocodingAsyncTask;
import com.example.locationsave.HEP.Address.ReverseGetAddress;
import com.example.locationsave.HEP.Address.SearchAreaArrayEntity;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.Hep.hep_LocationUpdate.KMS_UpdateResultAdapter;
import com.example.locationsave.HEP.Hep.hep_LocationUpdate.hep_LocationUpdateActivity;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationSearchResult;
import com.example.locationsave.HEP.KMS.Location.KMS_SearchResultAdapter;
import com.example.locationsave.HEP.KMS.Location.KMS_SelectLocation;
import com.example.locationsave.HEP.KMS.Map.KMS_CameraManager;
import com.example.locationsave.HEP.KMS.Map.KMS_MapOption;
import com.example.locationsave.HEP.KMS.Map.KMS_MarkerManager;
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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.locationsave.HEP.Hep.hep_LocationUpdate.hep_LocationUpdateActivity.updateRecyclerViewFlag;


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

    //리사이클 배열
    public static ArrayList<KMS_LocationSearchResult> kms_locationUpdateResults = new ArrayList<>();
    KMS_LocationSearchResult kms_locationUpdateResult;

    KMS_MarkerManager kms_markerManager = new KMS_MarkerManager().getInstanceMarkerManager();
    KMS_MapOption kms_mapOption = KMS_MapOption.getInstanceMapOption();
    LinearLayout linearLayout;
    public static AutoCompleteTextView editText;
    public static RecyclerView updateRecyclerView;

    //리사이클 임시 마커
    KMS_SelectLocation selectLocation = new KMS_SelectLocation();
    static InputMethodManager inputMethodManager; //키보드 설정 위한

    public static RelativeLayout updateRelativeBar;

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
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); //키보드-시스템서비스
        updateLinearLayoutManager = new LinearLayoutManager(getContext());

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

        updateRecyclerView = rootView.findViewById(R.id.updateResult_RecyclerView);
        updateRelativeBar = rootView.findViewById(R.id.updateResultBar);

        editText = rootView.findViewById(R.id.editText200);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                kms_locationUpdateResults.clear();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 공백이면
                    if(editText.getText().toString().equals("")){
                        Log.d("6","####공백입니다");
                        Toast.makeText(getContext(),"공백입니다. . .",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if(updateRecyclerView.getVisibility() == View.VISIBLE){
                        updateRecyclerView.setVisibility(View.GONE);
                    }
                    //공백 아닐 경우
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    updateRecyclerViewFlag = true;
                    Log.d("%%%%%에드 리사이클 불리언 내부"," " + updateRecyclerViewFlag);

                    //재 검색 시 초기화
                    kms_markerManager.initRecyclerMarker();
                    Log.d("%%%%%에드 이니트 리사이클","초기화 완료");

                    AreaSearch areaSearch = new AreaSearch();
                    ArrayList<SearchAreaArrayEntity> searchAreaArrayResult = areaSearch.SearchArea(editText.getText().toString());
                    ArrayList<GeocodingArrayEntity> geocodingArrayResult = areaSearch.Geocoding(editText.getText().toString());

//                    ArrayList<KMS_LocationSearchResult> kms_locationSearchResults = new ArrayList<>();
//                    KMS_LocationSearchResult kms_locationSearchResult;

                    if(searchAreaArrayResult.size()==0 && geocodingArrayResult.size()==0){
                        Log.d("%%%%%에드","검색결과가 없습니다");
                        Toast.makeText(getContext(),"검색결과가 없습니다",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    // ex) 신당동 164
                    else if(searchAreaArrayResult.size()==0){
                        for(int i=0; i<geocodingArrayResult.size();i++){
                            Log.d("%%%%%에드",i + " jibunAddress "+ geocodingArrayResult.get(i).getJibunAddress()
                                    + " roadAddress " + geocodingArrayResult.get(i).getRoadAddress() + " 위도 " +geocodingArrayResult.get(i).getLatitude()
                                    + " 경도 " + geocodingArrayResult.get(i).getLongitude());
                            kms_locationUpdateResult = new KMS_LocationSearchResult(editText.getText().toString(),geocodingArrayResult.get(i).getRoadAddress()
                                    , geocodingArrayResult.get(i).getLongitude(), geocodingArrayResult.get(i).getLatitude());
                            kms_locationUpdateResults.add(kms_locationUpdateResult);
                        }
                    }
                    // ex) 계명대학교
                    //교대요
                    else{
                        ArrayList<GeocodingArrayEntity> temp;
                        for(int i=0; i<searchAreaArrayResult.size();i++){
                            temp = areaSearch.Geocoding(searchAreaArrayResult.get(i).getAddress());
                            if(searchAreaArrayResult.get(i).getRoadAddress().equals("")){
//                                Log.d("6",i+"title "+searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
//                                        +" address " + searchAreaArrayResult.get(i).getAddress()
//                                        + " 위도 "+ temp.get(0).getLatitude() + " 경도 " + temp.get(0).getLongitude());
                                kms_locationUpdateResult = new KMS_LocationSearchResult(searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                        ,searchAreaArrayResult.get(i).getAddress(), temp.get(0).getLongitude(), temp.get(0).getLatitude());
                                kms_locationUpdateResults.add(kms_locationUpdateResult);
                            }
                            else{
//                                Log.d("6",i+"title "+searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
//                                        +" roadAddress " + searchAreaArrayResult.get(i).getRoadAddress()
//                                        + " 위도 "+ temp.get(0).getLatitude() + " 경도 " + temp.get(0).getLongitude());
                                kms_locationUpdateResult = new KMS_LocationSearchResult(searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                        ,searchAreaArrayResult.get(i).getRoadAddress(), temp.get(0).getLongitude(), temp.get(0).getLatitude());
                                kms_locationUpdateResults.add(kms_locationUpdateResult);
                            }
                            temp.clear();
                        }
                    }
                    for(int i=0; i<kms_locationUpdateResults.size();i++){
                        Log.d("6",i+" title " + kms_locationUpdateResults.get(i).getTitle() + " address " + kms_locationUpdateResults.get(i).getRoadAddress()
                                + " 위도 "+kms_locationUpdateResults.get(i).getLatitude() + " 경도 " +kms_locationUpdateResults.get(i).getLongitude()
                        + "result size = " + kms_locationUpdateResults.size());
                    }
                    kms_markerManager.AddUpdateRecyclerViewMarker(AddMap, kms_locationUpdateResults);
                    Log.d("%%%%%%에드 리사이클러","끝났음");

                    selectLocation.setSearchResultRecyclerView(getContext(), updateRecyclerView, updateRelativeBar);
                    LoadRecyclerView(); //기존 저장 함수 불러옴
                    return true;
                } //키입력 했을 시 종료
                return false;
            } //key 입력 이벤트 종료
        });




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
    public static LinearLayoutManager updateLinearLayoutManager;

    public void LoadRecyclerView(){
        InitRecyclerView();
        updateRecyclerView.setLayoutManager(updateLinearLayoutManager);
        KMS_UpdateResultAdapter mAdapter = new KMS_UpdateResultAdapter(kms_locationUpdateResults);
        updateRecyclerView.addItemDecoration(new DividerItemDecoration(updateRecyclerView.getContext(), 1));
        updateRecyclerView.setAdapter(mAdapter);
    }

    public void InitRecyclerView(){
        KMS_UpdateResultAdapter.LastPosition = -1;
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
                    ((TextView)activity.findViewById(R.id.updateLocation_AddressInfo)).setText(resultAddr);
                    ((TextView)activity.findViewById(R.id.locationupdate_locationAddr)).setText(resultAddr);
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
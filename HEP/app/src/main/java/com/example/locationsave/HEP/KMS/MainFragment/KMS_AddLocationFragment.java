package com.example.locationsave.HEP.KMS.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
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
import com.example.locationsave.HEP.Hep.hep_LocationUpdate.KMS_UpdateResultAdapter;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationSearchResult;
import com.example.locationsave.HEP.KMS.Location.KMS_SelectLocation;
import com.example.locationsave.HEP.KMS.Map.KMS_MapOption;
import com.example.locationsave.HEP.KMS.Map.KMS_MarkerManager;
import com.example.locationsave.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.locationsave.HEP.Hep.hep_LocationUpdate.hep_LocationUpdateActivity.updateRecyclerViewFlag;
import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.NMap;

public class KMS_AddLocationFragment extends Fragment implements OnMapReadyCallback {
    Activity activity;
    private OnTimePickerSetListener onTimePickerSetListener;
    TextView textView;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    public static NaverMap AddMap;

    KMS_MapOption mapOption = KMS_MapOption.getInstanceMapOption();

    public static ArrayList<KMS_LocationSearchResult> kms_locationUpdateResults = new ArrayList<>();
    KMS_LocationSearchResult kms_locationUpdateResult;

    KMS_MarkerManager kms_markerManager = new KMS_MarkerManager().getInstanceMarkerManager();

    public static AutoCompleteTextView editText;
    public static RecyclerView updateRecyclerView;

    KMS_SelectLocation selectLocation = new KMS_SelectLocation();
    static InputMethodManager inputMethodManager;
    public static RelativeLayout updateRelativeBar;

    double latitude, longitude;
    String title;

    boolean addMarkerFlag = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager addFragmentManager = getChildFragmentManager();
        MapFragment addMapFragment = (MapFragment) addFragmentManager.findFragmentById(R.id.addmap);

        addMapFragment = MapFragment.newInstance(mapOption.getFirstAddOptions());
        addFragmentManager.beginTransaction().add(R.id.addmap, addMapFragment).commit();
        addMapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        updateLinearLayoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnTimePickerSetListener) {
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
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.kms_location_fragment, container, false);
        setHasOptionsMenu(true);

        title = getArguments().getString("Title");
        String a3 = getArguments().getString("Address");
        latitude = getArguments().getDouble("latitude");
        longitude = getArguments().getDouble("longitude");

        updateRecyclerView = rootView.findViewById(R.id.updateResult_RecyclerView);
        updateRelativeBar = rootView.findViewById(R.id.updateResultBar);

        editText = rootView.findViewById(R.id.editText200);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                kms_locationUpdateResults.clear();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(editText.getText().toString().equals("")){
                        Toast.makeText(getContext(),"공백입니다. . .",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if(updateRecyclerView.getVisibility() == View.VISIBLE){
                        updateRecyclerView.setVisibility(View.GONE);
                    }
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    updateRecyclerViewFlag = true;

                    kms_markerManager.initRecyclerMarker();

                    AreaSearch areaSearch = new AreaSearch();
                    ArrayList<SearchAreaArrayEntity> searchAreaArrayResult = areaSearch.SearchArea(editText.getText().toString());
                    ArrayList<GeocodingArrayEntity> geocodingArrayResult = areaSearch.Geocoding(editText.getText().toString());

                    if(searchAreaArrayResult.size()==0 && geocodingArrayResult.size()==0){
                        Toast.makeText(getContext(),"검색결과가 없습니다",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else if(searchAreaArrayResult.size()==0){
                        for(int i=0; i<geocodingArrayResult.size();i++){
                            kms_locationUpdateResult = new KMS_LocationSearchResult(editText.getText().toString(),geocodingArrayResult.get(i).getRoadAddress()
                                    , geocodingArrayResult.get(i).getLongitude(), geocodingArrayResult.get(i).getLatitude());
                            kms_locationUpdateResults.add(kms_locationUpdateResult);
                        }
                    }
                    else{
                        ArrayList<GeocodingArrayEntity> temp;
                        for(int i=0; i<searchAreaArrayResult.size();i++){
                            temp = areaSearch.Geocoding(searchAreaArrayResult.get(i).getAddress());
                            if(searchAreaArrayResult.get(i).getRoadAddress().equals("")){
                                kms_locationUpdateResult = new KMS_LocationSearchResult(searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                        ,searchAreaArrayResult.get(i).getAddress(), temp.get(0).getLongitude(), temp.get(0).getLatitude());
                                kms_locationUpdateResults.add(kms_locationUpdateResult);
                            }
                            else{
                                kms_locationUpdateResult = new KMS_LocationSearchResult(searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                        ,searchAreaArrayResult.get(i).getRoadAddress(), temp.get(0).getLongitude(), temp.get(0).getLatitude());
                                kms_locationUpdateResults.add(kms_locationUpdateResult);
                            }
                            temp.clear();
                        }
                    }
                    for(int i=0; i<kms_locationUpdateResults.size();i++){
                    }
                    kms_markerManager.AddUpdateRecyclerViewMarker(AddMap, kms_locationUpdateResults);

                    selectLocation.setSearchResultRecyclerView(getContext(), updateRecyclerView, updateRelativeBar);
                    LoadRecyclerView(); //기존 저장 함수 불러옴
                    return true;
                } //키입력 했을 시 종료
                return false;
            } //key 입력 이벤트 종료
        });

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
        AddMap = naverMap;
        AddMap.addOnCameraIdleListener(new NaverMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition cameraPosition = AddMap.getCameraPosition(); //현재 위치 정보 반환하는 메소드
                onTimePickerSetListener.onTimePickerSet(cameraPosition.target.latitude, cameraPosition.target.longitude); //값 넘겨줌

                ReverseGeocodingAsyncTask asyncTask = new ReverseGeocodingAsyncTask(cameraPosition.target.latitude, cameraPosition.target.longitude);
                ReverseGetAddress reverseGetAddress = new ReverseGetAddress();
                try {
                    String resultAddr = reverseGetAddress.getJsonString(asyncTask.execute().get());
                    ((TextView)activity.findViewById(R.id.updateLocation_AddressInfo)).setText(resultAddr);
                    ((TextView)activity.findViewById(R.id.locationupdate_locationAddr)).setText(resultAddr);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        uiSettings.setZoomControlEnabled(false);  //줌 컨트롤 여부
        AddMap.setLocationSource(locationSource); //얘가 있으면 버튼이 활성화

        if(addMarkerFlag == false) {
            final Marker marker = new Marker();
            LatLng addMarkerLatLng = new LatLng(latitude, longitude);
            marker.setPosition(addMarkerLatLng);
            marker.setCaptionText(title);
            marker.setIcon(OverlayImage.fromResource(R.drawable.marker_design_pika2));
            marker.setWidth(120);
            marker.setHeight(160);
            marker.setMap(AddMap);
            addMarkerFlag = true;
        }

    }

    public interface OnTimePickerSetListener {
        void onTimePickerSet(double hour, double min);
    }
}
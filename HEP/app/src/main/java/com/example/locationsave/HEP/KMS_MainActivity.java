package com.example.locationsave.HEP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Address.GeocodingAsyncTask;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.KMS.BackPressed.KMS_BackPressedForFinish;
import com.example.locationsave.HEP.KMS.HashTag.KMS_FlowLayout;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTag;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxManager;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationFlagManager;
import com.example.locationsave.HEP.KMS.Location.KMS_SelectLocation;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;

import com.example.locationsave.HEP.Address.AreaSearch;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_FirebaseUser;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.Hep.hep_closeAppService;
import com.example.locationsave.HEP.KMS.BackPressed.KMS_BackPressedForFinish;
import com.example.locationsave.HEP.KMS.HashTag.KMS_FlowLayout;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTag;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxFlagManager;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxManager;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationFlagManager;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationSearchResult;
import com.example.locationsave.HEP.KMS.Location.KMS_SearchResultAdapter;
import com.example.locationsave.HEP.KMS.Location.KMS_SelectLocation;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_FragmentFlagManager;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_ClearableEditText_LoadLocation;

import com.example.locationsave.HEP.KMS.Toolbar.KMS_RecycleVIewManager;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_SearchManager;
import com.example.locationsave.HEP.KSH.KSH_AllSeeActivity;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.HEP.KSH.KSH_FireBase;
import com.example.locationsave.HEP.KSH.KSH_LoadingActivity;
import com.example.locationsave.HEP.KSH.KSH_RecyAdapter;

import com.example.locationsave.HEP.KSH.KSH_RecyclerviewAdapter;
import com.example.locationsave.HEP.KSH.NavIntent.KSH_NoticeIntent;
import com.example.locationsave.HEP.pcs_RecyclerView.Pcs_LocationRecyclerView;

import com.example.locationsave.HEP.KSH.NavIntent.KSH_HelpIntent;
import com.example.locationsave.HEP.KSH.NavIntent.KSH_NoticeIntent;
import com.example.locationsave.HEP.KSH.NavIntent.KSH_SetIntent;

import com.example.locationsave.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.map.CameraPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.NMap;

public class KMS_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /* 
    ---Index---
    1. Fragment
    2. BottomBar
    3-1. Toolbar
    3-2. Spinner & RecycleView
    4. Toolbar Search
    5. Animation
    6. 자동완성 텍스트 뷰
    7. HashTag
    8. FloatingIcon
    9. Location Layout
    10. BackPressed
    ----END----

    --기입순서--
    1. 상단
    -변수
    -함수
    2. oncreate
    ---END---
     */
    public static String directoryid = null;
    private DrawerLayout drawerLayout;
    private boolean recyFrag = false;
    private RecyclerView recyclerView;
    private View rView;
    private View fView;
    private View allSeeView;
    private RecyclerView.Adapter recyAdapter;
    private ArrayList<KSH_DirectoryEntity> arrayList;
    private ArrayList<String> arrayKey;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Query recentQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").orderByChild("token").equalTo(new hep_FirebaseUser().getFirebaseUserInstance().getUid());
        recentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> result = new HashMap<>();
                CameraPosition cameraPosition = NMap.getCameraPosition();
                result.put("latitude", cameraPosition.target.latitude);
                result.put("longitude", cameraPosition.target.longitude);
                if(directoryid != null)
                    result.put("directoryid", directoryid);

                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Log.d("Key : ", "" + dataSnapshot.getKey());
                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").child(dataSnapshot.getKey()).updateChildren(result);
                    }
                }
                else{
                    result.put("token", new hep_FirebaseUser().getFirebaseUserInstance().getUid());
                    new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").push().setValue(result);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
    }

    //    private Spinner spinner;
//    private Toolbar toolbar;
    private NavigationView navigationView;
    private KSH_DirectoryEntity ksh_directoryEntity;
    public void ksh_init(){
        startService(new Intent(this, hep_closeAppService.class)); // 앱 종료 이벤트
        rView = findViewById(R.id.include_recyclerView);
        fView = findViewById(R.id.frameLayout);   // frameLayout 위에 recyclerView가 나타나야함으로 frameLayout 선언
        allSeeView = findViewById(R.id.recy_allSee);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        arrayList = new ArrayList<>();  // 객체 담아서 adapter로 보낼 arraylist
        arrayKey = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        KSH_FireBase firebaseDatabase = KSH_FireBase.getInstance(); // 싱글톤
        AreaSearch areaSearch = new AreaSearch(); // 키자마자 한번 지오코딩 돌리는거
        areaSearch.Geocoding("신당동 164");
    }

    View mView;
    public static FragmentManager fragmentManager;
    public static Fragment mapFragment = null;
    public static Fragment LocationFragmet = null;
    KMS_FragmentFlagManager kms_fragmentFlagManager;
    public void kms_init(){
        fragmentManager = getSupportFragmentManager();
        mapFragment = new KMS_MapFragment();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, mapFragment).commit();
        kms_fragmentFlagManager = KMS_FragmentFlagManager.getInstanceFragment();
    }

    //1.Fragment
//    public FragmentManager fragmentManager;
//    public Fragment mapFragment, LocationFragmet = null;
//    kms_fragmentFlagManager = KMS_FragmentFlagManager.getInstanceFragment(); //프래그먼트 인스턴스 생성


//    public void setFragmentMapLayout(){ //맵 프레그먼트 출력 함수
//        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commit();/*프래그먼트 매니저가 프래그먼트를 담당한다!*/
//        if (mapFragment == null) {
//            mapFragment = new ListFragment();
//            fragmentManager.beginTransaction().add(R.id.frameLayout, mapFragment).commit();
//        }
//        if (mapFragment != null) {
//            //clearSearchBar(ac); //서치바 초기화
//            fragmentManager.beginTransaction().show(mapFragment).commit();
//            //Toast.makeText(this, "맵 생성완료", Toast.LENGTH_SHORT).show();
//        }
//        if (LocationFragmet != null)
//            fragmentManager.beginTransaction().hide(LocationFragmet).commit();
//    }
//    public void setFragmentLocationListLayout() {  //리스트 프레그먼트 출력 시
//        if (LocationFragmet == null) {
//            LocationFragmet = new Pcs_LocationRecyclerView();
//            fragmentManager.beginTransaction().add(R.id.frameLayout, LocationFragmet).commit();
//        }
//        if (mapFragment != null) {
//            fragmentManager.beginTransaction().hide(mapFragment).commit();
//        }
//        if (LocationFragmet != null){
//            fragmentManager.beginTransaction().show(LocationFragmet).commit();
//        }
//    }

    // Fragment에 그 함수(android:onClicked에 해당하는 함수)를 정의하면 안된다고 합니다. 따로 못빼겠음
    // https://stackoverflow.com/questions/21192386/android-fragment-onclick-button-method
    //2.Bottomber
    //하단 바
    public LinearLayout bottomBar;
    public void onBottomBarClicked(View v) { //Change Fragment
        switch (v.getId()) {
            case R.id.btnMain:
                //fragmentFlag = true;
                kms_fragmentFlagManager.flagSetTrueFragment(); //프레그먼트 플래그 true 로 변경
                kms_fragmentFlagManager.setFragmentMapLayout();
                logtest("맵 프래그먼트");
                break;
            case R.id.btnLocationList:
                //fragmentFlag = false;
                kms_fragmentFlagManager.flagSetFalseFragment(); //프래그먼트 플래그 false 로 변경
                kms_fragmentFlagManager.setFragmentLocationListLayout();
                logtest("리스트 프래그먼트");
                break;
            default:
                break;
        }
    }

    public void setBottomBar(LinearLayout bottomBar, boolean Flag) { //searchFlag 에 맞게 하단 바 가리기
        if (Flag == true){
            bottomBar.setVisibility(View.GONE);
        }
        else{
            bottomBar.setVisibility(View.VISIBLE);
        }
    }

    //3-1. Toolbar
    //우선 style.xml 액션바 제거
    Toolbar toolbar;
    Spinner spinner;
    KMS_RecycleVIewManager kms_recycleVIewManager = KMS_RecycleVIewManager.getInstanceRecycleView();

    // Activity가 시작될 때 호출되는 함수 -> MenuItem 생성과 초기화 진행
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list,menu);
        return true;
    }
    //장소 선택 위한 툴바 제거
    public void SetToolbar() { //액션바 상태에 따라서 세팅해준다.
        if (getSupportActionBar().isShowing()) { //만약 액션바 보이고 있으면 숨기기
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show(); //만약 액션바 안보이면 보이게
        }
    }

    //3-2. Spinner & RecycleView
    public void setSpinner(){
        if(kms_recycleVIewManager.flagCheckRecycleView() == true){ //리사이클 flag 가 true일 경우
            Animation animationH = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translatehide);
            rView.setAnimation(animationH);
            rView.setVisibility(fView.GONE);
            kms_recycleVIewManager.flagSetFalseRecycleView(); //리사이클 flag 를 false 로 변경
        }
        else if(kms_recycleVIewManager.flagCheckRecycleView() == false){ //리사이클 flag 가 false일 경우
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
            rView.setAnimation(animation);
            rView.setVisibility(fView.VISIBLE);
            kms_recycleVIewManager.flagSetTrueRecycleView(); //리사이클 flag를 true로 변경
        }
    }

    //4. Toolbar Search
    LinearLayout linearLayoutToolbarSearch;
    ConstraintLayout recy_con_layout;
    KMS_SearchManager kms_searchManager = KMS_SearchManager.getInstanceSearch();

    public void hideRecyclerView() { //리사이클 플래그가 false 이면 - 리사이클러 뷰가 안보이면 실행해준다. true 로 바꾼다.
        if (kms_recycleVIewManager.flagCheckRecycleView() == true) { //호출하였을 때 리사이클이 떠있을 경우에만 실행한다. 안떠있을 때 재실행 방지.
            animationH = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translatehide);
            recy_con_layout.setAnimation(animationH);
            recy_con_layout.setVisibility(mView.GONE);
            kms_recycleVIewManager.flagSetFalseRecycleView();
        }
    }

    //상단 툴바 클릭 이벤트
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search: {//상단 검색 버튼 클릭 시
                if(kms_recycleVIewManager.flagCheckRecycleView() == true){ // 만약 리사이클뷰 열려있으면 닫아준다.
                    setSpinner(); //이걸로 제어
                    hideRecyclerView(); //일단 디렉토리 열려있으면 삭제
                }//임시
                Toast.makeText(getApplicationContext(), "검색할 장소를 입력하세요.", Toast.LENGTH_LONG).show();
                //툴바 제거
                if (getSupportActionBar().isShowing()) {
                    kms_searchManager.flagSetTrueSearch();
                    getSupportActionBar().hide();
                    clearableEditText_loadLocation.requestFocus();
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    setBottomBar(bottomBar, kms_searchManager.flagGetSearch());
                    setSearchBar(kms_searchManager.flagGetSearch());
                    setFloatingItem(kms_searchManager.flagGetSearch());
                }
                return true;
            } //검색 버튼 종료
            case R.id.menu_tag_filter: {
                showHashTagFilter(); // 안보인다면 해시태그를 보이게 한 뒤 해시플래그를 트루로 만든다.
                return true;
            }
            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSearchBar(boolean searchFlag) { //searchFlag 에 맞게 상단 검색 바 출력
        if (searchFlag == true)
            linearLayoutToolbarSearch.setVisibility(View.VISIBLE);
        else
            linearLayoutToolbarSearch.setVisibility(View.GONE);
    }

    //5. Animation
    Animation animation;
    Animation animationH;

    //6. 자동완성 텍스트 뷰
    KMS_ClearableEditText_LoadLocation clearableEditText_loadLocation;
    static InputMethodManager inputMethodManager; //키보드 설정 위한

    //리스트뷰
    private List<String> list;

    //7. HashTag
    public static LinearLayout hastagView;

    //해시태그 선택
//    public class HasTagOnClickListener implements Button.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            kms_hashTagCheckBoxFlagManager.HashTagClickEvent(KMS_MainActivity.this, v);
//        }
//    }

//    CheckBox checkBoxAllHashTag; //체크박스 명 선언
    public static KMS_HashTag[] msHashTag = new KMS_HashTag[10]; //태그 배열
    public static KMS_FlowLayout.LayoutParams params = new KMS_FlowLayout.LayoutParams(20, 20);
    ; //해시태그 레이아웃을 위한 parms
    KMS_HashTagCheckBoxFlagManager kms_hashTagCheckBoxFlagManager = KMS_HashTagCheckBoxFlagManager.getInstanceHashTagCheckBox();
//    HasTagOnClickListener hasTagOnClickListener = new HasTagOnClickListener();
    KMS_HashTagCheckBoxManager kms_hashTagCheckBoxManager = KMS_HashTagCheckBoxManager.getInstanceHashTagCheckBox();
    // 확인을 눌렀을 때 눌린 태그들의 id값을 가져온다.

    public void onHashTagFilterButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.btnFilterSelect:
                kms_hashTagCheckBoxManager.AddClickHashTag(this);
                break;
            case R.id.btnFilterCancel:
                hideHashTagFilter();
                setFloatingItem(kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag());
                break;
            default:
                break;
        }
    }

    public void showHashTagFilter() {  //해시태그 체크박스 띄움
        if (kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag() == false) { //호출했을 때 해시필터 없을 경우에만 실행.
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
            hastagView.setAnimation(animation);
            hastagView.setVisibility(mView.VISIBLE);
            kms_hashTagCheckBoxFlagManager.flagSetTruehashTagCheckBoxFlag(); //해시태그 체크박스 true로 변경
        }
    }

    public void hideHashTagFilter() { //해시태그 체크박스 숨김
        if (kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag() == true) { //호출했을 때 해시필터 없을 경우에만 실행.
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alphahide);
            hastagView.setAnimation(animation);
            hastagView.setVisibility(mView.GONE);
            kms_hashTagCheckBoxFlagManager.flagSetFalsehashTagCheckBoxFlag(); //해시태그 체크박스 false 변경
        }
    }

    //8. FloatingIcon
    FloatingActionButton floatingButton;
    KMS_LocationFlagManager kms_locationFlagManager = KMS_LocationFlagManager.getInstanceLocation();

    public void setFloatingItem(boolean searchFlag) { //searchFlag 에 맞게 상단 검색 바 출력
        if (searchFlag == true)
            floatingButton.hide();
        else
            floatingButton.show();
    }

    public void onFloatingButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                if (kms_fragmentFlagManager.flagCheckFragment() == true) {
                    kms_locationFlagManager.flagSetTrueLocation();
                    SetToolbar(); //툴바 세팅
                    selectLocation.SetLinearLayout(getApplicationContext(), relativelayout_sub);
                } else {
                    IntentAddLocation();
                }
                setBottomBar(bottomBar, kms_locationFlagManager.flagGetLocation());
                setFloatingItem(kms_locationFlagManager.flagGetLocation());
                break;

            default:
                break;
        }
    } //onfloatingbuttoned 종료

    //9. Location Layout
    public static RelativeLayout relativelayout_sub;  // SelectLocation 단의 리니어 레이아웃
    LinearLayout linearLayout_selectLocation; // SelectLocation 단의 리니어 레이아웃
    KMS_SelectLocation selectLocation = new KMS_SelectLocation();
    boolean selectLocationFlag = false;     //장소추가 플래그
    boolean intentAddLocationFlag = false;  //장소 추가 인탠트 플래그

    public void IntentAddLocation() {
        Intent intent = new Intent(KMS_MainActivity.this, hep_LocationSaveActivity.class);
        CameraPosition cameraPosition = NMap.getCameraPosition();
        intent.putExtra("latitude",cameraPosition.target.latitude);
        intent.putExtra("longitude",cameraPosition.target.longitude);
        intent.putExtra("addr",((TextView)findViewById(R.id.selectLocation_AddressInfo)).getText());
        startActivityForResult(intent, ADD_MAIN_ACTIVITY_REQUEST_CODE);
        intentAddLocationFlag = true;
    }

    public void hideAddLocation(){ //장소 숨기는 함수
        kms_locationFlagManager.flagSetFalseLocation();
        getSupportActionBar().show();
        selectLocation.SetLinearLayout(getApplicationContext(), relativelayout_sub);
        setBottomBar(bottomBar, selectLocationFlag);
        setFloatingItem(selectLocationFlag);
    }

    //10. BackPressed
    KMS_BackPressedForFinish backPressedForFinish; //백프레스 클래스

    @Override
    public void onBackPressed() {
        // BackPressedForFinish 클래시의 onBackPressed() 함수를 호출한다.
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else if (kms_searchManager.flagGetSearch() == true) {
            getSupportActionBar().show();
            kms_searchManager.flagSetFalseSearch();
            setBottomBar(bottomBar, kms_searchManager.flagGetSearch());
            setSearchBar(kms_searchManager.flagGetSearch());
            setFloatingItem(kms_searchManager.flagGetSearch());
        }
        else if (kms_searchManager.flagGetSearch() == false && kms_recycleVIewManager.flagCheckRecycleView() == false
                && kms_locationFlagManager.flagGetLocation() == false && kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag() == false) {
            backPressedForFinish.onBackPressed();
            //서치상태 아닐때만 종료 가능
        }
        else { //드로워블도 없고 종료도 아니면 실행
            if (kms_locationFlagManager.flagGetLocation() == true) {
                hideAddLocation();
            }
            else if (intentAddLocationFlag == true && kms_fragmentFlagManager.flagCheckFragment() == true){ // 인텐트 상태이면서 맵에서 넘어왔을 경우
                intentAddLocationFlag = false; //인텐트 플래그 트루면 폴스로 바꿔줌
            }
            else if (intentAddLocationFlag == true && kms_fragmentFlagManager.flagCheckFragment() == false) { //인텐트 상태이면서 리스트에서 넘어왔을 경우
                intentAddLocationFlag = false; //인텐트 플래그 트루면 폴스로 바꿔줌
            }
            else if (kms_searchManager.flagGetSearch() == true) { //검색 트루였으면 액션바 다시 보여주고 false 로 바꿈
                getSupportActionBar().show();
                kms_searchManager.flagSetFalseSearch();
                //하단 바 및 아이콘 다시 원상복구시킴
                setSearchBar(kms_searchManager.flagGetSearch());
                setBottomBar(bottomBar, kms_searchManager.flagGetSearch());
                setFloatingItem(kms_searchManager.flagGetSearch());
            }
            else if(kms_recycleVIewManager.flagCheckRecycleView() == true){ //스피너 떠있으면 꺼준다.
                setSpinner();
                setFloatingItem(kms_recycleVIewManager.flagCheckRecycleView());
            }

            else if (kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag() == true) {
                hideHashTagFilter();
                setFloatingItem(kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag());
            }
        } //드로워블도 없고 종료도 아니면 실행
    }

    // 어플키면 먼저 map 보여주기위한 fragmentManager.begin 코드 들어가야함 !
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_notice:
                Intent noticeIntent = new Intent(this, KSH_NoticeIntent.class);
                startActivity(noticeIntent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;
            case R.id.nav_call:
                break;
            case R.id.nav_set:
                Intent setIntent = new Intent(this, KSH_SetIntent.class);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;
            case R.id.nav_help:
                Intent helpIntent = new Intent(this, KSH_HelpIntent.class);
                startActivity(helpIntent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // . Context 넘겨주기
    public static Context mainContext; //AddMainActivity 에 넘겨주기 위해 컨텍스트 생성
    public static final int ADD_MAIN_ACTIVITY_REQUEST_CODE = 1000;

    private ArrayList<KMS_LocationSearchResult> mArrayList;
    private KMS_SearchResultAdapter mAdapter;
    public static int count = -1;

    public void AddRecyclerView(){
        count++;
        KMS_LocationSearchResult data = new KMS_LocationSearchResult(count + "1", "Apple" + count);
        mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
        mAdapter.notifyDataSetChanged();
    }

    public void LoadRecyclerView(){
        InitRecyclerView();
        for(int i = 0; i < 5; i++){
            count++;
            KMS_LocationSearchResult data = new KMS_LocationSearchResult("Title : "+ count, " RoadAddress : " + count);
            //이걸로 카메라포지션 넘겨줌
            data.setLatitude(35.857654);
            data.setLongitude(128.498123); //받아온 값
            mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
            mAdapter.notifyDataSetChanged();
        }
    }

    public void InitRecyclerView(){
        count = -1;
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();
        KMS_SearchResultAdapter.LastPosition = -1;
    }

    public static int selectView = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kms_activity_main);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.searchResult_RecyclerVIew);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mArrayList = new ArrayList<>();
        mAdapter = new KMS_SearchResultAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        int e = 3;

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        LoadRecyclerView(); //기존 저장 함수 불러옴
        ksh_init();
        kms_init();
        logtest("온크리트 초기 flag  값");

//        Query directoryQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory").orderByChild("token").equalTo(new hep_FirebaseUser().getFirebaseUserInstance().getUid());
//        directoryQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                arrayList.clear();
//                arrayKey.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    ksh_directoryEntity = dataSnapshot.getValue(KSH_DirectoryEntity.class); // 만들어둔 Test 객체에 데이터를 담는다
//                    String key = dataSnapshot.getKey();
//                    arrayList.add(ksh_directoryEntity);  // 담은 데이터들을 arraylist에 넣고 recyclerview로 보낼 준비
//                    arrayKey.add(key);
//                }
//                if (recyAdapter != null)
//                    recyAdapter.notifyDataSetChanged(); // list 저장 및 새로고침
//
//                new hep_FireBase().getRecentData(new hep_Callback() {
//                    @Override
//                    public void onSuccess(hep_Recent hep_recent) {
//                        for (int i = 0; i < arrayKey.size(); i++) {
//                            if (directoryid == null) {
//                                if (hep_recent.directoryid.equals(arrayKey.get(i))) {
//                                    selectView = i + 1;
//                                    directoryid = hep_recent.directoryid;
//                                }
//
//                                if (arrayKey.size() == i && selectView == 0) {
//                                    directoryid = arrayKey.get(0);
//                                    selectView = 1;
//                                }
//                            }
//                        }
//                        recyAdapter = new KSH_RecyAdapter(KMS_MainActivity.this, arrayList, arrayKey, ksh_directoryEntity, selectView);
//                        recyclerView.setAdapter(recyAdapter);
//
//                        Query latilonginameQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location").orderByChild("directoryid").equalTo(directoryid);
//                        latilonginameQuery.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                new KMS_MarkerManager().getInstanceMarkerManager().initMarker();
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                    hep_Location hep_location = dataSnapshot.getValue(hep_Location.class);
//                                    new KMS_MarkerManager().getInstanceMarkerManager().addMarker(hep_location.name, hep_location.latitude, hep_location.longitude);
//
////                                    LatLng addMarkerLatLng = new LatLng(hep_location.latitude, hep_location.longitude);
////                                    //현재 장소 위경도값 받아와서 좌표 추가
////                                    Marker marker = new Marker();
////                                    marker.setPosition(addMarkerLatLng);
////
////                                    //마커 텍스트
////                                    marker.setCaptionText(hep_location.name);
////                                    marker.setCaptionRequestedWidth(200); //이름 최대 폭
////
////                                    //마커 이미지
////                                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_design_pika2));
////                                    marker.setWidth(120);
////                                    marker.setHeight(160);
////
////                                    marker.setMap(NMap);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onSuccess(hep_LocationTag hep_locationTag) {
//
//                    }
//
//                    @Override
//                    public void onFail(String errorMessage) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("1", " error "+String.valueOf(databaseError.toException()));
//            }
//        });

        recyAdapter = new KSH_RecyAdapter(KMS_MainActivity.this, arrayList, arrayKey, ksh_directoryEntity, selectView);
        recyclerView.setAdapter(recyAdapter);

        // loading
        Intent intent = new Intent(this, KSH_LoadingActivity.class);
        startActivity(intent);

        //1.Fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment mapFragment = new KMS_MapFragment();
//        fragmentManager.beginTransaction().replace(R.id.frameLayout, mapFragment).commit();

        //2. BottomBar
        bottomBar = findViewById(R.id.linearBottombar);

        //3. Toolbar
        //3-1. tollbar 선언
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Last Legend Start");
        // **NoActionBar 해주고 이 메서드 호출하면 toolbar를 Activity의 앱바로 사용가능
        setSupportActionBar(toolbar);

        // drawer
        navigationView.setNavigationItemSelectedListener(this);

        // navigationview에 사용자 이름, 이메일 출력
        View header = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //3-2. spinner 선언 & RecycleView
        spinner = findViewById(R.id.spinner);
        // spinner 터치(클릭) 시 이벤트처리
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //임시
                if (event.getAction() == MotionEvent.ACTION_DOWN && kms_recycleVIewManager.flagCheckRecycleView() == false) {
                    setSpinner();
                    setFloatingItem(kms_recycleVIewManager.flagCheckRecycleView());
                } else if (event.getAction() == MotionEvent.ACTION_DOWN && kms_recycleVIewManager.flagCheckRecycleView() == true) {
                    setSpinner();
                    setFloatingItem(kms_recycleVIewManager.flagCheckRecycleView());
                }
                return true;
            } //onTouch 종료
        }); //spinner ontouchListiner 종료

        allSeeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fView.getContext(), KSH_AllSeeActivity.class);
                intent.putExtra("array",arrayList);
                intent.putExtra("key",arrayKey);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        //4. Toolbar Search
        linearLayoutToolbarSearch = findViewById(R.id.linearLayoutToolbarSearch);

        //6. 자동완성 텍스트 뷰
        clearableEditText_loadLocation = findViewById(R.id.searchView); //프로젝트 단위

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //키보드-시스템서비스
        list = new ArrayList<String>();

        //https://sharp57dev.tistory.com/12 자동완성
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.clearable_edit_load_location);
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list));

        //7. HashTag
        hastagView = findViewById(R.id.HasTagView);
        hastagView.setBackgroundResource(R.drawable.hashtag);

//        addHashTag(); //해시태그 추가
//        checkAllHashTag(); //체크 해시태그
        View test_view = findViewById(R.id.drawer_layout);
        KMS_HashTagCheckBoxManager kms_hashTagCheckBoxManager = new KMS_HashTagCheckBoxManager(this, test_view);
        kms_hashTagCheckBoxManager.addHashTag();
        kms_hashTagCheckBoxManager.checkAllHashTag();


        // 8.floating icon
        floatingButton = findViewById(R.id.floatingActionButton);

        //9. Location Layout
        mainContext = this;
        relativelayout_sub = findViewById(R.id.relativeLayout_s);
        linearLayout_selectLocation = findViewById(R.id.linearLayout_s);
        linearLayout_selectLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        IntentAddLocation();      //추후 수정해야함

                        if(kms_fragmentFlagManager.flagCheckFragment() == true)
                            hideAddLocation(); //맵프레그먼트에서 넘어왔으면 추가 창 숨기기
                        return true;
                }
                return false;
            }
        });

        //10.BackPressed
        backPressedForFinish = new KMS_BackPressedForFinish(this);
    } //oncreate 종료

    ////////////////////////////////////////////////////////// addHashTag, checkAllHAshTag CheckBoxManager class 에 따로 빼기 /////////////////////////////
//    public void addHashTag() { //초기 해시태그 세팅
//        for (int i = 1; i < msHashTag.length; i++) {
//            msHashTag[i] = new KMS_HashTag(this);
//            msHashTag[i].setOnClickListener(hasTagOnClickListener);
//            msHashTag[i].setId(i);
//            if (i % 3 == 0)
//                msHashTag[i].init("1", "#22FFFF", R.drawable.hashtagborder, params);
//            else if (i % 2 == 0)
//                msHashTag[i].init("초기값", "#22FFFF", R.drawable.hashtagborder, params);
//            else
//                msHashTag[i].init("asdfan32of2ofndladf", "#3F729B", R.drawable.hashtagborder, params);
//
//            ((KMS_FlowLayout) findViewById(R.id.HashTagflowlayout)).addView(msHashTag[i]);
//        }
//    }//addHashTag 종료
//
//    public void checkAllHashTag() { //해시태그 모두 선택
//        checkBoxAllHashTag = findViewById(R.id.checkBox);
//        checkBoxAllHashTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (checkBoxAllHashTag.isChecked()) {
//                    kms_hashTagCheckBoxFlagManager.CheckBoxAllClick(KMS_MainActivity.this);
//                } else
//                    kms_hashTagCheckBoxFlagManager.CheckBoxAllUnClick(getApplicationContext());
//            }
//        });
//    } //checkAllHashTag 종료
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void logtest(String s){
        Log.d("이벤트 = ", "" + s);
        Log.d("fragment #flag = ", "" + kms_fragmentFlagManager.flagCheckFragment());
        Log.d("location #flag = ", "" + kms_locationFlagManager.flagGetLocation());
        Log.d("search #flag = ", "" + kms_searchManager.flagGetSearch());
        Log.d("hashtag #flag = ", "" + kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag());

    }
    //This method receive intent from closed activity
    //ADD_MAIN_ACTIVITY_REQUEST_CODE is
    //when LocationSaveActivity closed, Showing fragment of Location Recyclerview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode == ADD_MAIN_ACTIVITY_REQUEST_CODE){
             if(resultCode == RESULT_OK){
                 if(data.getBooleanExtra("result",false)) {
                     KMS_FragmentFlagManager d = KMS_FragmentFlagManager.getInstanceFragment();
                     d.setFragmentLocationListLayout();
                 }
             }
         }
    }

} //mainactivity 종료

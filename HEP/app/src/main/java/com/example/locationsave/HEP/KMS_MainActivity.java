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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Address.AreaSearch;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Callback;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Recent;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_FirebaseUser;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.KMS.BackPressed.KMS_BackPressedForFinish;
import com.example.locationsave.HEP.KMS.HashTag.KMS_FlowLayout;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTag;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxFlagManager;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationFlagManager;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationSearchResult;
import com.example.locationsave.HEP.KMS.Location.KMS_SearchResultAdapter;
import com.example.locationsave.HEP.KMS.Location.KMS_SelectLocation;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_FragmentManager;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_ClearableEditText_LoadLocation;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_RecycleVIewManager;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_SearchManager;
import com.example.locationsave.HEP.KSH.KSH_AllSeeActivity;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.HEP.KSH.KSH_FireBase;
import com.example.locationsave.HEP.KSH.KSH_LoadingActivity;
import com.example.locationsave.HEP.KSH.KSH_RecyAdapter;
import com.example.locationsave.HEP.KSH.NavIntent.KSH_NoticeIntent;
import com.example.locationsave.HEP.pcs_RecyclerView.Pcs_LocationRecyclerView;
import com.example.locationsave.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

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
//        toolbar = findViewById(R.id.dra_toolbar);
//        setSupportActionBar(toolbar);
//        spinner = findViewById(R.id.spinner);
        rView = findViewById(R.id.include_recyclerView);
        // frameLayout 위에 recyclerView가 나타나야함으로 frameLayout 선언
        fView = findViewById(R.id.frameLayout);
        allSeeView = findViewById(R.id.recy_allSee);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        arrayList = new ArrayList<>();  // 객체 담아서 adapter로 보낼 arraylist
        arrayKey = new ArrayList<>();
//        firebaseDatabase = FirebaseDatabase.getInstance();  // firebase db 연동
//        databaseReference = firebaseDatabase.getReference().child("Test");  // db table 연결
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // 싱글톤
        KSH_FireBase firebaseDatabase = KSH_FireBase.getInstance();

        AreaSearch areaSearch = new AreaSearch();
        areaSearch.Geocoding("신당동 164");
    }

    //1.Fragment
    public FragmentManager fragmentManager;
    public Fragment mapFragment, LocationFragmet = null;
    KMS_FragmentManager fm = KMS_FragmentManager.getInstanceFragment(); //프래그먼트 인스턴스 생성
    View mView;

    public void setFragmentMapLayout(){ //맵 프레그먼트 출력 함수
        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commit();/*프래그먼트 매니저가 프래그먼트를 담당한다!*/
        if (mapFragment == null) {
            mapFragment = new ListFragment();
            fragmentManager.beginTransaction().add(R.id.frameLayout, mapFragment).commit();
        }
        if (mapFragment != null) {
            //clearSearchBar(ac); //서치바 초기화
            fragmentManager.beginTransaction().show(mapFragment).commit();
            //Toast.makeText(this, "맵 생성완료", Toast.LENGTH_SHORT).show();
        }
        if (LocationFragmet != null)
            fragmentManager.beginTransaction().hide(LocationFragmet).commit();
    }
    public void setFragmentLocationListLayout() {  //리스트 프레그먼트 출력 시
        if (LocationFragmet == null) {

//            LocationFragmet = new KMS_LocationFragment();
            LocationFragmet = new Pcs_LocationRecyclerView();
            fragmentManager.beginTransaction().add(R.id.frameLayout, LocationFragmet).commit();
        }
        if (mapFragment != null) {
            fragmentManager.beginTransaction().hide(mapFragment).commit();
        }
        if (LocationFragmet != null){
            //##############해야됨
            //clearSearchBar(ac); //서치바 초기화
            //##############해야됨
            fragmentManager.beginTransaction().show(LocationFragmet).commit();
        }
    }


    //2.Bottomber
    //하단 바
    public LinearLayout bottomBar;

    public void onBottomBarClicked(View v) { //Change Fragment
        switch (v.getId()) {
            case R.id.btnMain:
                //fragmentFlag = true;
                fm.flagSetTrueFragment(); //프레그먼트 플래그 true 로 변경
                setFragmentMapLayout(); //프래그먼트 맵 띄우기

                logtest("맵 프래그먼트");
                Log.d("dd","dd");
                break;
            case R.id.btnLocationList:
                //fragmentFlag = false;
                fm.flagSetFalseFragment(); //프래그먼트 플래그 false 로 변경
                setFragmentLocationListLayout(); //프래그먼트 장소 리스트 띄우기

                logtest("리스트 프래그먼트");
                Log.d("dd2","dd");

                break;
            default:
                break;
        }
    }
    public void setBottomBar(LinearLayout bottomBar, boolean Flag) { //searchFlag 에 맞게 하단 바 가리기
        if (Flag == true){
            Log.d("####setBottomBar","bottomBar GONE, flag = " + Flag);
            bottomBar.setVisibility(View.GONE);
        }
        else{
            bottomBar.setVisibility(View.VISIBLE);
            Log.d("####setBottomBar","bottomBar VISIBLE, flag = " + Flag);
        }
    }


    //3-1. Toolbar
    //우선 style.xml 액션바 제거
    Toolbar toolbar;
    Spinner spinner;
    KMS_RecycleVIewManager rm = KMS_RecycleVIewManager.getInstanceRecycleView();

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
//    View test_view; //성훈 리사이클 뷰

    public void setSpinner(){
        if(rm.flagCheckRecycleView() == true){ //리사이클 flag 가 true일 경우
            Animation animationH = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translatehide);
            rView.setAnimation(animationH);
            rView.setVisibility(fView.GONE);
            Log.d("####setSpinner", "리사이클 트루");
            rm.flagSetFalseRecycleView(); //리사이클 flag 를 false 로 변경
        }
        else if(rm.flagCheckRecycleView() == false){ //리사이클 flag 가 false일 경우
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
            rView.setAnimation(animation);
            rView.setVisibility(fView.VISIBLE);
            Log.d("####setSpinner", "리사이클 폴스");
            rm.flagSetTrueRecycleView(); //리사이클 flag를 true로 변경
        }
    }


    //4. Toolbar Search
    LinearLayout linearLayoutToolbarSearch;
    ConstraintLayout recy_con_layout;
    KMS_SearchManager sm = KMS_SearchManager.getInstanceSearch();

    public void hideRecyclerView() { //리사이클 플래그가 false 이면 - 리사이클러 뷰가 안보이면 실행해준다. true 로 바꾼다.
        Log.d("####hideRecyclerView", "함수 실행", null);
        if (rm.flagCheckRecycleView() == true) { //호출하였을 때 리사이클이 떠있을 경우에만 실행한다. 안떠있을 때 재실행 방지.
            animationH = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translatehide);
            Log.d("####itemSelected", "애니메이션 실행", null);
            recy_con_layout.setAnimation(animationH);
            recy_con_layout.setVisibility(mView.GONE);
            rm.flagSetFalseRecycleView();
        }
    }

    //다시
    //상단 툴바 클릭 이벤트
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("####itemSelected", "툴바 아이템 선택", null);

        switch (item.getItemId()) {
            case R.id.menu_item_search: {//상단 검색 버튼 클릭 시
                Log.d("####itemSelected", "검색 버튼 클릭", null);
                if(rm.flagCheckRecycleView() == true){ // 만약 리사이클뷰 열려있으면 닫아준다.
                    setSpinner(); //이걸로 제어
                    Log.d("####itemSelected", "setSpinner 실행", null);
                    hideRecyclerView(); //일단 디렉토리 열려있으면 삭제
                    Log.d("####itemSelected", "hideRecyclerView 실행", null);
                }//임시

                Toast.makeText(getApplicationContext(), "검색할 장소를 입력하세요.", Toast.LENGTH_LONG).show();
                Log.d("####itemSelected", "검색할 장소 입력하세요.");
                //툴바 제거
                if (getSupportActionBar().isShowing()) {
                    //searchFlag = true;
                    sm.flagSetTrueSearch();
                    getSupportActionBar().hide();

                    clearableEditText_loadLocation.requestFocus();
                    //editText.setFocusableInTouchMode(true);
                    //clearbleText.requestFocus();

                    //inputMethodManager.showSoftInput(ac, 0);
                    //키보드 UP
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    Log.d("####keyboard", "키보드 UP");

                    //키보드 DOWN
                    /*View view = this.getCurrentFocus();
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);*/

                    Log.d("####keyboard", "키보드 DOWN");

//                    출처: https://kkangsnote.tistory.com/35 [깡샘의 토마토]

                    setBottomBar(bottomBar, sm.flagGetSearch());
                    setSearchBar(sm.flagGetSearch());
                    setFloatingItem(sm.flagGetSearch());
                }
                return true;
            } //검색 버튼 종료

            //해야됨

            case R.id.menu_tag_filter: {
                showHashTagFilter(); // 안보인다면 해시태그를 보이게 한 뒤 해시플래그를 트루로 만든다.
                //setFloatingItem(hashTagFilterFlag); //해시플래그가 트루면 숨긴다.

                return true;
            }

            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }


    //키보드
    public static void setKeyBoard(Context context){

       // inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        //키보드 DOWN
/*
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
*/
        Log.d("####keyboard", "키보드 다운");
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
    public class HasTagOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            hm.HashTagClickEvent(KMS_MainActivity.this, v);
        }
    }

    CheckBox checkBoxAllHashTag; //체크박스 명 선언
    public static KMS_HashTag[] msHashTag = new KMS_HashTag[10]; //태그 배열
    public static KMS_FlowLayout.LayoutParams params = new KMS_FlowLayout.LayoutParams(20, 20);
    ; //해시태그 레이아웃을 위한 parms
    KMS_HashTagCheckBoxFlagManager hm = KMS_HashTagCheckBoxFlagManager.getInstanceHashTagCheckBox();
    HasTagOnClickListener ob = new HasTagOnClickListener();

    // 확인을 눌렀을 때 눌린 태그들의 id값을 가져온다.

    public void onHashTagFilterButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.btnFilterSelect:
                hm.AddClickHashTag(this);
                break;
            case R.id.btnFilterCancel:
                hideHashTagFilter();
                setFloatingItem(hm.flagGethashTagCheckBoxFlag());
                break;
            default:
                break;
        }
    }

    public void showHashTagFilter() {  //해시태그 체크박스 띄움
        if (hm.flagGethashTagCheckBoxFlag() == false) { //호출했을 때 해시필터 없을 경우에만 실행.
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
            hastagView.setAnimation(animation);
            hastagView.setVisibility(mView.VISIBLE);
            hm.flagSetTruehashTagCheckBoxFlag(); //해시태그 체크박스 true로 변경
        }
    }

    public void hideHashTagFilter() { //해시태그 체크박스 숨김
        if (hm.flagGethashTagCheckBoxFlag() == true) { //호출했을 때 해시필터 없을 경우에만 실행.
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alphahide);
            hastagView.setAnimation(animation);
            hastagView.setVisibility(mView.GONE);
            hm.flagSetFalsehashTagCheckBoxFlag(); //해시태그 체크박스 false 변경
        }
    }


    //8. FloatingIcon
    FloatingActionButton floatingButton;
    KMS_LocationFlagManager lm = KMS_LocationFlagManager.getInstanceLocation();

    public void setFloatingItem(boolean searchFlag) { //searchFlag 에 맞게 상단 검색 바 출력
        if (searchFlag == true)
            floatingButton.hide();
        else
            floatingButton.show();
    }

    public void onFloatingButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
//clearSearchBar(ac); //서치바 초기화
                Log.d("floatingButton","####");

                if (fm.flagCheckFragment() == true) {
                    lm.flagSetTrueLocation();
                    SetToolbar(); //툴바 세팅
                    selectLocation.SetLinearLayout(getApplicationContext(), relativelayout_sub);
                } else {
                    IntentAddLocation();
                }
                //floating icon
                setBottomBar(bottomBar, lm.flagGetLocation());
                setFloatingItem(lm.flagGetLocation());

                //만약 리스트에서 누른거면 바로 인텐트
                break;

            default:
                break;
        }
    } //onfloatingbuttoned 종료


    //9. Location Layout
    public static RelativeLayout relativelayout_sub;  // SelectLocation 단의 리니어 레이아웃
    LinearLayout linearLayout_selectLocation; // SelectLocation 단의 리니어 레이아웃
    KMS_SelectLocation selectLocation = new KMS_SelectLocation();
    //장소추가 플래그
    boolean selectLocationFlag = false;

    //장소 추가 인탠트 플래그
    boolean intentAddLocationFlag = false;

    public void IntentAddLocation() {
        Intent intent = new Intent(KMS_MainActivity.this, hep_LocationSaveActivity.class);

        CameraPosition cameraPosition = NMap.getCameraPosition();
        intent.putExtra("latitude",cameraPosition.target.latitude);
        intent.putExtra("longitude",cameraPosition.target.longitude);
        intent.putExtra("addr",((TextView)findViewById(R.id.selectLocation_AddressInfo)).getText());


        startActivityForResult(intent, ADD_MAIN_ACTIVITY_REQUEST_CODE);
        intentAddLocationFlag = true;
        //GlobalFlag.getInstance().setIntentAddLocationFlag(); //인텐트하면 값 바꿔줌
        //Log.d("intentAddLocationFlag",  " " + GlobalFlag.getInstance().getIntentAddLocationFlag());
    }

    public void hideAddLocation(){ //장소 숨기는 함수
        lm.flagSetFalseLocation();
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

        else if (sm.flagGetSearch() == true) {
            Log.d("3","000000000000");
            getSupportActionBar().show();
            sm.flagSetFalseSearch();

            setBottomBar(bottomBar, sm.flagGetSearch());
            setSearchBar(sm.flagGetSearch());
            setFloatingItem(sm.flagGetSearch());
        }
        else if (sm.flagGetSearch() == false && rm.flagCheckRecycleView() == false && lm.flagGetLocation() == false && hm.flagGethashTagCheckBoxFlag() == false) {
            Log.d("3","111111");
            backPressedForFinish.onBackPressed();
            //서치상태 아닐때만 종료 가능
        }
        else { //드로워블도 없고 종료도 아니면 실행
            if (lm.flagGetLocation() == true) {
                Log.d("3","6666");

                hideAddLocation();
            }
            else if (intentAddLocationFlag == true && fm.flagCheckFragment() == true){ // 인텐트 상태이면서 맵에서 넘어왔을 경우
                //setFragmentMapLayout(); //맵 띄우기
                Log.d("3","222222");
                intentAddLocationFlag = false; //인텐트 플래그 트루면 폴스로 바꿔줌
            }
            else if (intentAddLocationFlag == true && fm.flagCheckFragment() == false) { //인텐트 상태이면서 리스트에서 넘어왔을 경우
                Log.d("3","333333");
                intentAddLocationFlag = false; //인텐트 플래그 트루면 폴스로 바꿔줌
            }

            else if (sm.flagGetSearch() == true) { //검색 트루였으면 액션바 다시 보여주고 false 로 바꿈
                Log.d("3","444444");

                getSupportActionBar().show();
                sm.flagSetFalseSearch();
                //하단 바 및 아이콘 다시 원상복구시킴
                setSearchBar(sm.flagGetSearch());
                setBottomBar(bottomBar, sm.flagGetSearch());
                setFloatingItem(sm.flagGetSearch());
            }
            else if(rm.flagCheckRecycleView() == true){ //스피너 떠있으면 꺼준다.
                Log.d("3","55555");

                setSpinner();
                setFloatingItem(rm.flagCheckRecycleView());
            }

            else if (hm.flagGethashTagCheckBoxFlag() == true) {
                Log.d("3","77777");

                hideHashTagFilter();
                setFloatingItem(hm.flagGethashTagCheckBoxFlag());
            }
        } //드로워블도 없고 종료도 아니면 실행
    }

    // 어플키면 먼저 map 보여주기위한 fragmentManager.begin 코드 들어가야함 !
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_notice:
                Intent intent = new Intent(this, KSH_NoticeIntent.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                // 밑에는 실수로 fragment
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KSH_NoticetFragment()).commit();
                break;
            case R.id.nav_excel:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KSH_ExceltFragment()).commit();
                break;
            case R.id.nav_call:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KSH_CalltFragment()).commit();
                break;
            case R.id.nav_help:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KSH_HelptFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    // . Context 넘겨주기
    public static Context mainContext; //AddMainActivity 에 넘겨주기 위해 컨텍스트 생성
    public static final int ADD_MAIN_ACTIVITY_REQUEST_CODE = 1000;
    public static final int ADD_MAIN_ACTIVITY_REPLY_CODE = 2000;
    public static final int ALLSEE_ACTIVITY_REQUEST_CODE = 3000;
    public static final int ALLSEE_ACTIVITY_REPLY_CODE = 4000;



    private ArrayList<KMS_LocationSearchResult> mArrayList;
    private KMS_SearchResultAdapter mAdapter;
    public static int count = -1;

    public void AddRecyclerView(){
        count++;

        KMS_LocationSearchResult data = new KMS_LocationSearchResult(count + "1", "Apple" + count);

        //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
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

            //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
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

/*        KMS_SearchRecyclerView searchRecyclerView = new KMS_SearchRecyclerView();
        searchRecyclerView.SetSearchRecyclerView();*/
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.searchResult_RecyclerVIew);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();

        mAdapter = new KMS_SearchResultAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        int e = 3;
        Log.d("####","셋 밑에" + e);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        LoadRecyclerView(); //기존 저장 함수 불러옴

        ksh_init();
        logtest("온크리트 초기 flag  값");

        Query directoryQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory").orderByChild("token").equalTo(new hep_FirebaseUser().getFirebaseUserInstance().getUid());
        directoryQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                arrayKey.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ksh_directoryEntity = dataSnapshot.getValue(KSH_DirectoryEntity.class); // 만들어둔 Test 객체에 데이터를 담는다
                    String key = dataSnapshot.getKey();
                    arrayList.add(ksh_directoryEntity);  // 담은 데이터들을 arraylist에 넣고 recyclerview로 보낼 준비
                    arrayKey.add(key);
//                    Log.d("6","초기 key 값 확인 "+arrayKey.get(0));
                }
                if(recyAdapter != null)
                    recyAdapter.notifyDataSetChanged(); // list 저장 및 새로고침

                new hep_FireBase().getRecentData(new hep_Callback() {
                    @Override
                    public void onSuccess(hep_Recent hep_recent) {
                        for (int i = 0; i < arrayKey.size(); i++) {
                            if (directoryid == null) {
                                if (hep_recent.directoryid.equals(arrayKey.get(i))) {
                                    selectView = i + 1;
                                    directoryid = hep_recent.directoryid;
                                }

                                if (arrayKey.size() == i && selectView == 0) {
                                    directoryid = arrayKey.get(0);
                                    selectView = 1;
                                }
                            }
                        }
                        recyAdapter = new KSH_RecyAdapter(KMS_MainActivity.this, arrayList, arrayKey, ksh_directoryEntity, selectView);
                        recyclerView.setAdapter(recyAdapter);

                        Query latilonginameQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location").orderByChild("directoryid").equalTo(directoryid);
                        latilonginameQuery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                    hep_Location hep_location = dataSnapshot.getValue(hep_Location.class);
                                    Log.d("@@@@", "latitude = " + hep_location.latitude + ", longitude = " + hep_location.longitude + ", name = " + hep_location.name);

                                    LatLng addMarkerLatLng = new LatLng(hep_location.latitude, hep_location.longitude);
                                    //현재 장소 위경도값 받아와서 좌표 추가
                                    Marker marker = new Marker();
                                    marker.setPosition(addMarkerLatLng);

                                    //마커 텍스트
                                    marker.setCaptionText(hep_location.name);
                                    marker.setCaptionRequestedWidth(200); //이름 최대 폭

                                    //마커 이미지
                                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_design_pika2));
                                    marker.setWidth(120);
                                    marker.setHeight(160);

                                    marker.setMap(NMap);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onSuccess(hep_LocationTag hep_locationTag) {

                    }

                    @Override
                    public void onFail(String errorMessage) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // loading
        Intent intent = new Intent(this, KSH_LoadingActivity.class);
        startActivity(intent);
        //1.Fragment
        fragmentManager = getSupportFragmentManager();
        mapFragment = new KMS_MapFragment();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, mapFragment).commit();

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
        ((TextView)header.findViewById(R.id.navUserName)).setText(new hep_FirebaseUser().getFirebaseUserInstance().getDisplayName());
        ((TextView)header.findViewById(R.id.navUserEmail)).setText(new hep_FirebaseUser().getFirebaseUserInstance().getEmail());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //3-2. spinner 선언 & RecycleView
        spinner = findViewById(R.id.spinner);
        // spinner 터치(클릭) 시 이벤트처리
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("####","spinner onTouch");
                //임시
                if (event.getAction() == MotionEvent.ACTION_DOWN && rm.flagCheckRecycleView() == false) {
                    setSpinner();
                    setFloatingItem(rm.flagCheckRecycleView());
                } else if (event.getAction() == MotionEvent.ACTION_DOWN && rm.flagCheckRecycleView() == true) {
                    setSpinner();
                    setFloatingItem(rm.flagCheckRecycleView());
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

        addHashTag(); //해시태그 추가
        checkAllHashTag(); //체크 해시태그

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

                        if(fm.flagCheckFragment() == true)
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
    public void addHashTag() { //초기 해시태그 세팅
        for (int i = 1; i < msHashTag.length; i++) {
            msHashTag[i] = new KMS_HashTag(this);
            msHashTag[i].setOnClickListener(ob);
            msHashTag[i].setId(i);
            if (i % 3 == 0)
                msHashTag[i].init("1", "#22FFFF", R.drawable.hashtagborder, params);
            else if (i % 2 == 0)
                msHashTag[i].init("초기값", "#22FFFF", R.drawable.hashtagborder, params);
            else
                msHashTag[i].init("asdfan32of2ofndladf", "#3F729B", R.drawable.hashtagborder, params);

            ((KMS_FlowLayout) findViewById(R.id.HashTagflowlayout)).addView(msHashTag[i]);
        }
    }//addHashTag 종료

    public void checkAllHashTag() { //해시태그 모두 선택
        checkBoxAllHashTag = findViewById(R.id.checkBox);
        checkBoxAllHashTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxAllHashTag.isChecked()) {
                    hm.CheckBoxAllClick(KMS_MainActivity.this);
                } else
                    hm.CheckBoxAllUnClick(getApplicationContext());
            }
        });
    } //checkAllHashTag 종료
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void logtest(String s){
        Log.d("이벤트 = ", "" + s);

        Log.d("fragment #flag = ", "" + fm.flagCheckFragment());
        Log.d("location #flag = ", "" + lm.flagGetLocation());
        Log.d("search #flag = ", "" + sm.flagGetSearch());
        Log.d("hashtag #flag = ", "" + hm.flagGethashTagCheckBoxFlag());
    }

} //mainactivity 종료

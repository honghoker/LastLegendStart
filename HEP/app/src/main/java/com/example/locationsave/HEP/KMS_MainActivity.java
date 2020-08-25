package com.example.locationsave.HEP;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.example.locationsave.HEP.CJH.CJH_RoundedImageView;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Callback;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Recent;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_FirebaseUser;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.Hep.hep_closeAppService;
import com.example.locationsave.HEP.KMS.BackPressed.KMS_BackPressedForFinish;
import com.example.locationsave.HEP.KMS.HashTag.KMS_FlowLayout;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTag;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxFlagManager;
import com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxManager;
import com.example.locationsave.HEP.KMS.HashTag.Pcs_HashTagCallback;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationFlagManager;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationSearchResult;
import com.example.locationsave.HEP.KMS.Location.KMS_SearchResultAdapter;
import com.example.locationsave.HEP.KMS.Location.KMS_SelectLocation;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_FragmentFlagManager;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.HEP.KMS.Map.KMS_MapOption;
import com.example.locationsave.HEP.KMS.Map.KMS_MarkerInformationFlagManager;
import com.example.locationsave.HEP.KMS.Map.KMS_MarkerManager;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_ClearableEditText_LoadLocation_auto;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_RecycleVIewManager;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_SearchBarManager;
import com.example.locationsave.HEP.KMS.Toolbar.KMS_SearchFlagManager;
import com.example.locationsave.HEP.KMS.Toolbar.KSH_LoadLocation;
import com.example.locationsave.HEP.KSH.KSH_AllSeeActivity;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.HEP.KSH.KSH_LoadingActivity;
import com.example.locationsave.HEP.KSH.KSH_NetworkStatus;
import com.example.locationsave.HEP.KSH.KSH_RecyAdapter;
import com.example.locationsave.HEP.KSH.NavIntent.KSH_HelpIntent;
import com.example.locationsave.HEP.KSH.NavIntent.KSH_NoticeIntent;
import com.example.locationsave.HEP.KSH.NavIntent.KSH_SetIntent;
import com.example.locationsave.HEP.KSH.sunghunTest;
import com.example.locationsave.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.naver.maps.map.CameraPosition;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.widget.AdapterView.GONE;
import static android.widget.AdapterView.OnClickListener;
import static android.widget.AdapterView.VISIBLE;
import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.NMap;
import static com.example.locationsave.HEP.KMS.Toolbar.KMS_ClearableEditText_LoadLocation_auto.editText_1;
import static com.example.locationsave.HEP.KMS.Toolbar.KMS_ClearableEditText_LoadLocation_auto.mContext;
import static com.example.locationsave.HEP.KSH.KSH_RecyAdapter.LastPosition;

public class KMS_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, sunghunTest {

    @Override
    public void onClick(String value, View view) {
        toolbar.setTitle(value);
        Animation animationH = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translatehide);
        rView.setAnimation(animationH);
        rView.setVisibility(fView.GONE);
        kms_recycleVIewManager.flagSetFalseRecycleView(); //리사이클 flag 를 false 로 변경
        setFloatingItem(kms_recycleVIewManager.flagCheckRecycleView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocationFragmet = null;
        Query recentQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").orderByChild("token").equalTo(new hep_FirebaseUser().getFirebaseUserInstance().getUid());
        recentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> result = new HashMap<>();
                CameraPosition cameraPosition = NMap.getCameraPosition();
                result.put("latitude", cameraPosition.target.latitude);
                result.put("longitude", cameraPosition.target.longitude);
                if (directoryid != null)
                    result.put("directoryid", directoryid);

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").child(dataSnapshot.getKey()).updateChildren(result);
                    }
                } else {
                    result.put("token", new hep_FirebaseUser().getFirebaseUserInstance().getUid());
                    new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").push().setValue(result);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void networkStatus() {
        int status = KSH_NetworkStatus.getConnectivityStatus(getApplicationContext());
        if (status == KSH_NetworkStatus.TYPE_NOT_CONNECTED) {
            Toast.makeText(this, "인터넷에 연결되지 않았습니다.", Toast.LENGTH_SHORT).show();
            this.finish();
            System.exit(0);
        }
    }

    public static String directoryid = null;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private View rView;
    public static View fView;
    private View allSeeView;
    private RecyclerView.Adapter recyAdapter;
    private ArrayList<KSH_DirectoryEntity> arrayList;
    private ArrayList<String> arrayKey;
    private NavigationView navigationView;
    private KSH_DirectoryEntity ksh_directoryEntity;
    private KMS_HashTagCheckBoxManager kms_hashTagCheckBoxManager; //HASHTAG BOX
    private KMS_HashTagCheckBoxFlagManager kms_hashTagCheckBoxFlagManager;
    private Object OnItemClickListener;
    private Button BtnMain;
    private Button BtnList;

    public void ksh_init() {
        startService(new Intent(this, hep_closeAppService.class)); // 앱 종료 이벤트
        rView = findViewById(R.id.include_recyclerView);
        fView = findViewById(R.id.frameLayout);   // frameLayout 위에 recyclerView가 나타나야함으로 frameLayout 선언
        allSeeView = findViewById(R.id.recy_allSee);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        arrayList = new ArrayList<>();  // 객체 담아서 adapter로 보낼 arraylist
        arrayKey = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        AreaSearch areaSearch = new AreaSearch(); // 키자마자 한번 지오코딩 돌리는거
        areaSearch.Geocoding("신당동 164");

        OnItemClickListener = this;
        BtnMain = findViewById(R.id.btnMain);
        BtnList = findViewById(R.id.btnLocationList);
    }

    View mView;
    public static FragmentManager fragmentManager;
    public static Fragment mapFragment = null;
    public static Fragment LocationFragmet = null;
    KMS_FragmentFlagManager kms_fragmentFlagManager;
    //2.Bottomber
    public LinearLayout bottomBar;
    //3-1. Toolbar
    //우선 style.xml 액션바 제거
    Toolbar toolbar;
    Spinner spinner;
    KMS_RecycleVIewManager kms_recycleVIewManager = KMS_RecycleVIewManager.getInstanceRecycleView();
    //4. Toolbar Search
    LinearLayout linearLayoutToolbarSearch;
    ConstraintLayout recy_con_layout;
    KMS_SearchFlagManager kms_searchFlagManager = KMS_SearchFlagManager.getInstanceSearch();
    KMS_MapOption kms_mapOption = KMS_MapOption.getInstanceMapOption();
    //5. Animation
    Animation animation;
    Animation animationH;
    KMS_ClearableEditText_LoadLocation_auto clearableEditText_loadLocation_auto;

    static InputMethodManager inputMethodManager; //키보드 설정 위한
    //리스트뷰
    private List<String> list;
    //7. HashTag
    public static LinearLayout hastagView;
    //    CheckBox checkBoxAllHashTag; //체크박스 명 선언
    public static KMS_HashTag[] msHashTag; //태그 배열
    public static KMS_FlowLayout.LayoutParams params = new KMS_FlowLayout.LayoutParams(20, 20);

    //8. FloatingIcon
    public static FloatingActionButton floatingButton;
    KMS_LocationFlagManager kms_locationFlagManager = KMS_LocationFlagManager.getInstanceLocation();
    //9. Location Layout
    public static RelativeLayout relativelayout_sub;  // SelectLocation 단의 리니어 레이아웃
    LinearLayout linearLayout_selectLocation; // SelectLocation 단의 리니어 레이아웃
    KMS_SelectLocation selectLocation = new KMS_SelectLocation();
    boolean selectLocationFlag = false;     //장소추가 플래그
    boolean intentAddLocationFlag = false;  //장소 추가 인탠트 플래그
    //10. BackPressed
    KMS_BackPressedForFinish backPressedForFinish; //백프레스 클래스
    public static KSH_LoadLocation ksh_loadLocation = new KSH_LoadLocation();
    public static RelativeLayout relativeLayout_load;

    // . Context 넘겨주기
    public static Context mainContext; //AddMainActivity 에 넘겨주기 위해 컨텍스트 생성
    public static final int ADD_MAIN_ACTIVITY_REQUEST_CODE = 1000;
    public static final int ADD_MAIN_ACTIVITY_REPLY_CODE = 2000;
    public static final int ALLSEE_ACTIVITY_REQUEST_CODE = 3000;
    public static final int ALLSEE_ACTIVITY_REPLY_CODE = 4000;

    private ArrayList<KMS_LocationSearchResult> mArrayList;
    public static int count = -1;

    public static int selectView = 1;
    RecyclerView mRecyclerView;
    public static LinearLayoutManager mLinearLayoutManager;
    public static ArrayList<KMS_LocationSearchResult> kms_locationSearchResults = new ArrayList<>();
    KMS_LocationSearchResult kms_locationSearchResult;
    AutoCompleteTextView editText;
    RecyclerView searchRecyclerView;
    Button btnClear;

    public static RecyclerView loadRecyclerView;
    public static ArrayList<String> test_1 = new ArrayList<>();

    KMS_MarkerInformationFlagManager kms_markerInformationFlagManager = KMS_MarkerInformationFlagManager.getMarkerInformationFlagManagerInstance();

    public static ArrayList<hep_Location> autoCompleteLocationList;

    KMS_SearchBarManager kms_searchBarManager = new KMS_SearchBarManager();
    KMS_MarkerManager kms_markerManager = new KMS_MarkerManager().getInstanceMarkerManager();

    RelativeLayout searchResultBar;

    public void kms_init() {
        autoCompleteLocationList = new ArrayList<>();
        kms_fragmentFlagManager = KMS_FragmentFlagManager.getInstanceFragment();
        bottomBar = findViewById(R.id.linearBottombar);   //2. BottomBar
        toolbar = findViewById(R.id.toolbar);  //3. Toolbar
        setSupportActionBar(toolbar);  // **NoActionBar 해주고 이 메서드 호출하면 toolbar를 Activity의 앱바로 사용가능
        navigationView.setNavigationItemSelectedListener(this); // drawer
        setNavUserData(); // nav 유저 정보 (사진, 이름, 이메일 세팅)


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        spinner = findViewById(R.id.spinner);   //3-2. spinner 선언 & RecycleView
        linearLayoutToolbarSearch = findViewById(R.id.linearLayoutToolbarSearch);  //4. Toolbar Search
        clearableEditText_loadLocation_auto = findViewById(R.id.searchView); //프로젝트 단위 //6. 자동완성 텍스트 뷰
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //키보드-시스템서비스

        hastagView = findViewById(R.id.HasTagView); //7. HashTag
        hastagView.setBackgroundResource(R.drawable.hashtag);


        floatingButton = findViewById(R.id.floatingActionButton);  // 8.floating icon
        mainContext = this; //9. Location Layout
        relativelayout_sub = findViewById(R.id.relativeLayout_s);
        relativeLayout_load = findViewById(R.id.relativeLayout_loadLoaction);
        linearLayout_selectLocation = findViewById(R.id.linearLayout_s);
        backPressedForFinish = new KMS_BackPressedForFinish(this);  //10.BackPressed
        mRecyclerView = (RecyclerView) findViewById(R.id.searchResult_RecyclerVIew);
        mLinearLayoutManager = new LinearLayoutManager(this);
        editText = findViewById(R.id.clearable_edit_search_location);
        searchRecyclerView = findViewById(R.id.searchResult_RecyclerVIew);
        btnClear = (Button) findViewById(R.id.clearable_search_location_button_clear);

        loadRecyclerView = findViewById(R.id.searchLordResult_RecyclerVIew);

        kms_searchFlagManager.flagSetFalseSearch();
        setSearchBar(kms_searchFlagManager.flagGetSearch());
        relativeLayoutRoadLoaction = findViewById(R.id.relativeLayout_loadLoaction);
        kms_searchBarManager.setOffLoadLocationSearchBar(relativeLayoutRoadLoaction);

        test_1.add("오마이걸");
        test_1.add("여자아이들");
        test_1.add("러블리즈");
        test_1.add("우주소녀");
        test_1.add("레드벨벳");

        searchResultBar = findViewById(R.id.search_resultBar);

    }

    private void setNavUserData() {

        final View header = navigationView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.navUserName)).setText(new hep_FirebaseUser().getFirebaseUserInstance().getDisplayName());
        ((TextView) header.findViewById(R.id.navUserEmail)).setText(new hep_FirebaseUser().getFirebaseUserInstance().getEmail());
        final Handler handler = new Handler();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    //Url의 이미지를 bitmap으로 변환
                    URL url = new URL(new hep_FirebaseUser().getFirebaseUserInstance().getPhotoUrl().toString());
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            CJH_RoundedImageView cjh_roundedImageView = new CJH_RoundedImageView(KMS_MainActivity.this);
                            ((ImageView) header.findViewById(R.id.usr_img)).setImageBitmap(cjh_roundedImageView.getCroppedBitmap(bm, 1000));
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
        t.start();
    }

    // https://stackoverflow.com/questions/21192386/android-fragment-onclick-button-method
    //2.Bottomber
    //하단 바
    public void onBottomBarClicked(View v) { //Change Fragment
        switch (v.getId()) {
            case R.id.btnMain:
                kms_fragmentFlagManager.flagSetTrueFragment(); //프레그먼트 플래그 true 로 변경
                kms_fragmentFlagManager.setFragmentMapLayout();
                BtnMain.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ksh_button_design));
                BtnList.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ksh_button_not_design));
                break;
            case R.id.btnLocationList:
                kms_fragmentFlagManager.flagSetFalseFragment(); //프래그먼트 플래그 false 로 변경
                kms_fragmentFlagManager.setFragmentLocationListLayout();
                BtnList.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ksh_button_design));
                BtnMain.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ksh_button_not_design));
                break;
            default:
                break;
        }
    }

    public void setBottomBar(LinearLayout bottomBar, boolean Flag) { //searchFlag 에 맞게 하단 바 가리기
        if (Flag == true) {
            bottomBar.setVisibility(View.GONE);
        } else {
            bottomBar.setVisibility(View.VISIBLE);
        }
    }

    // Activity가 시작될 때 호출되는 함수 -> MenuItem 생성과 초기화 진행
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
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
    public void setSpinner() {
        if (kms_recycleVIewManager.flagCheckRecycleView() == true) { //리사이클 flag 가 true일 경우
            Animation animationH = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translatehide);
            rView.setAnimation(animationH);
            rView.setVisibility(fView.GONE);
            kms_recycleVIewManager.flagSetFalseRecycleView(); //리사이클 flag 를 false 로 변경
        } else if (kms_recycleVIewManager.flagCheckRecycleView() == false) { //리사이클 flag 가 false일 경우
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
            rView.setAnimation(animation);
            rView.setVisibility(fView.VISIBLE);
            kms_recycleVIewManager.flagSetTrueRecycleView(); //리사이클 flag를 true로 변경
        }
    }

    public void hideRecyclerView() { //리사이클 플래그가 false 이면 - 리사이클러 뷰가 안보이면 실행해준다. true 로 바꾼다.
        if (kms_recycleVIewManager.flagCheckRecycleView() == true) { //호출하였을 때 리사이클이 떠있을 경우에만 실행한다. 안떠있을 때 재실행 방지.
            animationH = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translatehide);
            recy_con_layout.setAnimation(animationH);
            recy_con_layout.setVisibility(mView.GONE);
            kms_recycleVIewManager.flagSetFalseRecycleView();
        }
    }

    // 여기
    //상단 툴바 클릭 이벤트
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search: {//상단 검색 버튼 클릭 시


                if (kms_recycleVIewManager.flagCheckRecycleView() == true) { // 만약 리사이클뷰 열려있으면 닫아준다.
                    setSpinner(); //이걸로 제어
                    hideRecyclerView(); //일단 디렉토리 열려있으면 삭제
                }//임시
                Toast.makeText(getApplicationContext(), "검색할 장소를 입력하세요.", Toast.LENGTH_LONG).show();

                //툴바 제거
                if (getSupportActionBar().isShowing()) {
                    kms_searchFlagManager.flagSetTrueSearch();
                    getSupportActionBar().hide();
                    clearableEditText_loadLocation_auto.requestFocus();
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    setBottomBar(bottomBar, kms_searchFlagManager.flagGetSearch());
                    setSearchBar(kms_searchFlagManager.flagGetSearch());
                    setFloatingItem(kms_searchFlagManager.flagGetSearch());
                    kms_searchBarManager.setOnLoadLocationSearchBar(relativeLayoutRoadLoaction);
                }
                return true;
            } //검색 버튼 종료
            case R.id.menu_tag_filter: {
                Toast.makeText(getApplicationContext(), "현재 준비중입니다.", Toast.LENGTH_LONG).show();
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

    // 확인을 눌렀을 때 눌린 태그들의 id값을 가져온다.
    public void onHashTagFilterButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.btnFilterSelect:
                kms_hashTagCheckBoxManager.AddClickHashTag(this);
                break;
            case R.id.btnFilterCancel:
                hideHashTagFilter();
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

                    CameraPosition cameraPosition = NMap.getCameraPosition(); //현재 위치 정보 반환하는 메소드
                    ReverseGeocodingAsyncTask asyncTask = new ReverseGeocodingAsyncTask(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    ReverseGetAddress reverseGetAddress = new ReverseGetAddress();
                    try {
                        String resultAddr = reverseGetAddress.getJsonString(asyncTask.execute().get());
                        ((TextView) findViewById(R.id.selectLocation_AddressInfo)).setText(resultAddr);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


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


    public void IntentAddLocation() {
        Intent intent = new Intent(KMS_MainActivity.this, hep_LocationSaveActivity.class);
        CameraPosition cameraPosition = NMap.getCameraPosition();
        if (kms_fragmentFlagManager.flagCheckFragment() == true) {
            intent.putExtra("latitude", cameraPosition.target.latitude);
            intent.putExtra("longitude", cameraPosition.target.longitude);
            intent.putExtra("addr", ((TextView) findViewById(R.id.selectLocation_AddressInfo)).getText());
        }
        startActivityForResult(intent, ADD_MAIN_ACTIVITY_REQUEST_CODE);
        intentAddLocationFlag = true;
    }

    public void hideAddLocation() { //장소 숨기는 함수
        kms_locationFlagManager.flagSetFalseLocation();
        getSupportActionBar().show();
        selectLocation.SetLinearLayout(getApplicationContext(), relativelayout_sub);
        setBottomBar(bottomBar, selectLocationFlag);
        setFloatingItem(selectLocationFlag);

        editText.setText(null);
        searchResultBar.setVisibility(GONE);
        mRecyclerView.setVisibility(View.GONE);
        kms_markerManager.initRecyclerMarker(); //임시 마커 초기화
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (kms_searchFlagManager.flagGetSearch() == true) {
            getSupportActionBar().show();
            kms_searchFlagManager.flagSetFalseSearch();
            setBottomBar(bottomBar, kms_searchFlagManager.flagGetSearch());
            setSearchBar(kms_searchFlagManager.flagGetSearch());
            setFloatingItem(kms_searchFlagManager.flagGetSearch());
            kms_searchBarManager.setOffLoadLocationSearchBar(relativeLayoutRoadLoaction);
            editText_1.setText("");
        } else if (kms_markerInformationFlagManager.flagGetMarkerInformationFlag() == false && kms_searchFlagManager.flagGetSearch() == false && kms_recycleVIewManager.flagCheckRecycleView() == false
                && kms_locationFlagManager.flagGetLocation() == false && kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag() == false) {
            backPressedForFinish.onBackPressed();
            //서치상태 아닐때만 종료 가능
        } else { //드로워블도 없고 종료도 아니면 실행
            if (kms_locationFlagManager.flagGetLocation() == true) {
                hideAddLocation();
            } else if (kms_markerInformationFlagManager.flagGetMarkerInformationFlag() == true && kms_fragmentFlagManager.flagCheckFragment() == true) {
                new KMS_MarkerManager().getInstanceMarkerManager().setOffMarkerInformation(KMS_MainActivity.linearLayoutMakerInformation);
                kms_markerInformationFlagManager.flagSetFalseMarkerInformation();
                floatingButton.show();
            } else if (intentAddLocationFlag == true && kms_fragmentFlagManager.flagCheckFragment() == true) { // 인텐트 상태이면서 맵에서 넘어왔을 경우
                intentAddLocationFlag = false; //인텐트 플래그 트루면 폴스로 바꿔줌
            } else if (intentAddLocationFlag == true && kms_fragmentFlagManager.flagCheckFragment() == false) { //인텐트 상태이면서 리스트에서 넘어왔을 경우
                intentAddLocationFlag = false; //인텐트 플래그 트루면 폴스로 바꿔줌
            } else if (kms_searchFlagManager.flagGetSearch() == true) { //검색 트루였으면 액션바 다시 보여주고 false 로 바꿈
                getSupportActionBar().show();
                kms_searchFlagManager.flagSetFalseSearch();
                //하단 바 및 아이콘 다시 원상복구시킴
                setSearchBar(kms_searchFlagManager.flagGetSearch());
                setBottomBar(bottomBar, kms_searchFlagManager.flagGetSearch());
                setFloatingItem(kms_searchFlagManager.flagGetSearch());
            } else if (kms_recycleVIewManager.flagCheckRecycleView() == true) { //스피너 떠있으면 꺼준다.
                setSpinner();
                setFloatingItem(kms_recycleVIewManager.flagCheckRecycleView());
            } else if (kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag() == true) {
                hideHashTagFilter();
                setFloatingItem(kms_hashTagCheckBoxFlagManager.flagGethashTagCheckBoxFlag());
            }
        } //드로워블도 없고 종료도 아니면 실행
    }

    // 어플키면 먼저 map 보여주기위한 fragmentManager.begin 코드 들어가야함 !
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_notice:
                Intent noticeIntent = new Intent(this, KSH_NoticeIntent.class);
                startActivity(noticeIntent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.nav_set:
                Intent setIntent = new Intent(this, KSH_SetIntent.class);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.nav_help:
                Intent helpIntent = new Intent(this, KSH_HelpIntent.class);
                startActivity(helpIntent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LoadRecyclerView() {
        InitRecyclerView();
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        KMS_SearchResultAdapter mAdapter = new KMS_SearchResultAdapter(kms_locationSearchResults);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(searchRecyclerView.getContext(), 1));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void InitRecyclerView() {
        KMS_SearchResultAdapter.LastPosition = -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kms_activity_main);
        networkStatus();

        ksh_init();
        pcs_hashTagInit();
        kms_init();

        Query directoryQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory").orderByChild("token").equalTo(new hep_FirebaseUser().getFirebaseUserInstance().getUid());
        directoryQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                arrayKey.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ksh_directoryEntity = dataSnapshot.getValue(KSH_DirectoryEntity.class); // 만들어둔 Test 객체에 데이터를 담는다
                    String key = dataSnapshot.getKey();
                    arrayList.add(ksh_directoryEntity);  // 담은 데이터들을 arraylist에 넣고 recyclerview로 보낼 준비
                    arrayKey.add(key);
                }
                if (recyAdapter != null)
                    recyAdapter.notifyDataSetChanged(); // list 저장 및 새로고침

                new hep_FireBase().getRecentData(new hep_Callback() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }

                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot1) {
                    }

                    @Override
                    public void onSuccess(hep_LocationTag hep_locationTag) {
                    }

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

                        if (LastPosition != -1) {
                            toolbar.setTitle(arrayList.get(LastPosition - 1).getName());
                        } else {
                            toolbar.setTitle(arrayList.get(selectView - 1).getName());
                        }

                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location").orderByChild("directoryid").equalTo(directoryid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                autoCompleteLocationList.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    hep_Location hep_location = dataSnapshot.getValue(hep_Location.class);
                                    autoCompleteLocationList.add(hep_location);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        recyAdapter = new KSH_RecyAdapter(KMS_MainActivity.this, arrayList, arrayKey, ksh_directoryEntity, selectView, (sunghunTest) OnItemClickListener);
                        recyclerView.setAdapter(recyAdapter);

                        kms_mapOption.LastLatitude = hep_recent.latitude;
                        kms_mapOption.LastLongitued = hep_recent.longitude;
                        mapFragment = new KMS_MapFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frameLayout, mapFragment).commitAllowingStateLoss();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // loading
        Intent intent = new Intent(this, KSH_LoadingActivity.class);
        startActivity(intent);
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
                intent.putExtra("array", arrayList);
                intent.putExtra("key", arrayKey);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // 여기여기
        searchResultBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchRecyclerView.getVisibility() == VISIBLE) {
                    searchRecyclerView.setVisibility(GONE);
                } else {
                    searchRecyclerView.setVisibility(VISIBLE);
                }
            }
        });
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //키보드-시스템서비스
        list = new ArrayList<String>();

        linearLayout_selectLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        IntentAddLocation();      //추후 수정해야함

                        if (kms_fragmentFlagManager.flagCheckFragment() == true)
                            //hideAddLocation();
                            return true;
                }
                return false;
            }
        });


        Button button = findViewById(R.id.markerinfoclosebtn);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.linearLayoutMakerInformation)).setVisibility(GONE);
                floatingButton.show();
            }
        });

        // editText 클릭 포커스 맞춰졌을때
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (searchRecyclerView.getVisibility() == View.VISIBLE) {

                }
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                kms_locationSearchResults.clear();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 공백이면
                    if (editText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "공백입니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (searchRecyclerView.getVisibility() == View.VISIBLE) {
                        searchRecyclerView.setVisibility(View.GONE);
                    }
                    //공백 아닐 경우
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    //재 검색 시 초기화
                    kms_markerManager.initRecyclerMarker();

                    AreaSearch areaSearch = new AreaSearch();
                    ArrayList<SearchAreaArrayEntity> searchAreaArrayResult = areaSearch.SearchArea(editText.getText().toString());
                    ArrayList<GeocodingArrayEntity> geocodingArrayResult = areaSearch.Geocoding(editText.getText().toString());

                    if (searchAreaArrayResult.size() == 0 && geocodingArrayResult.size() == 0) {
                        Toast.makeText(getApplicationContext(), "검색결과가 없습니다", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    // ex) 신당동 164
                    else if (searchAreaArrayResult.size() == 0) {
                        for (int i = 0; i < geocodingArrayResult.size(); i++) {
                            kms_locationSearchResult = new KMS_LocationSearchResult(editText.getText().toString(), geocodingArrayResult.get(i).getRoadAddress()
                                    , geocodingArrayResult.get(i).getLongitude(), geocodingArrayResult.get(i).getLatitude());
                            kms_locationSearchResults.add(kms_locationSearchResult);
                        }
                    }
                    // ex) 계명대학교
                    //교대요
                    else {
                        ArrayList<GeocodingArrayEntity> temp;
                        for (int i = 0; i < searchAreaArrayResult.size(); i++) {
                            temp = areaSearch.Geocoding(searchAreaArrayResult.get(i).getAddress());
                            if (searchAreaArrayResult.get(i).getRoadAddress().equals("")) {
                                kms_locationSearchResult = new KMS_LocationSearchResult(searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                        , searchAreaArrayResult.get(i).getAddress(), temp.get(0).getLongitude(), temp.get(0).getLatitude());
                                kms_locationSearchResults.add(kms_locationSearchResult);
                            } else {
                                kms_locationSearchResult = new KMS_LocationSearchResult(searchAreaArrayResult.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                                        , searchAreaArrayResult.get(i).getRoadAddress(), temp.get(0).getLongitude(), temp.get(0).getLatitude());
                                kms_locationSearchResults.add(kms_locationSearchResult);
                            }
                            temp.clear();
                        }
                    }
                    kms_markerManager.AddRecyclerViewMarker(NMap);
                    selectLocation.setSearchResultRecyclerView(getApplicationContext(), searchRecyclerView, searchResultBar);
                    LoadRecyclerView(); //기존 저장 함수 불러옴
                    return true;
                } //키입력 했을 시 종료
                return false;
            } //key 입력 이벤트 종료
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        textViewMarkerInformationTitle = findViewById(R.id.titleTextView);
        linearLayoutMakerInformation = findViewById(R.id.linearLayoutMakerInformation);
        linearLayoutMakerInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //마커인포 클릭
            }
        });
    }

    private void pcs_hashTagInit() {
        View v = drawerLayout;
        kms_hashTagCheckBoxManager = new KMS_HashTagCheckBoxManager(this, v);
        kms_hashTagCheckBoxManager.initHashTag(new Pcs_HashTagCallback() {
            @Override
            public void onSuccess(KMS_HashTag[] kms_hashTags) {
                msHashTag = kms_hashTags;
            }
        });
        kms_hashTagCheckBoxManager.checkAllHashTag();
        kms_hashTagCheckBoxFlagManager = new KMS_HashTagCheckBoxFlagManager(msHashTag);
    }

    public static LinearLayout linearLayoutMakerInformation;
    public static TextView textViewMarkerInformationTitle;
    public static RelativeLayout relativeLayoutRoadLoaction;

    //This method receive intent from closed activity
    //ADD_MAIN_ACTIVITY_REQUEST_CODE is
    //when LocationSaveActivity closed, Showing fragment of Location Recyclerview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_MAIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra("result", false)) {
                    KMS_FragmentFlagManager d = KMS_FragmentFlagManager.getInstanceFragment();
                    d.setFragmentLocationListLayout();
                    if (kms_fragmentFlagManager.flagCheckFragment() == true){
                        hideAddLocation();

                    }
                    floatingButton.show();

                    BtnList.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ksh_button_design));
                    BtnMain.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ksh_button_not_design));
                }
            }
        }
    }
} //mainactivity 종료

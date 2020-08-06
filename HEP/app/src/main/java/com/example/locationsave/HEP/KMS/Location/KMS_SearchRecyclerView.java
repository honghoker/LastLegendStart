package com.example.locationsave.HEP.KMS.Location;

import androidx.appcompat.app.AppCompatActivity;

public class KMS_SearchRecyclerView extends AppCompatActivity {



/*
    public void AddRecyclerView(){
        count++;
        KMS_LocationSearchResult data = new KMS_LocationSearchResult(count + "1", "Apple" + count);
        //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
        mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
        mAdapter.notifyDataSetChanged();
    }

    public static void LoadRecyclerView(){
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

    public static void InitRecyclerView(){
        count = -1;
        KMS_MainActivity.mArrayList.clear();
        mAdapter.notifyDataSetChanged();
        KMS_SearchResultAdapter.LastPosition = -1;
    }

    public void SetSearchRecyclerView(){

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.searchResult_RecyclerVIew);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();

        mAdapter = new KMS_SearchResultAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        LoadRecyclerView(); //기존 저장 함수 불러옴
    }*/

}

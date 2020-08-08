package com.example.locationsave.HEP.Address;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AreaSearch {
    private ArrayList<SearchAreaArrayEntity> searchAreaArrayResult;
    private ArrayList<GeocodingArrayEntity> geocodingArrayResult;

    public AreaSearch() {

    }
    public ArrayList<SearchAreaArrayEntity> SearchArea(String area){
        // searchArea = ex) 계명대학교, 경북대학교 이렇게 단순히 건물 이름 검색했을 때 주소 list
        // 여기서 나오는 address, roadAddress 를 이용해서 다시 geocoding 돌리면
        SearchAreaAsyncTask searchAreaAsyncTask = new SearchAreaAsyncTask(area);
        SearchAreaGetAddress searchAreaGetAddress = new SearchAreaGetAddress();

        try {
            searchAreaArrayResult = searchAreaGetAddress.getJsonString(searchAreaAsyncTask.execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return searchAreaArrayResult;
    }

    public ArrayList<GeocodingArrayEntity> Geocoding(String address){
        // geocoding = ex) 신당동 164 이렇게 주소를 입력했을 때 관련된 상세주소 list
        GeocodingAsyncTask geocodingAsyncTask = new GeocodingAsyncTask(address);
        GeocodingGetAddress geocodingGetAddress = new GeocodingGetAddress();

        try {
            geocodingArrayResult = geocodingGetAddress.getJsonString(geocodingAsyncTask.execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return geocodingArrayResult;
    }
}

package com.example.locationsave.HEP.Address;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchAreaGetAddress {
    public ArrayList<SearchAreaArrayEntity> getJsonString(String jsonString){
        ArrayList<SearchAreaArrayEntity> result = new ArrayList<>();
        result.clear();
        SearchAreaEntity searchAreaEntity = new SearchAreaEntity();
        searchAreaEntity.setJson(jsonString);
        try{
            JSONObject jsonObject = new JSONObject(searchAreaEntity.getJson());
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for(int i=0; i<jsonArray.length();i++){
                JSONObject jObject = jsonArray.getJSONObject(i);
                searchAreaEntity.setTitle(jObject.getString("title"));
                searchAreaEntity.setAddress(jObject.getString("address"));
                searchAreaEntity.setRoadAddress(jObject.getString("roadAddress"));
//                Log.d("6","title " + searchAreaEntity.getTitle() + " address " + searchAreaEntity.getAddress() + " roadAddress " + searchAreaEntity.getRoadAddress());
                SearchAreaArrayEntity searchAreaArrayEntity = new SearchAreaArrayEntity(searchAreaEntity.getTitle(),searchAreaEntity.getAddress(),searchAreaEntity.getRoadAddress());
                result.add(searchAreaArrayEntity);
//                result.add(searchAreaEntity.getRoodAddress());
            }
            return result;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}


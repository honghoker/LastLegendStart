package com.example.locationsave.HEP.Address;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetAddress {
    public String getJsonString(String jsonString){
        GeocodingEntity geocodingEntity = new GeocodingEntity();
        geocodingEntity.setJson(jsonString);
        Log.d("6","22222222222" + geocodingEntity.getJson());
        try{
            String result = "";
            JSONObject jsonObject = new JSONObject(geocodingEntity.getJson());
            JSONArray jsonArray = jsonObject.getJSONArray("addresses");
            Log.d("6","333333");
            for(int i=0; i<jsonArray.length(); i++){
                Log.d("6","444444");
                JSONObject jObject = jsonArray.getJSONObject(i);

                geocodingEntity.setRoodAddress(jObject.getString("roadAddress"));
                Log.d("6","확인 " + geocodingEntity.getRoodAddress());

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "위치정보가 없습니다.";
    };
}

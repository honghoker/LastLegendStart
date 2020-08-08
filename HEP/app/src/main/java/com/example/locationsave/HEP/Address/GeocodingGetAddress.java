package com.example.locationsave.HEP.Address;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GeocodingGetAddress {
    public ArrayList<GeocodingArrayEntity> getJsonString(String jsonString){
        ArrayList<GeocodingArrayEntity> result = new ArrayList<>();
        result.clear();
        GeocodingEntity geocodingEntity = new GeocodingEntity();
        geocodingEntity.setJson(jsonString);
//        Log.d("6","22222222222" + geocodingEntity.getJson());
        try{
            JSONObject jsonObject = new JSONObject(geocodingEntity.getJson());
            JSONArray jsonArray = jsonObject.getJSONArray("addresses");
//            Log.d("6","333333");
            for(int i=0; i<jsonArray.length(); i++){
//                Log.d("6","444444");
                JSONObject jObject = jsonArray.getJSONObject(i);
                geocodingEntity.setJibunAddress(jObject.getString("jibunAddress"));
                geocodingEntity.setRoodAddress(jObject.getString("roadAddress"));
                geocodingEntity.setLatitude(jObject.getString("x"));
                geocodingEntity.setLongitude(jObject.getString("y"));

                GeocodingArrayEntity geocodingArrayEntity = new GeocodingArrayEntity(geocodingEntity.getJibunAddress(),geocodingEntity.getRoodAddress(),geocodingEntity.getLatitude(),geocodingEntity.getLongitude());
                result.add(geocodingArrayEntity);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

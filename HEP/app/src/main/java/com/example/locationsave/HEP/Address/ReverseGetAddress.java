package com.example.locationsave.HEP.Address;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReverseGetAddress {

    public String getJsonString(String jsonString){
        ReverseGeocodingEntity reverseGeocodingEntity = new ReverseGeocodingEntity();

        reverseGeocodingEntity.setJson(jsonString);
        try {
            String result = "";
            JSONObject jsonObject = new JSONObject(reverseGeocodingEntity.getJson());
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jObject = jsonArray.getJSONObject(i);
                JSONObject jRegion = jObject.getJSONObject("region");
                JSONObject area1 = jRegion.getJSONObject("area1");
                JSONObject area2 = jRegion.getJSONObject("area2");
                JSONObject area3 = jRegion.getJSONObject("area3");
                JSONObject area4 = jRegion.getJSONObject("area4");

                JSONObject jRegion1 = jObject.getJSONObject("land");
                JSONObject addition0 = jRegion1.getJSONObject("addition0");
                JSONObject addition1 = jRegion1.getJSONObject("addition1");
                JSONObject addition2 = jRegion1.getJSONObject("addition2");
                JSONObject addition3 = jRegion1.getJSONObject("addition3");
                JSONObject addition4 = jRegion1.getJSONObject("addition4");

                reverseGeocodingEntity.setArea1_name(area1.getString("name"));
                reverseGeocodingEntity.setArea2_name(area2.getString("name"));
                reverseGeocodingEntity.setArea3_name(area3.getString("name"));
                reverseGeocodingEntity.setArea4_name(area4.getString("name"));

                reverseGeocodingEntity.setAddition0_value(addition0.getString("value"));
                reverseGeocodingEntity.setAddition1_value(addition1.getString("value"));
                reverseGeocodingEntity.setAddition2_value(addition2.getString("value"));
                reverseGeocodingEntity.setAddition3_value(addition3.getString("value"));
                reverseGeocodingEntity.setAddition4_value(addition4.getString("value"));
                reverseGeocodingEntity.setOther_number1( jRegion1.getString("number1"));
                reverseGeocodingEntity.setOther_number2( jRegion1.getString("number2"));

                // 지번
                if(i == 0){
                    if(reverseGeocodingEntity.getOther_number2().trim().equals("")){
                        result = stringNullCheck(reverseGeocodingEntity.getArea1_name())+" "+stringNullCheck(reverseGeocodingEntity.getArea2_name())+" "+stringNullCheck(reverseGeocodingEntity.getArea3_name())
                                +" "+stringNullCheck(reverseGeocodingEntity.getArea4_name()) + stringNullCheck(reverseGeocodingEntity.getOther_number1());
                    }
                    else{
                        result = stringNullCheck(reverseGeocodingEntity.getArea1_name())+" "+stringNullCheck(reverseGeocodingEntity.getArea2_name())+" "+stringNullCheck(reverseGeocodingEntity.getArea3_name())
                                +" "+stringNullCheck(reverseGeocodingEntity.getArea4_name()) + stringNullCheck(reverseGeocodingEntity.getOther_number1()) + "-" +stringNullCheck(reverseGeocodingEntity.getOther_number2());
                    }
                }
                // 도로명
                else{
                    Log.d("5",i + " = "+stringNullCheck(reverseGeocodingEntity.getOther_number1()) + " " + stringNullCheck(reverseGeocodingEntity.getOther_number2()));
                    reverseGeocodingEntity.setOther_name( jRegion1.getString("name"));

                    if(reverseGeocodingEntity.getOther_number2().trim().equals(""))
                        result += "\n(" + stringNullCheck(reverseGeocodingEntity.getOther_name())+" "+stringNullCheck(reverseGeocodingEntity.getOther_number1()) +")";
                    else
                        result += "\n(" + stringNullCheck(reverseGeocodingEntity.getOther_name())+" "+stringNullCheck(reverseGeocodingEntity.getOther_number1())
                                +"-"+stringNullCheck(reverseGeocodingEntity.getOther_number2()) + ")";
                }

            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "위치정보가 없습니다.";
    }

    public String stringNullCheck(String s){
        if(s == null)
            s = "";
        return s;
    }
}

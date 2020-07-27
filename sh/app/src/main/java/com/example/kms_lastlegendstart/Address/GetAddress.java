package com.example.kms_lastlegendstart.Address;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetAddress {
    public void getJsonString(String jsonString){
        AddressEntity addressEntity = new AddressEntity();

        addressEntity.setJson(jsonString);

        try {
            JSONObject jsonObject = new JSONObject(addressEntity.getJson());
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jObject = jsonArray.getJSONObject(i);
                JSONObject jRegion = jObject.getJSONObject("region");
                JSONObject area1 = jRegion.getJSONObject("area1");
                JSONObject area2 = jRegion.getJSONObject("area2");
                JSONObject area3 = jRegion.getJSONObject("area3");

                JSONObject jRegion1 = jObject.getJSONObject("land");
//
                JSONObject addition0 = jRegion1.getJSONObject("addition0");
                JSONObject addition1 = jRegion1.getJSONObject("addition1");
                JSONObject addition2 = jRegion1.getJSONObject("addition2");
                JSONObject addition3 = jRegion1.getJSONObject("addition3");
                JSONObject addition4 = jRegion1.getJSONObject("addition4");
//
                addressEntity.setArea1_name(area1.getString("name"));
                addressEntity.setArea2_name(area2.getString("name"));
                addressEntity.setArea3_name(area3.getString("name"));
                Log.d("5","full name = " + addressEntity.getArea1_name()+" "+addressEntity.getArea2_name()+" "+addressEntity.getArea3_name());
//
                addressEntity.setAddition0_value(addition0.getString("value"));
                addressEntity.setAddition1_value(addition1.getString("value"));
                addressEntity.setAddition2_value(addition2.getString("value"));
                addressEntity.setAddition3_value(addition3.getString("value"));
                addressEntity.setAddition4_value(addition4.getString("value"));
                addressEntity.setOther_name( jRegion1.getString("name"));
                addressEntity.setOther_number1( jRegion1.getString("number1"));
                addressEntity.setOther_number2( jRegion1.getString("number2"));
                Log.d("5","full addition = " + addressEntity.getOther_name()+" "+addressEntity.getOther_number1()
                        +"-"+addressEntity.getOther_number2()+ " "+addressEntity.getAddition0_value()+ " " + addressEntity.getAddition1_value() + "-" +  addressEntity.getAddition2_value()
                        + " " + addressEntity.getAddition3_value()+" "+addressEntity.getAddition4_value());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

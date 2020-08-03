package com.example.locationsave.HEP.Address;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeocodingAsyncTask extends AsyncTask<Void,Void,String> {
    String clientId = "03gjuk2ph9";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "kcPZZM7ikGyaHL4uJGav6IMequOkarvHIB3Sta8D";//애플리케이션 클라이언트 시크릿값";
    String simpleAddress;

    public GeocodingAsyncTask(String simpleAddress) {
        this.simpleAddress = simpleAddress;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+simpleAddress;
//            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=연사리 93";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
//            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();

        } catch (Exception e) {
            Log.d("5", "에러요"+String.valueOf(e));
        }
        return null;
    }
}

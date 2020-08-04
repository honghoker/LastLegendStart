package com.example.locationsave.HEP.Address;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchAreaAsyncTask extends AsyncTask<Void,Void,String> {
    String clientId = "iXMNvmvSAOguc7tlSbcQ"; //애플리케이션 클라이언트 아이디값";
    String clientSecret = "n0VvnqlG_k";//애플리케이션 클라이언트 시크릿값";
    String simpleAddress;

    public SearchAreaAsyncTask(String simpleAddress) {
        this.simpleAddress = simpleAddress;
    }


    @Override
    protected String doInBackground(Void... voids) {
        try{
            String apiURL = "https://openapi.naver.com/v1/search/local.json?query="+simpleAddress+"&display=10&start=1&sort=random";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
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

        }catch (Exception e){
            Log.d("5", "에러요"+String.valueOf(e));
        }

        return null;
    }
}

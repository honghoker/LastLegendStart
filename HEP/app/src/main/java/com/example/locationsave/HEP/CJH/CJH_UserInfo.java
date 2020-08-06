package com.example.locationsave.HEP.CJH;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.locationsave.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.io.InputStream;
import java.net.URL;

public class CJH_UserInfo extends Application {
    public static FirebaseAuth mAuth;
    public static FirebaseUser user;

    //유저정보 갱신
    public static void reflash(){
        try{
            mAuth= FirebaseAuth.getInstance();
            user= mAuth.getCurrentUser();
        }catch (Exception e){
            Log.d("CJH_UserInfo_Error",e.toString());
        }
    }
    //파이어베이스 UID받기
    public static String getUid(){
        try{
            return user.getUid();
        }catch (Exception e){
            Log.d("CJH_UserInfo_getUid_Error",e.toString());
            return "";
        }
    }
    //이름 받기
    public static String getName(){
        try{
            if (user.getDisplayName()==null)
                return "null";
            return user.getDisplayName();
        }catch (Exception e){
            Log.d("CJH_UserInfo_getName_Error",e.toString());
            return "";
        }
    }

    //이메일 받기
    public static String getEmail(){
        try{
            if (user.getEmail()==null)
                return "null";
            return user.getEmail();
        }catch (Exception e){
            Log.d("CJH_UserInfo_getEmail_Error",e.toString());
            return "";
        }
    }

    //폰번호 받기
    public static String getPhoneNumber(){
        try{
            if(user.getPhoneNumber()==null)
                return "null";
            return user.getPhoneNumber();
        }catch (Exception e){
            Log.d("CJH_UserInfo_getEmail_Error",e.toString());
            return "";
        }
    }

    //유저 프로필사진 세팅
    public static void setPhoto(final ImageView iv, final Context context){
        final Handler handler = new Handler();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try{
                    //Url의 이미지를 bitmap으로 변환
                    URL url = new URL(user.getPhotoUrl().toString());
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            //RoundedImageView roundedImageView=new RoundedImageView(context);
                            //iv.setImageBitmap(roundedImageView.getCroppedBitmap(bm,1000));
                        }
                    });
                    iv.setImageBitmap(bm); //비트맵 객체로 보여주기
                } catch(Exception e){

                }
            }
        });
        t.start();
    }


    //로그아웃
    public static void logOut(Context context){
        //파이어베이스 로그아웃
        mAuth.signOut();
        //구글 로그아웃
        new CJH_SignInGoogle(context,context.getString(R.string.default_web_client_id)).signOutGoogle();
        //페이스북 로그아웃
        new CJH_SignInFacebook().signOutFacebook();

        //로그인 화면으로 되돌아감
        Intent intent = new Intent(context.getApplicationContext(), SignIn.class);
        context.startActivity(intent);
    }

    //로그인한 타입받기
    public static String getLoginType(){
        String type="";

        if(user!=null){
            for (UserInfo user: CJH_UserInfo.user.getProviderData()) {
                if (user.getProviderId().equals("facebook.com")) {
                    type="facebook";
                }else if(user.getProviderId().equals("google.com")){
                    type="google";
                }
            }
        }

        return type;
    }
    //앱 종료
    public static void exit(Activity context){
        context.moveTaskToBack(true);
        context.finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

package com.example.test1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import java.io.IOException;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;
    private Button btn_custom_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*FirebaseApp.initializeApp();
        String uid = "some-uid";
        String customToken = FirebaseAuth.getInstance().createCustomToken(uid);*/

       /* mContext = getApplicationContext();
        getHashKey(mContext);*/

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setServiceAccountId("my-client-id@my-project-id.iam.gserviceaccount.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirebaseApp.initializeApp(options);

        String uid = "some-uid";

        try {
            String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
// Send token back to client

        mContext = getApplicationContext();

        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        btn_custom_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_custom_login){
            Session session = Session.getCurrentSession();
            session.addCallback(new SessionCallback());
            session.open(AuthType.KAKAO_LOGIN_ALL, this);
        }
    }

   /* // 프로젝트의 해시키를 반환
    @Nullable
    public static String getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }

    }*/
}
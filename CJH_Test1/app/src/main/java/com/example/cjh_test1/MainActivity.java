package com.example.cjh_test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1001;
    Button signInNaver,signInKakao,signInFaceBook;
    SignInButton signInGoogle;

    private FirebaseAuth mAuth=null;
    private GoogleSignInClient mGoogleSignInClient;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInGoogle=findViewById(R.id.signInGoogle);
        signInFaceBook=findViewById(R.id.signInFaceBook);
        signInKakao=findViewById(R.id.signInNaver);
        signInNaver=findViewById(R.id.signInKakao);

        TextView signInGoogleText=(TextView)signInGoogle.getChildAt(0);
        signInGoogleText.setText(R.string.login_google);

        signInGoogle.setOnClickListener(this);
        signInFaceBook.setOnClickListener(this);
        signInKakao.setOnClickListener(this);
        signInNaver.setOnClickListener(this);

        mContext=this;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    public GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    protected FirebaseAuth getmAuth() {
        return mAuth;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), CJH_Next.class);
            startActivity(intent);
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signInGoogle) {
            startActivity(new Intent(this,CJH_SignIn.class));
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Log.w("@@@@@@@@@@@@@@@@@@@@@@@@@@@","");
            }
        }
    }
}
package com.example.cjh_test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CJH_Next extends AppCompatActivity implements View.OnClickListener{
    TextView textView;
    Button buttonLogOut,buttonRevoke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_j_h__next);

        textView=findViewById(R.id.userID);
        buttonLogOut=findViewById(R.id.logOut);
        buttonRevoke=findViewById(R.id.revoke);

        buttonLogOut.setOnClickListener(this);
        buttonRevoke.setOnClickListener(this);

        //textView.setText(getString(R.string.google_status_fmt, user.getEmail()));
    }

    /*private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
    }
*/
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.logOut) {
            //signOut();
        } else if(i==R.id.revoke){
           // revokeAccess();
        }
    }
}
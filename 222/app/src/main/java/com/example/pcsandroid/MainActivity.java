package com.example.pcsandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    Pcs_LocationRecyclerView pcsFragment ;


    class BtnOnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.fragmentButton:
                    setFragment();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDeclaration();



    }
    private void setDeclaration(){
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        Button button = (Button) findViewById(R.id.fragmentButton);
        button.setOnClickListener(onClickListener);
        pcsFragment = new Pcs_LocationRecyclerView();
    }
    private void setFragment(){
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragmentContainer, pcsFragment).commit();
    }

}
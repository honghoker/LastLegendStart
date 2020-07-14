package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Pcs_LocationRecyclerView pcsFragment ;

    class BtnOnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button4:
                    Log.d("tag","Here");
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
        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(onClickListener);
        pcsFragment = new Pcs_LocationRecyclerView();
    }
    private void setFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, pcsFragment).commit();
    }

}

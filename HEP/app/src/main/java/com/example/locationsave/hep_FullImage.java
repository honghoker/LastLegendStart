package com.example.locationsave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class hep_FullImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_fullimage);

        ImageView imageView = findViewById(R.id.fullImage);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        imageView.setImageBitmap(bitmap);
    }

    public void onButtonCloseFullImageOnclicked(View v){
        finish();
    }
    /*
     * String형을 BitMap으로 변환시켜주는 함수
     * */
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public Bitmap resizeBitmapImg(Bitmap source, int maxWidth, int maxHeight){
        //resizeBitmapImg(bitmap, ((LinearLayout) findViewById(R.id.Layout_FullImage)).getWidth(), ((LinearLayout) findViewById(R.id.Layout_FullImage)).getHeight());
        int newWidth = maxWidth;
        int newHeight = maxHeight;

        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }
}
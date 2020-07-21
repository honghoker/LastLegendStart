package com.example.locationsave;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class hep_ImageData implements Parcelable {
    Bitmap bitmap;
    Uri path;

    public hep_ImageData(Bitmap bitmap, Uri path){
        this.bitmap = bitmap;
        this.path = path;
    }

    protected hep_ImageData(Parcel in) {
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<hep_ImageData> CREATOR = new Creator<hep_ImageData>() {
        @Override
        public hep_ImageData createFromParcel(Parcel in) {
            return new hep_ImageData(in);
        }

        @Override
        public hep_ImageData[] newArray(int size) {
            return new hep_ImageData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bitmap, flags);
    }
}
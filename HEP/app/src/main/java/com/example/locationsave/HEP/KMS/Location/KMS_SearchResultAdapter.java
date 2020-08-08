package com.example.locationsave.HEP.KMS.Location;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.R;

import java.util.ArrayList;

public class KMS_SearchResultAdapter extends RecyclerView.Adapter<KMS_SearchResultAdapter.CustomViewHolder> {

    private ArrayList<KMS_LocationSearchResult> mList;

/*
개별 선택 위한 코드
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0); //포지션 선택 저장 구조
    //출처: https://thepassion.tistory.com/301 [좋은향기's 프로그램 블로그]
*/

    public static int LastPosition = -1; //단일 선택 위한 변수

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView Title;
        protected TextView RoadAddress;

        public CustomViewHolder(View view) {
            super(view);
            this.Title = (TextView) view.findViewById(R.id.title_listitem);
            this.RoadAddress = (TextView) view.findViewById(R.id.roadAddress_listitem);
        }
    }

    public KMS_SearchResultAdapter(ArrayList<KMS_LocationSearchResult> list) {
        this.mList = list;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.d("View", "onCreateviewHolder 실행");

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.kms_search_result_list, viewGroup, false);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);
        /*
        RecyclerView의 Adapter는 전체 아이템의 개수 기반

        적정수( 한화면에 표시되는 아이템 개수 + 스크롤시 사용할 여분의 아이템 개수)의

        ViewHolder( View )를 미리 생성, 화면상에 표시되지 않는 View를 재사용하는 구조

        출처: https://thepassion.tistory.com/301 [좋은향기's 프로그램 블로그]
         */

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

                LastPosition = position; //마지막 선택 값 저장.
                notifyDataSetChanged(); //값 변경 확인함.

//개별 선택 위한 코드
/*

 Log.d("View", "viewHolder Event Position : " + position + " mSize : " + mList.size());
                    if (mSelectedItems.get(position, false)) { //흰색 안눌렀을때가 false 이고 이때 true 로 변경된다.
                        Log.d("View", "파랑 클릭? viewHolder Event Item bool false: " + mSelectedItems.get(position, false));
                        mSelectedItems.put(position, false);
                        v.setBackgroundColor(Color.WHITE);
                    }
                    else{
                        Log.d("View", "흰색 클릭? viewHolder Event Item bool true: " + mSelectedItems.get(position, true));
                        mSelectedItems.put(position, true);
                        v.setBackgroundColor(Color.BLUE);
                    }*/
            }
        }); //클릭 리스너 종료

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        //viewholder.Title.setText(mList.get(position).getTitle());
        Log.d("View", "viewHolder Bind" + position);
        //개별 선택 위한 코드
        if (position == LastPosition)
            viewholder.itemView.setBackgroundColor(Color.BLUE);
        else
            viewholder.itemView.setBackgroundColor(Color.WHITE);


/*
//개별 선택 위한 코드
        if (mSelectedItems.get(position, false)) {
            viewholder.itemView.setBackgroundColor(Color.BLUE);
        } else {
            viewholder.itemView.setBackgroundColor(Color.WHITE);
        }
*/


        viewholder.Title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        viewholder.RoadAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        viewholder.Title.setGravity(Gravity.CENTER);
        viewholder.RoadAddress.setGravity(Gravity.CENTER);

        viewholder.Title.setText(mList.get(position).getTitle());
        viewholder.RoadAddress.setText(mList.get(position).getRoadAddress());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    //수정
/*    public void setData(ArrayList<String> data) {
        mList = data;
        notifyDataSetChanged();
    }*/
/*
    public class StdViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;

        public StdViewHolder(@NonNull View itemView) {
            super(itemView);
            this.Title = itemView.findViewById(R.id.title_listitem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    toggleItemSelected(position);
                }
            });
        }

        private void toggleItemSelected(int position) {

            if (mSelectedItems.get(position, false) == true) {
                mSelectedItems.delete(position);
                notifyItemChanged(position);
            } else {
                mSelectedItems.put(position, true);
                notifyItemChanged(position);
            }
        }

        private boolean isItemSelected(int position) {
            return mSelectedItems.get(position, false);
        }

        public void clearSelectedItem() {
            int position;

            for (int i = 0; i < mSelectedItems.size(); i++) {
                position = mSelectedItems.keyAt(i);
                mSelectedItems.put(position, false);
                notifyItemChanged(position);
            }

            mSelectedItems.clear();
        }
        //수정


    }*/

}
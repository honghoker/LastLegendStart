package com.example.pcs.LocationRecyclerViewSwipeHelper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.security.ConfirmationNotAvailableException;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class Pcs_SwipeHelper extends ItemTouchHelper.SimpleCallback {

    int buttonWidth;
    private RecyclerView recyclerView;
    private List<MyButton> buttonList;
    private GestureDetector gestureDetector;
    private int swipePosition = -1;
    private float swipeThreahold = 0.5f;
    private Map<Integer,List<MyButton>> buttonBuffer;
    private Queue<Integer> removerQueue;


    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for(MyButton button:buttonList){
                if(button.onClick(e.getX(),e.getY()))
                    break;
            }
            return true;
        }
    };
    private View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(swipePosition < 0) return false;
            Point point = new Point((int) event.getRawX(), (int)event.getRawY());

            RecyclerView.ViewHolder swipeViewHolder = recyclerView.findViewHolderForAdapterPosition(swipePosition);
            View swipedItem = swipeViewHolder.itemView;
            Rect rect = new Rect();
            swipedItem.getGlobalVisibleRect(rect);

            if(event.getAction() == event.ACTION_DOWN ||
            event.getAction() == event.ACTION_UP ||
            event.getAction() == event.ACTION_MOVE){
                if(rect.top < point.y && rect.bottom > point.y)
                    gestureDetector.onTouchEvent(e);
                else{
                    removerQueue.add(swipePosition);
                    swipePosition = -1;
                    recoverSwipeItem();
                }
            }
            return false;
        }
    };


    public Pcs_SwipeHelper(Context context, RecyclerView recyclerView, int buttonWidth) {
        super(0, ItemTouchHelper.LEFT);
        this.recyclerView = recyclerView;
        this.buttonList = new ArrayList<>();
        this.gestureDetector =  new GestureDetector(context, gestureListener);
        this.buttonBuffer = new HashMap<>();
        this.buttonWidth = buttonWidth;

        removerQueue = new LinkedList<Integer>(){
            @Override
            public boolean add(Integer integer) {
                if(contains(integer))
                    return false;
                else
                    return super.add(integer);
            }

        };
        attachSwipe();
    }
    private void attachSwipe(){
         ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
         itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void recoverSwipeItem() {
        while(!removerQueue.isEmpty()){
            int pos = removerQueue.poll();
            if(pos > -1)
                recyclerView.getAdapter().notifyItemChanged(pos);
        }
    }



    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
    private class MyButton{
        private String text;
        private int imageResId, textSize, color, pos;
        private RectF clickRegion;
        private MyButtonClickListener listener;
        private Context context;
        private Resources resources;

        public MyButton(Context context, String text, int imageResId, int textSize, int color, int pos, RectF clickRegion, MyButtonClickListener listener) {
            this.text = text;
            this.imageResId = imageResId;
            this.textSize = textSize;
            this.color = color;
            this.pos = pos;
            this.clickRegion = clickRegion;
            this.listener = listener;
            this.context = context;
            resources = context.getResources();
        }
        public boolean onClick(float x, float y){
            if(clickRegion != null && clickRegion.contains(x,y)){
                listener.onClick(pos);
                return true;
            }
            return false;

        }
        public void onDraw(Canvas c, RectF rectF, int pos){
            Paint p = new Paint();
            p.setColor(color);
            c.drawRect(rectF, p);

            p.setColor(Color.WHITE);
            p.setTextSize(textSize);

            Rect r = new Rect();
            float cHeight = rectF.height();
            float cWidth = rectF.width();
            p.setTextAlign(Paint.Align.CENTER);
            p.getTextBounds(text, 0, text.length(), r);
            float x=0, y=0;
            if(imageResId ==0){
                x = cWidth/2f - r.width()/2f - r.left;
                y = cHeight/2f + r.height()/2f - r.bottom;
                c.drawText(text, rectF.left+x, rectF.top+y, p);

            }else{
                Drawable d = ContextCompat.getDrawable(context, imageResId);
                Bitmap bitmap = drawabletToBitmap(d);
                c.drawBitmap(bitmap, (rectF.left+rectF.right)/2, (rectF.top + rectF.bottom)/2, p);
            }
            clickRegion = rectF;
            this.pos = pos
        }

    }
    private Bitmap drawabletToBitmap(Drawable d){
        if( d instanceof BitmapDrawable)
            return ((BitmapDrawable)d).getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(),
                d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);
        return bitmap;
    }

    @Override
    public void onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }
}

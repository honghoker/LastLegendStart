package com.example.locationsave.HEP.pcs_RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;


public class Pcs_RecyclerViewSwipeHelper extends ItemTouchHelper.Callback {
    private RecyclerView.ViewHolder currentItemViewHolder = null;
    private boolean swipeBack = false;
    private RectF buttonInstance = null;
    private ButtonsState buttonShowedState = ButtonsState.GONE;
    private static final float buttonWidth = 300;
    private Pcs_RecyclerViewSwipeAction swipeAction = null;

    public void onDraw(Canvas c) {
        if(currentItemViewHolder != null){
            drawButtons(c, currentItemViewHolder);
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if(swipeBack){
            swipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        drawButtons(c, viewHolder);
        if(actionState == ACTION_STATE_SWIPE){
            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
        float buttonWithWithoutPadding = buttonWidth - 20;
        float corners = 16;

        View itemView = viewHolder.itemView;
        Paint p = new Paint();


        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWithWithoutPadding, itemView.getBottom());
        p.setColor(Color.BLUE);
        c.drawRoundRect(leftButton,corners, corners, p);
        drawText("EDIT", c, leftButton, p);

        RectF rightButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getRight() - buttonWithWithoutPadding, itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRoundRect(rightButton,corners, corners, p);
        drawText("EDIT", c, rightButton, p);

        buttonInstance = null;
        if(buttonShowedState == ButtonsState.LEFT_VISIBLE)
            buttonInstance = leftButton;
        else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE)
            buttonInstance = rightButton;
    }

    private void drawText(String text, Canvas c, RectF leftButton, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, leftButton.centerX() - (textWidth/2), leftButton.centerY() + (textSize/2), p);
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dx, final float dy, final int actionState, final boolean isCurrentlyActive){
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL ||
                        event.getAction() == MotionEvent.ACTION_UP;
                if(swipeBack){
                    if(dx <- buttonWidth) buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                    else if(dx > buttonWidth) buttonShowedState = ButtonsState.LEFT_VISIBLE;

                    if(buttonShowedState != ButtonsState.GONE){
                        setTouchListener(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
                        setItemClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                    final float dx, final float dy, final int actionState, final boolean isCurrentlyActive){

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Pcs_RecyclerViewSwipeHelper.super.onChildDraw(c, recyclerView, viewHolder, 0F, dy, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemClickable(recyclerView, true);
                    swipeBack = false;
                    buttonShowedState = ButtonsState.GONE;
                }
                return false;
            }
        });
    }
    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                    final float dx, final float dy, final int actionState, final boolean isCurrentlyActive){

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setItemClickable(RecyclerView recyclerView, boolean isClickcAble) {
        for(int i = 0; i<recyclerView.getChildCount(); i++){
            recyclerView.getChildAt(i).setClickable(isClickcAble);
        }
    }

    public Pcs_RecyclerViewSwipeHelper(Pcs_RecyclerViewSwipeAction swipeAction) {
        this.swipeAction = swipeAction;
    }

    enum ButtonsState{
        GONE,
        LEFT_VISIBLE,
        RIGHT_VISIBLE
    }
}

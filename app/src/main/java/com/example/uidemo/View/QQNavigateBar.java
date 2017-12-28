package com.example.uidemo.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.uidemo.R;

/**
 * Created by abc on 2017/12/25.
 */

public class QQNavigateBar extends LinearLayout implements View.OnClickListener,View.OnTouchListener{
    public final static int LEFT = 1;
    public final static int MIDDLE = 2;
    public final static int RIGHT = 3;

    private ImageView leftBig,leftSmall;
    private ImageView middleBig,middleSmall;
    private ImageView rightBig,rightSmall;
    //图形属性
    private float leftX,leftY;
    private float middleX,middleY;
    private float rightX,rightY;
    
    //设置参数
    private final static float dragRange = 20;//拖拽范围
    private final static double scale =1.5;//小图移动比例

    //运行参数
    private int current = MIDDLE;
    private float lastDx = 0, lastDy = 0;
    
    public QQNavigateBar(Context context) {
        super(context);
        init();
    }

    public QQNavigateBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.qq_navigatebar,this);
        leftBig = (ImageView)findViewById(R.id.imageView_left_big);
        leftSmall = (ImageView)findViewById(R.id.imageView_left_small);
        middleBig = (ImageView)findViewById(R.id.imageView_middle_big);
        middleSmall = (ImageView) findViewById(R.id.imageView_middle_small);
        rightBig = (ImageView) findViewById(R.id.imageView_right_big);
        rightSmall = (ImageView)findViewById(R.id.imageView_right_small);

        leftBig.setOnClickListener(this);
        middleBig.setOnClickListener(this);
        rightBig.setOnClickListener(this);
        leftBig.setOnTouchListener(this);
        middleBig.setOnTouchListener(this);
        rightBig.setOnTouchListener(this);
        
        leftX = (leftBig.getLeft()+leftBig.getRight()) / 2;
        leftY = (leftBig.getBottom()+leftBig.getTop())/2;
        middleX = (middleBig.getLeft()+middleBig.getRight()) / 2;
        middleY = (middleBig.getBottom()+middleBig.getTop())/2;
        rightX = (middleBig.getLeft()+middleBig.getRight()) / 2;
        rightY = (middleBig.getBottom()+middleBig.getTop())/2;
    }
    
    private void changeStatus(int pos,boolean isChecked) {
        
        switch (pos) {
            case LEFT :
                if(isChecked) {
                      leftBig.setImageResource(R.drawable.notice_checked_big);
                      leftSmall.setImageResource(R.drawable.notice_checked_small);
                      middleSmall.setImageResource(R.drawable.contact_left_small);
                } else {
                    leftBig.setImageResource(R.drawable.notice_default_big);
                    leftSmall.setImageResource(R.drawable.notice_default_small);
                }
                break;
            case MIDDLE :
                if(isChecked) {
                    middleBig.setImageResource(R.drawable.contact_checked_big);
                    middleSmall.setImageResource(R.drawable.contact_checked_small);
                } else {
                    middleBig.setImageResource(R.drawable.contact_default_big);
                }
                break;
            case RIGHT :
                if(isChecked) {
                    rightSmall.setVisibility(View.VISIBLE);
                    rightBig.setImageResource(R.drawable.update_checked_big);
                    rightSmall.setImageResource(R.drawable.update_checked_small);
                    middleSmall.setImageResource(R.drawable.contact_right_small);
                } else {
                    rightSmall.setVisibility(View.INVISIBLE);
                    rightBig.setImageResource(R.drawable.update_default_big);
                }
                break;
        }
        
    }

    
    public void moveImage(int pos,float dx,float dy) {
        if(pos == 0) return ;
        switch (pos) {
            case LEFT :
                leftBig.setTranslationX(dx);
                leftBig.setTranslationY((dy));
                leftSmall.setTranslationX((1.5f*dx));
                leftSmall.setTranslationY((1.5f*dy));
                break;
            case MIDDLE :
                middleBig.setTranslationX(dx);
                middleBig.setTranslationY((dy));
                middleSmall.setTranslationX((1.5f*dx));
                middleSmall.setTranslationY((1.5f*dy));
                break;
            case RIGHT :
                rightBig.setTranslationX(dx);
                rightBig.setTranslationY((dy));
                rightSmall.setTranslationX((1.5f*dx));
                rightSmall.setTranslationY((1.5f*dy));
                break;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE :
                double dy,dx,fixX=0,fixY=0;
                int lastDrag = 0;
                switch (v.getId()) {
                    case R.id.imageView_left_big :
                        fixX = leftX;
                        fixY = leftX;
                        lastDrag = LEFT;
                        break;
                    case R.id.imageView_middle_big:
                        fixX = middleX;
                        fixY = middleY;
                        lastDrag = MIDDLE;
                        break;
                    case R.id.imageView_right_big :
                        fixX = rightX;
                        fixY = rightY;
                        lastDrag = RIGHT;
                        break ;
                }

                //计算偏移量
                dy = event.getY() - fixY;
                dx = event.getX() - fixX;
                float angle = (float) Math.atan2(dy,dx);
                float radius =  (float) Math.sqrt(dx*dx+dy*dy);
                if(radius>dragRange) radius = dragRange;


                moveImage(lastDrag,radius * (float)Math.cos(angle),radius * (float)Math.sin(angle));
                break;

            case MotionEvent.ACTION_UP :
                moveImage(LEFT,0,0);
                moveImage(MIDDLE,0,0);
                moveImage(RIGHT,0,0);
                break ;
            default:
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int last = current;
        switch (v.getId()) {
            case R.id.imageView_left_big :
                current = LEFT;
                changeStatus(last,false);
                changeStatus(current,true);
                break ;
            case R.id.imageView_middle_big :
                current = MIDDLE;
                changeStatus(last,false);
                changeStatus(current,true);
                break;
            case R.id.imageView_right_big :
                current = RIGHT;
                changeStatus(last,false);
                changeStatus(current,true);
                break;
        }
    }

    public float getNoticeX() {
        return leftX;
    }

    public void setNoticeX(float leftX) {
        this.leftX = leftX;
    }

    public float getNoticeY() {
        return leftY;
    }

    public void setNoticeY(float leftY) {
        this.leftY = leftY;
    }

    public float getContactX() {
        return middleX;
    }

    public void setContactX(float middleX) {
        this.middleX = middleX;
    }

    public float getContactY() {
        return middleY;
    }

    public void setContactY(float middleY) {
        this.middleY = middleY;
    }

    public float getUpdateX() {
        return rightX;
    }

    public void setUpdateX(float rightX) {
        this.rightX = rightX;
    }

    public float getUpdateY() {
        return rightY;
    }

    public void setUpdateY(float rightY) {
        this.rightY = rightY;
    }
}

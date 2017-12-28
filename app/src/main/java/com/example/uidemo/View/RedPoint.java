package com.example.uidemo.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

/**
 * Created by abc on 2017/12/24.
 */

public class RedPoint extends View {

    private Paint pointPaint,textPaint;
    private Path mPath;
    PointF mDragCanterPoint = new PointF(600, 450);
    PointF mFixCanterPoint = new PointF(250, 250);
    PointF mControlPoint = new PointF();
    PointF[] mFixTangentPoints = new PointF[] { new PointF(), new PointF() };
    PointF[] mDragTangentPoints = new PointF[] { new PointF(), new PointF() };
    float mDragRadius = 40;//拖拽圆半径
    float mFixRadius = 35;//固定圆半径
    private boolean hasBeenOut = false;//是否出过界
    private boolean isMoved = false;//是否在移动
    private boolean isReset = false;
    private String text;

    public RedPoint(Context context) {
        this(context,null);
    }

    public RedPoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pointPaint = new Paint();
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(20);
        pointPaint.setColor(Color.RED);
        pointPaint.setAntiAlias(true);
        mPath = new Path();

        text = "test";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("重绘","进入重绘");
        if(isMoved||isReset) {
            Log.d("重绘","移动重绘");
            calculate();
            canvas.drawCircle(mDragCanterPoint.x, mDragCanterPoint.y, mDragRadius, pointPaint);
            canvas.drawText(text,mDragCanterPoint.x-mDragRadius/2,mDragCanterPoint.y+mDragRadius/2,textPaint);
            if (!hasBeenOut||isReset) {
                canvas.drawCircle(mFixCanterPoint.x, mFixCanterPoint.y, mFixRadius, pointPaint);
                mPath.reset();
                mPath.moveTo(mFixTangentPoints[0].x, mFixTangentPoints[0].y);
                mPath.quadTo(mControlPoint.x, mControlPoint.y,
                        mDragTangentPoints[0].x, mDragTangentPoints[0].y);
                mPath.lineTo(mDragTangentPoints[1].x, mDragTangentPoints[1].y);
                mPath.quadTo(mControlPoint.x, mControlPoint.y,
                        mFixTangentPoints[1].x, mFixTangentPoints[1].y);
                mPath.close();
                canvas.drawPath(mPath, pointPaint);
            }
        }
        else {
            canvas.drawCircle(mFixCanterPoint.x, mFixCanterPoint.y, mFixRadius, pointPaint);
            canvas.drawText(text,mFixCanterPoint.x-mFixRadius/2,mFixCanterPoint.y+mFixRadius/2,textPaint);
        }

    }

    public void calculate() {
        double angle ;
        double dx,dy;
        dy = mDragCanterPoint.y - mFixCanterPoint.y;
        dx = mDragCanterPoint.x - mFixCanterPoint.x;

        mFixRadius = mDragRadius - (float)Math.sqrt((double)(dx*dx+dy*dy))/10;

        //计算角度
        if(dy != 0)  angle =  Math.atan2(dx,dy);
        else angle = Math.PI/2;
        //控制点、四个数据点
        mControlPoint.x = (mFixCanterPoint.x + mDragCanterPoint.x)/2;
        mControlPoint.y = (mFixCanterPoint.y + mDragCanterPoint.y)/2;
        mFixTangentPoints[0].x = (float) (mFixCanterPoint.x-mFixRadius*Math.cos(angle));
        mFixTangentPoints[0].y = (float) (mFixCanterPoint.y+mFixRadius*Math.sin(angle));
        mFixTangentPoints[1].x = (float) (mFixCanterPoint.x+mFixRadius*Math.cos(angle));
        mFixTangentPoints[1].y = (float) (mFixCanterPoint.y-mFixRadius*Math.sin(angle));
        mDragTangentPoints[0].x = (float) (mDragCanterPoint.x-mDragRadius*Math.cos(angle));
        mDragTangentPoints[0].y = (float) (mDragCanterPoint.y+mDragRadius*Math.sin(angle));
        mDragTangentPoints[1].x = (float) (mDragCanterPoint.x+mDragRadius*Math.cos(angle));
        mDragTangentPoints[1].y = (float) (mDragCanterPoint.y-mDragRadius*Math.sin(angle));
    }

    //很蠢的回弹实现
    public void pointReset() {


        final float dx =mDragCanterPoint.x - mFixCanterPoint.x;
        final float dy = mDragCanterPoint.y - mFixCanterPoint.y;
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f,0.0f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isReset = false;
                init();
            }
            @Override
            public void onAnimationStart(Animator animation) {
                isReset = true;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = animation.getAnimatedFraction();
                mDragCanterPoint.x = mFixCanterPoint.x + (1-current) * dx;
                mDragCanterPoint.y = mFixCanterPoint.y + (1-current) * dy;
                invalidate();


            }
            });
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(500);
        animator.start();

    }
    
    boolean checkOut() {
        double dx,dy;
        dy = mDragCanterPoint.y - mFixCanterPoint.y;
        dx = mDragCanterPoint.x - mDragCanterPoint.x;
        double dis = Math.sqrt((double)(dx*dx+dy*dy));
        if(dis >6*mDragRadius) return true;
        else return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(Math.abs(event.getX()-mFixCanterPoint.x)>1.5*mFixRadius
                        ||Math.abs(event.getY()-mFixCanterPoint.y)>1.5*mFixRadius)
                    return false;
                    break;
            case MotionEvent.ACTION_MOVE :
                mDragCanterPoint.x = event.getX();
                mDragCanterPoint.y = event.getY();
                if(checkOut()) hasBeenOut = true;
                isMoved = true;
                invalidate();
                break ;
            case MotionEvent.ACTION_UP:
                if(checkOut()) {
                    Toast.makeText(getContext(),"out",Toast.LENGTH_LONG).show();
                    setVisibility(View.GONE);
                }
                else {
                    if(!hasBeenOut) {
                        pointReset();
                    }
                    else {
                        init();
                        invalidate();
                    }
                    break;
                }
        }
        return true;
    }

    public void init() {
        isMoved = false;
        hasBeenOut = false;
        mDragCanterPoint.x = mFixCanterPoint.x;
        mDragCanterPoint.y = mFixCanterPoint.y;
        mFixRadius = mDragRadius;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

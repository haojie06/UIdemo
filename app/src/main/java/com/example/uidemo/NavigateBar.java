package com.example.uidemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by abc on 2017/12/18.
 */

public class NavigateBar extends LinearLayout implements View.OnClickListener{
    //各个控件
    private Button buttonFirst,buttonSecond,buttonThird,buttonFourth;
    private TextView textFirst,textSecond,textThird,textFourth;
    //图标资源
    public Drawable firstDefaultImage,secondDefaultImage,thirdDefaultImage,fourthDefaultImage;
    public Drawable firstCheckedImage,secondCheckedImage,thirdCheckedImage,fourthCheckedImage;
    //四个位置
    public static final int FIRST=1;
    public static final int SECOND=2;
    public static final int THIRD=3;
    public static final int FOURTH=4;
    //上次点击按钮
    private int lastClicked = FIRST;
    //回调
    public NavigateCallback callbackFirst,callbackSecond,callbackThird,callbackFourth;

    //回调接口
    public interface NavigateCallback {
        public void callBack();
    }


    public NavigateBar(Context context) {
        super(context);
        init();
    }

    public NavigateBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        changeButtonState(FIRST,true);
    }
    
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.navigate_bar,this);
        buttonFirst = (Button)findViewById(R.id.button_first);
        buttonSecond = (Button)findViewById(R.id.button_second);
        buttonThird = (Button) findViewById(R.id.button_third);
        buttonFourth = (Button) findViewById(R.id.button_fourth);
        textFirst = (TextView) findViewById(R.id.textView_first);
        textSecond = (TextView) findViewById(R.id.textView_second);
        textThird = (TextView) findViewById(R.id.textView_third);
        textFourth = (TextView) findViewById(R.id.textView_fourth);
        buttonFirst.setOnClickListener(this);
        buttonSecond.setOnClickListener(this);
        buttonThird.setOnClickListener(this);
        buttonFourth.setOnClickListener(this);
    }

    public void setText(int position,String content) {
        switch (position) {
            case FIRST:
                textFirst.setText(content);
                break;
            case SECOND:
                textSecond.setText(content);
                break;
            case THIRD :
                textThird.setText(content);
                break;
            case FOURTH:
                textFourth.setText(content);
                break;
        }
    }

    public void setDefaultImage(int position, Drawable image) {
        switch (position) {
            case FIRST:
                firstDefaultImage = image;
                buttonFirst.setBackground(image);
                break;
            case SECOND:
                secondDefaultImage = image;
                buttonSecond.setBackground(image);
                break;
            case THIRD :
                thirdDefaultImage = image;
                buttonThird.setBackground(image);
                break;
            case FOURTH:
                fourthDefaultImage = image;
                buttonFourth.setBackground(image);
                break;
            default:
                break;
        }
    }
    public void setCheckedImage(int position, Drawable image) {
        switch (position) {
            case FIRST:
                firstCheckedImage = image;
                break;
            case SECOND:
                secondCheckedImage = image;
                break;
            case THIRD :
                thirdCheckedImage = image;
                break;
            case FOURTH:
                fourthCheckedImage = image;
                break;
            default:
                break;
        }
    }

    public void setCallback(int position,NavigateCallback callback) {
        switch (position) {
            case FIRST:
                callbackFirst = callback;
                break ;
            case SECOND:
                callbackSecond = callback;
                break;
            case THIRD:
                callbackThird = callback;
                break;
            case FOURTH :
                callbackFourth = callback;
                break;
            default :
                break;
        }
    }

    public void changeButtonState(int position,boolean checked) {
        switch (position) {
            case FIRST:
                if(checked) buttonFirst.setBackground(firstCheckedImage);
                else buttonFirst.setBackground(firstDefaultImage);
                break;
            case SECOND :
                if(checked) buttonSecond.setBackground(secondCheckedImage);
                else buttonSecond.setBackground(secondDefaultImage);
                break;
            case THIRD :
                if(checked) buttonThird.setBackground(thirdCheckedImage);
                else buttonThird.setBackground(thirdDefaultImage);
                break;
            case FOURTH :
                if(checked) buttonFourth.setBackground(fourthCheckedImage);
                else buttonFourth.setBackground(fourthDefaultImage);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        //将上次点击按钮恢复默认
        changeButtonState(lastClicked,false);
        //将本次点击按钮改变状态并执行回调
        switch (v.getId()) {
            case R.id.button_first:
                changeButtonState(FIRST,true);
                lastClicked = FIRST;
                callbackFirst.callBack();
                break;
            case R.id.button_second:
                changeButtonState(SECOND,true);
                lastClicked = SECOND;
                callbackSecond.callBack();
                break;
            case R.id.button_third:
                changeButtonState(THIRD,true);
                lastClicked = THIRD;
                callbackThird.callBack();
                break;
            case R.id.button_fourth:
                changeButtonState(FOURTH,true);
                lastClicked = FOURTH;
                callbackFourth.callBack();
                break;
            default:
                break;
        }
    }
}

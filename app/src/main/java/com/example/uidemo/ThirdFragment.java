package com.example.uidemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by abc on 2017/12/18.
 */

public class ThirdFragment extends Fragment{
    FrameLayout piggy;
    View view;
    View.OnClickListener piggyCallback;


    public void setPiggyCallback(View.OnClickListener callback) {
        piggyCallback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.third_fragment,container,false);
        piggy = view.findViewById(R.id.viewGroup_piggy);
        piggy.setClickable(true);
        piggy.setOnClickListener(piggyCallback);
        return view;
    }
}

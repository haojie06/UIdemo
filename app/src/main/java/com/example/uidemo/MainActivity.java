package com.example.uidemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.uidemo.View.NavigateBar;

public class MainActivity extends AppCompatActivity {


    NavigateBar navigateBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRes();
    }

    public void initRes() {
        setContentView(R.layout.activity_main);
        initNavigateBar();
        changeFragment(new FirstFragment());
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_fragment,fragment);
        transaction.commit();
    }

    public void initNavigateBar() {
        navigateBar = findViewById(R.id.navigate_bar);
        navigateBar.setDefaultImage(NavigateBar.FIRST,getDrawable(R.drawable.house));
        navigateBar.setDefaultImage(NavigateBar.SECOND,getDrawable(R.drawable.pig));
        navigateBar.setDefaultImage(NavigateBar.THIRD,getDrawable(R.drawable.wallet));
        navigateBar.setDefaultImage(NavigateBar.FOURTH,getDrawable(R.drawable.profit));
        navigateBar.setCheckedImage(NavigateBar.FIRST,getDrawable(R.drawable.house_color));
        navigateBar.setCheckedImage(NavigateBar.SECOND,getDrawable(R.drawable.pig_color));
        navigateBar.setCheckedImage(NavigateBar.THIRD,getDrawable(R.drawable.wallet_color));
        navigateBar.setCheckedImage(NavigateBar.FOURTH,getDrawable(R.drawable.profit_color));
        navigateBar.setText(NavigateBar.FIRST,"首页");
        navigateBar.setText(NavigateBar.SECOND,"理财");
        navigateBar.setText(NavigateBar.THIRD,"资产");
        navigateBar.setText(NavigateBar.FOURTH,"我的");
        navigateBar.setCallback(NavigateBar.FIRST, new NavigateBar.NavigateCallback() {
            @Override
            public void callBack() {
                changeFragment(new FirstFragment());
            }
        });
        navigateBar.setCallback(NavigateBar.SECOND, new NavigateBar.NavigateCallback() {
            @Override
            public void callBack() {
                changeFragment(new SecondFragment());
            }
        });navigateBar.setCallback(NavigateBar.THIRD, new NavigateBar.NavigateCallback() {
            @Override
            public void callBack() {
                ThirdFragment thirdFragment = new ThirdFragment();
                thirdFragment.setPiggyCallback(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeFragment(new PiggyFragment());
                    }
                });
                changeFragment(thirdFragment);

            }
        });navigateBar.setCallback(NavigateBar.FOURTH, new NavigateBar.NavigateCallback() {
            @Override
            public void callBack() {
                changeFragment(new FourthFragment());
            }
        });
    }

}

package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.traffic.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Administrator on 2018/1/7.
 */

public class Fragment_3 extends Fragment {

    private  SlidingMenu menu;
    private View inflate;
//
//    public Fragment_3(SlidingMenu slidingMenu) {
//        this.menu = slidingMenu;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_3, null);
      // TextView tv_test3 = (TextView) inflate.findViewById(R.id.tv_test3);
      //  menu.addIgnoredView(tv_test3);
        return inflate;
    }
}

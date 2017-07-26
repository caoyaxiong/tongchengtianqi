package com.tongchengtianqi.zuitoutiao.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongchengtianqi.zuitoutiao.R;

public class MainFragment {
    public static final int[] mTabRes = new int[]{R.drawable.home,
            R.drawable.newscenter, R.drawable.personal_center,};
    public static final int[] mTabResPressed = new int[]{R.drawable.update,
            R.drawable.update, R.drawable.personal_center_press,};
    public static final String[] mTabTitle = {"首页", "视频", "我的"};
    public static final String[] mTabTitleSelect = {"刷新", "刷新", "我的"};
    private static Fragment[] fragments;

    public static Fragment[] getFragments() {
        fragments = new Fragment[3];
        fragments[0] = new HomeFragment();
        fragments[1] = new VideoFragment();
        fragments[2] = new MineFragment();
        return fragments;
    }

    public static View getTabView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab, null);
        ImageView tabIcon = view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(MainFragment.mTabRes[position]);
        TextView tabText = view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}

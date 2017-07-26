package com.tongchengtianqi.zuitoutiao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import com.tongchengtianqi.zuitoutiao.base.BaseFragment;
import com.tongchengtianqi.zuitoutiao.entity.TabTextBean;


public class VideoAdapter extends FragmentPagerAdapter{


    private final FragmentManager fm;
    private final List mFragmentList;
    private final List<TabTextBean.DataBean.ChannelsBean> mTabs;

    public VideoAdapter(FragmentManager fm, List<BaseFragment> mFragmentList, List<TabTextBean.DataBean.ChannelsBean> mTabs) {
        super(fm);

        this.fm = fm;
        this.mFragmentList = mFragmentList;
        this.mTabs = mTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}

package com.tongchengtianqi.zuitoutiao.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import com.tongchengtianqi.zuitoutiao.entity.TabTextBean;
import com.tongchengtianqi.zuitoutiao.fragment.VideoTabFragment;


public class VideoFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager childFragmentManager;
    private List<TabTextBean.DataBean.ChannelsBean> mVideoTabs;

    public VideoFragmentAdapter(FragmentManager childFragmentManager,
                                List<TabTextBean.DataBean.ChannelsBean> mVideoTabs) {
        super(childFragmentManager);
        this.childFragmentManager = childFragmentManager;

        this.mVideoTabs = mVideoTabs;
        Log.e("asdf", "getItem:::: ");
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void setmTabs(List<TabTextBean.DataBean.ChannelsBean> mTabs) {

        for (int i = 0; i < mTabs.size(); i++) {
            String name = makeFragmentName(containerId, getItemId(i));
            Fragment fragment = childFragmentManager.findFragmentByTag(name);
            if(fragment != null){
                if(fragment instanceof VideoTabFragment && i < mTabs.size()){
                    VideoTabFragment videoTabFragment = (VideoTabFragment) fragment;
                    videoTabFragment.setCid(mTabs.get(i).getCid());
                }
            }
        }
        notifyDataSetChanged();
    }

    private int containerId ;
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        containerId = container.getId();
        return super.instantiateItem(container, position);
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("cid", mVideoTabs.get(position).cid);
        Log.e("asdf", "getItem: ");
        VideoTabFragment videoTabFragment =  new VideoTabFragment();
        videoTabFragment.setArguments(bundle);
        return videoTabFragment;
    }


    @Override
    public int getCount() {
        return mVideoTabs.size();
        //Log.e("asdf", "getItem::::");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mVideoTabs.get(position).getCname();
    }
}

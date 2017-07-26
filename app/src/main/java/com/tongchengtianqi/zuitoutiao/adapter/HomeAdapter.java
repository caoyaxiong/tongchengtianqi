package com.tongchengtianqi.zuitoutiao.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.tongchengtianqi.zuitoutiao.entity.TabTextBean;
import com.tongchengtianqi.zuitoutiao.fragment.HomeTabFragment;

import java.util.List;


public class HomeAdapter extends FragmentPagerAdapter {

    private List<TabTextBean.DataBean.ChannelsBean> mTabs;
    private FragmentManager fm;

    public HomeAdapter(FragmentManager fm , List<TabTextBean.DataBean.ChannelsBean> mTabs) {
        super(fm);
        this.mTabs = mTabs;
        this.fm =fm;
    }

    public void setmTabs(List<TabTextBean.DataBean.ChannelsBean> mTabs) {
        this.mTabs = mTabs;
        for (int i = 0; i < mTabs.size(); i++) {
            String name = makeFragmentName(containerId, getItemId(i));
            Fragment fragment = fm.findFragmentByTag(name);
            if(fragment != null){
                if(fragment instanceof HomeTabFragment && i < mTabs.size()){
                    HomeTabFragment homeTabFragment = (HomeTabFragment) fragment;
                    homeTabFragment.setCid(mTabs.get(i).getCid());
                }
            }
        }
        notifyDataSetChanged();
    }

    /*返回要显示的Fragment的某个实例*/
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("cid", mTabs.get(position).cid);
        HomeTabFragment homeTabFragment =  new HomeTabFragment();
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;
    }

    /*返回显示fragment的总数*/
    @Override
    public int getCount() {
        return mTabs == null ? 0 : mTabs.size();
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
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
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }


    /** TabLayout会调用这个方法获取Fragment的名称，显示到Tabs中，当要自定义Tab的时候不应该重写该方法
     * @param position 要生成tab的标题
    @Override*/
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getCname();
    }

}
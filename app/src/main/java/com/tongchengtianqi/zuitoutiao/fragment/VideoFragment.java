package com.tongchengtianqi.zuitoutiao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.activity.SearchActivity;
import com.tongchengtianqi.zuitoutiao.adapter.VideoFragmentAdapter;
import com.tongchengtianqi.zuitoutiao.base.BaseFragment;
import com.tongchengtianqi.zuitoutiao.entity.TabTextBean;
import com.tongchengtianqi.zuitoutiao.http.Urls;
import com.tongchengtianqi.zuitoutiao.utils.JsonUtils;
import com.tongchengtianqi.zuitoutiao.utils.OkHttpManager;
import com.tongchengtianqi.zuitoutiao.utils.SpUtils;
import com.tongchengtianqi.zuitoutiao.utils.ToastUtils;

public class VideoFragment extends BaseFragment implements View.OnClickListener, TabLayout.OnTabSelectedListener {


    private LinearLayout mVideoImg;
    private TabLayout mVideoTabLayout;
    private ViewPager mVideoViewpager;
    private Intent mIntent;
    private VideoFragmentAdapter mVideoAdapter;
    private String spVideoTabName;
    private List<TabTextBean.DataBean.ChannelsBean> mVideoTab;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {      //缓存的fragment可见时调用
            initViewpager();
            initData();
            initListener();
        } else {
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mVideoImg = view.findViewById(R.id.video_img);
        mVideoTabLayout = view.findViewById(R.id.video_tabs);
        mVideoViewpager = view.findViewById(R.id.video_viewpager);
        mIntent = new Intent();
    }


    @Override
    protected void initListener() {
        mVideoImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_img:
                //跳转到搜索页面
                mIntent.setClass(getActivity(), SearchActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void initData() {
        Log.e("rab", "initDataa: " + "");
        final int index = 1;
        String mVideoTab = Urls.CHANNEL_DATA + "?" + "app_id=" + index;
        spVideoTabName = SpUtils.getStringParam(mActivity, "videoTabName", null);
        if (!TextUtils.isEmpty(spVideoTabName)) {
            parseVideoTabData(spVideoTabName);

        }
        OkHttpManager.enqueueAsync(mVideoTab, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(mActivity,"获取数据失败",2000);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String string = body.string();
                if (!TextUtils.equals(spVideoTabName, string)) {
                    spVideoTabName = string;
                    parseVideoTabData(spVideoTabName);
                    SpUtils.putParam(mActivity, "videoTabName", spVideoTabName);
                }
            }
        });
    }

    private void parseVideoTabData(String spVideoTabName) {
        TabTextBean mTabText = JsonUtils.json2Bean(spVideoTabName, TabTextBean.class);
        mVideoTab = mTabText.getData().getChannels();
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setVideoDataAndTabs(mVideoTab);
                }
            });
        }
    }

    private void setVideoDataAndTabs(List<TabTextBean.DataBean.ChannelsBean> mVideoTab) {
        mVideoAdapter.setmTabs(mVideoTab);
        mVideoViewpager.setOffscreenPageLimit(mVideoTab.size());
        for (int i = 0; i < mVideoTab.size(); i++) {
            mVideoTabLayout.addTab(mVideoTabLayout.newTab().setText(mVideoTab.get(i).getCname()));
        }
    }

    private void initViewpager() {
        Log.e("rab", "initview pager: " + "");
        mVideoAdapter = new VideoFragmentAdapter(getChildFragmentManager(),
                new ArrayList<TabTextBean.DataBean.ChannelsBean>());
        mVideoViewpager.setAdapter(mVideoAdapter);
        Log.e("asder", "initViewpager:");
        // 将ViewPager和TabLayout绑定
        mVideoTabLayout.setupWithViewPager(mVideoViewpager);
        mVideoTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        mVideoViewpager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}

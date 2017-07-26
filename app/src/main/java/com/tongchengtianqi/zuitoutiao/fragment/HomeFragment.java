package com.tongchengtianqi.zuitoutiao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.activity.ChannelManagementActivity;
import com.tongchengtianqi.zuitoutiao.activity.SearchActivity;
import com.tongchengtianqi.zuitoutiao.adapter.HomeAdapter;
import com.tongchengtianqi.zuitoutiao.base.BaseFragment;
import com.tongchengtianqi.zuitoutiao.entity.TabTextBean;
import com.tongchengtianqi.zuitoutiao.http.Urls;
import com.tongchengtianqi.zuitoutiao.utils.JsonUtils;
import com.tongchengtianqi.zuitoutiao.utils.OkHttpManager;
import com.tongchengtianqi.zuitoutiao.utils.SpUtils;
import com.tongchengtianqi.zuitoutiao.utils.ToastUtils;
import com.tongchengtianqi.zuitoutiao.view.CustomViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HomeFragment extends BaseFragment implements View.OnClickListener, TabLayout.OnTabSelectedListener {

    private CustomViewPager mHomeViewpager;
    private LinearLayout mChannelManagementImg;
    private TabLayout mHomeTabLayout;
    private Intent mIntent;
    public List<TabTextBean.DataBean.ChannelsBean> mChannels;
    private HomeAdapter mHomeAdapter;
    private TabTextBean mTabText;
    private String spTabName;
    private LinearLayout mHomeSearch;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mHomeViewpager = view.findViewById(R.id.home_viewpager);
        mHomeTabLayout = view.findViewById(R.id.home_tabs);  // 初始化标签
        mHomeSearch = view.findViewById(R.id.home_search);
        mChannelManagementImg = view.findViewById(R.id.channel_management_img); //频道管理*/
        mIntent = new Intent();
        initViewPager();
        initData();
    }

    @Override
    protected void initListener() {
        mChannelManagementImg.setOnClickListener(this);
        mHomeSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_search:
                //跳转到搜索页面
                mIntent.setClass(getActivity(), SearchActivity.class);
                startActivity(mIntent);
                break;

            case R.id.channel_management_img:
                //跳转到频道管理页面
                mIntent.setClass(getActivity(), ChannelManagementActivity.class);
                mIntent.putExtra("tabName", spTabName);
                startActivityForResult(mIntent, 100);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null && resultCode == 50) {
                String tab = data.getStringExtra("tab");
                for (int i = 0; i < mChannels.size(); i++) {
                    String cname = mChannels.get(i).getCname();
                    if (TextUtils.equals(tab, cname)) {
                        mHomeViewpager.setCurrentItem(i);
                        break;
                    }
                }
            } else if (data != null && resultCode == 40) {
                ArrayList<String> completeList = data.getStringArrayListExtra("completeList");
                if (completeList == null) {
                    return;
                }
                ArrayList<TabTextBean.DataBean.ChannelsBean> tempList = new ArrayList<>();
                tempList.addAll(mChannels);
                for (int i = 0; i < tempList.size(); i++) {
                    TabTextBean.DataBean.ChannelsBean channelsBean = tempList.get(i);
                    if (channelsBean != null && !completeList.contains(channelsBean.getCname())) {
                        tempList.remove(channelsBean);
                        if (i > 0) {
                            i = i - 1;
                        } else {
                            i = 0;
                        }
                    }
                }
                setDataAndTabs(tempList);
            }
        }
    }

    @Override
    public void initData() {
        final int index = 1;
        String channelUrl = Urls.CHANNEL_DATA + "?" + "app_id=" + index;
        spTabName = SpUtils.getStringParam(mActivity, "tabName", null);
        if (!TextUtils.isEmpty(spTabName)) {
            parseTabData(spTabName);

        }
        OkHttpManager.enqueueAsync(channelUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (mActivity != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(mActivity, "获取数据失败", 2000);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String string = body.string();
                if (!TextUtils.equals(spTabName, string)) {
                    spTabName = string;
                    parseTabData(spTabName);
                    SpUtils.putParam(mActivity, "tabName", string);
                }
            }
        });
    }


    private void parseTabData(String string) {
        mTabText = JsonUtils.json2Bean(string, TabTextBean.class);
        mChannels = mTabText.getData().getChannels();
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setDataAndTabs(mChannels);
                }
            });
        }
    }

    private void setDataAndTabs(List<TabTextBean.DataBean.ChannelsBean> tempChannles) {
        mHomeAdapter.setmTabs(tempChannles);
        mHomeViewpager.setOffscreenPageLimit(tempChannles.size());
        if (mHomeTabLayout.getTabCount() > 0) {
            mHomeTabLayout.removeAllTabs();
        }
        for (int i = 0; i < tempChannles.size(); i++) {
            mHomeTabLayout.addTab(mHomeTabLayout.newTab().setText(tempChannles.get(i).getCname()));
        }
    }

    private void initViewPager() {
        mHomeAdapter = new HomeAdapter(getChildFragmentManager(), new ArrayList<TabTextBean.DataBean.ChannelsBean>());
        mHomeViewpager.setAdapter(mHomeAdapter);
        // 将ViewPager和TabLayout绑定
        mHomeTabLayout.setupWithViewPager(mHomeViewpager);

        mHomeTabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        mHomeViewpager.setCurrentItem(position);

         /*Tab 选中之后，改变各个Tab的状态*/
        for (int i = 0; i < mHomeTabLayout.getTabCount(); i++) {
            View view = mHomeTabLayout.getTabAt(i).getCustomView();
            if (view == null) return;
            TextView text = view.findViewById(R.id.custom_tablayout_text);
            if (i == tab.getPosition()) { // 选中状态
                text.setTextColor(getResources().getColor(R.color.green));
                text.setText(mChannels.get(i).getCname());
            } else {// 未选中状态
                text.setTextColor(getResources().getColor(R.color.black));
                text.setText(mChannels.get(i).getCname());

            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    //获取自定义的tab
    private View getCustomTab(Context context, int index) {
        View customTab = LayoutInflater.from(context).inflate(R.layout.custom_tablayout, null);
        TextView mCustomTabText = customTab.findViewById(R.id.custom_tablayout_text);
        mCustomTabText.setText(mChannels.get(index).getCname());
        return customTab;
    }

}

package com.tongchengtianqi.zuitoutiao.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.activity.DetailPageActivity;
import com.tongchengtianqi.zuitoutiao.adapter.HomeMultipleItemQuickAdapter;
import com.tongchengtianqi.zuitoutiao.base.BaseFragment;
import com.tongchengtianqi.zuitoutiao.entity.MultipleBeanBase;
import com.tongchengtianqi.zuitoutiao.http.Urls;
import com.tongchengtianqi.zuitoutiao.utils.NetUtils;
import com.tongchengtianqi.zuitoutiao.utils.OkHttpManager;
import com.tongchengtianqi.zuitoutiao.utils.ToastUtils;
import com.tongchengtianqi.zuitoutiao.view.SpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HomeTabFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mTabFragmentRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private HomeMultipleItemQuickAdapter mAdapter;
    private String mChannelUrl;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isPullToRefresh;
    private String pageToken = "0";
    private int cid;
    private Intent mIntent;
    private TextView mPopupWindowText;
    private PopupWindow mPopWindow;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_tab;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mIntent = new Intent();
        mTabFragmentRecyclerView = view.findViewById(R.id.tab_fragment_recycler_view);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mTabFragmentRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new HomeMultipleItemQuickAdapter(mActivity); //多条目布局的适配器
        mTabFragmentRecyclerView.setAdapter(mAdapter);
        //设置预加载条目
        mAdapter.setPreLoadNumber(10);
        mAdapter.setOnLoadMoreListener(this, mTabFragmentRecyclerView);
        //设置item间距，20px
        mTabFragmentRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        //添加item之间的分割线
        mTabFragmentRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                LinearLayoutManager.VERTICAL));
        Bundle bundle = getArguments();
        if (bundle != null)
            cid = bundle.getInt("cid");
    }

    public void setCid(int cid) {
        this.cid = cid;
        pageToken = "0";
        isPullToRefresh = true;
        initData();
    }

    @Override
    public void onStart() {
        if (getUserVisibleHint()) {
            //界面可见、还未请求数据并且不是由onStart()请求数据时执行
            boolean isConnected = NetUtils.isConnected(mActivity);
            if (isConnected) {
                initData();
            } else {
                //用Toast提示没有连接网络
                ToastUtils.show(mActivity, "无网络连接", 2000);
            }
        }
        super.onStart();
    }

    @Override
    public void initListener() {

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (TextUtils.isEmpty(pageToken)) {
                    pageToken = "1";
                }
                isPullToRefresh = true;
                initData();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleBeanBase multipleBeanBase = mAdapter.getData().get(position);//返回当前条目的bean
                JSONObject object = (JSONObject) multipleBeanBase.getObject();
                String url = object.optString("url");
                mIntent.putExtra("url", url);
                mIntent.setClass(getActivity(), DetailPageActivity.class);
                startActivity(mIntent);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showPopupWindow();
               // ToastUtils.show(mActivity, "以减少此次内容推荐", 2000);
            }
        });
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.recyclerview_window, null);
        mPopWindow = new PopupWindow(contentView,
                RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //点击空白地方消失
        mPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //显示PopupWindow
        //View rootView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_home_tab, null);
        mPopWindow.showAtLocation(contentView, Gravity.TOP,0,0);
        //设置各个控件的点击响应
        mPopupWindowText = contentView.findViewById(R.id.popup_window);
        mPopupWindowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
                ToastUtils.show(mActivity,"删除该条目",2000);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser + cid);
        if (isVisibleToUser && mTabFragmentRecyclerView != null) {
            if (mAdapter.getData().size() == 0) {
                mSwipeRefresh.setRefreshing(true);
                initData();
            }
        }
    }

    @Override
    public void initData() {
        int index = 1;
        final String articleId = "0";
        long createTime = 0;
        if (TextUtils.isEmpty(pageToken)) {
            pageToken = "2";
        }
        mChannelUrl = Urls.INFORMATION_DATA + "?" + "app_id=" + index + "&" +"c=0&"+
                "cid=" + cid + "&" + "pageToken=" + pageToken + "&" + "articleId=" + articleId
                + "&" + "createTime=" + createTime;
        Log.e(TAG, "initData: " + mChannelUrl);


        OkHttpManager.enqueueAsync(mChannelUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (mActivity != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(mActivity, "获取数据失败", 2000);
                            mSwipeRefresh.setRefreshing(false);
                            if (!pageToken.equals("0")) {
                                mAdapter.loadMoreFail();
                            }
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefresh.setRefreshing(false);
                        }
                    });
                }
                ResponseBody responseBody = response.body();
                String tabJson = responseBody.string();
                try {
                    final JSONObject object = new JSONObject(tabJson);
                    if (object.optInt("status") == 0) {
                        JSONObject data = object.optJSONObject("data");
                        if (data != null) {
                            JSONArray articles = data.optJSONArray("articles");
                            final List<MultipleBeanBase> list = new ArrayList<MultipleBeanBase>();
                            for (int i = 0; i < articles.length(); i++) {
                                JSONObject article = articles.getJSONObject(i);
                                if (article != null) {
                                    list.add(new MultipleBeanBase(article.optInt("style_type"), article));
                                    if (i == 0) {
                                        pageToken = article.optString("page_token");
                                    }
                                }
                            }

                            if (mActivity == null) {
                                return;
                            }
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.loadMoreComplete();
                                    initTabViewpager(list);
                                }
                            });
                        }
                    } else {
                        Log.e(TAG, "onResponse: " + object.toString());
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.loadMoreFail();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static final String TAG = "HomeTabFragment";

    private void initTabViewpager(List<MultipleBeanBase> list) {
        Log.e(TAG, "initTabViewpager: " + list.size());
        if (isPullToRefresh) {
            isPullToRefresh = false;
            mAdapter.setNewData(list);
        } else {
            mAdapter.addData(list);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

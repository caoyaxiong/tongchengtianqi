package com.tongchengtianqi.zuitoutiao.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

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
import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.adapter.VideoItemQuickAdapter;
import com.tongchengtianqi.zuitoutiao.base.BaseFragment;
import com.tongchengtianqi.zuitoutiao.entity.VideoFragmentBean;
import com.tongchengtianqi.zuitoutiao.http.Urls;
import com.tongchengtianqi.zuitoutiao.utils.OkHttpManager;
import com.tongchengtianqi.zuitoutiao.utils.ToastUtils;
import com.tongchengtianqi.zuitoutiao.view.SpaceItemDecoration;

public class VideoTabFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout mVideoSwipeRefresh;
    private RecyclerView mVideoFragmentRecyclerView;
    private String pageToken = "0";
    private String mVideoUrl;
    private boolean isPullToRefresh;
    private VideoItemQuickAdapter mVideoAdapter;
    private int cid;
    private JSONArray articles;
    private JSONObject article;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.recyclerview_item_video_videoview;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("asdf", "setUserVisibleHint: " );
        if (isVisibleToUser && mVideoFragmentRecyclerView != null) {
            if (mVideoAdapter.getData().size() == 0) {
                mVideoSwipeRefresh.setRefreshing(true);
                initData();
            }
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Log.e("tag", "initView: "+"");
        mVideoSwipeRefresh = view.findViewById(R.id.video_swipe_refresh);
        mVideoFragmentRecyclerView = view.findViewById(R.id.video_fragment_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mVideoFragmentRecyclerView.setLayoutManager(mLinearLayoutManager);

        mVideoAdapter = new VideoItemQuickAdapter(mActivity, article);
        mVideoFragmentRecyclerView.setAdapter(mVideoAdapter);
        //设置预加载条目
        mVideoAdapter.setPreLoadNumber(10);
        mVideoAdapter.setOnLoadMoreListener(this, mVideoFragmentRecyclerView);
        //设置item间距，20px
        mVideoFragmentRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        //添加item之间的分割线
        mVideoFragmentRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                LinearLayoutManager.VERTICAL));
        Bundle bundle = getArguments();
        if (bundle != null) {
            cid = bundle.getInt("cid");
        }

    }
    public void setCid(int cid) {
        this.cid = cid;
        pageToken = "0";
        isPullToRefresh = true;
        initData();
    }

    @Override
    protected void initListener() {
        mVideoSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (TextUtils.isEmpty(pageToken)) {
                    pageToken = "1";
                }
                isPullToRefresh = true;
                initData();
            }
        });
        mVideoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.show(mActivity, "展示数据", 2000);
            }
        });
    }

    @Override
    public void initData() {

        int index = 1;
        final String articleId = "0";
        long createTime = 0;
        int cid = 2;
        if (TextUtils.isEmpty(pageToken)) {
            pageToken = "2";
        }
        mVideoUrl = Urls.INFORMATION_DATA + "?" + "app_id=" + index + "&" +
                "cid=" + cid + "&" + "pageToken=" + pageToken + "&" + "articleId=" + articleId
                + "&" + "createTime=" + createTime;

        OkHttpManager.enqueueAsync(mVideoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mActivity != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(mActivity, "获取数据失败", 2000);
                            mVideoSwipeRefresh.setRefreshing(false);
                            if (!pageToken.equals("0")) {
                                mVideoAdapter.loadMoreFail();
                            }
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("aqwe", "onResponse: " );
                if (mVideoSwipeRefresh != null && mVideoSwipeRefresh.isRefreshing()) {
                    mVideoSwipeRefresh.post(new Runnable() {
                        @Override
                        public void run() {
                            mVideoSwipeRefresh.setRefreshing(false);
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
                            articles = data.optJSONArray("articles");
                            final List<VideoFragmentBean> list = new ArrayList<VideoFragmentBean>();
                            for (int i = 0; i < articles.length(); i++) {
                                article = articles.getJSONObject(i);
                                if (article != null) {
                                    list.add(new VideoFragmentBean());
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
                                    mVideoAdapter.loadMoreComplete();
                                    initTabViewpager(list);
                                }
                            });
                        }
                    } else {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVideoAdapter.loadMoreFail();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initTabViewpager(List<VideoFragmentBean> list) {
        if (isPullToRefresh) {
            isPullToRefresh = false;
            mVideoAdapter.setNewData(list);
        } else {
            mVideoAdapter.addData(list);
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }

}
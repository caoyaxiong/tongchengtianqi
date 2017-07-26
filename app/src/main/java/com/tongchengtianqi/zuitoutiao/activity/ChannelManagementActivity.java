package com.tongchengtianqi.zuitoutiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.base.BaseActivity;
import com.tongchengtianqi.zuitoutiao.easytagdragview.EasyTipDragView;
import com.tongchengtianqi.zuitoutiao.easytagdragview.bean.SimpleTitleTip;
import com.tongchengtianqi.zuitoutiao.easytagdragview.bean.Tip;
import com.tongchengtianqi.zuitoutiao.easytagdragview.widget.TipItemView;
import com.tongchengtianqi.zuitoutiao.entity.TabTextBean;
import com.tongchengtianqi.zuitoutiao.model.TipDataModel;
import com.tongchengtianqi.zuitoutiao.utils.JsonUtils;
import com.tongchengtianqi.zuitoutiao.utils.SpUtils;

import static com.tongchengtianqi.zuitoutiao.R.id.search_back;


public class ChannelManagementActivity extends BaseActivity implements View.OnClickListener {

    private EasyTipDragView easyTipDragView;
    private LinearLayout mSearchBack;
    private ArrayList<String> completeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_easytipdragview);
        initTitleView();
        initView();
        initListener();
    }

    @Override
    protected void initView() {

        easyTipDragView = (EasyTipDragView) findViewById(R.id.easy_tip_drag_view);
        mSearchBack = (LinearLayout) findViewById(search_back);
        ImageView viewById = (ImageView) findViewById(R.id.tag_view_delete);
        Intent intent = getIntent();
        if(intent != null){
            String tabName = intent.getStringExtra("tabName");
            if(TextUtils.isEmpty(tabName)){
                finish();
                return;
            }
            List<String> tab_list = SpUtils.getStrListValue(this, "tab_list");
            TabTextBean mTabText = JsonUtils.json2Bean(tabName, TabTextBean.class);
            List<TabTextBean.DataBean.ChannelsBean> channels = mTabText.getData().getChannels();

            List<Tip> addTips = TipDataModel.getAddTips(channels ,tab_list);
            //设置可以添加的标签数据
            easyTipDragView.setAddData(addTips);


            //设置已包含的标签数据
            easyTipDragView.setDragData(tab_list.size() == 0 ? new ArrayList<Tip>() : TipDataModel.getStringAddTips(tab_list));
        }


        //在easyTipDragView处于非编辑模式下点击item的回调（编辑模式下点击item作用为删除item）
        easyTipDragView.setSelectedListener(new TipItemView.OnSelectedListener() {
            @Override
            public void onTileSelected(Tip entity, int position, View view) {
               // toast(((SimpleTitleTip) entity).getTip());
                Intent data = new Intent();
                data.putExtra("tab",((SimpleTitleTip) entity).getTip());
                setResult(50,data);
                finish();
            }
        });
        //设置每次数据改变后的回调（例如每次拖拽排序了标签或者增删了标签都会回调）
        easyTipDragView.setDataResultCallback(new EasyTipDragView.OnDataChangeResultCallback() {
            @Override
            public void onDataChangeResult(ArrayList<Tip> tips) {
                completeList = new ArrayList<String>();
                for (int i = 0; i < tips.size(); i++) {
                    Tip tip = tips.get(i);
                    if(tip instanceof SimpleTitleTip){
                        SimpleTitleTip titleTip = (SimpleTitleTip) tip;
                        completeList.add(titleTip.getTip());
                    }
                }
                Log.e("HomeFragment", "onDataChangeResult: "+completeList.size());
            }
        });
        //设置点击“确定”按钮后最终数据的回调
        easyTipDragView.setOnCompleteCallback(new EasyTipDragView.OnCompleteCallback() {
            @Override
            public void onComplete(ArrayList<Tip> tips) {

                List<String> list = new ArrayList<String>();
                for (int i = 0; i < tips.size(); i++) {
                    Tip tip = tips.get(i);
                    if(tip instanceof SimpleTitleTip){
                        SimpleTitleTip titleTip = (SimpleTitleTip) tip;
                        list.add(titleTip.getTip());
                    }
                }
                SpUtils.putStrListValue(ChannelManagementActivity.this,"tab_list",list);
            }
        });
        //显示EasyTagDragView
        easyTipDragView.open();
    }

    @Override
    protected void initListener() {
        mSearchBack.setOnClickListener(this);
    }

    private static final String TAG = "ChannelManagementActivity";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case search_back:
                easyTipDragView.close();
                Intent intent = new Intent();
                Log.e(TAG, String.valueOf("onClick: "+completeList== null));
                intent.putStringArrayListExtra("completeList",completeList);
                setResult(40,intent);
                finish();
                break;
        }
    }

    public void toast(String str) {
        Toast.makeText(ChannelManagementActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //点击返回键
            case KeyEvent.KEYCODE_BACK:
                //判断easyTipDragView是否已经显示出来
                if (easyTipDragView.isOpen()) {
                    if (!easyTipDragView.onKeyBackDown()) {
                        //btn.setVisibility(View.VISIBLE);
                        finish();
                    }
                    return true;
                }
                //....自己的业务逻辑

                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}

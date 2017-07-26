package com.tongchengtianqi.zuitoutiao;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongchengtianqi.zuitoutiao.base.BaseActivity;
import com.tongchengtianqi.zuitoutiao.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    private long clickTime = 0;
    private TabLayout mBottomTabLayout;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragments = MainFragment.getFragments();
        getSupportFragmentManager().beginTransaction().add(R.id.content, mFragments[0]).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.content, mFragments[1]).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.content, mFragments[2]).commit();
        getSupportFragmentManager().beginTransaction().show(mFragments[0]).commit();
        getSupportFragmentManager().beginTransaction().hide(mFragments[1]).commit();
        getSupportFragmentManager().beginTransaction().hide(mFragments[2]).commit();
        initTitleView();
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        mBottomTabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);
        /*通过setOnTabSelectedListener设置一个监听器来响应选项卡的选择状态*/
        mBottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());
                /*Tab 选中之后，改变各个Tab的状态*/
                for (int i = 0; i < mBottomTabLayout.getTabCount(); i++) {
                    View view = mBottomTabLayout.getTabAt(i).getCustomView();
                    ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
                    TextView text = (TextView) view.findViewById(R.id.tab_content_text);
                    if (i == tab.getPosition()) { // 选中状态
                        icon.setImageResource(MainFragment.mTabResPressed[i]);
                        text.setTextColor(getResources().getColor(R.color.green));
                        text.setText(MainFragment.mTabTitleSelect[i]);
                    } else {// 未选中状态
                        icon.setImageResource(MainFragment.mTabRes[i]);
                        text.setTextColor(getResources().getColor(R.color.black));
                        text.setText(MainFragment.mTabTitle[i]);

                    }
                }
            }

            /*未选中的时候调用*/
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            //再次选中时调用
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int mTabPosition = tab.getPosition();
                switch (mTabPosition) {
                    case 0:
                        //刷新首页数据
                        Toast.makeText(MainActivity.this, "刷新首页数据", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //刷新video页数据
                        Toast.makeText(MainActivity.this, "刷新video页数据", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        //刷新个人中心数据
                        Toast.makeText(MainActivity.this, "刷新个人中心数据", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        /*提供自定义的布局添加Tab*/
        for (int i = 0; i < 3; i++) {
            mBottomTabLayout.addTab(mBottomTabLayout.newTab().setCustomView(MainFragment
                    .getTabView(this, i)));
        }

    }

    @Override
    protected void initListener() {

    }

    //选择tab时调用
    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:

                getSupportFragmentManager().beginTransaction().show(mFragments[0]).commit();
                getSupportFragmentManager().beginTransaction().hide(mFragments[1]).commit();
                getSupportFragmentManager().beginTransaction().hide(mFragments[2]).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().show(mFragments[1]).commit();
                getSupportFragmentManager().beginTransaction().hide(mFragments[0]).commit();
                getSupportFragmentManager().beginTransaction().hide(mFragments[2]).commit();
                break;

            case 2:
                getSupportFragmentManager().beginTransaction().show(mFragments[2]).commit();
                getSupportFragmentManager().beginTransaction().hide(mFragments[0]).commit();
                getSupportFragmentManager().beginTransaction().hide(mFragments[1]).commit();
                break;
        }
    }

    //点击返回键时调用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出应用程序
    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再次点击退出", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
            System.exit(0);
        }

    }

}

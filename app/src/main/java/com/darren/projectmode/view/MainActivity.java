package com.darren.projectmode.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.darren.projectmode.R;
import com.darren.projectmode.adapter.TabsAdapter;
import com.darren.projectmode.app.IApplication;
import com.darren.projectmode.fragment.HomeFragment;
import com.darren.projectmode.fragment.MineFragment;
import com.darren.projectmode.fragment.OrderFragment;
import com.darren.projectmode.tools.ToolStatusBar;
import com.darren.projectmode.tools.ToolToast;
import com.darren.projectmode.widget.UITabWidget;

import java.lang.ref.WeakReference;

/**
 * 主页面
 * Created by ZENG DONG YANG on 2016/8/25.
 * e-mail:zengdongyang@incamel.com
 */
public class MainActivity extends FragmentActivity implements UITabWidget.OnItemChangedListener {
    private UITabWidget mTabWidget;
    private TabsAdapter mTabsAdapter;
    private FragmentManager manager;
    private int curPosition = 0;
    private long mExitTime = 0;
    private View mContentView;

    private WeakReference<Activity> context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = ToolStatusBar.setSatusBar(this, R.layout.activity_main, R.color.circle_color, false);
        setContentView(mContentView);

        context = new WeakReference<Activity>(this);
        IApplication.pushTask(context);

        mTabWidget = (UITabWidget) findViewById(R.id.maintab_view);
        mTabWidget.setOnItemChangedListener(this);
        manager = getSupportFragmentManager();
        mTabsAdapter = new TabsAdapter(this);

        createTabView();// 加载Tab界面

        if (savedInstanceState == null) {// 默认选中第一项Tab
            mTabWidget.setChecked(0);
        } else {
            int p = savedInstanceState.getInt("tabPosition", 0);
            mTabWidget.setChecked(p);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt("tabPosition", curPosition);
    }

    /*
     * 动态初始化内容界面
     */
    private void createTabView() {
        int position = 0;
        addTabSpec(UITabWidget.WIDGET_WHAT_HOME, position++);
        addTabSpec(UITabWidget.WIDGET_WHAT_ORDER, position++);
        addTabSpec(UITabWidget.WIDGET_WHAT_MINE, position++);
        mTabsAdapter.notifyDataSetChanged();
    }

    /*
     * 添加选项卡界面
     */
    private void addTabSpec(String key, int position) {
        int textId = 0, resId = 0, msgCount = 0;
        if (key.equals(UITabWidget.WIDGET_WHAT_HOME)) { // 首页
            resId = R.drawable.btn_home_selector;
            textId = R.string.tab_home_text;
            msgCount = 0;
            mTabsAdapter.addTab(HomeFragment.class);
        } else if (key.equals(UITabWidget.WIDGET_WHAT_ORDER)) { // 订单
            resId = R.drawable.btn_order_selector;
            textId = R.string.tab_order_text;
            msgCount = 2;
            mTabsAdapter.addTab(OrderFragment.class);
        } else if (key.equals(UITabWidget.WIDGET_WHAT_MINE)) { // 我的
            resId = R.drawable.btn_mine_selector;
            textId = R.string.tab_mine_text;
            msgCount = 3;
            mTabsAdapter.addTab(MineFragment.class);
        }
        mTabWidget.addTabView(resId, textId, msgCount, position);
    }

    @Override
    public void onChanged(int position) {
        switchFrament(position);
    }

    public void switchFrament(int position) {
        curPosition = position;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_pager, mTabsAdapter.getItem(position));
        transaction.commitAllowingStateLoss();
    }

    /**
     * 手机上的物理返回按钮
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis();
                ToolToast.showShort(this, "再按一次退出程序");
            } else {
                IApplication.removeAll();
            }
            return true;
        }
        return false;
    }

    public void changeToHome() {

    }
}

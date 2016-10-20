package com.darren.projectmode.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.darren.projectmode.app.IApplication;
import com.darren.projectmode.tools.ToolStatusBar;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * android 系统中的四大组件之一Activity基类
 * <p>
 * Created by ZENG DONG YANG on 2016/8/26.
 * e-mail:zengdongyang@incamel.com
 *
 * @version 1.0
 */
public abstract class BaseActivity extends Activity implements IBaseActivity {
    /**
     * 当前Activity渲染的视图View
     **/
    protected View mContextView;
    /**
     * 当前Activity的弱引用，防止内存泄露
     **/
    private WeakReference<Activity> context = null;

    protected IApplication iApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContextView = ToolStatusBar.setSatusBar(this, bindLayout(), setStatusColor(), setStatusTextColor());
        setContentView(mContextView);
        ButterKnife.bind(this);
        iApplication = (IApplication) getApplicationContext();
        // 将当前Activity压入栈
        context = new WeakReference<Activity>(this);
        iApplication.pushTask(context);
        initView(mContextView);
        // 业务操作
        doBusiness(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        destroy();
        super.onDestroy();
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    protected Activity getContext() {
        if (null != context)
            return context.get();
        else
            return null;
    }

}

package com.darren.projectmode.fragment;

import android.content.Context;
import android.view.View;

import com.darren.loglibs.ToolLog;
import com.darren.projectmode.R;
import com.darren.projectmode.base.BaseFragment;

/**
 * Created by ZENG DONG YANG on 2016/8/25.
 * e-mail:zengdongyang@incamel.com
 */
public class OrderFragment extends BaseFragment {

    @Override
    public int bindLayout() {
        ToolLog.d(getClass().getSimpleName(), "OrderFragment");
        return R.layout.fragment_order;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}

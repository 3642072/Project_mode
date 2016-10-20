package com.darren.projectmode.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * <p/>
 * Created by ZENG DONG YANG on 2016/8/26.
 * e-mail:zengdongyang@incamel.com
 *
 * @version 1.0
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {

    /**
     * 当前Fragment渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //渲染视图View(防止切换时重绘View)
        if (null != mContextView) {
            ViewGroup parent = (ViewGroup) mContextView.getParent();
            if (null != parent) {
                parent.removeView(mContextView);
            }
        } else {
            mContextView = inflater.inflate(bindLayout(), container, false);
            ButterKnife.bind(this, mContextView);//绑定framgent
            // 控件初始化
            initView(mContextView);
        }


        //业务处理
        doBusiness(getActivity());

        return mContextView;
    }


    /**
     * 获取当前Fragment依附在的Activity
     *
     * @return
     */
    protected Activity getContexts() {
        return getActivity();
    }

}

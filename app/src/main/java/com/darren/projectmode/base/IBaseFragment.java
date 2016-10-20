package com.darren.projectmode.base;

import android.content.Context;
import android.view.View;

/**
 * Fragment接口
 *
 * Created by ZENG DONG YANG on 2016/8/26.
 * e-mail:zengdongyang@incamel.com
 * @version 1.0
 */
public interface IBaseFragment {

    /**
     * 绑定渲染视图的布局文件
     *
     * @return 布局文件资源id
     */
    int bindLayout();

    /**
     * 初始化控件
     */
    void initView(final View view);

    /**
     * 业务处理操作（onCreateView方法中调用）
     *
     * @param mContext 当前Activity对象
     */
    void doBusiness(Context mContext);

}

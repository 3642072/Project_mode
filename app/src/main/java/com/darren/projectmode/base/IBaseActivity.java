package com.darren.projectmode.base;

import android.content.Context;
import android.view.View;

/**
 * Activity接口
 * <p>
 * Created by ZENG DONG YANG on 2016/8/26.
 * e-mail:zengdongyang@incamel.com
 *
 * @version 1.0
 */
public interface IBaseActivity {

    /**
     * 绑定渲染视图的布局文件
     *
     * @return 布局文件资源id
     */
    int bindLayout();

    /**
     * 状态栏背景颜色
     *
     * @return
     */
    int setStatusColor();

    /**
     * 状态栏字体颜色
     *
     * @return true：黑；false：白
     */
    boolean setStatusTextColor();

    /**
     * 初始化控件
     */
    void initView(final View view);

    /**
     * 业务处理操作（onCreate方法中调用）
     *
     * @param mContext 当前Activity对象
     */
    void doBusiness(Context mContext);

    /**
     * 暂停恢复刷新相关操作（onResume方法中调用）
     */
    void resume();

    /**
     * 销毁、释放资源相关操作（onDestroy方法中调用）
     */
    void destroy();

}

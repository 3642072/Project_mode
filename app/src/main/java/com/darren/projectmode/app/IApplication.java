package com.darren.projectmode.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.darren.loglibs.ToolLog;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * IApplication
 * Created by ZENG DONG YANG on 2016/8/26.
 * e-mail:zengdongyang@incamel.com
 */
public class IApplication extends Application {
    /***
     * 寄存整个应用Activity
     **/
    private static final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();
    /**
     * 对外提供整个应用生命周期的Context
     **/
    private static Application instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ToolLog.init(true, "Visa");
        ToolLog.d("PayApplication onCreate");
    }

    public static Application getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getBaseContext();
    }

    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    public static void pushTask(WeakReference<Activity> task) {
        activitys.push(task);
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    public static void removeTask(WeakReference<Activity> task) {
        activitys.remove(task);
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    public static void removeTask(int taskIndex) {
        if (activitys.size() > taskIndex)
            activitys.remove(taskIndex);
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    public static void removeToTop() {
        int end = activitys.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            if (!activitys.get(i).get().isFinishing()) {
                activitys.get(i).get().finish();
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    public static void removeAll() {
        // finish所有的Activity
        for (WeakReference<Activity> task : activitys) {
            if (!task.get().isFinishing()) {
                task.get().finish();
            }
        }
    }
}

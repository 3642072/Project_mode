package com.darren.projectmode.tools;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by ZENG DONG YANG on 2016/9/20.
 * e-mail:zengdongyang@incamel.com
 */
public class ToolStatusBar {
    /**
     * 添加Status状态栏
     *
     * @param context        上下文
     * @param layout         布局资源ID
     * @param statusColor    状态栏颜色
     * @param lightStatusBar 状态栏字体颜色 true：黑 false：白
     * @return
     */
    public static View setSatusBar(Activity context, int layout, int statusColor, boolean lightStatusBar) {
        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        ImageView iv = new ImageView(context);
        iv.setImageResource(statusColor);
        LinearLayout.LayoutParams statusParams;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = context.getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(context));
            // 设置浅色状态栏时的界面显示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decor = window.getDecorView();
                int ui = decor.getSystemUiVisibility();
                if (lightStatusBar) {
                    ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decor.setSystemUiVisibility(ui);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                StatusBarCompatFlavorRom.setLightStatusBar(window, lightStatusBar);
            }
        } else {
            statusParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        }
        rootLayout.addView(iv, statusParams);
        View mContextView = LayoutInflater.from(context).inflate(layout, null);
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.addView(mContextView, contentParams);
        return rootLayout;
    }


    public static int getStatusBarHeight(Activity context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.e("sbar", "get status bar height fail");
            e1.printStackTrace();
        }
        return sbar;
    }
}

package com.darren.projectmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.darren.loglibs.ToolLog;
import com.darren.projectmode.R;

import java.util.ArrayList;
import java.util.List;

import static com.darren.projectmode.config.SysEnv.getDisplayMetrics;


/**
 * Created by ZENG DONG YANG on 2016/8/26.
 * e-mail:zengdongyang@incamel.com
 */
public class UITabWidget extends RadioGroup implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final String WIDGET_WHAT_HOME = "home"; // 首页
    public static final String WIDGET_WHAT_ORDER = "order"; // 订单
    public static final String WIDGET_WHAT_MINE = "mine"; // 我的
    private LayoutInflater mInflater;
    private OnItemChangedListener mOnItemChangedListener;
    private static List<BadgeView> badgeViewList;
    private static List<RadioButton> radioButtonList;

    public UITabWidget(Context context) {
        this(context, null);
    }

    public UITabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(HORIZONTAL);
        mInflater = LayoutInflater.from(context);
        badgeViewList = new ArrayList<>();
        radioButtonList = new ArrayList<>();
    }

    public void addTabView(int resId, int textId, int msgCount, int position) {
        RadioButton button = (RadioButton) mInflater.inflate(
                R.layout.main_tab_widget_layout, this, false);
        button.setText(textId);
        button.setTag(position);
        button.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
        button.setOnCheckedChangeListener(this);
        button.setOnClickListener(this);
        addView(button);
        radioButtonList.add(button);

        BadgeView badge = new BadgeView(this.getContext());// 创建一个BadgeView对象，view为你需要显示提醒的控件
        badge.setTargetView(button);
        badge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badge.setBadgeMargin(0, 3, getDisplayMetrics().widthPixels / 3 / 9, 0);
        badgeViewList.add(badge);
        setMsgCount(position, msgCount);
    }


    /**
     * 设置显示的信息个数
     *
     * @param position 索引
     * @param msgCount 信息个数
     */
    public static void setMsgCount(int position, int msgCount) {
        if (position < badgeViewList.size()) {
            BadgeView badgeView = badgeViewList.get(position);
            if (msgCount > 0) {
                badgeView.setBadgeCount(msgCount);
                badgeView.setVisibility(VISIBLE);
            } else {
                badgeView.setVisibility(GONE);
            }
        }
    }

    /**
     * 设置选中选项
     *
     * @param position 索引
     */
    public void setChecked(int position) {
        if (position < radioButtonList.size())
            for (int i = 0; i < radioButtonList.size(); i++) {
                RadioButton button = (RadioButton) radioButtonList.get(i).findViewById(R.id.rb_item);
                if (i == position) {
                    button.setChecked(true);
                    button.setSelected(true);
                } else {
                    button.setChecked(false);
                    button.setSelected(false);
                }
            }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked && mOnItemChangedListener != null) {
            String tag = buttonView.getTag().toString();
            int position = Integer.parseInt(tag);
            mOnItemChangedListener.onChanged(position);
            ToolLog.d(getClass().getSimpleName(), "onCheckedChanged");
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null && mOnItemChangedListener != null) {
            String tag = v.getTag().toString();
            int position = Integer.parseInt(tag);
            setChecked(position);
            ToolLog.d(getClass().getSimpleName(), "onClick");
        }
    }

    /**
     * 设置监听事件
     */
    public void setOnItemChangedListener(
            OnItemChangedListener onItemChangedListener) {
        this.mOnItemChangedListener = onItemChangedListener;
    }


    public interface OnItemChangedListener {

        void onChanged(int position);
    }
}

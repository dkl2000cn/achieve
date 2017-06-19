package com.easygoal.achieve;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.CalendarView;

/**
 * 项目名称：achieve
 * 类描述：
 * 创建人：Acer
 * 创建时间：2017/6/5 0:40
 * 修改人：Acer
 * 修改时间：2017/6/5 0:40
 * 修改备注：
 */

public class mCalendarView extends CalendarView {
    public mCalendarView(@NonNull Context context) {
        super(context);
    }

    public mCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public mCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}

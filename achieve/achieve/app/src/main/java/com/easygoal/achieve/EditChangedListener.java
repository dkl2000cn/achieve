package com.easygoal.achieve;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

/**
 * Created by Acer on 2017/5/13.
 */

public class EditChangedListener implements TextWatcher {
    private Context context;
    public EditChangedListener(Context context) {
        this.context=context;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s.toString())) {
            try {
                if (TimeData.convertTime_YYYYTIMEtoYYTIME(s.toString()) != null) {

                } else {
                    Toast.makeText(context, "日期格式输入有误", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Toast.makeText(context, "日期输入正确", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

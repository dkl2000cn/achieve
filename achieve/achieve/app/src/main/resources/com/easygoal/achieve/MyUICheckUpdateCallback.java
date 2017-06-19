package com.easygoal.achieve;

import android.widget.Toast;

import com.baidu.autoupdatesdk.UICheckUpdateCallback;

/**
 * 项目名称：achieve
 * 类描述：
 * 创建人：Acer
 * 创建时间：2017/5/30 19:46
 * 修改人：Acer
 * 修改时间：2017/5/30 19:46
 * 修改备注：
 */

class MyUICheckUpdateCallback implements UICheckUpdateCallback {
    public MyUICheckUpdateCallback() {
    }

    @Override
    public void onNoUpdateFound() {
        LogUtils.d("no update");
    }

    @Override
    public void onCheckComplete() {
        LogUtils.d("check complete");

    }
}

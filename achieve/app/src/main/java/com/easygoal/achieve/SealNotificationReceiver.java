package com.easygoal.achieve;

import android.content.Context;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by Acer on 2017/4/23.
 */

public class SealNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
        //TaskTool.notiSend(context,"ddfdf","ssss",5000);
        return false; // ���� false, �ᵯ������ SDK Ĭ��֪ͨ; ���� true, ���� SDK ���ᵯ֪ͨ, ֪ͨ��Ҫ�����Զ��塣
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        return false; // ���� false, �������� SDK Ĭ�ϴ����߼�, �������֪ͨ��򿪻Ự�б��Ự����; ���� true, �������Զ��崦���߼���
    }

}

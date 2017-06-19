package com.easygoal.achieve;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MPAppCompatActivity1 extends BaseActivity {
    private final String TAG = "MPermissions";
    private int REQUEST_CODE_PERMISSION = 0x00099;

    /**
     * 请求权限
     *
     * @param permissions
     * @param requestCode
     */
    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        Toast.makeText(getApplicationContext(),""+permissions.length,Toast.LENGTH_SHORT).show();
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);

        } else {
            //Toast.makeText(getApplicationContext(),"no"+permissions.length,Toast.LENGTH_SHORT).show();
            //List<String> needPermissions = getDeniedPermissions(permissions);
           // List<String> needPermissions = getDeniedPermissions(permissions);
            //ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION);
            //Toast.makeText(getApplicationContext(),"request"+permissions.length,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return false;
    }

    /**
     *
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    /**
     *
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }

    /**
     *
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话�?
     */
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("��ʾ��Ϣ")
                .setMessage("��ǰӦ��ȱ�ٱ�ҪȨ�ޣ��ù�����ʱ�޷�ʹ�á�������Ҫ���뵥��???ȷ��???��ťǰ??�������Ľ���Ȩ����Ȩ??")
                .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    /**
     * ������ǰӦ������ҳ��
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * ��ȡȨ�޳ɹ�
     *
     * @param requestCode
     */
    public void permissionSuccess(int requestCode) {
        Log.d(TAG, "��ȡȨ�޳ɹ�=" + requestCode);

    }

    /**
     * Ȩ�޻�ȡʧ��
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
        Log.d(TAG, "��ȡȨ��ʧ��=" + requestCode);
    }

    @Override
    public void dobusiness() {

    }

    @Override
    public void bindViews() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
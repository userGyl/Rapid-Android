package com.rapid.gyl.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

import com.blankj.utilcode.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by gyl on 2020/9/1.
 */
public class BaseAct extends AppCompatActivity {

    private static final String TAG_OFFSET = "TAG_OFFSET";
    private static final int KEY_OFFSET = -123;

    private PermissionsListener mListener;

    /**
     * 为view添加状态栏的高度 用于适配
     *
     * @param view
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

            return;
        }
        view.setTag(TAG_OFFSET);
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) {

            return;
        }
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin + getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, true);
    }

    private static int getStatusBarHeight() {
        Resources resources = Utils.getApp().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");

        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 申请权限
     *
     * @param permissions 权限列表
     * @param listener    授权回调
     */
    public void requestPermissions(String[] permissions, PermissionsListener listener) {
        mListener = listener;
        List<String> requestPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions.add(permission);
            }
        }
        if (!requestPermissions.isEmpty() && Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, requestPermissions.toArray(new String[requestPermissions.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mListener != null) {
            switch (requestCode) {
                case 1:
                    List<String> deniedPermissions = new ArrayList<>();
                    //当所有拒绝的权限都勾选不再询问，这个值为true,这个时候可以引导用户手动去授权。
                    boolean isNeverAsk = true;
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            deniedPermissions.add(permissions[i]);
                            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) { // 点击拒绝但没有勾选不再询问
                                isNeverAsk = false;
                            }
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        try {
                            mListener.onGranted();
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                            mListener.onDenied(Arrays.asList(permissions), true);
                        }
                    } else {
                        mListener.onDenied(deniedPermissions, isNeverAsk);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void showAskDialog(String content) {
        new AlertDialog.Builder(this).setMessage(content)
                .setNegativeButton("取消", null)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);

                    }
                }).create().show();
    }


    public void showAskDialog() {
        showAskDialog("应用需要的一些权限被您拒绝，请您到设置页面手动授权哦~");
    }

}

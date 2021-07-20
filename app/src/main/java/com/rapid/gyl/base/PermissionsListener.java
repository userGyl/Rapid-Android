package com.rapid.gyl.base;

import java.util.List;

/**
 * Created by gyl on 2021-07-20.
 */
public interface PermissionsListener {
    void onGranted();

    void onDenied(List<String> deniedPermissions, boolean isNeverAsk);
}

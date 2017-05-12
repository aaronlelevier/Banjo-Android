package com.bwldr.banjo.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import static java.security.AccessController.getContext;

/**
 * Static class function object used to check required permissions
 */
public class PermissionUtil {

    private PermissionUtil(){
        throw new AssertionError("Static class so can't be instantiated");
    }

    /**
     * Return true if all permissions have been granted
     *
     * @param context activity
     * @param permissions manifest permissions to check
     * @return boolean
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext() != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}

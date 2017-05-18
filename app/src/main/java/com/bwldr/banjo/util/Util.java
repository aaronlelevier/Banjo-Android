package com.bwldr.banjo.util;

import android.app.Activity;
import android.widget.Toast;

/**
 * General Util static class to be used throughout the app
 */
public class Util {

    private Util() {
        throw new AssertionError("This is a static class");
    }

    public static void showToast(final Activity activity, final String text) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

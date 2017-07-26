package com.tongchengtianqi.zuitoutiao.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import com.tongchengtianqi.zuitoutiao.R;


public class SystemTintBarUtils {
        public static void setSystemBarColor(Activity activity) {
            setSystemBarColor(activity, R.color.title);
        }

        public static void setSystemBarColor(Activity activity, int colorId) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setNavigationBarTintEnabled(true);
                tintManager.setStatusBarTintResource(colorId);
                tintManager.setNavigationBarTintResource(R.color.black);
            }
        }

        @TargetApi(19)
        private static void setTranslucentStatus(Activity activity, boolean on) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
}

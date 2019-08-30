package com.csp.cache.util;

import android.util.Log;
import com.csp.cache.BuildConfig;

/**
 * Log日志工具类，Debug模式下输出，Releas模式下关闭输出
 */
public class LogUtil {

    public static String tag = "kkk";
    public static boolean isDebug = BuildConfig.DEBUG;

    public static void v(String content) {
        if (isDebug)
            Log.v(tag, content);
    }

    public static void d(String content) {
        if (isDebug)
            Log.d(tag, content);
    }

    public static void i(String content) {
        if (isDebug)
            Log.i(tag, content);
    }

    public static void w(String content) {
        if (isDebug)
            Log.w(tag, content);
    }

    public static void e(String content) {
        if (isDebug)
            Log.e(tag, content);
    }
}

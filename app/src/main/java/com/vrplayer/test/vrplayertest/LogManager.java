package com.vrplayer.test.vrplayertest;

import android.util.Log;

/**
 * Created by userd on 2017/9/20.
 */

public class LogManager {


    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }


    public static void v(String tag, String msg, Throwable tr) {
        Log.v(tag, msg, tr);
    }

    public static void d(String tag, String msg, Throwable tr) {
        Log.v(tag, msg, tr);
    }

    public static void i(String tag, String msg, Throwable tr) {
        Log.v(tag, msg, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        Log.v(tag, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.v(tag, msg, tr);
    }
}

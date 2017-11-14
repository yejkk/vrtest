package com.vrplayer.test.vrplayertest.listener;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by userd on 2017/9/25.
 */

public class MainHandler {

    private static Handler sMainHandler;

    public static void init(){
        if (sMainHandler == null){
            sMainHandler = new Handler(Looper.getMainLooper());
        }
    }

    public static Handler sharedHandler(){
        return sMainHandler;
    }
}

package com.vrplayer.test.vrplayertest.socket;

import android.os.Handler;

import com.vrplayer.test.vrplayertest.LogManager;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by userd on 2017/9/20.
 */

public class VRDeviceConnect extends NanoHTTPD {

    public final static String TAG = "VRDeviceConnect";
    public static int port = 9099;

    private Handler mHandler = new Handler();
    private static VRDeviceConnect mVRDeviceConnect = null;

    public VRDeviceConnect(int port) {
        super(port);
    }

    public void startWork() {
        try {
            start();
        } catch (Exception e) {
            LogManager.e(TAG, "[" + e.getMessage() + "]");
            mHandler.postDelayed(startRunnable, 2000);
        }
    }

    @Override
    public Response serve(String uri, Method method, Map<String, String> header,
                          Map<String, String> parameters, Map<String, String> files) {
       /* if (!StringUtils.isEmpty(uri)
                && (uri.equals("/") || uri.equals("/index.html"))) {
            // 合法请求
        }*/
        Iterator<String> iter = parameters.keySet().iterator();
        while (iter.hasNext()) {
            String value = iter.next();
        }
        return null;
    }

    public void stopWork() {
        mVRDeviceConnect.stop();
    }

    private Runnable startRunnable = new Runnable() {

        @Override
        public void run() {
            mVRDeviceConnect = new VRDeviceConnect(++port);
            mVRDeviceConnect.startWork();
        }
    };

}

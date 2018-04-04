package com.vrplayer.test.vrplayertest.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.asha.vrlib.common.VrSenorEvent;
import com.vrplayer.test.vrplayertest.util.JSONUTIL;

/**
 * Created by userd on 2017/12/26.
 */

public class ControllerReceiver extends BroadcastReceiver {
    private static final String TAG = "ControllerReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            Bundle bundle = intent.getExtras();
            handleMsg(context, bundle.getString("content", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMsg(final Context context, String content) {
        Log.d(TAG, content);

        String action = JSONUTIL.getValueByKey(content, "action");
        String data = JSONUTIL.getValueByKey(content, "data");
        if ("sensorEvent".equalsIgnoreCase(action)) {
            String sensorContent = JSONUTIL.getValueByKey(data, "event");
            VrSenorEvent vrSenorEvent = new VrSenorEvent(JSONUTIL.getIntValueByKey(sensorContent, "accuracy"),
                    System.currentTimeMillis(),
                    converttoFloatArray(JSONUTIL.getValueByKey(sensorContent, "values")));
//            VRPlayerActivity.receive(vrSenorEvent);
        }
    }

    /*
     * 转化String数组为float[]
     */
    public static float[] converttoFloatArray(String str){
        String str1 = str.substring(1,str.length()-1);
        System.out.println(str1);
        String str2[]= str1.split(",");
        float fl[] = new float[str2.length];
        for(int i=0;i<fl.length;i++){
            fl[i] = Float.parseFloat(str2[i]);
            System.out.println(fl[i]);
        }
        return fl;
    }
}

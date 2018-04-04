package com.vrplayer.test.vrplayertest.util;

import com.vrplayer.test.vrplayertest.LogManager;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by userd on 2017/12/26.
 */

public class JSONUTIL {
    private static final String TAG = "JsonUtil";

    public static String getValueByKey(String jsonStr, String key) {
        try {
            JSONTokener jsonParser = new JSONTokener(jsonStr);
            JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
            if(jsonObj.has(key)){
                return jsonObj.getString(key);
            }
        } catch (Exception e) {

            LogManager.e(TAG, "get value by key failed: " + jsonStr);
            e.printStackTrace();
        }
        return null;
    }

    public static int getIntValueByKey(String jsonStr, String key) {
        try {
            JSONTokener jsonParser = new JSONTokener(jsonStr);
            JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
            return jsonObj.getInt(key);
        } catch (Exception e) {
            LogManager.e(TAG, "get int value by key failed: " + jsonStr);
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }
}

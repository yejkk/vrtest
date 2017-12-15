package com.asha.vrlib.strategy.interactive;

import android.content.Context;
import android.content.res.Resources;

import com.asha.vrlib.MD360Director;

/**
 * Created by yexin on 2017/11/20.
 */

public class KeyStrategy extends AbsInteractiveStrategy {

    private static final float sDensity =  Resources.getSystem().getDisplayMetrics().density;

    private static final float sDamping = 0.2f;
    private static final String TAG = "KeyStrategy";
    public KeyStrategy(InteractiveModeManager.Params params) {
        super(params);
    }

    @Override
    public void turnOnInGL(Context context) {
        for (MD360Director director : getDirectorList()){
            director.reset();
        }
    }

    @Override
    public void turnOffInGL(Context context) {

    }

    @Override
    public boolean isSupport(Context context) {
        return true;
    }

    @Override
    public void onResume(Context context) {

    }

    @Override
    public void onPause(Context context) {

    }

    @Override
    public boolean handleDrag(int distanceX, int distanceY) {
        for (MD360Director director : getDirectorList()){
            director.setDeltaX(director.getDeltaX() - distanceX / sDensity * sDamping);
            director.setDeltaY(director.getDeltaY() - distanceY / sDensity * sDamping);
        }
        return false;
    }

    @Override
    public void onOrientationChanged(Context context) {

    }
}

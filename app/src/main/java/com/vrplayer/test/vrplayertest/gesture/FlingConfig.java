package com.vrplayer.test.vrplayertest.gesture;

import android.animation.TimeInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by userd on 2017/9/21.
 */

public class FlingConfig {

    private TimeInterpolator mInterpolator = new DecelerateInterpolator();

    private long mDuring = 400;

    private float mSensitivity = 1.0f;


    public FlingConfig setInterpolator(TimeInterpolator i) {
        this.mInterpolator = i;
        return this;
    }

    /***
     * @param during in ms
     * */
    public FlingConfig setDuring(long during) {
        this.mDuring = during;
        return this;
    }

    /***
     * @param sensitivity default is 1.0f, 10.0 is faster than 0.1f
     * */
    public FlingConfig setSensitivity(float sensitivity) {
        this.mSensitivity = sensitivity;
        return this;
    }

    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }

    public long getDuring() {
        return mDuring;
    }

    public float getSensitivity() {
        return mSensitivity;
    }
}

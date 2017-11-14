package com.vrplayer.test.vrplayertest.MDVRPlayer.setting;

import com.asha.vrlib.common.MDDirection;
import com.asha.vrlib.strategy.projection.AbsProjectionStrategy;
import com.asha.vrlib.strategy.projection.IMDProjectionFactory;
import com.asha.vrlib.strategy.projection.MultiFishEyeProjection;

/**
 * Created by userd on 2017/9/18.
 */

public class CustomProjectionFactory implements IMDProjectionFactory {

    public static final int CUSTOM_PROJECTION_FISH_EYE_RADIUS_VERTICAL = 9611;

    @Override
    public AbsProjectionStrategy createStrategy(int mode) {
        switch (mode){
            case CUSTOM_PROJECTION_FISH_EYE_RADIUS_VERTICAL:
                return new MultiFishEyeProjection(0.745f, MDDirection.VERTICAL);
            default:return null;
        }
    }
}

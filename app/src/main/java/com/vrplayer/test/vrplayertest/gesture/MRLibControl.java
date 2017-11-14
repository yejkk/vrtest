package com.vrplayer.test.vrplayertest.gesture;

import android.content.Context;

import com.asha.vrlib.MDVRLibrary;


/**
 * Created by userd on 2017/9/21.
 */

public class MRLibControl {

    private MDVRLibrary mMDVRLibrary;

    public static Builder with(Context context){
        return new Builder(context);
    }
    /**
     *
     */
    public static class Builder {
        private MDVRLibrary mMDVRLibrary;
        private Context context;

        private Builder(Context context) {
            this.context = context;
        }

        public Builder setMDVRLibrary(MDVRLibrary mMDVRLibrary){
            this.mMDVRLibrary = mMDVRLibrary;
            return this;
        }

        public MRLibControl build(){
            return new MRLibControl(this);
        }
    }

    private MRLibControl(Builder builder) {
        mMDVRLibrary = builder.mMDVRLibrary;
    }
}

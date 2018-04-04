package com.asha.vrlib.strategy.interactive;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.asha.vrlib.MD360Director;
import com.asha.vrlib.common.VrSenorEvent;
import com.asha.vrlib.strategy.interactive.InteractiveModeManager.Params;

public class MobileMotionStrategy extends AbsInteractiveStrategy {

    private final Object mMatrixLock = new Object();
    private WindowManager windowManager;
    private float[] mSensorMatrix = new float[16];
    private float[] mTmpMatrix = new float[16];
    private boolean mRegistered = true;
    private Boolean mIsSupport = null;
    private boolean isOn = true;
    private float orientationX;
    private float orientationY;
    private float orientationZ;
    private Runnable updateOrientationSensorRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mRegistered || !isOn) return;
            // mTmpMatrix will be used in multi thread.
            synchronized (mMatrixLock) {
                for (MD360Director director : getDirectorList()) {
                    director.updateJsMatrix(mTmpMatrix,1);
                    /*Log.i("orientationX",""+orientationX);
                    Log.i("orientationY",""+orientationY);
                    Log.i("orientationZ",""+orientationZ);
                    director.setDeltaX( orientationX);
                    director.setDeltaY(orientationY );
                    director.setDeltaZ(orientationZ );*/
                }
            }
        }
    };

    public MobileMotionStrategy(Params params) {
        super(params);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isSupport(Context context) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onPause(Context context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResume(Context context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void turnOffInGL(Context context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void turnOnInGL(Context context) {
        // TODO Auto-generated method stub
        isOn = true;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        for (MD360Director director : getDirectorList()) {
            director.reset();
        }
    }

    @Override
    public boolean handleDrag(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onOrientationChanged(Context context) {
        // TODO Auto-generated method stub

    }

    public boolean handleDrag(VrSenorEvent vrSenorEvent) {

        /*VRUtil.getBaseRotationMatrix(mSensorMatrix, vrSenorEvent.values[2], vrSenorEvent.values[0], vrSenorEvent.values[1], Surface.ROTATION_0);

        //VRUtil.sensorRotationVector2Matrix(vrSenorEvent.values, windowManager.getDefaultDisplay().getRotation(), mSensorMatrix);
        // mTmpMatrix will be used in multi thread.
        synchronized (mMatrixLock) {
            System.arraycopy(mSensorMatrix, 0, mTmpMatrix, 0, 16);
        }*/
        // mTmpMatrix will be used in multi thread.

        //1月9号测试版本
        orientationY = (vrSenorEvent.values[1] );
        orientationX = (vrSenorEvent.values[0]*4);

//        orientationX = (vrSenorEvent.values[1]+180 );
//        orientationY = (vrSenorEvent.values[2]);
//        orientationX = (float)(vrSenorEvent.values[1] * Math.PI /180);
//        orientationY = (float)(vrSenorEvent.values[2] * Math.PI /180);
//        orientationZ = vrSenorEvent.values[2];
        for (MD360Director director : getDirectorList()) {
            Log.i("orientationX", "" + orientationX);
            Log.i("orientationY", "" + orientationY);
//            Log.i("orientationZ",""+orientationZ);
            director.setDeltaX(orientationX);
            director.setDeltaY(orientationY);
//            director.setDeltaZ(orientationZ );
        }
//        getParams().glHandler.post(updateOrientationSensorRunnable);
        return true;
    }


}

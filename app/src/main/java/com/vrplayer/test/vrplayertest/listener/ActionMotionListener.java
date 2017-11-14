package com.vrplayer.test.vrplayertest.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.WindowManager;

import com.vrplayer.test.vrplayertest.LogManager;
import com.vrplayer.test.vrplayertest.socket.MsgParcelable;
import com.vrplayer.test.vrplayertest.socket.SendSocket;

/**
 * Created by userd on 2017/9/22.
 */

public class ActionMotionListener implements SensorEventListener {

    private static final String LOG_TAG = ActionMotionListener.class.getSimpleName();

    private WindowManager windowManager;
    private SensorEventListener mSensorListener;
    private SendSocket mSendSocket;
    private float[] mSensorMatrix = new float[16];
    private boolean mRegistered = false;
    private boolean isOn = false;

    protected void registerSensor(Context context){
        if (mRegistered) return;
        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        SensorManager mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (sensor == null){
            LogManager.e(LOG_TAG,"TYPE_ROTATION_VECTOR sensor not support!");
            return;
        }

        mSensorManager.registerListener(this, sensor, 1, MainHandler.sharedHandler());

        mRegistered = true;
    }

    protected void unregisterSensor(Context context){
        if (!mRegistered) return;

        SensorManager mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.unregisterListener(this);

        mRegistered = false;
    }

    public void setOnSensorListener(SensorEventListener mSensorListener){
        this.mSensorListener = mSensorListener;
    }

    public void setSendSocketr(SendSocket mSendSocket){
        isOn = true;
        this.mSendSocket = mSendSocket;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isOn && event.accuracy != 0){
            if (this.mSensorListener != null){
                this.mSensorListener.onSensorChanged(event);
            }

            MsgParcelable mMsgParcelable = new MsgParcelable(event,windowManager.getDefaultDisplay().getRotation());
            mSendSocket.sendMsg(mMsgParcelable);
//            int type = event.sensor.getType();
//            switch (type){
//                case Sensor.TYPE_ROTATION_VECTOR:
//                    // post
//                    VRUtil.sensorRotationVector2Matrix(event, windowManager.getDefaultDisplay().getRotation(), mSensorMatrix);
//
//                    // mTmpMatrix will be used in multi thread.
//                    synchronized (mMatrixLock){
//                        System.arraycopy(mSensorMatrix, 0, mTmpMatrix, 0, 16);
//                    }
//                    getParams().glHandler.post(updateSensorRunnable);
//                    break;
//            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (this.mSensorListener != null){
            this.mSensorListener.onAccuracyChanged(sensor,accuracy);
        }
    }
}

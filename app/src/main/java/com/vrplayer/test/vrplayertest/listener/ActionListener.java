package com.vrplayer.test.vrplayertest.listener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.google.vrtoolkit.cardboard.sensors.SensorEventProvider;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by userd on 2017/9/22.
 */

public class ActionListener implements SensorEventProvider {

    private static final String LOG_TAG = ActionListener.class.getSimpleName();
    private boolean isRunning;
    private SensorManager sensorManager;
    private Looper sensorLooper;
    private SensorEventListener sensorEventListener;
    private final ArrayList<SensorEventListener> registeredListeners = new ArrayList();
    private int level;

    public ActionListener(SensorManager sensorManager, int level) {
        this.sensorManager = sensorManager;
        this.level = level;
    }

    public ActionListener(SensorManager sensorManager) {
        this(sensorManager, 0);
    }

    private Sensor getUncalibratedGyro() {
        return Build.MANUFACTURER.equals("HTC")?null:this.sensorManager.getDefaultSensor(16);
    }

    public void start() {
        if(!this.isRunning) {
            this.sensorEventListener = new SensorEventListener() {
                public void onSensorChanged(SensorEvent event) {
                    synchronized(ActionListener.this.registeredListeners) {
                        Iterator var3 = ActionListener.this.registeredListeners.iterator();

                        while(var3.hasNext()) {
                            SensorEventListener listener = (SensorEventListener)var3.next();
                            listener.onSensorChanged(event);
                        }

                    }
                }

                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    synchronized(ActionListener.this.registeredListeners) {
                        Iterator var4 = ActionListener.this.registeredListeners.iterator();

                        while(var4.hasNext()) {
                            SensorEventListener listener = (SensorEventListener)var4.next();
                            listener.onAccuracyChanged(sensor, accuracy);
                        }

                    }
                }
            };
            HandlerThread sensorThread = new HandlerThread("sensor") {
                protected void onLooperPrepared() {
                    Handler handler = new Handler(Looper.myLooper());
                    Sensor accelerometer = ActionListener.this.sensorManager.getDefaultSensor(1);
                    ActionListener.this.sensorManager.registerListener(ActionListener.this.sensorEventListener, accelerometer, level, handler);
                    Sensor gyroscope = ActionListener.this.getUncalibratedGyro();
                    if(gyroscope == null) {
                        Log.i(ActionListener.LOG_TAG, "Uncalibrated gyroscope unavailable, default to regular gyroscope.");
                        gyroscope = ActionListener.this.sensorManager.getDefaultSensor(4);
                    }

                    ActionListener.this.sensorManager.registerListener(ActionListener.this.sensorEventListener, gyroscope, level, handler);
                }
            };
            sensorThread.start();
            this.sensorLooper = sensorThread.getLooper();
            this.isRunning = true;
        }
    }

    public void stop() {
        if(this.isRunning) {
            this.sensorManager.unregisterListener(this.sensorEventListener);
            this.sensorEventListener = null;
            this.sensorLooper.quit();
            this.sensorLooper = null;
            this.isRunning = false;
        }
    }

    public void registerListener(SensorEventListener listener) {
        ArrayList var2 = this.registeredListeners;
        synchronized(this.registeredListeners) {
            this.registeredListeners.add(listener);
        }
    }

    public void unregisterListener(SensorEventListener listener) {
        ArrayList var2 = this.registeredListeners;
        synchronized(this.registeredListeners) {
            this.registeredListeners.remove(listener);
        }
    }
}

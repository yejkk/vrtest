package com.asha.vrlib.strategy.interactive;

import android.content.Context;
import android.view.WindowManager;

import com.asha.vrlib.MD360Director;
import com.asha.vrlib.strategy.interactive.InteractiveModeManager.Params;
import com.asha.vrlib.common.VrSenorEvent;
import com.asha.vrlib.common.VRUtil;

public class MobileMotionStrategy  extends AbsInteractiveStrategy  {
	
	private WindowManager windowManager;
	private float[] mSensorMatrix = new float[16];

    private float[] mTmpMatrix = new float[16];
    
    private boolean mRegistered = true;

    private Boolean mIsSupport = null;

    private boolean isOn ;
    
    private final Object mMatrixLock = new Object();

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
        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        for (MD360Director director : getDirectorList()){
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
		
		VRUtil.sensorRotationVector2Matrix(vrSenorEvent.values, windowManager.getDefaultDisplay().getRotation(), mSensorMatrix);

        // mTmpMatrix will be used in multi thread.
		synchronized (mMatrixLock){
			System.arraycopy(mSensorMatrix, 0, mTmpMatrix, 0, 16);
		}
        getParams().glHandler.post(updateSensorRunnable);
        return true;
    }
	
	 private Runnable updateSensorRunnable = new Runnable() {
	        @Override
	        public void run() {
	            if (!mRegistered || !isOn) return;
	            // mTmpMatrix will be used in multi thread.
	            synchronized (mMatrixLock){
	                for (MD360Director director : getDirectorList()){
	                    director.updateSensorMatrix(mTmpMatrix);
	                }
	            }
	        }
	    };
	

}

package com.vrplayer.test.vrplayertest.gesture;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.vrplayer.test.vrplayertest.LogManager;
import com.vrplayer.test.vrplayertest.socket.SendSocket;

/**
 * Created by userd on 2017/9/21.
 */

public class gesturelistener {

    private GestureDetector mGestureDetector;
    private ValueAnimator valueAnimator;
    private FlingConfig  mFlingConfig;
    private boolean mFlingEnabled;

    private float mGlobalScale;
    private float mTouchSensitivity;
    private int mCurrentMode = 0;
    private static final int MODE_INIT = 0;
    private static final int MODE_PINCH = 1;

    private SendSocket.SendSocketListener sendSocketListener;

    public gesturelistener(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mCurrentMode == MODE_PINCH) return false;

               /* for (MDVRLibrary.IGestureListener listener : mClickListeners){
                    listener.onClick(e);
                }*/
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (mCurrentMode == MODE_PINCH) return false;

                if (sendSocketListener != null){
                    sendSocketListener.onSend(scaled(distanceX), scaled(distanceY));
                }
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (mCurrentMode == MODE_PINCH) return false;
                if (!mFlingEnabled) return false;

                animStart(velocityX, velocityY);
                return true;
            }
        });
    }

    public boolean handleTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        LogManager.i("action","vr_control");
        if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            if (mCurrentMode == MODE_PINCH) {
                // end anim
            }
            mCurrentMode = MODE_INIT;
        } else if (action == MotionEvent.ACTION_POINTER_UP) {
            // one point up
            if (mCurrentMode == MODE_PINCH) {
                // more than 2 pointer
                /*if (event.getPointerCount() > 2) {
                    if (event.getAction() >> 8 == 0) {
                        // 0 up
                        markPinchInfo(event.getX(1), event.getY(1), event.getX(2), event.getY(2));
                    } else if (event.getAction() >> 8 == 1) {
                        // 1 up
                        markPinchInfo(event.getX(0), event.getY(0), event.getX(2), event.getY(2));
                    }
                }*/
            }
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) {
            // >= 2 pointer
            mCurrentMode = MODE_PINCH;
//            markPinchInfo(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
        } else if (action == MotionEvent.ACTION_MOVE) {
            // >= 2 pointer
           /* if (mCurrentMode == MODE_PINCH && event.getPointerCount() > 1) {
                float distance = calDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                // float[] lineCenter = MathUtils.getCenterPoint(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                // mLastMovePoint.set(lineCenter[0], lineCenter[1]);
                handlePinch(distance);
            }*/
        } else if (action == MotionEvent.ACTION_DOWN){
            animCancel();
        }

        mGestureDetector.onTouchEvent(event);
        return true;
    }



    private void animStart(float velocityX, float velocityY) {
        animCancel();

        PropertyValuesHolder hvx = PropertyValuesHolder.ofFloat("vx", velocityX, 0);
        PropertyValuesHolder hvy = PropertyValuesHolder.ofFloat("vy", velocityY, 0);
        valueAnimator = ValueAnimator.ofPropertyValuesHolder(hvx, hvy).setDuration(mFlingConfig.getDuring());
        valueAnimator.setInterpolator(mFlingConfig.getInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private long lastTime = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long now = animation.getCurrentPlayTime();
                long dur = (now - lastTime);
                float sx = (float) animation.getAnimatedValue("vx") * dur / -1000 * mFlingConfig.getSensitivity();
                float sy = (float) animation.getAnimatedValue("vy") * dur / -1000 * mFlingConfig.getSensitivity();
                lastTime = now;

               /* if (mAdvanceGestureListener != null){
                    mAdvanceGestureListener.onDrag(scaled(sx), scaled(sy));
                }*/
            }
        });
        valueAnimator.start();
    }

    private void animCancel(){
        if (valueAnimator != null){
            valueAnimator.cancel();
        }
    }


    public boolean isFlingEnabled() {
        return mFlingEnabled;
    }

    public void setFlingEnabled(boolean flingEnabled) {
        this.mFlingEnabled = flingEnabled;
    }

    public void setFlingConfig(FlingConfig flingConfig) {
        this.mFlingConfig = flingConfig;
    }

    private float scaled(float input) {
        return input / mGlobalScale * mTouchSensitivity;
    }

    public void setmSendSocketListener(SendSocket.SendSocketListener mSendSocketListener) {
        sendSocketListener = mSendSocketListener;
    }

}

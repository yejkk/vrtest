package com.vrplayer.test.vrplayertest;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vrplayer.test.vrplayertest.gesture.gesturelistener;
import com.vrplayer.test.vrplayertest.socket.BasedParcelable;
import com.vrplayer.test.vrplayertest.socket.SendSocket;
import com.vrplayer.test.vrplayertest.socket.SendSocket.SendSocketListener;
import com.vrplayer.test.vrplayertest.socket.TouchMsgParcelable;

public class MainActivity extends Activity implements View.OnClickListener {
    private final String TAG="yxtest";
    private Button vrPlayButton;
    private Button vrControlButton;
    private View view;
    private gesturelistener mgesturelistener;
    private SendSocket sendSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        view = LayoutInflater.from(this).inflate(R.layout.activity_main,null );
        vrPlayButton = findViewById(R.id.vr_play);
        vrPlayButton.setOnClickListener(this);
        vrControlButton = findViewById(R.id.vr_control);
        vrControlButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View curview) {
        Log.i(TAG,"onClick");
        int i = curview.getId();
        if (i == R.id.vr_play) {
            Intent mIntent = new Intent(this, VRPlayerActivity.class);
            mIntent.setData(Uri.parse("http://192.168.20.177/openvod/yxtest/test.m3u8"));
            startActivity(mIntent);

        } else if (i == R.id.vr_control) {
            Toast.makeText(getApplication(), "vr_control", Toast.LENGTH_LONG);
            LogManager.i(TAG, "vr_control");
            sendSocket = new SendSocket();
            sendSocket.setmSendSocketListener(new SendSocketListener() {

                @Override
                public void onSend(float distanceX, float distanceY) {
                    BasedParcelable touchMsg = new TouchMsgParcelable(distanceX, distanceY);
                    sendSocket.sendMsg(touchMsg);
                }

                @Override
                public void onSendEvent(SensorEvent mSensorEvent, int rotation) {

                }
            });
            mgesturelistener = new gesturelistener(this);
            mgesturelistener.setmSendSocketListener(sendSocket.getmSendSocketListener());
            view.requestFocus();
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    LogManager.i(TAG, motionEvent.toString());
                    mgesturelistener.handleTouchEvent(motionEvent);
                    return true;
                }
            });


        } else {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogManager.i(TAG,"touch"+event);
        mgesturelistener.handleTouchEvent(event);
        return false;
    }
}

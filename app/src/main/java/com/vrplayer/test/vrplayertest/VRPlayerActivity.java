package com.vrplayer.test.vrplayertest;

import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.strategy.interactive.AbsInteractiveStrategy;
import com.asha.vrlib.strategy.interactive.InteractiveModeManager;
import com.vrplayer.test.vrplayertest.MDVRPlayer.BasePlayerActivity;
import com.vrplayer.test.vrplayertest.MDVRPlayer.MediaPlayerWrapper;
import com.vrplayer.test.vrplayertest.gesture.KeyStrategy;
import com.vrplayer.test.vrplayertest.gesture.MRLibControl;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by userd on 2017/9/18.
 */

public class VRPlayerActivity extends BasePlayerActivity{

    private final String TAG="VRPlayerActivity";
    GLSurfaceView testGlSurfaceView;
    private MediaPlayerWrapper mMediaPlayerWrapper = new MediaPlayerWrapper();
    private MRLibControl mMRLibControl;


    @Override
    public void onCreate(Bundle savedBundle){
        super.onCreate(savedBundle);
        mMediaPlayerWrapper.init();
        mMediaPlayerWrapper.setPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                cancelBusy();
                if (getVRLibrary() != null){
                    getVRLibrary().notifyPlayerChanged();
                }
            }
        });

        mMediaPlayerWrapper.getPlayer().setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                String error = String.format("Play Error what=%d extra=%d",what,extra);
                Toast.makeText(VRPlayerActivity.this, error, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mMediaPlayerWrapper.getPlayer().setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                getVRLibrary().onTextureResize(width, height);
            }
        });

        Uri uri = getUri();
        if (uri != null){
            mMediaPlayerWrapper.openRemoteFile(uri.toString());
            mMediaPlayerWrapper.prepare();
        }

        findViewById(R.id.control_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayerWrapper.pause();
                mMediaPlayerWrapper.destroy();
                mMediaPlayerWrapper.init();
                mMediaPlayerWrapper.openRemoteFile("video_31b451b7ca49710719b19d22e19d9e60.mp4");
                mMediaPlayerWrapper.prepare();
            }
        });


    }

    @Override
    protected MDVRLibrary createVRLibrary() {
        MDVRLibrary mMDVRLibrary = MDVRLibrary.with(this)
                .asVideo(new MDVRLibrary.IOnSurfaceReadyCallback() {
                    @Override
                    public void onSurfaceReady(Surface surface) {
                        mMediaPlayerWrapper.setSurface(surface);
                    }
                })
                .build(findViewById(R.id.gl_view));
        mMRLibControl = MRLibControl.with(this).setMDVRLibrary(mMDVRLibrary).build();

        return mMDVRLibrary;
    }

    /* TODO, FIXME */
	/* 1. where should we take the key event, here or let view's requestFocus */
	/* 2. support touch event */
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG + "key", "key down");
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(checkStrategy()){
                    Log.d(TAG , "mStrategy down");
                    mStrategy.handleDrag(-20,0);
                    return true;
                }
                return false;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(checkStrategy()){
                    Log.d(TAG , "mStrategy down");
                    mStrategy.handleDrag(20,0);
                    return true;
                }
                return false;
            case KeyEvent.KEYCODE_DPAD_UP:
                if(checkStrategy()){
                    Log.d(TAG , "mStrategy down");
                    mStrategy.handleDrag(0,-20);
                    return true;
                }
                return false;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(checkStrategy()){
                    Log.d(TAG , "mStrategy down");
                    mStrategy.handleDrag(0,20);
                    return true;
                }
                return false;
        }
        return false;
    }

    private AbsInteractiveStrategy mStrategy;
    public boolean handlerKeyEvent(){
        InteractiveModeManager.Params mParams = getVRLibrary().getInteractiveModeManager().getmParams();
        if(mParams == null){
            return false;
        }
        mStrategy = new KeyStrategy(mParams);
        return true;
    }

    public boolean checkStrategy(){
        if(mStrategy == null || !mStrategy.isSupport(this)){
            return (handlerKeyEvent()&&mStrategy.isSupport(this) );
        }else {
            return true;
        }
    }

}

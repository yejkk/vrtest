package com.vrplayer.test.vrplayertest.socket;

import android.hardware.SensorEvent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by userd on 2017/11/10.
 */

public class BasedParcelable implements Parcelable {

    public static final Creator<MsgParcelable> CREATOR = new Creator<MsgParcelable>() {
        @Override
        public MsgParcelable createFromParcel(Parcel in) {
            return new MsgParcelable(in);
        }

        @Override
        public MsgParcelable[] newArray(int size) {
            return new MsgParcelable[size];
        }
    };

    private int rotation;
    private SensorEvent mSensorEvent;


    public BasedParcelable() {
        super();

    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public SensorEvent getmSensorEvent() {
        return mSensorEvent;
    }

    public void setmSensorEvent(SensorEvent mSensorEvent) {
        this.mSensorEvent = mSensorEvent;
    }

    /**
     * 序列化功能由writeToParcel完成，最终通过Parcel的一系列Write方法完成
     * <p>
     * 功能：将当前对象写入序列化结构中，其中flags标识有两种值，0或1
     * 为1时标识当前对象需要作为返回值返回，不能立刻释放资源，即PARCELABLE_WRITE_RETURN_VALUE
     * 不过几乎所有情况都为0
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rotation);
        dest.writeString(mSensorEvent.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

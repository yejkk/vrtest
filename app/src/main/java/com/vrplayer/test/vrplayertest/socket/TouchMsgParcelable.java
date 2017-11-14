package com.vrplayer.test.vrplayertest.socket;

import android.os.Parcel;

/**
 * Created by userd on 2017/11/10.
 */

public class TouchMsgParcelable extends BasedParcelable {

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

    private float distanceX;
    private float distanceY;


    public TouchMsgParcelable(float distanceX, float distanceY) {
        super();
        this.distanceX = distanceX;
        this.distanceY = distanceY;
    }

    protected TouchMsgParcelable(Parcel in) {
        distanceX = in.readFloat();
        distanceY = in.readFloat();
    }

    public float getDistanceX() {
        return distanceX;
    }

    public void setDistanceX(float distanceX) {
        this.distanceX = distanceX;
    }

    public float getDistanceY() {
        return distanceY;
    }

    public void setDistanceY(float distanceY) {
        this.distanceY = distanceY;
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
        dest.writeFloat(distanceX);
        dest.writeFloat(distanceY);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

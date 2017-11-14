package com.vrplayer.test.vrplayertest.socket;

import android.hardware.SensorEvent;
import android.widget.Button;
import android.widget.EditText;

import com.vrplayer.test.vrplayertest.LogManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by userd on 2017/9/20.
 */

public class SendSocket {

    private static String IpAddress = "192.168.21.168";
    private static int Port = 9099;
    Socket socket = null;
    private EditText edittext = null;
    private Button send = null;
    private SendSocketListener mSendSocketListener;

    // 发送信息
    public void sendMsg(BasedParcelable mMsgParcelable) {

        try {
            // 创建socket对象，指定服务器端地址和端口号
            socket = new Socket(IpAddress, Port);
            /*// 获取 Client 端的输出流
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);*/
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            // 填充信息
            out.writeObject(mMsgParcelable);
            out.flush();
            out.close();
            System.out.println("msg=" + mMsgParcelable.toString());
            LogManager.i("SendSocket","msg=" + mMsgParcelable.toString());
            // 关闭

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setmSendSocketListener(SendSocketListener mSendSocketListener) {
        this.mSendSocketListener = mSendSocketListener;
    }

    public SendSocketListener getmSendSocketListener() {
        return mSendSocketListener;
    }

    public interface SendSocketListener {
        void onSend(float distanceX, float distanceY);

        void onSendEvent(SensorEvent mSensorEvent, int rotation);
    }
}

package com.atoken.cn.android_tcp.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Aatoken on 2018/6/11.
 */

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    //创建handler
    private static class MessengerHanlder extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MessengerType.MSG_FROM_CLIENT:
                    String data = msg.getData().getString("msg");
                    Log.d(TAG, data);

                    //接收到服务端信息之后返回
                    Messenger client = msg.replyTo;
                    Message replyMsg = Message.obtain(null, MessengerType.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "this is service...");
                    replyMsg.setData(bundle);
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }

    //创建消息
    private final Messenger mMessenger = new Messenger(new MessengerHanlder());


}

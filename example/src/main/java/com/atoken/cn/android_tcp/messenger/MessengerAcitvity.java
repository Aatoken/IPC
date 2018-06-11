package com.atoken.cn.android_tcp.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.atoken.cn.android_tcp.R;

/**
 * Created by Aatoken on 2018/6/11.
 */

public class MessengerAcitvity extends AppCompatActivity {
    private static final String TAG = "MessengerAcitvity";

    //2.定义Messenger
    private Messenger mService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_messenger);

        //1.绑定服务 并在onDestroy中销毁
        Intent intent=new Intent(this,MessengerService.class);
        bindService(intent,mConnection,BIND_AUTO_CREATE);

    }

    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mService=new Messenger(service);
            Message msg=Message.obtain(null,MessengerType.MSG_FROM_CLIENT);
            Bundle bundle=new Bundle();
            bundle.putString("msg","this is clinet...");
            msg.setData(bundle);
            Log.d(TAG,"客户端开始发送消息");

            msg.replyTo=mReplyMessenger;

            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    //创建 handler，messenger去接收服务端发送的消息
    private Messenger mReplyMessenger=new Messenger(new MessengerHandler());
    private static class   MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MessengerType.MSG_FROM_SERVICE:
                    String reply = msg.getData().getString("reply");
                    Log.d(TAG,reply);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }


        }
    }




}

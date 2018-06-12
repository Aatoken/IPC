package com.atoken.cn.android_tcp.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.atoken.cn.android_tcp.R;
import com.atoken.cn.android_tcp.messenger.MessageType;

import java.util.List;

/**
 * Created by Aatoken on 2018/6/11.
 */

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BMA";
    private IBookManager mBookManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_aidl_bookmanager);

        Intent intent = new Intent(BookManagerActivity.this, BookManagerService.class);
        bindService(intent, mConnect, BIND_AUTO_CREATE);

    }

    private ServiceConnection mConnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = IBookManager.Stub.asInterface(service);

            try {
                Book newBook = new Book(3, "linux");
                mBookManager.addBook(newBook);
                List<Book> list = mBookManager.getBookList();
                Log.d(TAG, "query book list,list type:" + list.getClass().getCanonicalName());
                Log.d(TAG, "query book list:" + list.toString());

                //订阅事件
                mBookManager.registListence(mNewBookArrivedListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }


            //设置死亡代理
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager=null;
            Log.d(TAG,"bind died!");
        }
    };

    private INewBookArrivedListener mNewBookArrivedListener = new INewBookArrivedListener.Stub() {

        @Override
        public void OnNewBookArrived(Book newbook) throws RemoteException {

            //通过handler处理事件
            mHandler.obtainMessage(MessageType.MSG_FROM_NewBook_Arrivad, newbook)
                    .sendToTarget();


        }

    };

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MessageType.MSG_FROM_NewBook_Arrivad:
                    Log.d(TAG, "receive new book:" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {

        if(mBookManager!=null && mBookManager.asBinder().isBinderAlive())
        {
            Log.d(TAG,"unregister listencer:"+mNewBookArrivedListener);
            try {
                mBookManager.unregistListence(mNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }



        unbindService(mConnect);
        super.onDestroy();

    }


    private IBinder.DeathRecipient mDeathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(mBookManager!=null)
            {
                return;
            }
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient,0);
            mBookManager=null;
        }
    };


}

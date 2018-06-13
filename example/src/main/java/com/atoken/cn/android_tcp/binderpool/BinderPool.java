package com.atoken.cn.android_tcp.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Aatoken on 2018/6/13.
 */

public class BinderPool {

    private static final String TAG = "BinderPool";
    private Context mContext;
    private static volatile BinderPool mInstance;
    private CountDownLatch mPoolDownLatch;

    private IBinderPool mBinderPool;



    public static BinderPool getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BinderPool.class) {
                if (mInstance == null) {
                    mInstance = new BinderPool(context);
                }
            }
        }
        return mInstance;
    }

    private BinderPool(Context context) {

        mContext = context.getApplicationContext();
        connectBinderService();
    }

    private synchronized void connectBinderService() {
        mPoolDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, connect, Context.BIND_AUTO_CREATE);

        try {
            mPoolDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    ServiceConnection connect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBinderPool = IBinderPool.Stub.asInterface(service);

            Log.d(TAG,"service success");

            //死亡代理
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            mPoolDownLatch.countDown();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"service failed");

        }
    };


    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binder died");
            //解除绑定
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBinderPool = null;
            //重连服务
            connectBinderService();
        }
    };


    /**
     * 外部调用 根据binderCode返回 Ibinder
     * @param binderCode
     * @return
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
         return binder;
    }

}
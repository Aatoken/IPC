package com.atoken.cn.android_tcp.binderpool;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.atoken.cn.android_tcp.R;

/**
 * Created by Aatoken on 2018/6/13.
 */

public class BinderPoolAcitivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolAcitivity";

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_binderpool);

        new Thread(){
            @Override
            public void run() {
                doWork();
            }
        }.start();

    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(BinderPoolAcitivity.this);

        IBinder securityBinder = binderPool.queryBinder(BinderType.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);

        Log.d(TAG, "visit ISecurityCenter");

        String msg = "helloworld-安卓";

        try {
            String pwd = mSecurityCenter.encrypt(msg);
            Log.d(TAG, "encrypt:" + pwd);
            Log.d(TAG, "decrypt:" + mSecurityCenter.decrypt(pwd));

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        IBinder comBinder = binderPool.queryBinder(BinderType.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(comBinder);

        Log.d(TAG, "visit ICompute");

        try {
            Log.d(TAG, "5+7:" + mCompute.add(5, 7));
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


}

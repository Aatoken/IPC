package com.atoken.cn.android_tcp.binderpool;

import android.os.RemoteException;

/**
 * Created by Aatoken on 2018/6/13.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }



}

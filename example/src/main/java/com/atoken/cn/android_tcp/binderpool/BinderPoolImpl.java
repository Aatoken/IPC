package com.atoken.cn.android_tcp.binderpool;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by Aatoken on 2018/6/13.
 */

public class BinderPoolImpl extends IBinderPool.Stub  {
    public BinderPoolImpl() {
    }

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {

        IBinder binder = null;
        //swtich()变量类型只能是int、short、char、byte和enum类型
        // （JDK 1.7 之后，类型也可以是String了）
        switch (binderCode) {
            case BinderType.BINDER_NONE:
                break;
            case BinderType.BINDER_SECURITY_CENTER:
                binder = new SecurityCenterImpl();
                break;
            case BinderType.BINDER_COMPUTE:
                binder = new ComputeImpl();
                break;
            default:
                break;
        }

        return binder;
    }
}

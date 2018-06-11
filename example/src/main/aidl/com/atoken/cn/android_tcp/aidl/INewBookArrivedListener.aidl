// INewBookArrivedListener.aidl
package com.atoken.cn.android_tcp.aidl;

//导包
import com.atoken.cn.android_tcp.aidl.Book;

interface INewBookArrivedListener {
    void OnNewBookArrived(in Book newbook);
}

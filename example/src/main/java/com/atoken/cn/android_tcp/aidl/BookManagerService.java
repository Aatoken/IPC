package com.atoken.cn.android_tcp.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Aatoken on 2018/6/11.
 */

public class BookManagerService extends Service {

    private static final String TAG = "BMS";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<INewBookArrivedListener> mListeners = new CopyOnWriteArrayList<>();

    //能够保证在高并发的情况下只有一个线程能够访问这个属性值。（类似我们之前所说的volatile）
    private AtomicBoolean mIsServiceDestory=new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG,"service 调用 getBookList");
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG,"service 调用 addBook");
            mBookList.add(book);
        }

        @Override
        public void registListence(INewBookArrivedListener listener) throws RemoteException {
            if(!mListeners.contains(listener))
            {
                mListeners.add(listener);
                Log.d(TAG,"订阅成功！");
            }else {
                Log.d(TAG,"已经订阅过了！");
            }
        }

        @Override
        public void unregistListence(INewBookArrivedListener listener) throws RemoteException {
            if(mListeners.contains(listener))
            {
                mListeners.remove(listener);
                Log.d(TAG,"注销订阅！");
            }else {
                Log.d(TAG,"没有订阅，无法注销！");
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "android"));
        mBookList.add(new Book(1, "ios"));

        //开启线程自动添加书籍
        new Thread(new ServiceWorker()).start();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        Log.d(TAG,"onNewBookArrivad,notify listences:"+mBookList.size());

        for (int i=0;i<mListeners.size();i++)
        {
            INewBookArrivedListener listener = mListeners.get(i);
            Log.d(TAG,"onNewBookArrivad,notify listences:"+listener);
            listener.OnNewBookArrived(book);
        }

    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            Log.d(TAG,"开启线程");
            while (!mIsServiceDestory.get())
            {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int bookId=mBookList.size()+1;
                Book newbook=new Book(bookId,"new book-"+bookId);

                try {
                    onNewBookArrived(newbook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onDestroy() {
        mIsServiceDestory.set(true);
        super.onDestroy();
        
    }
}

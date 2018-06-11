package com.atoken.cn.android_tcp.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.atoken.cn.android_tcp.R;

import java.util.List;

/**
 * Created by Aatoken on 2018/6/11.
 */

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG="BMA";
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
            IBookManager bookManager=IBookManager.Stub.asInterface(service);

            try {
                Book newBook=new Book(3,"linux");
                bookManager.addBook(newBook);
                List<Book> list=bookManager.getBookList();
                Log.d(TAG,"query book list,list type:"+list.getClass().getCanonicalName());
                Log.d(TAG,"query book list:"+list.toString());
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
        super.onDestroy();
        unbindService(mConnect);
    }
}

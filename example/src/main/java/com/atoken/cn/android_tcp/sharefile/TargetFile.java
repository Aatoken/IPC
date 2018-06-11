package com.atoken.cn.android_tcp.sharefile;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.atoken.cn.android_tcp.R;
import com.atoken.cn.android_tcp.ipckonws.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Aatoken on 2018/6/11.
 */

public class TargetFile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_targetfile);

        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                File file = new File(Environment.getExternalStorageDirectory(), "handler.txt");
                if(file.exists())
                {
                    try {
                        ObjectInputStream input=new ObjectInputStream(
                                new FileInputStream(file)
                        );

                        user=(User)input.readObject();

                        input.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }


}
